package com.meijm.rabbitmq.config;

import cn.hutool.json.JSONUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
@Scope("prototype")
@Component
public class DynamicMessageListener implements ChannelAwareMessageListener {
    private static String FAIL_COUNT = "failCount";
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        Integer count = message.getMessageProperties().getHeader(FAIL_COUNT);
        if (count == null) {
            count = 0;
        }
        try {
            process();
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            String exchage = message.getMessageProperties().getReceivedExchange();
            String routingKey = message.getMessageProperties().getReceivedRoutingKey();
            message.getMessageProperties().setHeader(FAIL_COUNT, ++count);
            channel.basicReject(deliveryTag, false);
            if (count > 2) {
                message.getMessageProperties().setHeader("old-exchage", exchage);
                message.getMessageProperties().setHeader("old-routingKey", routingKey);
                rabbitTemplate.sendAndReceive(exchage, getDeadLette(), message);
            } else {
                rabbitTemplate.sendAndReceive(exchage, routingKey, message);
            }
        }


    }

    public void process() {

    }

    public String getDeadLette() {
        return "plg-yc-m-test-dead-lette";
    }
}
