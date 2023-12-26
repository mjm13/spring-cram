package com.meijm.springstream.controller;

import com.meijm.springstream.component.MySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private MySource mySource;
    
    @GetMapping("/test")
    public String testStream(String message){
        mySource.send(message);
        return message;
    }
}
