package com.liudao51.shop.dao;

import com.liudao51.shop.entity.po.TradeGoods;

import java.util.Map;

/**
 * 商品Dao接口类
 */
public interface ITradeGoodsDao {
    TradeGoods selectById(Map args);

    TradeGoods selectOne(Map args);
}
