package com.liudao51.shop.dao;

import com.liudao51.shop.entity.po.TradeGoodsStockLog;

import java.util.List;
import java.util.Map;

/**
 * 商品库存日志Dao接口类
 */
public interface ITradeGoodsStockLogDao {

    Integer insertById(TradeGoodsStockLog goodsStockLog);

    TradeGoodsStockLog selectById(TradeGoodsStockLog goodsStockLog);

    TradeGoodsStockLog selectOne(Map<String, Object> args);

    List<TradeGoodsStockLog> selectList(Map<String, Object> args);
}
