package com.meijm.mongodb.data;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(value = "order_detail_info")
public class OrderDetailInfo {
    @Id
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
