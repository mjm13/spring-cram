package com.meijm.basis.javase;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
        Map<Integer, String> map = new ConcurrentHashMap<>();
//        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i <50000 ; i++) {
            int finalI = i;
            new Thread(() -> {
                map.put(finalI, finalI +"v");
            }).start();
        }
        for (int i = 0; i <50000 ; i++) {
            if (map.get(i)==null) {
                System.out.println(i);
            }
        }

        System.out.println(map.size());
    }
}
