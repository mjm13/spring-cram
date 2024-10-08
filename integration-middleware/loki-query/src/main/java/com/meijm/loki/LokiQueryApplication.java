package com.meijm.loki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = { "com.meijm.loki.feign" })
@SpringBootApplication
public class LokiQueryApplication {
    public static void main(String[] args) {
        SpringApplication.run(LokiQueryApplication.class);
    }
}
