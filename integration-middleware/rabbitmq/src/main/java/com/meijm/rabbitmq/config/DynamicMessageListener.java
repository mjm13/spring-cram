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
            log.info("开始消费消息");
            Thread.sleep(10000L);
            log.info("优先级:{}-消息:{}",message.getMessageProperties().getPriority(),new String(message.getBody(),"UTF-8"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            log.info("结束消费消息");
        }
    }

    public void process() {
        throw new RuntimeException("测试异常");
    }

    public String getDeadLette() {
        return "plg-yc-m-test-dead-lette";
    }
}
