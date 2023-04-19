package com.meijm.junit.controller;

import com.meijm.junit.model.Demo;
import com.meijm.junit.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/demo1")
public class Demo1Controller {
    @Autowired
    private DemoService demoService;

    @PostMapping("/insert")
    public long insert(@RequestBody  Demo demo) {
        return demoService.insert(demo);
    }

    @GetMapping("/query")
    public List<Demo> query() {
        return demoService.query();
    }
}
