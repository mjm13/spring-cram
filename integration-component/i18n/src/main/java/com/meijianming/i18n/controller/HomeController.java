package com.meijianming.i18n.controller;

import com.meijianming.i18n.config.DBMessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private DBMessageSource messageSource;

    @RequestMapping("/")
    public String welcome() {
        String temp = messageSource.getMessage("home.welcome", new Object[]{"aaa"});
        System.out.println(temp);
        return "index";
    }


    @RequestMapping("/loadI18N")
    @ResponseBody
    public Map<String, String> loadI18N(String lng, String ns) {
        System.out.println("lng:" + lng);
        System.out.println("ns:" + ns);
        Map<String, String> map = new HashMap<>();
        map.put("home.welcome", "Welcome {0} ");
        map.put("home.info", "This page is displayed in English.");
        map.put("home.changelanguage", "Supported languages : ");
        map.put("home.lang.en", "English");
        map.put("home.lang.de", "German");
        map.put("home.lang.zh", "Chinese");

        Map<String, Object> result = new HashMap<>();
        result.put(lng, map);
//		map.put("home.welcome","Welcome {0}.");
//		map.put("home.info","Diese Seite wird in deutscher Sprache angezeigt.");
//		map.put("home.changelanguage","Unterstützte Sprachen : ");
//		map.put("home.lang.en","Englisch");
//		map.put("home.lang.de","Deutsch");
//		map.put("home.lang.zh","Chinesisch");
//		map.put("home.welcome","Welcome {0}.");
//		map.put("home.info","此頁面以中文顯示.");
//		map.put("home.changelanguage","支持的語言  : ");
//		map.put("home.lang.en","英語");
//		map.put("home.lang.de","德語");
//		map.put("home.lang.zh","普通話");
        return map;
    }


}
