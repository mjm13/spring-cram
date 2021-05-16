# AbstractQueuedSynchronizer 
AQS是一个内部维护了先进先出队列+标识(数字)的模型，标识使用CAS模式修改，作为多线程工具的基础组件

![img](..\md-images\AQS队列结构.png)

## 属性

| 属性名                      | 说明                                              |
| --------------------------- | ------------------------------------------------- |
| volatile Node head          | 为头节点会清理Node中关联的pre，thread避免GC不回收 |
| volatile Node tail          | 尾节点                                            |
| volatile int state          | 0为空闲其它组件按需使用，使用cas来赋值，          |
| Thread exclusiveOwnerThread | 持有线程                                          |

> state为volatile的int，不同的业务场景按需实现，例如ReentrantLock中0代表未上锁
>
> 独占模式：
>
> ReentrantLock.Sync中state为0表示未锁定>0表示被几个线程持有
>
> `ReentrantReadWriteLock.Sync中state为读锁+写锁组合 通过位运算符来获取` 待验证
>
> ThreadPoolExecutor.Worker中state为0表示未执行
>
> 共享模式：

## 关键方法

### 独占模式

#### 持有资源：acquire

acquire: 独占模式获取,独占模式即只有一个线程可更新state.忽略中断标识，在获取之后响应中断。

acquireInterruptibly:独占模式获取,线程标识为中断则抛出异常

```java
public final void acquire(int arg) {
    if (!tryAcquire(arg) &&
        acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
        selfInterrupt();
}
```

![](..\md-images\AQS-acquire.png)

> tryAcquire:子类按需实现,使用独占模式更新state,增加state，成功返回true失败返回false
>
> 中断后不会正确响应park,所以需要重置线程中断标识,并在unpark之后进行中断补偿

#### 释放资源：release

release:以独占模式释放资源

```java
public final boolean release(int arg) {
    if (tryRelease(arg)) {
        Node h = head;
        if (h != null && h.waitStatus != 0)
            unparkSuccessor(h);
        return true;
    }
    return false;
}
```

![](..\md-images\AQS-release.png)

> tryRelease:子类按需实现,使用独占模式更新state，减少state,并处理对应独占线程

### 共享模式

#### 持有资源:acquireShared 

- acquireShared 共享模式获取，忽略中断线程，在获取之后相应中断。
- acquireSharedInterruptibly 共享模式获取，响应中断，线程中断则抛出异常。

```java
public final void acquireShared(int arg) {
    if (tryAcquireShared(arg) < 0)
        doAcquireShared(arg);
}
```

![](..\md-images\AQS-acquireShared.png)

> tryAcquireShared:子类按需实现，对返回值有如下要求：
>
> 负值:失败。
> 零:共享模式下的获取成功，但是没有后续共享模式下的获取可以成功。
> 正值: 如果共享模式下的获取成功并且后续共享模式下的获取也可能成功，则为正值，在这种情况下，后续的等待线程必须检查可用性。 （对三个不同返回值的支持使该方法可以在仅有时进行获取的情况下使用。）成功后，就已经获取了此对象。

#### 释放资源:releaseShared

* releaseShared:以共享模式释放资源

```java
public final boolean releaseShared(int arg) {
    if (tryReleaseShared(arg)) {
        doReleaseShared();
        return true;
    }
    return false;
}
```

![](..\md-images\AQS-releaseShared.png)

> tryReleaseShared:子类按需实现,使用共享模式更新state，减少state

### 其它关键方法

#### 检查并更新节点状态:shouldParkAfterFailedAcquire

> 在park线程之前的判断,当前置节点为取消时更新前置节点

```java
    private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {
        int ws = pred.waitStatus;
        if (ws == Node.SIGNAL)
            /*
             * This node has already set status asking a release
             * to signal it, so it can safely park.
             */
            return true;
        if (ws > 0) {
            /*
             * Predecessor was cancelled. Skip over predecessors and
             * indicate retry.
             */
            do {
                node.prev = pred = pred.prev;
            } while (pred.waitStatus > 0);
            pred.next = node;
        } else {
            /*
             * waitStatus must be 0 or PROPAGATE.  Indicate that we
             * need a signal, but don't park yet.  Caller will need to
             * retry to make sure it cannot acquire before parking.
             */
            compareAndSetWaitStatus(pred, ws, Node.SIGNAL);
        }
        return false;
    }
```

#### 唤醒后续节点:unparkSuccessor

> 唤醒后续节点,并清理取消的节点,

