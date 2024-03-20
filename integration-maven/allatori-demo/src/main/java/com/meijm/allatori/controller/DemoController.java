package com.meijm.allatori.controller;

import com.meijm.allatori.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/demo")
@RestController
public class DemoController {
    @Autowired
    private DemoService demoService;
    @RequestMapping("/index")
    public ResponseEntity index() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(demoService.data());
    }
}
