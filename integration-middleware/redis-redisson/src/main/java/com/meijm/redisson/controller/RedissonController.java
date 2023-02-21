package com.meijm.redisson.controller;

import cn.hutool.core.util.StrUtil;
import com.meijm.redisson.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RKeys;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
@RestController
@RequestMapping("/redisson")
public class RedissonController {
    @Autowired
    private RedissonClient redissonClient;

    @GetMapping("/ratomicLong")
    public long ratomicLong() {
        RAtomicLong temp = redissonClient.getAtomicLong("temp");
        return temp.getAndIncrement();
    }

    @GetMapping("/rkeys")
    public String rkeys(){
        RKeys keys = redissonClient.getKeys();
        Iterable<String> foundedKeys = keys.getKeysByPattern("*b");
        return StreamSupport.stream(foundedKeys.spliterator(),false)
                .collect(Collectors.joining(","));
    }

    @GetMapping("/rlock")
    public String rlock(){
        RLock lock = redissonClient.getLock("myLock");
        String result = "";
        try {
            boolean res = lock.tryLock(10,  TimeUnit.SECONDS);
            if (res) {
                try {
                    Thread.sleep(2000);
                    result = "计算完成";
                } finally {
                    lock.unlock();
                }
            }else{
                result = "未参与计算-未获取到锁";
            }
        } catch (InterruptedException e) {
            result = "未参与计算-创建锁异常";
        }
        return result;
    }

    @Autowired
    private CommonService commonService;
    @Autowired
    private RedisTemplate redisTemplate;


    @GetMapping("/set")
    public void set() {
        List<Map<String, String>> datas = commonService.getDatas();
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("datas", datas);
        for (int i = 0; i < 10; i++) {
            log.info("put key i={}", i);
            redisTemplate.opsForValue().set(String.valueOf(i) + "b", StrUtil.repeat("a", i * 10));
        }
    }

    @GetMapping("/get")
    public List<Map<String, String>> get() {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        return (List<Map<String, String>>) valueOperations.get("datas");
    }
}
