package com.meijm.dynamicdb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("bookinfo")
public class BookInfo {
    /**
     * 补全信息查询
     * https://www.bing.com/search?q=site%3Abook.douban.com++%E5%B7%B4%E6%AF%94%E4%BC%A6%E5%AF%8C%E7%BF%81%E7%9A%84%E8%B4%A2%E5%AF%8C%E8%AF%BE%E2%80%94%E2%80%94%E4%B8%80%E6%9C%AC%E4%B9%A6%E8%AF%BB%E6%87%82%E6%8A%95%E8%B5%84%E7%90%86%E8%B4%A2%E6%99%BA%E6%85%A7
     */
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
