package com.meijm.security.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/basic")
public class BasicController {
    //返回当前用户
    @GetMapping("/index")
    public Object index(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
