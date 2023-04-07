package com.meijm.rabbitmq.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.stereotype.Component;

@Component
public class DynamicMessageRecoverer implements MessageRecoverer {
    @Override
    public void recover(Message message, Throwable cause) {
        System.out.println("111");
    }
}
