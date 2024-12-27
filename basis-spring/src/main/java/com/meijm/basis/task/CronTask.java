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

    @Scheduled(fixedRate = 1)
    public synchronized void doSomething() throws  IllegalAccessException {
        Thread t = new Thread(() -> {
            log.info(HttpUtil.get("http://localhost:9821/dispatchswitch/getSwitchByCode?switchCode=ACROSS_SCHEDULE"));
        });
        t.start();
    }


}
