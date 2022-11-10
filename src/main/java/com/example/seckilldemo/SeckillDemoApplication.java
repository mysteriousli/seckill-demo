package com.example.seckilldemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@MapperScan("com.example.seckilldemo")
public class SeckillDemoApplication {
    public static void main(String[] args) {
//        ApplicationContext
        SpringApplication.run(SeckillDemoApplication.class, args);
    }
}
