package com.meijm.interview.javase;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
@Slf4j
public class ComparatorDemo {
    public static void main(String[] args) {
        Map<String,Integer> m1 = new HashMap<>();
        m1.put("m1",1);
        Map<String,Integer> m2 = new HashMap<>();
        m2.put("m1",3);
        Comparator<Map<String,Integer>> com  = Comparator.comparing(o -> o.get("m1"),(o1, o2) -> {
            log.info("o1:{}",o1);
            log.info("o2:{}",o2);
            return o2-o1;
        });
        List<Map<String,Integer>> arr = new ArrayList<>();
        arr.add(m1);
        arr.add(m2);
        log.info(arr.toString());
        arr.sort(com);
        log.info(arr.toString());
    }
}
