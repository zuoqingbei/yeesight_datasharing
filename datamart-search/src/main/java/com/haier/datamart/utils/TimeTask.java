package com.haier.datamart.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


public class TimeTask implements Job{

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);//用于解决job类中不能用注解问题
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
	     String id = dataMap.getString("id");
	     String status = dataMap.getString("status");
	        //进行sql
		// int a=	settingInfoService.changestatus(id,"0");
		 String CONNECTION_URL = "jdbc:mysql://39.108.215.138:3306/datamart2?characterEncoding=utf8";
			String user = "root";
			String pwd = "root";
			Connection conn = null;
		 PreparedStatement  dropPstmt =null;
		 String sql=null;
		 if ("0".equals(status)) {//关闭
			  sql=" update entering_table_setting_info set status='"+status+"',valid_begin_time=null where id='"+id+"'";
		}
		 if ("1".equals(status)) {//开启
			  sql=" update entering_table_setting_info set status='"+status+"',valid_begin_time=NOW() where id='"+id+"'";
		}
			
			conn=ExcelConnection.getConn(CONNECTION_URL,user,pwd);
			try {
				dropPstmt=(PreparedStatement) conn.prepareStatement(sql);
				int rs=dropPstmt.executeUpdate(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					if(conn!=null&&!conn.isClosed()){
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			System.out.println("执行了 ");
		
	}
	
/*	public void  get(String id){
		 int a=	dataSupplementUtils.settingInfoService.changestatus(id,"0");
	}*/
}
