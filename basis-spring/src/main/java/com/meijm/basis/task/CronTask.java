package com.meijm.basis.task;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.MultiThrowable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试执行是否会重复触发
 */
@Slf4j
@Component
public class CronTask {

    private final AtomicInteger scheduledCounter = new AtomicInteger();

    @Scheduled(fixedDelay = 1000)
    public synchronized void doSomething() throws  IllegalAccessException {
        log.info("begin");
        if (scheduledCounter.getAndIncrement()%3 == 0) {
                throw new IllegalAccessException();
        }
        log.info("end");
    }


}
