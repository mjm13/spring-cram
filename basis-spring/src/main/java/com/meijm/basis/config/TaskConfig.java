package com.meijm.basis.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ScheduledExecutorFactoryBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.util.ErrorHandler;

import javax.annotation.PostConstruct;

@Configuration
public class TaskConfig {
    @Autowired
    private TaskScheduler taskScheduler;
    
    @PostConstruct
    public void test(){
        if(taskScheduler instanceof ThreadPoolTaskScheduler){
            ThreadPoolTaskScheduler threadPoolTaskScheduler = (ThreadPoolTaskScheduler) taskScheduler;
            
        }
//        ((ThreadPoolTaskScheduler) taskScheduler).setErrorHandler(new ErrorHandler() {
//            @Override
//            public void handleError(Throwable t) {
//                System.out.println("3333333");
//                throw t;
//            }
//        });
        System.out.println("2222");
    }
}