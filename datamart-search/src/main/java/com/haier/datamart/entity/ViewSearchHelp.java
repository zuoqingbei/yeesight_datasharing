package com.haier.datamart.entity;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.activerecord.Model;

public class ViewSearchHelp {

	private List<ViewSearch> viewSearchs;
	private String count;
	public List<ViewSearch> getViewSearchs() {
		return viewSearchs;
	}
	public void setViewSearchs(List<ViewSearch> viewSearchs) {
		this.viewSearchs = viewSearchs;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	
	
}
