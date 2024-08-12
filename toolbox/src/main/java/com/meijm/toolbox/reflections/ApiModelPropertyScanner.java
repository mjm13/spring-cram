package com.meijm.toolbox.reflections;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * reflections - 0.9.11.jar （以下简称Reflections库）主要作用如下：
 * 核心功能方面
 * 元数据扫描和索引
 * 可以扫描指定的包路径、类路径等。
 * 对扫描到的类、方法、字段、注解等元数据进行索引 。
 * 元数据查询
 * 查找特定类型的所有子类 。例如有一个父类，可以获取到这个父类的所有子类 。
 * 查找被特定注解标注的元素（类、方法、字段等）。
 * 通过正则表达式获取匹配的资源文件。
 * 获取特定签名（包括参数、参数注解、返回类型等）的方法。
 * 获取所有方法的名字 。
 * 获取代码里所有字段、方法名、构造器的使用情况 。
 * 还可以获取方法参数名以及对方法调用情况的分析等 。
 */
public class ApiModelPropertyScanner {

    public static void scanAndPrintApiModelProperties(String packageName) {
        Reflections reflections = new Reflections(packageName, new FieldAnnotationsScanner());

        Set<Field> fields = reflections.getFieldsAnnotatedWith(Autowired.class);
        List<Field> fieldArr = fields.stream().sorted((o1, o2) -> o1.getDeclaringClass().getName().compareTo(o2.getDeclaringClass().getName())).collect(Collectors.toList());
        Class<?> lastDeclaringClass = fieldArr.get(0).getDeclaringClass();
        for (Field field : fieldArr) {
            Class<?> declaringClass = field.getDeclaringClass();
            Autowired annotation = field.getAnnotation(Autowired.class);
            if (!declaringClass.equals(lastDeclaringClass)) {
                lastDeclaringClass = declaringClass;
                System.out.println("");
            }
            String output = String.format("%s/%s.%s=%s",
                    declaringClass.getPackage().getName(),
                    declaringClass.getSimpleName(),
                    field.getName(),
                    annotation.required());

            System.out.println(output);
        }
    }

    public static void main(String[] args) {
        scanAndPrintApiModelProperties("com.prolog.cs.ops");
    }
}