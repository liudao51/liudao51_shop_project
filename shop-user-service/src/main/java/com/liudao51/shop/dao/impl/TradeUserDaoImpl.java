package com.liudao51.shop.dao.impl;

import com.liudao51.shop.dao.ITradeUserDao;
import com.liudao51.shop.entity.po.TradeUser;
import com.liudao51.shop.mapper.TradeUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 用户Dao实现类
 */
@Component
public class TradeUserDaoImpl implements ITradeUserDao {

    @Autowired
    TradeUserMapper tradeUserMapper;

    @Override
    public TradeUser selectById(TradeUser user) {
        return tradeUserMapper.selectById(user);
    }

    @Override
    public TradeUser selectOne(Map args) {
        return tradeUserMapper.selectOne(args);
    }

    @Override
    public List<TradeUser> selectList(Map<String, Object> args) {
        return tradeUserMapper.selectList(args);
    }
}
