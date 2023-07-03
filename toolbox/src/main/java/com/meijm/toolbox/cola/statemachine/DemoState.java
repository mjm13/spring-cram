package com.meijm.toolbox.cola.statemachine;

// 定义状态
public enum DemoState {
    //异常状态，抛弃当前任务
    ABNORMAL,
    //搬运中,结束任务之前不可打断
    CARRY,
    //充电中，电量至中电量以上可执行搬运任务
    CHARGE,
    //移动中，移动至赞助点，移动至任务起始位置
    MOVING,
    //空闲，可转换为其它任意状态
    IDLE;
}