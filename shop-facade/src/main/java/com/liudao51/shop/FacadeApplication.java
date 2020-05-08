package com.liudao51.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FacadeApplication {
    public static void main(String[] args) {
        SpringApplication.run(FacadeApplication.class, args);
        System.out.println("shop-facade模块项目启动成功...");
    }
}
