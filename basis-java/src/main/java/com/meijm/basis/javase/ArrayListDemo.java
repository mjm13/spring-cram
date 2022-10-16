package com.meijm.basis.javase;

import com.meijm.basis.test.InnerTest;

import java.util.ArrayList;
import java.util.List;

public class ArrayListDemo {
    public static void main(String[] args) {
        List<InnerTest.InnerMeiJM> ar = new ArrayList<>();

        List<Integer> arr = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            arr.add(i);
        }
        try {
            throw new RuntimeException("test exception");
        }catch (Exception e){
            e.printStackTrace();
        }
        for (Integer integer : arr) {
            arr.remove(integer);
        }
        System.out.println("over");
    }
}
