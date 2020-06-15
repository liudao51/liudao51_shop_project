package com.liudao51.shop.dao;

import com.liudao51.shop.entity.po.TradeUser;

import java.util.Map;

/**
 * 用户数据访问接口类
 */
public interface ITradeUserDao {
    TradeUser selectById(Map args);

    TradeUser selectOne(Map args);
}
