package com.meijm.toolbox.groovy;

import groovy.lang.GroovyClassLoader;

public class GroovyLoadDemo {
    public static void main(String[] args) throws Exception {
        GroovyClassLoader groovyClassLoader = new GroovyClassLoader();

        String code1 = "class MyClass { public int hashCode() { return 1 } }";
        Class clazz1 = groovyClassLoader.parseClass(code1);
        Object instance1 = clazz1.newInstance();
        System.out.println(instance1.hashCode());

        String code2 = "class MyClass { public int hashCode() { return 2 } }";
        Class clazz2 = groovyClassLoader.parseClass(code2);
        Object instance2 = clazz2.newInstance();
        System.out.println(instance2.hashCode());

        instance1 = clazz1.newInstance();
        System.out.println(instance1.hashCode());
    }
}
