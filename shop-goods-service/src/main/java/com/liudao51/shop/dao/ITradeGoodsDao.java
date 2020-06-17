package com.liudao51.shop.dao;

import com.liudao51.shop.entity.po.TradeGoods;

import java.util.List;
import java.util.Map;

/**
 * 商品Dao接口类
 */
public interface ITradeGoodsDao {
    TradeGoods selectById(Map<String, Object> args);

    TradeGoods selectOne(Map<String, Object> args);

    List<TradeGoods> selectList(Map<String, Object> args);
}
