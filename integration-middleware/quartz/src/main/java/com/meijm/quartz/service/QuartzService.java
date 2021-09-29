package com.meijm.quartz.service;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;



/**
 * @ClassName QuartzService
 * @Description TDO
 * @Author zhao.zhilue
 * @Date 2019/7/31 15:49
 * @Version 1.0
 **/
@Slf4j
@Service
public class QuartzService {
	@Autowired
	private Scheduler scheduler;


	@PostConstruct
	public void startScheduler() {
		try {
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}


	public void saveJob(String name,String group,String cron,Class clazz, Map<String, String> jobData) {
		try {
			JobDetail jobDetailTemp = scheduler.getJobDetail(JobKey.jobKey(name, group));
			if (jobDetailTemp != null) {
				scheduler.deleteJob(new JobKey(name, group));
				return;
			}
			// 创建jobDetail实例，绑定Job实现类
			// 指明job的名称，所在组的名称，以及绑定job类
			// 任务名称和组构成任务key
			JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(name, group).storeDurably().build();
			// 设置job参数
			if (jobData != null && jobData.size() > 0) {
				jobDetail.getJobDataMap().putAll(jobData);
			}
			// 定义调度触发规则
			// 使用cornTrigger规则
			// 触发器key
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity(name, group)
					.startAt(DateBuilder.futureDate(1, IntervalUnit.SECOND))
					.withSchedule(CronScheduleBuilder.cronSchedule(cron)).startNow().build();
			// 把作业和触发器注册到任务调度中
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * 获取所有计划中的任务列表
	 *
	 * @return
	 */
	public List<Map<String,Object>> queryAllJob() {
		List<Map<String,Object>> jobList = new ArrayList<>();
		try {
			GroupMatcher<JobKey> matcher = GroupMatcher.groupContains("");
			Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
			for (JobKey jobKey : jobKeys) {
				Map<String, Object> map = new HashMap<>();
				map.put("name", jobKey.getName());
				map.put("group", jobKey.getGroup());
				JobDetail detail = scheduler.getJobDetail(jobKey);
				map.putAll(detail.getJobDataMap());

				CronTrigger trigger = (CronTrigger) scheduler.getTrigger(new TriggerKey(jobKey.getName(), jobKey.getGroup()));
				Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
				map.put("status", triggerState.name());
				map.put("cron", trigger.getCronExpression());
				jobList.add(map);
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return jobList;
	}
}
