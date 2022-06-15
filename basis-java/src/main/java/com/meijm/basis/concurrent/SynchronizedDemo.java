package com.meijm.basis.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

@Slf4j
public class SynchronizedDemo {

    public static void main(String[] args) {
        SynchronizedDemo demo = new SynchronizedDemo();
        SynchronizedDemo demo1 = new SynchronizedDemo();
        SynchronizedDemo demo2 = new SynchronizedDemo();
        new Thread(() -> {
            demo.lockMethod();
        }).start();
        new Thread(() -> {
            demo.lockInstance(demo1);
        }).start();
        new Thread(() -> {
            SynchronizedDemo.lockStaticMethod();
        }).start();
        log.info("打印demo头信息");
        log.info(ClassLayout.parseInstance(demo).toPrintable());
        log.info("打印demo1头信息");
        log.info(ClassLayout.parseInstance(demo1).toPrintable());
        log.info("打印demo2头信息");
        log.info(ClassLayout.parseInstance(demo2).toPrintable());
        log.info("打印Class头信息");
        log.info(ClassLayout.parseInstance(SynchronizedDemo.class).toPrintable());
    }

    public synchronized static void lockStaticMethod() {
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void lockMethod() {
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void lockInstance(SynchronizedDemo demo) {
        synchronized (demo) {
            try {
                Thread.sleep(1000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
