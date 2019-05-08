package com.haier.datamart.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.haier.datamart.entity.AdminDataContent;

public class UrlDatabase {
	  public  String  subString(String url) {  
		    String tempStr=StringUtils.substringAfter(url, "//");
		    String StrDatabase=StringUtils.substringAfter(tempStr, "/");
			return StrDatabase;
	     
	    }  
	public static void main(String[] args) {
		 List<AdminDataContent> tableList = new ArrayList<AdminDataContent>();
		 AdminDataContent adminDataContent=new AdminDataContent();
		 adminDataContent.setId("111111");
		 tableList.add(adminDataContent);
		 tableList.remove(adminDataContent);
		 System.out.println(tableList);
	}
}
