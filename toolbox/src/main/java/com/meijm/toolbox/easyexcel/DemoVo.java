package com.meijm.toolbox.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class DemoVo {
    @ExcelProperty("编号")
    private String id;
    @ExcelProperty("名称")
    private String name;
}
