package com.meijm.security.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/form")
public class FormController {
    //返回当前用户
    @RequestMapping("/index")
    public Object index(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
