package com.meijm.basis.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.meijm.basis.event.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class PrintController {
    @CrossOrigin
    @PostMapping(value = "/print")
    public String print() {
        return "print";
    }


    @CrossOrigin
    @PostMapping(value = "/test")
    public JSONObject test(@RequestBody Object object) {
        log.info(JSONUtil.toJsonStr(object));
        JSONObject a = new JSONObject();
        a.set("aaa","a1");
        return a;
    }

}
