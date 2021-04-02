package com.meijm.roketmq.shell;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import javax.annotation.Resource;

import java.util.Map;

import static com.meijm.roketmq.common.TestUtil.getMessage;

@Slf4j
@ShellComponent
public class SpecialDemoShell {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Resource(name = "otherRocketMQTemplate")
    private RocketMQTemplate otherRocketMQTemplate;

    @ShellMethod("receive")
    public void receive() {
        String result = rocketMQTemplate.sendAndReceive("Reply_Topic",getMessage("sendAndReceive"),String.class,100000);
        log.info(result);
    }

    @ShellMethod("otherSend")
    public void otherSend() {
//        otherRocketMQTemplate.send("Special_Topic",getMessage("另一个生产者"));

        MessageHeaderAccessor headers = new MessageHeaderAccessor(null);
        headers.setHeader(RocketMQHeaders.KEYS,"Key-MeiJM");
        Message<Map<String, String>> msg = getMessage("另一个生产者,携带tag,key",headers);
        otherRocketMQTemplate.send("Special_Topic:测试标签",msg);
    }


    @ShellMethod("pullMsg")
    public void pullMsg() {
//        https://www.jianshu.com/p/fc6e4cfe39cb
//        https://blog.csdn.net/weixin_38003389/article/details/86658396
    }
}
