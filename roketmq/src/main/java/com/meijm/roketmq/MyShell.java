package com.meijm.roketmq;

import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@ShellComponent
public class MyShell {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @ShellMethod("temp")
    public void temp() {
        Map<String, String> data = new HashMap<>();
        data.put("mjm03", "mjm03");
        Message<Map<String, String>> message = MessageBuilder.withPayload(data).
                build();
        TransactionSendResult result  = rocketMQTemplate.sendMessageInTransaction("mjmTopic", message,"mytest");
        System.out.println("in MyShell Message Id"+result.getMsgId());
    }

}
