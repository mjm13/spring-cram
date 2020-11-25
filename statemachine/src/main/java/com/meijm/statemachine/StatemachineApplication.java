package com.meijm.statemachine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("org.springframework.statemachine.data.jpa")
@EntityScan("org.springframework.statemachine.data.jpa")
@SpringBootApplication
public class StatemachineApplication {
    public static void main(String[] args) {
        SpringApplication.run(StatemachineApplication.class, args);
    }

}