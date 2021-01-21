package com.meijm.roketmq.listener;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQReplyListener;
import org.springframework.stereotype.Component;

@Slf4j
@RocketMQMessageListener(topic = "Special_Topic", consumerGroup = "mjm2")
@Component
public class SpecialMsgListener implements RocketMQReplyListener<MessageExt,String> {
    @Override
    public String onMessage(MessageExt messageExt) {
        log.info("messageExt:{}", JSONUtil.toJsonStr(messageExt));
        return "get msg";
    }

}
