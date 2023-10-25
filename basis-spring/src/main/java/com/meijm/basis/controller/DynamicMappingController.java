package com.meijm.basis.controller;

import cn.hutool.core.date.DateUtil;
import com.meijm.basis.annotation.DynamicMapping;
import com.meijm.basis.dto.TestQueryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/dynamicMapping")
public class DynamicMappingController {
    @DynamicMapping
    @RequestMapping(value = "/test")
    public Map test(Map<String, String> data) {
        data.put("date", DateUtil.now());
        return data;
    }
}
