package com.meijm.rabbitmq.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Configuration
public class DynamicRabbitConfig implements ApplicationContextAware {

    @Lazy
    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        for (int i = 0; i < 2; i++) {
            String queueName= "plg-yc-m-test"+i;
            Queue queue = new Queue(queueName);
            DirectExchange exchange = new DirectExchange("plg-yc-exchage-"+i);
            rabbitAdmin.declareQueue(queue);
            rabbitAdmin.declareExchange(exchange);
            rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(queueName));

            DynamicContainer dynamicContainer = applicationContext.getBean(DynamicContainer.class);
            dynamicContainer.setQueueNames(queueName);
            dynamicContainer.setConcurrentConsumers(1);
            dynamicContainer.start();
        }
    }
}
