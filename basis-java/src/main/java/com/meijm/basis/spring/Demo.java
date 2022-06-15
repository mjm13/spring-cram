package com.meijm.basis.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Demo {
    public Integer division(String a,String b){
        int aint = Integer.valueOf(a);
        int bint = Integer.valueOf(b);
        return aint/bint;
    }

}
