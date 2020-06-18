package com.liudao51.shop.facade;

import com.liudao51.shop.entity.po.TradeGoods;
import com.liudao51.shop.entity.po.TradeGoodsNumberLog;
import com.liudao51.shop.entity.pojo.ResponseResultInfo;

import java.util.Map;

/**
 * 商品服务接口类
 */
public interface IGoodsService {
    TradeGoods selectById(Long userId);

    TradeGoods selectOne(Map args);

    ResponseResultInfo reduceGoodsStock(TradeGoodsNumberLog goodsNumberLog);
}
