package com.meijm.roketmq.springConsumer;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Slf4j
@RocketMQMessageListener(topic = "Basis_Topic", consumerGroup = "OriginalMsg")
@Component
public class OriginalMsgListener implements RocketMQListener<MessageExt>{
    @Override
    public void onMessage(MessageExt messageExt) {
        log.info("messageExt:{}", JSONUtil.toJsonStr(messageExt));
    }

}
