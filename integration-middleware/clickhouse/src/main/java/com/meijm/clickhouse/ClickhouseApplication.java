package com.meijm.clickhouse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.meijm.clickhouse.dao")
@SpringBootApplication
public class ClickhouseApplication {
	public static void main(String[] args) {
		SpringApplication.run(ClickhouseApplication.class, args);
	}

}
