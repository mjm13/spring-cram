package com.meijm.alibaba.cmb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CmbApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmbApplication.class, args);
    }
}
