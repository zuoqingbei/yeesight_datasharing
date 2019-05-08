package com.haier.datamart.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.haier.datamart.entity.AdminDatasourceConfig;
import com.haier.datamart.entity.User;
import com.haier.datamart.service.IAdminDatasourceConfigService;
@Configuration
@Component
@EnableScheduling // 该注解必须要加
public class ScheduleTask   {
	@Autowired
	private IAdminDatasourceConfigService configService;
	@Autowired
	private IAdminDatasourceConfigService  datasourceConfigService;
	public void scheduleTest(){
		 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("我是定时任务类，现在的执行时间是：：：：：：" + sdf.format(new Date())+"即将执行表扫描");
        List<AdminDatasourceConfig> configs = configService.getAll();
        System.out.println(configs);
		for(AdminDatasourceConfig config:configs){
			User user=new User();
			user.setId(config.getUserId());
			Thread rthread = new Thread(new ITableScanUtils(config.getId(), user,datasourceConfigService));
			rthread.start();
			 
		}
    }
	
}
