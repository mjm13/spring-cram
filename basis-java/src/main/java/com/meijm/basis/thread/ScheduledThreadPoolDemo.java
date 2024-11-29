package com.meijm.basis.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


@Slf4j
public class ScheduledThreadPoolDemo {
    public static void main(String[] args) {
        AtomicInteger i = new AtomicInteger();
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
        executor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                log.info("Task is running {}",i.get());
                if (i.getAndIncrement() % 3 == 1) {
//                    异常会导致任务终止
//                    throw new RuntimeException("test");
                    try {
                        Thread.sleep(10000L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }, 0, 1, TimeUnit.SECONDS);

    }
}
