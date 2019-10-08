package com.taoyuanx.mvcseed;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author dushitaoyuan
 * @date 2019/9/2622:46
 * @desc: 启动类
 */
@SpringBootApplication
@MapperScan(basePackages = "com.taoyuanx.mvcseed.dao")
public class SeedBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(SeedBootApplication.class, args);
    }
}
