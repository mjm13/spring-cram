package com.meijm.thread.shell;

import com.meijm.thread.demo.ThreadDemo;
import com.meijm.thread.demo.ThreadPoolDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ThreadShell {

    @Autowired
    private ThreadDemo threadDemo;

    @ShellMethod("deadlock")
    public void deadlock(){
        threadDemo.deadlock();
    }
    @ShellMethod("join")
    public void join() throws InterruptedException {
        threadDemo.join();
    }
    @ShellMethod("interrupt")
    public void interrupt() throws InterruptedException {
        threadDemo.interrupt();
    }

    @Autowired
    private ThreadPoolDemo threadPoolDemo;

    @ShellMethod("siglePool")
    public void siglePool(){
        threadPoolDemo.siglePool();
    }
}
