package com.meijm.basis.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * join 等待该线程执行完成
 * t.join() 当前线程等待t线程执行完成之后再执行当前线程
 * join(millis) 当前线程等待指定毫秒数,同wait
 */
@Slf4j
public class JoinDemo {
    public static void main(String[] args) {
        SequentialExecThread previousThread = null;
        log.info("join-开始执行");
        for (int i = 0; i < 10; i++) {
            SequentialExecThread joinDemo = new SequentialExecThread(previousThread, i);
            joinDemo.start();
            previousThread = joinDemo;
        }
        while (Thread.activeCount() != 1) {
        }
        log.info("join-结束执行");
    }
}

@Slf4j
class SequentialExecThread extends Thread {
    private int i;
    private SequentialExecThread previousThread; //上一个线程

    public SequentialExecThread(SequentialExecThread previousThread, int i) {
        this.previousThread = previousThread;
        this.i = i;
    }

    @Override
    public void run() {
        try {
            if (previousThread != null) {
                previousThread.joinBySelfByThis();
//                previousThread.joinBySelf();
//                previousThread.joinBySelfByObject();
            }
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("执行线程{}", i);
    }

    public final synchronized void joinBySelf()
            throws InterruptedException {
        while (isAlive()) {
            log.info("等待执行-开始");
            wait(0);
            log.info("等待执行-结束");
        }
    }

    public final void joinBySelfByObject()
            throws InterruptedException {
        synchronized (Object.class) {
            while (isAlive()) {
                log.info("等待执行-开始");
                Object.class.wait(0);
                log.info("等待执行-结束");
            }
        }
    }

    public final void joinBySelfByThis()
            throws InterruptedException {
        synchronized (this) {
            while (isAlive()) {
                log.info("等待执行-开始");
                this.wait(0);
                log.info("等待执行-结束");
            }
        }
    }
}