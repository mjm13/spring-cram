package com.meijm.basis.javase;

import cn.hutool.json.JSONUtil;

public class ArraysDemo {
    public static void main(String[] args) {
        int[] array = new int[] { 42, 2, 0, 3, 4, 0 };
        int[] result = new int[array.length];
        int idx = 0;
        for (int n : array) {
            if (n != 0) {
                result[idx++] = n;
            }
        }
        System.out.println(JSONUtil.toJsonStr(result));
    }
}
