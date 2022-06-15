package com.meijm.basis.design.application.convert;

import lombok.Data;

@Data
public class PersonA {
    private String name;
    private int age;
    public PersonA(String name, int age){
        this.name = name;
        this.age = age;
    }
}
