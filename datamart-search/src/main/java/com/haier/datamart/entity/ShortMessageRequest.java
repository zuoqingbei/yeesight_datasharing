package com.haier.datamart.entity;

public class ShortMessageRequest {
	private String enterpriseid;
	private String loginName;
	private String mobiles;
	private String content;
	private String password;
	public String getEnterpriseid() {
		return enterpriseid;
	}
	public void setEnterpriseid(String enterpriseid) {
		this.enterpriseid = enterpriseid;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getMobiles() {
		return mobiles;
	}
	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "enterpriseid=" + enterpriseid + "&loginName=" + loginName + "&mobiles=" + mobiles
				+ "&content=" + content + "&password=" + password;
	}
	public ShortMessageRequest(String enterpriseid, String loginName, String mobiles, String content, String password) {
		super();
		this.enterpriseid = enterpriseid;
		this.loginName = loginName;
		this.mobiles = mobiles;
		this.content = content;
		this.password = password;
	}
	public ShortMessageRequest() {
	}
	
	
}
