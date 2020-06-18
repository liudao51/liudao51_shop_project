package com.liudao51.shop.dao;

import com.liudao51.shop.entity.po.TradeCoupon;

import java.util.List;
import java.util.Map;

/**
 * 优惠券Dao接口类
 */
public interface ITradeCouponDao {
    TradeCoupon selectById(Map<String, Object> args);

    TradeCoupon selectOne(Map<String, Object> args);

    List<TradeCoupon> selectList(Map<String, Object> args);
}
