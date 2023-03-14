package com.meijm.service2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MService2Application {
    public static void main(String[] args)
    {
        SpringApplication.run(MService2Application.class, args);
    }
}
