package com.liudao51.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShopCommonApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopCommonApplication.class, args);
        System.out.println("shop-common 模块启动成功...");
    }
}
