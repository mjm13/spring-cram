package com.meijm.roketmq.controller;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.meijm.roketmq.common.TestUtil.getMessage;

@Slf4j
@RestController
@RequestMapping(value = "/basis")
public class BasisController {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping(value = ("/asyncMsg"))
    public void async() {
        rocketMQTemplate.asyncSend("Basis_Topic", getMessage("异步消息"), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("sendResult:{}", JSONUtil.toJsonStr(sendResult));
            }

            @Override
            public void onException(Throwable throwable) {
                log.error(throwable.getMessage(), throwable);
            }
        });
    }

    @GetMapping(value = ("/syncMsg"))
    public void sync() {
        SendResult result = rocketMQTemplate.syncSend("Basis_Topic", getMessage("同步消息"));
        log.info("result:{}", JSONUtil.toJsonStr(result));
    }

    @GetMapping(value = ("/transactionMsg"))
    public void transaction() {
        TransactionSendResult result1 = rocketMQTemplate.sendMessageInTransaction("Basis_Topic", getMessage("本地事物+事物检查"), RocketMQLocalTransactionState.UNKNOWN);
        log.info("result1:{}", JSONUtil.toJsonStr(result1));

        TransactionSendResult result2 = rocketMQTemplate.sendMessageInTransaction("Basis_Topic", getMessage("本地事物"), RocketMQLocalTransactionState.COMMIT);
        log.info("result2:{}", JSONUtil.toJsonStr(result2));
    }

}
