package com.meijm.rabbitmq.manual;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class RabbitMqManualApplication {
    public static void main(String[] args) {
        SpringApplication.run(RabbitMqManualApplication.class, args);
    }
}