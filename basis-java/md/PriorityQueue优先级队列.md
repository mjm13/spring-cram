# 说明
与常见队列不同，优先级队列不是采取先进先出模式使用，队列头部是优先级最高的元素。
> 优先级使用Comparable接口定义。

* JDK优先级队列
  * PriorityQueue：优先级队列，内部不限长非线程安全。内部使用变长数组来存储数据，由于没有长度限制所以存在内存溢出风险。
  * PriorityBlockingQueue：功能同PriorityQueue额外实现了BlockingQueue接口实现线程安全的读写，内部维护了ReentrantLock在操作队列时加锁保证线程安全。
* hutool工具包中优先级队列
  * BoundedPriorityQueue：继承PriorityQueue，增加了数组长度限制，淘汰队列末尾元素。

# 代码
* 可修改compareTo接口查看排序不同时效果
```java
import cn.hutool.core.collection.BoundedPriorityQueue;
import cn.hutool.core.comparator.CompareUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.PriorityQueue;
@Slf4j
public class PriorityQueueDemo {
    public static void main(String[] args) {
        Test t1 = new Test(10, "t1");
        Test t2 = new Test(20, "t2");
        Test t3 = new Test(30, "t3");
        Test t4 = new Test(40, "t4");
        Test t5 = new Test(50, "t5");
        Test t6 = new Test(35, "t6");

        PriorityQueue<Test> queue = new PriorityQueue<Test>();
        queue.add(t1);
        queue.add(t2);
        queue.add(t3);
        queue.add(t4);
        queue.add(t5);
        queue.add(t6);
        log.info("1.优先级队列顶端内容:{}", queue.peek());
        log.info("2.优先级队列内容:{}", queue);

        BoundedPriorityQueue<Test> boundedQueue = new BoundedPriorityQueue<Test>(2);
        boundedQueue.add(t1);
        boundedQueue.add(t2);
        boundedQueue.add(t3);
        boundedQueue.add(t4);
        log.info("3.有限优先级队列内容：{}", boundedQueue);
        boundedQueue.add(t5);
        boundedQueue.add(t6);
        log.info("4.有限优先级队列内容：{}", boundedQueue);
    }
}

@Data
class Test implements Comparable<Test> {
    private int priority;
    private String name;

    public Test(int priority, String name) {
        this.priority = priority;
        this.name = name;
    }

    @Override
    public int compareTo(Test o) {
        return CompareUtil.compare(o.getPriority(), priority);
    }
}

```
# 补充资料
通过上面代码的输出可发现队列顶端一定是最大值，但第2位就不一定是排序为2的了，是因为内部使用数组实现的二叉堆，二叉堆保证了顶端元素一定是排序最大的值，且插入，删除操作为

# 参考资料
https://www.cnblogs.com/henry-1202/p/9307927.html