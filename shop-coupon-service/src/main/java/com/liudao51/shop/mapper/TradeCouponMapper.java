package com.liudao51.shop.mapper;

import com.liudao51.shop.entity.po.TradeCoupon;
import com.liudao51.shop.entity.po.TradeUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 优惠券 Mapper 接口
 */
@Mapper
@Repository
public interface TradeCouponMapper {

    TradeCoupon selectById(@Param("parameter") Map<String, Object> args);

    TradeCoupon selectOne(@Param("parameter") Map<String, Object> args);

    List<TradeCoupon> selectList(@Param("parameter") Map<String, Object> args);
}