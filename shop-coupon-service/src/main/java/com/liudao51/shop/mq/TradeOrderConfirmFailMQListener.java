package com.liudao51.shop.mq;

import com.alibaba.fastjson.JSON;
import com.liudao51.shop.common.constant.AppCode;
import com.liudao51.shop.common.constant.ErrorCode;
import com.liudao51.shop.common.exception.ExceptionUtilsX;
import com.liudao51.shop.common.util.ObjectUtilsX;
import com.liudao51.shop.dao.ITradeCouponDao;
import com.liudao51.shop.entity.po.TradeCoupon;
import com.liudao51.shop.entity.pojo.TradeOrderMqInfo;
import com.liudao51.shop.util.TradeCouponLogUtilsX;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 订单确认失败MQ消息监听器
 */
@Component
@RocketMQMessageListener(topic = "${app.mq.topic.order}", consumerGroup = "${app.mq.consumer.group.order}", messageModel = MessageModel.BROADCASTING)
public class TradeOrderConfirmFailMQListener implements RocketMQListener<MessageExt> {

    @Autowired
    private ITradeCouponDao tradeCouponDao;

    @Override
    public void onMessage(MessageExt messageExt) {
        //保证时间统一
        Long currentTime = new Date().getTime();

        try {
            //1.解释消息内容
            String body = new String(messageExt.getBody(), AppCode.CHARSET_UTF8.getValue());
            TradeOrderMqInfo tradeOrderMqInfo = JSON.parseObject(body, TradeOrderMqInfo.class);
            TradeCouponLogUtilsX.debug("couponService接收订单取消消息成功");

            //2.回退优惠券使用状态（因为状态只有一个,重复消费不会造成影响,所以这里不需要核查消息是否消费过）
            if (ObjectUtilsX.isEmpty(tradeOrderMqInfo)) {
                ExceptionUtilsX.cast(ErrorCode.ORDER_ORDER_STATUS_CANCEL_ERROR);
            }

            TradeCoupon coupon = new TradeCoupon();
            coupon.setCouponId(tradeOrderMqInfo.getCouponId());
            TradeCoupon coupon2 = tradeCouponDao.selectById(coupon);
            if (ObjectUtilsX.isEmpty(coupon2)) {
                ExceptionUtilsX.cast(ErrorCode.COUPON_USE_STATUS_CANCEL_ERROR);
            }

            coupon2.setOrderId(0L);
            coupon2.setUseStatus(AppCode.COUPON_USE_STATUS_NO_USE.getCode());
            coupon2.setUseTime(0L);
            coupon2.setUpdateTime(currentTime);
            Integer affectedRows = tradeCouponDao.updateById(coupon2);
            if (0 == affectedRows) {
                ExceptionUtilsX.cast(ErrorCode.COUPON_USE_STATUS_CANCEL_ERROR);
            }

            TradeCouponLogUtilsX.debug("订单优惠券:" + coupon2.getCouponId() + "取消成功");

        } catch (Exception e) {
            TradeCouponLogUtilsX.error("订单:" + e.getMessage(), e);
            ExceptionUtilsX.cast(ErrorCode.COUPON_USE_STATUS_CANCEL_ERROR);
        }
    }
}
