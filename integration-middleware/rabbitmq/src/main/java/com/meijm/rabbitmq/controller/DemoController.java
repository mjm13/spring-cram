package com.meijm.rabbitmq.controller;

import cn.hutool.json.JSONUtil;
import com.meijm.rabbitmq.config.DynamicContainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/")
public class DemoController {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ApplicationContext applicationContext;

    @RequestMapping(value = "demo", method = RequestMethod.GET)
    public String start() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setPriority(i); // 设置消息优先级
            Map<String,String> data = new HashMap<>();
            data.put("msg","测试消息"+i);
            MessageConverter messageConverter = rabbitTemplate.getMessageConverter();
            rabbitTemplate.send("plg-yc-exchage","plg-yc-m-test" + 0,messageConverter.toMessage(data,messageProperties) );  
        }
        Thread.sleep(1000L);
        return "发送完成!";
    }
    @RequestMapping(value = "dynamic", method = RequestMethod.GET)
    public String dynamic(int count) {
        DynamicContainer dynamicContainer = (DynamicContainer) applicationContext.getBean("mjmm");
        dynamicContainer.setConcurrentConsumers(count);
//        dynamicContainer.stop(() -> {
//            dynamicContainer.setConcurrentConsumers(5);
//            dynamicContainer.setConcurrency("5-10");
//            dynamicContainer.start();
//        });
        return "动态接收完成!";
    }

    @RequestMapping(value = "deadletter", method = RequestMethod.GET)
    public String deadletter() {
        Message message = rabbitTemplate.receive("plg-yc-m-test-dead-lette");
        String exchage = message.getMessageProperties().getHeader("old-exchage");
        String routingKey = message.getMessageProperties().getHeader("old-routingKey");
        log.info(exchage);
        log.info(routingKey);
        rabbitTemplate.sendAndReceive(exchage,routingKey,message);
        return "接收完成!";
    }
}
