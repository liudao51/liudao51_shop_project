package com.liudao51.shop.dao.impl;

import com.liudao51.shop.dao.ITradeGoodsDao;
import com.liudao51.shop.entity.po.TradeGoods;
import com.liudao51.shop.mapper.TradeGoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 商品Dao实现类
 * <p>
 * Created by jewel on 2020/6/15.
 */
@Component
public class TradeGoodsDaoImpl implements ITradeGoodsDao {

    @Autowired
    TradeGoodsMapper tradeGoodsMapper;

    @Override
    public TradeGoods selectById(Map args) {
        return tradeGoodsMapper.selectById(args);
    }

    @Override
    public TradeGoods selectOne(Map args) {
        return tradeGoodsMapper.selectOne(args);
    }
}
