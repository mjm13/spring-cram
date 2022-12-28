package com.meijm.basis.javase;

public class RecursionTest {
    static int number = 0;
    public void test() throws InterruptedException {
        System.out.println(number++);
        // 100 9027
        Thread.sleep(100);
        test();
    }

    public static void main(String[] args) throws InterruptedException {
        RecursionTest recursionTest = new RecursionTest();
        recursionTest.test();
    }
}
