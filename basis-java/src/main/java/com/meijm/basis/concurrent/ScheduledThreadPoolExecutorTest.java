package com.meijm.basis.concurrent;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * https://www.cnblogs.com/duanxz/p/3752777.html
 * 测试定时任务执行异常
 */
public class ScheduledThreadPoolExecutorTest {
    public static void main(String[] args) {
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
        BusinessTask task = new BusinessTask();
        // 1秒后开始执行任务，以后每隔2秒执行一次
        executorService.scheduleWithFixedDelay(task, 1000, 2000, TimeUnit.MILLISECONDS);
    }

    private static class BusinessTask implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println("任务开始...");
                doBusiness();
                System.out.println("任务结束...");
            }catch (Exception e) {
                String a = null;
                System.out.println("a:="+a.toString());
            }
        }
        
        private void doBusiness() {
            String a = null;
            System.out.println("a:="+a.toString());
        }
    }
}