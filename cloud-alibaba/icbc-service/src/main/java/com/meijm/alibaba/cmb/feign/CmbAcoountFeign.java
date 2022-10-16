package com.meijm.alibaba.cmb.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="cmb-service")
public interface CmbAcoountFeign {
    @GetMapping("/account/print")
    String print(@RequestParam String word);
}
