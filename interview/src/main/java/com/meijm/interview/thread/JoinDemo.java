package com.meijm.interview.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * join 等待该线程执行完成
 * t.join() 当前线程等待t线程执行完成之后再执行当前线程
 * join(millis) 当前线程等待指定毫秒数,同wait
 */
@Slf4j
public class JoinDemo {
    public static void main(String[] args) {
        Thread previousThread = null;
        log.info("join-开始执行");
        for (int i = 0; i < 10; i++) {
            JoinThread joinDemo = new JoinThread(previousThread, i);
            joinDemo.start();
            previousThread = joinDemo;
        }
        while (Thread.activeCount() > 2) {
        }
        log.info("join-结束执行");
    }
}

@Slf4j
class JoinThread extends Thread {
    int i;
    Thread previousThread; //上一个线程

    public JoinThread(Thread previousThread, int i) {
        this.previousThread = previousThread;
        this.i = i;
    }

    @Override
    public void run() {
        try {
            if (previousThread != null) {
                previousThread.join();
                Thread.yield();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("执行线程{}", i);
    }

}