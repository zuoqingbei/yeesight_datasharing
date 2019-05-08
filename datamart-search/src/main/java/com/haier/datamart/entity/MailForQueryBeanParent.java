package com.haier.datamart.entity;

import java.util.List;

public class MailForQueryBeanParent {
	 private static final long serialVersionUID = 1L;
	 private MailSettingInfo mailBaseInfo;
	 private List<MailForQueryBeanTableMap> tableMapList;
	public MailSettingInfo getMailBaseInfo() {
		return mailBaseInfo;
	}
	public void setMailBaseInfo(MailSettingInfo mailBaseInfo) {
		this.mailBaseInfo = mailBaseInfo;
	}
	public List<MailForQueryBeanTableMap> getTableMap() {
		return tableMapList;
	}
	public void setTableMap(List<MailForQueryBeanTableMap> tableMap) {
		this.tableMapList = tableMap;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "MailForQueryBeanParent [mailBaseInfo=" + mailBaseInfo + ", tableMapList=" + tableMapList + "]";
	}
	 
}
