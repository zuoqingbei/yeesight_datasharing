
package com.haier.datamart.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.haier.datamart.entity.AdminDatasourceConfig;
import com.haier.datamart.entity.EnteringLogs;
import com.haier.datamart.entity.EnteringTableSettingDetail;
import com.haier.datamart.entity.EnteringTableSettingInfo;
import com.haier.datamart.entity.User;
import com.haier.datamart.service.impl.EnteringLogsServiceImpl;
@Component
public class DataSupplementUtils {
	/**
	 * 上传excel文件
	 * @param fileName
	 * @param file
	 * @param userName
	 * @return
	 */
	@Autowired
	private  EnteringLogsServiceImpl elsi;
	public static DataSupplementUtils dataSupplementUtils;
	static HashMap<String, Object> map = new HashMap<String, Object>();
    static HashMap<String, Object> mapType = new HashMap<String, Object>();//获取数据库中与excel列规定的类型，与实际excel列值的类型做判断
    static HashMap<String, Object> mapName = new HashMap<String, Object>();//获取数据库中与excel列名，与实际excel列值的类型做判断
    static HashMap<String, String> mapIsPk = new HashMap<String, String>();//获取此字段是否为主键
    static Map<String,String> mapMaxLength = new HashMap<String,String>();//存储列号与数据库中规定的最大长度
    static Map<String,String> mapExcelGs = new HashMap<String,String>();//存储excel公式
    static int errorNum=0;//记录导入错误数
    //错误信息接收器
    static String errorMsg = "";
    static List<String> sqls=new ArrayList<String>();//用於儲存sql語句做批量插入 目标表
    static List<String> baksqls=new ArrayList<String>();//用於儲存sql語句做批量插入备份表
	
	public void setUserInfo(EnteringLogsServiceImpl elsi) {
		this.elsi = elsi;
	}
	@PostConstruct
    public void init() {
		dataSupplementUtils = this;
		dataSupplementUtils.elsi = this.elsi;
 
    }
	
	public static final List<String> DATA_FORMAT= new ArrayList<String>();
	static{
		DATA_FORMAT.add("yyyy/MM/dd");
		DATA_FORMAT.add("yyyy-MM-dd");
		DATA_FORMAT.add("yyyy-MM-dd HH:mm:ss");
		DATA_FORMAT.add("yyyy/MM/dd HH:mm:ss");
		DATA_FORMAT.add("yyyy/MM");
		DATA_FORMAT.add("yyyy-MM");
		DATA_FORMAT.add("HH:mm:ss"); 
		DATA_FORMAT.add("yyyyMMdd");
		DATA_FORMAT.add("yyyyMMdd");
		DATA_FORMAT.add("yyyyMMdd HH:mm:ss");
		DATA_FORMAT.add("yyyyMMdd HH:mm:ss");
		DATA_FORMAT.add("yyyyMM");
		
	}
	
