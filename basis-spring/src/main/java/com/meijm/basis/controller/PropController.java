package com.meijm.basis.controller;

import cn.hutool.json.JSONUtil;
import com.meijm.basis.bean.ComplexProp;
import com.meijm.basis.event.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class PropController {
    @Autowired
    private ComplexProp complexProp;


    @RequestMapping(value = "complexProp", method = RequestMethod.GET)
    public String complexProp() {
        return JSONUtil.toJsonStr(complexProp);
    }

}
