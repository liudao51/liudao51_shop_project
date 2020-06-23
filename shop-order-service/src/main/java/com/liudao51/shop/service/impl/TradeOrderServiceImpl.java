package com.liudao51.shop.service.impl;

import com.alibaba.fastjson.JSON;
import com.liudao51.shop.common.constant.AppCode;
import com.liudao51.shop.common.constant.ErrorCode;
import com.liudao51.shop.common.exception.AppException;
import com.liudao51.shop.common.exception.ExceptionUtilsX;
import com.liudao51.shop.common.result.ServiceResult;
import com.liudao51.shop.common.util.NumericUtilsX;
import com.liudao51.shop.common.util.ObjectUtilsX;
import com.liudao51.shop.common.util.PropertiesUtilsX;
import com.liudao51.shop.common.uuid.UidUtilsX;
import com.liudao51.shop.dao.ITradeOrderDao;
import com.liudao51.shop.entity.po.*;
import com.liudao51.shop.entity.pojo.TradeOrderCancleMsgInfo;
import com.liudao51.shop.entity.pojo.TradeOrderMqInfo;
import com.liudao51.shop.facade.ITradeCouponService;
import com.liudao51.shop.facade.ITradeGoodsService;
import com.liudao51.shop.facade.ITradeOrderService;
import com.liudao51.shop.facade.ITradeUserService;
import com.liudao51.shop.util.TradeOrderLogUtilsX;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单服务实现类
 */
@Component
public class TradeOrderServiceImpl implements ITradeOrderService {

    @Autowired
    private ITradeUserService userService;

    @Autowired
    private ITradeGoodsService goodsService;

    @Autowired
    private ITradeCouponService couponService;

    @Autowired
    private ITradeOrderDao orderDao;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private DefaultMQProducer defaultMQProducer;

    /**
     * 校验预订单有效性
     *
     * @param preOrder
     * @return
     */
    private Boolean checkPreOrderValid(TradeOrder preOrder) throws AppException {
        Boolean isValid = false;

        //1.校验订单是否存在(这里的订单还没有入库,所以不用查数据库)
        if (ObjectUtilsX.isEmpty(preOrder)) {
            ExceptionUtilsX.cast(ErrorCode.ORDER_NOT_EXIST_ERROR);
        }

        //2.校验下单用户是否存在
        ServiceResult<TradeUser> userResult = userService.selectById(preOrder.getUserId());
        if (ObjectUtilsX.isEmpty(userResult) || ObjectUtilsX.isEmpty(userResult.getData())) {
            ExceptionUtilsX.cast(ErrorCode.USER_NOT_EXITS_ERROR);
        } else if (userResult.getCode().compareTo(AppCode.SUCCESS.getCode()) != 0) {
            ExceptionUtilsX.cast(userResult.getCode(), userResult.getMsg());
        }

        //3.校验订单商品是否存在
        ServiceResult<TradeGoods> goodsResult = goodsService.selectById(preOrder.getGoodsId());
        if (ObjectUtilsX.isEmpty(goodsResult) || ObjectUtilsX.isEmpty(goodsResult.getData())) {
            ExceptionUtilsX.cast(ErrorCode.GOODS_NOT_EXITS_ERROR);
        } else if (goodsResult.getCode().compareTo(AppCode.SUCCESS.getCode()) != 0) {
            ExceptionUtilsX.cast(goodsResult.getCode(), goodsResult.getMsg());
        }

        TradeGoods goods = goodsResult.getData();

        //4.校验订单商品单价是否合法
        if (preOrder.getGoodsPrice().compareTo(goods.getGoodsPrice()) != 0) {
            ExceptionUtilsX.cast(ErrorCode.REQUEST_SIGN_ERROR);
        }

        //5.校验订单商品数量是否合法
        if (preOrder.getGoodsNumber() >= goods.getGoodsStock()) {
            ExceptionUtilsX.cast(ErrorCode.GOODS_STOCK_NOT_ENOUGH_ERROR);
        }

        //商品总价
        BigDecimal goodsAmount = preOrder.getGoodsPrice().multiply(new BigDecimal(preOrder.getGoodsNumber()));
        //订单运费
        BigDecimal shippingFee = new BigDecimal(0);
        //订单总价
        BigDecimal orderAmount = new BigDecimal(0);

        //6.校验订单运费
        if (goodsAmount.compareTo(new BigDecimal(100)) == 1) { //满100包邮
            shippingFee = BigDecimal.ZERO;
        } else { //默认10元运费
            shippingFee = new BigDecimal(10);
        }
        if (preOrder.getShippingFee().compareTo(shippingFee) != 0) {
            ExceptionUtilsX.cast(ErrorCode.ORDER_SHIPPINGFEE_ERROR);
        }

        //7.校验订单优惠
        BigDecimal discount = new BigDecimal(0);
        if (NumericUtilsX.isNumeric(preOrder.getCouponId()) && preOrder.getCouponId().compareTo(0L) != 0) {
            TradeCoupon coupon = new TradeCoupon();
            coupon.setCouponId(preOrder.getCouponId());
            coupon.setUserId(preOrder.getUserId());
            coupon.setCouponPrice(preOrder.getCouponPaid());

            ServiceResult<Object> checkCouponAvailableResult = couponService.checkCouponAvailable(coupon);
            if (ObjectUtilsX.isEmpty(checkCouponAvailableResult)) {
                ExceptionUtilsX.cast(ErrorCode.COUPON_INVALID_ERROR);
            } else if (checkCouponAvailableResult.getCode().compareTo(AppCode.SUCCESS.getCode()) != 0) {
                ExceptionUtilsX.cast(checkCouponAvailableResult.getCode(), checkCouponAvailableResult.getMsg());
            }

            discount = discount.add(coupon.getCouponPrice()); //折扣优惠累加
        }

        //8.校验订单总金额（订单总金额=商品总金额+运费-优惠）
        orderAmount = goodsAmount.add(shippingFee).subtract(discount);
        if (preOrder.getOrderAmount().compareTo(orderAmount) != 0) {
            ExceptionUtilsX.cast(ErrorCode.ORDER_AMOUNT_ERROR);
        }

        //订单校验通过
        isValid = true;

        return isValid;
    }

