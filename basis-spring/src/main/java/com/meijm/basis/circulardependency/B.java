package com.meijm.basis.circulardependency;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class B {
    public B(){
        log.info("初始化B");
    }
    @Autowired
    private A a;
}
