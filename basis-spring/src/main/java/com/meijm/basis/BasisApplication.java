package com.meijm.basis;

import com.meijm.basis.bean.ComplexProp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableAsync
@EnableAspectJAutoProxy
@SpringBootApplication
@EnableConfigurationProperties(ComplexProp.class)
@EnableScheduling
public class BasisApplication {
    public static void main(String[] args) {
        SpringApplication.run(BasisApplication.class, args);
    }
}
