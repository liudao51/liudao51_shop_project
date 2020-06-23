package com.liudao51.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations = {"classpath:dubbo/shop-order-web-dubbo.xml"})
public class ShopOrderWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopOrderWebApplication.class, args);
        System.out.println("shop-order-web 模块启动成功...");
    }
}
