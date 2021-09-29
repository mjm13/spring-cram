package com.meijm.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class KafkaMqApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaMqApplication.class, args);
    }
}