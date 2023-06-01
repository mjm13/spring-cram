package com.meijm.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class SlidingWindowCounter {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private String counterKey = "testcounter";
    private long windowSize = 5000;


    public void addData() {
        long currentTime = System.currentTimeMillis();
        String data = String.valueOf(currentTime);

        redisTemplate.opsForZSet().add(counterKey, data, currentTime);

        // 清理过期数据
        long minScore = currentTime - windowSize;
        redisTemplate.opsForZSet().removeRangeByScore(counterKey, 0, minScore);
    }

    public long getCount() {
        return redisTemplate.opsForZSet().zCard(counterKey);
    }

    public Set<String> getData() {
        return redisTemplate.opsForZSet().range(counterKey, 0, -1);
    }
}