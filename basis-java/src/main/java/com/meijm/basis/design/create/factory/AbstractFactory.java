package com.meijm.basis.design.create.factory;

import com.meijm.basis.design.create.factory.lib.*;
/**
 *  抽象工厂：抽象工厂、具体工厂（多个）、抽象产品(多个)、具体产品
 *
 *   由子类创建一系列具体的产品
 *   例子 java.sql.Connection
 */
public interface AbstractFactory {
    ProductA createProductA();
    ProductB createProductB();
}
class Product1Factory implements AbstractFactory {
    @Override
    public ProductA createProductA() {
        return new ProductA1();
    }
    @Override
    public ProductB createProductB() {
        return new ProductB1();
    }
}

class Product2Factory implements AbstractFactory {
    @Override
    public ProductA createProductA() {
        return new ProductA2();
    }
    @Override
    public ProductB createProductB() {
        return new ProductB2();
    }
}

