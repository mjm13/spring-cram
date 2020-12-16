package com.meijm.thread.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class ThreadPoolDemo {

    public void siglePool() {
        ExecutorService service = Executors.newWorkStealingPool();
        service.execute(() -> {
            System.out.println("执行线程");
        });
    }

}
