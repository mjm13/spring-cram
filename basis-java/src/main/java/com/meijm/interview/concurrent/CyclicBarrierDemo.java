package com.meijm.interview.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * CyclicBarrier 可循环利用的屏障
 *  类似于CountDownLatch 区别是CyclicBarrier是在线程中等待达到条件
 *  内部使用ReentrantLock实现
 */
@Slf4j
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, ()->{
            log.info("会议中");
            log.info("会议结束");
        });
        for (int i = 0; i <5 ; i++) {
            new CyclicBarrierThread(cyclicBarrier,"人员"+i).start();
        }
    }
}

@Slf4j
class CyclicBarrierThread extends Thread {
    private CyclicBarrier cyclicBarrier;

    public CyclicBarrierThread(CyclicBarrier cyclicBarrier, String name) {
        super(name);
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        try {
            int random = (int) (Math.random()*10);
            log.info("等待其它人员进入会议1");
            TimeUnit.SECONDS.sleep(random);
            cyclicBarrier.await();
            TimeUnit.SECONDS.sleep(random);
            log.info("等待其它人员进入会议2");
            TimeUnit.SECONDS.sleep(random);
            cyclicBarrier.await();
            log.info("离场");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
