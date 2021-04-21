package com.meijm.thread.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试interrupt 效果
 * interrupt() 外部程序设置线程为终止状态
 * 线程本身通过this.isInterrupted() 获取状态变化,判断是否需要结束
 */
@Slf4j
public class InterruptThread extends Thread {
    @Override
    public void run() {
        while (true) {
            if (this.isInterrupted()) {
                log.info("退出循环");
                break;
            }
            log.info("循环中");
        }
        log.info("循环结束");
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new InterruptThread();
        log.info("开始执行程序");
        t1.start();
        Thread.sleep(100);
        log.info("标识interrupt为true");
        t1.interrupt();
        log.info("程序执行完成");
    }
}