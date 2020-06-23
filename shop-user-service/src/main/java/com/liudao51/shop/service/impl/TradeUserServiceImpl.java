package com.liudao51.shop.service.impl;

import com.liudao51.shop.common.constant.ErrorCode;
import com.liudao51.shop.common.exception.ExceptionUtilsX;
import com.liudao51.shop.common.result.ServiceResult;
import com.liudao51.shop.common.util.NumericUtilsX;
import com.liudao51.shop.common.util.StringUtilsX;
import com.liudao51.shop.dao.ITradeUserDao;
import com.liudao51.shop.entity.po.TradeUser;
import com.liudao51.shop.facade.ITradeUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户服务实现类
 */
@Slf4j
@Component
public class TradeUserServiceImpl implements ITradeUserService {

    @Autowired
    private ITradeUserDao tradeUserDao;

    /**
     * 查找单个用户(通过主键id)
     *
     * @param userId
     * @return
     */
    @Override
    public ServiceResult<TradeUser> selectById(Long userId) {
        if (!NumericUtilsX.isNumeric(userId, NumericUtilsX.NUMERIC_TYPE_INTEGER)) {
            ExceptionUtilsX.cast(ErrorCode.REQUEST_PARAM_ERROR);
        }

        try {
            TradeUser user = new TradeUser();
            user.setUserId(userId);
            TradeUser data = tradeUserDao.selectById(user);
            return new ServiceResult<>(ErrorCode.SUCCESS, data);
        } catch (Exception e) {
            return new ServiceResult<>(ErrorCode.USER_QUERY_ERROR);
        }
    }

    /**
     * 查找单个用户(通过查询条件)
     *
     * @param reqArgs
     * @return
     */
    @Override
    public ServiceResult<TradeUser> selectOne(Map reqArgs) {

        Map<String, Object> args = new HashMap<>();
        if (!StringUtilsX.isEmpty(StringUtilsX.getString(reqArgs.get("user_id").toString(), ""))) {
            args.put("user_id", reqArgs.get("user_id"));
        }

        try {
            TradeUser data = tradeUserDao.selectOne(args);
            return new ServiceResult<>(ErrorCode.SUCCESS, data);
        } catch (Exception e) {
            return new ServiceResult<>(ErrorCode.USER_QUERY_ERROR);
        }
    }
}
