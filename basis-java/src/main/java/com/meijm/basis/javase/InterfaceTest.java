package com.meijm.basis.javase;

public class InterfaceTest {
    public void test(){
        System.out.println("in InterfaceTest Class");
    }

    public static void main(String[] args) {
        InterfaceTest test = new InterfaceTest();
        MyInter myInter = test::test;
        myInter.test();
    }
}

 interface MyInter{
    void test();
 }
