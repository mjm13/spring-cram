package com.meijm.interview.javase;

public class InheritDemo {
    public static void main(String[] args) {
        TempChild child = new TempChild();
        child.test();
    }
}

//若子类没有重写该方法，则默认会优先调用父类中的方法，不会报错。
//若子类重写了该方法，则相当于同时重写了父类以及接口中的方法。调用的是子类重写后的方法
interface TempInterface {
    void test();
}

class TempParent {
    public void test() {
        System.out.println("in TempParent");
    }
}

class TempChild extends TempParent implements TempInterface {

}
