package com.meijm.interview.spring;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
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
