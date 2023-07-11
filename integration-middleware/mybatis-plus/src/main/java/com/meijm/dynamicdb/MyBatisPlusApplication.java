package com.meijm.dynamicdb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.meijm.dynamicdb.mapper")
public class MyBatisPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyBatisPlusApplication.class, args);
    }

}
