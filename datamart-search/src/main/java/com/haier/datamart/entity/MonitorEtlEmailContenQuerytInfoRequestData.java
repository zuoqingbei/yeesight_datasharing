package com.haier.datamart.entity;

import java.util.List;

public class MonitorEtlEmailContenQuerytInfoRequestData {
	String templateId;
	List<MonitorEtlEmailContentQueryInfo> dataList;

	public String getTemplateId() {
		return templateId;
	}

	public void setMailId(String templateId) {
		this.templateId = templateId;
	}

	public List<MonitorEtlEmailContentQueryInfo> getDataList() {
		return dataList;
	}

	public void setDataList(List<MonitorEtlEmailContentQueryInfo> dataList) {
		this.dataList = dataList;
	}
}
