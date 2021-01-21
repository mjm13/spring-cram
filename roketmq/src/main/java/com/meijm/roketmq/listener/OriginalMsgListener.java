package com.meijm.roketmq.listener;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

@Slf4j
@RocketMQMessageListener(topic = "Basis_Topic", consumerGroup = "OriginalMsg")
@Component
public class OriginalMsgListener implements RocketMQListener<MessageExt>{
    @Override
    public void onMessage(MessageExt messageExt) {
        log.info("messageExt:{}", JSONUtil.toJsonStr(messageExt));
    }

}
