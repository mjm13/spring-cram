package com.meijm.interview.javase;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: jujun chen
 * @description: 使用了CGLIB来动态生成类，元空间存储类信息，-XX:MetaspaceSize=10m -XX:MaxMetaspaceSize=10m
 * 如果只设置堆的大小，并不会溢出
 * @date: 2019/4/7
 */
public class JavaMetaSpaceOOM {

    static class OOMObject {
    }

    public static void main(final String[] args) {
        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    return methodProxy.invokeSuper(o, objects);
                }
            });
            enhancer.create();
        }
    }
}

/**
 * java.lang.OutOfMemoryError
 * OOM -Xmx20m -Xms20m -XX:+HeapDumpOnOutOfMemoryError
 * -XX:+HeapDumpOnOutOfMemoryError   :配置创建堆栈日志
 * -XX:HeapDumpPath=/usr/local/app/oom
 *
 */
class OOMTest {
    public static void main(String[] args) {
        List<Object> objList = new ArrayList();
        while(true) {
            objList.add(new Object());
        }
    }
}

/**
 * java.lang.StackOverflowError
 *SOE栈异常 -Xss125k
 */
class SOETest {
    static int count = 0;
    public static void main(String[] args) {
        try {
            stackMethod();
        } catch(Error err) {
            err.printStackTrace();
            System.out.println("执行count=" + count);
        }
    }
    private static void stackMethod() {
        count ++;
        stackMethod();
    }
}