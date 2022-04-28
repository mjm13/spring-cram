package com.meijm.interview.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 *  ReentrantReadWriteLock: 读写锁
 *  内部使用一个aqs 用位运算符左移16位代表共享锁--读锁,
 * 不位移的时候代表独占锁--写锁
 */
@Slf4j
public class ReadWriteLockDemo {
    volatile static StringBuilder book = new StringBuilder();
    public static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 100; i++) {
            if (i % 10 == 0) {
                service.execute(new WriteRunnable("线程" + i));
            } else {
                service.execute(new ReadRunnable("线程" + i));
            }
        }
    }
}

@Slf4j
class ReadRunnable implements Runnable {
    private String name;

    public ReadRunnable(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        Lock readLock = ReadWriteLockDemo.readWriteLock.readLock();
        readLock.lock();
        try {
            TimeUnit.SECONDS.sleep((long) Math.random() * 10);
            log.info("{} 阅读到内容：{}", name, ReadWriteLockDemo.book);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readLock.unlock();
        }
    }
}

@Slf4j
class WriteRunnable implements Runnable {
    private String name;

    public WriteRunnable(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        Lock writeLock = ReadWriteLockDemo.readWriteLock.writeLock();
        writeLock.lock();
        try {
            int random = (int) (Math.random() * 10);
            TimeUnit.SECONDS.sleep(random);
            log.info("{},开始写之前:{}", name, ReadWriteLockDemo.book);
            ReadWriteLockDemo.book.append(random);
            log.info("{},开始写之后:{}", name, ReadWriteLockDemo.book);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }
}
