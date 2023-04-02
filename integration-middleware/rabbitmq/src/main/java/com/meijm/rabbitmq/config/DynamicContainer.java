package com.meijm.rabbitmq.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Scope("prototype")
@Component
public class DynamicContainer extends SimpleMessageListenerContainer {
    @Autowired
    private ConnectionFactory connectionFactory;

    @PostConstruct
    public void init(){
        this.setConnectionFactory(connectionFactory);
        this.setAcknowledgeMode(AcknowledgeMode.MANUAL);
    }
}
