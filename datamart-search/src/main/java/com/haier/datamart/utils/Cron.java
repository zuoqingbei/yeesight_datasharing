package com.haier.datamart.utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.derby.tools.sysinfo;
import org.apache.tools.ant.Task;
import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

public class Cron {
	//定义调度器
private static	SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    /**
     * 
     * @author lixiaoyi
     * @date 2018年8月6日 下午8:23:59
     * @TODO
     */
	public static void task( String id,String cron,String taskname,String status) throws SchedulerException{
		System.out.println(id+"lllll"+cron);

    Scheduler scheduler = schedulerFactory.getScheduler();
    //写入任务
    JobDetail jobDetail = JobBuilder.newJob(TimeTask.class)
            .withIdentity(taskname, "group"+taskname).usingJobData("id", id).usingJobData("status", status).build();
    
    //作业触发器
    try {
		CronExpression cronExpression =new CronExpression(cron);
		 CronTrigger cronTrigger =TriggerBuilder.newTrigger().
				    withIdentity("cronTrigger"+taskname, "group"+taskname).//定义name跟group
				    withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();
				    //注册作业和触发器
				    scheduler.scheduleJob(jobDetail, cronTrigger);
				    //开始调度
				    if(!scheduler.isShutdown()) {
				    	scheduler.start();
				    }
				    
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   
   // scheduler.clear();
   /* TriggerKey triggerKey = cronTrigger.getKey();
    scheduler.pauseTrigger(triggerKey);// 停止触发器  
    scheduler.unscheduleJob(triggerKey);// 移除触发器  
*/    
   
    
	}
	public static void main(String[] args) {
		try {
		 	task("d266cfb210d046b6ad761026fae8321c", "*/5 * * * * ?", "task", "1");
		  try {
			Thread.sleep(10000);
			remove("task");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void  remove(String taskname) throws SchedulerException{
		Scheduler sched =  schedulerFactory.getScheduler();
		/*TriggerKey key = new TriggerKey("cronTrigger"+taskname);
		sched.pauseTrigger(key);//停止触发器
*/		GroupMatcher<JobKey> matcher = GroupMatcher.groupEquals("group"+taskname);
        Set<JobKey> jobkeySet = sched.getJobKeys(matcher);
        List<JobKey> jobkeyList = new ArrayList<JobKey>();
        jobkeyList.addAll(jobkeySet);
        sched.deleteJobs(jobkeyList);
        System.out.println("change");
	}
}

