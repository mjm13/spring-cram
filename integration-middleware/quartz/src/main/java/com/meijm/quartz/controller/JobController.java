package com.meijm.quartz.controller;

import com.meijm.quartz.job.TestJob;
import com.meijm.quartz.service.QuartzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/job")
public class JobController {
    @Autowired
    private QuartzService quartzService;

    @RequestMapping(value = "/list")
    @ResponseBody
    public List<Map<String, Object>> jobList(String keyword) {
        return quartzService.queryAllJob();
    }

    @RequestMapping(value = "/saveJob1")
    @ResponseBody
    public List<Map<String, Object>> saveJob1(){
        Map<String,String> map = new HashMap<>();
        map.put("version","2");
        quartzService.saveJob("test","mjm","0/30 * * * * ? *", TestJob.class,map);
        return quartzService.queryAllJob();
    }

    @RequestMapping(value = "/saveJob2")
    @ResponseBody
    public List<Map<String, Object>> saveJob2(){
        Map<String,String> map = new HashMap<>();
        quartzService.saveJob("test1","mjm","0/30 * * * * ? *", TestJob.class,map);
        return quartzService.queryAllJob();
    }


    public static void main(String[] args) {
        System.out.println(JobController.class.getName());
    }
}
