package com.meijm.basis.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
/**
 * 测试condition功能
 * Condition.await:加入等待队列，并释放锁
 * Condition.signal/Condition.await()
 *
 * Object.notify()/Object.wait()
 */
@Slf4j
public class LockConditionDemo {
    private final ReentrantLock lock = new ReentrantLock();
    // 队列不满的条件
    private final Condition notFull = lock.newCondition();
    // 队列不空的条件
    private final Condition notEmpty = lock.newCondition();
    // 缓冲队列
    private Queue<String> queue = new LinkedList<>();
    // 队列容量
    private int capacity = 5;

    public void put(String element) throws InterruptedException {
        lock.lock();
        try {
            // 当队列满时，等待不满条件
            while (queue.size() >= capacity) {
                log.info("队列已满，生产者线程等待");
                notFull.await();
            }
            queue.offer(element);
            log.info("生产元素: {}, 当前队列大小: {}", element, queue.size());
            // 通知消费者可以消费了
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public String take() throws InterruptedException {
        lock.lock();
        try {
            // 当队列空时，等待不空条件
            while (queue.isEmpty()) {
                log.info("队列为空，消费者线程等待");
                notEmpty.await();
            }
            String item = queue.poll();
            log.info("消费元素: {}, 当前队列大小: {}", item, queue.size());
            // 通知生产者可以生产了
            notFull.signal();
            return item;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        LockConditionDemo buffer = new LockConditionDemo();

        // 创建生产者线程
        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    buffer.put("Item " + i);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // 创建消费者线程
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    buffer.take();
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
    }
}