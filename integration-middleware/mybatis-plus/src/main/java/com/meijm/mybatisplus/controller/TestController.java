package com.meijm.mybatisplus.controller;

import com.meijm.mybatisplus.entity.User;
import com.meijm.mybatisplus.mapper.AnalysisResultMapper;
import com.meijm.mybatisplus.mapper.UserMapper;
import com.meijm.mybatisplus.vo.AnalysisResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/test")
@RestController
public class TestController {
    @Autowired
    private AnalysisResultMapper analysisResultMapper;

    @RequestMapping("/index")
    public ResponseEntity index() {
        List<AnalysisResultVO> datas = analysisResultMapper.findAnalysisResult();
        return ResponseEntity.ok(datas);
    }
}
