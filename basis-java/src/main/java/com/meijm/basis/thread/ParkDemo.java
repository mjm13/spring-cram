package com.meijm.basis.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSurport.park 为中止某个线程
 * 如线程标识为中断则不会生效
 */
@Slf4j
public class ParkDemo {
    public static void main(String[] args) throws InterruptedException {
        ParkDemo.demo();
//        ParkDemo.interruptParkDemo();
    }
    public  static void demo() throws InterruptedException {
        Thread temp = new ParkTestThread();
        temp.start();
        Thread.sleep(1000);
        log.info("开始准备unpark");
        LockSupport.unpark(temp);
        log.info("程序执行完成");
    }

    public  static void interruptParkDemo() throws InterruptedException {
        Thread temp = new ParkTestThread();
        temp.start();
        temp.interrupt();
        Thread.sleep(1000);
        log.info("开始准备unpark");
        LockSupport.unpark(temp);
        log.info("程序执行完成");
    }
}
@Slf4j
class ParkTestThread extends Thread{
    @Override
    public void run() {
            log.info("开始准备park,interrupt:{}",this.isInterrupted());
            LockSupport.park();
            log.info("线程执行完成");
    }
}
