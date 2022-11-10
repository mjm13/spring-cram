package com.meijm.alibaba.cmb.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class ConfigController {

    @Value("${remoteValue}")
    private String remoteValue;

    @GetMapping("/print")
    public String getRemoteValue() {
        return remoteValue;
    }
}
