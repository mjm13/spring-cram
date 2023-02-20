package com.meijm.toolbox.test;

import cn.hutool.core.date.DateBetween;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

public class DateDiff {
    public static void main(String[] args) {
        //0100030005开始
        //到0705耗时11765
        //到0706耗时3333
        //到顶升耗时290
        //到0705耗时3323
        //到0105耗时9552
        //到0110耗时7318

        String ran1 = "2023-02-16 11:48:33.2023"; //0905-开始
        String ran11 = "2023-02-16 11:48:40.5200";    //0905-结束
        long ran1diff  =DateBetween.create(DateUtil.parse(ran1),DateUtil.parse(ran11)).between(DateUnit.MS);
        System.out.println(ran1diff);
    }
}
