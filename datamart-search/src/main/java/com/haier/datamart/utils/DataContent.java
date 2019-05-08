package com.haier.datamart.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.haier.datamart.entity.AdminDataContent;
import com.haier.datamart.entity.AdminDatasourceConfig;
import com.haier.datamart.service.IAdminDatasourceConfigService;
/**
 * 根据AdminDatasourceConfig对象获取表名
* @author doushuihai  
* @date 2018年6月6日下午4:23:07  
* @TODO
 */

public class DataContent {
	
	public  List<AdminDataContent> getDataContent(AdminDatasourceConfig datasourceConfig,Connection conn){
	     List<Map<String,Object>> tables  = new ArrayList<Map<String,Object>>();
	     List<AdminDataContent> tableList = new ArrayList<AdminDataContent>();
	     try {	
	    	 String url=datasourceConfig.getDbUrl();  
	         String username=datasourceConfig.getDbName();   
	         String password= datasourceConfig.getDbPassword();  
	         String databasetype =datasourceConfig.getDbType(); 
	         String dbname=new UrlDatabase().subString(url);
	         tables=DBMSMetaUtil.listTables(databasetype, url, username, password, dbname);
            for (Map<String, Object> map:tables) {
            	AdminDataContent adminDataContent = new AdminDataContent();
            	adminDataContent.setId(UUIDUtils.getUuid());
            	adminDataContent.setDataSourceId(datasourceConfig.getId());
            	if(map.get("TABLE_SCHEM")!=null){
            		adminDataContent.setTableName(map.get("TABLE_SCHEM")+"."+map.get("TABLE_NAME"));
            	}else if(StringUtils.isNotBlank(dbname)){
            		adminDataContent.setTableName(dbname+"."+map.get("TABLE_NAME"));
            	}else{
            		adminDataContent.setTableName(map.get("TABLE_NAME")+"");
            	}
            	adminDataContent.setRemarks(map.get("REMARKS")==null?"":map.get("REMARKS")+"");
            	System.out.println(map.get("TABLE_SCHEM")+"-"+map.get("TABLE_NAME")+"-"+map.get("REMARKS")+"");
            	adminDataContent.setCreateBy(datasourceConfig.getCreateBy());
            	adminDataContent.setUpdateBy(datasourceConfig.getUpdateBy());
            	adminDataContent.setCreateDate(new Date());
            	adminDataContent.setUpdateDate(new Date());
            	adminDataContent.setDelFlag("0");
                tableList.add(adminDataContent);
            }
	           /* rs.close();
	            stmt.close();*/
	            
	            
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return tableList;
	}

}

