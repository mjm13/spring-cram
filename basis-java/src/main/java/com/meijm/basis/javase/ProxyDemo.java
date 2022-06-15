package com.meijm.basis.javase;

import cn.hutool.core.util.RandomUtil;
import org.springframework.cglib.proxy.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyDemo {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        //JDKProxy  代理
//        MiddleMan middleMan = new MiddleMan(new Zhangsan());
//        People people = (People) Proxy.newProxyInstance(ProxyDemo.class.getClassLoader(), new Class[]{People.class}, middleMan);
//        System.out.println(people.doSomething());

        //Spring-CGLIB 代理
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Lisi.class);
        enhancer.setCallbackFilter(new SpringCallbackFilter());//filter要比callbacks先设置
        enhancer.setCallbackTypes(new Class[]{SpringMiddleMan.class,NoOp.class});
        Class<?> subclass = enhancer.createClass();
        Enhancer.registerCallbacks(subclass, new Callback[] {
                new SpringMiddleMan(), NoOp.INSTANCE
        });
        Lisi lisi = (Lisi) subclass.newInstance();
        System.out.println(lisi.doSomething());
        System.out.println(lisi.doSomething());
        System.out.println(lisi.doSomething());
    }
}

/**
 * 代理增强,
 * 需持有被代理实例
 * https://www.baeldung.com/java-dynamic-proxies
 */
class MiddleMan implements InvocationHandler {
    private People people;

    public MiddleMan(People people) {
        this.people = people;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("MiddleMan.invoke   begin");
        Object invoke = method.invoke(people, args);
        System.out.println("MiddleMan.invoke   end");
        return invoke;
    }
}

interface People {
    String doSomething();
}

class Zhangsan implements People {
    @Override
    public String doSomething() {
        System.out.println("in   Zhangsan.doSomething");
        return "My Name is Zhangsan";
    }
}


class SpringMiddleMan implements MethodInterceptor {

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("SpringMiddleMan.invoke   begin");
        Object res = methodProxy.invokeSuper(obj, args);
        System.out.println("SpringMiddleMan.invoke   end");
        return res;
    }
}

class SpringCallbackFilter implements CallbackFilter{

    @Override
    public int accept(Method method) {
        return RandomUtil.randomInt(2);
    }
}

class Lisi {
    public String doSomething() {
        System.out.println("in  Lisi.doSomething");
        return "My Name is Lisi";
    }
}