```java
private void unparkSuccessor(Node node) {
    /*
     * If status is negative (i.e., possibly needing signal) try
     * to clear in anticipation of signalling.  It is OK if this
     * fails or if status is changed by waiting thread.
     */
    int ws = node.waitStatus;
    if (ws < 0)
        compareAndSetWaitStatus(node, ws, 0);

    /*
     * Thread to unpark is held in successor, which is normally
     * just the next node.  But if cancelled or apparently null,
     * traverse backwards from tail to find the actual
     * non-cancelled successor.
     */
    Node s = node.next;
    if (s == null || s.waitStatus > 0) {
        s = null;
        for (Node t = tail; t != null && t != node; t = t.prev)
            if (t.waitStatus <= 0)
                s = t;
    }
    if (s != null)
        LockSupport.unpark(s.thread);
}
```

#### 共享模式设置队列头:setHeadAndPropagate

> 共享模式下多个线程同时持有资源,头节点会频繁变化需要及时释放资源

```java
private void setHeadAndPropagate(Node node, int propagate) {
    Node h = head; // Record old head for check below
    setHead(node);
    /*
     * Try to signal next queued node if:
     *   Propagation was indicated by caller,
     *     or was recorded (as h.waitStatus either before
     *     or after setHead) by a previous operation
     *     (note: this uses sign-check of waitStatus because
     *      PROPAGATE status may transition to SIGNAL.)
     * and
     *   The next node is waiting in shared mode,
     *     or we don't know, because it appears null
     *
     * The conservatism in both of these checks may cause
     * unnecessary wake-ups, but only when there are multiple
     * racing acquires/releases, so most need signals now or soon
     * anyway.
     */
    if (propagate > 0 || h == null || h.waitStatus < 0 ||
        (h = head) == null || h.waitStatus < 0) {
        Node s = node.next;
        if (s == null || s.isShared())
            doReleaseShared();
    }
}
```



## Node

| 属性名          | 说明                                                         |
| --------------- | ------------------------------------------------------------ |
| int waitStatus  | 1：CANCELLED，表示当前的线程被取消；<br/>-1：SIGNAL，后续节点需要unpark；<br/>-2：CONDITION，表示当前节点在等待condition，也就是在condition队列中；<br/>-3：PROPAGATE，A releaseShared应该传播到其他节点。 在doReleaseShared中对此进行了设置（仅适用于头节点），以确保传播继续进行，即使此后进行了其他操作也是如此。 <br/> 0：表示当前节点在sync队列中，等待着获取锁。 |
| Node prev       | 前驱节点，比如当前节点被取消，那就需要前驱节点和后继节点来完成连接。 |
| Node next       | 后继节点。                                                   |
| Thread thread   | 入队列时的当前线程。                                         |
| Node nextWaiter | 为NULL表示为独占模式,另有SHARED和其它condition?              |

> PROPAGATE:共享模式中会通过状态是否小于0来判断是否需要唤醒后续节点,共享模式下多个线程可同时持有state变更,waitStatus会频繁从0切换为SIGNAL,区分SIGNAL增加的中间状态所以称为传播值

## ConditionObject

| 属性             | 说明   |
| ---------------- | ------ |
| Node firstWaiter | 头节点 |
| Node lastWaiter  | 尾节点 |

> 为Condition接口实现,Condition的目的主要是替代Object的wait,notify,notifyAll方法的,它是基于Lock实现的.(而Lock是来替代synchronized方法).

![img](D:\Project\Self_Project\spring-cram\interview\md-images\ConditionObject队列结构.png)

### 关键方法

#### 阻塞线程:await 

await:对应Object.wait(),通过AQS机制释放锁定的资源,终止当前线程,恢复后使用AQS独占模式重新锁定资源

```java
public final void await() throws InterruptedException {
    if (Thread.interrupted())
        throw new InterruptedException();
    Node node = addConditionWaiter();
    long savedState = fullyRelease(node);
    int interruptMode = 0;
    while (!isOnSyncQueue(node)) {
        LockSupport.park(this);
        if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
            break;
    }
    if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
        interruptMode = REINTERRUPT;
    if (node.nextWaiter != null) // clean up if cancelled
        unlinkCancelledWaiters();
    if (interruptMode != 0)
        reportInterruptAfterWait(interruptMode);
}
```



#### 唤醒线程:signal

```java
public final void signal() {
    if (!isHeldExclusively())
        throw new IllegalMonitorStateException();
    Node first = firstWaiter;
    if (first != null)
        doSignal(first);
}
```