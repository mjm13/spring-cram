# 问题
编写两个线程,一个线程打印1-26的整数，另一个线程打印字母A-Z。打印顺序为1A2B3C….26Z。即按照整数和字母的顺序从小到大打印，并且每打印一个整数后，打印一个字母，交替打印

# 测试执行代码

```java
import java.util.List;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Practice {
    public static AtomicBoolean printNumber = new AtomicBoolean(false);
    public static AtomicInteger currentIndex = new AtomicInteger(1);

    public static void main(String[] args) throws InterruptedException {
        System.out.println("-------------使用Synchronized------------------");
        new PrintLetterBySync().start();
        new PrintNumBySync().start();
        Thread.sleep(1000);
        System.out.println("-------------使用AtomicInteger------------------");
        new PrintLetterByAtomic().start();
        new PrintNumByAtomic().start();
        Thread.sleep(1000);
        System.out.println("-------------使用TransferQueue------------------");
        LinkedTransferQueue<String> numTransfer = new LinkedTransferQueue<String>();
        LinkedTransferQueue<String> letterTransfer = new LinkedTransferQueue<String>();
        new PrintLetterByExchanger(numTransfer, letterTransfer).start();
        new PrintNumByExchanger(numTransfer, letterTransfer).start();
    }
}
```
* 使用Synchronized：对同一资源加锁-等待/唤醒的方式达成交替执行的目的，换成Lock也是一样的思路。
* 使用AtomicInteger：使用CAS思路查看当前索引余数是否为单双数来判断当前是否应该执行打印，否则自旋，打印完成后自增。
* 使用TransferQueue：此队列长度为0，在消费者未消费之前会阻塞线程，一个队列存储数字，一个队列存储字符交替获取和生产保证交替执行。

> 下面是具体代码
## 使用Synchronized
``` java
class PrintNumBySync extends Thread {
    private List<Integer> nums = IntStream.range(1, 27).boxed().collect(Collectors.toList());

    @Override
    public void run() {
        try {
            Practice.printNumber.set(true);
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
                if (!Practice.printNumber.get()) {
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
```
* IntStream.range不包含最后一个数字。
* 最后一个字符不需要wait。
* printNumber是为了保证先打印数字

## 使用AtomicInteger
``` java
class PrintNumByAtomic extends Thread {
    private List<Integer> nums = IntStream.range(1, 27).boxed().collect(Collectors.toList());

    @Override
    public void run() {
        for (Integer num : nums) {
            while (Practice.currentIndex.get() % 2 != 1) {
                Thread.yield();
            }
            System.out.print(num);
            Practice.currentIndex.incrementAndGet();
        }
    }
}

class PrintLetterByAtomic extends Thread {
    private List<String> letters = IntStream.range(65, 91).boxed().map(integer -> String.valueOf((char) integer.intValue())).collect(Collectors.toList());

    @Override
    public void run() {
        for (String letter : letters) {
            while (Practice.currentIndex.get() % 2 != 0) {
                Thread.yield();
            }
            System.out.println(letter);
            Practice.currentIndex.incrementAndGet();
        }
    }
}
```
理论上来说使用volatile 也是可以的，因为有自旋。

## 使用TransferQueue
```java

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
```
先生产数字，消费数字保障打印顺序，否则会出现相互等待的情况。