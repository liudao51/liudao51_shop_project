package com.liudao51.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations = {"classpath:dubbo/shop-coupon-dubbo.xml"})
public class ShopCouponServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopCouponServiceApplication.class, args);
        System.out.println("shop-user-service 模块启动成功...");
    }
}
