package com.meijm.roketmq.config;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQTransactionListener
public class MqTransactionListener implements RocketMQLocalTransactionListener {

    /**
     * 本地事物 当消息存储至mq后回调,结果返回UNKNOWN时执行检查事物
     * @param msg
     * @param arg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        log.info("executeLocalTransaction:{}" , JSONUtil.toJsonStr(msg));
        return (RocketMQLocalTransactionState) arg;
    }

    /**
     * 检查事物
     * @param msg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        log.info("checkLocalTransaction:{}" , JSONUtil.toJsonStr(msg));
        return RocketMQLocalTransactionState.UNKNOWN;
    }
}