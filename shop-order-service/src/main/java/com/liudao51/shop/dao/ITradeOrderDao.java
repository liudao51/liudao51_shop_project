package com.liudao51.shop.dao;

import com.liudao51.shop.entity.po.TradeOrder;

import java.util.List;
import java.util.Map;

/**
 * 订单Dao接口类
 */
public interface ITradeOrderDao {
    Integer insertById(TradeOrder order);

    Integer updateById(TradeOrder order);

    TradeOrder selectById(TradeOrder order);

    TradeOrder selectOne(Map<String, Object> args);

    List<TradeOrder> selectList(Map<String, Object> args);

}
