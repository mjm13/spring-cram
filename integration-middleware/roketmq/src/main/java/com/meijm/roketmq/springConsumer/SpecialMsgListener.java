package com.meijm.roketmq.springConsumer;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Component;

@Slf4j
@RocketMQMessageListener(topic = "Special_Topic", consumerGroup = "SpecialMsg")
@Component
public class SpecialMsgListener implements RocketMQListener<MessageExt> , RocketMQPushConsumerLifecycleListener {
    @Override
    public void onMessage(MessageExt messageExt) {
        log.info("messageExt:{}", JSONUtil.toJsonStr(messageExt));
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        consumer.setConsumeThreadMin(2); //消费端拉去到消息以后分配线索去消费
        consumer.setConsumeThreadMax(10);//最大消费线程，一般情况下，默认队列没有塞满，是不会启用新的线程的
        consumer.setPullInterval(1000);//消费端多久一次去rocketMq 拉去消息
        consumer.setPullBatchSize(32);     //消费端每个队列一次拉去多少个消息，若该消费端分赔了N个监控队列，那么消费端每次去rocketMq拉去消息说为N*1
//        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_TIMESTAMP);
//        consumer.setConsumeTimestamp(UtilAll.timeMillisToHumanString3(System.currentTimeMillis()));
//        consumer.setConsumeMessageBatchMaxSize(2);
    }
}
