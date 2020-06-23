package com.liudao51.shop.dao;

import com.liudao51.shop.entity.po.TradeMqConsumerLog;

import java.util.List;
import java.util.Map;

/**
 * 消息消费记录Dao接口类
 */
public interface ITradeMqConsumerLogDao {

    Integer insertById(TradeMqConsumerLog goodsStockLog);

    Integer updateById(TradeMqConsumerLog goodsStockLog);

    TradeMqConsumerLog selectById(TradeMqConsumerLog goodsStockLog);

    TradeMqConsumerLog selectOne(Map<String, Object> args);

    List<TradeMqConsumerLog> selectList(Map<String, Object> args);
}
