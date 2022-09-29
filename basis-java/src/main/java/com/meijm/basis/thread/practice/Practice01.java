package com.meijm.basis.thread.practice;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 问题：
 * 设计一个多线程的程序如下：设计一个火车售票模拟程序。假如火车站要有100张火车票要卖出，现在有5个售票点同时售票，用5个线程模拟这5个售票点的售票情况。
 * 解决方案：
 * 使用CountDownLatch或AtomicInteger表示票数
 */
public class Practice01 {

    public static void main(String[] args) {

        CountDownLatch tickets = new CountDownLatch(100);

        new Thread(new Sell(tickets),"售票点1").start();
        new Thread(new Sell(tickets),"售票点2").start();
        new Thread(new Sell(tickets),"售票点3").start();
        new Thread(new Sell(tickets),"售票点4").start();
        new Thread(new Sell(tickets),"售票点5").start();
    }
}

class Sell implements Runnable{
    private  CountDownLatch tickets;
    public Sell(CountDownLatch tickets){
        this.tickets = tickets;
    }

    @Override
    public void run() {
        while (tickets.getCount()>0){
            tickets.countDown();
            System.out.println(Thread.currentThread().getName() + "---------->购票成功 剩余" + tickets.getCount());
        }
    }
}

