package com.liudao51.shop;

import com.liudao51.shop.entity.po.TradeOrder;
import com.liudao51.shop.common.result.ServiceResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShopOrderWebApplication.class)
public class ShopOrderWebTestApplication {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void confirmOrder() {
        Long couponId = 345988230098857984L;
        Long goodsId = 345959443973935104L;
        Long userId = 345963634385633280L;

        TradeOrder order = new TradeOrder();
        order.setGoodsId(goodsId);
        order.setUserId(userId);
        order.setCouponId(couponId);
        order.setAddress("北京");
        order.setGoodsNumber(1);
        order.setGoodsPrice(new BigDecimal(1000));
        order.setShippingFee(BigDecimal.ZERO);
        order.setOrderAmount(new BigDecimal(980));
        order.setMoneyPaid(new BigDecimal(100));

        ServiceResult result = restTemplate.postForEntity("/order-web/order/confirm", order, ServiceResult.class).getBody();

        System.out.println(result);
    }

    /*
{
    "userId": "345963634385633280",
    "address": "北京",
    "consignee": "小威",
    "goodsId": "345959443973935104",
    "goodsNumber": "1",
    "goodsPrice": "1000",
    "goodsAmount": "1000",
    "couponId": "345988230098857984",
    "couponPaid": "20",
    "shippingFee": "0",
    "orderAmount": "980"
}
    */
}
