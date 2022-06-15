package com.meijm.basis.design.create.factory.lib;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductB2 implements ProductB {

    @Override
    public void show() {
        log.info("show product B2");
    }
}
