package com.meijm.interview.concurrent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SynchronizedDemo {

    public synchronized void lockMethod() {
        try {
            log.info("锁方法");
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SynchronizedDemo demo = new SynchronizedDemo();
        SynchronizedDemo demo1 = new SynchronizedDemo();
        new Thread(() -> {
            demo.lockMethod();
        }).start();
        new Thread(() -> {
            demo.lockInstance(demo1);
        }).start();

    }

    public void lockInstance(SynchronizedDemo demo){
        log.info("进入锁实例demo");
        synchronized (demo){
            try {
                log.info("锁实例");
                Thread.sleep(1000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
