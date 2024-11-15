package com.meijm.basis.concurrent;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueExample {

    // 使用ConcurrentLinkedQueue作为共享队列
    private static ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();

    // 生产者线程
    private static class Producer implements Runnable {
        private String name;
        public Producer(String name){
            this.name = name;
        }
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                String item = name+"-Item " + i;
                queue.offer(item); // 添加数据到队列
                System.out.println("Produced: " + item);
                try {
                    Thread.sleep(100); // 模拟生产时间
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    // 消费者线程
    private static class Consumer implements Runnable {
        @Override
        public void run() {
            while (true) {
                String item = queue.poll(); // 从队列中取数据
                if (item != null) {
                    System.out.println("Consumed: " + item);
                } else {
                    // 如果队列为空，消费者可以选择等待或做其他事情
                    try {
                        Thread.sleep(100); // 避免忙等
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread producerThread1 = new Thread(new Producer("aaa"));
        Thread producerThread2 = new Thread(new Producer("bbb"));
        Thread consumerThread = new Thread(new Consumer());

        producerThread1.start();
        producerThread2.start();
        consumerThread.start();
    }
}