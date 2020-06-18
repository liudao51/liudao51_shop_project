package com.liudao51.shop.service.impl;

import com.liudao51.shop.common.constant.ErrorCode;
import com.liudao51.shop.common.exception.ExceptionUtilsX;
import com.liudao51.shop.common.util.NumericUtilsX;
import com.liudao51.shop.common.util.ObjectUtilsX;
import com.liudao51.shop.common.util.StringUtilsX;
import com.liudao51.shop.dao.ITradeGoodsDao;
import com.liudao51.shop.entity.po.TradeGoods;
import com.liudao51.shop.entity.po.TradeGoodsNumberLog;
import com.liudao51.shop.entity.pojo.ResponseResultInfo;
import com.liudao51.shop.facade.IGoodsService;
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
public class GoodsServiceImpl implements IGoodsService {

    @Autowired
    private ITradeGoodsDao tradeGoodsDao;

    /**
     * 查找单个商品(通过主键id)
     *
     * @param goodsId
     * @return
     */
    @Override
    public TradeGoods selectById(Long goodsId) {
        if (!NumericUtilsX.isNumeric(goodsId, NumericUtilsX.NUMERIC_TYPE_INTEGER)) {
            ExceptionUtilsX.cast(ErrorCode.REQUEST_PARAM_ERROR);
        }

        Map args = new HashMap<String, String>();
        args.put("goods_id", goodsId);

        return tradeGoodsDao.selectById(args);
    }

    /**
     * 查找单个商品(通过查询条件)
     *
     * @param reqArgs
     * @return
     */
    @Override
    public TradeGoods selectOne(Map reqArgs) {
        Map args = new HashMap<String, String>();
        if (!StringUtilsX.isEmpty(StringUtilsX.getString(reqArgs.get("goods_id").toString(), ""))) {
            args.put("goods_id", reqArgs.get("goods_id"));
        }

        return tradeGoodsDao.selectOne(args);
    }

    /**
     * 扣减商品库存,并记录商品库存操作日志
     *
     * @param goodsNumberLog
     * @return
     */
    @Override
    public ResponseResultInfo reduceGoodsStock(TradeGoodsNumberLog goodsNumberLog) {
        if (ObjectUtilsX.isEmpty(goodsNumberLog) ||
                NumericUtilsX.isNumeric(goodsNumberLog.getOrderId()) ||
                NumericUtilsX.isNumeric(goodsNumberLog.getGoodsId()) ||
                NumericUtilsX.isNumeric(goodsNumberLog.getGoodsNumber()) ||
                goodsNumberLog.getGoodsNumber() <= 0) {
            ExceptionUtilsX.cast(ErrorCode.REQUEST_PARAM_ERROR);
        }

        Map args = new HashMap<String, Object>();
        args.put("goods_id", goodsNumberLog.getGoodsId());
        TradeGoods goods = tradeGoodsDao.selectById(args);
        if (ObjectUtilsX.isEmpty(goods)) {
            ExceptionUtilsX.cast(ErrorCode.GOODS_NUMBER_LOG_GOODS_NOT_EXITS_ERROR);
        }
        if (goods.getGoodsNumber() < goodsNumberLog.getGoodsNumber()) {
            ExceptionUtilsX.cast(ErrorCode.GOODS_NUMBER_NOT_ENOUGH_ERROR);
        }

        //扣减商品库存
        goods.setGoodsNumber(goods.getGoodsNumber() - goodsNumberLog.getGoodsNumber());
        int affectedRows = tradeGoodsDao.update(goods);
        if (affectedRows == 0) {
            ExceptionUtilsX.cast(ErrorCode.GOODS_NUMBER_REDUCE_FAIL_ERROR);
        }

        //记录商品库存操作日志
        goodsNumberLog.setGoodsNumber(-(goodsNumberLog.getGoodsNumber()));
        goodsNumberLog.setLogTime(new Date());
        int affectedRows2 = tradeGoodsNumberLogDao.insert(goodsNumberLog);
        if (affectedRows2 == 0) {
            ExceptionUtilsX.cast(ErrorCode.GOODS_NUMBER_REDUCE_FAIL_ERROR);
        }

        return new ResponseResultInfo<>(ErrorCode.SUCCESS);
    }
}
