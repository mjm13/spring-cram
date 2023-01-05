package com.meijm.basis.javase;

import cn.hutool.core.collection.BoundedPriorityQueue;
import cn.hutool.core.comparator.CompareUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

@Slf4j
public class PriorityQueueDemo {
    public static void main(String[] args) {
//        Queue<Integer> boundedPriorityQueue = new BoundedPriorityQueue<>(3, new Comparator<Integer>() {
//            @Override
//            public int compare(Integer o1, Integer o2) {
//                return o1.compareTo(o2);
//            }
//        });
//        boundedPriorityQueue.add(10);
//        boundedPriorityQueue.add(20);
//        boundedPriorityQueue.add(30);
//        boundedPriorityQueue.add(40);
//        System.out.println(boundedPriorityQueue);
//        boundedPriorityQueue.add(33);
//        boundedPriorityQueue.add(35);
//        System.out.println(boundedPriorityQueue);



        BoundedPriorityQueue<Test> queue = new BoundedPriorityQueue<Test>(2, new Comparator<Test>() {
            @Override
            public int compare(Test o1, Test o2) {
                return o1.compareTo(o2);
            }
        });
        Test t1 = new Test(10, "t1");
        Test t2 = new Test(20, "t2");
        Test t3 = new Test(30, "t3");
        Test t4 = new Test(40, "t4");
        Test t5 = new Test(33, "t5");
        Test t6 = new Test(35, "t6");
        queue.add(t1);
        queue.add(t2);
        queue.add(t3);
        queue.add(t4);
        System.out.println(queue);
        queue.add(t5);
        queue.add(t6);
        System.out.println(queue);
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
        return CompareUtil.compare(priority,o.getPriority() );
    }
}
