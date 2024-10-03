package com.sakura.spzx.manger;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: sakura
 * @date: 2024/10/2 15:50
 * @description:
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.sakura.spzx")
@MapperScan("com.sakura.spzx.manger.mapper")
public class SpzxMangerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpzxMangerApplication.class, args);
    }
}
