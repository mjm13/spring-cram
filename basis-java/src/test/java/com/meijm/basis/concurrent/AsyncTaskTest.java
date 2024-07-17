package com.meijm.basis.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
public class AsyncTaskTest {
    @Test
    public void completableFutureTest() throws Exception {
        CompletableFuture<String>[] futures = new CompletableFuture[10];
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                try {
                    TimeUnit.SECONDS.sleep((long) (Math.random() * 10));
                    return finalI + "-result";
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            futures[i]=future;
        }
        //不等待直接获取结果
        Thread.sleep(1000);
        for (CompletableFuture<String> future : futures) {
            log.info(future.getNow("unover"));
        }
        
//        CompletableFuture.allOf(futures).join();
//        for (CompletableFuture<String> future : futures) {
//            log.info(future.getNow("unover"));
//        }
    }

    @Test
    public void futureTaskTest() throws Exception {
        ExecutorService service = Executors.newCachedThreadPool();
        List<FutureTask> tasks = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            FutureTask task = new FutureTask<>(() -> {
                TimeUnit.SECONDS.sleep((long) (Math.random() * 10));
                return finalI + "-result";
            });
            service.submit(task);
            tasks.add(task);
        }
        for (int i = 0; i < tasks.size(); i++) {
            FutureTask<String> task = tasks.get(i);
            if (i % 7 == 0) {
                task.cancel(true);
            }
        }
        for (FutureTask<String> task : tasks) {
            if (!task.isCancelled()) {
                log.info(task.get());
            }
        }
        log.info("主程序结束");
    }
}