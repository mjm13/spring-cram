package com.meijm.interview.concurrent;

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
@Slf4j
public class LockConditionDemo {
    /**
     * 非公平锁,都可争夺
     */
    public static final ReentrantLock lock = new ReentrantLock();

    public static final Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        log.info("主程序开始");
        ConditionDemoThread conditionDemoThread = new ConditionDemoThread();
        conditionDemoThread.start();
        Thread.sleep(1000);
        LockConditionDemo.lock.lock();
        log.info("主程序lock");
        try{
            log.info("主程序释放-Condition.signal()");
            LockConditionDemo.condition.signal();
            Thread.sleep(1000);
            log.info("主程序释放完成");
        }finally {
            LockConditionDemo.lock.unlock();
        }
        log.info("主程序unlock");
    }
}

@Slf4j
class ConditionDemoThread extends Thread{
    @Override
    public void run() {
        LockConditionDemo.lock.lock();
        log.info("线程lock ");
        try{
            log.info("线程开始等待-Condition.await()");
            LockConditionDemo.condition.await();
            log.info("线程恢复");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LockConditionDemo.lock.unlock();
            log.info("线程unlock ");
        }
        try {
            Thread.sleep(1000);
            log.info("线程结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}