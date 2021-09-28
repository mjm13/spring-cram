package com.meijm.kafka.controller;

import cn.hutool.json.JSONUtil;
import com.google.common.collect.ImmutableMap;
import com.meijm.kafka.vo.TestVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
//@RequestMapping(value = "/basis")
public class BasisController {

    @Lazy
    @Resource(name = "kafkaTemplate")
    private KafkaTemplate kafkaTemplate;
    @Lazy
    @Resource(name = "custiomKafkaTemplate")
    private KafkaTemplate custiomKafkaTemplate;

    @GetMapping(value = ("/send"))
    public void send() {
        HashMap<String, String> map = new HashMap<>();
        map.put("sendType","send");
        kafkaTemplate.send("test01", JSONUtil.toJsonStr(map));
    }

    @GetMapping(value = ("/customSend"))
    public void customSend() {
        TestVo vo = new TestVo();
        vo.setKey("sendType");
        vo.setValue("send");
        custiomKafkaTemplate.send("custom04", vo);
    }

}
