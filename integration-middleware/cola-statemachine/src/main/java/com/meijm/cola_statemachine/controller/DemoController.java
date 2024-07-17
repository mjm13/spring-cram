package com.meijm.cola_statemachine.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "demo")
public class DemoController {
    @RequestMapping(value = "test", method = RequestMethod.GET)
    public Map<String, Object> test() {
        return null;
    }
}
