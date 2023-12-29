package com.meijm.basis.thread;

import java.util.concurrent.CountDownLatch;

public class SyncDemo {
    public static void main(String[] args) throws InterruptedException {
        long begin = System.currentTimeMillis();
        System.out.println(begin);
        CountDownLatch temp = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                synchronized (String.valueOf(finalI)){
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    temp.countDown();
                }
            }).start();
        }
        temp.await();
        long end = System.currentTimeMillis();
        System.out.println(end);
        System.out.println(end-begin);
    }
}
