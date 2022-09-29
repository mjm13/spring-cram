package com.meijm.basis.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Slf4j
@Configuration
@AutoConfigureAfter({PropertySourceLoader.class})
public class AConfigOrderTest {
    @Bean("atest")
    public HashMap atest(){
        log.info("init atest");
        HashMap<String,String> atest = new HashMap<>();
        atest.put("aa","aa");
        return atest;
    }
}
