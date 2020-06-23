package com.liudao51.shop.facade;

import com.liudao51.shop.common.result.ServiceResult;
import com.liudao51.shop.entity.po.TradeUser;

import java.util.Map;

/**
 * 用户服务接口类
 */
public interface ITradeUserService {
    ServiceResult<TradeUser> selectById(Long userId);

    ServiceResult<TradeUser> selectOne(Map args);
}
