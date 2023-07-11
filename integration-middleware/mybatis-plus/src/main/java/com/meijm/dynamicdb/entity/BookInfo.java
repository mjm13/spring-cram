package com.meijm.dynamicdb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("bookinfo")
public class BookInfo {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String path;
    private Long size;
    private String author;
    private String nation;
    private String intro;
    private String suffix;
}
