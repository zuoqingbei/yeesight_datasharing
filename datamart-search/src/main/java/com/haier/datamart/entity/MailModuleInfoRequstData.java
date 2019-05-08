package com.haier.datamart.entity;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * 邮件抬头信息
 * @author zuoqb123
 * @date 2019-01-10
 */
 
public class MailModuleInfoRequstData  { 
	
	 MailModuleInfo  mailModuleInfo ;
	
	List<MailPlatInfo> mailPlatInfoList;

	@Override
	public String toString() {
		return "MailModuleInfoRequstData [mailModuleInfo=" + mailModuleInfo + ", mailPlatInfoList=" + mailPlatInfoList
				+ "]";
	}

	public MailModuleInfo getMailModuleInfo() {
		return mailModuleInfo;
	}

	public void setMailModuleInfo(MailModuleInfo mailModuleInfo) {
		this.mailModuleInfo = mailModuleInfo;
	}

	public List<MailPlatInfo> getMailPlatInfoList() {
		return mailPlatInfoList;
	}

	public void setMailPlatInfoList(List<MailPlatInfo> mailPlatInfoList) {
		this.mailPlatInfoList = mailPlatInfoList;
	}
	
	
}
