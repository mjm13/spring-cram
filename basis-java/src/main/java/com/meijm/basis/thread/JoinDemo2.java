package com.meijm.basis.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * join 等待该线程执行完成
 * t.join() 当前线程等待t线程执行完成之后再执行当前线程
 * join(millis) 当前线程等待指定毫秒数,同wait
 */
@Slf4j
public class JoinDemo2 {
    public static void main(String[] args) {
        Thread a = new Thread(() -> {
            try {
                Thread.sleep(1000L);
                log.info("1");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread b = new Thread(() -> {
            try {
                Thread.sleep(1000L);
                a.join();
                log.info("2");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread c = new Thread(() -> {
            try {
                Thread.sleep(1000L);
                b.join();
                log.info("3");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        a.start();
        b.start();
        c.start();
        log.info("当前运行线程数：{}",Thread.activeCount());
    }
}
