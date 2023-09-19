package com.meijm.seataDemo1.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description
 * @Author liq
 * @Date 2022/6/1 11:16
 **/
@FeignClient(name = "seata-demo2")
public interface Demo2Feign {

    /**
     * @return
     */
    @GetMapping("/demo2/updateDemo2")
    Boolean updateDemo2();


    /**
     * @return
     */
    @GetMapping("/demo2/updateDemo2ThrowException")
    Boolean updateDemo2ThrowException();
}
