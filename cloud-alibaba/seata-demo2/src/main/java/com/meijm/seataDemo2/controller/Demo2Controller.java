package com.meijm.seataDemo2.controller;


import com.meijm.seataDemo2.service.Demo2Service;
import io.seata.core.context.RootContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@Log4j2
@RestController
@RequestMapping("/demo2")
public class Demo2Controller {
    @Autowired
    private Demo2Service demo2Service;

    @GetMapping("/updateDemo2")
    public Boolean updateDemo2(HttpServletRequest request) {
        log.info("request.getHeader(RootContext.KEY_XID):{}", request.getHeader(RootContext.KEY_XID));
        log.info("request.getHeader(RootContext.KEY_XID.toLowerCase()):{}", request.getHeader(RootContext.KEY_XID.toLowerCase()));
        demo2Service.updateDemo2();
        return Boolean.TRUE;
    }

    @GetMapping("/updateDemo2ThrowException")
    public Boolean updateDemo2ThrowException(HttpServletRequest request) {
        log.info("request.getHeader(RootContext.KEY_XID):{}", request.getHeader(RootContext.KEY_XID));
        log.info("request.getHeader(RootContext.KEY_XID.toLowerCase()):{}", request.getHeader(RootContext.KEY_XID.toLowerCase()));
        demo2Service.updateDemo2ThrowException();
        return Boolean.TRUE;
    }
}
