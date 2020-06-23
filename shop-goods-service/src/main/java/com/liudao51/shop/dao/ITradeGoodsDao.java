package com.liudao51.shop.dao;

import com.liudao51.shop.entity.po.TradeGoods;

import java.util.List;
import java.util.Map;

/**
 * 商品Dao接口类
 */
public interface ITradeGoodsDao {

    Integer updateById(TradeGoods goods);

    TradeGoods selectById(TradeGoods goods);

    TradeGoods selectOne(Map<String, Object> args);

    List<TradeGoods> selectList(Map<String, Object> args);
}
