package com.liudao51.shop.facade;

import com.liudao51.shop.common.result.ServiceResult;
import com.liudao51.shop.entity.po.TradeCoupon;

import java.util.Map;

/**
 * 优惠券服务接口类
 */
public interface ITradeCouponService {
    ServiceResult<TradeCoupon> selectById(Long userId);

    ServiceResult<TradeCoupon> selectOne(Map args);

    ServiceResult<Object> checkCouponAvailable(TradeCoupon coupon);

    ServiceResult<Object> useCoupon(TradeCoupon coupon);
}
