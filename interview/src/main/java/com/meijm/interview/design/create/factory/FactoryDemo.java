package com.meijm.interview.design.create.factory;

public class FactoryDemo {
    public static void main(String[] args) {
        /**
         * 简单工厂
         */
        SimpleFactory.getProduct("A").use();
        SimpleFactory.getProduct("B").use();
        /**
         * 工厂方法
         */
        FactoryMethod factoryA = new ProductAFactoryMethod();
        FactoryMethod factoryB = new ProductBFactoryMethod();
        factoryA.createProduct().use();
        factoryB.createProduct().use();

        /**
         * 抽象工厂
         */
        AbstractFactory factory1 = new Product1Factory();
        AbstractFactory factory2 = new Product2Factory();
        factory1.createProductA().use();
        factory1.createProductB().show();
        factory2.createProductA().use();
        factory2.createProductB().show();
    }
}
