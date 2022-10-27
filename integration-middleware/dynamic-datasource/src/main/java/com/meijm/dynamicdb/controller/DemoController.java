package com.meijm.dynamicdb.controller;

import com.meijm.dynamicdb.entity.Test;
import com.meijm.dynamicdb.service.DemoService;
import com.meijm.dynamicdb.vo.DataSourceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RequestMapping("/demo")
@RestController
public class DemoController {

    @Autowired
    private DemoService demoService;

    @PostMapping("/createDataSource")
    public String createDataSource(DataSourceVo vo) {
        demoService.createDataSource(vo);
        return "操作成功";
    }

    @GetMapping("/getDataSourceKeys")
    public Set<String> getDataSourceKeys() {
        return demoService.getDataSourceKeys();
    }

    @GetMapping("/changeSourceAndQuery")
    public List<Test> changeSourceAndQuery(String poolName) {
        return demoService.changeSourceAndQuery(poolName);
    }


}
