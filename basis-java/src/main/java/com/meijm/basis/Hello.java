package com.meijm.basis;

public class Hello {
    public static void main(String[] args) {
        System.out.println("1111111111111111111");
        test:{
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
                if(i == 5){
                    break test;
                }
            }
        }
        System.out.println("222222222222222222222");
    }
}
