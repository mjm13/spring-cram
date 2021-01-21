package com.meijm.roketmq.shell;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import javax.annotation.Resource;

import static com.meijm.roketmq.common.TestUtil.getMessage;

@Slf4j
@ShellComponent
public class SpecialDemoShell {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @ShellMethod("receive")
    public void receive() {
        String result = rocketMQTemplate.sendAndReceive("Special_Topic",getMessage("sendAndReceive"),String.class,100000);
        log.info(result);
    }


}
