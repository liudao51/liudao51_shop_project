package com.liudao51.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShopEntityApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopEntityApplication.class, args);
        System.out.println("shop-entity 模块启动成功...");
    }
}
