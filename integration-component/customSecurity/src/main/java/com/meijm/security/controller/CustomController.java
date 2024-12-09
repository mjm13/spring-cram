package com.meijm.security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/custom")
public class CustomController {
    //返回当前用户
    @GetMapping("/index")
    public Object index(){
        System.out.println("11111");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("22222");
        return authentication;
    }
}
