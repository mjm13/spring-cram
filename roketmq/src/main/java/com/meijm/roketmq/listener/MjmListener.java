package com.meijm.roketmq.listener;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@RocketMQMessageListener(topic = "mjmTopic", consumerGroup = "mjm")
@Service
public class MjmListener implements RocketMQListener<Map<String,String>> {
    @Override
    public void onMessage(Map<String,String> message) {
        System.out.println("message:"+message);
    }
}