    /**
     * 创建预订单
     *
     * @param preOrder
     * @return
     */
    private TradeOrder createPreOrder(TradeOrder preOrder) throws AppException {
        //保证时间统一
        Long currentTime = new Date().getTime();

        //1.设置订单状态为不可见
        preOrder.setOrderStatus(AppCode.ORDER_ORDER_STATUS_NO_CONFIRM.getCode());

        //2.设置订单ID
        Long orderId = UidUtilsX.getUid();
        preOrder.setOrderId(orderId);

        //3.设置下单时间
        preOrder.setCreateTime(currentTime);

        //4.设置其他默认值
        preOrder.setUpdateTime(currentTime);
        preOrder.setPayStatus(AppCode.ORDER_PAY_STATUS_NO_PAY.getCode());
        preOrder.setShippingStatus(AppCode.ORDER_SHIPPING_STATUS_NO_DELIVER.getCode());
        preOrder.setMoneyPaid(BigDecimal.ZERO);
        preOrder.setPayAmount(BigDecimal.ZERO);
        preOrder.setPayTime(0L);
        preOrder.setConfirmTime(0L);
        if (!NumericUtilsX.isNumeric(preOrder.getCouponId()) || preOrder.getCouponId().compareTo(0L) == 0) {
            preOrder.setCouponId(0L);
            preOrder.setCouponPaid(BigDecimal.ZERO);
        }

        //4.订单入库
        Integer affectedRows = orderDao.insertById(preOrder);
        if (0 == affectedRows) {
            ExceptionUtilsX.cast(ErrorCode.ORDER_CREATE_PRE_ORDER_ERROR);
        }

        return preOrder;
    }

