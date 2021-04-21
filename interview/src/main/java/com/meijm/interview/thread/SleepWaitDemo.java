package com.meijm.interview.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试sleep和wait对锁的释放情况
 */
@Slf4j
public class SleepWaitDemo {
    public static void main(String[] args) {
//        WaitThread.test();
        SleepThread.test();
    }
}
@Slf4j
class WaitThread extends Thread{
    private  Object lock;
    public WaitThread(int index,Object lock){
        super("WaitThread-"+index);
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock){
            log.info("开始休眠");
            try {
                lock.wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("执行结束");
        }
    }

    public static void test(){
        Object lock = new Object();
        for (int i = 1; i <3 ; i++) {
            new WaitThread(i,lock).start();
        }
    }
}
@Slf4j
class SleepThread extends Thread{
    private  Object lock;
    public SleepThread(int index,Object lock){
        super("SleepThread-"+index);
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock){
            log.info("开始休眠");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("执行结束");
        }
    }

    public static void test(){
        Object lock = new Object();
        for (int i = 1; i <3 ; i++) {
            new SleepThread(i,lock).start();
        }
    }
}