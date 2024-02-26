package com.meijm.mvnpackage.controller;

import com.meijm.mvnpackage.entity.Enterpise;
import com.meijm.mvnpackage.mapper.EnterpiseMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/enterpise")
@RestController
public class EnterpiseController {
    @Resource
    private EnterpiseMapper enterpiseMapper;

    @RequestMapping("/index")
    public ResponseEntity index() {
        List<Enterpise> enterpiseList = enterpiseMapper.findEnterpise();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(enterpiseList);
    }
}
