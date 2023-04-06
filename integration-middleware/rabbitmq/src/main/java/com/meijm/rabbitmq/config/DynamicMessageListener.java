package com.meijm.rabbitmq.config;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

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
            message.getMessageProperties().getHeader("x-death");
            int retryCount = 0 ;
//            channel.basicReject(deliveryTag, false);
//                    message.getMessageProperties().getHeader("x-death") != null ?
//                    (int) message.getMessageProperties().getHeader("x-death")[0].get("count") : 0;
//            if (retryCount >= 3) {
//                channel.basicReject(deliveryTag, false);
                // 超过最大重试次数，存储到数据库中
//                System.out.println("超过最大重试次数");
//            } else {
                // 未达到最大重试次数，重新发送消息
//                channel.basicReject(deliveryTag, false);
                throw new AmqpRejectAndDontRequeueException("重试消息");
//            }

//            String exchage = message.getMessageProperties().getReceivedExchange();
//            String routingKey = message.getMessageProperties().getReceivedRoutingKey();
//            message.getMessageProperties().setHeader(FAIL_COUNT, ++count);
//            channel.basicReject(deliveryTag, false);
//            if (count > 2) {
//                message.getMessageProperties().setHeader("old-exchage", exchage);
//                message.getMessageProperties().setHeader("old-routingKey", routingKey);
//                rabbitTemplate.sendAndReceive(exchage, getDeadLette(), message);
//            } else {
//                rabbitTemplate.sendAndReceive(exchage, routingKey, message);
//            }
        }


    }

    public void process() {
        throw new RuntimeException("测试异常");
    }

    public String getDeadLette() {
        return "plg-yc-m-test-dead-lette";
    }
}
