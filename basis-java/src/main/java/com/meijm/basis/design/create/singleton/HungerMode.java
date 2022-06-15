package com.meijm.basis.design.create.singleton;

/**
 * 懒汉模式
 */
public class HungerMode {
    private static HungerMode instance = new HungerMode();

    private HungerMode(){}

    public static HungerMode getInstance(){
        return instance;
    }
}