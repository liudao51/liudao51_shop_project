package com.liudao51.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations = {"classpath:dubbo/shop-user-dubbo.xml"})
public class ShopUserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopUserServiceApplication.class, args);
        System.out.println("shop-user-service 模块启动成功...");
    }
}
