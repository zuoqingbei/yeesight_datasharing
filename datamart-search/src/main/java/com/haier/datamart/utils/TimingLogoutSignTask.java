package com.haier.datamart.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.haier.datamart.service.ISysVisitorService;

/**
 * 定时注销签证
 * @author: ZhangJiang
 * @date: 2018-12-17 上午10:05:09
 */
@Component
@EnableScheduling // 该注解必须要加
public class TimingLogoutSignTask {
	
	@Autowired
	private ISysVisitorService sysVisitorService;

	/**
	 * 每天凌晨1点执行方法
	 */
//	@Scheduled(fixedRate = 1000 * 3)
	@Scheduled(cron = "0 0 1 * * ?")
	public void reportCurrentByCron() {
		System.out.println(currentTime() + "---注销过期签证任务开始");
		sysVisitorService.timingLogoutSign();
		System.out.println(currentTime() + "---注销过期签证任务结束");
	}

	private String currentTime() {
		return new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(new Date());
	}

}
