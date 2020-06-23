package com.liudao51.shop.dao.impl;

import com.liudao51.shop.dao.ITradeMqConsumerLogDao;
import com.liudao51.shop.entity.po.TradeMqConsumerLog;
import com.liudao51.shop.mapper.TradeMqConsumerLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 商品库存日志Dao实现类
 */
@Component
public class TradeMqConsumerLogDaoImpl implements ITradeMqConsumerLogDao {

    @Autowired
    TradeMqConsumerLogMapper tradeMqConsumerLogMapper;

    @Override
    public Integer insertById(TradeMqConsumerLog goodsStockLog) {
        return tradeMqConsumerLogMapper.insertById(goodsStockLog);
    }

    @Override
    public Integer updateById(TradeMqConsumerLog goodsStockLog) {
        return tradeMqConsumerLogMapper.updateById(goodsStockLog);
    }

    @Override
    public TradeMqConsumerLog selectById(TradeMqConsumerLog goodsStockLog) {
        return tradeMqConsumerLogMapper.selectById(goodsStockLog);
    }

    @Override
    public TradeMqConsumerLog selectOne(Map<String, Object> args) {
        return tradeMqConsumerLogMapper.selectOne(args);
    }

    @Override
    public List<TradeMqConsumerLog> selectList(Map<String, Object> args) {
        return tradeMqConsumerLogMapper.selectList(args);
    }

}
