package com.meijm.interview.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
/**
 * 测试condition功能
 * Condition.await:加入等待队列，并释放锁
 * Condition.signal/Condition.await()
 *
 * Object.notify()/Object.wait()
 */
public class LockConditionDemo {
    /**
     * 非公平锁,都可争夺
     */
    public static final ReentrantLock conditionLock = new ReentrantLock();

    public static final Condition condition = conditionLock.newCondition();

    public static void main(String[] args) throws InterruptedException {

        ReentrantLockConditionThread.test();
    }
}

@Slf4j
class ReentrantLockConditionThread extends Thread{
    @Override
    public void run() {
        LockConditionDemo.conditionLock.lock();
        try{
            log.info("线程开始等待-LockConditionDemo.condition.await()");
            LockConditionDemo.condition.await();
            log.info("线程恢复");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LockConditionDemo.conditionLock.unlock();
            log.info("unlock 释放锁定");
        }
        try {
            Thread.sleep(1000);
            log.info("线程结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void test() throws InterruptedException {
        log.info("主程序开始");
        ReentrantLockConditionThread rlct = new ReentrantLockConditionThread();
        rlct.start();
        Thread.sleep(1000);
        LockConditionDemo.conditionLock.lock();
        try{
            log.info("主程序释放-LockConditionDemo.condition.signal()");
            LockConditionDemo.condition.signal();
            Thread.sleep(1000);
            log.info("主程序释放完成");
        }finally {
            LockConditionDemo.conditionLock.unlock();
        }
        log.info("主程序结束");
    }
}