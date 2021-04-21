package com.meijm.interview.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * notify,wait 为object方法.
 * 使用时需被synchronized包裹
 * synchronized锁的对象和调用方法的对象需要是同一个
 * synchronized写在方法上效果同synchronized(this)
 */
@Slf4j
public class WaitNotifyDemo{

    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        List<WaitNotifyThread> nds = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            WaitNotifyThread notifyDemo = new WaitNotifyThread("WaitNotifyThread-" + i, lock);
            notifyDemo.start();
            nds.add(notifyDemo);
        }
        Thread.sleep(1000);
            //批量唤醒
//        synchronized (lock) {
//            log.info("开始唤醒nde");
//            lock.notifyAll();
//        }

        //逐个唤醒
        // 在idea中运行除了main进程 还有Monitor Ctrl-Break所以需要判断activeCount>2
        while (Thread.activeCount() > 2) {
            synchronized (lock) {
                lock.notify();
                Thread.sleep(1000);
            }
        }
        Thread.currentThread().getThreadGroup().list();
        log.info("主程序结束,Thread.activeCount():{}",Thread.activeCount());
    }
}

@Slf4j
class WaitNotifyThread extends Thread {

    private Object lock;

    public WaitNotifyThread(String name, Object lock) {
        super(name);
        this.lock = lock;
    }

    @Override
    public void run() {
        log.info("开始wait");
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.info("结束wait");
    }
}