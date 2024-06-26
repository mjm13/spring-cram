package com.meijm.basis.thread.practice;

import java.util.List;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;



/**
 * 问题：
 * 编写两个线程,一个线程打印1-52的整数，另一个线程打印字母A-Z。打印顺序为12A34B56C….5152Z。即按照整数和字母的顺序从小到大打印，并且每打印两个整数后，打印一个字母，交替循环
 * 解决方案：
 */
public class Practice02 {

    public static AtomicBoolean printNumber = new AtomicBoolean(false);
    public static AtomicInteger currentIndex = new AtomicInteger(1);

    public static void main(String[] args) throws InterruptedException {
        System.out.println("-------------使用Synchronized------------------");
        new PrintLetterBySync().start();
        new PrintNumBySync().start();
//        Thread.sleep(1000);
        System.out.println("-------------使用AtomicInteger------------------");
//        new PrintLetterByAtomic().start();
//        new PrintNumByAtomic().start();
//        Thread.sleep(1000);
        System.out.println("-------------使用TransferQueue------------------");
//        LinkedTransferQueue<String> numTransfer = new LinkedTransferQueue<String>();
//        LinkedTransferQueue<String> letterTransfer = new LinkedTransferQueue<String>();
//        new PrintLetterByExchanger(numTransfer, letterTransfer).start();
//        new PrintNumByExchanger(numTransfer, letterTransfer).start();
    }
}

class PrintNumBySync extends Thread {
    private List<Integer> nums = IntStream.range(1, 27).boxed().collect(Collectors.toList());

    @Override
    public void run() {
        try {
            Practice02.printNumber.set(true);
            synchronized (Object.class) {
                for (Integer num : nums) {
                    System.out.print(num);
                    if (num == 26) {
                        Object.class.notify();
                        return;
                    }
                    Object.class.notify();
                    Object.class.wait();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class PrintLetterBySync extends Thread {
    private List<String> letters = IntStream.range(65, 91).boxed().map(integer -> String.valueOf((char) integer.intValue())).collect(Collectors.toList());

    @Override
    public void run() {
        try {
            synchronized (Object.class) {
                if (!Practice02.printNumber.get()) {
                    Object.class.wait();
                }
                for (String letter : letters) {
                    System.out.println(letter);
                    if (letter.equals("Z")) {
                        Object.class.notify();
                        return;
                    }
                    Object.class.notify();
                    Object.class.wait();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class PrintNumByAtomic extends Thread {
    private List<Integer> nums = IntStream.range(1, 27).boxed().collect(Collectors.toList());

    @Override
    public void run() {
        for (Integer num : nums) {
            while (Practice02.currentIndex.get() % 2 != 1) {
                Thread.yield();
            }
            System.out.print(num);
            Practice02.currentIndex.incrementAndGet();
        }
    }
}

class PrintLetterByAtomic extends Thread {
    private List<String> letters = IntStream.range(65, 91).boxed().map(integer -> String.valueOf((char) integer.intValue())).collect(Collectors.toList());

    @Override
    public void run() {
        for (String letter : letters) {
            while (Practice02.currentIndex.get() % 2 != 0) {
                Thread.yield();
            }
            System.out.println(letter);
            Practice02.currentIndex.incrementAndGet();
        }
    }
}

class PrintNumByExchanger extends Thread {
    private List<String> letters = IntStream.range(65, 91).boxed().map(integer -> String.valueOf((char) integer.intValue())).collect(Collectors.toList());
    private LinkedTransferQueue<String> numTransfer;
    private LinkedTransferQueue<String> letterTransfer;

    public PrintNumByExchanger(LinkedTransferQueue<String> numTransfer, LinkedTransferQueue<String> letterTransfer) {
        this.numTransfer = numTransfer;
        this.letterTransfer = letterTransfer;
    }

    @Override
    public void run() {
        for (String letter : letters) {
            try {
                String num = numTransfer.take();
                System.out.print(num);
                letterTransfer.transfer(letter);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class PrintLetterByExchanger extends Thread {
    private List<Integer> nums = IntStream.range(1, 27).boxed().collect(Collectors.toList());
    private LinkedTransferQueue<String> numTransfer;
    private LinkedTransferQueue<String> letterTransfer;

    public PrintLetterByExchanger(LinkedTransferQueue<String> numTransfer, LinkedTransferQueue<String> letterTransfer) {
        this.numTransfer = numTransfer;
        this.letterTransfer = letterTransfer;
    }

    @Override
    public void run() {
        for (Integer num : nums) {
            try {
                numTransfer.transfer(String.valueOf(num));
                String temp = letterTransfer.take();
                System.out.println(temp);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}