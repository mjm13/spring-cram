package com.meijm.interview.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * Callable 使用demo
 * FutureTask 包装Callable 提供取消功能
 * Future  ExecutorService.submit() 执行结果
 */
@Slf4j
public class CallableDemo  {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Future 例子
//        futureDemo();
        //FutureTask 例子
        futureTaskDemo();
    }

    public static void futureDemo() throws ExecutionException, InterruptedException{
        ExecutorService service = Executors.newCachedThreadPool();
        List<Future> futures = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(100);
        for (int i = 0; i < 100; i++) {
            Future<String> future = service.submit(new TestCallable("job"+i,latch));
            futures.add(future);
        }
        latch.await();
        for (Future<String> future:futures) {
            log.info(future.get());
        }
        log.info("主程序结束");
    }
    public static void futureTaskDemo() throws ExecutionException, InterruptedException{
        ExecutorService service = Executors.newCachedThreadPool();
        List<FutureTask> tasks = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(100);
        for (int i = 0; i < 100; i++) {
            FutureTask task = new FutureTask<>(new TestCallable("job"+i,latch));
            service.submit(task);
            tasks.add(task);
        }
        latch.await();
        for (FutureTask<String> task:tasks) {
            log.info(task.get());
        }
        log.info("主程序结束");
    }
}

class TestCallable implements Callable<String>{

    private CountDownLatch latch;

    private String name;

    public TestCallable(String name,CountDownLatch latch){
        this.name = name;
        this.latch = latch;
    }

    @Override
    public String call() throws Exception {
        TimeUnit.SECONDS.sleep((long)(Math.random()*10));
        latch.countDown();
        return name+"-result";
    }
}