package com.haier.datamart.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.github.pagehelper.PageHelper;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.entity.AdminDataContent;
import com.haier.datamart.entity.AdminDataContentDetail;
import com.haier.datamart.entity.AdminDataSqlExceLogs;
import com.haier.datamart.entity.AdminDatasourceConfig;
import com.haier.datamart.exception.BusinessException;
import com.haier.datamart.service.IAdminDataContentDetailService;
import com.haier.datamart.service.IAdminDataContentService;
import com.haier.datamart.service.IAdminDataSqlExceLogsService;
import com.haier.datamart.service.IAdminDatasourceConfigService;
import com.haier.datamart.utils.JdbcUtil;
import com.haier.datamart.utils.RexUtils;
import com.haier.datamart.utils.UUIDUtils;
import com.haier.datamart.utils.jdbc.JdbcDataQuery;
import com.haier.datamart.utils.jdbc.handle.CountResultHandle;
import com.haier.datamart.utils.jdbc.handle.MapResultHandle;
/**
 *
 * @author zuoqb123
 * @date 2019-01-02
 * @todo sql执行日志路由
 */
@RestController
@RequestMapping("/api/adminDataSqlExceLogs")
@Api(value = "sql执行日志",description="sql执行日志 @author zuoqb123")
public class AdminDataSqlExceLogsController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(AdminDataSqlExceLogsController.class);

    @Autowired
    public IAdminDataSqlExceLogsService iAdminDataSqlExceLogsService;
    @Autowired
	private IAdminDatasourceConfigService configService;
	@Autowired
	private IAdminDataContentService contentService;
	@Autowired
	private IAdminDataContentDetailService detailService;
	
	
	@ApiOperation(value = "执行sql", notes = "执行sql", httpMethod = "POST")
	@RequestMapping(value = "/execeSql", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public Object execeSql(HttpServletRequest request,
			@RequestParam(value="dataSourceId",required = true) String dataSourceId,
			@RequestParam(value="contentId",required = true) String contentId,
			@RequestParam(value="sql",required = true) String sql,
			@RequestParam(value="userId",required = true) String userId,
			@RequestParam(value="params",required = false) String params) {
		/*User user=getUserByUid(userId);
		if(user==null){
			return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
		}*/
		Map<String,Object> datas=new HashMap<String, Object>();
		/**
    	 * 开始处理SQL
    	 */
    	//解析SQL中的${}与#{}
    	//匹配所有${}与匹配所有#{}正则表达式
    	String rexg="\\#\\{([^\\}]+)\\}|\\$\\{([^\\}]+)\\}";
    	//按照顺序查找出所有# $ 比如  [#{time}, #{cbkCode}, ${startIndex}, ${pageSize}]
    	List<String> matcher=RexUtils.getString(sql, rexg);
    	PublicResult<Map<String,String>> dealParamsResult=JdbcUtil.dealParams(params);
    	PublicResult<Map<String, List<Map<String, Object>>>> formatSqlResult= JdbcUtil.formatSql(sql, matcher, dealParamsResult);
    	if(!PublicResultConstant.SUCCESS.msg.equals(formatSqlResult.getMsg())){
    		return formatSqlResult;
    	}
    	//将SQL中所有${}与所有#{}替换为？
    	sql=RexUtils.getReplace(sql, rexg,"?");

		AdminDatasourceConfig config = configService.get(dataSourceId);
		if(config==null){
			return new PublicResult<>(PublicResultConstant.PARAM_ERROR,"数据源无效，请配置数据源！", null);
		}
		Connection conn = JdbcUtil.getConn(config);
		if(conn==null){
			return new PublicResult<>(PublicResultConstant.FAILED,"数据库连接失败！",null);
		}
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
	    AdminDataSqlExceLogs entity=new AdminDataSqlExceLogs();
        entity.setId(UUIDUtils.getUuid());
		entity.setCreateDate(new Date());
		entity.setCreateBy(userId);
		entity.setDataSourceId(dataSourceId);
		entity.setContentId(contentId);
		entity.setParams(params);
		try {

			//将where后面的‘与’替换
			Pattern p=Pattern.compile("where",Pattern.CASE_INSENSITIVE);
			Matcher m=p.matcher(sql);
			while(m.find()){
	            String whereStr=m.group();
	            String[] splits=sql.split(whereStr);
	            sql=splits[0];
	            for(int x=0;x<splits.length;x++){
	            	if(x>0){
	            		sql+=whereStr+splits[x].replaceAll("‘", "'").replaceAll("’", "'");
	            	}
	            }
	        }
			entity.setSqls(sql);
			pstmt = conn.prepareStatement(sql);
			//设置参数 外层循环
			for(int index=0;index<matcher.size();index++){
				String matcherKey=matcher.get(index);
				String variableValue = JdbcUtil.getParamValueByKey(dealParamsResult, matcherKey);
				if(matcherKey.trim().startsWith("#")){
					//#替换sql语句中的参数，只能是字符串
					pstmt.setString(index+1, variableValue);
				}
				if(matcherKey.trim().startsWith("$")){
					//$替换sql语句中的内容，只能是数字
					pstmt.setInt(index+1, Integer.valueOf(variableValue));
				}
			}
			resultSet = pstmt.executeQuery();
			List<Map<String,Object>> result = new ArrayList<>();
			//获取列名
            List<String> names= new ArrayList<>();
            System.out.println(resultSet.getMetaData().getColumnCount());
            for(int i=1;i<=resultSet.getMetaData().getColumnCount();i++){
                names.add(resultSet.getMetaData().getColumnLabel(i));
            }
            //构造数据
            while (resultSet.next()){
                Map<String,Object> item = new java.util.LinkedHashMap<>();
                for(String name:names){
                    item.put(name,resultSet.getObject(name));
                }
                result.add(item);
            }
            datas.put("columns", names);
            datas.put("values", result);
			entity.setExcStatus("1");
			iAdminDataSqlExceLogsService.insert(entity);
            return datas;
		} catch (Exception e) {
			e.printStackTrace();
			entity.setExcStatus("0");
			iAdminDataSqlExceLogsService.insert(entity);
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(),null);
		}finally {
			try {
				JdbcUtil.close(conn, pstmt, resultSet);
				if(conn!=null){
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(),null);
			}
		}
	}
	
	@ApiOperation(value = "查询表中数据", notes = "查询表中数据", httpMethod = "POST")
	@RequestMapping(value = "/selectTableDatas", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public Object selectTableDatas(HttpServletRequest request,
			@RequestParam(value="dataSourceId",required = true) String dataSourceId,
			@RequestParam(value="contentId",required = true) String contentId,
			@RequestParam(value="userId",required = true) String userId,
			@RequestParam(value="pageNum",required = true) Integer pageNum,
			Integer pageSize) {
		/*User user=getUserByUid(userId);
		if(user==null){
			return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
		}*/
		Map<String,Object> datas=new HashMap<String, Object>();
    	

		AdminDatasourceConfig config = configService.get(dataSourceId);
		if(config==null){
			return new PublicResult<>(PublicResultConstant.PARAM_ERROR,"数据源无效，请配置数据源！", null);
		}
		AdminDataContent adminDataContent=contentService.get(contentId);
		if(adminDataContent==null){
			return new PublicResult<>(PublicResultConstant.PARAM_ERROR,"表不存在！", null);
		}
		List<AdminDataContentDetail> columns = detailService.getAllBy(contentId);
		datas.put("columns", columns);
		try {
			  if(pageSize == null){
		            pageSize = 20;
		        }
		        StringBuffer sb  = new StringBuffer("SELECT * from "+adminDataContent.getTableName() );
		        String sqlCount = "select count(*) count_ from ( "+sb.toString()+" ) a53f98f";//数量的sql
		        Integer start = (pageNum-1)*pageSize;
		        sb.append(" limit "+start+","+pageSize);
		        String sqlContent = sb.toString();
		        try(JdbcDataQuery jdbcDataQuery = JdbcDataQuery.createJdbcDataQuery(config.getDbUrl(),config.getDbName(),config.getDbPassword())){
		           Integer allCount = jdbcDataQuery.query(new CountResultHandle<>(),sqlCount);
		            List<Map<String,Object>> resultList =  jdbcDataQuery.query(new MapResultHandle<>(),sqlContent.toString());
		            datas.put("allCount",allCount);
		            datas.put("list",resultList);
		            return datas;
		        }catch (Exception e){
		            throw  new BusinessException(e);
		        }
		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(),null);
		}
	}
	
	

    /**
     * @date   2019-01-02
     * @author zuoqb123
     * @todo   新增sql执行日志
     */
  	@ApiOperation(value = "新增sql执行日志", notes = "新增sql执行日志", httpMethod = "POST")
	@RequestMapping(value = "/add", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<AdminDataSqlExceLogs> add(HttpServletRequest request,@RequestBody AdminDataSqlExceLogs entity) {
		try {
			if(StringUtils.isBlank(entity.getId())){
				//新增
				entity.setId(UUIDUtils.getUuid());
				entity.setCreateDate(new Date());
				entity.setCreateBy(getLoginUser(request).getId());
				if(iAdminDataSqlExceLogsService.insert(entity)){
					return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
				}else{
					return new PublicResult<>(PublicResultConstant.ERROR, null);
				}
			}else{
				return new PublicResult<>(PublicResultConstant.PARAM_ERROR, "新增主键必须为空!",null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
	}
    
    /**
     * @date   2019-01-02
     * @author zuoqb123
     * @todo   删除sql执行日志
     */
  	@ApiOperation(value = "删除sql执行日志", notes = "删除sql执行日志", httpMethod = "POST")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<AdminDataSqlExceLogs> delete(HttpServletRequest request,@PathVariable("id") String id) {
		try {
			AdminDataSqlExceLogs entity=new AdminDataSqlExceLogs();
			entity.setId(id);
			entity.setDelFlag(DEL_FLAG);
			entity.setUpdateDate(new Date());
			entity.setUpdateBy(getLoginUser(request).getId());
			 if(iAdminDataSqlExceLogsService.updateById(entity)){
				 return new PublicResult<>(PublicResultConstant.SUCCESS, null);
			 }else{
				 return new PublicResult<>(PublicResultConstant.ERROR, null);
			 }
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
	}
	
	 /**
     * @date   2019-01-02
     * @author zuoqb123
     * @todo   更新sql执行日志
     */
  	@ApiOperation(value = "更新sql执行日志", notes = "更新sql执行日志", httpMethod = "POST")
	@RequestMapping(value = "/update", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<AdminDataSqlExceLogs> update(HttpServletRequest request,AdminDataSqlExceLogs entity) {
		try {
			if(entity!=null&&StringUtils.isNotBlank(entity.getId())){
				//更新
				entity.setUpdateDate(new Date());
				entity.setUpdateBy(getLoginUser(request).getId());
				if(iAdminDataSqlExceLogsService.updateById(entity)){
					return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
				}else{
					return new PublicResult<>(PublicResultConstant.ERROR, null);
				}
			}else{
				return new PublicResult<>(PublicResultConstant.PARAM_ERROR, "修改主键不能为空!",null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
	}
    
    
    /**
     * @date   2019-01-02
     * @author zuoqb123
     * @todo   查询单个sql执行日志
     */
  	@ApiOperation(value = "查询单个sql执行日志", notes = "查询单个sql执行日志", httpMethod = "GET")
  	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET ,produces = { "application/json;charset=UTF-8" })
  	public PublicResult<AdminDataSqlExceLogs> get(HttpServletRequest request,@PathVariable("id") String id) {
  		AdminDataSqlExceLogs entity=null;
  		try {
  			EntityWrapper<AdminDataSqlExceLogs> wrapper = new EntityWrapper<AdminDataSqlExceLogs>();
  			wrapper.where("del_flag={0}", UN_DEL_FLAG);
  			wrapper.eq("id", id);
  			entity=iAdminDataSqlExceLogsService.selectOne(wrapper);
  			return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
  	}
	
    /**
     * @date   2019-01-02
     * @author zuoqb123
     * @todo   分页查询sql执行日志
     */
  	@ApiOperation(value = "分页查询sql执行日志", notes = "分页查询sql执行日志", httpMethod = "GET")
  	@RequestMapping(value = "/list", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
    public Object list(AdminDataSqlExceLogs entity,@RequestParam(value="pageNum",required = false,defaultValue="1") Integer pageNum,
			@RequestParam(value="pageSize",required = false,defaultValue="10") Integer pageSize,HttpServletRequest request) {
		try {
			EntityWrapper<AdminDataSqlExceLogs> wrapper = searchWrapper(request, entity);
			PageHelper.startPage(pageNum, pageSize);
			int cu=(pageNum-1)*pageSize;
			Page<AdminDataSqlExceLogs> page=new Page<AdminDataSqlExceLogs>(cu, pageSize);
			page = iAdminDataSqlExceLogsService.selectPage(page,wrapper);
			page.setTotal(iAdminDataSqlExceLogsService.selectCount(wrapper));
			return new PublicResult<>(PublicResultConstant.SUCCESS, page);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(),null);
		}

	}
    /**
     * @date   2018年9月25日 下午5:36:10
     * @author zuoqb123
     * @todo   构建查询条件-以后扩展
     */
    private EntityWrapper<AdminDataSqlExceLogs> searchWrapper(HttpServletRequest request, AdminDataSqlExceLogs entity) {
		EntityWrapper<AdminDataSqlExceLogs> wrapper = new EntityWrapper<AdminDataSqlExceLogs>();
		wrapper.where("del_flag={0}", UN_DEL_FLAG);
		if(getLoginUser(request)!=null&&StringUtils.isNotBlank(getLoginUser(request).getId())){
			if(!isAdmin(request))
			 wrapper.eq("create_by", getLoginUser(request).getId());
		}
				//根据编号模糊查询
		if(entity.getId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getId()))){
			wrapper.like("id", String.valueOf(entity.getId()));
		}
				//根据数据源编码模糊查询
		if(entity.getDataSourceId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getDataSourceId()))){
			wrapper.like("data_source_id", String.valueOf(entity.getDataSourceId()));
		}
				//根据表编码模糊查询
		if(entity.getContentId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getContentId()))){
			wrapper.like("content_id", String.valueOf(entity.getContentId()));
		}
				//根据模糊查询
		if(entity.getSqls()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getSqls()))){
			wrapper.like("sqls", String.valueOf(entity.getSqls()));
		}
				//根据模糊查询
		if(entity.getParams()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getParams()))){
			wrapper.like("params", String.valueOf(entity.getParams()));
		}
				//根据执行结果 0-失败 1-成功模糊查询
		if(entity.getExcStatus()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getExcStatus()))){
			wrapper.like("exc_status", String.valueOf(entity.getExcStatus()));
		}
				//根据创建人模糊查询
		if(entity.getCreateBy()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getCreateBy()))){
			wrapper.like("create_by", String.valueOf(entity.getCreateBy()));
		}
				//根据创建时间模糊查询
		if(entity.getCreateDate()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getCreateDate()))){
			wrapper.like("create_date", String.valueOf(entity.getCreateDate()));
		}
				//根据更新人模糊查询
		if(entity.getUpdateBy()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getUpdateBy()))){
			wrapper.like("update_by", String.valueOf(entity.getUpdateBy()));
		}
				//根据更新时间模糊查询
		if(entity.getUpdateDate()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getUpdateDate()))){
			wrapper.like("update_date", String.valueOf(entity.getUpdateDate()));
		}
				//根据备注模糊查询
		if(entity.getRemarks()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getRemarks()))){
			wrapper.like("remarks", String.valueOf(entity.getRemarks()));
		}
				//根据模糊查询
		if(entity.getDelFlag()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getDelFlag()))){
			wrapper.like("del_flag", String.valueOf(entity.getDelFlag()));
		}
				wrapper.orderBy("create_date", true);
		System.out.println(wrapper.originalSql());
		return wrapper;
	}
}

