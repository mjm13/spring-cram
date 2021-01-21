package com.meijm.roketmq;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.shell.jline.PromptProvider;

@SpringBootApplication
public class RoketmqApplication {
    public static void main(String[] args) {
        SpringApplication.run(RoketmqApplication.class, args);
    }

    @Bean
    public PromptProvider myPromptProvider() {
        return () -> new AttributedString("spring-shell:>", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
    }
}