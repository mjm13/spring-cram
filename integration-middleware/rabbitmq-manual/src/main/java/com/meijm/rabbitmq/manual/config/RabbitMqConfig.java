package com.meijm.rabbitmq.manual.config;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Bean
    public ConnectionFactory createConnectionFactory(){
        ConnectionFactory factory = new ConnectionFactory();
        // 设置连接参数
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        return  factory;
    }

}
