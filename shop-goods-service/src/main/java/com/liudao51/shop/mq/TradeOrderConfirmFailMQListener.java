package com.liudao51.shop.mq;

import com.alibaba.fastjson.JSON;
import com.liudao51.shop.common.constant.AppCode;
import com.liudao51.shop.common.constant.ErrorCode;
import com.liudao51.shop.common.exception.ExceptionUtilsX;
import com.liudao51.shop.common.util.ObjectUtilsX;
import com.liudao51.shop.common.util.PropertiesUtilsX;
import com.liudao51.shop.common.uuid.UidUtilsX;
import com.liudao51.shop.dao.ITradeGoodsDao;
import com.liudao51.shop.dao.ITradeMqConsumerLogDao;
import com.liudao51.shop.entity.po.TradeGoods;
import com.liudao51.shop.entity.po.TradeMqConsumerLog;
import com.liudao51.shop.entity.pojo.TradeOrderMqInfo;
import com.liudao51.shop.util.TradeGoodsLogUtilsX;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 订单确认失败MQ消息监听器
 */
@Component
@RocketMQMessageListener(topic = "${app.mq.topic.order}", consumerGroup = "${app.mq.consumer.group.order}", messageModel = MessageModel.BROADCASTING)

public class TradeOrderConfirmFailMQListener implements RocketMQListener<MessageExt> {
    @Autowired
    private ITradeGoodsDao tradeGoodsDao;

    @Autowired
    private ITradeMqConsumerLogDao tradeMqConsumerLogDao;

