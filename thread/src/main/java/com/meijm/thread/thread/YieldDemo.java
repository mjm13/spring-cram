package com.meijm.thread.thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class YieldThread extends Thread{

    public YieldThread(String name){
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            log.info("线程{}执行至第{}次",this.getName(),i);
            if (i == 5) {
                Thread.yield();
            }
        }
    }

    public static void main(String[] args) {
        YieldThread t1 = new YieldThread("t1");
        YieldThread t2 = new YieldThread("t2");
        t1.start();
        t2.start();
    }
}