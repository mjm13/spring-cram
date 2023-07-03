package com.meijm.toolbox.cola.statemachine;

import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.alibaba.cola.statemachine.builder.StateMachineBuilderFactory;

public class StatemachineDemo {
    public static void main(String[] args) {
//        // 创建状态机生成器
//        StateMachineBuilder<DemoState, DemoEvent, DemoContext> builder = StateMachineBuilderFactory.create();
//
//        // 定义状态转换事件、条件和动作
//        builder.externalTransition()
//                .from(DemoState.STATE1)
//                .to(DemoState.STATE2)
//                .on(DemoEvent.EVENT1)
//                .perform(doAction());
//
//        builder.externalTransitions()
//                .fromAmong(DemoState.STATE1, DemoState.STATE2, DemoState.STATE3)
//                .to(DemoState.STATE4)
//                .on(DemoEvent.EVENT4)
//                .perform(doAction());
//
//        builder.externalTransition()
//                .from(DemoState.STATE4)
//                .to(DemoState.STATE1)
//                .on(DemoEvent.EVENT2)
//                .perform(doAction());
//
//        builder.internalTransition()
//                .within(DemoState.STATE2)
//                .on(DemoEvent.EVENT3)
//                .perform(doAction());
//
//        // 根据状态机生成器创建状态机，并设置初始状态
//        StateMachine<DemoState, DemoEvent, DemoContext> stateMachine = builder.build("test");
//
//        // 定义上下文，写入一个message字段
//        DemoContext demoContext = new DemoContext();
//        demoContext.message = "Hello, world!";
//        // 触发状态转换
//        stateMachine.fireEvent(DemoState.STATE1,DemoEvent.EVENT1, demoContext);
//
//        stateMachine.fireEvent(DemoState.STATE4,DemoEvent.EVENT2, demoContext);
//
//        stateMachine.showStateMachine();

    }
    // 执行动作方法
    private static DemoAction doAction() {
        return new DemoAction() ;
    }
}
