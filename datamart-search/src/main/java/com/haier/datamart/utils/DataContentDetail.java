package com.haier.datamart.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.RestController;

import com.haier.datamart.entity.AdminDataContent;
import com.haier.datamart.entity.AdminDataContentDetail;
import com.haier.datamart.entity.AdminDatasourceConfig;
import com.haier.datamart.entity.TableScanError;
import com.haier.datamart.mapper.TableScanErrorMapper;
import com.haier.datamart.service.ITableScanErrorService;
/**
 * 根据AdminDatasourceConfig对象获取表名
* @author doushuihai  
* @date 2018年6月6日下午4:23:07  
* @TODO
 */
@RestController
public class DataContentDetail {
	@Autowired
	private ITableScanErrorService EerrorService ;
	public  Map<String, List> getDataContentDetail(AdminDatasourceConfig datasourceConfig,Connection conn,List<AdminDataContent> adminDataContentList) throws Exception{
		 
		
		 List<AdminDataContentDetail> contentDetailsList=new ArrayList<AdminDataContentDetail>();
	     List<TableScanError> errors=new ArrayList<TableScanError>();
	    
	    	 for(AdminDataContent adminDataContent:adminDataContentList){
	    		 try {
	    			 String url=datasourceConfig.getDbUrl();  
	    	         String username=datasourceConfig.getDbName();   
	    	         String password= datasourceConfig.getDbPassword();  
	    	         String databasetype =datasourceConfig.getDbType(); 
	    	         String tableName=adminDataContent.getTableName();
	    	         if(StringUtils.isNotBlank(tableName)){
	    	        	 String[] names=tableName.split("\\.");
	    	        	 if(names.length>1){
	    	        		 tableName=names[1];
	    	        	 }
	    	         }
	    	         List<Map<String, Object>> columns=DBMSMetaUtil.listColumns(databasetype, url, username, password,tableName);
	    			 for(Map<String, Object> m:columns){
	    				 AdminDataContentDetail contentDetail=new AdminDataContentDetail();
			             	contentDetail.setId(UUIDUtils.getUuid());
			             	contentDetail.setContentId(adminDataContent.getId());
			             	contentDetail.setDatasourceId(adminDataContent.getDataSourceId());
			             	contentDetail.setColumnName(m.get("COLUMN_NAME")+"");
			             	contentDetail.setColumnType(m.get("TYPE_NAME")+"");
			             	contentDetail.setRemarks(m.get("REMARKS")==null?"":m.get("REMARKS")+"");
			             	System.out.println(m.get("COLUMN_NAME")+"-"+m.get("TYPE_NAME")+"-"+m.get("REMARKS")+"");
			             	contentDetail.setCreateBy(adminDataContent.getCreateBy());
			             	contentDetail.setCreateDate(new Date());
			             	contentDetail.setUpdateBy(adminDataContent.getUpdateBy());
			             	contentDetail.setUpdateDate(new Date());
			             	contentDetail.setDelFlag("0");
			             	contentDetailsList.add(contentDetail);
	    			 }
				} catch (Exception e) {
					e.printStackTrace();
					 TableScanError error=new TableScanError();
					error.setId(UUIDUtils.getUuid());
					error.setDataSourceId(adminDataContent.getDataSourceId());
					error.setErrorTableName(adminDataContent.getTableName());
					error.setErrorDatabase(StringUtils.substringBefore(adminDataContent.getTableName(), "."));
					//error.setCreateBy(adminDataContent.getCreateBy());
					error.setCreateDate(new Date());
					//error.setUpdateBy(adminDataContent.getUpdateBy());
					error.setUpdateDate(new Date());
					error.setRemarks(e.toString());
					errors.add(error);
					continue;
					
				}   
	    		 
	    		/* try {
	    			 String tableName=adminDataContent.getTableName();
			    	 String sql = "describe " +tableName ;  
		             PreparedStatement ps = conn.prepareStatement(sql);  
		             ResultSet resultset = ps.executeQuery();  
		             while(resultset.next()){
			             	AdminDataContentDetail contentDetail=new AdminDataContentDetail();
			             	contentDetail.setId(UUIDUtils.getUuid());
			             	contentDetail.setContentId(adminDataContent.getId());
			             	contentDetail.setDatasourceId(adminDataContent.getDataSourceId());
			             	contentDetail.setColumnName(resultset.getString(1));
			             	contentDetail.setColumnType(resultset.getString(2));
			             	contentDetail.setCreateBy(adminDataContent.getCreateBy());
			             	contentDetail.setCreateDate(new Date());
			             	contentDetail.setUpdateBy(adminDataContent.getUpdateBy());
			             	contentDetail.setUpdateDate(new Date());
			             	contentDetail.setDelFlag("0");
			             	contentDetailsList.add(contentDetail);
			             	  
			             }
				} catch (Exception e) {
					e.printStackTrace();
					 TableScanError error=new TableScanError();
					error.setId(UUIDUtils.getUuid());
					error.setDataSourceId(adminDataContent.getDataSourceId());
					error.setErrorTableName(adminDataContent.getTableName());
					error.setErrorDatabase(StringUtils.substringBefore(adminDataContent.getTableName(), "."));
					//error.setCreateBy(adminDataContent.getCreateBy());
					error.setCreateDate(new Date());
					//error.setUpdateBy(adminDataContent.getUpdateBy());
					error.setUpdateDate(new Date());
					error.setRemarks(e.toString());
					errors.add(error);
					continue;
					
				}   */    
	    	 }
	   
	    	Map<String, List> map=new HashMap<String, List>();
	 		map.put("errorsList", errors);
	 		map.put("contentDetailsList", contentDetailsList);
		return map;
	}
}
