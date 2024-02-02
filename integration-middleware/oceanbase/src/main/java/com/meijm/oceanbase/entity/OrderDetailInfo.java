package com.meijm.oceanbase.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "order_detail_info")
public class OrderDetailInfo {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String skuName;
    private Double skuPrice;
    private Integer count;
    private Date createTime;
    private Date packTime;
    private Integer actualNum;
    private Integer planNum;
    private String waveNo;
    private String customerCode;
    private Integer pickNum;
    private String itemId;
    private Integer state;
}
