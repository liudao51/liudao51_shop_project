package com.liudao51.shop.dao.impl;

import com.liudao51.shop.dao.ITradeGoodsStockLogDao;
import com.liudao51.shop.entity.po.TradeGoodsStockLog;
import com.liudao51.shop.mapper.TradeGoodsStockLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 商品库存日志Dao实现类
 */
@Component
public class TradeGoodsStockLogDaoImpl implements ITradeGoodsStockLogDao {

    @Autowired
    TradeGoodsStockLogMapper tradeGoodsStockLogMapper;

    @Override
    public Integer insertById(TradeGoodsStockLog goodsStockLog) {
        return tradeGoodsStockLogMapper.insertById(goodsStockLog);
    }

    @Override
    public TradeGoodsStockLog selectById(TradeGoodsStockLog goodsStockLog) {
        return tradeGoodsStockLogMapper.selectById(goodsStockLog);
    }

    @Override
    public TradeGoodsStockLog selectOne(Map<String, Object> args) {
        return tradeGoodsStockLogMapper.selectOne(args);
    }

    @Override
    public List<TradeGoodsStockLog> selectList(Map<String, Object> args) {
        return tradeGoodsStockLogMapper.selectList(args);
    }

}
