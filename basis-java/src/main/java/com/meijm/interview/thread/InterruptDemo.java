package com.meijm.interview.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * interrupt() : 标识此线程为终止状态,配合isInterrupted()使用
 * interrupted() :重制中断状态，原有线程为中断返回true，原有线程不是中断返回false
 * :isInterrupted() : 获取线程终止状态,线程中可通过此状态判断是否需要终止线程
 */
@Slf4j
public class InterruptDemo {
    public static void main(String[] args) throws InterruptedException {
        //线程通过interrupt状态关闭线程
//        InterrupThread.demo();
        //主程序通过interrupt 终止wait线程并报错
//        InterrupWaitThread.demo();
        // interrupted为Thread的静态方法,操作的对象为当前线程而不是线程实例
        InterrupedThread.demo();

    }
}

@Slf4j
class InterrupThread extends Thread {
    @Override
    public void run() {
        log.info("依据interrupt状态判断是否结束");
        while (true) {
            if (this.isInterrupted()) {
                log.info("退出循环");
                break;
            }
        }
        log.info("循环结束");
    }

    public static void demo() throws InterruptedException {
        Thread t1 = new InterrupThread();
        log.info("开始执行程序");
        t1.start();
        Thread.sleep(1000);
        log.info("标识interrupt为true");
        t1.interrupt();
        log.info("程序执行完成");
    }
}

@Slf4j
class InterrupWaitThread extends Thread {
    @Override
    public void run() {
        log.info("开始等待");
        synchronized (this) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.info("线程-结束执行");
    }

    public static void demo() throws InterruptedException {
        Thread t1 = new InterrupWaitThread();
        log.info("主程序-开始执行");
        t1.start();
        Thread.sleep(1000);
        log.info("标识interrupt为true");
        t1.interrupt();
        log.info("主程序-结束执行");
    }
}

@Slf4j
class InterrupedThread{
    public static void demo() throws InterruptedException {
        log.info("当前线程终止状态isInterrupted:{}",Thread.currentThread().isInterrupted());
        log.info("调用interrupt,设置当前线程为终止状态");
        Thread.currentThread().interrupt();
        log.info("当前线程终止状态isInterrupted:{}",Thread.currentThread().isInterrupted());
        log.info("第一次调用Thread.interrupted(),返回值：{}",Thread.interrupted());
        log.info("当前线程终止状态isInterrupted:{}",Thread.currentThread().isInterrupted());
        log.info("第二次调用Thread.interrupted(),返回值：{}", Thread.interrupted());
        log.info("当前线程终止状态isInterrupted:{}",Thread.currentThread().isInterrupted());
        log.info("=================end===============================");
    }
}