package com.haier.datamart.controller;

import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.util.StringUtil;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.entity.MonitorEtlControlParam;
import com.haier.datamart.entity.MonitorEtlParamM1;
import com.haier.datamart.entity.MonitorEtlParamM2;
import com.haier.datamart.entity.MonitorEtlParamM3;
import com.haier.datamart.entity.MonitorEtlParamM4;
import com.haier.datamart.entity.MonitorEtlParamM5;
import com.haier.datamart.entity.MonitorEtlParamM6;
import com.haier.datamart.entity.User;
import com.haier.datamart.service.IMonitorService;
import com.haier.datamart.service.IUserService;
import com.haier.datamart.utils.GenerationSequenceUtil;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author leizhiguo123
 * @since 2018-07-19
 */
@RestController
@RequestMapping("/template")
public class MonitorController  extends BaseController{
	
	@Autowired
	private IMonitorService iMonitorService;
	@Autowired
	private IUserService userService;
	
	/**
	 * 表输出数据库id
	 */
	private static String tableoutputDatabase;
	/**
	 * 表输出表名
	 */
	private static String tableoutputTable;
	/**
	 * 表输出字段名
	 */
	private static String tableoutputColumn;
	/**
	 * 流输出字段后缀
	 */		
	private static  String sufStreamoutputColumn;
	/**
	 * 
	 */
	private static Map<String,Integer> operatorMap = new HashMap<String,Integer>();
	static{
		tableoutputDatabase = "11";
		tableoutputTable = "monitor_etl_result";
		tableoutputColumn = "index_id@#index_name@#flag@#batch_no@#v_etl_date@#etl_type_desc@#module_desc";
		operatorMap.put(">=", 5);//此处operatorMap排列顺序不能换
		operatorMap.put("<>", 1);
		operatorMap.put("!=", 1);
		operatorMap.put("<=", 3);
		operatorMap.put(">", 4);
		operatorMap.put("<", 2);
		operatorMap.put("=", 0);
	}
	
	
	/**
	 * 根据关键字模糊搜索
	 * @param request
	 * @param moduleName
	 * @param moduleDesc
	 * @param createorName
	 * @return
	 */
	@ApiOperation(value = "根据关键字模糊搜索", notes = "根据关键字模糊搜索", httpMethod = "GET")
	@RequestMapping(value = "/fuzzSearch",method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	//@Log(description = "API接口:/template/fuzzSearch")
	public Object fuzzSearch(HttpServletRequest request,
			String moduleName,String moduleDesc,String loginName){
		try { 
			User user = getLoginUser(request);
			if(user.getId()==null){
				return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
			}
			List<MonitorEtlControlParam> mecp = new ArrayList<MonitorEtlControlParam>();
			if(StringUtil.isNotEmpty(moduleName)||StringUtil.isNotEmpty(moduleDesc)||StringUtil.isNotEmpty(loginName)){
				if(StringUtil.isEmpty(loginName)){
					mecp.addAll((List<MonitorEtlControlParam>)iMonitorService.fuzzSearch( moduleName, moduleDesc,null));
				}else{
					List<User> users = userService.getUserIdByLoginName(loginName);
					for (User user2 : users) {
						mecp.addAll((List<MonitorEtlControlParam>)iMonitorService.fuzzSearch( moduleName, moduleDesc,user2.getId()));
					}
				}
			}else{//若无参数,功能为获取控制参数集合
				 mecp = iMonitorService.getControllerParam();
			}
			return new PublicResult<>(PublicResultConstant.SUCCESS,mecp);
		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED,"参数错误!");
		}
		
	}
	/**
	 * 获取模板控制参数信息
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "获取模板控制参数信息", notes = "获取模板控制参数信息", httpMethod = "GET")
	@RequestMapping(value = "/getControllerParamList",method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	//@Log(description = "API接口:/template/getControllerParamList")
	public Object getControllerParamList(HttpServletRequest request){
		try {
			User user = getLoginUser(request);
			if(user.getId()==null){
				return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
			}
			List<MonitorEtlControlParam> mecp = iMonitorService.getControllerParam();
			for (MonitorEtlControlParam monitorEtlControlParam : mecp) {//设置创建者栏为登录名
				String creatorId = monitorEtlControlParam.getCreateBy();
				if(creatorId!=null){
					User creatorUser  = userService.selectOne(creatorId);
					if(creatorUser!=null){
						monitorEtlControlParam.setCreateBy(creatorUser.getLoginName());
					}else{
						monitorEtlControlParam.setCreateBy("");
					}
				}else{
					monitorEtlControlParam.setCreateBy("");
				}
			}
			return new PublicResult<>(PublicResultConstant.SUCCESS,mecp);
		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED,"参数错误!");
		}
		
	}
	
	/**
	 * 获取当前指标下的模板监控配置
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "获取当前指标下的模板监控配置", notes = "获取当前指标下的模板监控配置", httpMethod = "GET")
	@RequestMapping(value = "/getMonitorDetailByIndex",method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	//@Log(description = "API接口:/template/getMonitorDetailByIndex")
	public Object getMonitorDetailByIndex(HttpServletRequest request,String indexId){
		try {
			
			User user = getLoginUser(request);
			//user.setId(null);
			if(user.getId()==null){
				return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
			}
			List<MonitorEtlControlParam> mecps = iMonitorService.getModuleIdByIndexId(indexId);
			
			Map resultMap = new HashMap<>();
			resultMap.put("indexId", mecps.get(0).getIndexId());
			resultMap.put("moduleName", mecps.get(0).getModuleName());//设置模板名称
			resultMap.put("indexName", mecps.get(0).getIndexName());//设置指标名称
			for (MonitorEtlControlParam monitorEtlControlParam : mecps) {
				//MonitorEtlControlParam config = iMonitorService.getConfigBymoduleId(monitorEtlControlParam.getModuleId());
				String id = monitorEtlControlParam.getModuleId();
				String flag = monitorEtlControlParam.getEtlTypeId();
				int a = Integer.parseInt(flag.substring(flag.length()-1, flag.length()));
				switch (a) {
				case 1:
					MonitorEtlParamM1 m1 = iMonitorService.getModel1ById(id);
					if(m1==null){
						m1=new MonitorEtlParamM1();
					}
					if(m1.getFilterLeftvalue()!=null&&m1.getFilterRightvalue()!=null){
						m1.setFilterLeftvalue(m1.getFilterLeftvalue()+"="+m1.getFilterRightvalue());
					}
					if(m1.getMergejoinmeta()!=null&&m1.getMergejoinmeta1()!=null){
						m1.setMergejoinmeta(m1.getMergejoinmeta()+"="+m1.getMergejoinmeta1());
					}
					if(m1.getStreamoutputColumn()!=null){
						m1.setTableoutputColumn(m1.getStreamoutputColumn().replace(tableoutputColumn, "").replace("#", "")
								.replace("@", ","));
					}
					m1.setMecp(monitorEtlControlParam);
					resultMap.put(a, m1.getModuleId());
					break;
				case 2:
					MonitorEtlParamM2 m2 = iMonitorService.getModel2ById(id);
					if(m2==null){
						m2=new MonitorEtlParamM2();
					}
					if(m2.getFilterLeftvalue()!=null&&m2.getFilterRightvalue()!=null){
						m2.setFilterLeftvalue(m2.getFilterLeftvalue()+"="+m2.getFilterRightvalue());
					}
					if(m2.getMergejoinmeta()!=null&&m2.getMergejoinmeta1()!=null){
						m2.setMergejoinmeta(m2.getMergejoinmeta()+"="+m2.getMergejoinmeta1());
					}
					if(m2.getStreamoutputColumn()!=null){
						m2.setTableoutputColumn(m2.getStreamoutputColumn().replace(tableoutputColumn, "").replace("#", "")
								.replace("@", ","));
					}
					m2.setMecp(monitorEtlControlParam);
					resultMap.put(a, m2.getModuleId());
					break;
				case 3:
					MonitorEtlParamM3 m3 = iMonitorService.getModel3ById(id);
					if(m3==null){
						m3=new MonitorEtlParamM3();
					}
					if(m3.getStreamoutputColumn()!=null){
						m3.setTableoutputColumn(m3.getStreamoutputColumn().replace(tableoutputColumn, "").replace("#", "")
								.replace("@", ","));
					}
					m3.setMecp(monitorEtlControlParam);
					resultMap.put(a, m3.getModuleId());
					break;
				case 4:
					MonitorEtlParamM4 m4 = iMonitorService.getModel4ById(id);
					if(m4==null){
						m4=new MonitorEtlParamM4();
					}
					if(m4.getStreamoutputColumn()!=null){
						m4.setTableoutputColumn(m4.getStreamoutputColumn().replace(tableoutputColumn, "").replace("#", "")
								.replace("@", ","));
					}
					if(m4.getFilterLeftvalue()!=null&&
					  m4.getFilterRightvalue()!=null&&
					  m4.getFilterFunction()!=null){
						m4.setFilterLeftvalue(m4.getFilterLeftvalue()+m4.getFilterFunction()+m4.getFilterRightvalue());
					}
					if(m4.getMergejoinmeta()!=null&&m4.getMergejoinmeta1()!=null){
						m4.setMergejoinmeta(m4.getMergejoinmeta()+"="+m4.getMergejoinmeta1());
					}
					m4.setMecp(monitorEtlControlParam);
					resultMap.put(a, m4.getModuleId());
					break;
				case 5:
					MonitorEtlParamM5 m5 = iMonitorService.getModel5ById(id);
					if(m5==null){
						m5=new MonitorEtlParamM5();
					}
					m5.setMecp(monitorEtlControlParam);
					resultMap.put(a, m5.getModuleId());
					if(m5.getStreamoutputColumn()!=null){
						m5.setTableoutputColumn(m5.getStreamoutputColumn().replace(tableoutputColumn, "").replace("#", "")
								.replace("@", ","));
					}
					break;
				case 6:
					MonitorEtlParamM6 m6 = iMonitorService.getModel6ById(id);
					if(m6==null){
						m6=new MonitorEtlParamM6();
					}
					m6.setMecp(monitorEtlControlParam);
					resultMap.put(a, m6.getModuleId());
					break;
				default:
					return new PublicResult<>(PublicResultConstant.FAILED,"数据库内容有异常!");
				}
			}
			return new PublicResult<>(PublicResultConstant.SUCCESS,resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED,"参数错误!");
		}
		
	}
	/**
	 * 从配置参数表中检查该指标编码下是否已有监控模板的配置了
	 * @param IndexId 实际为indexCode
	 * @return
	 */
	@ApiOperation(value = "从配置参数表中检查该指标编码下是否已有监控模板的配置", notes = "从配置参数表中检查该指标编码下是否已有监控模板的配置", httpMethod = "GET")
	@RequestMapping(value = "/checkIsExist", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	//@Log(description = "API接口:/template/checkIsExist")
	public Object checkIsExist(HttpServletRequest request,String indexId){
		try {
			User user = getLoginUser(request);
			/*if(user.getId()==null){
				return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
			}*/
			List a = new ArrayList();
			boolean result = 
					iMonitorService.checkIsExist(indexId)?
					a.add(PublicResultConstant.ERROR)&&a.add("该指标下已经存在模板!"):
					a.add(PublicResultConstant.SUCCESS)&&a.add("");
			return new PublicResult<>((PublicResultConstant)(a.get(0)),a.get(1));
		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED,"参数错误!");
		}
		
	}
	/**
	 * 增加模板一
	 * @param request
	 * @param paramM1
	 * @return
	 */
	@ApiOperation(value = "增加模板一", notes = "分页查询文件上传记录表", httpMethod = "POST")
	@RequestMapping(value = "/alterModel1",method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	//@Log(description = "API接口:/template/alterModel1")
	public Object alterModel1(HttpServletRequest request,@RequestBody MonitorEtlParamM1 paramM1){
		try {
				Date currentDate = new Date();//生成当前日期
				
				Map<String, Object> result = new HashMap<>();//返回的数据(配置id和模板id)
				
				User user = getLoginUser(request);
				/*if(user.getId()==null){
					return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
				}*/
				MonitorEtlControlParam mecp = paramM1.getMecp();
			
				
				mecp.setCreateBy(user.getId());
				paramM1.setCreateBy(user.getId());
				
				mecp.setCreateDate(currentDate);
				paramM1.setCreateDate(currentDate);
				
				mecp.setUpdateBy(user.getId());
				paramM1.setUpdateBy(user.getId());
				
				mecp.setUpdateDate(currentDate);
				paramM1.setUpdateDate(currentDate);
				
				
				mecp.setEtlTypeId("TYPE0001");
				paramM1.setTypeId("TYPE0001");
				paramM1.setTableoutputDatabase(tableoutputDatabase);
				paramM1.setTableoutputTable(tableoutputTable);
				paramM1.setStreamoutputColumn(tableoutputColumn);
				if(StringUtil.isNotEmpty(paramM1.getTableoutputColumn())){//表输出字段
					String realOutputColumn = "";
					for (String outputColumn : paramM1.getTableoutputColumn().split(",")) {
						realOutputColumn += "@#"+outputColumn;
					}
					paramM1.setStreamoutputColumn(tableoutputColumn+realOutputColumn);
				}
				paramM1.setTableoutputColumn(tableoutputColumn);
				if(paramM1.getIndexId()!=null){
					mecp.setIndexId(paramM1.getIndexId());
				}
				
				
				if(StringUtil.isNotEmpty(paramM1.getMergejoinmeta())){//表与表关联字段
					if(paramM1.getMergejoinmeta().matches("^[^=]*=[^=]*$")){
						String[] mergejoinmetas = paramM1.getMergejoinmeta().split("=");
						paramM1.setMergejoinmeta(mergejoinmetas[0]);
						paramM1.setMergejoinmeta1(mergejoinmetas[1]);
					}else{
						return new PublicResult<>(PublicResultConstant.ERROR,"关联字段输入框格式错误");
					}
				}
				if(StringUtil.isNotEmpty(paramM1.getFilterLeftvalue())){//筛选条件
					if(paramM1.getFilterLeftvalue().matches("^[^=]*=[^=]*$")){
						String[] FilterValues = paramM1.getFilterLeftvalue().split("=");
						paramM1.setFilterLeftvalue(FilterValues[0]);
						paramM1.setFilterRightvalue(FilterValues[1]);
					}else{
						return new PublicResult<>(PublicResultConstant.ERROR,"筛选条件输入框格式错误");
					}
				}
				
				if(StringUtil.isEmpty(paramM1.getModuleId())){
					String configId = GenerationSequenceUtil.getUUID();//随机生成id
					String moduleId = GenerationSequenceUtil.getUUID();//随机生成id
					paramM1.setModuleId(moduleId);
					mecp.setId(configId);
					mecp.setModuleId(moduleId); 
					
					if(iMonitorService.addModel1One(paramM1,mecp)){//若返回true
						result.put("moduleId",moduleId);
						return new PublicResult<>(PublicResultConstant.SUCCESS,result);
					}else{
						return new PublicResult<>(PublicResultConstant.ERROR,"添加失败!");
					}
				}else{
					mecp.setModuleId(paramM1.getModuleId());
					result.put("moduleId",paramM1.getModuleId());
					if(iMonitorService.updateParamM1(paramM1,mecp)){
						return new PublicResult<>(PublicResultConstant.SUCCESS,result);
					}else{
						return new PublicResult<>(PublicResultConstant.ERROR,"修改失败!");
					}
				}
				
				
			}catch (Exception e) {
				
				e.printStackTrace();
				return new PublicResult<>(PublicResultConstant.FAILED,"发生未知错误,操作失败!");
			}
		
	}
	
	/**
	 * 根据模板id和配置id获取回显信息
	 * @param request
	 * @param configId
	 * @param moduleId
	 * @return
	 */
	@ApiOperation(value = "根据模板id和配置id获取回显信息", notes = "根据模板id和配置id获取回显信息", httpMethod = "GET")
	@RequestMapping(value = "/getModel1", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	//@Log(description = "API接口:/template/getModel1")
	public Object getModel1(HttpServletRequest request,String moduleId){
		try {
			User user = getLoginUser(request);
			if(user.getId()==null){
				return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
			}
			MonitorEtlControlParam mecp = iMonitorService.getConfigBymoduleId(moduleId);
			MonitorEtlParamM1 m1 = iMonitorService.getModel1ById(moduleId);
			
			if(m1.getFilterLeftvalue()!=null&&m1.getFilterRightvalue()!=null){
				m1.setFilterLeftvalue(m1.getFilterLeftvalue()+"="+m1.getFilterRightvalue());
			}
			if(m1.getMergejoinmeta()!=null&&m1.getMergejoinmeta1()!=null){
				m1.setMergejoinmeta(m1.getMergejoinmeta()+"="+m1.getMergejoinmeta1());
			}
			String str = m1.getStreamoutputColumn().
			replace(tableoutputColumn, "").replace("#", "")
			.replace("@", ",");
			if(StringUtil.isNotEmpty(str)){
				if(tableoutputColumn.length()!=0){
					m1.setTableoutputColumn(str.substring(1, str.length()));
				}else{
					m1.setTableoutputColumn(str);
				}
			}else{
				m1.setTableoutputColumn("");
			}
			
			
			m1.setMecp(mecp);
			return  new PublicResult<>(PublicResultConstant.SUCCESS,m1);
		} catch (Exception e) {
			e.printStackTrace();
			return  new PublicResult<>(PublicResultConstant.FAILED,e.getMessage());
		}
		
	}
	/**
	 * 所有模板的删除操作
	 * @param request
	 * @param moduleId
	 * @return
	 */
	@ApiOperation(value = "所有模板的删除操作", notes = "所有模板的删除操作", httpMethod = "POST")
	@RequestMapping(value = "/deleteModel",method = {RequestMethod.POST,RequestMethod.GET}, produces = { "application/json;charset=UTF-8" })
	//@Log(description = "API接口:/template/deleteModel")
	public Object deleteModel(HttpServletRequest request,String indexId){
		
		try {
			User user = getLoginUser(request);
			if(user.getId()==null){
				return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
			}
			//boolean flag = true;
			List<MonitorEtlControlParam> mecps = iMonitorService.getModuleIdByIndexId(indexId);
			for (MonitorEtlControlParam monitorEtlControlParam : mecps) {
				String moduleId = monitorEtlControlParam.getModuleId();
				String etlTypeId  =  monitorEtlControlParam.getEtlTypeId();
				iMonitorService.deleteModel(moduleId,etlTypeId);
			}
			return new PublicResult<>(PublicResultConstant.SUCCESS,"删除成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED,"发生未知错误,删除失败!");
		}
		
	}
	
	@ApiOperation(value = "操作模板二", notes = "操作模板二", httpMethod = "POST")
	@RequestMapping(value = "/alterModel2",method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	//@Log(description = "API接口:/template/alterModel2")
	public Object alterModel2(HttpServletRequest request,@RequestBody MonitorEtlParamM2 paramM2){
		try {
				Date currentDate = new Date();//生成当前日期
				
				Map<String, Object> result = new HashMap<>();//返回的数据(配置id和模板id)
				
				
				User user = getLoginUser(request);
				if(user.getId()==null){
					return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
				}
				MonitorEtlControlParam mecp = paramM2.getMecp();
				
				if(paramM2.getIndexId()!=null){
					mecp.setIndexId(paramM2.getIndexId());
				}
				mecp.setCreateBy(user.getId());
				paramM2.setCreateBy(user.getId());
				
				mecp.setCreateDate(currentDate);
				paramM2.setCreateDate(currentDate);
				
				mecp.setUpdateBy(user.getId());
				paramM2.setUpdateBy(user.getId());
				
				mecp.setUpdateDate(currentDate);
				paramM2.setUpdateDate(currentDate);
				
				mecp.setEtlTypeId("TYPE0002");
				paramM2.setTypeId("TYPE0002");
				paramM2.setTableoutputDatabase(tableoutputDatabase);
				paramM2.setTableoutputTable(tableoutputTable);
				paramM2.setStreamoutputColumn(tableoutputColumn);
				if(StringUtil.isNotEmpty(paramM2.getTableoutputColumn())){//表输出字段
					String realOutputColumn = "";
					for (String outputColumn : paramM2.getTableoutputColumn().split(",")) {
						realOutputColumn += "@#"+outputColumn;
					}
					paramM2.setStreamoutputColumn(tableoutputColumn+realOutputColumn);
				}
				paramM2.setTableoutputColumn(tableoutputColumn);
				//((\\w)*=.*&)*
				//paramM2.setHttpParamname("a=b&c=d&C=F&D=F");
				if(StringUtil.isNotEmpty(paramM2.getHttpParamname())){//http参数
					String str = paramM2.getHttpParamname()+"&";
					int count1 = 0;
					int count2 = 0;
					String key1 = "=";
					String key2 = "&";
					for (int i=0;i<str.length();i++) {
						if(key1.equals(String.valueOf(str.charAt(i)))){
							count1++;
						}
						if(key2.equals(String.valueOf(str.charAt(i)))){
							count2++;
						}
					}
					
					if(str.matches("((\\w)*=.*&)*")&&count1==count2){//若格式正确
						String newStr = str;
						String param = "";
						String value = "";
						String httpParamName = "";
						String httpParamValue = "";
						for(int i=0;i<count1;i++){
							httpParamName += "@#"+newStr.substring(0,newStr.indexOf("="));
							newStr = newStr.substring(newStr.indexOf("=")+1);
							httpParamValue += "@#"+newStr.substring(0,newStr.indexOf("&"));
							newStr = newStr.substring(newStr.indexOf("&")+1);
						}
						httpParamName = httpParamName.substring(2);
						httpParamValue = httpParamValue.substring(2);
						paramM2.setHttpParamname(httpParamName);
						paramM2.setHttpParamvalue(httpParamValue);
					}else{
						return new PublicResult<>(PublicResultConstant.ERROR,"界面WS参数格式错误!");
					}
				}
				if(StringUtil.isNotEmpty(paramM2.getMergejoinmeta())){//表与表关联字段
					if(paramM2.getMergejoinmeta().matches("^[^=]*=[^=]*$")){
						String[] mergejoinmetas = paramM2.getMergejoinmeta().split("=");
						paramM2.setMergejoinmeta(mergejoinmetas[0]);
						paramM2.setMergejoinmeta1(mergejoinmetas[1]);
					}else{
						return new PublicResult<>(PublicResultConstant.ERROR,"关联字段输入框格式错误");
					}
				}
				if(StringUtil.isNotEmpty(paramM2.getFilterLeftvalue())){//筛选条件
					if(paramM2.getFilterLeftvalue().matches("^[^=]*=[^=]*$")){
						String[] FilterValues = paramM2.getFilterLeftvalue().split("=");
						paramM2.setFilterLeftvalue(FilterValues[0]);
						paramM2.setFilterRightvalue(FilterValues[1]);
					}else{
						return new PublicResult<>(PublicResultConstant.ERROR,"筛选条件输入框格式错误");
					}
				}
				
				if(StringUtil.isEmpty(paramM2.getModuleId())){
					String configId = GenerationSequenceUtil.getUUID();//随机生成id
					String moduleId = GenerationSequenceUtil.getUUID();//随机生成id
					paramM2.setModuleId(moduleId);
					mecp.setId(configId);
					mecp.setModuleId(moduleId); 
					
					result.put("moduleId",moduleId);
					if(iMonitorService.addModel2One(paramM2,mecp)){//若返回true
						return new PublicResult<>(PublicResultConstant.SUCCESS,result);
					}else{
						return new PublicResult<>(PublicResultConstant.ERROR,"添加失败!");
					}
				}else{
					mecp.setModuleId(paramM2.getModuleId());
					result.put("moduleId",paramM2.getModuleId());
					if(iMonitorService.updateParamM2(paramM2,mecp)){
						return new PublicResult<>(PublicResultConstant.SUCCESS,result);
					}else{
						return new PublicResult<>(PublicResultConstant.ERROR,"修改失败!");
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
				return new PublicResult<>(PublicResultConstant.FAILED,"发生未知错误,添加失败!");
			}
		
	}
	
	/**
	 * 根据模板id和配置id获取回显信息
	 * @param request
	 * @param configId
	 * @param moduleId
	 * @return
	 */
	@ApiOperation(value = "根据模板id和配置id获取模板二回显信息", notes = "根据模板id和配置id获取模板二回显信息", httpMethod = "GET")
	@RequestMapping(value = "/getModel2", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	//@Log(description = "API接口:/template/getModel2")
	public Object getModel2(HttpServletRequest request,String moduleId){
		try {
			User user = getLoginUser(request);
			/*if(user.getId()==null){
				return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
			}*/
			MonitorEtlControlParam mecp = iMonitorService.getConfigBymoduleId(moduleId);
			MonitorEtlParamM2 m2 = iMonitorService.getModel2ById(moduleId);
			if(m2.getFilterLeftvalue()!=null&&m2.getFilterRightvalue()!=null){
				m2.setFilterLeftvalue(m2.getFilterLeftvalue()+"="+m2.getFilterRightvalue());
			}
			if(m2.getMergejoinmeta()!=null&&m2.getMergejoinmeta1()!=null){
				m2.setMergejoinmeta(m2.getMergejoinmeta()+"="+m2.getMergejoinmeta1());
			}
			String str = m2.getStreamoutputColumn().
					replace(tableoutputColumn, "").replace("#", "")
					.replace("@", ",");
					if(StringUtil.isNotEmpty(str)){
						if(tableoutputColumn.length()!=0){
							m2.setTableoutputColumn(str.substring(1, str.length()));
						}else{
							m2.setTableoutputColumn(str);
						}
					}else{
						m2.setTableoutputColumn("");
					}
			String targetStr = "";
			if(StringUtil.isNotEmpty(m2.getHttpParamname())){
				String[] names= m2.getHttpParamname().split("@#");
				String[] pa= m2.getHttpParamvalue().split("@#");
 				for (int x=0;x<names.length;x++) {
 					targetStr += names[x]+"="+pa[x]+"&";
				}
			}
			
			if(StringUtil.isNotEmpty(targetStr))
			m2.setHttpParamname(targetStr.substring(0,targetStr.length()-1));
			m2.setMecp(mecp);
			return  new PublicResult<>(PublicResultConstant.SUCCESS,m2);
		} catch (Exception e) {
			e.printStackTrace();
			return  new PublicResult<>(PublicResultConstant.FAILED,e.getMessage());
		}
		
	}
	
	/*模板3*/
	@ApiOperation(value = "根据模板id和配置id获取模板3回显信息", notes = "根据模板id和配置id获取模板3回显信息", httpMethod = "POST")
	@RequestMapping(value = "/alterModel3",method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	//@Log(description = "API接口:/template/alterModel3")
	public Object alterModel3(HttpServletRequest request,@RequestBody MonitorEtlParamM3 paramM3){
		try {
				Date currentDate = new Date();//生成当前日期
				
				Map<String, Object> result = new HashMap<>();//返回的数据(配置id和模板id)
				
				User user = getLoginUser(request);
				if(user.getId()==null){
					return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
				}
				MonitorEtlControlParam mecp = paramM3.getMecp();
				
				if(paramM3.getIndexId()!=null){
					mecp.setIndexId(paramM3.getIndexId());
				}
				mecp.setCreateBy(user.getId());
				paramM3.setCreateBy(user.getId());
				
				mecp.setCreateDate(currentDate);
				paramM3.setCreateDate(currentDate);
				
				mecp.setUpdateBy(user.getId());
				paramM3.setUpdateBy(user.getId());
				
				mecp.setUpdateDate(currentDate);
				paramM3.setUpdateDate(currentDate);
				
				mecp.setEtlTypeId("TYPE0003");
				paramM3.setTypeId("TYPE0003");
				paramM3.setTableoutputDatabase(tableoutputDatabase);
				paramM3.setTableoutputTable(tableoutputTable);
				paramM3.setStreamoutputColumn(tableoutputColumn);
				if(StringUtil.isNotEmpty(paramM3.getTableoutputColumn())){//表输出字段
					String realOutputColumn = "";
					for (String outputColumn : paramM3.getTableoutputColumn().split(",")) {
						realOutputColumn += "@#"+outputColumn;
					}
					paramM3.setStreamoutputColumn(tableoutputColumn+realOutputColumn);
				}
				paramM3.setTableoutputColumn(tableoutputColumn);
				
				if(StringUtil.isEmpty(paramM3.getModuleId())){
					String configId = GenerationSequenceUtil.getUUID();//随机生成id
					String moduleId = GenerationSequenceUtil.getUUID();//随机生成id
					paramM3.setModuleId(moduleId);
					mecp.setId(configId);
					mecp.setModuleId(moduleId); 
					
					result.put("moduleId",moduleId);
					if(iMonitorService.addModel3One(paramM3,mecp)){//若返回true
						return new PublicResult<>(PublicResultConstant.SUCCESS,result);
					}else{
						return new PublicResult<>(PublicResultConstant.ERROR,"添加失败!");
					}
				}else{
					mecp.setModuleId(paramM3.getModuleId());
					result.put("moduleId",paramM3.getModuleId());
					if(iMonitorService.updateParamM3(paramM3,mecp)){
						return new PublicResult<>(PublicResultConstant.SUCCESS,result);
					}else{
						return new PublicResult<>(PublicResultConstant.ERROR,"修改失败!");
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
				return new PublicResult<>(PublicResultConstant.FAILED,"发生未知错误,添加失败!");
			}
		
	}
	
	/**
	 * 根据模板id和配置id获取回显信息
	 * @param request
	 * @param configId
	 * @param moduleId
	 * @return
	 */
	@ApiOperation(value = "根据模板id和配置id获取模板3回显信息", notes = "根据模板id和配置id获取模板3回显信息", httpMethod = "GET")
	@RequestMapping(value = "/getModel3", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	//@Log(description = "API接口:/template/getModel3")
	public Object getModel3(HttpServletRequest request,String moduleId){
		try {
			User user = getLoginUser(request);
			/*if(user.getId()==null){
				return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
			}*/
			MonitorEtlControlParam mecp = iMonitorService.getConfigBymoduleId(moduleId);
			MonitorEtlParamM3 m3 = iMonitorService.getModel3ById(moduleId);
			String str = m3.getStreamoutputColumn().replace(tableoutputColumn, "")
					.replace("#", "")
					.replace("@", ",");
				if(StringUtil.isNotEmpty(str)){
						if(tableoutputColumn.length()!=0){
							m3.setTableoutputColumn(str.substring(1, str.length()));
						}else{
							m3.setTableoutputColumn(str);
						}
					}else{
						m3.setTableoutputColumn("");
					}
			
			m3.setMecp(mecp);
			return  new PublicResult<>(PublicResultConstant.SUCCESS,m3);
		} catch (Exception e) {
			e.printStackTrace();
			return  new PublicResult<>(PublicResultConstant.FAILED,e.getMessage());
		}
		
	}
	
	/*模板4*/
	@RequestMapping(value = "/alterModel4",method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	//@Log(description = "API接口:/template/alterModel4")
	public Object alterModel4(HttpServletRequest request,@RequestBody MonitorEtlParamM4 paramM4){
		try {
				Date currentDate = new Date();//生成当前日期
				
				Map<String, Object> result = new HashMap<>();//返回的数据(配置id和模板id)
				
				User user = getLoginUser(request);
				/*if(user.getId()==null){
					return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
				}*/
				MonitorEtlControlParam mecp = paramM4.getMecp();
				
				if(paramM4.getIndexId()!=null){
					mecp.setIndexId(paramM4.getIndexId());
				}
				
				mecp.setCreateBy(user.getId());
				paramM4.setCreateBy(user.getId());
				
				mecp.setCreateDate(currentDate);
				paramM4.setCreateDate(currentDate);
				
				mecp.setUpdateBy(user.getId());
				paramM4.setUpdateBy(user.getId());
				
				mecp.setUpdateDate(currentDate);
				paramM4.setUpdateDate(currentDate);
				
				mecp.setEtlTypeId("TYPE0004");
				paramM4.setTypeId("TYPE0004");
				paramM4.setTableoutputDatabase(tableoutputDatabase);
				paramM4.setTableoutputTable(tableoutputTable);
				paramM4.setStreamoutputColumn(tableoutputColumn);
				if(StringUtil.isNotEmpty(paramM4.getTableoutputColumn())){//表输出字段
					String realOutputColumn = "";
					for (String outputColumn : paramM4.getTableoutputColumn().split(",")) {
						realOutputColumn += "@#"+outputColumn;
					}
					paramM4.setStreamoutputColumn(tableoutputColumn+realOutputColumn);
				}
				paramM4.setTableoutputColumn(tableoutputColumn);
				if(StringUtil.isNotEmpty(paramM4.getMergejoinmeta())){//表与表关联字段
					if(paramM4.getMergejoinmeta().matches("^[^=]*=[^=]*$")){
						String[] mergejoinmetas = paramM4.getMergejoinmeta().split("=");
						paramM4.setMergejoinmeta(mergejoinmetas[0]);
						paramM4.setMergejoinmeta1(mergejoinmetas[1]);
					}else{
						return new PublicResult<>(PublicResultConstant.ERROR,"输入框格式错误");
					}
				}
				if(StringUtil.isNotEmpty(paramM4.getFilterLeftvalue())){//筛选条件
					int flag = 0;
					AAA:
					for (String pattern : operatorMap.keySet()) {
						
							if(paramM4.getFilterLeftvalue().matches("^[^"+pattern+"]*"+pattern+"[^"+pattern+"]*$")){
								String[] FilterValues = paramM4.getFilterLeftvalue().split(pattern);
									paramM4.setFilterLeftvalue(FilterValues[0]);
									paramM4.setFilterRightvalue(FilterValues[1]);
									paramM4.setFilterFunction(operatorMap.get(pattern));
									break AAA;
							}
						
						flag++;
					}
					if(flag==operatorMap.size()){
						return new PublicResult<>(PublicResultConstant.ERROR,"输入框格式错误");
					}
				}
				if(StringUtil.isEmpty(paramM4.getModuleId())){
					String configId = GenerationSequenceUtil.getUUID();//随机生成id
					String moduleId = GenerationSequenceUtil.getUUID();//随机生成id
					paramM4.setModuleId(moduleId);
					mecp.setId(configId);
					mecp.setModuleId(moduleId); 
					
					result.put("moduleId",moduleId);
					if(iMonitorService.addModel4One(paramM4,mecp)){//若返回true
						return new PublicResult<>(PublicResultConstant.SUCCESS,result);
					}else{
						return new PublicResult<>(PublicResultConstant.ERROR,"添加失败!");
					}
				}else{
					mecp.setModuleId(paramM4.getModuleId());
					result.put("moduleId",paramM4.getModuleId());
					if(iMonitorService.updateParamM4(paramM4,mecp)){
						return new PublicResult<>(PublicResultConstant.SUCCESS,result);
					}else{
						return new PublicResult<>(PublicResultConstant.ERROR,"修改失败!");
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
				return new PublicResult<>(PublicResultConstant.FAILED,"发生未知错误,添加失败!");
			}
		
	}
	
	/**
	 * 根据模板id和配置id获取回显信息
	 * @param request
	 * @param configId
	 * @param moduleId
	 * @return
	 */
	@RequestMapping(value = "/getModel4",method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	////@Log(description = "API接口:/template/getModel4")
	public Object getModel4(HttpServletRequest request,String moduleId){
		try {
			User user = getLoginUser(request);
			/*if(user.getId()==null){
				return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
			}*/
			MonitorEtlControlParam mecp = iMonitorService.getConfigBymoduleId(moduleId);
			MonitorEtlParamM4 m4 = iMonitorService.getModel4ById(moduleId);
			String str = m4.getStreamoutputColumn().
					replace(tableoutputColumn, "").replace("#", "")
					.replace("@", ",");
					if(StringUtil.isNotEmpty(str)){
						if(tableoutputColumn.length()!=0){
							m4.setTableoutputColumn(str.substring(1, str.length()));
						}else{
							m4.setTableoutputColumn(str);
						}
					}else{
						m4.setTableoutputColumn("");
					}
			Map map = new HashMap<Integer,String>();
			for (String key : operatorMap.keySet()) {
				map.put(operatorMap.get(key),key);
			}
			if(m4.getFilterLeftvalue()!=null&&
			  m4.getFilterRightvalue()!=null&&
			  m4.getFilterFunction()!=null){
				m4.setFilterLeftvalue(m4.getFilterLeftvalue()+map.get(m4.getFilterFunction())+m4.getFilterRightvalue());
			}
			if(m4.getMergejoinmeta()!=null&&m4.getMergejoinmeta1()!=null){
				m4.setMergejoinmeta(m4.getMergejoinmeta()+"="+m4.getMergejoinmeta1());
			}
			m4.setMecp(mecp);
			return  new PublicResult<>(PublicResultConstant.SUCCESS,m4);
		} catch (Exception e) {
			e.printStackTrace();
			return  new PublicResult<>(PublicResultConstant.FAILED,e.getMessage());
		}
		
	}
	
	/*模板5*/
	@RequestMapping(value = "/alterModel5",method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	//@Log(description = "API接口:/template/alterModel5")
	public Object alterModel5(HttpServletRequest request,@RequestBody MonitorEtlParamM5 paramM5){
		try {
				Date currentDate = new Date();//生成当前日期
				
				Map<String, Object> result = new HashMap<>();//返回的数据(配置id和模板id)
				
				
				User user = getLoginUser(request);
				/*if(user.getId()==null){
					return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
				}*/
				MonitorEtlControlParam mecp = paramM5.getMecp();
				
				if(paramM5.getIndexId()!=null){
					mecp.setIndexId(paramM5.getIndexId());
				}
				mecp.setCreateBy(user.getId());
				paramM5.setCreateBy(user.getId());
				
				mecp.setCreateDate(currentDate);
				paramM5.setCreateDate(currentDate);
				
				mecp.setUpdateBy(user.getId());
				paramM5.setUpdateBy(user.getId());
				
				mecp.setUpdateDate(currentDate);
				paramM5.setUpdateDate(currentDate);
				
				mecp.setEtlTypeId("TYPE0005");
				paramM5.setTypeId("TYPE0005");
				paramM5.setTableoutputDatabase(tableoutputDatabase);
				paramM5.setTableoutputTable(tableoutputTable);
				paramM5.setStreamoutputColumn(tableoutputColumn);
				if(StringUtil.isNotEmpty(paramM5.getTableoutputColumn())){//表输出字段
					String realOutputColumn = "";
					for (String outputColumn : paramM5.getTableoutputColumn().split(",")) {
						realOutputColumn += "@#"+outputColumn;
					}
					paramM5.setStreamoutputColumn(tableoutputColumn+realOutputColumn);
				}
				paramM5.setTableoutputColumn(tableoutputColumn);
				if(paramM5.getMecp().getStartDate()!=null){
					mecp.setStartDate(paramM5.getMecp().getStartDate());
				}
				if(paramM5.getMecp().getEndDate()!=null){
					mecp.setEndDate(paramM5.getMecp().getEndDate());
				}
				if(paramM5.getMecp().getStartTime()!=null){
					mecp.setStartTime(paramM5.getMecp().getStartTime());
				}else{
					mecp.setStartTime(new Date("00:00:00"));
				}
				if(paramM5.getMecp().getEndTime()!=null){
					mecp.setEndTime(paramM5.getMecp().getEndTime());
				}else{
					mecp.setEndTime(new Date("00:00:00"));
				}
				if(paramM5.getMecp().getIntervalType()!=null){
					mecp.setIntervalType(paramM5.getMecp().getIntervalType());
				}
				if(StringUtil.isEmpty(paramM5.getModuleId())){
					String configId = GenerationSequenceUtil.getUUID();//随机生成id
					String moduleId = GenerationSequenceUtil.getUUID();//随机生成id
					paramM5.setModuleId(moduleId);
					mecp.setId(configId);
					mecp.setModuleId(moduleId); 
					
					result.put("moduleId",moduleId);
					if(iMonitorService.addModel5One(paramM5,mecp)){//若返回true
						return new PublicResult<>(PublicResultConstant.SUCCESS,result);
					}else{
						return new PublicResult<>(PublicResultConstant.ERROR,"添加失败!");
					}
				}else{
					mecp.setModuleId(paramM5.getModuleId());
					result.put("moduleId",paramM5.getModuleId());
					if(iMonitorService.updateParamM5(paramM5,mecp)){
						return new PublicResult<>(PublicResultConstant.SUCCESS,result);
					}else{
						return new PublicResult<>(PublicResultConstant.ERROR,"修改失败!");
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
				return new PublicResult<>(PublicResultConstant.FAILED,"发生未知错误,添加失败!");
			}
		
	}
	
	/**
	 * 根据模板id和配置id获取回显信息
	 * @param request
	 * @param configId
	 * @param moduleId
	 * @return
	 */
	@RequestMapping(value = "/getModel5",method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	//@Log(description = "API接口:/template/getModel5")
	public Object getModel5(HttpServletRequest request,String moduleId){
		try {
			User user = getLoginUser(request);
			/*if(user.getId()==null){
				return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
			}*/
			MonitorEtlControlParam mecp = iMonitorService.getConfigBymoduleId(moduleId);
			MonitorEtlParamM5 m5 = iMonitorService.getModel5ById(moduleId);
			m5.setMecp(mecp);
			String str = m5.getStreamoutputColumn().
					replace(tableoutputColumn, "").replace("#", "")
					.replace("@", ",");
					if(StringUtil.isNotEmpty(str)){
						if(tableoutputColumn.length()!=0){
							m5.setTableoutputColumn(str.substring(1, str.length()));
						}else{
							m5.setTableoutputColumn(str);
						}
					}else{
						m5.setTableoutputColumn("");
					}
			return  new PublicResult<>(PublicResultConstant.SUCCESS,m5);
		} catch (Exception e) {
			e.printStackTrace();
			return  new PublicResult<>(PublicResultConstant.FAILED,e.getMessage());
		}
		
	}
	
	/*模板6*/
	@RequestMapping(value = "/alterModel6",method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	//@Log(description = "API接口:/template/alterModel6")
	public Object alterModel6(HttpServletRequest request,@RequestBody MonitorEtlParamM6 paramM6){
		try {
				Date currentDate = new Date();//生成当前日期
				
				Map<String, Object> result = new HashMap<>();//返回的数据(配置id和模板id)
				
				
				User user = getLoginUser(request);
				/*if(user.getId()==null){
					return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
				}*/
				MonitorEtlControlParam mecp = paramM6.getMecp();
				if(paramM6.getIndexId()!=null){
					mecp.setIndexId(paramM6.getIndexId());
				}
				
				mecp.setCreateBy(user.getId());
				paramM6.setCreateBy(user.getId());
				
				mecp.setCreateDate(currentDate);
				paramM6.setCreateDate(currentDate);
				
				mecp.setUpdateBy(user.getId());
				paramM6.setUpdateBy(user.getId());
				
				mecp.setUpdateDate(currentDate);
				paramM6.setUpdateDate(currentDate);
				
				mecp.setEtlTypeId("TYPE0006");
				paramM6.setTypeId("TYPE0006");
				if(paramM6.getMecp().getStartDate()!=null){
					mecp.setStartDate(paramM6.getMecp().getStartDate());
				}
				if(paramM6.getMecp().getEndDate()!=null){
					mecp.setEndDate(paramM6.getMecp().getEndDate());
				}
				if(paramM6.getMecp().getStartTime()!=null){
					mecp.setStartTime(paramM6.getMecp().getStartTime());
				}else{
					mecp.setStartTime(new Date("00:00:00"));
				}
				if(paramM6.getMecp().getEndTime()!=null){
					mecp.setEndTime(paramM6.getMecp().getEndTime());
				}else{
					mecp.setEndTime(new Date("00:00:00"));
				}
				if(paramM6.getMecp().getIntervalType()!=null){
					mecp.setIntervalType(paramM6.getMecp().getIntervalType());
				}
				if(StringUtil.isEmpty(paramM6.getModuleId())){
					String configId = GenerationSequenceUtil.getUUID();//随机生成id
					String moduleId = GenerationSequenceUtil.getUUID();//随机生成id
					paramM6.setModuleId(moduleId);
					mecp.setId(configId);
					mecp.setModuleId(moduleId); 
					
					result.put("moduleId",moduleId);
					if(iMonitorService.addModel6One(paramM6,mecp)){//若返回true
						return new PublicResult<>(PublicResultConstant.SUCCESS,result);
					}else{
						return new PublicResult<>(PublicResultConstant.ERROR,"添加失败!");
					}
				}else{
					mecp.setModuleId(paramM6.getModuleId());
					paramM6.setModuleId(paramM6.getModuleId());
					result.put("moduleId",paramM6.getModuleId());
					if(iMonitorService.updateParamM6(paramM6,mecp)){
						return new PublicResult<>(PublicResultConstant.SUCCESS,result);
					}else{
						return new PublicResult<>(PublicResultConstant.ERROR,"修改失败!");
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
				return new PublicResult<>(PublicResultConstant.FAILED,"发生未知错误,添加失败!");
			}
		
	}
	
	/**
	 * 根据模板id和配置id获取回显信息
	 * @param request
	 * @param configId
	 * @param moduleId
	 * @return
	 */
	@RequestMapping(value = "/getModel6",method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	//@Log(description = "API接口:/template/getModel6")
	public Object getModel6(HttpServletRequest request,String moduleId){
		try {
			User user = getLoginUser(request);
			/*if(user.getId()==null){
				return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
			}*/
			MonitorEtlControlParam mecp = iMonitorService.getConfigBymoduleId(moduleId);
			MonitorEtlParamM6 m6 = iMonitorService.getModel6ById(moduleId);
			m6.setMecp(mecp);
			return  new PublicResult<>(PublicResultConstant.SUCCESS,m6);
		} catch (Exception e) {
			e.printStackTrace();
			return  new PublicResult<>(PublicResultConstant.FAILED,e.getMessage());
		}
		
	}
	
	/**
	 * 根据指标编码获取模板控制参数信息
	 */
	@ApiOperation(value = "根据指标编码获取模板控制参数信息", notes = "根据指标编码获取模板控制参数信息", httpMethod = "GET")
	@RequestMapping(value = "/getMonitorListByIndexId",method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	public Object getMonitorListByIndexId(HttpServletRequest request,@RequestParam(value="indexId") String indexId){
		try {
			User user = getLoginUser(request);
			/*if(user.getId()==null){
				return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
			}*/
			List<MonitorEtlControlParam> list = iMonitorService.getMonitorListByIndexId(indexId);
			return new PublicResult<>(PublicResultConstant.SUCCESS,list);
		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED,"参数错误!");
		}
	}
	
	/**
	 * 根据表编码查询配置的模板信息
	 */
	@ApiOperation(value = "根据表编码查询配置的模板信息", notes = "根据表编码查询配置的模板信息", httpMethod = "GET")
	@RequestMapping(value = "/getMonitorListByContentId",method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	public Object getMonitorListByContentId(HttpServletRequest request,@RequestParam(value="contentId") String contentId){
		try {
			User user = getLoginUser(request);
			/*if(user.getId()==null){
				return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
			}*/
			List<MonitorEtlControlParam> list = iMonitorService.getMonitorListByContentId(contentId);
			return new PublicResult<>(PublicResultConstant.SUCCESS,list);
		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED,"参数错误!");
		}
	}
	
	/**
	 * 根据报表查询配置的模板信息
	 */
	@ApiOperation(value = "根据报表查询配置的模板信息", notes = "根据报表查询配置的模板信息", httpMethod = "GET")
	@RequestMapping(value = "/getMonitorListByReportId",method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	public Object getMonitorListByReportId(HttpServletRequest request,@RequestParam(value="reportId") String reportId){
		try {
			User user = getLoginUser(request);
			/*if(user.getId()==null){
				return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
			}*/
			List<MonitorEtlControlParam> list = iMonitorService.getMonitorListByReportId(reportId);
			return new PublicResult<>(PublicResultConstant.SUCCESS,list);
		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED,"参数错误!");
		}
	}
}

