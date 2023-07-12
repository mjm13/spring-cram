package com.meijm.jetcache;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableMethodCache(basePackages = "com.meijm.jetcache")
@EnableCreateCacheAnnotation
@SpringBootApplication
public class JetcacheApplication {
    public static void main(String[] args) {
        SpringApplication.run(JetcacheApplication.class, args);
    }
}