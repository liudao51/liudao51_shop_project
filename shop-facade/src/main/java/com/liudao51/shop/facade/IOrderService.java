package com.liudao51.shop.facade;

import com.liudao51.shop.entity.po.TradeOrder;
import com.liudao51.shop.entity.pojo.ResponseResultInfo;

/**
 * 订单服务接口类
 */
public interface IOrderService {
    /**
     * 下单接口
     *
     * @param order
     * @return
     */
    public ResponseResultInfo confirmOrder(TradeOrder order);
}
