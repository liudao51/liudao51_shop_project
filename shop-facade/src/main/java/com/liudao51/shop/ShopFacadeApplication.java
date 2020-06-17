package com.liudao51.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShopFacadeApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopFacadeApplication.class, args);
        System.out.println("shop-facade 模块启动成功...");
    }
}
