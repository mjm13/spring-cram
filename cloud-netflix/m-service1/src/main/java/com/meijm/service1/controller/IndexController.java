package com.meijm.service1.controller;

import com.meijm.service1.feign.Service2Index;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class IndexController {

    @Autowired
    private Service2Index service2Index;

    @GetMapping(value = {"/index",""})
    public Map<String,String> index(){
        Map<String,String> data = new HashMap<>();
        data.put("service1","service1-index");
        data.putAll(service2Index.index());
        return data;
    }
}