    /**
     * 扣减库存
     *
     * @param order
     */
    private Boolean reduceGoodsStock(TradeOrder order) {
        TradeGoodsStockLog goodsStockLog = new TradeGoodsStockLog();
        goodsStockLog.setOrderId(order.getOrderId());
        goodsStockLog.setGoodsId(order.getGoodsId());
        goodsStockLog.setGoodsStock(order.getGoodsNumber());
        ServiceResult<Object> reduceGoodsStockResult = goodsService.reduceGoodsStock(goodsStockLog);

        if (ObjectUtilsX.isEmpty(reduceGoodsStockResult)) {
            ExceptionUtilsX.cast(ErrorCode.GOODS_STOCK_REDUCE_ERROR);
        } else if (reduceGoodsStockResult.getCode().compareTo(AppCode.SUCCESS.getCode()) != 0) {
            ExceptionUtilsX.cast(reduceGoodsStockResult.getCode(), reduceGoodsStockResult.getMsg());
        }
        return true;
    }

    /**
     * 扣减优惠
     *
     * @throws AppException
     */
    private Boolean reduceDiscount(TradeOrder order) throws AppException {
        //扣减优惠券
        if (NumericUtilsX.isNumeric(order.getCouponId()) && order.getCouponId().compareTo(0L) != 0) {
            TradeCoupon coupon = new TradeCoupon();
            coupon.setCouponId(order.getCouponId());
            coupon.setUserId(order.getUserId());
            coupon.setCouponPrice(order.getCouponPaid());
            coupon.setOrderId(order.getOrderId());
            ServiceResult<Object> useCouponResult = couponService.useCoupon(coupon);

            if (ObjectUtilsX.isEmpty(useCouponResult)) {
                ExceptionUtilsX.cast(ErrorCode.COUPON_REDUCE_ERROR);
            } else if (useCouponResult.getCode().compareTo(AppCode.SUCCESS.getCode()) != 0) {
                ExceptionUtilsX.cast(useCouponResult.getCode(), useCouponResult.getMsg());
            }
        }
        //扣减其他优惠(如满减)
        //...
        return true;
    }

    /**
     * 保存订单确认状态
     *
     * @throws AppException
     */
    private Boolean saveOrderConfirmStatus(TradeOrder order) throws AppException {
        //保证时间统一
        Long currentTime = new Date().getTime();

        if (!NumericUtilsX.isNumeric(order.getOrderId()) || order.getOrderId().compareTo(0L) == 0) {
            ExceptionUtilsX.cast(ErrorCode.ORDER_INVALID_ERROR);
        }

        TradeOrder order2 = new TradeOrder();
        order2.setOrderId(order.getOrderId());
        order2.setOrderStatus(AppCode.ORDER_ORDER_STATUS_ALREADY_CONFIRM.getCode());
        order2.setConfirmTime(currentTime);
        order2.setUpdateTime(currentTime);
        Integer affectedRows = orderDao.updateById(order2);
        if (0 == affectedRows) {
            return false;
        }

        return true;
    }

    /**
     * 发送订单确认失败消息到MQ
     *
     * @return
     */
    private Boolean sendOrderConfirmFailMessage(TradeOrderCancleMsgInfo tradeOrderCancleMsgInfo) throws AppException {
        try {
            Message message = new Message(tradeOrderCancleMsgInfo.getTopic(),
                    tradeOrderCancleMsgInfo.getTag(),
                    tradeOrderCancleMsgInfo.getKey(),
                    tradeOrderCancleMsgInfo.getBody().getBytes()
            );
            SendResult sendResult = rocketMQTemplate.getProducer().send(message);
            if (!sendResult.getSendStatus().equals(SendStatus.SEND_OK)) {
                return false;
            }
        } catch (Exception e) {
            ExceptionUtilsX.cast(ErrorCode.MQ_MESSAGE_SEND_ERROR);
        }
        return true;
    }

