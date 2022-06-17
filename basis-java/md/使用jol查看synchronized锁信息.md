# [JOL](https://github.com/openjdk/jol)
JOL 是OpenJdk发布的用查看对象在JVM中信息，全称是Java Object Layout。可以通过命令行使用。
下面例子中仅使用到了ClassLayout，有兴趣可以看看官网介绍。

pom依赖，jol 0.16版本没有使用字节码展示对象头而是直接展示了锁信息，与jstack日志一致，如果想查看对象头变化请使用 0.10版
```xml
        <dependency>
            <groupId>org.openjdk.jol</groupId>
            <artifactId>jol-core</artifactId>
            <version>0.16</version>
        </dependency>
```
# [synchronization](https://docs.oracle.com/javase/tutorial/essential/concurrency/sync.html)
synchronization是同步关键字，下面的示例使用synchronization+jol查看锁信息。有兴趣可以直接运行看看效果，也可以直接拖到最后看看结论或者看看oracle官方解释。

JAVA代码
```java
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

@Slf4j
public class SynchronizedDemo {

    public static void main(String[] args) {
        SynchronizedDemo demo = new SynchronizedDemo();
        SynchronizedDemo demo1 = new SynchronizedDemo();
        SynchronizedDemo demo2 = new SynchronizedDemo();
        new Thread(() -> {
            demo.lockMethod();
        }).start();
        new Thread(() -> {
            demo.lockInstance(demo1);
        }).start();
        new Thread(() -> {
            SynchronizedDemo.lockStaticMethod();
        }).start();
        log.info("打印demo头信息");
        log.info(ClassLayout.parseInstance(demo).toPrintable());
        log.info("打印demo1头信息");
        log.info(ClassLayout.parseInstance(demo1).toPrintable());
        log.info("打印demo2头信息");
        log.info(ClassLayout.parseInstance(demo2).toPrintable());
        log.info("打印Class头信息");
        log.info(ClassLayout.parseInstance(SynchronizedDemo.class).toPrintable());
    }

    public synchronized static void lockStaticMethod() {
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void lockMethod() {
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void lockInstance(SynchronizedDemo demo) {
        synchronized (demo) {
            try {
                Thread.sleep(1000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
```
打印日志
```
2022-06-15 15:28:48 INFO    --- [main      ] c.m.basis.concurrent.SynchronizedDemo    : 打印demo头信息
2022-06-15 15:28:49 INFO    --- [main      ] c.m.basis.concurrent.SynchronizedDemo    : com.meijm.basis.concurrent.SynchronizedDemo object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     0x000000002097f050 (thin lock: 0x000000002097f050)
  8   4        (object header: class)    0xf800c105
 12   4        (object alignment gap)    
Instance size: 16 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

2022-06-15 15:28:49 INFO    --- [main      ] c.m.basis.concurrent.SynchronizedDemo    : 打印demo1头信息
2022-06-15 15:28:49 INFO    --- [main      ] c.m.basis.concurrent.SynchronizedDemo    : com.meijm.basis.concurrent.SynchronizedDemo object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     0x0000000020a7ee10 (thin lock: 0x0000000020a7ee10)
  8   4        (object header: class)    0xf800c105
 12   4        (object alignment gap)    
Instance size: 16 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

2022-06-15 15:28:49 INFO    --- [main      ] c.m.basis.concurrent.SynchronizedDemo    : 打印demo2头信息
2022-06-15 15:28:49 INFO    --- [main      ] c.m.basis.concurrent.SynchronizedDemo    : com.meijm.basis.concurrent.SynchronizedDemo object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     0x0000000000000001 (non-biasable; age: 0)
  8   4        (object header: class)    0xf800c105
 12   4        (object alignment gap)    
Instance size: 16 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

2022-06-15 15:28:49 INFO    --- [main      ] c.m.basis.concurrent.SynchronizedDemo    : 打印Class头信息
2022-06-15 15:28:49 INFO    --- [main      ] c.m.basis.concurrent.SynchronizedDemo    : java.lang.Class object internals:
OFF  SZ                                              TYPE DESCRIPTION                    VALUE
  0   8                                                   (object header: mark)          0x000000001ba5570a (fat lock: 0x000000001ba5570a)
  8   4                                                   (object header: class)         0xf80003df
 12   4                     java.lang.reflect.Constructor Class.cachedConstructor        null
 16   4                                   java.lang.Class Class.newInstanceCallerCache   null
 20   4                                  java.lang.String Class.name                     (object)
 24   4                                                   (alignment/padding gap)        
 28   4                       java.lang.ref.SoftReference Class.reflectionData           (object)
 32   4   sun.reflect.generics.repository.ClassRepository Class.genericInfo              null
 36   4                                java.lang.Object[] Class.enumConstants            null
 40   4                                     java.util.Map Class.enumConstantDirectory    null
 44   4                    java.lang.Class.AnnotationData Class.annotationData           (object)
 48   4             sun.reflect.annotation.AnnotationType Class.annotationType           null
 52   4                java.lang.ClassValue.ClassValueMap Class.classValueMap            null
 56  32                                                   (alignment/padding gap)        
 88   4                                               int Class.classRedefinedCount      0
 92   4                                                   (object alignment gap)         
Instance size: 512 bytes
Space losses: 36 bytes internal + 4 bytes external = 40 bytes total
```

> 在代码运行时可使用jstack -l 进程id，查看具体锁信息，

# 结论
synchronization锁定的是JVM中运行的对象，静态方法锁定的是Class对象，代码块锁定的是括号中的对象。



# 参考资料
https://github.com/openjdk/jol

https://docs.oracle.com/javase/tutorial/essential/concurrency/locksync.html