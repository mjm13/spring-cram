package com.meijm.interview.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * interrupt 中断此线程
 * interrupt() 外部程序设置线程为终止状态
 * 线程本身通过this.isInterrupted() 获取状态变化,判断是否需要结束
 */
@Slf4j
public class InterruptDemo {
    public static void main(String[] args) throws InterruptedException {
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
}