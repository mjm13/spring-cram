package com.meijm.basis.design.create.singleton;

/**
 * 懒汉模式
 */
public class LazyMode {
    private static volatile LazyMode instance = null;

    private LazyMode(){}

    /**
     * 多线程场景存在延迟问题
     * @return
     */
    public synchronized static LazyMode getSyncMethodInstance(){
        if (instance == null){
            instance = new LazyMode() ;
        }
        return instance;
    }

    /**
     * 最大程度保证了线程安全和延迟
     * @return
     */
    public static LazyMode getSyncLazyInstance(){
        if(instance==null){
            synchronized (LazyMode.class){
                if(instance==null){
                    instance = new LazyMode();
                }
            }
        }
        return instance;
    }
}