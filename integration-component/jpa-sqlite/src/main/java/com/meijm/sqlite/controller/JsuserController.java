package com.meijm.sqlite.controller;

import cn.hutool.core.util.RandomUtil;
import com.meijm.sqlite.dao.JsuserDao;
import com.meijm.sqlite.data.Jsuser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class JsuserController {
    @Autowired
    private JsuserDao jsuserDao;

    @GetMapping("/add")
    public String add() {
        Jsuser jsuser = new Jsuser();
        jsuser.setAge(RandomUtil.randomInt());
        jsuser.setName(RandomUtil.randomString(5));
        jsuserDao.save(jsuser);
        return "添加成功";
    }

    @GetMapping("/query")
    public List<Jsuser> query() {
        return jsuserDao.findAll();
    }

}
