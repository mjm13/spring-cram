package com.meijm.roketmq.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.util.Map;

import static com.meijm.roketmq.common.TestUtil.getMessage;

@Slf4j
@RestController
@RequestMapping(value = "/special")
public class SpecialController {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Resource(name = "otherRocketMQTemplate")
    private RocketMQTemplate otherRocketMQTemplate;

    @GetMapping(value =("/receive"))
    public String receive() {
        return rocketMQTemplate.sendAndReceive("Reply_Topic",getMessage("sendAndReceive"),String.class,100000);
    }

    @GetMapping(value =("/otherSend"))
    public Boolean otherSend() {
//        otherRocketMQTemplate.send("Special_Topic",getMessage("另一个生产者"));

        MessageHeaderAccessor headers = new MessageHeaderAccessor(null);
        headers.setHeader(RocketMQHeaders.KEYS,"Key-MeiJM");
        Message<Map<String, String>> msg = getMessage("另一个生产者,携带tag,key",headers);
        otherRocketMQTemplate.send("Special_Topic:测试标签",msg);
        return Boolean.TRUE;
    }


//    @ShellMethod("pullMsg")
//    public void pullMsg() {
//        https://www.jianshu.com/p/fc6e4cfe39cb
//        https://blog.csdn.net/weixin_38003389/article/details/86658396
//    }
}
