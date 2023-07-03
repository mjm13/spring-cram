package com.meijm.toolbox.cola.statemachine;

import com.alibaba.cola.statemachine.Action;

public class DemoAction implements Action<DemoState, DemoEvent, DemoContext> {
    @Override
    public void execute(DemoState from, DemoState to, DemoEvent event, DemoContext context) {
        System.out.println(String.format("执行转换: %s -> %s [demoEvent:%s] [message:%s]", from, to, event, context.message));
    }
}
