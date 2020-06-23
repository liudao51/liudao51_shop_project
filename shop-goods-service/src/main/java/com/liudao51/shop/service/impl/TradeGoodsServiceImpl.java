package com.liudao51.shop.service.impl;

import com.liudao51.shop.common.constant.ErrorCode;
import com.liudao51.shop.common.exception.AppException;
import com.liudao51.shop.common.result.ServiceResult;
import com.liudao51.shop.common.util.NumericUtilsX;
import com.liudao51.shop.common.util.ObjectUtilsX;
import com.liudao51.shop.common.util.StringUtilsX;
import com.liudao51.shop.common.uuid.UidUtilsX;
import com.liudao51.shop.dao.ITradeGoodsDao;
import com.liudao51.shop.dao.ITradeGoodsStockLogDao;
import com.liudao51.shop.entity.po.TradeGoods;
import com.liudao51.shop.entity.po.TradeGoodsStockLog;
import com.liudao51.shop.facade.ITradeGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 商品服务实现类
 */
@Slf4j
@Component
public class TradeGoodsServiceImpl implements ITradeGoodsService {

    @Autowired
    private ITradeGoodsDao tradeGoodsDao;

    @Autowired
    private ITradeGoodsStockLogDao tradeGoodsStockLogDao;

    /**
     * 查找单个商品(通过主键id)
     *
     * @param goodsId
     * @return
     */
    @Override
    public ServiceResult<TradeGoods> selectById(Long goodsId) {
        if (!NumericUtilsX.isNumeric(goodsId, NumericUtilsX.NUMERIC_TYPE_INTEGER)) {
            return new ServiceResult<>(ErrorCode.REQUEST_PARAM_ERROR);
        }
        try {
            TradeGoods goods = new TradeGoods();
            goods.setGoodsId(goodsId);
            TradeGoods data = tradeGoodsDao.selectById(goods);
            return new ServiceResult<>(ErrorCode.SUCCESS, data);
        } catch (Exception e) {
            return new ServiceResult<>(ErrorCode.GOODS_QUERY_ERROR);
        }
    }

    /**
     * 查找单个商品(通过查询条件)
     *
     * @param reqArgs
     * @return
     */
    @Override
    public ServiceResult<TradeGoods> selectOne(Map reqArgs) {
        Map<String, Object> args = new HashMap<>();
        if (!StringUtilsX.isEmpty(StringUtilsX.getString(reqArgs.get("goods_id").toString(), ""))) {
            args.put("goods_id", reqArgs.get("goods_id"));
        }
        try {
            TradeGoods data = tradeGoodsDao.selectOne(args);
            return new ServiceResult<>(ErrorCode.SUCCESS, data);
        } catch (Exception e) {
            return new ServiceResult<>(ErrorCode.GOODS_QUERY_ERROR);
        }
    }

    /**
     * 扣减商品库存,并记录商品库存操作日志
     *
     * @param goodsStockLog
     * @return
     */
    @Override
    public ServiceResult<Object> reduceGoodsStock(TradeGoodsStockLog goodsStockLog) throws AppException {

        //保证时间统一
        Long currentTime = new Date().getTime();

        if (ObjectUtilsX.isEmpty(goodsStockLog) ||
                !NumericUtilsX.isNumeric(goodsStockLog.getOrderId()) ||
                !NumericUtilsX.isNumeric(goodsStockLog.getGoodsId()) ||
                !NumericUtilsX.isNumeric(goodsStockLog.getGoodsStock()) ||
                goodsStockLog.getGoodsStock() <= 0) {
            return new ServiceResult<>(ErrorCode.REQUEST_PARAM_ERROR);
        }

        //扣减商品库存
        TradeGoods goods = new TradeGoods();
        goods.setGoodsId(goodsStockLog.getGoodsId());
        goods = tradeGoodsDao.selectById(goods);

        if (ObjectUtilsX.isEmpty(goods)) {
            return new ServiceResult<>(ErrorCode.GOODS_STOCK_LOG_GOODS_NOT_EXITS_ERROR);
        }
        if (goods.getGoodsStock() < goodsStockLog.getGoodsStock()) {
            return new ServiceResult<>(ErrorCode.GOODS_STOCK_NOT_ENOUGH_ERROR);
        }

        goods.setGoodsStock(goods.getGoodsStock() - goodsStockLog.getGoodsStock());
        goods.setUpdateTime(currentTime);
        Integer affectedRows = tradeGoodsDao.updateById(goods);
        if (affectedRows == 0) {
            return new ServiceResult<>(ErrorCode.GOODS_STOCK_REDUCE_ERROR);
        }

        //记录商品库存操作日志
        goodsStockLog.setGoodsStock(-(goodsStockLog.getGoodsStock()));
        goodsStockLog.setCreateTime(currentTime);
        goodsStockLog.setUpdateTime(currentTime);
        goodsStockLog.setGoodsStockId(UidUtilsX.getUid());
        Integer affectedRows2 = tradeGoodsStockLogDao.insertById(goodsStockLog);
        if (affectedRows2 == 0) {
            return new ServiceResult<>(ErrorCode.GOODS_STOCK_REDUCE_ERROR);
        }

        return new ServiceResult<>(ErrorCode.SUCCESS);
    }
}
