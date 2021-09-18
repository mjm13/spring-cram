package com.meijm.roketmq.springConsumer;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQReplyListener;
import org.springframework.stereotype.Component;

@Slf4j
@RocketMQMessageListener(topic = "Reply_Topic", consumerGroup = "ReplyMsg")
@Component
public class ReplyMsgListener implements RocketMQReplyListener<MessageExt,String> {
    @Override
    public String onMessage(MessageExt messageExt) {
        log.info("messageExt:{}", JSONUtil.toJsonStr(messageExt));
        return "get msg";
    }

}
