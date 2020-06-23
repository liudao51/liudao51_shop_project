package com.liudao51.shop.mapper;

import com.liudao51.shop.entity.po.TradeCoupon;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 优惠券 Mapper 接口
 */
@Mapper
@Repository
public interface TradeCouponMapper extends BaseMapper<TradeCoupon> {

}