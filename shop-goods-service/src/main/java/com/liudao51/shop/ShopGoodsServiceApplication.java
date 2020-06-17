package com.liudao51.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations = {"classpath:dubbo/shop-goods-dubbo.xml"})
public class ShopGoodsServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopGoodsServiceApplication.class, args);
        System.out.println("shop-goods-service 模块启动成功...");
    }
}
