package com.meijm.thread.demo;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;
import org.springframework.shell.standard.ShellComponent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 示例展示ThreadLocal,InheritableThreadLocal,TransmittableThreadLocal
 * 跨线程访问时的访问效果
 * ThreadLocal 无法跨线程传值
 * InheritableThreadLocal  子线程修改后其它线程会取修改后的值而不是父线程的值,修改不会影响父线程值
 * TransmittableThreadLocal 子线程修改不影响后续线程访问,修改不会影响父线程值
 */
@ShellComponent
public class ThreadLocalDemo {
    private static ThreadLocal<String> tl = new ThreadLocal();
    private static ThreadLocal<String> ttl = new TransmittableThreadLocal<>();
    private static ThreadLocal<String> itl = new InheritableThreadLocal();
    private static AtomicInteger index = new AtomicInteger(1);


    private static ExecutorService executorService = Executors.newFixedThreadPool(1);

    //InheritableThreadLocal 跨线程传递问题
    public static void main(String[] args) {
        System.out.println(String.format("线程名称-%s, 变量值=%s", Thread.currentThread().getName(), itl.get()));

        executorService.execute(()->{
            System.out.println(String.format("线程名称-%s, 变量值=%s", Thread.currentThread().getName(), itl.get()));
        });

        itl.set("1"); // 等上面的线程池第一次启用完了，父线程再给自己赋值

        executorService.execute(()->{
            System.out.println(String.format("线程名称-%s, 变量值=%s", Thread.currentThread().getName(), itl.get()));
        });

        System.out.println(String.format("线程名称-%s, 变量值=%s", Thread.currentThread().getName(), itl.get()));
    }

//    public static void main(String[] args) throws Exception {
//        setLocal("主线程初始化");
//        fc(index);
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        Runnable runnable = TtlRunnable.get(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("-----------------开始-------------------");
//                fc(index);
//                setLocal("子线程修改后");
//                fc(index);
//                System.out.println("------------------结束----------------------");
//            }
//        });
//        TimeUnit.SECONDS.sleep(1);
//        executorService.submit(runnable);
//        TimeUnit.SECONDS.sleep(1);
//        System.out.println("****************************************");
//        fc(index);
//        System.out.println("****************************************");
//        executorService.submit(runnable);
//        TimeUnit.SECONDS.sleep(1);
//    }

    private static void setLocal(String val) {
        tl.set(val);
        ttl.set(val);
        itl.set(val);
    }

    private static void fc(AtomicInteger index) {
        String temp = "%s-当前线程名称: %s,%s:%s|%s:%s|%s:%s ";
        System.out.println(String.format(temp, index.toString(), Thread.currentThread().getName(), "ThreadLocal", tl.get(), "TransmittableThreadLocal", ttl.get(), "InheritableThreadLocal", itl.get()));
        index.getAndIncrement();
    }
}