package com.meijm.rabbitmq.controller;

import cn.hutool.json.JSONUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
public class DemoController {

    @Autowired
    private RabbitTemplate kafkaTemplate;

    @GetMapping(value = ("/send"))
    public void send() {
        HashMap<String, String> map = new HashMap<>();
        map.put("sendType", "send");
        kafkaTemplate.convertAndSend("plg-yc-m-test", JSONUtil.toJsonStr(map));
    }

}
