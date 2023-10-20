package com.meijm.basis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableAsync
@SpringBootApplication
@EnableScheduling
public class BasisApplication {
    public static void main(String[] args) {
        SpringApplication.run(BasisApplication.class, args);
    }
}
