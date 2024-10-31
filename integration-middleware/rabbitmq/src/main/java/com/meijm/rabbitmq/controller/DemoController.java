package com.meijm.rabbitmq.controller;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "demo", method = RequestMethod.GET)
    public String start() {
        for (int i = 0; i < 10; i++) {
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setPriority(i); // 设置消息优先级
            Map<String,String> data = new HashMap<>();
            data.put("msg","测试消息"+i);
            MessageConverter messageConverter = rabbitTemplate.getMessageConverter();
            rabbitTemplate.send("plg-yc-exchage","plg-yc-m-test" + 0,messageConverter.toMessage(data,messageProperties) );  
        }

//        for (int i = 0; i <1 ; i++) {
//            Map<String,String> data = new HashMap<>();
//            data.put("msg","测试消息"+i);
//            //同步消息发送
//            rabbitTemplate.convertAndSend("plg-yc-exchage","plg-yc-m-test" + 0, data);
//        }
        return "发送完成!";
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
