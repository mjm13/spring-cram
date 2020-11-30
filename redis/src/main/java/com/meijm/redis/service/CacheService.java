package com.meijm.redis.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CacheService {
    @Autowired
    private CommonService commonService;

    @Cacheable(value = "spring")
    public List<Map<String, String>> getCache() {
        log.info("get value in getCache() ");
        return commonService.getDatas();
    }
}
