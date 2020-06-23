package com.liudao51.shop.dao.impl;

import com.liudao51.shop.dao.ITradeGoodsDao;
import com.liudao51.shop.entity.po.TradeGoods;
import com.liudao51.shop.mapper.TradeGoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 商品Dao实现类
 */
@Component
public class TradeGoodsDaoImpl implements ITradeGoodsDao {

    @Autowired
    TradeGoodsMapper tradeGoodsMapper;

    @Override
    public Integer updateById(TradeGoods goods) {
        return tradeGoodsMapper.updateById(goods);
    }

    @Override
    public TradeGoods selectById(TradeGoods goods) {
        return tradeGoodsMapper.selectById(goods);
    }

    @Override
    public TradeGoods selectOne(Map<String, Object> args) {
        return tradeGoodsMapper.selectOne(args);
    }

    @Override
    public List<TradeGoods> selectList(Map<String, Object> args) {
        return tradeGoodsMapper.selectList(args);
    }

}
