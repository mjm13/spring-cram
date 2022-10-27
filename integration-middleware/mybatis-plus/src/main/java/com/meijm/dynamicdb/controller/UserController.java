package com.meijm.dynamicdb.controller;

import com.meijm.dynamicdb.entity.User;
import com.meijm.dynamicdb.mapper.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {
    @Resource
    private UserMapper userMapper;

    @RequestMapping("/index")
    public ResponseEntity index() {
        List<User> userList = userMapper.findUsers();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userList);
    }
}
