package com.haier.datamart.controller;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import jodd.util.URLDecoder;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.util.StringUtil;
import com.haier.datamart.annotation.Log;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.entity.AdminDatasourceConfig;
import com.haier.datamart.entity.EnteringTableSettingDetail;
import com.haier.datamart.entity.EnteringTableSettingDetailData;
import com.haier.datamart.entity.EnteringTableSettingInfo;
import com.haier.datamart.entity.User;
import com.haier.datamart.redis.RedisUtils;
import com.haier.datamart.service.IAdminDatasourceConfigService;
import com.haier.datamart.service.IEnteringTableSettingHeaderService;
import com.haier.datamart.service.IEnteringTableSettingInfoService;
import com.haier.datamart.service.IScanSubjectAreaIndexService;
import com.haier.datamart.utils.DataSupplementUtils;
import com.haier.datamart.utils.DataUpdateOne;
import com.haier.datamart.utils.ExcelConnection;
import com.haier.datamart.utils.ExcleUtils;
import com.haier.datamart.utils.GenerationSequenceUtil;

/**
 * 导入模板
 * 
 * @author doushuihai
 * @date 2018年7月5日下午2:01:53
 * @TODO
 */
@RestController
public class DataImportController extends BaseController {
	@Autowired
	private IEnteringTableSettingInfoService infoService;
	@Autowired
	private IScanSubjectAreaIndexService areaIndexService;
	@Autowired
	private IAdminDatasourceConfigService dataSourceConfigservice;
	@Autowired
	public IEnteringTableSettingHeaderService iEnteringTableSettingHeaderService;

	/**
	 * 在导入模板前先检验是否在允许的时间段内
	 * 
	 * @author doushuihai
	 * @date 2018年7月10日下午2:02:23
	 * @TODO
	 */
	@GetMapping(value = "/data/checkentrance", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/data/checkentrance")
	public Object importCheckEntrance(HttpServletRequest request) {
		String settingId = request.getParameter("settingId");// 666
																// 改为通过settingId获取参数
		EnteringTableSettingInfo settingInfo = infoService.getById(settingId);
		if (settingInfo == null) {
			return new PublicResult<>(PublicResultConstant.FAILED, "无此指标信息");
		}
		Date validBeginTime = settingInfo.getValidBeginTime();
		Date validEndTime = settingInfo.getValidEndTime();
		Date currentDate = new Date();
		Object message = "";
		if (currentDate.after(validBeginTime)
				&& currentDate.before(validEndTime)) {
			return new PublicResult<>(PublicResultConstant.SUCCESS, message);
		} else {
			return new PublicResult<>(PublicResultConstant.FAILED,
					"请在规定的时间内进行操作");
		}

	}

	/**
	 * 
	 * @time   2018年11月19日 下午3:27:39
	 * @author zuoqb
	 * @todo   补录数据导入-通过Excel文件
	 * @param  params:默认导入的值  格式 name::123;;vmonth::201812
	 * @return_type   Object
	 */
	@RequestMapping(value = "/data/dataimport", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@Log(description = "API接口:/data/dataimport")
	public Object toDataImport(HttpServletRequest request,
			@RequestParam(value = "file", required = true) MultipartFile file,
			@RequestParam(value="settingId",required = true) String settingId,
			@RequestParam(value="params",required = false) String params) {
		User user = getLoginUser(request);
		/*if (user.getId() == null || "".equals(user.getId())) {
			return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,
					"用户未登录!");
		}*/
		if(StringUtils.isNotBlank(params))
		params=URLDecoder.decode(params, "UTF-8");
		return importEnteringByExcel(file, settingId, params, user);
	}
	
	

	/**
	 * 模板导入对外接口-
	 * 
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/data/dataimportForeign", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@Log(description = "API接口:/data/dataimportForeign")
	public Object dataimportForeign(HttpServletRequest request,
			@RequestParam(value = "file", required = true) MultipartFile file,
			@RequestParam(value="settingId",required = true) String settingId,
			@RequestParam(value="userId",required = true) String userId,
			@RequestParam(value="params",required = false) String params) {
		if (StringUtils.isEmpty(userId)) {
			return new PublicResult<>(PublicResultConstant.UNAUTHORIZED, "");
		}
		if(StringUtils.isNotBlank(params))
		params=URLDecoder.decode(params, "UTF-8");
		User user = new User();
		user.setId(userId);
		
		return importEnteringByExcel(file, settingId, params, user);
	}

	/**
	 * @time   2018年11月19日 下午3:32:30
	 * @author zuoqb
	 * @todo   补录处理Excel数据
	 * @param  @param file
	 * @param  @param settingId
	 * @param  @param params
	 * @param  @param user
	 */
	public Object importEnteringByExcel(MultipartFile file, String settingId,
			String params, User user) {
		String fileName = file.getOriginalFilename();// 获取文件名
		// 验证文件名是否合格
		if (!ExcleUtils.validateExcel(fileName)) {
			return new PublicResult<>(PublicResultConstant.FAILED,
					"文件必须是excel格式！");
		}
		if(StringUtils.isNotBlank(params))
		params=URLDecoder.decode(params, "UTF-8");
		// 进一步判断文件内容是否为空（即判断其大小是否为0或其名称是否为null）
		long size = file.getSize();
		if (StringUtils.isEmpty(fileName) || size == 0) {
			return new PublicResult<>(PublicResultConstant.FAILED, "文件不能为空！");
		}
		EnteringTableSettingInfo settingInfo = infoService.getById(settingId);
		String industryId = "";

		if (settingInfo == null) {
			return new PublicResult<>(PublicResultConstant.FAILED, "无此指标信息");
		}
		String importTable = settingInfo.getName();
		String bakImportTable = settingInfo.getBakTableName();// 备份表名
		if (StringUtil.isEmpty(bakImportTable)) {
			bakImportTable = importTable + "_bak";
		}
		List<EnteringTableSettingDetail> list = infoService
				.getBySettingId(settingInfo.getId());
		if (CollectionUtils.isEmpty(list)) {
			return new PublicResult<>(PublicResultConstant.FAILED,
					"根据此指标id没有找到表字段详细信息");
		}
		// 获取数据信息
		AdminDatasourceConfig config = dataSourceConfigservice.get(settingInfo
				.getDatasourceConfigId());
		if (config == null) {
			return new PublicResult<>(PublicResultConstant.FAILED, "数据源不存在!");
		}
		// 获取本分数据源
		AdminDatasourceConfig bakConfig = dataSourceConfigservice
				.get(settingInfo.getBakDatasourceConfigId());
		Map<String, String> defaultValues=null;
		try {
			defaultValues = checkDefaultValues(params, list);
		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED, e.getMessage());
		}
		int maxHeaderNum = iEnteringTableSettingHeaderService
				.getHeadersRowNums(settingId);
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("allSettingDetail", list);
		param.put("settingInfo",settingInfo);
		param.put("industryId",industryId);
		param.put("user",user);
		param.put("config",config);
		param.put("configBak",bakConfig);
		param.put("maxHeaderRows",maxHeaderNum);
		param.put("defaultValues",defaultValues);
		param.put("optRedis",false);//不写入Redis，直接将数据入库
		Map<String,Object> backsMap = DataSupplementUtils.batchImport(file,param);
		String message=null;
		if(backsMap!=null){
			message=backsMap.get("errorMsg")+"";
			return enteringStatus(message);
		}else{
			return new PublicResult<>(PublicResultConstant.FAILED, "系统异常，请检查模板！");
		}
	}
	
	

	/**
	 * @time   2018年11月19日 下午4:32:39
	 * @author zuoqb
	 * @todo   补录结果处理
	 * @param  @param errorMsg
	 * @param  @return
	 * @return_type   Object
	 */
	public Object enteringStatus(String errorMsg) {
		if (StringUtils.isNotBlank(errorMsg) &&( errorMsg.indexOf("由于补录配置不允许出现错误继续导入") != -1|| errorMsg.indexOf("请下载最新版本模板！") != -1)) {
			return new PublicResult<>(PublicResultConstant.FAILED, errorMsg);
		} else {
			return new PublicResult<>(PublicResultConstant.SUCCESS, errorMsg);
		}
	}
	
	/**
	 * 
	 * @time   2018年11月19日 上午9:58:50
	 * @author zuoqb
	 * @todo   补录数据导入-写入redis方式，不直接入库操作
	 * @param  @param request
	 * @param  @param file
	 * @param  @param settingId
	 * @param  @param params:默认导入的值  格式 name::123;;vmonth:201812
	 * @param  @return
	 * @return_type   Object
	 */
	@RequestMapping(value = "/data/dataimportRedis", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@Log(description = "API接口:/data/dataimportRedis")
	public Object dataimportRedis(HttpServletRequest request,
			@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam(value = "params", required = false) String params,
			@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "settingId", required = true) String settingId) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		String redisKey=settingId+"_"+userId+"_"+sdf.format(new Date());
		Map<String,Object> backsMap=new HashMap<String, Object>();//最终返回的结果数据
		if (StringUtils.isBlank(userId)) {
			return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"用户信息不能为空!");
		}
		String fileName = file.getOriginalFilename();// 获取文件名
		// 验证文件名是否合格
		if (!ExcleUtils.validateExcel(fileName)) {
			return new PublicResult<>(PublicResultConstant.FAILED,
					"文件必须是excel格式！");
		}
		// 进一步判断文件内容是否为空（即判断其大小是否为0或其名称是否为null）
		long size = file.getSize();
		