    @Override
    public void onMessage(MessageExt messageExt) {
        //保证时间统一
        Long currentTime = new Date().getTime();
        Integer affectedRows = 0;
        String groupName = PropertiesUtilsX.getDefaultProperties().getProperty("app.mq.consumer.group.order", AppCode.MQ_CONSUMER_GROUP_ORDER.getValue());
        String tag = null;
        String key = null;
        String msgId = null;
        String body = null;
        TradeMqConsumerLog tradeMqConsumerLog = null;

        try {
            //1.解释消息内容
            msgId = messageExt.getMsgId();
            tag = messageExt.getTags();
            key = messageExt.getKeys();
            body = new String(messageExt.getBody(), AppCode.CHARSET_UTF8.getValue());
            TradeOrderMqInfo tradeOrderMqInfo = JSON.parseObject(body, TradeOrderMqInfo.class);
            TradeGoodsLogUtilsX.debug("goodService接收订单取消消息成功");

            //===>>>回退库存（因为库存可以重复减少,所以这里需要核查该消息是否消费过,以防止多次消费）
            if (ObjectUtilsX.isEmpty(tradeOrderMqInfo)) {
                ExceptionUtilsX.cast(ErrorCode.GOODS_STOCK_ROLLBACK_ERROR);
            }

            //2.查找消费记录并更新消费处理状态为处理中
            Map<String, Object> map = new HashMap<>();
            map.put("msg_id", msgId);
            map.put("msg_tag", tag);
            map.put("msg_key", key);
            map.put("group_name", groupName);
            tradeMqConsumerLog = tradeMqConsumerLogDao.selectOne(map);

            //没有消费记录
            if (ObjectUtilsX.isEmpty(tradeMqConsumerLog)) {
                tradeMqConsumerLog = new TradeMqConsumerLog();
                tradeMqConsumerLog.setMqConsumerLogId(UidUtilsX.getUid());
                tradeMqConsumerLog.setMsgId(msgId);
                tradeMqConsumerLog.setMsgGroupName(groupName);
                tradeMqConsumerLog.setMsgTag(tag);
                tradeMqConsumerLog.setMsgKey(key);
                tradeMqConsumerLog.setMsgBody(body);
                tradeMqConsumerLog.setConsumerStatus(AppCode.MQ_CONSUMER_STATUS_PROCESSING.getCode());
                tradeMqConsumerLog.setConsumerCount(0);
                tradeMqConsumerLog.setConsumerTime(0L);
                tradeMqConsumerLog.setRemark("订单确认失败");
                tradeMqConsumerLog.setCreateTime(currentTime);
                tradeMqConsumerLog.setUpdateTime(currentTime);
                affectedRows = tradeMqConsumerLogDao.insertById(tradeMqConsumerLog);
                if (0 == affectedRows) {
                    ExceptionUtilsX.cast(ErrorCode.MQ_CONSUMER_STATUS_INSERT_ERROR);
                }
            } else { //有消费记录
                //处理过且成功
                if (AppCode.MQ_CONSUMER_STATUS_SUCCESS.getCode().equals(tradeMqConsumerLog.getConsumerStatus())) {
                    TradeGoodsLogUtilsX.debug("消息：" + msgId + ",已经处理过");
                    return;
                }
                //处理中
                if (AppCode.MQ_CONSUMER_STATUS_PROCESSING.getCode().equals(tradeMqConsumerLog.getConsumerStatus())) {
                    TradeGoodsLogUtilsX.debug("消息：" + msgId + ",正在处理");
                    return;
                }
                //处理失败
                if (AppCode.MQ_CONSUMER_STATUS_FAIL.getCode().equals(tradeMqConsumerLog.getConsumerStatus())) {
                    Integer times = tradeMqConsumerLog.getConsumerCount();
                    if (times > AppCode.MQ_CONSUMER_COUNT_MAX.getCode()) {
                        TradeGoodsLogUtilsX.debug("消息：" + msgId + ",处理处数不能超过" + AppCode.MQ_CONSUMER_COUNT_MAX.getCode() + "次");
                    }
                    tradeMqConsumerLog.setConsumerStatus(AppCode.MQ_CONSUMER_STATUS_PROCESSING.getCode());
                    tradeMqConsumerLog.setUpdateTime(currentTime);
                    affectedRows = tradeMqConsumerLogDao.updateById(tradeMqConsumerLog);
                    if (0 == affectedRows) {
                        ExceptionUtilsX.cast(ErrorCode.MQ_CONSUMER_STATUS_PROCESSING_UPDATE_ERROR);
                    }
                }
            }

            //3.执行商品库存回退
            TradeGoods goods = new TradeGoods();
            goods.setGoodsId(tradeOrderMqInfo.getGoodsId());
            TradeGoods goods2 = tradeGoodsDao.selectById(goods);
            if (ObjectUtilsX.isEmpty(goods2)) {
                ExceptionUtilsX.cast(ErrorCode.MQ_MESSAGE_GOODS_INVALID_ERROR);
            }
            goods2.setGoodsStock(goods2.getGoodsStock() + tradeOrderMqInfo.getGoodsNum());
            goods2.setUpdateTime(currentTime);
            affectedRows = tradeGoodsDao.updateById(goods2);
            if (0 == affectedRows) {
                TradeGoodsLogUtilsX.debug("消息：" + msgId + "," + ErrorCode.GOODS_STOCK_ROLLBACK_ERROR);
                ExceptionUtilsX.cast(ErrorCode.GOODS_STOCK_ROLLBACK_ERROR);
            }

            //4.更新消费处理状态为处理成功
            tradeMqConsumerLog.setConsumerStatus(AppCode.MQ_CONSUMER_STATUS_SUCCESS.getCode());
            tradeMqConsumerLog.setConsumerTime(currentTime);
            tradeMqConsumerLog.setUpdateTime(currentTime + 1); //防止跟前面的更新时间一样,这里时间要加1
            affectedRows = tradeMqConsumerLogDao.updateById(tradeMqConsumerLog);
            if (0 == affectedRows) {
                ExceptionUtilsX.cast(ErrorCode.MQ_CONSUMER_STATUS_SUCCESS_UPDATE_ERROR);
            }

        } catch (Exception e) {
            TradeGoodsLogUtilsX.error("订单:" + e.getMessage(), e);
            try {
                //5.当报错时重新查找消费记录并更新消费处理状态为处理失败
                Map<String, Object> map = new HashMap<>();
                map.put("msg_id", msgId);
                map.put("msg_tag", tag);
                map.put("msg_key", key);
                map.put("group_name", groupName);
                TradeMqConsumerLog tradeMqConsumerLog2 = tradeMqConsumerLogDao.selectOne(map);
                if (ObjectUtilsX.isEmpty(tradeMqConsumerLog2)) {
                    //数据未入库成功
                    tradeMqConsumerLog2.setMqConsumerLogId(UidUtilsX.getUid());
                    tradeMqConsumerLog2.setMsgId(msgId);
                    tradeMqConsumerLog2.setMsgGroupName(groupName);
                    tradeMqConsumerLog2.setMsgTag(tag);
                    tradeMqConsumerLog2.setMsgKey(key);
                    tradeMqConsumerLog2.setMsgBody(body);
                    tradeMqConsumerLog2.setConsumerStatus(AppCode.MQ_CONSUMER_STATUS_FAIL.getCode());
                    tradeMqConsumerLog2.setConsumerCount(0);
                    tradeMqConsumerLog2.setConsumerTime(0L);
                    tradeMqConsumerLog2.setRemark("订单确认失败");
                    tradeMqConsumerLog2.setCreateTime(currentTime);
                    tradeMqConsumerLog2.setUpdateTime(currentTime);
                    affectedRows = tradeMqConsumerLogDao.insertById(tradeMqConsumerLog2);
                    if (0 == affectedRows) {
                        ExceptionUtilsX.cast(ErrorCode.MQ_CONSUMER_STATUS_INSERT_ERROR);
                    }
                } else {
                    tradeMqConsumerLog2.setConsumerStatus(AppCode.MQ_CONSUMER_STATUS_FAIL.getCode());
                    tradeMqConsumerLog2.setConsumerCount(tradeMqConsumerLog2.getConsumerCount() + 1); //失败次数累加
                    tradeMqConsumerLog2.setUpdateTime(currentTime + 1);
                    affectedRows = tradeMqConsumerLogDao.updateById(tradeMqConsumerLog2);
                    if (0 == affectedRows) {
                        ExceptionUtilsX.cast(ErrorCode.MQ_CONSUMER_STATUS_FAIL_UPDATE_ERROR);
                    }
                }
            } catch (Exception e2) {
                TradeGoodsLogUtilsX.error("订单:" + e2.getMessage(), e2);
                ExceptionUtilsX.cast(ErrorCode.GOODS_STOCK_ROLLBACK_ERROR);
            }
        }
    }
}
