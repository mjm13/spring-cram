package com.meijm.interview.design.application.convert;

public class Client {
    public static void main(String[] args) {
        PersonA a =  ConvertRegistry.getInstance().convert("xml",null);
        System.out.println(a.toString());
    }
}
