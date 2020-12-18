package com.meijm.service1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = { "com.meijm.service1.feign" })
public class MService1Application {
    public static void main(String[] args)
    {
        SpringApplication.run(MService1Application.class, args);
    }
}
