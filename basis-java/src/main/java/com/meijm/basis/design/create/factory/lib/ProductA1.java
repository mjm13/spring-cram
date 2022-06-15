package com.meijm.basis.design.create.factory.lib;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductA1 implements ProductA {
    public void use() {
        log.info("use product A1");
    }
}
