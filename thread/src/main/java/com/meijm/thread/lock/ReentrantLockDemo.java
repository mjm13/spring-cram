package com.meijm.thread.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class ReentrantLockTest {
    /**
     * 非公平锁,都可争夺
     */
    public static final ReentrantLock nonfairLock = new ReentrantLock(false);
    /**
     * 公平锁,排队争夺
     */
    public static final ReentrantLock fairLock = new ReentrantLock(true);


    public static void main(String[] args) throws InterruptedException {
        // 正常使用, 效果类似synchronized
//        for (int i = 0; i < 100; i++) {
//            new FairLockThread("线程"+i).start();
//        }
        for (int i = 0; i < 1000; i++) {
            new NonFairLockThread("线程"+i).start();
        }
    }
}


/**
 * ReentrantLock 基础使用,同synchronized关键字
 */
@Slf4j
class FairLockThread extends Thread {
    public FairLockThread(String name) {
        super(name);
    }
    @Override
    public void run() {
        ReentrantLockTest.fairLock.lock();
        try {
            log.info("线程获取了锁");
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            ReentrantLockTest.fairLock.unlock();
        }
    }
}

@Slf4j
class NonFairLockThread extends Thread {
    public NonFairLockThread(String name) {
        super(name);
    }
    @Override
    public void run() {
        ReentrantLockTest.nonfairLock.lock();
        try {
            log.info("线程获取了锁");
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            ReentrantLockTest.nonfairLock.unlock();
        }
    }
}