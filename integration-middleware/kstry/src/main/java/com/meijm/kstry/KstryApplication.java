package com.meijm.kstry;

import cn.kstry.framework.core.annotation.EnableKstry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
//@EnableKstry(bpmnPath = "./bpmn/*.bpmn,./bpmn/*.json")
@EnableKstry
public class KstryApplication {

    public static void main(String[] args) {
        SpringApplication.run(KstryApplication.class, args);
    }
}