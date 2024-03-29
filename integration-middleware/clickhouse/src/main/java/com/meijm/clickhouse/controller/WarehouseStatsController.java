package com.meijm.clickhouse.controller;

import cn.hutool.core.util.RandomUtil;
import com.meijm.clickhouse.dao.WarehouseStatsDao;
import com.meijm.clickhouse.data.WarehouseStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
public class WarehouseStatsController {
    @Autowired
    private WarehouseStatsDao warehouseStatsDao;

    @GetMapping("/add")
    public String add() {
        
        WarehouseStats stats = new WarehouseStats();
        stats.setWarehouse("黄石仓");
        stats.setWarehouseArea("一楼");
        stats.setWarehouseType("东1");
        stats.setTs(new Date());
        stats.setVersion(System.currentTimeMillis());
        stats.setFreeSlots(RandomUtil.randomInt(1,1000));
        warehouseStatsDao.insert(stats);
        return "添加成功";
    }

    @GetMapping("/query")
    public List<WarehouseStats> query() {
        return warehouseStatsDao.selectByMap(new HashMap<>());
    }

}
