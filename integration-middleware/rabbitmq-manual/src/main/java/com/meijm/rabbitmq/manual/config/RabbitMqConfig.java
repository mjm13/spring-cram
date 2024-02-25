package com.meijm.rabbitmq.manual.config;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@Configuration
public class RabbitMqConfig {
    @Bean
    public Channel createChannel(){
        try {
            ConnectionFactory factory = new ConnectionFactory();
            // 设置连接参数
            factory.setHost("192.168.110.200");
            factory.setPort(5672);
            factory.setUsername("admin");
            factory.setPassword("prolog0212");
            Channel channel =  factory.newConnection().createChannel();
            channel.queueDeclare("temp-queue",false,false,false,null);
            channel.exchangeDeclare("temp-exchange", BuiltinExchangeType.DIRECT);
            channel.queueBind("temp-queue","temp-exchange","temp-queue");
            return channel;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

}
