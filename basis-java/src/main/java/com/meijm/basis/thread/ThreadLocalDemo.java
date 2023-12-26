package com.meijm.basis.thread;

import cn.hutool.core.util.StrUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 示例展示ThreadLocal,InheritableThreadLocal,TransmittableThreadLocal
 * 跨线程访问时的访问效果
 * ThreadLocal 无法跨线程传值
 * InheritableThreadLocal
 *  1.子线程修改后其它线程会取修改后的值而不是父线程的值,修改不会影响父线程值
 *  2.父线程再次修改也不会改变子线程中修改的值------------会导致赋值逻辑不清
 * TransmittableThreadLocal 阿里开源--需配置java启动参数或使用指定API生产run接口
 * 1.子线程修改不影响后续线程访问,修改不会影响父线程值
 * 2.父线程再次修改会影响子新开线程中内容
 */
@Slf4j
public class ThreadLocalDemo {
    private static ThreadLocal<String> tl = new ThreadLocal();
    private static ThreadLocal<String> ttl = new TransmittableThreadLocal<>();
    private static ThreadLocal<String> itl = new InheritableThreadLocal();

    //InheritableThreadLocal 跨线程传递问题
    public static void main(String[] args) throws InterruptedException {
        /**
         * 各种线程变量传递情况
         */
        ThreadLocalDemo.demo();
    }

    public static void demo() throws InterruptedException {
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
//        executorService.submit(() -> {
//            fc("子线程修改前为0");
//            setLocal("1");
//            fc("子线程修改为1");
//            latch.countDown();
//        });

        latch.await();

        CountDownLatch latch2 = new CountDownLatch(1);
        executorService.submit(TtlRunnable.get(() -> {
            fc("差异");
            latch2.countDown();
        }));
//        executorService.submit(() -> {
//            fc("其它线程查看");
//            latch2.countDown();
//        });
        latch2.await();

        fc("主线程修改前0");
        setLocal("2");
        fc("主线程修改为2");

        CountDownLatch latch3 = new CountDownLatch(1);
        executorService.submit(TtlRunnable.get(() -> {
            fc("差异");
            latch3.countDown();
        }));
//        executorService.submit(() -> {
//            fc("其它线程查看");
//            latch3.countDown();
//        });
        latch3.await();
        log.info("执行结束");
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