package com.meijm.interview.design.create.builder;

import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;

import java.util.Calendar;

import static java.util.Calendar.SUNDAY;

/**
 * 建造者模式
 * 与工厂模式的区别是更加关注装配顺序
 *
 * 将产品与具体的创造过程解耦
 * 具体实例,springSecurity
 * org.springframework.security.config.annotation.web.builders.HttpSecurity
 *
 * java.lang.AbstractStringBuilder#append(java.lang.Object)
 * Calendar.Builder
 */
public class BuilderDemo {
    public static void main(String[] args) {
        Calendar cal = new Calendar.Builder().setCalendarType("iso8601")
                .setWeekDate(2013, 1, SUNDAY).build();
    }
}
