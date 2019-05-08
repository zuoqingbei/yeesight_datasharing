package com.haier.datamart.entity;

import java.util.List;
/**
 * 
* @Description: 查询系统下的指标 报表 辅助类
* @date 2018年9月4日 下午3:22:03
* @version V1.0
* @return
 */
public class SysHelp {
	//系统
	private Dict dict;
	//指标
	private List<SearchIndex> searchIndexs;
    //指标个数
	private int indexsize;
	//报表
	private List<SearchReports> reports;
	//报表个数
	private int reportsize;
	
	public int getIndexsize() {
		return indexsize;
	}
	public void setIndexsize(int indexsize) {
		this.indexsize = indexsize;
	}
	public int getReportsize() {
		return reportsize;
	}
	public void setReportsize(int reportsize) {
		this.reportsize = reportsize;
	}
	public Dict getDict() {
		return dict;
	}
	public void setDict(Dict dict) {
		this.dict = dict;
	}
	
	public List<SearchIndex> getSearchIndexs() {
		return searchIndexs;
	}
	public void setSearchIndexs(List<SearchIndex> searchIndexs) {
		this.searchIndexs = searchIndexs;
	}
	public List<SearchReports> getReports() {
		return reports;
	}
	public void setReports(List<SearchReports> reports) {
		this.reports = reports;
	}
	

}
