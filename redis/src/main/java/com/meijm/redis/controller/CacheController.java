package com.meijm.redis.controller;

import com.meijm.redis.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/cache")
public class CacheController {
    int i = 1;
    @Autowired
    private CacheService cacheService;
    @GetMapping("/get")
    public List<Map<String, String>> get() {
        log.info("i:{}",++i);
        return cacheService.getCache();
    }
}
