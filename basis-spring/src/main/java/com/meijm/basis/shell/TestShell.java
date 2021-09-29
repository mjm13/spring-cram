package com.meijm.basis.shell;

import com.meijm.basis.common.event.TestEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class TestShell {

    @Autowired
    ApplicationContext applicationContext;

    @ShellMethod("springConsumer test")
    public void listener(){
        applicationContext.publishEvent(new TestEvent(this, "test1"));
        applicationContext.publishEvent(new TestEvent(this, "test2"));
        applicationContext.publishEvent(new TestEvent(this, "test3"));
    }
}
