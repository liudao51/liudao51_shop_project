package com.liudao51.shop.service.impl;

import com.liudao51.shop.common.constant.AppCode;
import com.liudao51.shop.common.constant.ErrorCode;
import com.liudao51.shop.common.exception.ExceptionUtilsX;
import com.liudao51.shop.common.util.ObjectUtilsX;
import com.liudao51.shop.common.util.UidUtilsX;
import com.liudao51.shop.dao.ITradeOrderDao;
import com.liudao51.shop.entity.po.TradeGoods;
import com.liudao51.shop.entity.po.TradeOrder;
import com.liudao51.shop.entity.po.TradeUser;
import com.liudao51.shop.entity.pojo.ResponseResultInfo;
import com.liudao51.shop.facade.IGoodsService;
import com.liudao51.shop.facade.IOrderService;
import com.liudao51.shop.facade.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单服务实现类
 */
@Slf4j
@Component
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private IUserService userService;

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private ITradeOrderDao orderDao;

    /**
     * 校验预订单有效性
     *
     * @param preOrder
     * @return
     */
    private Boolean checkPreOrderValid(TradeOrder preOrder) {
        Boolean isValid = false;

        //1.校验订单是否存在(这里的订单还没有入库,所以不用查数据库)
        if (ObjectUtilsX.isEmpty(preOrder)) {
            ExceptionUtilsX.cast(ErrorCode.ORDER_NOT_EXIST_ERROR);
        }

        //2.校验下单用户是否存在
        TradeUser user = userService.selectById(preOrder.getUserId());
        if (ObjectUtilsX.isEmpty(user)) {
            ExceptionUtilsX.cast(ErrorCode.USER_NOT_EXITS_ERROR);
        }

        //3.校验订单商品是否存在
        TradeGoods goods = goodsService.selectById(preOrder.getGoodsId());
        if (ObjectUtilsX.isEmpty(goods)) {
            ExceptionUtilsX.cast(ErrorCode.GOODS_NOT_EXITS_ERROR);
        }

        //4.校验订单商品单价是否合法
        if (preOrder.getGoodsPrice().compareTo(goods.getGoodsPrice()) != 0) {
            ExceptionUtilsX.cast(ErrorCode.REQUEST_SIGN_ERROR);
        }

        //5.校验订单商品数量是否合法
        if (preOrder.getGoodsNumber() >= goods.getGoodsNumber()) {
            ExceptionUtilsX.cast(ErrorCode.GOODS_STOCK_NOT_ENOUGH);
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
            ExceptionUtilsX.cast(ErrorCode.ORDER_SHIPPINGFEE_INVALID);
        }
        //7.校验订单优惠
        BigDecimal discount = new BigDecimal(0);

        //8.校验订单总金额（订单总金额=商品总金额+运费-优惠）
        orderAmount = goodsAmount.add(shippingFee).subtract(discount);
        if (preOrder.getOrderAmount().compareTo(orderAmount) != 0) {
            ExceptionUtilsX.cast(ErrorCode.ORDER_AMOUNT_INVALID);
        }

        //9.校验订单用户余额

        //10.校验订单剩余需支付金额

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
    private TradeOrder createPreOrder(TradeOrder preOrder) {

        //1.设置订单状态为不可见
        preOrder.setOrderStatus(AppCode.ORDER_NO_CONFIRM.getCode());

        //2.设置订单ID
        Long orderId = UidUtilsX.getUid();
        preOrder.setOrderId(orderId);

        //3.设置下单时间
        preOrder.setAddTime(new Date());

        //4.订单入库
        int affectedRows = orderDao.insert(preOrder);
        if (0 == affectedRows) {
            ExceptionUtilsX.cast(ErrorCode.ORDER_CREATE_PRE_ORDER_ERROR);
        }

        return preOrder;
    }

    /**
     * 确认订单
     *
     * @param preOrder
     * @return
     */
    @Override
    public ResponseResultInfo confirmOrder(TradeOrder preOrder) {
        //1.校验订单
        Boolean isValid = this.checkPreOrderValid(preOrder);
        if (!isValid) {
            ExceptionUtilsX.cast(ErrorCode.ORDER_INVALID_ERROR);
        }

        //2.创建预订单
        TradeOrder order = this.createPreOrder(preOrder);
        if (ObjectUtilsX.isEmpty(order)) {
            ExceptionUtilsX.cast(ErrorCode.ORDER_CREATE_PRE_ORDER_ERROR);
        }

        //3.扣减库存

        //4.扣减优惠

        //5.扣减用户余额

        //6.确认订单

        //6-1.确认成功

        //6-2.确认失败

        return null;
    }
}