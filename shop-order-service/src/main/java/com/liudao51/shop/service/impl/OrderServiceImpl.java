package com.liudao51.shop.service.impl;

import com.liudao51.shop.entity.po.TradeOrder;
import com.liudao51.shop.entity.pojo.ResponseResultInfo;
import com.liudao51.shop.facade.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 订单服务实现类
 */
@Slf4j
@Component
public class OrderServiceImpl implements IOrderService {
    /**
     * 下单接口
     */
    @Override
    public ResponseResultInfo confirmOrder(TradeOrder order) {
        return null;
    }
}
