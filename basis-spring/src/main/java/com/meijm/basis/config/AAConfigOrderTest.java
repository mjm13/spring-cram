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
public class AAConfigOrderTest {
    @Bean("aatest")
    public HashMap aatest(){
        log.info("init aatest");
        HashMap<String,String> atest = new HashMap<>();
        atest.put("aa","aa");
        return atest;
    }
}
