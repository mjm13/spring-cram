package com.meijm.springstream.controller;

import com.meijm.springstream.listener.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private MessageHandler handler;

    @GetMapping("/test")
    public String testStream(String message) {
        handler.sendMessage(message);
        return message;
    }
}
