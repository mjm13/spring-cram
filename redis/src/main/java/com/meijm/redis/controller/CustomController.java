package com.meijm.redis.controller;

import com.meijm.redis.service.CommonService;
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

@RestController
@RequestMapping("/custom")
public class CustomController {
    @Autowired
    private CommonService commonService;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/set")
    public void set() {
        List<Map<String,String>> datas =  commonService.getDatas();
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("datas",datas);
    }

    @GetMapping("/get")
    public List<Map<String, String>> get() {
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();
        return (List<Map<String,String>>)valueOperations.get("datas");
    }
}
