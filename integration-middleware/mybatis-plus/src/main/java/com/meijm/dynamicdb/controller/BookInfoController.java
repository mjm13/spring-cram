package com.meijm.dynamicdb.controller;

import com.meijm.dynamicdb.entity.BookInfo;
import com.meijm.dynamicdb.mapper.BookInfoMapper;
import com.meijm.dynamicdb.service.BookInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/book")
@RestController
public class BookInfoController {
    @Autowired
    private BookInfoService bookInfoService;

    @RequestMapping("/init")
    public ResponseEntity init() {
        bookInfoService.init();
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }
}
