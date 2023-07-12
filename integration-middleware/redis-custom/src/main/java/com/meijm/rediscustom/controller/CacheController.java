package com.meijm.rediscustom.controller;

import com.meijm.rediscustom.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/cache")
public class CacheController {
    @Autowired
    private CacheService cacheService;

    @GetMapping("/cacheMap")
    public Map<String, String> cacheMap(@RequestParam String key) {
        return cacheService.getCache(key);
    }

    @GetMapping("/deleteCacheMap")
    public String deleteCacheMap(@RequestParam String key) {
        cacheService.deleteCache(key);
        return "ok";
    }

}
