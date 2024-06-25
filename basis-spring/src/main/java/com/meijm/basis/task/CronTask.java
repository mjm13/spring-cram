package com.meijm.basis.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 测试执行是否会重复触发
 */
@Slf4j
@Component
public class CronTask {
//    @Scheduled(cron="*/1 * * * * *")
    public void doSomething() throws InterruptedException {
        log.info("任务执行中");
        Thread.sleep(2000);
    }


    @Scheduled(fixedRate = 5000)
    public void test() {
        log.info("任务执行中");
        throw new RuntimeException("111");
    }
}
