package com.meijm.basis.design.create.singleton;

import lombok.extern.slf4j.Slf4j;

/**
 *  单例模式
 *  懒汉模式:需要时初始化
 *  饿汉模式:启动时初始化
 *  枚举模式:通过枚举类型特性实例化
 *
 *  适用场景:
 *  系统只需要一个实例,
 *  例如
 *  jdk 自带 Runtime.getRuntime()  属于饿汉模式
 *  shiro 权限org.apache.shiro.SecurityUtil 属于懒汉模式,需要时创建并将权限信息绑定到当前线程
 */
@Slf4j
public class SingletonDemo {
    public static void main(String[] args) throws InterruptedException {
        //饿汉模式
        log.info(" HungerMode.getInstance().hashCode():{}", HungerMode.getInstance().hashCode());
        log.info(" HungerMode.getInstance().hashCode():{}", HungerMode.getInstance().hashCode());
        // 枚举模式
        log.info("EnumMode.INSTANCE.hashCode():{}", EnumMode.INSTANCE.hashCode());
        log.info("EnumMode.INSTANCE.hashCode():{}", EnumMode.INSTANCE.hashCode());

        //懒汉模式
        log.info(" LazyMode.getSyncLazyInstance().hashCode():{}", LazyMode.getSyncLazyInstance().hashCode());
        log.info(" LazyMode.getSyncLazyInstance().hashCode():{}", LazyMode.getSyncLazyInstance().hashCode());

        log.info(" LazyMode.getSyncMethodInstance().hashCode():{}", LazyMode.getSyncMethodInstance().hashCode());
        log.info(" LazyMode.getSyncMethodInstance().hashCode():{}", LazyMode.getSyncMethodInstance().hashCode());
    }
}