		if (StringUtils.isEmpty(fileName) || size == 0) {
			return new PublicResult<>(PublicResultConstant.FAILED, "文件不能为空！");
		}
		EnteringTableSettingInfo settingInfo = infoService.getById(settingId);
		if (settingInfo == null) {
			return new PublicResult<>(PublicResultConstant.FAILED, "无此指标信息");
		}
		List<EnteringTableSettingDetail> allDetails = infoService.getBySettingId(settingInfo.getId());
		List<EnteringTableSettingDetail> settingDetails=new ArrayList<EnteringTableSettingDetail>();//导出到模板的配置
		List<EnteringTableSettingDetail> notExportSettingDetails=new ArrayList<EnteringTableSettingDetail>();//没有导出到模板的配置
		  for(EnteringTableSettingDetail d:allDetails){
			  if("1".equals(d.getIsExport())){
				  notExportSettingDetails.add(d);
			  }else{
				  settingDetails.add(d);
			  }
		  }
	
		if (CollectionUtils.isEmpty(allDetails)) {
			return new PublicResult<>(PublicResultConstant.FAILED,
					"根据此指标id没有找到表字段详细信息");
		}
		// 获取数据信息
		AdminDatasourceConfig config = dataSourceConfigservice.get(settingInfo.getDatasourceConfigId());
		if (config == null) {
			return new PublicResult<>(PublicResultConstant.FAILED, "数据源不存在!");
		}
		// 获取本分数据源
		AdminDatasourceConfig bakConfig = dataSourceConfigservice.get(settingInfo.getBakDatasourceConfigId());
		int maxHeaderNum = iEnteringTableSettingHeaderService
				.getHeadersRowNums(settingId);
		try {
			InputStream is = null;
			if (!ExcleUtils.validateExcel(fileName)) {
				return "文件格式不正确";
			}
			// 根据新建的文件实例化输入流
			is = file.getInputStream();
			// 根据版本选择创建Workbook的方式
			Workbook wb =  WorkbookFactory.create(is);
			if (maxHeaderNum == 0){
				maxHeaderNum = 1;
			}
			// 错误信息接收器
			// 得到第一个shell
			Sheet sheet = wb.getSheetAt(0);

			// 得到Excel的行数
			int totalRows = sheet.getPhysicalNumberOfRows();
			// 总列数
			int totalCells = 0;
			// 得到Excel的列数(前提是有行数)，从第二行算起
			if (totalRows >= 2 && sheet.getRow(maxHeaderNum) != null) {
				totalCells = settingDetails.size();
			}
			//默认列值
			Map<String, String> defaultValues=new HashMap<String, String>();
			try {
				defaultValues = checkDefaultValues(params, allDetails);
			} catch (Exception e) {
				e.printStackTrace();
				return new PublicResult<>(PublicResultConstant.FAILED, e.getMessage());
			}
			
			HashMap<Integer, EnteringTableSettingDetail> excelHeaders = new HashMap<Integer, EnteringTableSettingDetail>();
			List<List<Map<String,Object>>> rowsDatas=new ArrayList<List<Map<String,Object>>>();//存放所有行数据
			for (int r = maxHeaderNum - 1; r < totalRows; r++) {
				// 解析表头最后一行，获取配置信息
				Row row = sheet.getRow(r);
				if (r == maxHeaderNum - 1) {
					for (int i = 0; i < totalCells; i++) {
						Cell cellTop = row.getCell(i);
						cellTop.setCellType(Cell.CELL_TYPE_STRING);
						String colname = cellTop.getStringCellValue();

						for (EnteringTableSettingDetail detail : settingDetails) {
							if (colname.equals(detail.getExcelColName())) {
								excelHeaders.put(i,detail);
							}
						}
					}
					continue;
				}
				
				//解析数据
				 if(r>maxHeaderNum-1){
					 List<Map<String,Object>> rowData=new ArrayList<Map<String,Object>>();
					 for(int c = 0; c <totalCells; c++){
						 Map<String,Object> rowMap=new HashMap<String, Object>();
						 Cell cell = row.getCell(c);
						 if(cell==null){
							 cell=row.createCell(c);
						 }
						 EnteringTableSettingDetail settingDetail=excelHeaders.get(c);
						 if(settingDetail!=null){
							 String colType=settingDetail.getColType();
							 if(StringUtils.isBlank(colType)){
								 colType="varchar";
							 }
							 colType=colType.toLowerCase();
							 String colName=settingDetail.getColName();
							 String excelName=settingDetail.getExcelColName();
							 String excelGs = settingDetail.getExcelGs();//获取当前列的excel公式
							 String  detailValue = "";
							 detailValue = DataSupplementUtils.getExcelName(row, c, cell, colType,
										excelGs, detailValue);
							 rowMap.put("colName", colName);
							 rowMap.put("excelName", excelName);
							 rowMap.put("detailValue", detailValue);
							 rowMap.put("orderNo", c+1);
							 rowMap.put("isDefault", false);
							 rowData.add(rowMap);
						 }
						
					 }
					 //放入没有导出的列的默认值
					 int x=0;
					 for (String in : defaultValues.keySet()) {
						 Map<String,Object> defaultRowMap=new HashMap<String, Object>();
						 for(EnteringTableSettingDetail d:notExportSettingDetails){
							 if(d.getColName().equals(in)){
								 defaultRowMap.put("colName", d.getColName());
								 defaultRowMap.put("excelName", d.getExcelColName());
								 defaultRowMap.put("detailValue", defaultValues.get(in));
								 defaultRowMap.put("orderNo", totalCells+x+1);
								 defaultRowMap.put("isDefault", true);
								 rowData.add(defaultRowMap);
								 x++;
								 break;
							 }
						 }
					 }
					 rowsDatas.add(rowData);
				 }
			}
			backsMap.put("allSettingDetail", allDetails);
			backsMap.put("excelHeaders", excelHeaders);
			backsMap.put("rowsDatas", rowsDatas);
			backsMap.put("redisKey", redisKey);
			backsMap.put("defaultValues", defaultValues);
			backsMap.put("totalRows", totalRows-maxHeaderNum);
			backsMap.put("maxHeaderRows", maxHeaderNum);
			backsMap.put("fileName", fileName);
			User user=new User();
			user.setId(userId);
			backsMap.put("user", user);
			backsMap.put("params", params);
			backsMap.put("fileSize", size);
			backsMap.put("notExportSettingDetails", notExportSettingDetails);
			backsMap.put("settingInfo",settingInfo);
			backsMap.put("config",config);
			backsMap.put("configBak",bakConfig);
			backsMap.put("optRedis",true);//写入Redis，不直接将数据入库
			Map<String,Object> map= DataSupplementUtils.batchImport(file,backsMap);
			if(map!=null){
				backsMap.put("sqls",map.get("sqls"));
				backsMap.put("baksqls",map.get("baksqls"));
				backsMap.put("totalRows",map.get("totalRows"));
				backsMap.put("errorMsg",map.get("errorMsg"));
				backsMap.put("maxHeaderRows",map.get("maxHeaderRows"));
			}
			backsMap.remove("wb");
			backsMap.remove("user");
			boolean b=RedisUtils.set(redisKey, backsMap);
			if(!b){
				return new PublicResult<>(PublicResultConstant.FAILED, "放入Redis失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED, e.getMessage());
		}
		return new PublicResult<>(PublicResultConstant.SUCCESS, backsMap);
	}
	
	/**
	 * 
	 * @time   2018年11月19日 下午4:31:14
	 * @author zuoqb
	 * @todo   将Redis里面之前补录数据入库
	 * @param  @param request
	 * @param  @param redisKey
	 * @param  @param userId
	 * @param  @param settingId
	 * @param  @return
	 * @return_type   Object
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/data/noticeRedis2Db", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@Log(description = "API接口:/data/noticeRedis2Db")
	public Object noticeRedis2Db(HttpServletRequest request,
			@RequestParam(value = "redisKey", required = true) String redisKeys,
			@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "settingId", required = true) String settingId) {
		
		try {
			List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
			boolean isSuccess=true;
			for(String redisKey:redisKeys.split(",")){
				Map<String,Object> map=new HashMap<String, Object>();
				Map<String,Object> backsMap=(Map<String, Object>) RedisUtils.get(redisKey);//最终返回的结果数据
				map.put("redisKey", redisKey);
				if(backsMap==null){
					map.put("isSuccess", false);
					map.put("errorMsg", redisKey+"缓存数据不存在!");
					isSuccess=false;
				}
				try {
					String errorMsg=(String) backsMap.get("errorMsg");
					List<String> sqls= (List<String>) backsMap.get("sqls");
					List<String> baksqls= (List<String>) backsMap.get("baksqls");
					backsMap.put("optRedis", false);
					User user=new User();
					user.setId(userId);
					backsMap.put("user", user);
					errorMsg=DataSupplementUtils.insetData2Db(backsMap,"",sqls,baksqls);
					if (StringUtils.isNotBlank(errorMsg) &&( errorMsg.indexOf("由于补录配置不允许出现错误继续导入") != -1|| errorMsg.indexOf("请下载最新版本模板！") != -1)) {
						map.put("isSuccess", false);
						map.put("errorMsg", errorMsg);
						isSuccess=false;
						break;
					} else {
						//删除Redis数据
						RedisUtils.remove(redisKey);
						map.put("isSuccess", true);
						isSuccess=true;
						map.put("errorMsg", errorMsg);
					}
					result.add(map);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
			if(isSuccess){
				return new PublicResult<>(PublicResultConstant.SUCCESS,result);
			}else{
				return new PublicResult<>(PublicResultConstant.ERROR,null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED, e.getMessage());
		}
	}
	
	/**
	 * 
	 * @time   2018年11月14日 下午3:08:47
	 * @author zuoqb
	 * @todo   处理补录导入数据的某些默认值数据
	 * @param  @param params
	 * @param  @param list
	 * @param  @return
	 * @return_type   Map<String,String>
	 */
	protected Map<String, String> defaultValues(String params,
			List<EnteringTableSettingDetail> list) {
		Map<String,String> defaultValues=new HashMap<String, String>();
		if(StringUtils.isNotBlank(params)){
			//name::123;;vmonth:201812
			String[] paramsArray=params.split(";;");
			for(String param:paramsArray){
				String[] s=param.split("::");
				if(s!=null&&s.length>1){
					for(EnteringTableSettingDetail detail:list){
						//必须在配置里面
						if(detail.getColName().equals(s[0])){
							defaultValues.put(s[0], s[1]);
							break;
						}
					}
				}
			}
		}
		return defaultValues;
	}

	protected Map<String, String> checkDefaultValues(String params,
			List<EnteringTableSettingDetail> list) throws Exception {
		Map<String,String> defaultValues=new HashMap<String, String>();
		if(StringUtils.isNotBlank(params)){
			//name::123;;vmonth:201812
			if(StringUtils.isNotBlank(params))
			params=URLDecoder.decode(params, "UTF-8");
			String[] paramsArray=params.split(";;");
			for(String param:paramsArray){
				String[] s=param.split("::");
				if(s!=null&&s.length>1){
					for(EnteringTableSettingDetail detail:list){
						//必须在配置里面
						if(detail.getColName().equals(s[0].trim())){
							String colType=detail.getColType();
							String colName=detail.getColName();
							String detailValue=s[1];
							 if("int".equals(colType) || "integer".equals(colType) || "double".equals(colType) || "bigint".equals(colType) ||
			            		"float".equals(colType) || "numeric".equals(colType)||"decimal".equals(colType)){
			            		  // if(NumberUtils.isNumber(detailValue)){
			            		   if(DataSupplementUtils.checkNums(detailValue)){
			                		   //mapValue.put("is_success", "0"); 
			            		   }else{
			            			   throw new Exception(colName+"只能为数字类型"+colType+"数据!");
			            		   }
			            	   }
			            	   if("datetime".equals(colType) || "timestamp".equals(colType) ||  
			            		"date".equals(colType) ||"time".equals(colType)){
			            		   if(DataSupplementUtils.getDateFormatByString(detailValue)!=-1){
			                		   //mapValue.put("is_success", "0");
			            		   }else{
			            			   throw new Exception(colName+"只能存放时间类型"+colType+"数据!");
			            		   }
			            	   }
							defaultValues.put(s[0], s[1]);
							break;
						  }
						}
					}
				}
			}
		return defaultValues;
	}
	/**
	 * 修改或者新增单条数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/data/alterOneEntry", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/data/alterOneEntry")
	public PublicResult<String> alterOneEntry(HttpServletRequest request,
			@RequestBody Map<String, String> map) {
		String result = "参数错误!";
		try {
			User user = getLoginUser(request);// 获取当前登录用户信息
			if (user.getId() == null) {
				return new PublicResult<String>(
						PublicResultConstant.INVALID_USER, "用户未登录!");
			}
			String settingId = map.get("settingId");/* 666 */

			EnteringTableSettingInfo settingInfo = infoService
					.getById(settingId);
			// System.out.println("settingInfo.getId():" + settingInfo.getId());
			List<EnteringTableSettingDetail> list = infoService
					.getBySettingId(settingInfo.getId());// 根据settingInfo获取对应的表字段详细信息
			/*
			 * if((map.size()-2)<list.size()){//如果传入参数少于应传递数量 return new
			 * PublicResult<String>(PublicResultConstant.ERROR, "传入参数少于应传递数量!");
			 * }
			 */

			/* DataFormatter77 */

			String tableName = settingInfo.getName();// 获取目标表名
			// 获取数据源信息
			AdminDatasourceConfig config = dataSourceConfigservice
					.get(settingInfo.getDatasourceConfigId());
			// 备份数据源
			AdminDatasourceConfig configBak = new AdminDatasourceConfig();
			if (StringUtil.isNotEmpty(settingInfo.getBakDatasourceConfigId())) {
				configBak = dataSourceConfigservice.get(settingInfo
						.getBakDatasourceConfigId());
			} else {
				configBak = dataSourceConfigservice
						.get(ExcelConnection.defaultDataSourceId);
			}
			if (configBak == null) {
				return new PublicResult<>(PublicResultConstant.FAILED,
						"此配置备份数据源信息连接失败,请联系管理员!");
			}
			// 备份表名
			String bakTableName = settingInfo.getBakTableName(); // 备份表名
			if (StringUtil.isEmpty(bakTableName)) {
				bakTableName = settingInfo.getName() + "_bak";
				settingInfo.setBakTableName(bakTableName);
			}
			String idOfEntry = map.get("idOfEntry");// 当前这条数据的id
			if ("ADD".equals(idOfEntry)) {// 如果匹配addOne则为新增功能
				idOfEntry = "ADD" + GenerationSequenceUtil.getUUID();
			}
			Map<String, String> realMap = new LinkedHashMap<String, String>();
			for (EnteringTableSettingDetail etsd : list) {
				String key = etsd.getColName();
				System.out.println("key::::" + key + ",value::" + map.get(key));
				realMap.put(key, map.get(key));
			}
			for (Entry<String, String> entry : realMap.entrySet()) {
				System.out.println("entry:" + entry);
			}
			/*
			 * Map<Integer, String> orderIdMaping = new HashMap<Integer,
			 * String>();
			 */
			Map<String, Integer> idOrderMaping = new HashMap<String, Integer>();
			Map<String, String> colNumTypeMaping = new HashMap<String, String>();
			Map<String, String> colNumNameMaping = new HashMap<String, String>();
			Map<String, String> colNumIsPkMaping = new HashMap<String, String>();
			Map<String, String> colNumMaxLengthMaping = new HashMap<String, String>();
			Map<String, String> colNumExcelGsMaping = new HashMap<String, String>();
			for (int i = 0; i < list.size(); i++) {
				EnteringTableSettingDetail etsd = list.get(i);
				/* orderIdMaping.put(etsd.getOrderNo(), etsd.getId()); */
				/**
				 * 之前是id号映射顺序,现在orderno映射orderno
				 */
				// idOrderMaping.put(etsd.getId(), etsd.getOrderNo());
				idOrderMaping.put(etsd.getOrderNo() + "", etsd.getOrderNo());
				colNumTypeMaping.put(i + "", etsd.getColType());
				colNumNameMaping.put(i + "", etsd.getColName());
				colNumIsPkMaping.put(i + "", etsd.getColPk());
				colNumMaxLengthMaping.put(i + "", etsd.getColLength());
				colNumExcelGsMaping.put(i + "", etsd.getExcelGs());
			}

			result = DataUpdateOne
					.checkOneAndUpdate(user, tableName, bakTableName,
							idOfEntry, realMap, /* orderIdMaping, */
							idOrderMaping, colNumTypeMaping, colNumNameMaping,
							colNumIsPkMaping, colNumMaxLengthMaping,
							colNumExcelGsMaping, config.getDbUrl(),
							config.getDbName(), config.getDbPassword(),
							configBak.getDbUrl(), configBak.getDbName(),
							configBak.getDbPassword(), settingId);
			return new PublicResult<String>(PublicResultConstant.SUCCESS,
					result);
		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<String>(PublicResultConstant.FAILED, result);
		}

	}

	@RequestMapping(value = "/data/deleteOneEntry", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.GET)
	@Log(description = "API接口:/data/deleteOneEntry")
	public PublicResult<String> deleteOne(HttpServletRequest request) {

		try {
			User user = getLoginUser(request);// 获取当前登录用户信息
			String settingId = request.getParameter("settingId");/* 666 */
			String idOfEntry = request.getParameter("idOfEntry");
			if (user.getId() == null) {
				return new PublicResult<String>(PublicResultConstant.FAILED,
						"当前用户未登录!");
			} else {
				EnteringTableSettingInfo settingInfo = infoService
						.getById(settingId);// 根据指标id获取对应的settingInfo
				String tableName = settingInfo.getName();// 获取目标表名
				// 获取数据源id
				AdminDatasourceConfig config = dataSourceConfigservice
						.get(settingInfo.getDatasourceConfigId());
				try {
					DataUpdateOne.deleteOne(tableName, idOfEntry, user,
							config.getDbUrl(), config.getDbName(),
							config.getDbPassword());
					return new PublicResult<String>(
							PublicResultConstant.SUCCESS, "删除成功!");
				} catch (SQLException e) {
					e.printStackTrace();
					return new PublicResult<String>(
							PublicResultConstant.FAILED, "删除失败,数据库错误!");
				}
			}
		} catch (Exception e) {
			return new PublicResult<String>(PublicResultConstant.FAILED,
					"删除失败,系统错误!");
		}

	}

	/**
	 * 模板导入对外校验接口
	 * 
	 * @param request
	 * @param file
	 * @return
	 */
	@SuppressWarnings("resource")
	@RequestMapping(value = "/data/dataimportForeignCheck", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@Log(description = "API接口:/data/dataimportForeignCheck")
	public Object dataimportForeignCheck(HttpServletRequest request,
			@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam(value = "params", required = true) String params,
			@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "settingId", required = true) String settingId) {
		if (StringUtils.isEmpty(userId)) {
			return new PublicResult<>(PublicResultConstant.UNAUTHORIZED, "");
		}
		if(StringUtils.isNotBlank(params))
		params=URLDecoder.decode(params, "UTF-8");
		String fileName = file.getOriginalFilename();// 获取文件名
		// 验证文件名是否合格
		if (!ExcleUtils.validateExcel(fileName)) {
			return new PublicResult<>(PublicResultConstant.FAILED,
					"文件必须是excel格式！");
		}
		// 进一步判断文件内容是否为空（即判断其大小是否为0或其名称是否为null）
		long size = file.getSize();
		if (StringUtils.isEmpty(fileName) || size == 0) {
			return new PublicResult<>(PublicResultConstant.FAILED, "文件不能为空！");
		}
		EnteringTableSettingInfo settingInfo = infoService.getById(settingId);
		if (settingInfo == null) {
			return new PublicResult<>(PublicResultConstant.FAILED, "无此指标信息");
		}
		List<EnteringTableSettingDetail> list = infoService
				.getBySettingId(settingInfo.getId());
		if (CollectionUtils.isEmpty(list)) {
			return new PublicResult<>(PublicResultConstant.FAILED,
					"根据此指标id没有找到表字段详细信息");
		}
		// 获取数据信息
		AdminDatasourceConfig config = dataSourceConfigservice.get(settingInfo
				.getDatasourceConfigId());
		if (config == null) {
			return new PublicResult<>(PublicResultConstant.FAILED, "数据源不存在!");
		}
		int maxHeaderNum = iEnteringTableSettingHeaderService
				.getHeadersRowNums(settingId);
		String message="";
		try {
			InputStream is = null;
			if (!fileName.matches("^.+\\.(?i)(xls)$")
					&& !fileName.matches("^.+\\.(?i)(xlsx)$")) {
				return "文件格式不正确";
			}
			// 根据新建的文件实例化输入流
			is = file.getInputStream();
			// 根据版本选择创建Workbook的方式
			Workbook wb = null;
			// 根据文件名判断文件是2003版本还是2007版本
			if (ExcleUtils.isExcel2007(fileName)) {
				wb = new XSSFWorkbook(is);
			} else {
				wb = new HSSFWorkbook(is);
			}
			if (maxHeaderNum == 0)
				maxHeaderNum = 1;
			// 错误信息接收器
			// 得到第一个shell
			Sheet sheet = wb.getSheetAt(0);

			// 得到Excel的行数
			int totalRows = sheet.getPhysicalNumberOfRows();
			// 总列数
			int totalCells = 0;
			// 得到Excel的列数(前提是有行数)，从第二行算起
			if (totalRows >= 2 && sheet.getRow(maxHeaderNum) != null) {
				totalCells = list.size();
			}
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			for (int r = maxHeaderNum - 1; r < totalRows; r++) {
				// 解析表头最后一行，获取配置信息
				Row row = sheet.getRow(r);
				if (r == maxHeaderNum - 1) {
					for (int i = 0; i < totalCells; i++) {
						Cell cellTop = row.getCell(i);
						cellTop.setCellType(Cell.CELL_TYPE_STRING);
						String colname = cellTop.getStringCellValue();

						for (EnteringTableSettingDetail detail : list) {
							if (colname.equals(detail.getExcelColName())) {
								map.put(detail.getExcelColName(),i);
							}
						}
					}
					continue;
				}
			}
			//校验数据
			JSONArray checks=JSONArray.parseArray(params);
			for(int x=0;x<checks.size();x++){
				JSONObject o=checks.getJSONObject(x);
				String key=o.getString("name");
				String value=o.getString("value");
				if(StringUtils.isNotBlank(key)&&StringUtils.isNotBlank(value)){
					int index=map.get(key);
					for (int r = maxHeaderNum; r < totalRows; r++) {
						// 解析表头最后一行，获取配置信息
						Row row = sheet.getRow(r);
						Cell cell = row.getCell(index);
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						String cellValue=cell.getStringCellValue();
						if(value.indexOf(cellValue)==-1){
							message+="第"+r+"行列"+key+"值错误,";
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED, e.getMessage());
		}
		if (StringUtils.isBlank(message) ) {
			return new PublicResult<>(PublicResultConstant.SUCCESS, message);
		} else {
			return new PublicResult<>(PublicResultConstant.FAILED, message);
		}
	}
	
	/**
	 * 
	 * @time   2018年11月19日 下午3:27:39
	 * @author zuoqb
	 * @todo   补录数据导入-通过Excel文件 -包含多sheet页
	 * @param  params:默认导入的值  格式 name::123;;vmonth:201812
	 * @return_type   Object
	 */
	@RequestMapping(value = "/data/dataimportSheets", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@Log(description = "API接口:/data/dataimportSheets")
	public Object dataimportSheets(HttpServletRequest request,
			@RequestParam(value = "file", required = true) MultipartFile file,
			@RequestParam(value="settingId",required = true) String settingIds,
			@RequestParam(value="userId",required = true) String userId,
			@RequestParam(value="params",required = false) String params) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		if (StringUtils.isEmpty(userId)) {
			return new PublicResult<>(PublicResultConstant.UNAUTHORIZED, "");
		}
		params=URLDecoder.decode(params, "UTF-8"); 
		User user = new User();
		user.setId(userId);
		String fileName = file.getOriginalFilename();// 获取文件名
		// 验证文件名是否合格
		if (!ExcleUtils.validateExcel(fileName)) {
			return new PublicResult<>(PublicResultConstant.FAILED,
					"文件必须是excel格式！");
		}
		// 进一步判断文件内容是否为空（即判断其大小是否为0或其名称是否为null）
		long size = file.getSize();
		if (StringUtils.isEmpty(fileName) || size == 0) {
			return new PublicResult<>(PublicResultConstant.FAILED, "文件不能为空！");
		}
		
		List<Map<String,Object>> allData=new ArrayList<Map<String,Object>>();
		for(String settingId:settingIds.split(",")){
			EnteringTableSettingInfo settingInfo = infoService.getById(settingId);
			
			if (settingInfo == null) {
				return new PublicResult<>(PublicResultConstant.FAILED, "无此指标信息");
			}
			String redisKey=settingId+"_"+userId+"_"+sdf.format(new Date());
			Map<String,Object> backsMap=new HashMap<String, Object>();//最终返回的结果数据
			
			String importTable = settingInfo.getName();
			String bakImportTable = settingInfo.getBakTableName();// 备份表名
			if (StringUtil.isEmpty(bakImportTable)) {
				bakImportTable = importTable + "_bak";
			}
			List<EnteringTableSettingDetail> list = infoService
					.getBySettingIdIncludeData(settingInfo.getId());
			if (CollectionUtils.isEmpty(list)) {
				return new PublicResult<>(PublicResultConstant.FAILED,
						"根据此指标id没有找到表字段详细信息");
			}
			// 获取数据信息
			AdminDatasourceConfig config = dataSourceConfigservice.get(settingInfo
					.getDatasourceConfigId());
			if (config == null) {
				return new PublicResult<>(PublicResultConstant.FAILED, "数据源不存在!");
			}
			// 获取本分数据源
			AdminDatasourceConfig bakConfig = dataSourceConfigservice
					.get(settingInfo.getBakDatasourceConfigId());
			Map<String, String> defaultValues=null;
			try {
				defaultValues = checkDefaultValues(params, list);
			} catch (Exception e) {
				e.printStackTrace();
				return new PublicResult<>(PublicResultConstant.FAILED, e.getMessage());
			}
			int maxHeaderNum = iEnteringTableSettingHeaderService
					.getHeadersRowNums(settingId);
			
			List<EnteringTableSettingDetail> allDetails = infoService.getBySettingId(settingInfo.getId());
			List<EnteringTableSettingDetail> settingDetails=new ArrayList<EnteringTableSettingDetail>();//导出到模板的配置
			List<EnteringTableSettingDetail> notExportSettingDetails=new ArrayList<EnteringTableSettingDetail>();//没有导出到模板的配置
			for(EnteringTableSettingDetail d:allDetails){
				if("1".equals(d.getIsExport())){
					notExportSettingDetails.add(d);
				}else{
					settingDetails.add(d);
				}
			}
			List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
			//根据版本选择创建Workbook的方式
			try {
				Workbook wb =WorkbookFactory.create(file.getInputStream());
				int allSheets=wb.getNumberOfSheets();//sheet页总页数
				//从不同sheet页汇总数据
				HashMap<Integer, EnteringTableSettingDetail> excelHeaders = new HashMap<Integer, EnteringTableSettingDetail>();
				int i=0;
				int j=0;
				int totalRows=0;
				int columnNums=0;
				HashMap<String, Object> mapValueForUpdate= new HashMap<String, Object>();//储存excel列名与列指的对应，以更新数据库
				for(EnteringTableSettingDetail settingDetail:list){
					excelHeaders.put(i,settingDetail);
					i++;
					if(!CollectionUtils.isEmpty(settingDetail.getDetailData())){
						Map<String,Object> tranlate=new HashMap<String, Object>();
						List<String> columnData=new ArrayList<String>();//存放列全部数据
						for(EnteringTableSettingDetailData s:settingDetail.getDetailData()){
							int sheetIndex=s.getSheetIndex();
							String dataRange=s.getDataRange();
							//没有设定取数位置或者设置的sheet页码大于总页码  退出处理
							if(StringUtils.isBlank(dataRange)||allSheets<sheetIndex){
								System.err.println("没有设定取数位置或者设置的sheet页码大于总页码  退出处理!");
								continue;
							}else{ 
								List<String> l=ExcleUtils.getExcelValuesByRange(wb, sheetIndex, dataRange);
								columnData.addAll(l);
							}
						}
						columnNums++;
						if(totalRows<columnData.size()){
							totalRows=columnData.size();
						}
						tranlate.put("data", columnData);
						tranlate.put("settingDetail", settingDetail);
						result.add(tranlate);
						if("1".equals(settingDetail.getColPk())){
							mapValueForUpdate.put(j+"", settingDetail);
						}
						j++;
					};
				}	
				maxHeaderNum=maxHeaderNum - 1<0?0:(maxHeaderNum - 1);
				//处理
				List<List<Map<String,Object>>> rowsDatas=new ArrayList<List<Map<String,Object>>>();//存放所有行数据
				getRowsDatas(defaultValues, maxHeaderNum, notExportSettingDetails,
						result, totalRows, columnNums, rowsDatas);
				backsMap.put("redisKey", redisKey);
				backsMap.put("allSettingDetail", allDetails);
				backsMap.put("excelHeaders", excelHeaders);
				backsMap.put("rowsDatas", rowsDatas);
				backsMap.put("defaultValues", defaultValues);
				backsMap.put("totalRows", totalRows-maxHeaderNum);
				backsMap.put("maxHeaderRows", maxHeaderNum);
				backsMap.put("fileName", fileName);
				user.setId(userId);
				backsMap.put("user", user);
				backsMap.put("params", params);
				backsMap.put("fileSize", size);
				backsMap.put("notExportSettingDetails", notExportSettingDetails);
				backsMap.put("settingInfo",settingInfo);
				backsMap.put("config",config);
				backsMap.put("configBak",bakConfig);
				backsMap.put("optRedis",true);//写入Redis，不直接将数据入库
				//生成sql  bakSql
				List<String> sqls=new ArrayList<String>();
				List<String> baksqls=new ArrayList<String>();
				dealSqls(user, importTable, bakImportTable, config,
						mapValueForUpdate, rowsDatas, sqls, baksqls);
				
				backsMap.put("sqls",sqls);
				backsMap.put("baksqls",baksqls);
				backsMap.put("totalRows",totalRows);
				backsMap.put("errorMsg","");
				backsMap.put("maxHeaderRows",maxHeaderNum);
				backsMap.remove("wb");
				backsMap.remove("user");
				boolean b=RedisUtils.set(redisKey, backsMap);
				if(!b){
					return new PublicResult<>(PublicResultConstant.FAILED, "放入Redis失败！");
				}else{
					allData.add(backsMap);
				}
			}  catch (Exception e) {
				e.printStackTrace();
			}
		}
       return new PublicResult<>(PublicResultConstant.SUCCESS, allData);
	}

	/**
	 * @time   2018年12月13日 下午6:14:24
	 * @author zuoqb
	 * @todo   TODO
	 * @param  @param user
	 * @param  @param importTable
	 * @param  @param bakImportTable
	 * @param  @param config
	 * @param  @param mapValueForUpdate
	 * @param  @param rowsDatas
	 * @param  @param sqls
	 * @param  @param baksqls
	 * @param  @throws SQLException
	 * @return_type   void
	 */
	public void dealSqls(User user, String importTable, String bakImportTable,
			AdminDatasourceConfig config,
			HashMap<String, Object> mapValueForUpdate,
			List<List<Map<String, Object>>> rowsDatas, List<String> sqls,
			List<String> baksqls) throws SQLException {
		for(List<Map<String,Object>> rowData:rowsDatas){
			/**
			 *rowData结构
			 * [
				{
				"colName":"dvdxgsdsfff",
				"isDefault":false,
				"orderNo":1,
				"excelName":"字符串",
				"detailValue":"7"
				},
				{
				"colName":"na",
				"isDefault":false,
				"orderNo":2,
				"excelName":"文本",
				"detailValue":"1"
				}
				]
			 */
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String now = sdf.format(new Date());
			System.err.println("当前时间:"+now+"================");
			//有设置主键
			if(!CollectionUtils.isEmpty(mapValueForUpdate)){
			       String sqlForUpdate="";
		    	   sqlForUpdate = selectSql(mapValueForUpdate, importTable,
						rowData, sqlForUpdate);
		    	   Connection conn = null;
		    	   PreparedStatement  pstmt = null ;
		    	   ResultSet rs =null;
		    	   try {
		    		   conn = ExcelConnection.getConn(config.getDbUrl(),config.getDbName(),config.getDbPassword());
				       conn.setAutoCommit(false);
			           pstmt = (PreparedStatement)conn.prepareStatement(sqlForUpdate);
					   rs = pstmt.executeQuery();
					   //之前没有
					   if(rs.next()==false){
						   insertSql(user, importTable, bakImportTable, sqls, baksqls,
								rowData,now);
					   }else{
						   //更新
						   updateSql(user, importTable, bakImportTable, sqls, baksqls,
								rowData,mapValueForUpdate,now);
					   }
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					ExcelConnection.close(conn, pstmt, rs);
				}
				   
		     }else{
		    	 insertSql(user, importTable, bakImportTable, sqls, baksqls, rowData,now);
		     }
		      
		}
		
	}

	/**
	 * @time   2018年12月13日 下午6:13:57
	 * @author zuoqb
	 * @todo   TODO
	 * @param  @param defaultValues
	 * @param  @param maxHeaderNum
	 * @param  @param notExportSettingDetails
	 * @param  @param result
	 * @param  @param totalRows
	 * @param  @param columnNums
	 * @param  @param rowsDatas
	 * @return_type   void
	 */
	public void getRowsDatas(Map<String, String> defaultValues,
			int maxHeaderNum,
			List<EnteringTableSettingDetail> notExportSettingDetails,
			List<Map<String, Object>> result, int totalRows, int columnNums,
			List<List<Map<String, Object>>> rowsDatas) {
		for (int r =maxHeaderNum; r < totalRows; r++) {
			// 解析表头最后一行，获取配置信息
			//解析数据
			 List<Map<String,Object>> rowData=new ArrayList<Map<String,Object>>();
			 for(int c = 0; c <columnNums; c++){
				 Map<String,Object> rowMap=new HashMap<String, Object>();
				 EnteringTableSettingDetail settingDetail=(EnteringTableSettingDetail) result.get(c).get("settingDetail");
				 List<String> columnData=new ArrayList<String>();//存放列全部数据
				 columnData=(List<String>) result.get(c).get("data");
				 if(settingDetail!=null){
					 String colType=settingDetail.getColType();
					 if(StringUtils.isBlank(colType)){
						 colType="varchar";
					 }
					 colType=colType.toLowerCase();
					 String colName=settingDetail.getColName();
					 String excelName=settingDetail.getExcelColName();
					 String  detailValue = "";
					 if(columnData.size()>r){
						 detailValue=columnData.get(r);
					 }
					 rowMap.put("colName", colName);
					 rowMap.put("excelName", excelName);
					 rowMap.put("detailValue", detailValue);
					 rowMap.put("orderNo", c+1);
					 rowMap.put("isDefault", false);
					 rowData.add(rowMap);
				 }
				
			 }
			 //放入没有导出的列的默认值
			 int x=0;
			 for (String in : defaultValues.keySet()) {
				 Map<String,Object> defaultRowMap=new HashMap<String, Object>();
				 for(EnteringTableSettingDetail d:notExportSettingDetails){
					 if(d.getColName().equals(in)){
						 defaultRowMap.put("colName", d.getColName());
						 defaultRowMap.put("excelName", d.getExcelColName());
						 defaultRowMap.put("detailValue", defaultValues.get(in));
						 defaultRowMap.put("orderNo", columnNums+x+1);
						 defaultRowMap.put("isDefault", true);
						 rowData.add(defaultRowMap);
						 x++;
						 break;
					 }
				 }
			 }
			 rowsDatas.add(rowData);
		}
	}

	/**
	 * @time   2018年12月12日 下午2:17:59
	 * @author zuoqb
	 * @todo   TODO
	 * @param  @param mapValueForUpdate
	 * @param  @param tablename
	 * @param  @param rowData
	 * @param  @param sqlForUpdate
	 * @param  @return
	 * @return_type   String
	 */
	public String selectSql(HashMap<String, Object> mapValueForUpdate,
			String tablename, List<Map<String, Object>> rowData,
			String sqlForUpdate) {
		sqlForUpdate+="select id from "+tablename+" where del_flag=0 ";
		   if(mapValueForUpdate.size()!=0){
			   sqlForUpdate+=" and ";
		   }
		   for (String in : mapValueForUpdate.keySet()) {
			   int index=Integer.valueOf(in);
			   String str="";
			   EnteringTableSettingDetail detail=(EnteringTableSettingDetail) mapValueForUpdate.get(in);
			   if(rowData.size()>index){
				   str=rowData.get(index).get("detailValue")+"";
			   }
			   sqlForUpdate+=detail.getColName()+"='"+str+"' and ";
			}
		   sqlForUpdate = sqlForUpdate.substring(0,sqlForUpdate.length() - 4);
		return sqlForUpdate;
	}

	/**
	 * @time   2018年12月12日 下午2:16:08
	 * @author zuoqb
	 * @todo    拼接更新sql
	 * @param  @param user
	 * @param  @param tablename
	 * @param  @param bakTablename
	 * @param  @param sqls
	 * @param  @param baksqls
	 * @param  @param rowData
	 * @return_type   void
	 */
	public void updateSql(User user, String tablename, String bakTablename,
			List<String> sqls, List<String> baksqls,
			List<Map<String, Object>> rowData,HashMap<String, Object> mapValueForUpdate,String now) {
		   String sql = "update "+tablename+" set ";
		   String sqlbak = "insert into "+bakTablename+" ";
		   sqlbak+=" (";
		   for (Map<String,Object> mapValue : rowData) {
			   if(mapValue.get("detailValue")!=null&&StringUtils.isNotBlank(mapValue.get("detailValue")+"")){
				   sql+=mapValue.get("colName")+"='"+mapValue.get("detailValue")+"',";
				   sqlbak+=mapValue.get("colName")+",";
			   }
		   }
		  
		   sql+="update_by='"+user.getId()+"',update_date='"+now+"' where ";
		   sqlbak+="id,create_by,update_by,create_date";
		   sqlbak+=") values(";
		   for (Map<String,Object> mapValue : rowData) {
			   if(mapValue.get("detailValue")!=null&&StringUtils.isNotBlank(mapValue.get("detailValue")+"")){
				   sqlbak+="'";
				   String str = (String) mapValue.get("detailValue");//得到每个key多对用value的值
				   sqlbak+=str+"',";
			   }
		   }
		   sqlbak+="'"+GenerationSequenceUtil.getUUID()+"','"+user.getId()+"','"+user.getId()+"','"+now+"'";
		   sqlbak+=")";
		   for (String in : mapValueForUpdate.keySet()) {
			   int index=Integer.valueOf(in);
			   String str="";
			   EnteringTableSettingDetail detail=(EnteringTableSettingDetail) mapValueForUpdate.get(in);
			   if(rowData.size()>index){
				   str=rowData.get(index).get("detailValue")+"";
			   }
			   sql+=detail.getColName()+"='"+str+"' and ";
			}
		   sql = sql.substring(0,sql.length() - 4);
		   System.out.println(sql+"=================================================================");
		   sqls.add(sql);
		   baksqls.add(sqlbak);
	}

	/**
	 * @time   2018年12月12日 下午1:59:06
	 * @author zuoqb
	 * @todo    拼接插入sql
	 * @param  @param user
	 * @param  @param tablename
	 * @param  @param bakTablename
	 * @param  @param sqls
	 * @param  @param baksqls
	 * @param  @param rowData
	 * @return_type   void
	 */
	public void insertSql(User user, String tablename, String bakTablename,
			List<String> sqls, List<String> baksqls,
			List<Map<String, Object>> rowData,String now) {
		//插入
		   String sql = "insert into "+tablename;
		   String sqlbak = "insert into "+bakTablename+" ";
		   sql+=" (";
		   sqlbak+=" (";
		   for (Map<String,Object> mapValue : rowData) {
			   if(mapValue.get("detailValue")!=null&&StringUtils.isNotBlank(mapValue.get("detailValue")+"")){
				   sql+=mapValue.get("colName")+",";
				   sqlbak+=mapValue.get("colName")+",";
			   }
		   }
		
		   sql+="id,create_by,update_by,create_date";
		   sqlbak+="id,create_by,update_by,create_date";
		   sql+=") values(";
		   sqlbak+=") values(";
		   for (Map<String,Object> mapValue : rowData) {
			   if(mapValue.get("detailValue")!=null&&StringUtils.isNotBlank(mapValue.get("detailValue")+"")){
				   sql+="'";
				   sqlbak+="'";
				   String str = (String) mapValue.get("detailValue");//得到每个key多对用value的值
				   sql+=str+"',";
				   sqlbak+=str+"',";
			   }
		   }
		   sql+="'"+GenerationSequenceUtil.getUUID()+"','"+user.getId()+"','"+user.getId()+"','"+now+"'";
		   sql+=")";
		   sqlbak+="'"+GenerationSequenceUtil.getUUID()+"','"+user.getId()+"','"+user.getId()+"','"+now+"'";
		   sqlbak+=")";
		   System.out.println(sqlbak+"=================================================================");
		   sqls.add(sql);
		   baksqls.add(sqlbak);
	}
	

}
