package com.meijm.basis.javase;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

/**
 * 虚引用示例
 */
public class ReferenceDemo {
    public static void main(String[] args) throws InterruptedException {
        // 创建虚引用队列
        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        Runtime runtime = Runtime.getRuntime();
        // 关联对象
        String o = ReferenceDemo.class.toString();

        // 创建虚引用
        PhantomReference<Object> phantomReference = new PhantomReference<>(o, queue);

        // 清除强引用，使对象可以被垃圾回收
        o = null;
        // 不断检查虚引用队列，直到对象被回收
        Reference<?> poll = null;
        while ((poll = queue.poll()) == null) {
            System.out.println("对象尚未被回收...");
//            System.out.println(runtime.freeMemory());
            Thread.sleep(500); // 每隔 500 毫秒检查一次
            System.gc();       // 再次请求垃圾回收
        }
//        System.out.println(runtime.freeMemory());
        System.out.println("对象已被回收，虚引用：" + poll);
    }
}
