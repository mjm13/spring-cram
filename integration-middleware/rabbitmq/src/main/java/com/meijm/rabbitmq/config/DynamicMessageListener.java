package com.meijm.rabbitmq.config;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

@Slf4j
@Scope("prototype")
@Component
public class DynamicMessageListener implements MessageListener {
    private static String FAIL_COUNT = "failCount";
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void onMessage(Message message)  {
        try {
            log.info(new String(message.getBody(),"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        throw new RuntimeException("测试重试");
//        long deliveryTag = message.getMessageProperties().getDeliveryTag();
//        Integer count = message.getMessageProperties().getHeader(FAIL_COUNT);
//        if (count == null) {
//            count = 0;
//        }
//        try {
//            process();
//        } catch (Exception e) {
//                throw new AmqpRejectAndDontRequeueException("重试消息");
//        }


    }

    public void process() {
        throw new RuntimeException("测试异常");
    }

    public String getDeadLette() {
        return "plg-yc-m-test-dead-lette";
    }
}
