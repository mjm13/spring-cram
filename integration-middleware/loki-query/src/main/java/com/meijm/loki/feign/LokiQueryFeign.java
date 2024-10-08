package com.meijm.loki.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@FeignClient(name = "loki-query", url = "${loki.url}")
public interface LokiQueryFeign {
    @GetMapping("/query")
    Map<String,Object> query(@SpringQueryMap Map<String,Object> params);
}
