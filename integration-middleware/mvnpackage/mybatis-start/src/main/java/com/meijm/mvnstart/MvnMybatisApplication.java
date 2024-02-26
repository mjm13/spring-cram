package com.meijm.mvnstart;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.meijm.*.mapper")
@SpringBootApplication
public class MvnMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(MvnMybatisApplication.class, args);
    }

}
