package com.meijm.jetcache.service;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.Cached;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TestService {
    @Cached(name="userCache.", key="#key", expire = 3600)
    public Map<String,String> cacheMap(String key){
        Map<String,String> temp = new HashMap<>();
        temp.put(key,key);
        return temp;
    }

    @CacheInvalidate(name="userCache.", key="#key")
    public void deleteCacheMap(String key){

    }
}
