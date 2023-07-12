package com.meijm.rediscustom.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CacheService {

    @Cacheable(value = "custom:",key = "#key")
    public Map<String, String> getCache(String key) {
        Map<String,String> data = new HashMap<>();
        data.put(key,key);
        return data;
    }

    @CacheEvict(value = "custom:" ,allEntries = true)
    public void deleteCache(String key){

    }
}
