package com.haier.datamart.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.util.StringUtil;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.entity.AdminDatasourceConfig;
import com.haier.datamart.entity.EnteringTableSettingDetail;
import com.haier.datamart.entity.EnteringTableSettingInfo;
import com.haier.datamart.service.IAdminDatasourceConfigService;
import com.haier.datamart.service.IEnteringTableSettingInfoService;
import com.haier.datamart.utils.DataSupplementUtils;
import com.haier.datamart.utils.ExcelConnection;
import com.haier.datamart.utils.GenerationSequenceUtil;

/**
 * 
 * 补录配置HTTP接口
 * zuoqb
 */
@RestController
public class EnteringHttpController {
	@Autowired
	private IEnteringTableSettingInfoService settingInfoService;
	@Autowired
	private IAdminDatasourceConfigService adminDatasourceConfigService;
	private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 
	 * @time   2018年9月18日 上午11:43:23
	 * @author zuoqb
	 * @todo   对外提供补录HTTP接口
	 * @param  @param params：补录数据 格式：
	 * @param  @param userCode：用户编码
	 * @param  @param type：类型 1-根据ID 0-根据配置名称 默认为0
	 * @param  [
    {
        "type": "cxzcxzcxzc",
        "data": [
            {
                "dvdxgsdsfff": "11.3",
                "na": "日日顺物流",
                "ffff": 294,
                "ggg": "dfgdfd"
            },
            {
                "dvdxgsdsfff": "201807",
                "na": "55",
                "ffff": 66,
                "ggg": "201807"
            }
        ]
    }
]
	 * @return_type   PublicResult
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/entering/insert/v1", produces = { "application/json;charset=UTF-8" })
	//@Log(description = "补录配置HTTP接口:/entering/insert")
	public PublicResult insertV1(@RequestParam(value="params",required = true) String params,
			@RequestParam(value="userCode",required = true) String userCode,@RequestParam(value="type",required = false) String type) {
		try {
			if(StringUtils.isBlank(params)){
				return new PublicResult<>(PublicResultConstant.FAILED,
						"补录数据不能为空");
			}else if(StringUtils.isBlank(userCode)){
				return new PublicResult<>(PublicResultConstant.FAILED,
						"用户工号不能为空");
			}else{
				if(StringUtils.isBlank(type)){
					type="0";//根据补录ID补录数据
				}
				JSONArray arr=JSONArray.parseArray(params);
				Date date=new Date();
				String createDate=sdf.format(date);
				String allMsg= "";
				for(int i=0;i<arr.size();i++){
					List<String> sqls=new ArrayList<String>();//用於儲存sql語句做批量插入 目标表
					List<String> baksqls=new ArrayList<String>();//用於儲存sql語句做批量插入备份表
					JSONObject jsonObject = arr.getJSONObject(i); // 遍历 jsonarray 数组，把每一个对象转成 json 对象
					 String key =jsonObject.getString("type");//作用域-补录配置编码
					  //根据作用域查询配置信息
					  EnteringTableSettingInfo settingInfo=null;
					  if("0".equals(type)){
						  //根据配置名称查询补录配置
						 settingInfo=settingInfoService.getSettingInfoByDesc(key);
					  }else if("1".equals(type)){
						  settingInfo=settingInfoService.getById(key);
					  }
					  if(settingInfo==null){
						  return new PublicResult<>(PublicResultConstant.FAILED,"作用域：‘"+key+"’未进行补录配置");
					  }
					  List<EnteringTableSettingDetail> settingDetailList = settingInfoService.getBySettingId(settingInfo.getId());
					  if(settingDetailList==null||settingDetailList.size()==0){
						  return new PublicResult<>(PublicResultConstant.FAILED,"作用域：‘"+key+"’未进行补录明细配置");
					  }
					  //获取数据源
					  AdminDatasourceConfig config = adminDatasourceConfigService.get(settingInfo.getDatasourceConfigId());
					  //获取备份数据源
					  AdminDatasourceConfig bakConfig=  adminDatasourceConfigService.get(settingInfo.getBakDatasourceConfigId());
					  String errorMsg= "";
					  String mValue=jsonObject.getString("data");//当前作用域传入的补录数据
					  if(config==null||bakConfig==null){
						  return new PublicResult<>(PublicResultConstant.FAILED,settingInfo.getName()+"补录数据源不存在");
					  }else{
						  errorMsg=  dealEnteringSql(userCode, createDate, sqls, baksqls,
								jsonObject, key, settingInfo, settingDetailList,mValue,config);
					  }
					  JSONArray mData=JSONArray.parseArray(mValue);//当前作用域数据
					  if("0".equals(settingInfo.getErrorContinue())||StringUtils.isBlank(errorMsg)){
						  operationoperationDb(sqls, baksqls, config, bakConfig);
						  allMsg+="总条数"+(mData.size())+"。"+"导入成功共"+(sqls.size())+"条数据！其中存在以下问题问题。";
						  if(StringUtils.isNotBlank(errorMsg)){
							  allMsg+=errorMsg;
						  }
					  }else{
						  String optResult = "总条数"+(mData.size())+"。由于补录配置不允许出现错误继续导入，故导入成功0条！";
						  allMsg+=optResult+errorMsg;
					  }
					
				}
				if(allMsg.indexOf("补录配置不允许出现错误继续导入")!=-1){
					return new PublicResult<>(PublicResultConstant.FAILED, allMsg);
				}else{
					return new PublicResult<>(PublicResultConstant.SUCCESS, allMsg);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED,
					e.getMessage());
		}

	}
	
	/**
	 * 
	 * @time   2018年12月10日 下午4:54:42
	 * @author zuoqb
		  [{
	        "补录配置编码/配置名称": [
	            {
	                "补录配置表字段": "补录配置表字段对应数值",
	                "CBK_NAME": "日日顺物流",
	                "VALUE": 294,
	                "CBK_CODE": "BD1010001001"
	            }
	        ]
	    },
	    {
	        "2": [
	            {
	                "buss_date": "201807",
	                "xiaowei": "日日顺物流",
	                "cdate": 294,
	                "CBK_CODE": "BD1010001001"
	            },
	            {
	                "buss_date": "201807",
	                "xiaowei": "日日顺乐家",
	                "cdate": 11,
	                "CBK_CODE": "BD1010001002"
	            }
	        ]
	    }
	]
	 * @return_type   PublicResult
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/entering/insert", produces = { "application/json;charset=UTF-8" })
	//@Log(description = "补录配置HTTP接口:/entering/insert")
	public PublicResult insert(@RequestParam(value="params",required = true) String params,
			@RequestParam(value="userCode",required = true) String userCode,@RequestParam(value="type",required = false) String type) {
		try {
			if(StringUtils.isBlank(params)){
				return new PublicResult<>(PublicResultConstant.FAILED,
						"补录数据不能为空");
			}else if(StringUtils.isBlank(userCode)){
				return new PublicResult<>(PublicResultConstant.FAILED,
						"用户工号不能为空");
			}else{
		
				if(StringUtils.isBlank(type)){
					type="0";//根据补录ID补录数据
				}
				JSONArray arr=JSONArray.parseArray(params);
				Date date=new Date();
				String createDate=sdf.format(date);
				String allMsg= "";
				for(int i=0;i<arr.size();i++){
					List<String> sqls=new ArrayList<String>();//用於儲存sql語句做批量插入 目标表
					List<String> baksqls=new ArrayList<String>();//用於儲存sql語句做批量插入备份表
					JSONObject jsonObject = arr.getJSONObject(i); // 遍历 jsonarray 数组，把每一个对象转成 json 对象
					Iterator<String> it = jsonObject.keySet().iterator(); //遍历key
					while (it.hasNext()) {  
					  String key = it.next();//作用域-补录配置编码
					  //根据作用域查询配置信息
					  EnteringTableSettingInfo settingInfo=null;
					  if("0".equals(type)){
						  //根据配置名称查询补录配置
						 settingInfo=settingInfoService.getSettingInfoByDesc(key);
					  }else if("1".equals(type)){
						  settingInfo=settingInfoService.getById(key);
					  }
					  if(settingInfo==null){
						  return new PublicResult<>(PublicResultConstant.FAILED,"作用域：‘"+key+"’未进行补录配置");
					  }
					  List<EnteringTableSettingDetail> settingDetailList = settingInfoService.getBySettingId(settingInfo.getId());
					  if(settingDetailList==null||settingDetailList.size()==0){
						  return new PublicResult<>(PublicResultConstant.FAILED,"作用域：‘"+key+"’未进行补录明细配置");
					  }
					  //获取数据源
					  AdminDatasourceConfig config = adminDatasourceConfigService.get(settingInfo.getDatasourceConfigId());
					  //获取备份数据源
					  AdminDatasourceConfig bakConfig=  adminDatasourceConfigService.get(settingInfo.getBakDatasourceConfigId());
					  String errorMsg= "";
					  String mValue=jsonObject.getString(key);//当前作用域传入的补录数据
					  if(config==null||bakConfig==null){
						  return new PublicResult<>(PublicResultConstant.FAILED,settingInfo.getName()+"补录数据源不存在");
					  }else{
						  errorMsg=  dealEnteringSql(userCode, createDate, sqls, baksqls,
								jsonObject, key, settingInfo, settingDetailList,mValue,config);
					  }
					  JSONArray mData=JSONArray.parseArray(mValue);//当前作用域数据
					  if("0".equals(settingInfo.getErrorContinue())||StringUtils.isBlank(errorMsg)){
						  operationoperationDb(sqls, baksqls, config, bakConfig);
						  allMsg+="总条数"+(mData.size())+"。"+"导入成功共"+(sqls.size())+"条数据！其中存在以下问题问题。";
						  if(StringUtils.isNotBlank(errorMsg)){
							  allMsg+=errorMsg;
						  }
					  }else{
						  String optResult = "总条数"+(mData.size())+"。由于补录配置不允许出现错误继续导入，故导入成功0条！";
						  allMsg+=optResult+errorMsg;
					  }
					}  
					
				}
				if(allMsg.indexOf("补录配置不允许出现错误继续导入")!=-1){
					return new PublicResult<>(PublicResultConstant.FAILED, allMsg);
				}else{
					return new PublicResult<>(PublicResultConstant.SUCCESS, allMsg);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED,
					e.getMessage());
		}

	}

	/**
	 * 操作数据库-将数据入库
	 * @param sqls
	 * @param baksqls
	 * @param config
	 * @param bakConfig
	 * @throws SQLException
	 */
	protected void operationoperationDb(List<String> sqls, List<String> baksqls,
			AdminDatasourceConfig config, AdminDatasourceConfig bakConfig)
			throws SQLException {
		Connection conn = ExcelConnection.getConn(config.getDbUrl(), config.getDbName(), config.getDbPassword());
		conn.setAutoCommit(false);
		Connection bakConn = ExcelConnection.getConn(bakConfig.getDbUrl(), bakConfig.getDbName(), bakConfig.getDbPassword());// 切换备份数据源
		bakConn.setAutoCommit(false);
		Statement stmt = (Statement) conn.createStatement();
		Statement bakstmt = (Statement) bakConn.createStatement();
		for (String sql : sqls) {
			stmt.addBatch(sql);
		}
		// 备份
		for (String baksql : baksqls) {
			bakstmt.addBatch(baksql);
		}
		stmt.executeBatch();
		bakstmt.executeBatch();
		if (!conn.getAutoCommit())
		conn.commit();
		if (!bakConn.getAutoCommit())
		bakConn.commit();
	}
	/**
	 * 处理数据拼接sql语句
	 * @param userCode
	 * @param createDate
	 * @param sqls
	 * @param baksqls
	 * @param jsonObject
	 * @param key
	 * @param settingInfo
	 * @param settingDetailList
	 * @throws Exception 
	 */
	protected String dealEnteringSql(String userCode, String createDate,
			List<String> sqls, List<String> baksqls, JSONObject jsonObject,
			String key, EnteringTableSettingInfo settingInfo,
			List<EnteringTableSettingDetail> settingDetailList,String mValue,AdminDatasourceConfig config) throws Exception {
		  System.out.println(key); 
		  System.out.println(mValue);
		  String rowMessage="";//全部行错误信息
		  List<EnteringTableSettingDetail> mapValueForUpdate=new ArrayList<EnteringTableSettingDetail>();//设置唯一的列
		  for(EnteringTableSettingDetail settingDetail:settingDetailList){
			  if("1".equals(settingDetail.getColPk())){
				  mapValueForUpdate.add(settingDetail);
			  }
		  }
		  if(StringUtils.isNotBlank(mValue)){
			  JSONArray mData=JSONArray.parseArray(mValue);//当前作用域数据
			  
			  String importTable = settingInfo.getName();
			  String bakImportTable = settingInfo.getBakTableName();//备份表名
			  if(StringUtil.isEmpty(bakImportTable)){
				 bakImportTable = importTable+"_bak";
			  }
			  for(int j=0;j<mData.size();j++){
				  JSONObject mJsonObject = mData.getJSONObject(j);
				  Iterator<String> mIt = mJsonObject.keySet().iterator();
				  //校验数据
				  String error_msg="";//错误信息
				  String error_col="";//错误列字段
	        	  for(EnteringTableSettingDetail detail:settingDetailList){
	        		  String detailValue=mJsonObject.get(detail.getColName())+"";
	        		  int colMaxLength=Integer.valueOf(detail.getColLength());
	        		  String colType=detail.getColType();
	        		  if(StringUtils.isBlank(detailValue)||"null".equals(detailValue)||mJsonObject.get(detail.getColName())==null){
	        			  continue;
	        		  }
	        		  if(detailValue.length()<=colMaxLength){
         	 			 if("varchar".equals(colType) || "char".equals(colType) ||"tinytext".equals(colType)||
         	 				"text".equals(colType)){
         	 				 
		            	   }
		            	   if("int".equals(colType) || "integer".equals(colType) || "double".equals(colType) || "bigint".equals(colType) ||
		            		"float".equals(colType) || "numeric".equals(colType)||"decimal".equals(colType)){
		            		  // if(NumberUtils.isNumber(detailValue)){
		            		   if(DataSupplementUtils.checkNums(detailValue)){
		                		   //mapValue.put("is_success", "0"); 
		            		   }else{
		            			   error_msg+="只能为数字类型数据,";
		            			   error_col += detail.getColName()+",";
		            		   }
		            	   }
		            	   if("datetime".equals(colType) || "timestamp".equals(colType) ||  
		            		"date".equals(colType) ||"time".equals(colType)||"year".equals(colType)){
		            		   if(DataSupplementUtils.getDateFormatByString(detailValue)!=-1){
		                		   //mapValue.put("is_success", "0");
		            		   }else{
		            			   error_msg+="只能存放时间类型数据,";
		            			   error_col += detail.getColName()+",";
		            		   }
		            	   } else{
	                		   //mapValue.put("is_success", "0");
		            	   }
         	 		  }else {
         	 			 error_msg+="数据长度超过"+colMaxLength+",";
         	 			 error_col += detail.getColName()+",";
         	 		  }
	        	  }
	        	  String[] s=error_msg.split(",");
		           boolean isnotNull=false;
		           for(String str:s){
		        	   if(!"空白格".equals(str)){
		        		   isnotNull=true;
		        	   }
		           }
	        	  if(StringUtils.isNotBlank(error_msg)&&isnotNull){
	        		  rowMessage+="第"+(j+1)+"行数据错误！";
	        		  //当前行有错误数据
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
	        		  //没有错误 拼接SQL
	        		  if(mapValueForUpdate.size()>0){
	        			  //有设置唯一
	        			  String sqlForUpdate="";
	        			  sqlForUpdate+="select id from "+importTable+" where del_flag=0 ";
	        			  sqlForUpdate+=" and ";
	        			  for (EnteringTableSettingDetail in : mapValueForUpdate) {
	        				   sqlForUpdate+=in.getColName()+"='"+mJsonObject.get(in.getColName())+"' and ";
	        			  }
	        			  sqlForUpdate = sqlForUpdate.substring(0,sqlForUpdate.length() - 4);
	        			  Connection conn = ExcelConnection.getConn(config.getDbUrl(),config.getDbName(),config.getDbPassword());
					      PreparedStatement  pstmt = null ;
					      conn.setAutoCommit(false);
				          pstmt = (PreparedStatement)conn.prepareStatement(sqlForUpdate);
		        		  ResultSet rs = pstmt.executeQuery();
		        		  //之前没有
		        		  if(rs.next()==false){
		        			  inserSql(userCode, createDate, sqls, baksqls,
		  							settingDetailList, importTable, bakImportTable,
		  							mJsonObject, mIt); 
		        		  }else{
		        			  updateSql(userCode, sqls, baksqls,
									settingDetailList, mapValueForUpdate,
									importTable, bakImportTable, mJsonObject,
									mIt);
		        		  }
	        		  }else{
	        			  //没有唯一 都是插入操作
	        			  inserSql(userCode, createDate, sqls, baksqls,
	  							settingDetailList, importTable, bakImportTable,
	  							mJsonObject, mIt);
	        		  }
	        	  }
	        	  
			  }
		  }
		  return rowMessage;
	}

	/**
	 * @time   2018年12月12日 下午3:54:15
	 * @author zuoqb
	 * @todo   拼接更新sql
	 * @param  @param userCode
	 * @param  @param sqls
	 * @param  @param baksqls
	 * @param  @param settingDetailList
	 * @param  @param mapValueForUpdate
	 * @param  @param importTable
	 * @param  @param bakImportTable
	 * @param  @param mJsonObject
	 * @param  @param mIt
	 */
	public void updateSql(String userCode, List<String> sqls,
			List<String> baksqls,
			List<EnteringTableSettingDetail> settingDetailList,
			List<EnteringTableSettingDetail> mapValueForUpdate,
			String importTable, String bakImportTable, JSONObject mJsonObject,
			Iterator<String> mIt) {
		  String sql = "update "+importTable+" set ";
		  String sqlbak = "insert into "+bakImportTable+" ";
		  sqlbak+=" (";
		while (mIt.hasNext()) {
			Object o = mIt.next().toString().toLowerCase();
			// 判断字段在补录配置里面
			for (EnteringTableSettingDetail detail : settingDetailList) {
				if (detail.getColName().toLowerCase()
						.equals(o.toString().toLowerCase())) {
					Iterator<String> mItValue = mJsonObject
							.keySet().iterator();
					Object value = "";
					while (mItValue.hasNext()) {
						String mkey = mItValue.next();
						if(mkey.equals(detail.getColName())){
							value = mJsonObject.get(mkey);// 得到每个key多对用value的值
							if (value == null|| StringUtils.isBlank(value.toString())) {
								value = "";
							}
							break;
						}
					}
					sql += o + "='" + value + "',";
					sqlbak += o + ",";
					break;
				}
			}
		}
		  
		   sql+="update_by='"+userCode+"' where ";
		   sqlbak+="id,create_by,update_by";
		   sqlbak+=") values(";
		   Iterator<String> mItValue = mJsonObject.keySet().iterator();
		  while (mItValue.hasNext()) {
			  String mkey=mItValue.next();
			  Object str = mJsonObject.get(mkey);//得到每个key多对用value的值
			  if(StringUtils.isBlank(str.toString())||str==null){
				  str="";
			  }
			  for(EnteringTableSettingDetail detail:settingDetailList){
				  if(detail.getColName().toLowerCase().equals(mkey.toLowerCase())){
					  sqlbak+="'";
					  sqlbak+=str+"',";
					  break;
				  }
			  }
		  }
		   sqlbak+="'"+GenerationSequenceUtil.getUUID()+"','"+userCode+"','"+userCode+"'";
		   sqlbak+=")";
		   
		  for (EnteringTableSettingDetail in : mapValueForUpdate) {
			  sql+=in.getColName()+"='"+mJsonObject.get(in.getColName())+"' and ";
		  }
		   sql = sql.substring(0,sql.length() - 4);
		   System.out.println(sql+"=================================================================");
		   sqls.add(sql);
		   baksqls.add(sqlbak);
	}

	/**
	 * @time   2018年12月12日 下午3:33:29
	 * @author zuoqb
	 * @todo   拼接插入sql
	 * @param  @param userCode
	 * @param  @param createDate
	 * @param  @param sqls
	 * @param  @param baksqls
	 * @param  @param settingDetailList
	 * @param  @param importTable
	 * @param  @param bakImportTable
	 * @param  @param mJsonObject
	 * @param  @param mIt
	 * @return_type   void
	 */
	public void inserSql(String userCode, String createDate, List<String> sqls,
			List<String> baksqls,
			List<EnteringTableSettingDetail> settingDetailList,
			String importTable, String bakImportTable, JSONObject mJsonObject,
			Iterator<String> mIt) {
		  String sql = "insert into "+importTable;
		  String sqlbak = "insert into "+bakImportTable+" ";
		  sql+=" (";
		  sqlbak+=" (";
		  while (mIt.hasNext()) {
			  Object o=mIt.next().toString().toLowerCase();
			  //判断字段在补录配置里面
			  for(EnteringTableSettingDetail detail:settingDetailList){
				  if(detail.getColName().toLowerCase().equals(o.toString().toLowerCase())){
					  sql+=o+",";
					  sqlbak+=o+",";
					  break;
				  }
			  }
		  }
		  sql+="id,is_success,create_by,update_by,create_date";
		  sqlbak+="id,is_success,create_by,update_by,create_date";
		  sql+=") values(";
		  sqlbak+=") values(";
		  Iterator<String> mItValue = mJsonObject.keySet().iterator();
		  while (mItValue.hasNext()) {
			  String mkey=mItValue.next();
			  Object str = mJsonObject.get(mkey);//得到每个key多对用value的值
			  if(StringUtils.isBlank(str.toString())||str==null){
				  str="";
			  }
			  for(EnteringTableSettingDetail detail:settingDetailList){
				  if(detail.getColName().toLowerCase().equals(mkey.toLowerCase())){
					  sql+="'";
					  sqlbak+="'";
					  sql+=str+"',";
					  sqlbak+=str+"',";
					  break;
				  }
			  }
		  }
		  sql+="'"+GenerationSequenceUtil.getUUID()+"','0','"+userCode+"','"+userCode+"','"+createDate+"'";
		  sql+=")";
		  sqlbak+="'"+GenerationSequenceUtil.getUUID()+"','0','"+userCode+"','"+userCode+"','"+createDate+"'";
		  sqlbak+=")";
		  //System.out.println(sql);
		  //System.out.println(sqlbak);
		  sqls.add(sql);
		  baksqls.add(sqlbak);
	}
}
