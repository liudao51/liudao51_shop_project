package com.liudao51.shop.facade;

import com.liudao51.shop.common.result.ServiceResult;
import com.liudao51.shop.entity.po.TradeGoods;
import com.liudao51.shop.entity.po.TradeGoodsStockLog;

import java.util.Map;

/**
 * 商品服务接口类
 */
public interface ITradeGoodsService {
    ServiceResult<TradeGoods> selectById(Long userId);

    ServiceResult<TradeGoods> selectOne(Map args);

    ServiceResult<Object> reduceGoodsStock(TradeGoodsStockLog goodsStockLog);
}
