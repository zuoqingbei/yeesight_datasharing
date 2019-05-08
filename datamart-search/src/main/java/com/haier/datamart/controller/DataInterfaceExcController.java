package com.haier.datamart.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Date;
import java.util.List;

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
import com.haier.datamart.entity.DataInterfaceExc;
import com.haier.datamart.entity.User;
import com.haier.datamart.service.IDataInterfaceExcService;
/**
 *
 * @author zuoqb123
 * @date 2018-12-06
 * @todo 路由
 */
@RestController
@RequestMapping("/api/dataInterfaceExc")
@Api(value = "",description=" @author zuoqb123")
public class DataInterfaceExcController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(DataInterfaceExcController.class);

    @Autowired
    public IDataInterfaceExcService iDataInterfaceExcService;
    
    
    
    /**
	 * 
	 * @time   2018年12月7日 下午7:36:30
	 * @author zuoqb
	 * @todo   根据数据源+表查询接口
	 */
    
    @ApiOperation(value = " 根据数据源+表查询接口", notes = " 根据数据源+表查询接口", httpMethod = "GET")
    @RequestMapping(value = "/findExcByDbAndContentId", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
	public Object findExcByDbAndContentId(HttpServletRequest request,@RequestParam("dataSourceId")String dataSourceId,
			@RequestParam("contentId")String contentId,
			@RequestParam(value="pageNum",required = false,defaultValue="1") Integer pageNum,
			@RequestParam(value="pageSize",required = false,defaultValue="10") Integer pageSize,
			@RequestParam(value="userId",required = true) String userId) {
		try {
			User u=getUserByUid(userId);
			int cu=(pageNum-1)*pageSize;
			Page<DataInterfaceExc> page=new Page<DataInterfaceExc>(cu, pageSize);
			List<DataInterfaceExc> excs=iDataInterfaceExcService.findExcByDbAndContentId(dataSourceId, contentId, cu, pageSize,userId,u.isHasAdmin()+"");
			page.setRecords(excs);
			page.setTotal(iDataInterfaceExcService.findExcByDbAndContentId(dataSourceId, contentId, 0, Integer.MAX_VALUE,userId,u.isHasAdmin()+"").size());
			return new PublicResult<>(PublicResultConstant.SUCCESS, page);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
	}
    
    
    /**
   	 * 
   	 * @time   2018年12月7日 下午7:36:30
   	 * @author zuoqb
   	 * @todo   根据指标编码查询接口
   	 */
       
    @ApiOperation(value = " 根据指标编码查询接口", notes = " 根据指标编码查询接口", httpMethod = "GET")
    @RequestMapping(value = "/findExcByIndexId", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
   	public Object findExcByIndexId(HttpServletRequest request,@RequestParam("indexId")String indexId,
   			@RequestParam(value="pageNum",required = false,defaultValue="1") Integer pageNum,
   			@RequestParam(value="pageSize",required = false,defaultValue="10") Integer pageSize,
			@RequestParam(value="userId",required = true) String userId) {
   		try {
   			User u=getUserByUid(userId);
   			int cu=(pageNum-1)*pageSize;
   			Page<DataInterfaceExc> page=new Page<DataInterfaceExc>(cu, pageSize);
   			List<DataInterfaceExc> excs=iDataInterfaceExcService.findExcByIndexId(indexId, cu, pageSize,userId,u.isHasAdmin()+"");
   			page.setRecords(excs);
   			page.setTotal(iDataInterfaceExcService.findExcByIndexId(indexId, 0, Integer.MAX_VALUE,userId,u.isHasAdmin()+"").size());
   			return new PublicResult<>(PublicResultConstant.SUCCESS, page);
   		} catch (Exception e) {
   			e.printStackTrace();
   			logger.error(e.getMessage());
   			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
   		}
   	}
    
    /**
   	 * 
   	 * @time   2018年12月7日 下午7:36:30
   	 * @author zuoqb
   	 * @todo   根据报表编码查询接口
   	 */
       
    @ApiOperation(value = " 根据报表编码查询接口", notes = " 根据报表编码查询接口", httpMethod = "GET")
    @RequestMapping(value = "/findExcByReportId", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
   	public Object findExcByReportId(HttpServletRequest request,@RequestParam("reportId")String reportId,
   			@RequestParam(value="pageNum",required = false,defaultValue="1") Integer pageNum,
   			@RequestParam(value="pageSize",required = false,defaultValue="10") Integer pageSize,
			@RequestParam(value="userId",required = true) String userId) {
   		try {
   			User u=getUserByUid(userId);
   			int cu=(pageNum-1)*pageSize;
   			Page<DataInterfaceExc> page=new Page<DataInterfaceExc>(cu, pageSize);
   			List<DataInterfaceExc> excs=iDataInterfaceExcService.findExcByReportId(reportId, cu, pageSize,userId,u.isHasAdmin()+"");
   			page.setRecords(excs);
   			page.setTotal(iDataInterfaceExcService.findExcByReportId(reportId, 0, Integer.MAX_VALUE,userId,u.isHasAdmin()+"").size());
   			return new PublicResult<>(PublicResultConstant.SUCCESS, page);
   		} catch (Exception e) {
   			e.printStackTrace();
   			logger.error(e.getMessage());
   			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
   		}
   	}
    
    
    /**
     * @date   2018-12-06
     * @author zuoqb123
     * @todo   新增
     */
  	@ApiOperation(value = "新增", notes = "新增", httpMethod = "POST")
	@RequestMapping(value = "/add", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<DataInterfaceExc> add(HttpServletRequest request,@RequestBody DataInterfaceExc entity) {
		try {
			if(entity.getId()!=null){
				//新增
				entity.setCreateTime(new Date());
				entity.setCreateBy(getLoginUser(request).getId());
				if(iDataInterfaceExcService.insert(entity)){
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
     * @date   2018-12-06
     * @author zuoqb123
     * @todo   删除
     */
  	@ApiOperation(value = "删除", notes = "删除", httpMethod = "POST")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<DataInterfaceExc> delete(HttpServletRequest request,@PathVariable("id") Long id) {
		try {
			DataInterfaceExc entity=new DataInterfaceExc();
			entity.setId(id);
			entity.setDelFlag(DEL_FLAG);
			entity.setLastUpdateTime(new Date());
			entity.setLastUpdateBy(getLoginUser(request).getId());
			 if(iDataInterfaceExcService.updateById(entity)){
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
     * @date   2018-12-06
     * @author zuoqb123
     * @todo   更新
     */
  	@ApiOperation(value = "更新", notes = "更新", httpMethod = "POST")
	@RequestMapping(value = "/update", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<DataInterfaceExc> update(HttpServletRequest request,DataInterfaceExc entity) {
		try {
			if(entity.getId()!=null){
				//更新
				entity.setLastUpdateTime(new Date());
				entity.setLastUpdateBy(getLoginUser(request).getId());
				if(iDataInterfaceExcService.updateById(entity)){
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
     * @date   2018-12-06
     * @author zuoqb123
     * @todo   查询单个
     */
  	@ApiOperation(value = "查询单个", notes = "查询单个", httpMethod = "GET")
  	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET ,produces = { "application/json;charset=UTF-8" })
  	public PublicResult<DataInterfaceExc> get(HttpServletRequest request,@PathVariable("id") String id) {
  		DataInterfaceExc entity=null;
  		try {
  			EntityWrapper<DataInterfaceExc> wrapper = new EntityWrapper<DataInterfaceExc>();
  			wrapper.where("del_flag={0}", UN_DEL_FLAG);
  			wrapper.eq("id", id);
  			entity=iDataInterfaceExcService.selectOne(wrapper);
  			return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
  	}
	
    /**
     * @date   2018-12-06
     * @author zuoqb123
     * @todo   分页查询
     */
  	@ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "GET")
  	@RequestMapping(value = "/list", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
    public Object list(DataInterfaceExc entity,@RequestParam(value="pageNum",required = false,defaultValue="1") Integer pageNum,
			@RequestParam(value="pageSize",required = false,defaultValue="10") Integer pageSize,HttpServletRequest request) {
		try {
			EntityWrapper<DataInterfaceExc> wrapper = searchWrapper(request, entity);
			PageHelper.startPage(pageNum, pageSize);
			int cu=(pageNum-1)*pageSize;
			Page<DataInterfaceExc> page=new Page<DataInterfaceExc>(cu, pageSize);
			page = iDataInterfaceExcService.selectPage(page,wrapper);
			page.setTotal(iDataInterfaceExcService.selectCount(wrapper));
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
    private EntityWrapper<DataInterfaceExc> searchWrapper(HttpServletRequest request, DataInterfaceExc entity) {
		EntityWrapper<DataInterfaceExc> wrapper = new EntityWrapper<DataInterfaceExc>();
		wrapper.where("del_flag={0}", UN_DEL_FLAG);
		if(getLoginUser(request)!=null&&StringUtils.isNotBlank(getLoginUser(request).getId())){
			if(!isAdmin(request))
			 wrapper.eq("create_by", getLoginUser(request).getId());
		}
				//根据模糊查询
		if(entity.getId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getId()))){
			wrapper.like("id", String.valueOf(entity.getId()));
		}
				//根据查询指标标识模糊查询
		if(entity.getDataType()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getDataType()))){
			wrapper.like("data_type", String.valueOf(entity.getDataType()));
		}
				//根据命名空间模糊查询
		if(entity.getDataSpace()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getDataSpace()))){
			wrapper.like("data_space", String.valueOf(entity.getDataSpace()));
		}
				//根据需要执行的sql语句，需要传参的位置使用#{参数名}，动态日期类型date_dt_kpi固定参数名称。模糊查询
		if(entity.getDataSql()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getDataSql()))){
			wrapper.like("data_sql", String.valueOf(entity.getDataSql()));
		}
				//根据sql需要传入的参数，如果需要动态日期date_dt_kpi无需配置模糊查询
		if(entity.getParamId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getParamId()))){
			wrapper.like("param_id", String.valueOf(entity.getParamId()));
		}
				//根据需要历史记录放入缓存的开始时间,根据更新类型（cache_type）模糊查询
		if(entity.getBeginDate()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getBeginDate()))){
			wrapper.like("begin_date", String.valueOf(entity.getBeginDate()));
		}
				//根据需要查询的时间格式，例如：yyyyMMdd模糊查询
		if(entity.getDateFormat()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getDateFormat()))){
			wrapper.like("date_format", String.valueOf(entity.getDateFormat()));
		}
				//根据是否需要定时刷新的标识,0不刷新，1刷新模糊查询
		if(entity.getFreshFlag()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getFreshFlag()))){
			wrapper.like("fresh_flag", String.valueOf(entity.getFreshFlag()));
		}
				//根据数据缓存天数，根据更新类型（cache_type）判断模糊查询
		if(entity.getUpdateDays()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getUpdateDays()))){
			wrapper.like("update_days", String.valueOf(entity.getUpdateDays()));
		}
				//根据定任务执行时间偏移量，默认执行时间减一天。注：该设置只对定时任务有效。模糊查询
		if(entity.getTimerOffset()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getTimerOffset()))){
			wrapper.like("timer_offset", String.valueOf(entity.getTimerOffset()));
		}
				//根据Cron表达式是一个字符串配置定时任务执行时间模糊查询
		if(entity.getExcTime()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getExcTime()))){
			wrapper.like("exc_time", String.valueOf(entity.getExcTime()));
		}
				//根据放入缓存的方式，按天数存放（0），或者按开始时间存放（1）模糊查询
		if(entity.getCacheType()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getCacheType()))){
			wrapper.like("cache_type", String.valueOf(entity.getCacheType()));
		}
				//根据横竖数据格式转换，默认纵向排列（V：垂直排列，H水平排列）模糊查询
		if(entity.getTransformData()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getTransformData()))){
			wrapper.like("transform_data", String.valueOf(entity.getTransformData()));
		}
				//根据创建主体模糊查询
		if(entity.getCreateBy()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getCreateBy()))){
			wrapper.like("create_by", String.valueOf(entity.getCreateBy()));
		}
				//根据创建时间模糊查询
		if(entity.getCreateTime()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getCreateTime()))){
			wrapper.like("create_time", String.valueOf(entity.getCreateTime()));
		}
				//根据最后更新主体模糊查询
		if(entity.getLastUpdateBy()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getLastUpdateBy()))){
			wrapper.like("last_update_by", String.valueOf(entity.getLastUpdateBy()));
		}
				//根据最后更新时间模糊查询
		if(entity.getLastUpdateTime()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getLastUpdateTime()))){
			wrapper.like("last_update_time", String.valueOf(entity.getLastUpdateTime()));
		}
				//根据备注模糊查询
		if(entity.getRemark()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getRemark()))){
			wrapper.like("remark", String.valueOf(entity.getRemark()));
		}
				//根据对应datasourceid 可以为空模糊查询
		if(entity.getDataSourceId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getDataSourceId()))){
			wrapper.like("dataSourceId", String.valueOf(entity.getDataSourceId()));
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

