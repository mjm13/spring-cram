package com.meijm.basis.design.create.factory;


import com.meijm.basis.design.create.factory.lib.ProductA;
import com.meijm.basis.design.create.factory.lib.ProductA1;
import com.meijm.basis.design.create.factory.lib.ProductA2;

/**
 *  工厂方法：抽象工厂、具体工厂（多个）、抽象产品、具体产品
 *
 *   将创建方法延迟到子类
 *   例子 org.springframework.beans.factory.FactoryBean
 */
public interface FactoryMethod {
    ProductA createProduct();
}

class ProductAFactoryMethod implements FactoryMethod {
    @Override
    public ProductA createProduct() {
        return new ProductA1();
    }
}

class ProductBFactoryMethod implements FactoryMethod {
    @Override
    public ProductA createProduct() {
        return new ProductA2();
    }
}