package com.liudao51.shop.mq;

import com.alibaba.fastjson.JSON;
import com.liudao51.shop.common.constant.AppCode;
import com.liudao51.shop.common.constant.ErrorCode;
import com.liudao51.shop.common.exception.ExceptionUtilsX;
import com.liudao51.shop.common.util.ObjectUtilsX;
import com.liudao51.shop.dao.ITradeOrderDao;
import com.liudao51.shop.entity.po.TradeOrder;
import com.liudao51.shop.entity.pojo.TradeOrderMqInfo;
import com.liudao51.shop.util.TradeOrderLogUtilsX;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 订单确认失败MQ消息监听器
 */
@Component
@RocketMQMessageListener(topic = "${app.mq.topic.order}", consumerGroup = "${app.mq.consumer.group.order}", messageModel = MessageModel.BROADCASTING)
public class TradeOrderConfirmFailMQListener implements RocketMQListener<MessageExt> {

    @Autowired
    private ITradeOrderDao tradeOrderDao;

    @Override
    public void onMessage(MessageExt messageExt) {
        try {
            //1.解释消息内容
            String body = new String(messageExt.getBody(), AppCode.CHARSET_UTF8.getValue());
            TradeOrderMqInfo tradeOrderMqInfo = JSON.parseObject(body, TradeOrderMqInfo.class);
            TradeOrderLogUtilsX.debug("orderService接收订单取消消息成功");

            //2.更新订单状态为已取消（因为状态只有一个,重复消费不会造成影响,所以这里不需要核查消息是否消费过）
            if (ObjectUtilsX.isEmpty(tradeOrderMqInfo)) {
                ExceptionUtilsX.cast(ErrorCode.ORDER_ORDER_STATUS_CANCEL_ERROR);
            }

            TradeOrder order = new TradeOrder();
            order.setOrderId(tradeOrderMqInfo.getOrderId());
            TradeOrder order2 = tradeOrderDao.selectById(order);
            if (ObjectUtilsX.isEmpty(order2)) {
                ExceptionUtilsX.cast(ErrorCode.ORDER_ORDER_STATUS_CANCEL_ERROR);
            }

            order2.setOrderStatus(AppCode.ORDER_ORDER_STATUS_CANCEL.getCode());
            Integer affectedRows = tradeOrderDao.updateById(order2);
            if (0 == affectedRows) {
                ExceptionUtilsX.cast(ErrorCode.ORDER_ORDER_STATUS_CANCEL_ERROR);
            }

            TradeOrderLogUtilsX.debug("订单:" + order2.getOrderId() + "取消成功");

        } catch (Exception e) {
            TradeOrderLogUtilsX.error("订单:" + e.getMessage(), e);
            ExceptionUtilsX.cast(ErrorCode.ORDER_ORDER_STATUS_CANCEL_ERROR);
        }
    }
}
