package com.meijm.clickhouse.task;

import cn.hutool.core.util.RandomUtil;
import com.meijm.clickhouse.dao.WarehouseStatsDao;
import com.meijm.clickhouse.data.WarehouseStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
@EnableScheduling
public class ScheduledTasks {
    @Autowired
    private WarehouseStatsDao dao;

    //每秒执行一次，测试行合并
    @Scheduled(fixedRate = 1000)
    public void task1() {
        WarehouseStats stats = new WarehouseStats();
        stats.setWarehouse("黄石仓");
        stats.setWarehouseArea("一楼");
        stats.setWarehouseType("东1");
        stats.setTs(new Date((System.currentTimeMillis() / 3600000) * 3600000));
        stats.setVersion(System.currentTimeMillis());
        stats.setFreeSlots(RandomUtil.randomInt(1,1000));
        dao.insert(stats);
    }

/*-- 
建表语句
`default`.warehouse_stats definition
    CREATE TABLE default.warehouse_stats
            (
    `warehouse` String,
    `warehouse_area` String,
    `warehouse_type` String,
    `ts` DateTime,
    `free_slots` UInt32,
    `version` UInt64
            )
    ENGINE = ReplacingMergeTree(version)
    PARTITION BY toStartOfHour(ts)
    ORDER BY (warehouse,
              warehouse_area,
              warehouse_type)
    SETTINGS allow_nullable_key = 1,
            index_granularity = 8192;
            
            */
}
