package com.meijm.interview.aqs;

import lombok.extern.slf4j.Slf4j;
/**
 * 当两个线程持有的锁为对放目标锁时发生死锁
 */
@Slf4j
public class DeadLockDemo {
    /**
     * PowerShell
     *   .\jps.exe   查看进程编号
     *   .\jstack.exe 进程编号  查看状态
     *    Found one Java-level deadlock:  查看到死锁
     * @param args
     */
    public static void main(String[] args) {
        Thread lockA =new LockAThread();
        Thread lockB =new LockBThread();
        log.info("程序开始");
        lockA.start();
        lockB.start();
        log.info("程序结束");
    }
}

@Slf4j
class LockAThread extends Thread {
    @Override
    public void run() {
        log.info("运行中,开始获取LockA锁");
        try {
            synchronized (LockAThread.class) {
                log.info("获取LockA锁成功开始休眠");
                Thread.sleep(1000);
                log.info("休眠完成,开始获取LockB锁");
                synchronized (LockBThread.class) {
                    log.info("获取LockB锁成功");
                }
            }
        } catch (InterruptedException e) {
            log.error("运行报错", e);
        }
        log.info("运行完成");
    }
}
@Slf4j
class LockBThread extends Thread {
    @Override
    public void run() {
        log.info("运行中,开始获取LockB锁");
        try {
            synchronized (LockBThread.class) {
                log.info("获取LockB锁成功开始休眠");
                Thread.sleep(1000);
                log.info("休眠完成,开始获取LockA锁");
                synchronized (LockAThread.class) {
                    log.info("获取LockA锁成功");
                }
            }
        } catch (InterruptedException e) {
            log.error("运行报错", e);
        }
        log.info("运行完成");
    }
}