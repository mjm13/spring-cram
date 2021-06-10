package com.meijm.interview.design.create.factory.lib;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductB1 implements ProductB {
    @Override
    public void show() {
        log.info("show product B1");
    }
}
