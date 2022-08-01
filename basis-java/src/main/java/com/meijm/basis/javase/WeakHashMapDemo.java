package com.meijm.basis.javase;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class WeakHashMapDemo {
    public static void main(String[] args) {
        HashMap hashMap = new HashMap();

        WeakHashMap weakHashMap = new WeakHashMap();

        String keyHashMap = new String("keyHashMap");
        String keyWeakHashMap = new String("keyWeakHashMap");
        WeakHashMapDemo key = new WeakHashMapDemo();
        hashMap.put(keyHashMap, "Ankita");
        weakHashMap.put(keyWeakHashMap, "Atul");

        System.out.println("NO GC: hashMap:" + hashMap.get("keyHashMap") + " and weakHashMap:"+weakHashMap.get("keyWeakHashMap"));
        System.gc();
        System.out.println("1st GC: hashMap:" + hashMap.get("keyHashMap") + " and weakHashMap:"+weakHashMap.get("keyWeakHashMap"));

        keyHashMap = null;
        keyWeakHashMap = null;

        System.gc();
        System.out.println("2st GC: hashMap:" + hashMap.get("keyHashMap") + " and weakHashMap:"+weakHashMap.get("keyWeakHashMap"));
    }
}
