package com.prolog.seataDemo1;

import cn.hutool.http.HttpUtil;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

/**
 * 问题记录
 * 1.AT模式为异步提交，存在脏读风险，需额外增加分布式锁才能避免脏读
 * 2.
 */
@Log4j2
public class SeataDemoTest {
    private static String BASE_URL = "http://localhost:9090";
    private static String UPDATE_DEMO_SLEEP_2 = "/demo1/updateDemoSleep2";
    private static String UPDATE_DEMO_SLEEP_5 = "/demo1/updateDemoSleep5";

    private static String UPDATE_MANY_NORMAL_SLEEP_2 = "/demo1/updateManyNormalSleep2";
    private static String UPDATE_MANY_ENDING_EXCEPTION_SLEEP_5 = "/demo1/updateManyEndingExceptionSleep5";



    private static String UPDATE_DEMO_ERROR = "/demo1/updateDemoError";
    private static String UPDATE_MANY_ENDING_EXCEPTION = "/demo1/updateManyEndingException";
    private static String UPDATE_MANY_NORMAL = "/demo1/updateManyNormal";
    private static String UPDATE_MANY_SERVICE_EXCEPTION = "/demo1/updateManyServiceException";
    private static String UPDATE_DEMO_WITH_OUT_TRANSACTION = "/demo1/updateDemoWithOutTransaction";


    /**
     * 测试多服务事务回滚
     * seata AT模式 读未提交
     */
    @Test
    public void testManyService(){
        try {
            CountDownLatch downLatch = new CountDownLatch(2);
            new Thread(() -> {
                System.out.println("begin UPDATE_MANY_ENDING_EXCEPTION_SLEEP_5-----");
                HttpUtil.get(BASE_URL + UPDATE_MANY_ENDING_EXCEPTION_SLEEP_5);
                System.out.println("end UPDATE_MANY_ENDING_EXCEPTION_SLEEP_5-----");
                downLatch.countDown();
            }).start();
            Thread.sleep(100);
            new Thread(() -> {
                System.out.println("begin UPDATE_MANY_NORMAL_SLEEP_2-----");
                HttpUtil.get(BASE_URL + UPDATE_MANY_NORMAL_SLEEP_2);
                System.out.println("end UPDATE_MANY_NORMAL_SLEEP_2-----");
                downLatch.countDown();
            }).start();
//            Thread.sleep(100);
//            new Thread(() -> {
//                System.out.println("begin UPDATE_MANY_NORMAL_SLEEP_2-----1");
//                HttpUtil.get(BASE_URL + UPDATE_MANY_NORMAL_SLEEP_2+"?mark=demo1-UPDATE_MANY_NORMAL_SLEEP_2----1");
//                System.out.println("end UPDATE_MANY_NORMAL_SLEEP_2-----1");
//                downLatch.countDown();
//            }).start();
            downLatch.await();
            System.out.println("end testManyService");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 测试不同延迟下事务提交是否覆盖
     * 默认重试次数30，间隔10，超出抛异常Global lock wait timeout
     * 已下测试基于配置
     * seata:
     * client:
     * rm:
     * lock:
     * retry-times: 3000
     *
     * @throws InterruptedException
     */
    @Test
    public void testDifferentialDelay() throws InterruptedException {
        CountDownLatch downLatch = new CountDownLatch(2);
        new Thread(() -> {
            System.out.println("begin UPDATE_DEMO_SLEEP_5-----");
            HttpUtil.get(BASE_URL + UPDATE_DEMO_SLEEP_5);
            System.out.println("end UPDATE_DEMO_SLEEP_5-----");
            downLatch.countDown();
        }).start();
        Thread.sleep(100);
        new Thread(() -> {
            System.out.println("begin UPDATE_DEMO_SLEEP_2-----");
            HttpUtil.get(BASE_URL + UPDATE_DEMO_SLEEP_2);
            System.out.println("end UPDATE_DEMO_SLEEP_2-----");
            downLatch.countDown();
        }).start();
        downLatch.await();
        System.out.println("end testDifferentialDelay");
    }

    /**
     * 测试并发下事务的准确性，依赖超长的锁存活时间,seata使用的全局锁为非公平模式，并不是谁先等待谁先获取
     * @GlobalTransactional(rollbackFor = Exception.class,timeoutMills = 600000)
     * @throws InterruptedException
     */
    @Test
    public void testDifferentialDelayBatch() throws InterruptedException {
        CountDownLatch downLatch = new CountDownLatch(20);
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                System.out.println("begin UPDATE_DEMO_SLEEP_2-----" + finalI);
                HttpUtil.get(BASE_URL + UPDATE_DEMO_SLEEP_2+"?mark=demo1-updateDemoSleep2----"+finalI);
                System.out.println("end UPDATE_DEMO_SLEEP_2-----" + finalI);
                downLatch.countDown();
            }).start();
            Thread.sleep(200);
        }
        downLatch.await();
        System.out.println("end testDifferentialDelay");
    }
}
