package com.meijm.service2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class IndexController {

    @GetMapping(value = {"/index",""})
    public Map<String,String> index(){
        Map<String,String> data = new HashMap<>();
        data.put("service2","service2-index");
        return data;
    }
}