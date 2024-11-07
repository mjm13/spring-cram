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
    @Scheduled(cron="*/1 * * * * *")
    public void doSomething() throws InterruptedException {
        log.info("任务执行中111111111111111");
        Thread.sleep(2000);
    }


    @Scheduled(fixedRate = 1000)
    public void test() throws Exception {
        log.info("任务执行中222222222222");
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(3000);
                throw new SQLException("111");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        t.start();
    }
}
