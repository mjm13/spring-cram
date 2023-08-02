package com.meijm.basis.thread;

public class ThreadTest {

    public static void main(String[] args) throws InterruptedException {
        TestThread testThread = new TestThread();
        testThread.start();
        Thread.sleep(100L);
        System.out.println(testThread.isAlive());
        testThread.myactive = false;
        Thread.sleep(1000L);
        System.out.println(testThread.isAlive());
    }
}

class TestThread extends Thread{
    public volatile boolean myactive = true;

    @Override
    public void run() {
        while (myactive) {
            System.out.println(111);
        }

    }
}
