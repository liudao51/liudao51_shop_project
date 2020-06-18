package com.liudao51.shop.dao.impl;

import com.liudao51.shop.dao.ITradeCouponDao;
import com.liudao51.shop.entity.po.TradeCoupon;
import com.liudao51.shop.mapper.TradeCouponMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 优惠券Dao实现类
 */
@Component
public class TradeCouponDaoImpl implements ITradeCouponDao {

    @Autowired
    TradeCouponMapper tradeCouponMapper;

    @Override
    public TradeCoupon selectById(Map<String, Object> args) {
        return tradeCouponMapper.selectById(args);
    }

    @Override
    public TradeCoupon selectOne(Map<String, Object> args) {
        return tradeCouponMapper.selectOne(args);
    }

    @Override
    public List<TradeCoupon> selectList(Map<String, Object> args) {
        return tradeCouponMapper.selectList(args);
    }
}
