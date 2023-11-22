package com.meijm.jacksoncrypto.controller;

import com.meijm.jacksoncrypto.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexController {
    @GetMapping("/crypto")
    public User crypto(){
        User user = new User();
        user.setName("mjm");
        user.setAddress("aaa-bbb-ccc");
        user.setPwd("temp");
        return user;
    }
}
