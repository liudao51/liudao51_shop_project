package com.liudao51.shop.facade;

import com.liudao51.shop.common.result.ServiceResult;
import com.liudao51.shop.entity.po.TradeOrder;

/**
 * 订单服务接口类
 */
public interface ITradeOrderService {
    /**
     * 下单接口
     *
     * @param order
     * @return
     */
    public ServiceResult<Object> confirmOrder(TradeOrder order);
}
