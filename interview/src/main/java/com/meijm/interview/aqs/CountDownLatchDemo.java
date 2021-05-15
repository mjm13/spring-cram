package com.meijm.interview.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(7);
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 1; i <=7 ; i++) {
            service.execute(new CountDownLatchThread("thread-"+i,latch));
        }
        latch.await();
        log.info("召唤神龙");
    }
}

@Slf4j
class CountDownLatchThread extends Thread{
    private CountDownLatch latch;
    public CountDownLatchThread(String threadName,CountDownLatch latch){
        super(threadName);
        this.latch = latch;
    }
    @Override
    public void run() {
        log.info("开始找龙珠");
        try {
            TimeUnit.SECONDS.sleep((long)(Math.random()*10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("找到龙珠");
        latch.countDown();
    }
}
