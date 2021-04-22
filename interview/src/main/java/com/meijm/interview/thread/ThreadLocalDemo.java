package com.meijm.interview.thread;

import cn.hutool.core.util.StrUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 示例展示ThreadLocal,InheritableThreadLocal,TransmittableThreadLocal
 * 跨线程访问时的访问效果
 * ThreadLocal 无法跨线程传值
 * InheritableThreadLocal  子线程修改后其它线程会取修改后的值而不是父线程的值,修改不会影响父线程值
 * TransmittableThreadLocal 阿里开源--子线程修改不影响后续线程访问,修改不会影响父线程值
 */
@Slf4j
public class ThreadLocalDemo {
    private static ThreadLocal<String> tl = new ThreadLocal();
    private static ThreadLocal<String> ttl = new TransmittableThreadLocal<>();
    private static ThreadLocal<String> itl = new InheritableThreadLocal();

    //InheritableThreadLocal 跨线程传递问题
    public static void main(String[] args) throws InterruptedException {
        log.info("开始执行");
        setLocal("0");
        fc("初始为0");

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        CountDownLatch latch = new CountDownLatch(1);
        executorService.submit(TtlRunnable.get(() -> {
            fc("子线程修改前为0");
            setLocal("1");
            fc("子线程修改为1");
            latch.countDown();
        }));

        latch.await();

        CountDownLatch latch2 = new CountDownLatch(1);
        executorService.submit(TtlRunnable.get(() -> {
            fc("其它线程查看");
            latch2.countDown();
        }));
        latch2.await();

        fc("主线程修改前1");
        setLocal("2");
        fc("主线程修改为2");

        CountDownLatch latch3 = new CountDownLatch(1);
        executorService.submit(TtlRunnable.get(() -> {
            fc("其它线程查看");
            latch3.countDown();
        }));
        latch3.await();
        log.info("执行结束");
    }

    private static void checkOver(ExecutorService threadPool) {
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException ex) {
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private static void setLocal(String val) {
        tl.set(val);
        ttl.set(val);
        itl.set(val);
    }

    private static void fc(String msg) {
        String temp = "{}ThreadLocal:{} | TransmittableThreadLocal:{} |InheritableThreadLocal:{} ";
        msg = StrUtil.padAfter(msg,13," ");
        String tlValue = StrUtil.padAfter(tl.get(),4," ");
        String ttlValue = StrUtil.padAfter(ttl.get(),4," ");
        String itlValue = StrUtil.padAfter(itl.get(),4," ");
        log.info(temp, msg, tlValue, ttlValue, itlValue);
    }
}