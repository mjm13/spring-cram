package com.meijm.roketmq.shell;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static com.meijm.roketmq.common.TestUtil.getMessage;

@Slf4j
@ShellComponent
public class BasisDemoShell {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @ShellMethod("asyncMsg")
    public void async() {
        rocketMQTemplate.asyncSend("Basis_Topic", getMessage("异步消息"), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("sendResult:{}",JSONUtil.toJsonStr(sendResult));
            }

            @Override
            public void onException(Throwable throwable) {
                log.error(throwable.getMessage(),throwable);
            }
        });
    }

    @ShellMethod("syncMsg")
    public void sync() {
        SendResult result = rocketMQTemplate.syncSend("Basis_Topic", getMessage("同步消息"));
        log.info("result:{}",JSONUtil.toJsonStr(result));
    }


    @ShellMethod("transactionMsg")
    public void transaction() {
        TransactionSendResult result1  = rocketMQTemplate.sendMessageInTransaction("Basis_Topic", getMessage("本地事物+事物检查"), RocketMQLocalTransactionState.UNKNOWN);
        log.info("result1:{}", JSONUtil.toJsonStr(result1));

        TransactionSendResult result2 = rocketMQTemplate.sendMessageInTransaction("Basis_Topic", getMessage("本地事物"), RocketMQLocalTransactionState.COMMIT);
        log.info("result2:{}", JSONUtil.toJsonStr(result2));
    }

}
