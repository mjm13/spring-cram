package com.meijm.loki.controller;

import com.meijm.loki.feign.LokiQueryFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/loki-query")
public class QueryController {
    @Autowired
    private LokiQueryFeign lokiQueryFeign;

    @PostMapping("/query")
    public Map<String, Object> query(@RequestBody Map<String, Object> params) {
        return lokiQueryFeign.query(params);
    }

    @PostMapping("/test")
    public String test() {
        return "";
    }

}
