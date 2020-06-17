package com.liudao51.shop.dao;

import com.liudao51.shop.entity.po.TradeUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户数据访问接口类
 */
public interface ITradeUserDao {
    TradeUser selectById(Map<String, Object> args);

    TradeUser selectOne(Map<String, Object> args);

    List<TradeUser> selectList(Map<String, Object> args);
}
