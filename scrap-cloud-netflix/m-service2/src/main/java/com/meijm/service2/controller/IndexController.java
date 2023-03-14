package com.meijm.service2.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class IndexController {

    @GetMapping(value = {"/index",""})
    public Map<String,String> index(ServletRequest request){
        log.info("in service2 index ");
        Map<String,String> data = new HashMap<>();
        data.put("service2","service2-index");
        return data;
    }
}