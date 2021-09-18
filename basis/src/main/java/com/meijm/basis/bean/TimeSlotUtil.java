package com.meijm.basis.bean;

import cn.hutool.core.date.DateUtil;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * 判断多个时间段是否出现重叠
 *
 * @author cavancao
 */
public class TimeSlotUtil {

    public static boolean checkOverlap(List<String> list) {
        if (list.isEmpty() || list.size() == 1) {
            return false;
        }
        Collections.sort(list);//排序ASC 
        AtomicBoolean flag = new AtomicBoolean(false);
        //初始化时间
        List<Map<String, Date>> dateList = list.stream().map(s -> {
            String[] itime = s.split("-");
            Date begin = DateUtil.parseTime(itime[0] + ":00");
            Date end = DateUtil.parseTime(itime[1] + ":00");
            Map<String, Date> data = new HashMap<>();
            data.put("begin", begin);
            data.put("end", end);
            return data;
        }).collect(Collectors.toList());

        List<Date> list1 = dateList.stream().sorted(Comparator.comparing(o -> o.get("begin")))
                .flatMap(map -> {
                    List<Date> tempList = new ArrayList<>();
                    tempList.add(map.get("begin"));
                    tempList.add(map.get("end"));
                    return tempList.stream();
                }).collect(Collectors.toList());
        List<Date> list2 = list1.stream().sorted().collect(Collectors.toList());
        System.out.println(list1.toString());
        System.out.println(list2.toString());
        return !list1.toString().equals(list2.toString());
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();

        list.add("08:00-08:30");
        list.add("09:00-12:00");
        list.add("13:00-16:30");
        list.add("16:40-17:00");
        list.add("18:00-20:00");

        boolean flag = checkOverlap(list);

        System.out.println("\n当前时间段列表重叠验证结果为：" + flag);
    }
} 