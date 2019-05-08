package com.haier.datamart.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.haier.datamart.entity.EnteringLogs;
import com.haier.datamart.entity.User;
import com.haier.datamart.service.impl.EnteringLogsServiceImpl;
@Component
public class DataUpdateOne {
	@Autowired
	private  EnteringLogsServiceImpl elsi;
	public static DataUpdateOne dataUpdateOne;
	public void setUserInfo(EnteringLogsServiceImpl elsi) {
		this.elsi = elsi;
	}
	@PostConstruct
    public void init() {
		dataUpdateOne = this;
		dataUpdateOne.elsi = this.elsi;
 
    }
	/**
	 * 检查传入的单条数据并据此更新数据库
	 * @param user 使用修改操作的用户
	 * @param tableName 需要更新的表名
	 * @param idOfEntry 传入的要更新的单条数据的id
	 * @param map 传入的键值对
	 * @param orderIdMaping setting_detail表中的order_no和id的映射关系
	 * @param idOrderMaping setting_detail表中的id和order_no的映射关系
	 * @param colNumTypeMaping 列号对应的col_type
	 * @param colNumNameMaping 列号对应的col_colName
	 * @param colNumMaxLengthMaping 列号对应的maxLength
	 * @param colNumExcelGsMaping 列号对应的excelGs
	 * @return 更新情况
	 */
	public static String  checkOneAndUpdate(
				User user,
				String tableName,
				String bakTableName,
				String idOfEntry,
				Map<String,String> map,
				/*Map<Integer,String> orderIdMaping,*/
				Map<String,Integer> idOrderMaping,
				Map<String,String> colNumTypeMaping,
				Map<String,String> colNumNameMaping,
				Map<String,String> colNumIsPkMaping,
				Map<String,String> colNumMaxLengthMaping,
				Map<String,String> colNumExcelGsMaping,
				String url,String names,String pwd,
				String bakUrl,String bakNames,String bakPwd,
				String settingId)throws Exception {
		
		String error_col="";//记录异常的字段
	    String error_msg="";//记录异常的字段错误原因
	    HashMap<String, String> mapValue = new LinkedHashMap<String, String>();//储存excel列名与列指的对应，以插入数据库
	    /*check阶段*/
	   /* List<Integer> orderList = new ArrayList<Integer>();//存放order_no
		Set<String> set = idOrderMaping.keySet();
		for (Object object : set) {
			orderList.add(idOrderMaping.get((String) object));
		}
		Collections.sort(orderList);//将order_no排序
		HashMap<Integer, Integer> orderIndexMaping 
				= new HashMap<Integer, Integer>();
		
		for (int i = 0; i < orderList.size(); i++) {//生成列号和order的映射 
			orderIndexMaping.put(i+1, orderList.get(i));
		}*/
	    HashMap<String, Object> mapValueForUpdate= new HashMap<String, Object>();//储存excel列名与列指的对应，以更新数据库
	    Map<Integer,String> idDetailVlueMaping = new LinkedHashMap<Integer,String>();//settingid表中的id和对应实体表的字段值的映射关系
	    Integer a = 0;
	    for (String value : map.values()) {
			a++;
			//根据列号获取顺序,再根据顺序获取id
			//idDetailVlueMaping.put(Integer.parseInt(orderIdMaping.get(orderIndexMaping.get(a))), value);
			idDetailVlueMaping.put(a, value);
		}
	    int num = -1;//记录遍历次数
	    mapValue.put("is_success", "0");
	    
	    for (String key : map.keySet()){
	    	System.out.println("key==="+key);///////
	    	num++;
	    	String cell = map.get(key);
	    	String colName = key;
	    	 if (cell==null ||"".equals(cell.trim())) {
	   		   //String colName = key;
	    		
	   		   String name = colNumNameMaping.get(num+"");
	   		   mapValue.put(colName, "");
	   		   mapValue.put("is_success", "1");
	   		   error_col += name+",";
	   		   error_msg+="空白格,";
	   		   mapValue.put("error_msg", error_msg);
	   		   mapValue.put("error_col", error_col);
	          }else{//if (null != cell)
	        	String colType;
           	   if((String) colNumTypeMaping.get(num+"")==null||"".equals((String)colNumTypeMaping.get(num+""))){
           		   colType = "varchar";
           	   }else{
           		   colType = ((String) colNumTypeMaping.get(num+"")).toLowerCase();
           	   }
	        	
	       	   //String colName=(String) map.get(num+"");
	       	   String name=(String) colNumNameMaping.get(num+"");
	       	   String detailValue = map.get(key);
	       	   String isPk=(String) colNumIsPkMaping.get(num+"");
	       	  // String detailValue = cell.getStringCellValue();
	       	   //System.out.println("mapMaxLength.get(c)"+mapMaxLength.get(c+""));
	       	   Integer colMaxLength = 10000;
	       	   if(colNumMaxLengthMaping.get(num+"")!=null&&"".equals(colNumMaxLengthMaping.get(num+""))){
	       		  colMaxLength = Integer.parseInt(colNumMaxLengthMaping.get(num+""));//获取当前列规定的最大长度
	       	   }
	       	   String excelGs = colNumExcelGsMaping.get(num+"");//获取当前列的excel公式
	       	   //存入前去空格
	       	   mapValue.put(colName,detailValue.replaceAll("^[　*| *| *|//s*]*", "").replaceAll("[　*| *| *|//s*]*$", ""));
	       	   
	       	   if(excelGs!=null&&!"".equals(excelGs)){
	       		 List formatResults = DataUpdateOne.tesTbyConstraint(excelGs, detailValue, idOrderMaping, idDetailVlueMaping);
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
	  		            			   error_msg+="此处只能为数字类型数据,";
	  		            			   throw new Exception("内容填写异常!");
	  		            		   }
	  		            	   }
	  		            	   
	  		            	   if("datetime".equals(colType) || "timestamp".equals(colType) ||  
	  		            		"date".equals(colType) ||"time".equals(colType)){
	  		            		   if(DataSupplementUtils.getDateFormatByString(detailValue)!=-1){
	  		                		  // mapValue.put("is_success", "0");
	  		            		   }else{
	  		            			   error_msg+="此处只能存放时间类型数据,";
	  		            			   throw new Exception("内容填写异常,");
	  		            		   }
	  		            	   } else{
	  	                		   //mapValue.put("is_success", "0");
	  		            	   }
	           	 		  }else {
	           	 			 error_msg+="数据长度异常,";
	           	 			 throw new Exception("长度异常,");
	           	 		  }
	           	 		
	  					} catch (Exception e) {
	  						  mapValue.put("is_success", "1");
	  						  mapValue.put("error_msg", error_msg);
	  						  error_col+=name+",";
	  						  mapValue.put("error_col", error_col);
	           		   }
	           	   if("1".equals(isPk)){
	       			   mapValueForUpdate.put(colName, detailValue);
	       		   }
	           	  
	          }//else结束
		     	  
		}//for循环结束
	    //判断传入数据和规定主键是否冲突
	    
	    /*检测是否有pk冲突*/
	    boolean flag = true;
	    String result = idOfEntry;
	    Connection conn = ExcelConnection.getConn(url,names,pwd);
	    PreparedStatement  pstmt = null ;
	    ResultSet rs = null;
	    String sqlForUpdate="";
           if(!CollectionUtils.isEmpty(mapValueForUpdate)){
        	   sqlForUpdate+="select id from "+tableName+" where del_flag=0 ";
        	   if(mapValueForUpdate.size()!=0){
        		   sqlForUpdate+=" and ";
        	   }
        	   for (String in : mapValueForUpdate.keySet()) {
        		   String str = (String) mapValueForUpdate.get(in);//得到每个key多对用value的值
        		   sqlForUpdate+=in+"='"+str+"' and ";
	        	    }
        	   sqlForUpdate = sqlForUpdate.substring(0,sqlForUpdate.length() - 4);
        	   pstmt = conn.prepareStatement(sqlForUpdate);
               rs =  pstmt.executeQuery();
    	       if(rs.next()){
    	    	   flag = false;
    	    	   result = rs.getString(1);
    	       }
           }
	      
	    if(idOfEntry.startsWith("ADD")&&!flag){//若为增加按钮且存在结果集
	    	idOfEntry = result;//id是结果集
	    }else{//若为更新按钮
	    	if(!flag){//若有结果集
	    		idOfEntry = result;//id是结果集
	    	}
	    }
	    
	    
	    try {
			updateOne(user,tableName,mapValue, idOfEntry, error_col, error_msg,url,names,pwd);
			//增加备份啊喂
			idOfEntry = "ADD"+GenerationSequenceUtil.getUUID();
			updateOne(user,bakTableName,mapValue, idOfEntry, error_col, error_msg,bakUrl,bakNames,bakPwd);
			/*增加日志*/
			int successNum = 0;
			int failNum = 0;
			if("".equals(error_col)){
				successNum = 1;
			}else{
				failNum = 1;
			}
		    Date date = new Date();
	        DateFormat formater = new SimpleDateFormat(
	        "yyyy-MM-dd HH:mm:ss");
	        String dateStr = formater.format(date);
	        
	        
			EnteringLogs log = new EnteringLogs();
	           log.setId(GenerationSequenceUtil.getUUID());
	           log.setAllNum(1+"");
	           log.setSuccessNum(successNum+"");
	           log.setFailNum(failNum+"");
	           log.setFailData(null);
	           log.setIndustryId("");
	           log.setSettingId(settingId);
	           if(user.getId()==null||"".equals(user.getId())){
	        	   user.setId("null");
	           }
	           log.setUpdateBy(user.getId());
	           log.setUpdateDate(dateStr);
	           if(!idOfEntry.startsWith("ADD")){//如果是修改功能,适应创建人和创建日期
	        	   pstmt = conn.prepareStatement
	        	 ("select create_date,create_by from "+tableName+" where del_flag=0 and id='"+idOfEntry+"'");
	        	   rs = pstmt.executeQuery();
	        	   while(rs.next()){
	        		   dateStr = rs.getString(1);
		        	   user.setId(rs.getString(2));
	        	   }
	           }
	           log.setCreateDate(dateStr);
	           log.setCreateBy(user.getId());
	           System.out.println("补录日志:"+log);
	           dataUpdateOne.elsi.addLog(log);
	           ExcelConnection.close(conn, pstmt, rs);
		} catch (SQLException e) {
			e.printStackTrace();
			return "发生未知错误,不符合限定公式要求校验失败据更新失败!";
		}
	    if("".equals(error_col)||"".equals(error_msg)){
	    	 return "更新成功!";
	    }else{
	    	 return "更新成功:其中有填写异常的字段!";
	    }
	   
		//return "更新成功:其中填写异常的字段有:"+error_col+",错误原因是:"+error_msg;
	}
	public static void updateOne(User user,String tableName,Map<String,String> mapValue,
			String idOfEntry,String error_col,String error_msg,String url,String name,String pwd) throws SQLException{
		Date date = new Date();
        DateFormat formater = new SimpleDateFormat(
        "yyyy-MM-dd HH:mm:ss");
        String dateStr = formater.format(date);//获取更新时间为当前时间
		Connection conn = ExcelConnection.getConn(url,name,pwd);//获取数据库连接
		PreparedStatement stat = null;
		//ResultSet rs = null;
		String entrys = "";
		String isSuccess = "";
		if(idOfEntry.startsWith("ADD")){//增加功能
			for (Entry<String, String> entry : mapValue.entrySet()) {
				if("is_success".equals(entry.getKey())){
					isSuccess = entry.getValue();
				}
				if("error_msg".equals(entry.getKey())||"error_col".equals(entry.getKey())||"is_success".equals(entry.getKey())){
					continue;
				}
				entrys += "'"+entry.getValue()+"',";
			}
		}else{
			for (Entry<String, String> entry : mapValue.entrySet()) {
				entrys += entry.getKey()+"='"+entry.getValue()+"',";
			}
		}
		entrys.replace("null", "");
		String	sql = "";
		if(idOfEntry.startsWith("ADD")){
			sql = "insert into "+tableName+" values('"+ idOfEntry.replace("ADD", "")+"',"+entrys
					+isSuccess+",'"+error_col+"','"+error_msg+"','"
					+dateStr+"','"+user.getId()+"','"
					+dateStr+"','"+user.getId()+"','"
					+"默认'"+",0)";
		}else{
			sql = "UPDATE "+tableName+" SET "+entrys+"update_by='"
					+user.getId()
					+"',update_date='"+dateStr
					+"',error_msg='"+error_msg
					+"',error_col='"+error_col
					+"' WHERE id='"
					+idOfEntry+"' AND del_flag!=1";
		}
		 	
		System.out.println("sql===="+sql);
		stat = conn.prepareStatement(sql);
		stat.executeUpdate();//执行更新
		try {
			if(conn!=null){
				conn.close();
			}
			if(stat!=null){
				stat.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteOne(String tableName,String idOfEntry,User user,String url,String name,String pwd) throws SQLException{
		Date date = new Date();
        DateFormat formater = new SimpleDateFormat(
        "yyyy-MM-dd HH:mm:ss");
        String dateStr = formater.format(date);//获取更新时间为当前时间
		Connection conn = ExcelConnection.getConn(url,name,pwd);//获取数据库连接
		PreparedStatement stat = null;
		String sql = "UPDATE "+tableName+" SET "+"update_by='"
					+user.getId()
					+"',update_date='"+dateStr
					+"',del_flag='1'"
					+" WHERE "
					+"id= '"+idOfEntry+"'";
		System.out.println("sql==="+sql);
		stat = conn.prepareStatement(sql);
		stat.executeUpdate();
		try {
			if(conn!=null){
				conn.close();
			}
			if(stat!=null){
				stat.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param excelGs
	 * @param detailValue
	 * @param idOrderMaping settingid表中的id和顺序的映射关系
	 * @param idDetailVlueMaping   settingid表中的id和对应实体表的字段值的映射关系
	 * @return 
	 */
	public static List tesTbyConstraint (String excelGs, String detailValue, Map<String, Integer> idOrderMaping,
			Map<Integer, String> idDetailVlueMaping){
		List resultList = new ArrayList();
		try {
			/* 如果类型为设置公式 */
			if (!excelGs.startsWith("限定")) {
				String[] ids = null;
				Map<Integer, Integer> orderIndexMaping = null;
				try {
					ids = AddFormulaAndDataValidationForExcel.getIdFroFormula(excelGs);// 获取公式中包含的列号
					List<Integer> orderList = new ArrayList<Integer>();

					/*
					 * for (Entry<String, Integer> a : idOrderMaping.entrySet()) {
					 * System.out.println("前置idOrderMaping映射关系:"+a); }
					 */
					Set<String> set = idOrderMaping.keySet();
					for (Object object : set) {
						// System.out.println("set中object:"+Integer.parseInt((String)object));
						// System.out.println("获取id获取顺序id"+idOrderMaping.get((String)object));
						orderList.add(idOrderMaping.get((String) object));
					}
					Collections.sort(orderList);
					/*
					 * for (Integer integer : orderList) {
					 * System.out.print("id顺序"+integer); }
					 */
					/* 生成orderno和列号的映射 */
					orderIndexMaping = new HashMap<Integer, Integer>();
					for (int i = 0; i < orderList.size(); i++) {
						orderIndexMaping.put(orderList.get(i), i);
					}
					/*
					 * for (Entry<Integer, Integer> a : orderIndexMaping.entrySet())
					 * { System.out.println("orderno和列号的映射"+a); }
					 */
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (excelGs.startsWith("sum") || excelGs.startsWith("SUM")) {// 若公式为sum
					System.out.println("进入校验sum公式,");
					String sub = excelGs.substring(4, excelGs.length() - 1);
					String[] operators = sub.split("\\d");

					List<String> operatorLists = new ArrayList<String>();
					// 筛选出公式中运算符
					for (String operator : operators) {
						if ("+".equals(operator) || "-".equals(operator)) {
							operatorLists.add(operator);
						}
					}
					String dynamicCell = null;
					Double expectedValue = -0.0;// 预期值
					Double dynamicCellValue = 0.0;// 记录目标列的值
					List<Double> dynamicCellValues = new ArrayList<Double>();// 存放目标列的值
					/*
					 * for (Entry<String, Integer> a : idOrderMaping.entrySet()) {
					 * System.out.println("idOrderMaping映射关系:"+a); }
					 */
					for (int i = 0; i < ids.length; i++) {
						//Integer cellNum = orderIndexMaping.get(idOrderMaping.get(ids[i]));// 根据id获取当前id对应的列号
						Integer cellNum = Integer.parseInt(ids[i]);
						dynamicCell = idDetailVlueMaping.get(cellNum);// 获取当前列
						if (idDetailVlueMaping.get(cellNum) == null || "".equals(idDetailVlueMaping.get(cellNum))) {
							resultList.add(new Boolean(false));
							resultList.add(new String("公式指定的目标列为空,"));
							return resultList;
						}
						try {
							dynamicCellValue += Double.parseDouble(idDetailVlueMaping.get(cellNum));// 获取单元格中的数字
						} catch (Exception e) {
							resultList.add(new Boolean(false));
							resultList.add(new String("公式指定的目标列不是数字类型,"));
							return resultList;
						}
						System.out.println("当前id对应的列号:" + cellNum + ",对应的值:" + expectedValue);
						dynamicCellValues.add(dynamicCellValue);
					}
					for (int i = 0; i < dynamicCellValues.size(); i++) {
						if (i != 0) {// 从第二起开始做运算
							if ("+".equals(operatorLists.get(i - 1))) {
								expectedValue += dynamicCellValues.get(i);
							} else if ("-".equals(operatorLists.get(i - 1))) {
								expectedValue -= dynamicCellValues.get(i);
							}
						}
					}

					System.out.println("预期值:" + expectedValue + ",实际值:" + detailValue);
					if (expectedValue != Double.parseDouble(detailValue)) {// 不符合预期值
						resultList.add(new Boolean(false));
						resultList.add(new String("公式计算错误,"));
						return resultList;
					} else {
						resultList.add(new Boolean(true));
						resultList.add(new String("公式计算正确,"));
						return resultList;
					}
				} else {
					resultList.add(new Boolean(false));
					resultList.add(new String("非sum函数,目前无法校验,"));
					return resultList;
				}
			} else {/* 类型为其他限定类型 */

				String[] results;
				if (excelGs.startsWith("限定列表")) {// 若类型为设置列表
					System.out.println("进入校验列表,");
					results = AddFormulaAndDataValidationForExcel.parseExcelList(excelGs);
					for (String result : results) {
						if (result != null && !"".equals(result)) {
							if (result.equals(detailValue)) {
								System.out.println("校验列表成功,");
								resultList.add(new Boolean(true));
								resultList.add(new String("校验列表成功,"));
								System.out.println("用户输入的为:" + detailValue);
								return resultList;
							}
						}
					}

				} else {
					results = AddFormulaAndDataValidationForExcel.parseExcelConstraint(excelGs);
					if ("限定时刻".equals(results[0]) || "限定日期".equals(results[0])) {// 如果是日期类型
						if (detailValue.trim().equals(results[1].trim()) || detailValue.trim().equals(results[2].trim())) {// 若解析后字符串一样直接成功
							resultList.add(new Boolean(true));
							resultList.add(new String("校验日期成功,"));
							return resultList;
						} else {
							/* 获取不同字符串对应的日期类型 */
							int type1 = DataSupplementUtils.getDateFormatByString(detailValue);
							int type2 = DataSupplementUtils.getDateFormatByString(results[1],"");
							int type3 = DataSupplementUtils.getDateFormatByString(results[2],"");
							if (type1==-1||type2 == -1 || type3 == -1) {/////
								resultList.add(new Boolean(false));
								resultList.add(new String("校验日期失败,"));
								return resultList;
								/*resultList.add(new Boolean(true));
								resultList.add(new String("校验日期成功"));
								return resultList;*/
							}
							SimpleDateFormat sdf1 = new SimpleDateFormat(DataSupplementUtils.DATA_FORMAT.get(type1));
							SimpleDateFormat sdf2 = new SimpleDateFormat(DataSupplementUtils.DATA_FORMAT.get(type2));
							SimpleDateFormat sdf3 = new SimpleDateFormat(DataSupplementUtils.DATA_FORMAT.get(type3));
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
								/*if (date1.after(date2) && date1.before(date3)) {// 若日期在公式规定范围内
									resultList.add(new Boolean(true));
									resultList.add(new String("校验日期成功,"));
									return resultList;
								}else{//日期不在公式规定范围内
									resultList.add(new Boolean(false));
									resultList.add(new String("日期不在规定范围内,"));
									return resultList;
								}*/
							} catch (ParseException e) {
								e.printStackTrace();
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
					}else {// 若非限定时间类型的
						System.out.println("进入校验数字类型");
						System.out.println("results[1]:" + results[1] + ",results[2]:" + results[2]);
						System.out.println("detailValue:" + detailValue);
						try {
							if (Double.parseDouble(detailValue) >= Double.parseDouble(results[1])
									&& Double.parseDouble(detailValue) <= Double.parseDouble(results[2])) {
								System.out.println("校验数字类型成功,");
								resultList.add(new Boolean(true));
								resultList.add(new String("校验数字类型成功,"));
								return resultList;
							}
						} catch (Exception e) {
							resultList.add(new Boolean(false));
							resultList.add(new String("公式指定的目标列不是数字类型,"));
							return resultList;
						}
					}
				}
			}
			resultList.add(new Boolean(false));
			resultList.add(new String("不符合限定公式要求校验失败,"));
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			resultList.add(new Boolean(true));
			resultList.add(new String("没有设置公式限定或者不支持当前公式校验,"));
			return resultList;
		}
	}
}
