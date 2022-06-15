package com.meijm.basis.design.create.factory;

import com.meijm.basis.design.create.factory.lib.ProductA;
import com.meijm.basis.design.create.factory.lib.ProductA1;
import com.meijm.basis.design.create.factory.lib.ProductA2;

/**
 * 简单工厂：具体工厂（一个）、抽象产品、具体产品
 * <p>
 * 适用场景:
 * 同一类型多种具体实现，依据参数创建实例
 * jdk 中 DateFormat.getDateInstance()
 * spring 中 AnnotationConfigApplicationContext.getBean()
 */
public class SimpleFactory {
    public static ProductA getProduct(String type) {
        ProductA product = null;
        if ("A".equals(type)) {
            product = new ProductA1();
        } else if ("B".equals(type)) {
            product = new ProductA2();
        }
        return product;
    }
}

