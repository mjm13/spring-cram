package com.meijm.jetcache.controller;

import com.meijm.jetcache.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class JetCacheController {
    @Autowired
    private TestService testService;

    @GetMapping("/cacheMap")
    public Map<String, String> cacheMap(@RequestParam String key) {
        return testService.cacheMap(key);
    }

    @GetMapping("/deleteCacheMap")
    public String deleteCacheMap(@RequestParam String key) {
        testService.deleteCacheMap(key);
        return "ok";
    }

}
