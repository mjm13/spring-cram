package com.meijm.basis.concurrent;

import com.google.common.util.concurrent.Striped;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Lock;

/**
 * @Description 测试Guava细粒度锁
 * Striped 原理：
 * 通过参数生成N个锁
 * 使用时通过key计算hash生成下标取生成的锁，所以可能会造成死锁
 * @Author MeiJM
 * @Date 2022/4/28
 **/
@Slf4j
public class GuavaStripedDemo {
    public static final Striped<Lock> striped = Striped.lazyWeakLock(100);

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> testStripedLock(1));
        Thread thread2 = new Thread(() -> testStripedLock(1));
        Thread thread3 = new Thread(() -> testStripedLock(2));
        thread1.start();
        thread2.start();
        thread3.start();
    }

    public static void testStripedLock(Object key) {
        Lock lock = striped.get(key);
        lock.lock();
        try {
            Thread.sleep(2000);
            log.info("测试上锁 key：{}", key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

