package com.liudao51.shop.service.impl;

import com.liudao51.shop.common.constant.ErrorCode;
import com.liudao51.shop.common.exception.ExceptionUtilsX;
import com.liudao51.shop.common.util.NumericUtilsX;
import com.liudao51.shop.common.util.StringUtilsX;
import com.liudao51.shop.dao.ITradeUserDao;
import com.liudao51.shop.entity.po.TradeUser;
import com.liudao51.shop.facade.IUserService;
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
public class UserServiceImpl implements IUserService {

    @Autowired
    private ITradeUserDao tradeUserDao;

    /**
     * 查找单个用户(通过主键id)
     *
     * @param userId
     * @return
     */
    @Override
    public TradeUser selectById(Long userId) {
        if (!NumericUtilsX.isNumeric(userId, NumericUtilsX.NUMERIC_TYPE_INTEGER)) {
            ExceptionUtilsX.cast(ErrorCode.REQUEST_PARAM_ERROR);
        }

        Map args = new HashMap<String, String>();
        args.put("user_id", userId);

        return tradeUserDao.selectById(args);
    }

    /**
     * 查找单个用户(通过查询条件)
     *
     * @param reqArgs
     * @return
     */
    @Override
    public TradeUser selectOne(Map reqArgs) {

        Map args = new HashMap<String, String>();
        if (!StringUtilsX.isEmpty(StringUtilsX.getString(reqArgs.get("user_id").toString(), ""))) {
            args.put("user_id", reqArgs.get("user_id"));
        }

        return tradeUserDao.selectOne(args);
    }
}
