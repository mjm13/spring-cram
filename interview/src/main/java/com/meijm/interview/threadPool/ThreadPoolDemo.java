package com.meijm.interview.threadPool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class ThreadPoolDemo {

}

@Slf4j
class ThreadPoolTest{
    public static void demo(){
        Executors.newFixedThreadPool(1);
        Executors.newSingleThreadExecutor();

        Executors.newCachedThreadPool();
        Executors.newScheduledThreadPool(2);


        Executors.newWorkStealingPool(1);
    }
}