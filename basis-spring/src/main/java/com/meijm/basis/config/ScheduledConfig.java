package com.meijm.basis.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;

/**
 * @author chenbo
 * @date 2021/1/18 20:25
 */
@Slf4j
@Configuration
//所有定时任务统一管理，定时任务启动使用不同的线程
public class ScheduledConfig implements SchedulingConfigurer {

    @Override
    // 设定一个长度为50的线程池
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(200); // 设置线程池大小为80
        taskScheduler.setThreadNamePrefix("router-"); // 设置线程名称前缀
        taskScheduler.setAwaitTerminationSeconds(60); // 设置关闭时等待任务完成的时间为60秒
        taskScheduler.setRemoveOnCancelPolicy(true); // 设置关闭时移除已取消的任务
        taskScheduler.setErrorHandler(t -> {
            log.error("定时任务执行异常：", t);
        });
        taskScheduler.setRejectedExecutionHandler((r, executor) -> {
            log.error("定时任务执行被拒绝：", r);
            if (!executor.isShutdown()) {
                log.error("定时任务由当前线程执行：", r);
                r.run();
            }
        });
        taskScheduler.initialize(); // 初始化线程池
        taskRegistrar.setScheduler(taskScheduler); // 设置任务调度器
//        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(80));
    }
}
