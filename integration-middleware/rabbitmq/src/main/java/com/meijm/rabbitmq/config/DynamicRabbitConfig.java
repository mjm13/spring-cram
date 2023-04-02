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
        //以业务类型创建exchage
        DirectExchange exchange = new DirectExchange("plg-yc-exchage");
        Queue deadLetteQueue = new Queue("plg-yc-m-test-dead-lette",true);
        rabbitAdmin.declareExchange(exchange);
        rabbitAdmin.declareQueue(deadLetteQueue);
        rabbitAdmin.declareBinding(BindingBuilder.bind(deadLetteQueue).to(exchange).with("plg-yc-m-test-dead-lette"));
        for (int i = 0; i < 2; i++) {
            String queueName= "plg-yc-m-test"+i;
            Queue queue = new Queue(queueName,true);
            //以具体业务创建queue
            rabbitAdmin.declareQueue(queue);
            rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(queueName));

            DynamicContainer dynamicContainer = applicationContext.getBean(DynamicContainer.class);
            DynamicMessageListener dynamicMessageListener = applicationContext.getBean(DynamicMessageListener.class);
            dynamicContainer.setMessageListener(dynamicMessageListener);
            dynamicContainer.setQueueNames(queueName);
            dynamicContainer.start();
        }
    }
}
