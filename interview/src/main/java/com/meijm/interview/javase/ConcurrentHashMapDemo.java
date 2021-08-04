package com.meijm.interview.javase;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ConcurrentHashMap
 *
 *  1.7中 使用Segment 分段锁 来保障线程安全
 *  1.8中 使用synchronized +CAS 来控制
 *  synchronized：在添加时锁定Node对象
 *  通过 SIZECTL 属性来控制扩容
 */
public class ConcurrentHashMapDemo {
    public static void main(String[] args) {

        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("xxx","bbb");
    }
}
