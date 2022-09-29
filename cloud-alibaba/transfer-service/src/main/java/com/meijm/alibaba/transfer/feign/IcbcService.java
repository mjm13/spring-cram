package com.meijm.alibaba.transfer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "icbc-service")
public interface IcbcService {

    @PostMapping(value = "/account/update")
    String test(@PathVariable("message") String message);
}