    /**
     * 确认订单
     *
     * @param preOrder
     * @return
     */
    @Override
    public ServiceResult<Object> confirmOrder(TradeOrder preOrder) {
        TradeOrder order = null;

        try {
            //1.校验订单
            Boolean ischeckPreOrderValid = this.checkPreOrderValid(preOrder);
            if (!ischeckPreOrderValid) {
                ExceptionUtilsX.cast(ErrorCode.ORDER_INVALID_ERROR);
            }
            TradeOrderLogUtilsX.debug("订单校验通过");

            //2.创建预订单
            order = this.createPreOrder(preOrder);
            if (ObjectUtilsX.isEmpty(order)) {
                ExceptionUtilsX.cast(ErrorCode.ORDER_CREATE_PRE_ORDER_ERROR);
            }
            TradeOrderLogUtilsX.debug("订单:" + order.getOrderId() + ", 创建预订单成功");
        } catch (Exception e) {
            TradeOrderLogUtilsX.error("订单:创建预订单失败,错误:" + ExceptionUtilsX.toString(e), e);
            return new ServiceResult<>(ErrorCode.ORDER_CONFIRM_ERROR);
        }

        //===>>> 以下需要进行幂等操作的
        try {
            //3.扣减库存
            Boolean isReduceGoodsStock = this.reduceGoodsStock(order);
            if (!isReduceGoodsStock) {
                ExceptionUtilsX.cast(ErrorCode.GOODS_STOCK_REDUCE_ERROR);
            }
            TradeOrderLogUtilsX.debug("订单:" + order.getOrderId() + ", 扣减库存成功");

            //4.扣减优惠
            Boolean isReduceDiscount = this.reduceDiscount(order);
            if (!isReduceDiscount) {
                ExceptionUtilsX.cast(ErrorCode.ORDER_REDUCE_DISCOUNT_ERROR);
            }
            TradeOrderLogUtilsX.debug("订单:" + order.getOrderId() + ", 扣减优惠成功");

            //调试发送失败消息
            ExceptionUtilsX.cast(ErrorCode.FAIL);

            //5.确认订单
            Boolean isSaveOrderConfirmStatus = this.saveOrderConfirmStatus(order);
            if (!isSaveOrderConfirmStatus) {
                ExceptionUtilsX.cast(ErrorCode.ORDER_CONFIRM_ERROR);
            }
            TradeOrderLogUtilsX.debug("订单:" + order.getOrderId() + ", 确认成功");
        } catch (Exception e) {
            TradeOrderLogUtilsX.error("订单:" + order.getOrderId() + ",错误:" + ExceptionUtilsX.toString(e), e);
            try {
                //发送订单确认失败消息到MQ...
                //订单MQ实体
                TradeOrderMqInfo tradeOrderMqInfo = new TradeOrderMqInfo();
                tradeOrderMqInfo.setOrderId(order.getOrderId());
                tradeOrderMqInfo.setUserId(order.getUserId());
                tradeOrderMqInfo.setUserMoney(order.getMoneyPaid());
                tradeOrderMqInfo.setGoodsId(order.getGoodsId());
                tradeOrderMqInfo.setGoodsNum(order.getGoodsNumber());
                tradeOrderMqInfo.setCouponId(order.getCouponId());
                //订单取消消息实体
                TradeOrderCancleMsgInfo tradeOrderCancleMsgInfo = new TradeOrderCancleMsgInfo();
                tradeOrderCancleMsgInfo.setTopic(PropertiesUtilsX.getDefaultProperties().getProperty("app.mq.topic.order", AppCode.MQ_TOPIC_ORDER.getValue()));
                tradeOrderCancleMsgInfo.setTag(PropertiesUtilsX.getDefaultProperties().getProperty("app.mq.tag.order.cancel", AppCode.MQ_TAG_ORDER_CANCEL.getValue()));
                tradeOrderCancleMsgInfo.setKey(order.getOrderId().toString());
                tradeOrderCancleMsgInfo.setBody(JSON.toJSONString(tradeOrderMqInfo));
                Boolean isSendOrderConfirmFailMessage = sendOrderConfirmFailMessage(tradeOrderCancleMsgInfo);
            } catch (Exception e2) {
                TradeOrderLogUtilsX.error("订单:" + order.getOrderId() + ",错误:" + ExceptionUtilsX.toString(e2), e);
            }

            return new ServiceResult<>(ErrorCode.ORDER_CONFIRM_ERROR);
        }

        return new ServiceResult<>(ErrorCode.SUCCESS);
    }
}
