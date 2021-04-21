package com.meijm.quartz.job;

//开发人员：lpf@sunmnet.com 
//创建时间：2020/9/22 16:36
//方法名：
//详述：
//版本迭代：

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Map;

@Slf4j
public class TestJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        String group = jobExecutionContext.getTrigger().getKey().getGroup();
        String name = jobExecutionContext.getTrigger().getKey().getName();
        Map<String, Object> map = jobExecutionContext.getJobDetail().getJobDataMap().getWrappedMap();

        log.info("name:{},group:{}-----JobDataMap:{}", name, group, map);
    }
}
