package com.meijm.allatori.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DemoService {
    public Map<String, String> data() {
        Map<String, String> data = new HashMap<>();
        data.put("hello", "allatori");
        return data;
    }
}
