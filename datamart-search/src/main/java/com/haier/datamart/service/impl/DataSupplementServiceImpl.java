package com.haier.datamart.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.haier.datamart.entity.EnteringTableSettingDetail;
import com.haier.datamart.utils.ExcleUtils;

public class DataSupplementServiceImpl {
	/**
	 * 上传excel文件到临时目录后并开始解析
	 * @param fileName
	 * @param file
	 * @param userName
	 * @return
	 */
	public String batchImport(String fileName,MultipartFile mfile,
			String userName,List<EnteringTableSettingDetail> list,
			String tablename){
		
		   File uploadDir = new  File("E:\\fileupload");
	       //创建一个目录 （它的路径名由当前 File 对象指定，包括任一必须的父路径。）
	       if (!uploadDir.exists()) uploadDir.mkdirs();
	       //新建一个文件
	       File tempFile = new File("E:\\fileupload\\" + new Date().getTime() + ".xlsx"); 
	       //初始化输入流
	       InputStream is = null;  
	       try{
	    	   //将上传的文件写入新建的文件中
	    	   mfile.transferTo(tempFile);
	    	   
	    	   //根据新建的文件实例化输入流
	           is = new FileInputStream(tempFile);
	    	   
	    	   //根据版本选择创建Workbook的方式
	           Workbook wb = null;
	           //根据文件名判断文件是2003版本还是2007版本
	           if(ExcleUtils.isExcel2007(fileName)){
	        	  wb = new XSSFWorkbook(is); 
	           }else{
	        	  wb = new HSSFWorkbook(is); 
	           }
	           //解析excel里面的内容并插入到数据库
	           return readExcelValue(wb,userName,tempFile,list,tablename);
	      }catch(Exception e){
	          e.printStackTrace();
	      } finally{
	          if(is !=null)
	          {
	              try{
	                  is.close();
	              }catch(IOException e){
	                  is = null;    
	                  e.printStackTrace();  
	              }
	          }
	      }
        return "导入出错！请检查数据格式！";
    }
	
	
	/**
	   * 解析Excel里面的数据
	   * @param wb
	   * @return
	 * @throws SQLException 
	   */
	  public String readExcelValue(Workbook wb,String userName,File tempFile,List<EnteringTableSettingDetail> list,String tablename) throws SQLException{
		  
		   //错误信息接收器
		   String errorMsg = "";
	       //得到第一个shell  
	       Sheet sheet=wb.getSheetAt(0);
	       //得到Excel的行数
	       int totalRows=sheet.getPhysicalNumberOfRows();
	       //总列数
		   int totalCells = 0; 
	       //得到Excel的列数(前提是有行数)，从第二行算起
	       if(totalRows>=2 && sheet.getRow(1) != null){
	            totalCells=sheet.getRow(1).getPhysicalNumberOfCells();
	       }
//	       List<UserKnowledgeBase> userKnowledgeBaseList=new ArrayList<UserKnowledgeBase>();
//	       UserKnowledgeBase tempUserKB;    
	       
	       String br = "<br/>";
	       HashMap<String, Object> map = new HashMap<String, Object>();
	       HashMap<String, Object> mapValue = new HashMap<String, Object>();
	       //循环Excel行数,从第二行开始。标题不入库
	       for(int r=0;r<totalRows;r++){
	    	   String rowMessage = "";
	           Row row = sheet.getRow(r);
	           if (row == null){
	        	   errorMsg += br+"第"+(r+1)+"行数据有问题，请仔细检查！";
	        	   continue;
	           }
	          
	           if(r==0){
	        	   for(int i = 0; i <totalCells; i++){
		        	   Cell cellTop = row.getCell(i);
		        	   
		               String colname = cellTop.getStringCellValue();
		               for(EnteringTableSettingDetail detail: list){
		            	   if(colname.equals(detail.getExcelColName())){
		            		   map.put(i+"", detail.getColName());
		            	   }
		               }
		           }
	           }else{
	        	   //循环Excel的列
		           for(int c = 0; c <totalCells; c++){
		               Cell cell = row.getCell(c);
		               if (null != cell){
		            	   String colName=(String) map.get(c+"");
		            	   String detailValue = cell.getStringCellValue();
		            	   mapValue.put(colName, detailValue);
		               }else{
		            	   rowMessage += "第"+(c+1)+"列数据有问题，请仔细检查；";
		               }
		           }
	           }
	          
	          
	           //拼接每行的错误提示
	           if(!StringUtils.isEmpty(rowMessage)){
	        	   errorMsg += br+"第"+(r+1)+"行，"+rowMessage;
	           }else{
	      /*  	   String sb=bigjys.substring(1,bigjys.length()-1);
	   			String[] p=sb.split(",");
	   			sql+=" and jysc in (";
	   			for(String s:p){
	   				sql+=" ?,";
	   				paramlist.add(s);
	   			}
	   			sql=sql.substring(0,sql.length()-1);
	   			sql+=" )";
	        	   int i = 0;
	        	   String sql = "insert into "+tablename+" (Name,Sex,Age) values(?,?,?)";
	        	   PreparedStatement pstmt;
	        	   Connection conn = ExcelConnection.getConn();
		    	   pstmt = (PreparedStatement) conn.prepareStatement(sql);
		           pstmt.setString(1, student.getName());
		           pstmt.setString(2, student.getSex());
		           pstmt.setString(3, student.getAge());
		           i = pstmt.executeUpdate();*/
		           //pstmt.close();
		          // conn.close();
//	        	   userKnowledgeBaseList.add(tempUserKB);
	           }
	       }
	       
	       //删除上传的临时文件
	       if(tempFile.exists()){
	    	   tempFile.delete();
	       }
	       
	       //全部验证通过才导入到数据库
	       if(StringUtils.isEmpty(errorMsg)){
//	    	   for(UserKnowledgeBase userKnowledgeBase : userKnowledgeBaseList){
//	    		   this.saveUserKnowledge(userKnowledgeBase, userName);
//	    	   }
//	    	   errorMsg = "导入成功，共"+userKnowledgeBaseList.size()+"条数据！";
	       }
	       return errorMsg;
	  }

}
