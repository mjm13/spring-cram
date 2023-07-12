package com.meijm.rediscustom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class RedisCustomApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedisCustomApplication.class);
    }
}
