package com.liudao51.shop.dao;

import com.liudao51.shop.entity.po.TradeUser;

import java.util.List;
import java.util.Map;

/**
 * 用户Dao接口类
 */
public interface ITradeUserDao {
    TradeUser selectById(TradeUser user);

    TradeUser selectOne(Map<String, Object> args);

    List<TradeUser> selectList(Map<String, Object> args);
}
