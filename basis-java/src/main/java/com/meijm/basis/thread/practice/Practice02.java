package com.meijm.basis.thread.practice;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 问题：
 * 编写两个线程,一个线程打印1-52的整数，另一个线程打印字母A-Z。打印顺序为12A34B56C….5152Z。即按照整数和字母的顺序从小到大打印，并且每打印两个整数后，打印一个字母，交替循环
 * 解决方案：
 */
public class Practice02 {
    public static void main(String[] args) {

        new Thread(new PrintNum()).start();
        new Thread(new PrintLetter()).start();

    }
}

class PrintNum implements Runnable {
    private List<Integer> nums = IntStream.range(1, 53).boxed().collect(Collectors.toList());

    @Override
    public void run() {
        try {
            synchronized (Object.class) {
                for (Integer num : nums) {
                    System.out.print(num);
                    if (num == 52){
                        Object.class.notify();
                        return;
                    }
                    if (num % 2 == 0) {
                        Object.class.notify();
                        Object.class.wait();
                    }
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class PrintLetter implements Runnable {
    private List<String> letters = IntStream.range(65, 91).boxed().map(integer -> {
        int temp = integer;
        char b = (char) temp;
        return String.valueOf(b);
    }).collect(Collectors.toList());

    @Override
    public void run() {
        try {
            synchronized (Object.class) {
                for (String letter : letters) {
                    System.out.println(letter);
                    if (letter.equals("Z")){
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