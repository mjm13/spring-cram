package com.meijm.service1.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Component
@FeignClient("M-SERVICE2")
public interface Service2Index {
    @GetMapping("/index")
    Map<String,String> index();
}
