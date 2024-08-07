package com.meijm.cola_statemachine.data.enums;

public enum StateMachineEnum {

    /**
     * 测试状态机
     */
    TEST_MACHINE("testMachine","测试状态机");

    /**
     * code
     */
    private String code;

    /**
     * desc
     */
    private String desc;

    StateMachineEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}