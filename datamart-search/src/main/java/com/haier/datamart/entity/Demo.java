package com.haier.datamart.entity;

import java.util.List;

public class Demo {
	
	private String name;
	
	private String sex;
	
	private int age;
	
	private String desc;
	
	private List<DemoChild> list;

	public List<DemoChild> getList() {
		return list;
	}

	public void setList(List<DemoChild> list) {
		this.list = list;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	

}
