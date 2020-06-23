package com.liudao51.shop.controller;

import com.liudao51.shop.common.constant.AppCode;
import com.liudao51.shop.common.constant.ErrorCode;
import com.liudao51.shop.common.exception.ExceptionUtilsX;
import com.liudao51.shop.common.result.ResponseResult;
import com.liudao51.shop.common.result.ServiceResult;
import com.liudao51.shop.common.util.ObjectUtilsX;
import com.liudao51.shop.entity.po.TradeOrder;
import com.liudao51.shop.facade.ITradeOrderService;
import com.liudao51.shop.protocol.ConfirmOrderReq;
import com.liudao51.shop.util.TradeOrderWebLogUtilsX;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/order")
public class TradeOrderController {

    @Autowired
    private ITradeOrderService orderService;

    @RequestMapping("/confirm")
    public ResponseResult<Object> confirmOrder(@RequestBody ConfirmOrderReq confirmOrderReq) {

        TradeOrder preOrder = new TradeOrder();
        preOrder.setUserId(confirmOrderReq.getUserId());
        preOrder.setAddress(confirmOrderReq.getAddress());
        preOrder.setConsignee(confirmOrderReq.getConsignee());
        preOrder.setGoodsId(confirmOrderReq.getGoodsId());
        preOrder.setGoodsNumber(confirmOrderReq.getGoodsNumber());
        preOrder.setGoodsPrice(confirmOrderReq.getGoodsPrice());
        preOrder.setGoodsAmount(confirmOrderReq.getGoodsAmount());
        preOrder.setCouponId(confirmOrderReq.getCouponId());
        preOrder.setCouponPaid(confirmOrderReq.getCouponPaid());
        preOrder.setShippingFee(confirmOrderReq.getShippingFee());
        preOrder.setOrderAmount(confirmOrderReq.getOrderAmount());

        ServiceResult<Object> confirmOrderResultInfo = orderService.confirmOrder(preOrder);
        if (ObjectUtilsX.isEmpty(confirmOrderResultInfo) || confirmOrderResultInfo.getCode().compareTo(AppCode.SUCCESS.getCode()) != 0) {
            TradeOrderWebLogUtilsX.error("订单:" + preOrder.getOrderId() + ",错误:" + ExceptionUtilsX.toString(confirmOrderResultInfo));
            return new ResponseResult<>(ErrorCode.ORDER_CONFIRM_ERROR);
        }

        return new ResponseResult<>(ErrorCode.SUCCESS, confirmOrderResultInfo.getData());
    }
}
