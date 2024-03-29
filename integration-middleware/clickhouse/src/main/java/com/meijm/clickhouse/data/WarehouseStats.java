package com.meijm.clickhouse.data;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("warehouse_stats")
@Data
public class WarehouseStats {
    private Long version;
    private String warehouse;
    private String warehouseArea;
    private String warehouseType;
    private int freeSlots;
    private Date ts;
}
