package com.meijm.basis.test;

public class InnerTest {
    public String a;
    public class InnerMeiJM{
        public String b;
    }

    public static void main(String[] args) {
        Integer a = null;
        if(1L == a){
            System.out.println(11);
        }
        System.out.println(22);
    }
}
