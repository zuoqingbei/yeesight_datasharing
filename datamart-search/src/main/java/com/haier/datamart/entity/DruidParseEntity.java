package com.haier.datamart.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DruidParseEntity {
	public String sql;
	public AdminDatasourceConfig dataSourceConfig;
	public List<String> tables=new ArrayList<String>();//表
	public List<Map<String,Object>> columns=new ArrayList<Map<String,Object>>();//字段
	public List<DataInterfaceTableRelative> relatives=new ArrayList<DataInterfaceTableRelative>();
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public List<DataInterfaceTableRelative> getRelatives() {
		return relatives;
	}
	public void setRelatives(List<DataInterfaceTableRelative> relatives) {
		this.relatives = relatives;
	}
	public AdminDatasourceConfig getDataSourceConfig() {
		return dataSourceConfig;
	}
	public void setDataSourceConfig(AdminDatasourceConfig dataSourceConfig) {
		this.dataSourceConfig = dataSourceConfig;
	}
	public List<String> getTables() {
		return tables;
	}
	public List<Map<String, Object>> getColumns() {
		return columns;
	}
	public void setColumns(List<Map<String, Object>> columns) {
		this.columns = columns;
	}
	public void setTables(List<String> tables) {
		this.tables = tables;
	}
	
	
	

}
