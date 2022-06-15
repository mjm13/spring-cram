package com.meijm.basis.javase;

import java.util.ArrayList;
import java.util.List;

public class ArrayListDemo {
    public static void main(String[] args) {

        // ArrayList.DEFAULT_CAPACITY 默认初始容量10
        List<String> temp = new ArrayList<>(2);
        temp.add("1");
        temp.add("2");
        //java.util.ArrayList.grow  翻倍扩容
        temp.add("3");

    }
}
