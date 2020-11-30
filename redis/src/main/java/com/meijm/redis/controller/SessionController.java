package com.meijm.redis.controller;

import com.meijm.redis.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/session")
public class SessionController {
    @Autowired
    private CommonService commonService;
    @GetMapping("/set")
    public void set(HttpSession session) {
        List<Map<String,String>> datas =  commonService.getDatas();
        session.setAttribute("datas",datas);
    }
    @GetMapping("/get")
    public List<Map<String, String>> get(HttpSession session) {
        return (List<Map<String,String>>)session.getAttribute("datas");
    }
}
