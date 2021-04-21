package com.meijm.interview.threadPool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@ShellComponent
public class ThreadPoolDemo {

    @ShellMethod("siglePool")
    public void siglePool() {
        ExecutorService service = Executors.newWorkStealingPool();
        service.execute(() -> {
            System.out.println("执行线程");
            for (int i = 0; i < 10; i++) {
                System.out.println();
            }
            System.out.println("执行完成");
        });
    }

}
