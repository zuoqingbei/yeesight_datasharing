package com.haier.datamart.entity;

import java.util.List;

public class IndexAddHelp {
	
	private String dataId;
	private String contentId;
	private  String dbName;
	private String tabelName;
	private List<AdminDataContentDetail> detail;
	
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getTabelName() {
		return tabelName;
	}
	public void setTabelName(String tabelName) {
		this.tabelName = tabelName;
	}
	public String getDataId() {
		return dataId;
	}
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	public String getContentId() {
		return contentId;
	}
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	public List<AdminDataContentDetail> getDetail() {
		return detail;
	}
	public void setDetail(List<AdminDataContentDetail> detail) {
		this.detail = detail;
	}
	
	

}