	public static  Map<String,Object> batchImport(MultipartFile mfile,Map<String,Object> param){
	       InputStream is = null;  
	       try{
	    	   
	    	   //根据新建的文件实例化输入流
	    	    is = mfile.getInputStream();
	    	   //根据版本选择创建Workbook的方式
	           Workbook wb =WorkbookFactory.create(is);
	           param.put("wb", wb);
	           //解析excel里面的内容并插入到数据库
	           return readExcelValue(param);
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
        return null;
    }
	
	
	/**
	   * 解析Excel里面的数据
	   * @param wb
	   * @return
	 * @throws Exception 
	   */
	  @SuppressWarnings("unchecked")
	public static  Map<String,Object> readExcelValue(Map<String,Object> param) throws Exception{
		  Map<String,Object> backsMap=new HashMap<String, Object>();//返回结果
		  Workbook wb=(Workbook) param.get("wb");
		  List<EnteringTableSettingDetail> allSettingDetail=(List<EnteringTableSettingDetail>) param.get("allSettingDetail");
		  EnteringTableSettingInfo settingInfo= (EnteringTableSettingInfo) param.get("settingInfo");
		  
		  String tablename=settingInfo.getName();
		  String bakTablename=settingInfo.getBakTableName();
		  String settingId=settingInfo.getId();
		  String industryId=(String) param.get("industryId");
		  User user=(User) param.get("user");
		  AdminDatasourceConfig config = (AdminDatasourceConfig) param.get("config");
		  // 备份数据源
		  AdminDatasourceConfig configBak=(AdminDatasourceConfig) param.get("configBak");
		  String url=config.getDbUrl();
		  String names=config.getDbName();
		  String pwd=config.getDbPassword();
		  Integer maxHeaderRows=(Integer) param.get("maxHeaderRows");
		  Map<String,String> defaultValues=(Map<String, String>) param.get("defaultValues");
		  boolean optRedis=(boolean) param.get("optRedis");//是否写入Redis，true-直接将数据入库 false-值放入redis
		  
		  List<EnteringTableSettingDetail> settingDetails=new ArrayList<EnteringTableSettingDetail>();//导出到模板的配置
		  List<EnteringTableSettingDetail> notExportSettingDetails=new ArrayList<EnteringTableSettingDetail>();//没有导出到模板的配置
		  for(EnteringTableSettingDetail d:allSettingDetail){
			  if("1".equals(d.getIsExport())){
				  notExportSettingDetails.add(d);
			  }else{
				  settingDetails.add(d);
			  }
		  }
		  //获取id和order的映射关系
		  Map<String,Integer> idOrderMaping = new HashMap<String,Integer>();
		  for (EnteringTableSettingDetail detail : settingDetails) {
			idOrderMaping.put(detail.getId(), detail.getOrderNo());
		  }
		  if(maxHeaderRows==0){
			  maxHeaderRows=1;
		  }
	       //得到第一个shell  
	       Sheet sheet=wb.getSheetAt(0);
	       //得到Excel的行数
	       int totalRows=sheet.getPhysicalNumberOfRows();
	       //总列数
		   int totalCells = 0; 
	       //得到Excel的列数(前提是有行数)，从第二行算起
	       if(totalRows>=2 && sheet.getRow(maxHeaderRows) != null){
	            totalCells= settingDetails.size();//sheet.getRow(1).getPhysicalNumberOfCells();
	       }
	       Connection conn = ExcelConnection.getConn(url,names,pwd);
	       PreparedStatement  pstmt = null ;
	       conn.setAutoCommit(false);
	       System.out.println("行数：==============="+totalRows);
	       int countExcelGs = 0;
	       for (EnteringTableSettingDetail detail : settingDetails) {//统计出公式为计算类型的列有几个
	    	   System.out.println("公式:"+detail.getExcelGs());
	    	   if(detail.getExcelGs()!=null&&detail.getExcelGs().contains("[")){
				   countExcelGs++;
				   System.out.println("带[的公式数量:"+countExcelGs);
			   }
	       }
	       String br = "<br/>";
	       sqls=new ArrayList<String>();//用於儲存sql語句做批量插入 目标表
	       baksqls=new ArrayList<String>();//用於儲存sql語句做批量插入备份表
	       map = new HashMap<String, Object>();
	       mapType = new HashMap<String, Object>();//获取数据库中与excel列规定的类型，与实际excel列值的类型做判断
	       mapName = new HashMap<String, Object>();//获取数据库中与excel列名，与实际excel列值的类型做判断
	       mapIsPk = new HashMap<String, String>();//获取此字段是否为主键
	       mapMaxLength = new HashMap<String,String>();//存储列号与数据库中规定的最大长度
	       mapExcelGs = new HashMap<String,String>();
	       errorMsg="";
	       errorNum=0;
	       errorMsg = checkExcelContent(settingDetails, tablename, bakTablename, user, maxHeaderRows,
				idOrderMaping, sheet, totalRows, totalCells, br, conn, pstmt,
				sqls, baksqls, countExcelGs,notExportSettingDetails,defaultValues);
	       
	       /*入库*/
	       addOptLog(settingId, industryId, user);
           //当出现错误信息的时候 是否允许正确的继续导入 0-允许 1-不允许
	       backsMap.put("errorMsg", errorMsg);
	       backsMap.put("maxHeaderRows", maxHeaderRows);
	       backsMap.put("totalRows", totalRows);
	       backsMap.put("sqls", sqls);
	       backsMap.put("baksqls", baksqls);
	       param.put("totalRows", totalRows);
	       if(("0".equals(settingInfo.getErrorContinue())||StringUtils.isBlank(errorMsg))&&!optRedis){
	    	   //允许除错误数据之外的数据继续导入
	    	   errorMsg=insetData2Db(param,errorMsg,sqls,baksqls);
			} else {
				String optResult = "总条数" + (totalRows - maxHeaderRows) + ",其中" + (errorNum)
						+ "条存在问题。由于补录配置不允许出现错误继续导入，故导入成功0条！";
				errorMsg = optResult + errorMsg;
			}
	       //重新写入错误信息-不要删
	       backsMap.put("errorMsg", errorMsg);
	       return backsMap;
	  }
	/**
	 * @time   2018年11月19日 下午2:33:10
	 * @author zuoqb
	 * @todo   将补录数据入库
	 */
	public static String insetData2Db(Map<String, Object> param,
			String errorMess, List<String> sqls, List<String> baksqls)
			throws SQLException {
		Connection conn = null;
		AdminDatasourceConfig config = (AdminDatasourceConfig) param
				.get("config");
		// 备份数据源
		AdminDatasourceConfig configBak = (AdminDatasourceConfig) param
				.get("configBak");
		String url = config.getDbUrl();
		String names = config.getDbName();
		String pwd = config.getDbPassword();
		String bakUrl = configBak.getDbUrl();
		String bakNames = configBak.getDbName();
		String bakPwd = configBak.getDbPassword();
		Integer maxHeaderRows = (Integer) param.get("maxHeaderRows");
		if(maxHeaderRows==0){
			maxHeaderRows=1;
		}
		Integer totalRows = (Integer) param.get("totalRows");
		// 插入备份数据
		conn = ExcelConnection.getConn(bakUrl, bakNames, bakPwd);// 切换备份数据源
		Statement stmt = (Statement) conn.createStatement();
		// 备份
		for (String baksql : baksqls) {
			stmt.addBatch(baksql);
		}
		stmt.executeBatch();
		if (!conn.getAutoCommit())
			conn.commit();

		// 插入主表数据
		conn = ExcelConnection.getConn(url, names, pwd);// 切换备份数据源
		stmt = (Statement) conn.createStatement();
		for (String sql : sqls) {
			System.out.println(sql);
			stmt.addBatch(sql);
		}
		stmt.executeBatch();
		if (!conn.getAutoCommit())
			conn.commit();
		/*String optResult = "总条数" + (totalRows - maxHeaderRows) + ",导入成功共"
				+ (sqls.size()) + "条数据！其中" + (errorNum) + "条存在问题。";*/
		String optResult = "导入成功共"
				+ (sqls.size()) + "条数据！" + (errorNum) + "条存在问题。";
		errorMess = optResult + errorMess;
		if (stmt != null) {
			stmt.close();
		}
		if (conn != null) {
			conn.close();
		}
		return errorMess;
	}
	/**
	 * 
	 * @time   2018年11月19日 下午3:22:08
	 * @author zuoqb
	 * @todo   添加操作日志
	 */
	protected static void addOptLog(String settingId, String industryId,
			User user) {
		int success_num=sqls.size()-errorNum;
	       Date date = new Date();
           DateFormat formater = new SimpleDateFormat(
           "yyyy-MM-dd HH:mm:ss");
           String dateStr = formater.format(date);
           EnteringLogs log = new EnteringLogs();
           log.setId(GenerationSequenceUtil.getUUID());
           log.setAllNum(sqls.size()+"");
           log.setSuccessNum(success_num+"");
           log.setFailNum(errorNum+"");
           log.setFailData(null);
           log.setIndustryId(industryId+"");
           log.setSettingId(settingId);
           log.setCreateDate(dateStr);
           if(user.getId()==null||"".equals(user.getId())){
        	   user.setId("");
           }
           log.setCreateBy(user.getId());
           log.setUpdateBy(user.getId());
           log.setUpdateDate(dateStr);
           dataSupplementUtils.elsi.addLog(log);
	}
	public static boolean checkNums(String detailValue){
		if(StringUtils.isNotBlank(detailValue)){
			return detailValue.matches("^-?\\d+(\\.\\d+)?$");
		}else{
			return false;
		}
	}
	  /**
	   * 
	   * @time   2018年11月8日 上午11:28:35
	   * @author zuoqb
	   * @todo   校验excel数据格式 拼接SQL
	   */
	protected static String checkExcelContent(
			List<EnteringTableSettingDetail> list, String tablename,
			String bakTablename, User user, Integer maxHeaderRows,
			Map<String, Integer> idOrderMaping, Sheet sheet, int totalRows,
			int totalCells, String br, Connection conn,
			PreparedStatement pstmt, List<String> sqls, List<String> baksqls,
			int countExcelGs,List<EnteringTableSettingDetail> notExportSettingDetails,Map<String,String> defaultValues) throws SQLException {
		
		AAA:
	       //循环Excel行数,从第二行开始。标题不入库
	       for(int r=maxHeaderRows-1;r<totalRows;r++){
	    	   String rowMessage = "";
	           Row row = sheet.getRow(r);//获取每一行
	           if (row == null){
	        	   errorMsg += br+"第"+(r+1)+"行数据有问题，请仔细检查！";
	        	   continue;
	           }
	          
	           //解析表头最后一行，获取配置信息
	           if(r==maxHeaderRows-1){
	        	   int rowNums=row.getLastCellNum();
	        	   if(rowNums!=list.size()){
	        		   return "请下载最新版本模板！";
	        	   }
	        	   for(int i = 0; i <totalCells; i++){
		        	   Cell cellTop = row.getCell(i);
		        	   cellTop.setCellType(Cell.CELL_TYPE_STRING);
		               String colname = cellTop.getStringCellValue();
		               
		               for(EnteringTableSettingDetail detail: list){
		            	   if(colname.equals(detail.getExcelColName())){
		            		   map.put(i+"", detail.getColName());
		            		   mapType.put(i+"", detail.getColType());
		            		   mapName.put(i+"", detail.getExcelColName());
		            		   mapIsPk.put(i+"", detail.getColPk());
		            		   if(detail.getColLength()!=null){
		            			   mapMaxLength.put(i+"", detail.getColLength());
		            		   }
		            		   if(detail.getExcelGs()!=null&&!"".equals(detail.getExcelGs().trim())){
		            			   mapExcelGs.put(i+"", detail.getExcelGs());
		            		   }
		            		  
		            	   }
		               }
		           }
	        	   continue;
	           }
	           Integer countEmpty = 0;//存储当前行的空白单元格数量
	           //处理补录数据
	           if(r>maxHeaderRows-1){
	        	   String error_col="";//记录异常的字段
	    	       String error_msg="";//记录异常的字段错误原因
	        	   HashMap<String, Object> mapValue = new HashMap<String, Object>();//储存excel列名与列指的对应，以插入数据库
	        	   mapValue.put("is_success", "0");
	        	   HashMap<String, Object> mapValueForUpdate= new HashMap<String, Object>();//储存excel列名与列指的对应，以更新数据库
	        	   //循环Excel的列
	        	   
		           for(int c = 0; c <totalCells; c++){
		        	   Cell cell = row.getCell(c);
		        	   if(cell==null){
		        		  //countEmpty++;
		        		  row.createCell(c);
		        		  //System.out.println("因为null,countEmpty:"+countEmpty);
		        	   }else{//若当前单元格为空串或者当前单元格带有公式限定
		        		  if(row.getCell(c).getCellType()==Cell.CELL_TYPE_NUMERIC){
		        			  if("".equals((cell.getNumericCellValue()+"").trim())){
		        				  countEmpty++;
		        				  System.out.println("因为空串,countEmpty:"+countEmpty);
		        			  }
		        		  }else{
		        			  row.getCell(c).setCellType(Cell.CELL_TYPE_STRING);
		        			  if( "".equals(cell.getStringCellValue().trim())){
		        				  countEmpty++;
			        			  System.out.println("因为空串,countEmpty:"+countEmpty);
			        		  }
		        		  }
		        		 
		        	   }
		        	   System.out.println("当前公式:"+mapExcelGs.get(c+""));
		        	  
		        	   //System.out.println("本行空格数量:"+countEmpty);
		        	   //若连续一行空白单元格(除公式列)
		        	   if((countExcelGs+countEmpty)>=totalCells){
		        		   break AAA;
		        	   }
		        	   if (cell==null ||"".equals(cell.toString().trim())) {
		        		   String colName=(String) map.get(c+"");
		        		   String name=(String) mapName.get(c+"");
		        		   mapValue.put(colName, "");
                		   mapValue.put("is_success", "1");
                		   error_col+=name+",";
                		   error_msg+="空白格,";
                		   mapValue.put("error_col", error_col);
                		   mapValue.put("error_msg", error_msg);
		               }else{ //if (null != cell)
		            	   /*888*/
		            	   String colType;
		            	   if((String) mapType.get(c+"")==null||"".equals((String) mapType.get(c+""))){
		            		   colType = "varchar";
		            	   }else{
		            		   colType = ((String) mapType.get(c+"")).toLowerCase();
		            	   }
		            	   String colName=(String) map.get(c+"");
		            	   String name=(String) mapName.get(c+"");
		            	   String excelGs = mapExcelGs.get(c+"");//获取当前列的excel公式
		            	   String  detailValue = "";
		            	   detailValue = getExcelName(row, c, cell, colType,
								excelGs, detailValue);
		            	   
		            	  
		            	   
		            	   String isPk=(String) mapIsPk.get(c+"");
		            	   Integer colMaxLength = 10000;
		            	   if(mapMaxLength.get(c+"")!=null&&"".equals(mapMaxLength.get(c+""))){
		            		  colMaxLength = Integer.parseInt(mapMaxLength.get(c+""));//获取当前列规定的最大长度
		            	   }
		            	   //存入前去除首位空格
		            	   mapValue.put(colName, detailValue.replaceAll("^[　*| *| *|//s*]*", "").replaceAll("[　*| *| *|//s*]*$", ""));
		            	   
		            	   
		            	   
		            	  if(excelGs!=null&&!"".equals(excelGs)){
		            		  List formatResults = tesTbyConstraint(excelGs, detailValue, idOrderMaping, row);
			            	   if(!((Boolean)formatResults.get(0))){//若excel公式校验不过关
			            		   error_msg+=(String)formatResults.get(1);
			            		   mapValue.put("is_success", "1");
		                		   error_col+=name+",";
		                		   mapValue.put("error_col", error_col);
		                		   mapValue.put("error_msg", error_msg);
		                		   if("1".equals(isPk)){
			                			 mapValueForUpdate.put(colName, detailValue);
		                		   }
		                		   System.out.println("公式校验失败!");
			            	   }
		            	  }
		            		   try {
			            	 		  if(detailValue.length()<=colMaxLength){
			            	 			 if("varchar".equals(colType) || "char".equals(colType) ||"tinytext".equals(colType)||
			            	 				"text".equals(colType)){
					                		   //mapValue.put("is_success", "0");
					            	   }
					            	   if("int".equals(colType) || "integer".equals(colType) || "double".equals(colType) || "bigint".equals(colType) ||
					            		"float".equals(colType) || "numeric".equals(colType)||"decimal".equals(colType)){
					            		  // if(NumberUtils.isNumber(detailValue)){
					            		   if(DataSupplementUtils.checkNums(detailValue)){
					                		   //mapValue.put("is_success", "0"); 
					            		   }else{
					            			   error_msg+="只能为数字类型数据,";
					            			   throw new Exception("内容填写异常!");
					            		   }
					            	   }
					            	   if("datetime".equals(colType) || "timestamp".equals(colType) ||  
					            		"date".equals(colType) ||"time".equals(colType)){
					            		   if(getDateFormatByString(detailValue)!=-1){
					                		   //mapValue.put("is_success", "0");
					            		   }else{
					            			   error_msg+="只能存放时间类型数据,";
					            			   throw new Exception("内容填写异常");
					            		   }
					            	   } else{
				                		   //mapValue.put("is_success", "0");
					            	   }
			            	 		  }else {
			            	 			 error_msg+="数据长度超过"+colMaxLength+",";
			            	 			 throw new Exception("长度异常!");
			            	 		  }
			            	 		
								} catch (Exception e) {
									  mapValue.put("is_success", "1");
			                		  mapValue.put("error_msg", error_msg);
			                		  error_col += name+",";
			  						  mapValue.put("error_col", error_col);
			            		   }
			            	   if("1".equals(isPk)){
	                			   mapValueForUpdate.put(colName, detailValue);
	                		   }
			            	  
		               }
		               
		           }
		           String sqlForUpdate="";
		           if(!CollectionUtils.isEmpty(mapValueForUpdate)){
		        	   sqlForUpdate+="select id from "+tablename+" where del_flag=0 ";
		        	   if(mapValueForUpdate.size()!=0){
		        		   sqlForUpdate+=" and ";
		        	   }
		        	   for (String in : mapValueForUpdate.keySet()) {
		        		   String str = (String) mapValueForUpdate.get(in);//得到每个key多对用value的值
		        		   sqlForUpdate+=in.trim()+"='"+str.trim()+"' and ";
			        	    }
		        	   sqlForUpdate = sqlForUpdate.substring(0,sqlForUpdate.length() - 4);
		           }
		           
		           System.out.println(sqlForUpdate+"=================================================================");
		          
		           if(StringUtils.isNotBlank(error_col)){
		        	   error_col = error_col.substring(0,error_col.length() - 1);
		           }
		           mapValue.put("error_col", error_col);

		           if(StringUtils.isNotBlank(error_msg)){
		        	   error_msg = error_msg.substring(0,error_msg.length() - 1);
		           }
		           mapValue.put("error_msg", error_msg);
		           Date date = new Date();
                   DateFormat formater = new SimpleDateFormat(
                   "yyyy-MM-dd HH:mm:ss");
                   String dateStr = formater.format(date);
		           mapValue.put("create_date", dateStr);
		           mapValue.put("update_date", dateStr);
		           String[] s=error_msg.split(",");
		           boolean isnotNull=false;
		           for(String str:s){
		        	   if(!"空白格".equals(str)){
		        		   isnotNull=true;
		        	   }
		           }
		           if(mapValue.get("is_success").equals("1")&&isnotNull){
		        	   errorNum++;
		        	   //拼接当前行错误信息
		        	   String[] cols=error_col.split(",");
		        	   String[] msg=error_msg.split(",");
		        	   for(int x=0;x<cols.length;x++){
		        		   if(x<cols.length){
		        			   rowMessage+=cols[x]+"-"+msg[x]+";";
		        		   }else{
		        			   rowMessage+=cols[x]+"-"+msg[x];
		        		   }
		        	   }
		           }else{
		        	   //不是错误数据才开始拼接sql
		        	   if(StringUtils.isNotBlank(sqlForUpdate)){
		        		   pstmt = (PreparedStatement)conn.prepareStatement(sqlForUpdate);
		        		   ResultSet rs = pstmt.executeQuery();
		        		   if(rs.next()==false){
		        			   String sql = "insert into "+tablename;
		        			   String sqlbak = "insert into "+bakTablename+" ";
		        			   sql+=" (";
		        			   sqlbak+=" (";
		        			   for (String in : mapValue.keySet()) {
		        				   if(mapValue.get(in)!=null&&StringUtils.isNotBlank(mapValue.get(in)+"")){
		        					   sql+=in+",";
		        					   sqlbak+=in+",";
		        				   }
		        			   }
		        			   for (String in : defaultValues.keySet()) {
		        				   if(defaultValues.get(in)!=null&&StringUtils.isNotBlank(defaultValues.get(in)+"")){
		        					   sql+=in+",";
		        					   sqlbak+=in+",";
		        				   }
		        			   }
		        			   sql+="id,create_by,update_by";
		        			   sqlbak+="id,create_by,update_by";
		        			   sql+=") values(";
		        			   sqlbak+=") values(";
		        			   for (String in : mapValue.keySet()) {
		        				   if(mapValue.get(in)!=null&&StringUtils.isNotBlank(mapValue.get(in)+"")){
		        					   sql+="'";
		        					   sqlbak+="'";
		        					   String str = (String) mapValue.get(in);//得到每个key多对用value的值
		        					   sql+=str+"',";
		        					   sqlbak+=str+"',";
		        				   }
		        			   }
		        			   for (String in : defaultValues.keySet()) {
		        				   if(defaultValues.get(in)!=null&&StringUtils.isNotBlank(defaultValues.get(in)+"")){
		        					   sql+="'";
		        					   sqlbak+="'";
		        					   String str = (String) defaultValues.get(in);//得到每个key多对用value的值
		        					   sql+=str+"',";
		        					   sqlbak+=str+"',";
		        				   }
		        			   }
		        			   sql+="'"+GenerationSequenceUtil.getUUID()+"','"+user.getId()+"','"+user.getId()+"'";
		        			   sql+=")";
		        			   sqlbak+="'"+GenerationSequenceUtil.getUUID()+"','"+user.getId()+"','"+user.getId()+"'";
		        			   sqlbak+=")";
		        			   System.out.println(sqlbak+"=================================================================");
		        			   sqls.add(sql);
		        			   baksqls.add(sqlbak);
		        			   
		        		   }else{
		        			   String sql = "update "+tablename+" set ";
		        			   String sqlbak = "insert into "+bakTablename+" ";
		        			   sqlbak+=" (";
		        			   for (String in : mapValue.keySet()) {
		        				   if(mapValue.get(in)!=null&&StringUtils.isNotBlank(mapValue.get(in)+"")){
		        					   String value = (String) mapValue.get(in);//得到每个key多对用value的值
		        					   sql+=in+"='"+value+"',";
		        					   sqlbak+=in+",";
		        				   }
		        			   }
		        			   for (String in : defaultValues.keySet()) {
		        				   if(defaultValues.get(in)!=null&&StringUtils.isNotBlank(defaultValues.get(in)+"")){
		        					   String value = (String) defaultValues.get(in);//得到每个key多对用value的值
		        					   sql+=in+"='"+value+"',";
		        					   sqlbak+=in+",";
		        				   }
		        			   }
		        			   sql+="update_by='"+user.getId()+"' where ";
		        			   sqlbak+="id,create_by,update_by";
		        			   sqlbak+=") values(";
		        			   for (String in : mapValue.keySet()) {
		        				   if(mapValue.get(in)!=null&&StringUtils.isNotBlank(mapValue.get(in)+"")){
		        					   sqlbak+="'";
		        					   String str = (String) mapValue.get(in);//得到每个key多对用value的值
		        					   sqlbak+=str+"',";
		        					   //map.keySet()返回的是所有key的值
		        				   }
		        			   }
		        			   for (String in : defaultValues.keySet()) {
		        				   if(defaultValues.get(in)!=null&&StringUtils.isNotBlank(defaultValues.get(in)+"")){
		        					   sqlbak+="'";
		        					   String str = (String) defaultValues.get(in);//得到每个key多对用value的值
		        					   sqlbak+=str+"',";
		        					   //map.keySet()返回的是所有key的值
		        				   }
		        			   }
		        			   sqlbak+="'"+GenerationSequenceUtil.getUUID()+"','"+user.getId()+"','"+user.getId()+"'";
		        			   sqlbak+=")";
		        			   for (String in : mapValueForUpdate.keySet()) {
		        				   String str = (String) mapValue.get(in);//得到每个key多对用value的值
		        				   sql+=in+"='"+str+"' and ";
		        			   }
		        			   sql = sql.substring(0,sql.length() - 4);
		        			   System.out.println(sql+"=================================================================");
		        			   sqls.add(sql);
		        			   baksqls.add(sqlbak);
		        		   }
		        		   continue;
		        	   }else{//若没有主键啊喂
		        		   String sql = "insert into "+tablename;
		        		   String sqlbak = "insert into "+bakTablename+" ";//备份语句
		        		   sql+=" (";
		        		   sqlbak+=" (";
		        		   for (String in : mapValue.keySet()) {
		        			   if(mapValue.get(in)!=null&&StringUtils.isNotBlank(mapValue.get(in)+"")){
		        				   sql+=in+",";
		        				   sqlbak+=in+",";
		        			   }
		        		   }
		        		   for (String in : defaultValues.keySet()) {
		        			   if(defaultValues.get(in)!=null&&StringUtils.isNotBlank(defaultValues.get(in)+"")){
		        				   sql+=in+",";
		        				   sqlbak+=in+",";
		        			   }
		        		   }
		        		   sql+="id,create_by,update_by";
		        		   sqlbak+="id,create_by,update_by";
		        		   sql+=") values(";
		        		   sqlbak+=") values(";
		        		   for (String in : mapValue.keySet()) {
		        			   	if(mapValue.get(in)!=null&&StringUtils.isNotBlank(mapValue.get(in)+"")){
		        			   		sql+="'";
		        			   		sqlbak+="'";
		        			   		String str = (String) mapValue.get(in);//得到每个key多对用value的值
		        			   		sql+=str+"',";
		        			   		sqlbak+=str+"',";
		        			   }
		        		   }
		        		   for (String in : defaultValues.keySet()) {
		        			   	if(defaultValues.get(in)!=null&&StringUtils.isNotBlank(defaultValues.get(in)+"")){
		        			   		sql+="'";
		        			   		sqlbak+="'";
		        			   		String str = (String) defaultValues.get(in);//得到每个key多对用value的值
		        			   		sql+=str+"',";
		        			   		sqlbak+=str+"',";
		        			   }
		        		   }
		        		   sql+="'"+GenerationSequenceUtil.getUUID()+"','"+user.getId()+"','"+user.getId()+"'";
		        		   sql+=")";
		        		   sqlbak+="'"+GenerationSequenceUtil.getUUID()+"','"+user.getId()+"','"+user.getId()+"'";
		        		   sqlbak+=")";
		        		   sqls.add(sql);
		        		   baksqls.add(sqlbak);
		        	   }
		        	   continue;
		             }
		           }
		           
	           System.out.println("本行空格数量共:"+countEmpty+countExcelGs);
	           System.out.println("总共列数:"+list.size());
	           //拼接每行的错误提示
	           if(!StringUtils.isEmpty(rowMessage)){
	        	   errorMsg += br+"第"+(r+1)+"行，"+rowMessage;
	           }
	       }
		return errorMsg;
	}
	/**
	 * @time   2018年11月19日 上午11:17:30
	 * @author zuoqb
	 * @todo   获取输入值
	 * @param  @param row
	 * @param  @param c
	 * @param  @param cell
	 * @param  @param colType
	 * @param  @param excelGs
	 * @param  @param detailValue
	 * @param  @return
	 * @return_type   String
	 */
	public static String getExcelName(Row row, int c, Cell cell,
			String colType, String excelGs, String detailValue) {
		if("datetime".equals(colType) || "timestamp".equals(colType) ||  
		    		"date".equals(colType) ||"time".equals(colType)){
			   if(excelGs!=null&&!"".equals(excelGs)){
				   String[] exprses = AddFormulaAndDataValidationForExcel.parseExcelConstraint(excelGs);
				   if("限定日期".equals(exprses[0])||"限定时间".equals(exprses[0])){
					   int dateType = getDateFormatByString(exprses[1],"");
					 
						   SimpleDateFormat sdf = new SimpleDateFormat(DATA_FORMAT.get(dateType));
						   Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
						   detailValue =  sdf.format(date); 
					   
				   }
			   }else{//若公式为空
				   row.getCell(c).setCellType(Cell.CELL_TYPE_STRING);
				  try {
					  SimpleDateFormat sdf;
					   if(!"time".equals(colType)&&!"year".equals(colType)){
		    			    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    		   }else{
		    			    sdf = new SimpleDateFormat("HH:mm:ss");
		    		   }
					   Double preValue = Double.parseDouble(cell.getStringCellValue());
					   if(preValue<=1){
						   sdf = new SimpleDateFormat("HH:mm:ss");
					   }
					   Date date = HSSFDateUtil.getJavaDate(preValue);
					   detailValue =  sdf.format(date);
				} catch (Exception e) {
					detailValue = cell.getStringCellValue();
				}
			   } 
		    }else{
		    	row.getCell(c).setCellType(Cell.CELL_TYPE_STRING);
		    	detailValue = cell.getStringCellValue();
		    }
		return detailValue;
	}
	  /**
	   * @param 字符串
	   * @return 日期类型
	   * 返回-1则表明不是日期类型的字符串
	   */
	  public static int getDateFormatByString(String str){
		  int type = -1;
		  SimpleDateFormat sdf;
		  for (int i=0;i<DATA_FORMAT.size();i++) {
		    sdf = new SimpleDateFormat(DATA_FORMAT.get(i));
			sdf.setLenient(false);
			
	        try {
				Date testDate = sdf.parse(str);
				String testStr = sdf.format(testDate);
				if(!str.equals(testStr)){
					throw new ParseException("前后不匹配!", 00000);
				}
				type = i;
				break;
			} catch (ParseException e) {
			}
		  }
		  return type;
	  }
	 /**
	  * @param str
	  * @param flag 
	  * @return
	  */
	  public static int getDateFormatByString(String str,String flag){
		  int type = -1;
		  SimpleDateFormat sdf;
		  for (int i=0;i<DATA_FORMAT.size();i++) {
		    sdf = new SimpleDateFormat(DATA_FORMAT.get(i));
			sdf.setLenient(false);
			
	        try {
				Date testDate = sdf.parse(str);
				type = i;
				break;
			} catch (ParseException e) {
			}
		  }
		  return type;
	  }
	/**
	 * 如果返回-1,说明一个日期类型也没有匹配到
	 * @param str
	 * @return 日期格式类型
	 */
	  
	  /**
	   * @param excelGs
	   * @param detailValue
	   * @param idOrderMaping
	   * @param row
	   * @return 当前行excel公式符合要求返回true,否则返回flase
	   */
	  public static List tesTbyConstraint(String excelGs,
			  								String detailValue,
			  								Map<String,Integer> idOrderMaping,
			  								Row row){
		List resultList = new ArrayList();
			/*如果类型为设置公式*/
		   	   if(!excelGs.startsWith("限定")){
		   		   String[] ids = null;
		   		   Map<Integer,Integer> orderIndexMaping = null;
		      			try {
		      				ids = AddFormulaAndDataValidationForExcel
		      							  .getIdFroFormula(excelGs);//获取公式中包含的id
		      				List<Integer> orderList = new ArrayList<Integer>();
		      				
		      				/*for (Entry<String, Integer> a : idOrderMaping.entrySet()) {
								System.out.println("前置idOrderMaping映射关系:"+a);
							}*/
		      				Set<String> set = idOrderMaping.keySet();
		      				for (Object object : set) {
		      					//System.out.println("set中object:"+Integer.parseInt((String)object));
		      					//System.out.println("获取id获取顺序id"+idOrderMaping.get((String)object));
		      					orderList.add(idOrderMaping.get((String)object));
							}
		      				Collections.sort(orderList);
		      				/*for (Integer integer : orderList) {
								System.out.print("id顺序"+integer);
							}*/
		      				/*生成orderno和列号的映射*/
		      				orderIndexMaping = new HashMap<Integer,Integer>();
		      				for (int i=0;i<orderList.size();i++) {
								orderIndexMaping.put(orderList.get(i), i);
							}
		      				/*for (Entry<Integer, Integer> a : orderIndexMaping.entrySet()) {
								System.out.println("orderno和列号的映射"+a);
							}*/
		      			} catch (Exception e) {
		      			}
		      			if(excelGs.startsWith("sum")||excelGs.startsWith("SUM")){//若公式为sum
		      					System.out.println("进入校验sum公式");
		      			 		String sub = excelGs.substring(4,excelGs.length()-1);
		      					String[] operators = sub.split("\\d");
		      					
		      					List<String> operatorLists = new ArrayList<String>();
		      					//筛选出公式中运算符
		      					for (String operator : operators) {
		      						if("+".equals(operator)||"-".equals(operator)){
		      							operatorLists.add(operator);
		      						}
		      					}
		      					Cell dynamicCell = null;
		      					Double expectedValue = -0.0;//预期值
		      					Double dynamicCellValue =  0.0;//记录目标列的值
		      					List<Double> dynamicCellValues = new ArrayList<Double>();//存放目标列的值
		      					/*for (Entry<String, Integer> a : idOrderMaping.entrySet()) {
									System.out.println("idOrderMaping映射关系:"+a);
								}*/
		      					for(int i=0;i<ids.length;i++){
		      						//Integer cellNum = orderIndexMaping.get(idOrderMaping.get(ids[i]));//根据id获取当前id对应的列号
		      						Integer cellNum = Integer.parseInt(ids[i])-1;
		      						dynamicCell = row.getCell(cellNum);//获取当前列
		      						if(dynamicCell.getStringCellValue()==null||
		      						  "".equals( dynamicCell.getStringCellValue())){
		      							resultList.add(new Boolean(false));
		      							resultList.add(new String("公式指定的目标列为空,"));
		      							return resultList;
		      						}
		      						try {
		      							dynamicCellValue += Double.parseDouble(dynamicCell.getStringCellValue());//获取单元格中的数字
									} catch (Exception e) {
										resultList.add(new Boolean(false));
		      							resultList.add(new String("公式指定的目标列不是数字类型,"));
		      							return resultList;
									}
		      						System.out.println("当前id对应的列号:"+cellNum+",对应的值:"+expectedValue);
		      						dynamicCellValues.add(dynamicCellValue);
		      					}
		      					for (int i=0;i<dynamicCellValues.size();i++) {
		      						if(i!=0){//从第二起开始做运算
		      							if("+".equals(operatorLists.get(i-1))){
		           							expectedValue += dynamicCellValues.get(i);
		           						}else if("-".equals(operatorLists.get(i-1))){
		           							expectedValue -= dynamicCellValues.get(i);
		           						}
		      						}
								}
		      					
		      					System.out.println("预期值:"+expectedValue+",实际值:"+detailValue);
		      					if(expectedValue!=Double.parseDouble(detailValue)){//不符合预期值
		      						resultList.add(new Boolean(false));
		    						resultList.add(new String("公式计算错误,"));
		    						return resultList;
		      					}else{
		      						resultList.add(new Boolean(true));
		    						resultList.add(new String("公式计算正确,"));
		    						return resultList;
		      					}
		      			 	}else{
		      			 		resultList.add(new Boolean(false));
								resultList.add(new String("非sum函数目前无法校验,"));
								return resultList;
		      			 	}
		      		  }else{/*类型为其他限定类型*/
		      			  
		  		  		String[] results;
		  				if(excelGs.startsWith("限定列表")){//若类型为设置列表
		  					System.out.println("进入校验列表");
		  					results = AddFormulaAndDataValidationForExcel.parseExcelList(excelGs);
		  					for (String result : results) {
		  						if(result!=null&&!"".equals(result)){
		  							if(result.equals(detailValue)){
		  								System.out.println("校验列表成功,");
		  		  						resultList.add(new Boolean(true));
		  								resultList.add(new String("校验列表成功,"));
		  								System.out.println("用户输入的为:"+detailValue);
		  								return resultList;
		  							}
		  						}
		  					}
		  					
		  					
		  				}else{
		  					results = AddFormulaAndDataValidationForExcel.parseExcelConstraint(excelGs);
		  					if("限定时刻".equals(results[0])||"限定日期".equals(results[0])){//如果是日期类型
		  						if(detailValue.trim().equals(results[1].trim())||
		  						   detailValue.trim().equals(results[2].trim())){//若解析后字符串一样直接成功
		  							resultList.add(new Boolean(true));
		  							resultList.add(new String("校验日期成功,"));
		  							return resultList;
		  						}else{
		  							/*获取不同字符串对应的日期类型*/
		  							int type1 = getDateFormatByString(detailValue);
		  							int type2 = getDateFormatByString(results[1],"公式规定的放松要求");
		  							int type3 = getDateFormatByString(results[2],"公式规定的放松要求");
		  							if(type1==-1||type2==-1||type3==-1){//////////////////////
		  								resultList.add(new Boolean(false));
			  							resultList.add(new String("日期类型校验失败,"));
			  							return resultList;
		  							}
		  							SimpleDateFormat sdf1 = new SimpleDateFormat(DATA_FORMAT.get(type1));
		  							SimpleDateFormat sdf2 = new SimpleDateFormat(DATA_FORMAT.get(type2));
		  							SimpleDateFormat sdf3 = new SimpleDateFormat(DATA_FORMAT.get(type3));
		  							try {
		  								Date date1 = sdf1.parse(detailValue);
		  								Date date2 = sdf2.parse(results[1]);
		  								Date date3 = sdf3.parse(results[2]);
		  								if(date1.getTime()>=date2.getTime()&&date1.getTime()<=date3.getTime()){
		  									resultList.add(new Boolean(true));
		  		  							resultList.add(new String("校验日期成功,"));
		  		  							return resultList;
		  								}else{
		  									resultList.add(new Boolean(false));
				  							resultList.add(new String("日期不在规定范围内,"));
				  							return resultList;
		  								}
		  								/*if(date1.after(date2)&&date1.before(date3)){//若日期在公式规定范围内
		  									resultList.add(new Boolean(true));
		  		  							resultList.add(new String("校验日期成功,"));
		  		  							return resultList;
		  								}else{
		  									resultList.add(new Boolean(false));
				  							resultList.add(new String("日期不在规定范围内,"));
				  							return resultList;
		  								}*/
		  							} catch (ParseException e) {
		  							}
		  						}
		  					}else if("限定文本长度".equals(results[0])){
		  						if(Integer.parseInt(results[1])<=detailValue.length()&&Integer.parseInt(results[2])>=detailValue.length()){
		  							resultList.add(new Boolean(true));
  			  						resultList.add(new String("校验文本长度成功,"));
  			  						return resultList;
		  						}else{
		  							resultList.add(new Boolean(false));
  			  						resultList.add(new String("文本长度不正确,"));
  			  						return resultList;
		  						}
		  					}else{//若非限定时间类型的
		  						System.out.println("进入校验数字类型,");
		  						System.out.println("results[1]:"+results[1]+",results[2]:"+results[2]);
		  						System.out.println("detailValue:"+detailValue);
		  						try {
		  							if(Double.parseDouble(detailValue)>=Double.parseDouble(results[1])&&
		  		  						  Double.parseDouble(detailValue)<=Double.parseDouble(results[2])){
		  		  							System.out.println("校验数字类型成功,");
		  		  							resultList.add(new Boolean(true));
		  			  						resultList.add(new String("校验数字类型成功,"));
		  			  						return resultList;
		  		  						}else{
		  		  						resultList.add(new Boolean(false));
	  			  						resultList.add(new String("数字不在指定范围内,"));
	  			  						return resultList;
		  		  						}
								} catch (Exception e) {
									e.printStackTrace();
									resultList.add(new Boolean(false));
		  							resultList.add(new String("公式指定的目标列不是数字类型,"));
		  							return resultList;
								}
		  					}
		  				}
		      		  }
		
		resultList.add(new Boolean(false));
		resultList.add(new String("不支持的限定类型,"));
		return resultList;
	  }
}
