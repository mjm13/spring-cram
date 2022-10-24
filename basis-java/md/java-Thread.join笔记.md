
# Thread.join方法
说明:当前线程挂起，等待目标线程结束运行。
> join无参方法调用的是join(long millis),下面是源码
```java
    public final void join() throws InterruptedException {
        join(0);
    }

    public final synchronized void join(long millis)
    throws InterruptedException {
        long base = System.currentTimeMillis();
        long now = 0;

        if (millis < 0) {
            throw new IllegalArgumentException("timeout value is negative");
        }

        if (millis == 0) {
            while (isAlive()) {
                wait(0);
            }
        } else {
            while (isAlive()) {
                long delay = millis - now;
                if (delay <= 0) {
                    break;
                }
                wait(delay);
                now = System.currentTimeMillis() - base;
            }
        }
    }
```
只用看millis == 0这一段就行。
* isAlive():查看线程当前是否存活
* wait(0):Object方法表示当前线程等待，理论上要需要有人唤醒才是完整流程当前线程才会继续。[stackoverflow](https://stackoverflow.com/questions/9866193/who-and-when-notify-the-thread-wait-when-thread-join-is-called)上有人提出了类似的疑问，第一个回答的大哥展示了thread.cpp源码，线程结束时会唤醒当前实例对应的wait方法

# 模拟测试
下面的代码模拟了join的代码，并给出了其它两种测试方法查看是否能达到相同效果。
```
@Slf4j
public class JoinDemo {
    public static void main(String[] args) {
        SequentialExecThread previousThread = null;
        log.info("join-开始执行");
        for (int i = 0; i < 10; i++) {
            SequentialExecThread joinDemo = new SequentialExecThread(previousThread, i);
            joinDemo.start();
            previousThread = joinDemo;
        }
        while (Thread.activeCount() != 1) {
        }
        log.info("join-结束执行");
    }
}

@Slf4j
class SequentialExecThread extends Thread {
    private int i;
    private SequentialExecThread previousThread; //上一个线程

    public SequentialExecThread(SequentialExecThread previousThread, int i) {
        this.previousThread = previousThread;
        this.i = i;
    }

    @Override
    public void run() {
        try {
            if (previousThread != null) {
                previousThread.joinBySelfByThis();
//                previousThread.joinBySelf();
//                previousThread.joinBySelfByObject();
            }
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("执行线程{}", i);
    }

    public final synchronized void joinBySelf()
            throws InterruptedException {
        while (isAlive()) {
            log.info("等待执行-开始");
            wait(0);
            log.info("等待执行-结束");
        }
    }

    public final void joinBySelfByThis()
            throws InterruptedException {
        synchronized (this) {
            while (isAlive()) {
                log.info("等待执行-开始");
                this.wait(0);
                log.info("等待执行-结束");
            }
        }
    }
    
    public final void joinBySelfByObject()
        throws InterruptedException {
        synchronized (Object.class) {
            while (isAlive()) {
                log.info("等待执行-开始");
                Object.class.wait(0);
                log.info("等待执行-结束");
            }
        }
    }
}
```
* joinBySelf：和join方法一致，仅截取了millis == 0这一段
* joinBySelfByThis：使用synchronized(this)锁定当前实例，也达到预期
* joinBySelfByObject:使用synchronized(Object.class)锁定Object的class实例，此方法达不到预期，说明唤醒的只是当前实例对应的wait方法。

基础知识点复习：synchronized关键字加在方法上时表示当前实例的方法为同步代码块，加在静态方法上时表示当前静态方法为同步代码块。

> Thread.activeCount():获取当前执行线程数，确保主线程最后执行


# 参考资料
https://stackoverflow.com/questions/9866193/who-and-when-notify-the-thread-wait-when-thread-join-is-called