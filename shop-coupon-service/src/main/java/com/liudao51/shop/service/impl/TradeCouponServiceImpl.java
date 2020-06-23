package com.liudao51.shop.service.impl;

import com.liudao51.shop.common.constant.AppCode;
import com.liudao51.shop.common.constant.ErrorCode;
import com.liudao51.shop.common.exception.ExceptionUtilsX;
import com.liudao51.shop.common.result.ServiceResult;
import com.liudao51.shop.common.util.NumericUtilsX;
import com.liudao51.shop.common.util.ObjectUtilsX;
import com.liudao51.shop.common.util.StringUtilsX;
import com.liudao51.shop.dao.ITradeCouponDao;
import com.liudao51.shop.entity.po.TradeCoupon;
import com.liudao51.shop.facade.ITradeCouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 优惠券服务实现类
 */
@Slf4j
@Component
public class TradeCouponServiceImpl implements ITradeCouponService {

    @Autowired
    private ITradeCouponDao tradeCouponDao;

    /**
     * 查找单个优惠券(通过主键id)
     *
     * @param couponId
     * @return
     */
    @Override
    public ServiceResult<TradeCoupon> selectById(Long couponId) {
        if (!NumericUtilsX.isNumeric(couponId, NumericUtilsX.NUMERIC_TYPE_INTEGER)) {
            ExceptionUtilsX.cast(ErrorCode.REQUEST_PARAM_ERROR);
        }

        try {
            TradeCoupon coupon = new TradeCoupon();
            coupon.setCouponId(couponId);
            TradeCoupon data = tradeCouponDao.selectById(coupon);
            return new ServiceResult<>(ErrorCode.SUCCESS, data);
        } catch (Exception e) {
            return new ServiceResult<>(ErrorCode.COUPON_QUERY_ERROR);
        }
    }

    /**
     * 查找单个优惠券(通过查询条件)
     *
     * @param reqArgs
     * @return
     */
    @Override
    public ServiceResult<TradeCoupon> selectOne(Map reqArgs) {
        Map<String, Object> args = new HashMap<>();
        if (!StringUtilsX.isEmpty(StringUtilsX.getString(reqArgs.get("coupon_id").toString(), ""))) {
            args.put("coupon_id", reqArgs.get("coupon_id"));
        }

        try {
            TradeCoupon data = tradeCouponDao.selectOne(args);
            return new ServiceResult<>(ErrorCode.SUCCESS, data);
        } catch (Exception e) {
            return new ServiceResult<>(ErrorCode.COUPON_QUERY_ERROR);
        }
    }

    /**
     * 查询优惠券是否可用
     *
     * @param coupon
     * @return
     */
    @Override
    public ServiceResult<Object> checkCouponAvailable(TradeCoupon coupon) {
        if (ObjectUtilsX.isEmpty(coupon) ||
                !NumericUtilsX.isNumeric(coupon.getCouponId()) ||
                !NumericUtilsX.isNumeric(coupon.getUserId())) {
            return new ServiceResult<>(ErrorCode.COUPON_INVALID_ERROR);
        }

        TradeCoupon coupon2 = tradeCouponDao.selectById(coupon);

        if (coupon2.getUserId().compareTo(coupon.getUserId()) != 0) { //用户ID不一致
            return new ServiceResult<>(ErrorCode.COUPON_INVALID_ERROR);
        }
        if (coupon2.getCouponPrice().compareTo(coupon.getCouponPrice()) != 0) { //优惠券面额不一致
            return new ServiceResult<>(ErrorCode.COUPON_INVALID_ERROR);
        }
        if (coupon2.getUseStatus().compareTo(AppCode.COUPON_USE_STATUS_NO_USE.getCode()) != 0) { //优惠券已经被使用
            return new ServiceResult<>(ErrorCode.COUPON_IS_USED_ERROR);
        }

        return new ServiceResult<>(ErrorCode.SUCCESS, null);
    }

    /**
     * 使用优惠券
     *
     * @param coupon
     * @return
     */
    @Override
    public ServiceResult<Object> useCoupon(TradeCoupon coupon) {
        //保证时间统一
        Long currentTime = new Date().getTime();

        //检查优惠券是否可用
        ServiceResult<Object> isCheckCouponAvailableResult = this.checkCouponAvailable(coupon);
        if (!ObjectUtilsX.isEmpty(isCheckCouponAvailableResult) && isCheckCouponAvailableResult.getCode().compareTo(ErrorCode.SUCCESS.getCode()) != 0) {
            return new ServiceResult<>(ErrorCode.COUPON_INVALID_ERROR);
        }

        //检查订单是否可以使用此优惠券
        if (!NumericUtilsX.isNumeric(coupon.getOrderId()) && coupon.getOrderId().compareTo(0L) == 0) {
            return new ServiceResult<>(ErrorCode.COUPON_ORDER_ID_ERROR);
        }

        try {
            coupon.setUseStatus(AppCode.COUPON_USE_STATUS_ALREADY_USE.getCode());
            coupon.setUseTime(currentTime);
            coupon.setUpdateTime(currentTime);
            Integer affectedRows = tradeCouponDao.updateById(coupon);
            if (0 == affectedRows) {
                return new ServiceResult<>(ErrorCode.COUPON_REDUCE_ERROR);
            }
            return new ServiceResult<>(ErrorCode.SUCCESS, null);
        } catch (Exception e) {
            return new ServiceResult<>(ErrorCode.COUPON_REDUCE_ERROR);
        }
    }
}
