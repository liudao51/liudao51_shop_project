package com.liudao51.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations = {"classpath:dubbo/shop-order-service-dubbo.xml"})
public class ShopOrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopOrderServiceApplication.class, args);
        System.out.println("shop-order-service 模块启动成功...");
    }
}
