package com.haier.datamart.entity;

import java.util.List;
import java.util.Map;

public class MailForQueryBeanTableMap {
	private static final long serialVersionUID = 1L;
	private String title;//表头
	private String titleColor;//表头颜色
	private List<Map<String, Object>>  content;//基础数据
	private List<Map<String, Object>>  bodyStyle;//列名和颜色的映射关系
	private List<Map<String, Object>> titleColumnMap;//列名和展示名名映射关系
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public List<Map<String, Object>> getBodyStyle() {
		return bodyStyle;
	}
	public void setBodyStyle(List<Map<String, Object>> bodyStyle) {
		this.bodyStyle = bodyStyle;
	}
	public String getTitleColor() {
		return titleColor;
	}
	public void setTitleColor(String titleColor) {
		this.titleColor = titleColor;
	}
	public List<Map<String, Object>> getContent() {
		return content;
	}
	public void setContent(List<Map<String, Object>> content) {
		this.content = content;
	}
	public List<Map<String, Object>> getTitleColumnMap() {
		return titleColumnMap;
	}
	public void setTitleColumnMap(List<Map<String, Object>> titleColumnMap) {
		this.titleColumnMap = titleColumnMap;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "MailForQueryBeanTableMap [title=" + title + ", titleColor=" + titleColor + ", content=" + content
				+ ", bodyStyle=" + bodyStyle + ", titleColumnMap=" + titleColumnMap + "]";
	}
	
	
	
}
