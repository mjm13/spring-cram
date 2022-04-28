package com.meijm.interview.concurrent;

import java.util.concurrent.Exchanger;

/**
 *  Exchanger 线程之间交换数据
 */
public class ExchangerDemo {

    static Exchanger<String> exchanger=new Exchanger<String>();
    static class Task implements Runnable{
        @Override
        public void run() {
            try {
                String result=exchanger.exchange(Thread.currentThread().getName());
                System.out.println("this is "+Thread.currentThread().getName()+" receive data:"+result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args)throws  Exception{

        Thread t1=new Thread(new Task(),"thread1");
        Thread t2=new Thread(new Task(),"thread2");
        Thread t3=new Thread(new Task(),"thread3");
        Thread t4=new Thread(new Task(),"thread4");
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}