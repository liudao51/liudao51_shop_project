package com.liudao51.shop.facade;

import com.liudao51.shop.entity.po.TradeUser;

import java.util.Map;

/**
 * 用户服务接口类
 */
public interface IUserService {
    TradeUser selectById(Long userId);

    TradeUser selectOne(Map args);
}
