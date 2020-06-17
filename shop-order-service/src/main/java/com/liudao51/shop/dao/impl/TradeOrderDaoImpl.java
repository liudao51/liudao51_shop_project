package com.liudao51.shop.dao.impl;

import com.liudao51.shop.dao.ITradeOrderDao;
import com.liudao51.shop.entity.po.TradeOrder;
import com.liudao51.shop.mapper.TradeOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 订单Dao实现类
 */
@Component
public class TradeOrderDaoImpl implements ITradeOrderDao {

    @Autowired
    TradeOrderMapper tradeOrderMapper;

    @Override
    public TradeOrder selectById(Map<String, Object> args) {
        return tradeOrderMapper.selectById(args);
    }

    @Override
    public TradeOrder selectOne(Map<String, Object> args) {
        return tradeOrderMapper.selectOne(args);
    }

    @Override
    public List<TradeOrder> selectList(Map<String, Object> args) {
        return tradeOrderMapper.selectList(args);
    }

    @Override
    public int insert(TradeOrder order) {
        return tradeOrderMapper.insert(order);
    }

}
