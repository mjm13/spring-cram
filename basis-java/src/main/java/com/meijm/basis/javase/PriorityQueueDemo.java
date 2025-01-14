package com.meijm.basis.javase;

import cn.hutool.core.collection.BoundedPriorityQueue;
import cn.hutool.core.comparator.CompareUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
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

        List<Test> list = new ArrayList<>();
        list.add(t1);
        list.add(t2);
        list.add(t3);
        list.add(t4);
        list.add(t5);
        list.add(t6);
        log.info("排序输出------------------");
        list.stream().sorted().forEach(test -> log.info("test:{}", test));
        log.info("排序输出------------------");
        
        PriorityQueue<Test> priorityQueue = new PriorityQueue<Test>();
        priorityQueue.add(t1);
        priorityQueue.add(t2);
        priorityQueue.add(t3);
        priorityQueue.add(t4);
        priorityQueue.add(t5);
        priorityQueue.add(t6);
        log.info("优先级队列顶端内容:{}", priorityQueue.peek());
        log.info("优先级队列内容:{}", priorityQueue);
        log.info("优先级队列顶端内容:{}", priorityQueue.poll());
        log.info("优先级队列内容:{}", priorityQueue);

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

/**
 * 优先级队列是越小越优先
 * 大小的判断取决于Comparable的实现，
 * CompareUtil.compare(1,2)  1<2  结果为-1
 */
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
        return CompareUtil.compare(o.getPriority(),priority );
    }
}
