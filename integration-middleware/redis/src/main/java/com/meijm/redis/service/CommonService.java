package com.meijm.redis.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CommonService {


    public List<Map<String,String>> getDatas(){
        List<Map<String,String>> datas = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Map data = new HashMap();
            data.put("k"+i,"v"+i);
            datas.add(data);
        }
        return datas;
    }
}
