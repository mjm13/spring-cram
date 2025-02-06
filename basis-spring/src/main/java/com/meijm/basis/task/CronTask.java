package com.meijm.basis.task;

import cn.hutool.http.HttpUtil;
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

    @Scheduled(initialDelay=3000,fixedDelay=1000)
    public  void doSomething() throws  Exception {
        Thread.sleep(60*1000*20);
        log.error("1111111111111111");
    }


}
