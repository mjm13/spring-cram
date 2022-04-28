package com.meijm.interview.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock公平锁,非公平锁及synchronized获取锁情况
 * java.util.concurrent.locks.AbstractQueuedSynchronizer.Node#next 865行
 */
@Slf4j
public class ReentrantLockDemo {
    /**
     * 非公平锁,都可争夺
     */
    public static final ReentrantLock nonfairLock = new ReentrantLock(false);
    /**
     * 公平锁,排队争夺
     */
    public static final ReentrantLock fairLock = new ReentrantLock(true);

    public static void main(String[] args) throws InterruptedException {
        //synchronized 锁
//        SynchronizedThread.test();

        // 公平锁, ,有序打印
//        FairLockThread.test();

        //非公平锁 无序打印
        NonFairLockThread.test();
    }
}

@Slf4j
class SynchronizedThread extends Thread {
    public SynchronizedThread(String name) {
        super(name);
    }
    @Override
    public void run() {
        while (true) {
            synchronized (ReentrantLockDemo.class){
                try {
                    log.info("线程获取了锁");
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public  static void test(){
        for (int i = 1; i < 6; i++) {
            new SynchronizedThread("线程"+i).start();
        }
    }
}
/**
 * ReentrantLock-FairLock 公平锁
 */
@Slf4j
class FairLockThread extends Thread {
    public FairLockThread(String name) {
        super(name);
    }
    @Override
    public void run() {
        while (true) {
            ReentrantLockDemo.fairLock.lock();
            try {
                log.info("线程获取了锁");
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                ReentrantLockDemo.fairLock.unlock();
            }
        }
    }

    public  static void test(){
        for (int i = 1; i < 6; i++) {
            new FairLockThread("线程"+i).start();
        }
    }
}
/**
 * ReentrantLock-NonFairLock 非公平锁
 */
@Slf4j
class NonFairLockThread extends Thread {
    public NonFairLockThread(String name) {
        super(name);
    }
    @Override
    public void run() {
        while (true) {
            ReentrantLockDemo.nonfairLock.lock();
            try {
                log.info("线程获取了锁");
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                ReentrantLockDemo.nonfairLock.unlock();
            }
        }
    }

    public  static void test(){
        for (int i = 1; i < 6; i++) {
            new NonFairLockThread("线程"+i).start();
        }
    }
}