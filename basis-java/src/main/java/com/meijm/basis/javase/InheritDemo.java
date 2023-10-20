package com.meijm.basis.javase;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description 继承边界测试，查看重载后实体执行结果
 * @Author MeiJM
 * @Date 2022/4/27
 **/
@Slf4j
public class InheritDemo {
    public static void main(String[] args) {
        log.info("-----------------------TempParent---------------------");
        TempParent parent = new TempChild();
        parent.childDefault();
        parent.parentDefault();
        parent.interfaceDefault();
        log.info("-----------------------TempChild---------------------");
        TempChild child = new TempChild();
        child.childDefault();
        child.parentDefault();
        child.interfaceDefault();
        log.info("-----------------------TempInterface---------------------");
        TempInterface tempInterface = new TempChild();
        tempInterface.childDefault();
        tempInterface.parentDefault();
        tempInterface.interfaceDefault();
    }
}

//若子类没有重写该方法，则默认会优先调用父类中的方法，不会报错。
//若子类重写了该方法，则相当于同时重写了父类以及接口中的方法。调用的是子类重写后的方法
interface TempInterface {
    default void interfaceDefault() {
        System.out.println("interfaceDefault in TempInterface");
    }

    void parentDefault();

    void childDefault();
}
@Slf4j
class TempParent {
    public void interfaceDefault() {
        log.info("interfaceDefault in TempParent");
    }

    public void parentDefault() {
        log.info("parentDefault in TempParent");
    }

    public void childDefault(){
        log.info("childDefault in TempParent");
    }
}
@Slf4j
class TempChild extends TempParent implements TempInterface {
    public void childDefault(){
        log.info("childDefault in TempChild");
    }
}
