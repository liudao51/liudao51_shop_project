package com.liudao51.shop.service.impl;

import com.liudao51.shop.common.constant.ErrorCode;
import com.liudao51.shop.common.exception.ExceptionUtilsX;
import com.liudao51.shop.common.util.ObjectUtilsX;
import com.liudao51.shop.entity.po.TradeGoods;
import com.liudao51.shop.entity.po.TradeOrder;
import com.liudao51.shop.entity.po.TradeUser;
import com.liudao51.shop.entity.pojo.ResponseResultInfo;
import com.liudao51.shop.facade.IGoodsService;
import com.liudao51.shop.facade.IOrderService;
import com.liudao51.shop.facade.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 订单服务实现类
 */
@Slf4j
@Component
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private IUserService userService;

    @Autowired
    private IGoodsService goodsService;

    /**
     * 校验预订单有效性
     *
     * @param order
     * @return
     */
    private Boolean checkPreOrderValid(TradeOrder order) {
        Boolean isValid = true;

        //1.校验订单是否存在(这里的订单还没有入库,所以不用查数据库)
        if (ObjectUtilsX.isEmpty(order)) {
            isValid = false;
            ExceptionUtilsX.cast(ErrorCode.ORDER_NOT_EXIST_ERROR);
        }
        //2.校验下单用户是否存在
        TradeUser user = userService.selectById(order.getUserId());
        if (ObjectUtilsX.isEmpty(user)) {
            isValid = false;
            ExceptionUtilsX.cast(ErrorCode.USER_NOT_EXITS_ERROR);
        }
        //3.校验订单商品是否存在
        TradeGoods goods = goodsService.selectById(order.getGoodsId());
        if (ObjectUtilsX.isEmpty(goods)) {
            isValid = false;
            ExceptionUtilsX.cast(ErrorCode.GOODS_NOT_EXITS_ERROR);
        }

        //4.校验订单商品单价是否合法

        //5.校验订单商品数量是否合法

        return isValid;
    }

    /**
     * 下单接口
     *
     * @param order
     * @return
     */
    @Override
    public ResponseResultInfo confirmOrder(TradeOrder order) {
        //1.校验订单
        Boolean isValid = this.checkPreOrderValid(order);
        if (!isValid) {
            ExceptionUtilsX.cast(ErrorCode.ORDER_INVALID_ERROR);
        }

        //2.生成预订单
        //createPreOrder(order);

        //3.扣减库存

        //4.扣减优惠

        //5.扣减用户余额

        //6.确认订单

        //6-1.确认成功

        //6-2.确认失败

        return null;
    }
}
