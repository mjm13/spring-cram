package com.meijm.rabbitmq.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DynamicRabbitConfig implements ApplicationContextAware {

    @Lazy
    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //以业务类型创建exchage
        DirectExchange exchange = new DirectExchange("plg-yc-exchage");
        rabbitAdmin.declareExchange(exchange);

//        Queue deadLetteQueue = new Queue("plg-yc-m-test-dead-lette",true);
//        rabbitAdmin.declareQueue(deadLetteQueue);
//        rabbitAdmin.declareBinding(BindingBuilder.bind(deadLetteQueue).to(exchange).with("plg-yc-m-test-dead-lette"));
        for (int i = 0; i < 1; i++) {
            String queueName = "plg-yc-m-test" + i;
            Map<String, Object> args = new HashMap<>();
            args.put("x-max-priority", 10); // 设置最大优先级为10
            Queue queue = new Queue(queueName, true, false, false,args);
            //以具体业务创建queue
            rabbitAdmin.declareQueue(queue);
            rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(queueName));

            DynamicContainer dynamicContainer = applicationContext.getBean(DynamicContainer.class);
            //设置消费消息数量  配合优先级使用，默认一次拿250条消息，会导致优先级无效
            dynamicContainer.setPrefetchCount(1);
            DynamicMessageListener dynamicMessageListener = applicationContext.getBean(DynamicMessageListener.class);
            dynamicContainer.setMessageListener(dynamicMessageListener);
            dynamicContainer.setQueueNames(queueName);
            dynamicContainer.start();
        }
    }
}
