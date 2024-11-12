package com.meijm.basis.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

/**
 * 测试执行是否会重复触发
 */
@Slf4j
@Component
public class CronTask {
    @Scheduled(fixedRate = 1000)
    public void doSomething() throws InterruptedException {
        log.info("任务执行中111111111111111");
    }


    @Scheduled(fixedRate = 1000)
    public void test() throws InterruptedException {
        log.info("任务执行中222222222222");
        Thread.sleep(30000L);
//        int i = 1/0;
    }
}
