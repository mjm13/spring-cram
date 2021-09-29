package com.meijm.basis.bean;

import cn.hutool.core.date.DateUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class DemoVo {
    private Date start;
    private Date end;

    public static void main(String[] args) {
       DemoVo vo1 = new DemoVo();
       DemoVo vo2 = new DemoVo();
       List<DemoVo> vos = new ArrayList<>();


    }
}
