package com.meijm.log4j2.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RestController
public class TestController {
    @RequestMapping(value = "/index")
    public String test(){
        log.info("aaaa----{}","mm",new NullPointerException("vcc"));
        return "";
    }


    public static void main(String[] args) {
        String stackFrame = "at com.prolog.cs.platform.power.service.UserOnlineServiceImpl.UserOnlineList(UserOnlineServiceImpl.java:223)";
        Pattern pattern = Pattern.compile("at\\s+(com\\.prolog.*?)\\.((\\w+)\\((.*?)\\))");
        Matcher matcher = pattern.matcher(stackFrame);
        Matcher a = Pattern.compile("at (com\\.prolog.*?)\\.(\\w+\\(.*?)").matcher(stackFrame.trim());
        if (a.find()) {
            String str = a.group(1);
            System.out.println(str);

            String str2 = a.group(2);
            System.out.println(str2);
        }
//        if (matcher.find()) {
//            String classPath = matcher.group(1); // 类的完整路径
//            String classMethodLineNumber = matcher.group(2); // 类名、方法名、文件名和行号
//
//            System.out.println("类的完整路径: " + classPath);
//            System.out.println("类名、方法名和行号: " + classMethodLineNumber);
//        }
    }
}
