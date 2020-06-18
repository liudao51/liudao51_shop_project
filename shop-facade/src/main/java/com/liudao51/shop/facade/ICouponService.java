package com.liudao51.shop.facade;

import com.liudao51.shop.entity.po.TradeCoupon;

import java.util.Map;

/**
 * 优惠券服务接口类
 */
public interface ICouponService {
    TradeCoupon selectById(Long userId);

    TradeCoupon selectOne(Map args);
}
