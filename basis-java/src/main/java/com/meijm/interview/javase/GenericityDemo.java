package com.meijm.interview.javase;

import lombok.extern.slf4j.Slf4j;

import java.util.Collection;



/**
 * @Description 泛型继承相关demo
 * @Author MeiJM
 * @Date 2022/5/19
 **/
@Slf4j
public class GenericityDemo {
    public static void main(String[] args) {
    }
}

interface GenericityInterface<T> {
    String getString(T t);
}
abstract class GenericityClass<A extends Collection, B> {
    abstract String getString(A a, B b);
}

class SubGenericity1<T, B, A extends Collection> extends GenericityClass<A, B> implements GenericityInterface<T> {
    @Override
    public String getString(T o) {
        return o.getClass().toString();
    }

    @Override
    String getString(A a, B o) {
        return o.getClass().toString() + "|" + o.getClass();
    }
}

class SubGenericity2<T extends Collection, B> extends GenericityClass<T, B> implements GenericityInterface<T> {
    @Override
    public String getString(T o) {
        return o.getClass().toString();
    }

    @Override
    String getString(T a, B o) {
        return o.getClass().toString() + "|" + o.getClass();
    }
}

class SubGenericity3 extends GenericityClass implements GenericityInterface {
    @Override
    public String getString(Object o) {
        return o.getClass().toString();
    }

    @Override
    String getString(Collection collection, Object o) {
        return o.getClass().toString() + "|" + o.getClass();
    }
}
class SubGenericity4<T> extends GenericityClass implements GenericityInterface<T> {
    @Override
    public String getString(T o) {
        return o.getClass().toString();
    }

    @Override
    String getString(Collection collection, Object o) {
        return o.getClass().toString() + "|" + o.getClass();
    }
}
class SubGenericity5<T extends Collection,B> extends GenericityClass<T,B> implements GenericityInterface {
    @Override
    public String getString(Object o) {
        return o.getClass().toString();
    }

    @Override
    String getString(T collection, B o) {
        return o.getClass().toString() + "|" + o.getClass();
    }
}