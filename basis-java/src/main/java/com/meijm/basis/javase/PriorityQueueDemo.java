package com.meijm.basis.javase;

import cn.hutool.core.collection.BoundedPriorityQueue;
import cn.hutool.core.comparator.CompareUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.PriorityQueue;

@Slf4j
public class PriorityQueueDemo {
    public static void main(String[] args) {
//        int k = 100;
//        while (k > 0) {
//            int parent = (k - 1) >>> 1;
//            k = parent;
//            log.info("k:{}",k);
//        }
//        int half = 100 >>> 1;        // loop while a non-leaf
//        int k = 0;
//        while (k < half) {
//            int child = (k << 1) + 1; // assume left child is least
//            k = child;
//            log.info("k:{}", k);
//        }
//        if (true) {
//            return;
//        }
        Test t1 = new Test(10, "t1");
        Test t2 = new Test(20, "t2");
        Test t3 = new Test(30, "t3");
        Test t4 = new Test(40, "t4");
        Test t5 = new Test(50, "t5");
        Test t6 = new Test(45, "t6");

        PriorityQueue<Test> queue = new PriorityQueue<Test>();
        queue.add(t1);
        queue.add(t2);
        queue.add(t3);
        queue.add(t4);
        queue.add(t5);
        queue.add(t6);
        log.info("优先级队列顶端内容:{}", queue.peek());
        log.info("优先级队列内容:{}", queue);
        log.info("优先级队列顶端内容:{}", queue.poll());
        log.info("优先级队列内容:{}", queue);

        BoundedPriorityQueue<Test> boundedQueue = new BoundedPriorityQueue<Test>(3);
        boundedQueue.add(t1);
        boundedQueue.add(t2);
        boundedQueue.add(t3);
        boundedQueue.add(t4);
        log.info("有限优先级队列内容：{}", boundedQueue);
        boundedQueue.add(t5);
        boundedQueue.add(t6);
        log.info("有限优先级队列内容：{}", boundedQueue);
    }
}

@Data
class Test implements Comparable<Test> {
    private int priority;
    private String name;

    public Test(int priority, String name) {
        this.priority = priority;
        this.name = name;
    }

    @Override
    public int compareTo(Test o) {
        return CompareUtil.compare(o.getPriority(), priority);
    }
}
