package com.meijm.roketmq.listener;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@RocketMQMessageListener(topic = "Basis_Topic", consumerGroup = "FormatMsg")
@Component
public class FormatMsgListener implements RocketMQListener<Map<String,String>> {
    @Override
    public void onMessage(Map<String,String> message) {
        log.info("message:{}", JSONUtil.toJsonStr(message));
    }
}
