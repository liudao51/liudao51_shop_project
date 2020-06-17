package com.liudao51.shop.facade;

import com.liudao51.shop.entity.po.TradeGoods;

import java.util.Map;

/**
 * 商品服务接口类
 */
public interface IGoodsService {
    TradeGoods selectById(Long userId);

    TradeGoods selectOne(Map args);
}
