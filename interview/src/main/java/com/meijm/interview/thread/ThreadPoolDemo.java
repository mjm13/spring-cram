package com.meijm.interview.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class ThreadPoolDemo {
    public static void main(String[] args) {
        ExecutorService service = Executors.newSingleThreadExecutor();
//        ArrayBlockingQueue a = new ArrayBlockingQueue();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            service.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("i:" + finalI);
                }
            });
        }
    }
}

@Slf4j
class SynchronousQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        PriorityBlockingQueue<String> a = new PriorityBlockingQueue<>();
        a.put("aaaaa");
        a.take();
        ArrayBlockingQueue<String> b = new ArrayBlockingQueue<>(1);
        LinkedBlockingQueue<String> c = new LinkedBlockingQueue<>(1);
        final SynchronousQueue<Integer> queue = new SynchronousQueue<Integer>(false);
        ExecutorService service = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(20);
        for (int i = 0; i < 20; i++) {
            int finalI = i;
            if (i % 2 == 0) {
                service.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            queue.put(finalI);
                            log.info("生产元素i:{}-结果:", finalI);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        latch.countDown();
                    }
                });
            } else {
                service.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            log.info("消费元素i:{}", queue.take());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        latch.countDown();
                    }
                });
            }
        }
        latch.await();

        log.info("程序执行结束:queue.size()={}", queue.size());
    }
}