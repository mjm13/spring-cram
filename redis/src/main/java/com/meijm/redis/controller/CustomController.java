package com.meijm.redis.controller;

import cn.hutool.core.util.StrUtil;
import com.meijm.redis.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/custom")
public class CustomController {
    @Autowired
    private CommonService commonService;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/setTemp")
    public void setTemp(String key,String value) {
        redisTemplate.opsForValue().set(key, value);
    }


    @GetMapping("/set")
    public void set() {
        List<Map<String,String>> datas =  commonService.getDatas();
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("datas",datas);
        for (int i = 0; i < 1000000; i++) {
            log.info("put key i={}",i);
            redisTemplate.opsForValue().set(String.valueOf(i)+"b", StrUtil.repeat("a",i*10));
        }
//        redisTemplate.opsForValue().set("mjm13", "MeiJM");
    }

    @GetMapping("/get")
    public List<Map<String, String>> get() {
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();
        return (List<Map<String,String>>)valueOperations.get("datas");
    }
}
