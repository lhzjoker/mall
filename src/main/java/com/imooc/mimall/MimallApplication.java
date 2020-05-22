package com.imooc.mimall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.imooc.mimall.dao")
@SpringBootApplication
public class MimallApplication {

    public static void main(String[] args) {
        SpringApplication.run(MimallApplication.class, args);
    }

}
