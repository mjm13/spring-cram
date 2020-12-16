package com.meijm.thread.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;

@Slf4j
@Component
public class ThreadDemo {
    public void deadlock() {
        Thread t1 = new Thread(() -> {
            log.info("t1运行中,开始获取Object锁");
            try {
                synchronized (Object.class) {
                    log.info("t1获取Object锁成功开始休眠");
                    Thread.sleep(1000);
                    log.info("t1休眠完成,开始获取ThreadDemo锁");
                    synchronized (ThreadDemo.class) {
                        log.info("t1获取ThreadDemo锁成功");
                    }
                }
            } catch (InterruptedException e) {
                log.error("t1运行报错", e);
            }
            log.info("t1运行完成");
        }, "t1");
        Thread t2 = new Thread(() -> {
            log.info("t2运行中,开始获取Object锁");
            try {
                synchronized (ThreadDemo.class) {
                    log.info("t2获取ThreadDemo锁成功开始休眠");
                    Thread.sleep(1000);
                    log.info("t2休眠完成,开始获取ThreadDemo锁");
                    synchronized (Object.class) {
                        log.info("t2获取Object锁成功");
                    }
                }
            } catch (InterruptedException e) {
                log.error("t2运行报错", e);
            }
            log.info("t2运行完成");
        }, "t2");

        t1.start();
        t2.start();
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.size();
    }

    public void join() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                log.info("t1执行开始");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error("t1运行报错", e);
            }
            log.info("t1运行完成");
        }, "t1");
        log.info("t1.start()");
        t1.start();
        log.info("t1.join()");
        t1.join();
        log.info("结束");
    }

    public void interrupt() throws InterruptedException {
        Thread t1 = new MyThread();
        t1.start();
        Thread.sleep(1000);
        t1.interrupt();
        System.out.println("程序执行完成");
    }
    class MyThread extends Thread{
        @Override
        public void run(){
            while (true){
                if(this.isInterrupted()){
                    System.out.println("退出循环");
                    break;
                }
                System.out.println("循环中");
            }
            System.out.println("循环结束");
        }
    }


}
