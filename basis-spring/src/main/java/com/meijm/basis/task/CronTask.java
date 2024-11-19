package com.meijm.basis.task;

import lombok.extern.slf4j.Slf4j;
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

    @Scheduled(fixedRate = 1000)
    public synchronized void doSomething() throws InterruptedException {
        log.info("begin");
        Thread.sleep(2000);
        log.info("end");
    }


}
