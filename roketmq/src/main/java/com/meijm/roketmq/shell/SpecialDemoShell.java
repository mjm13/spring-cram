package com.meijm.roketmq.shell;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import javax.annotation.Resource;

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
        otherRocketMQTemplate.send("Special_Topic",getMessage("另一个生产者"));
    }


    @ShellMethod("pullMsg")
    public void pullMsg() {
//        https://www.jianshu.com/p/fc6e4cfe39cb
//        https://blog.csdn.net/weixin_38003389/article/details/86658396
    }
}
