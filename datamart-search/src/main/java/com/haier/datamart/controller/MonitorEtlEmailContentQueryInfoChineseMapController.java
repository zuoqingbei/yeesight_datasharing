package com.haier.datamart.controller;

import java.util.Date;

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
import com.haier.datamart.entity.MonitorEtlEmailContentQueryInfoChineseMap;
import com.haier.datamart.service.IMonitorEtlEmailContentQueryInfoChineseMapService;
import com.haier.datamart.utils.UUIDUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 *
 * @author ZhangJiang123
 * @date 2018-12-19
 * @todo 路由
 */
@RestController
@RequestMapping("/api/monitorEtlEmailContentQueryInfoChineseMap")
@Api(value = "",description=" @author ZhangJiang123")
public class MonitorEtlEmailContentQueryInfoChineseMapController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(MonitorEtlEmailContentQueryInfoChineseMapController.class);

    @Autowired
    public IMonitorEtlEmailContentQueryInfoChineseMapService iMonitorEtlEmailContentQueryInfoChineseMapService;

    /**
     * @date   2018-12-19
     * @author ZhangJiang123
     * @todo   新增
     */
  	@ApiOperation(value = "新增", notes = "新增", httpMethod = "POST")
	@RequestMapping(value = "/add", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<MonitorEtlEmailContentQueryInfoChineseMap> add(HttpServletRequest request,@RequestBody MonitorEtlEmailContentQueryInfoChineseMap entity) {
		try {
			if(StringUtils.isBlank(entity.getId())){
				//新增
				entity.setId(UUIDUtils.getUuid());
				entity.setCreateDate(new Date());
				entity.setCreateBy(getLoginUser(request).getId());
				if(iMonitorEtlEmailContentQueryInfoChineseMapService.insert(entity)){
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
     * @date   2018-12-19
     * @author ZhangJiang123
     * @todo   删除
     */
  	@ApiOperation(value = "删除", notes = "删除", httpMethod = "POST")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<MonitorEtlEmailContentQueryInfoChineseMap> delete(HttpServletRequest request,@PathVariable("id") String id) {
		try {
			MonitorEtlEmailContentQueryInfoChineseMap entity=new MonitorEtlEmailContentQueryInfoChineseMap();
			entity.setId(id);
			entity.setDelFlag(DEL_FLAG);
			entity.setUpdateDate(new Date());
			entity.setUpdateBy(getLoginUser(request).getId());
			 if(iMonitorEtlEmailContentQueryInfoChineseMapService.updateById(entity)){
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
     * @date   2018-12-19
     * @author ZhangJiang123
     * @todo   更新
     */
  	@ApiOperation(value = "更新", notes = "更新", httpMethod = "POST")
	@RequestMapping(value = "/update", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<MonitorEtlEmailContentQueryInfoChineseMap> update(HttpServletRequest request,MonitorEtlEmailContentQueryInfoChineseMap entity) {
		try {
			if(entity!=null&&StringUtils.isNotBlank(entity.getId())){
				//更新
				entity.setUpdateDate(new Date());
				entity.setUpdateBy(getLoginUser(request).getId());
				if(iMonitorEtlEmailContentQueryInfoChineseMapService.updateById(entity)){
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
     * @date   2018-12-19
     * @author ZhangJiang123
     * @todo   查询单个
     */
  	@ApiOperation(value = "查询单个", notes = "查询单个", httpMethod = "GET")
  	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET ,produces = { "application/json;charset=UTF-8" })
  	public PublicResult<MonitorEtlEmailContentQueryInfoChineseMap> get(HttpServletRequest request,@PathVariable("id") String id) {
  		MonitorEtlEmailContentQueryInfoChineseMap entity=null;
  		try {
  			EntityWrapper<MonitorEtlEmailContentQueryInfoChineseMap> wrapper = new EntityWrapper<MonitorEtlEmailContentQueryInfoChineseMap>();
  			wrapper.where("del_flag={0}", UN_DEL_FLAG);
  			wrapper.eq("id", id);
  			entity=iMonitorEtlEmailContentQueryInfoChineseMapService.selectOne(wrapper);
  			return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
  	}
	
    /**
     * @date   2018-12-19
     * @author ZhangJiang123
     * @todo   分页查询
     */
  	@ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "GET")
  	@RequestMapping(value = "/list", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
    public Object list(MonitorEtlEmailContentQueryInfoChineseMap entity,@RequestParam(value="pageNum",required = false,defaultValue="1") Integer pageNum,
			@RequestParam(value="pageSize",required = false,defaultValue="10") Integer pageSize,HttpServletRequest request) {
		try {
			EntityWrapper<MonitorEtlEmailContentQueryInfoChineseMap> wrapper = searchWrapper(request, entity);
			PageHelper.startPage(pageNum, pageSize);
			int cu=(pageNum-1)*pageSize;
			Page<MonitorEtlEmailContentQueryInfoChineseMap> page=new Page<MonitorEtlEmailContentQueryInfoChineseMap>(cu, pageSize);
			page = iMonitorEtlEmailContentQueryInfoChineseMapService.selectPage(page,wrapper);
			page.setTotal(iMonitorEtlEmailContentQueryInfoChineseMapService.selectCount(wrapper));
			return new PublicResult<>(PublicResultConstant.SUCCESS, page);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(),null);
		}

	}
    /**
     * @date   2018年9月25日 下午5:36:10
     * @author ZhangJiang123
     * @todo   构建查询条件-以后扩展
     */
    private EntityWrapper<MonitorEtlEmailContentQueryInfoChineseMap> searchWrapper(HttpServletRequest request, MonitorEtlEmailContentQueryInfoChineseMap entity) {
		EntityWrapper<MonitorEtlEmailContentQueryInfoChineseMap> wrapper = new EntityWrapper<MonitorEtlEmailContentQueryInfoChineseMap>();
		wrapper.where("del_flag={0}", UN_DEL_FLAG);
		if(getLoginUser(request)!=null&&StringUtils.isNotBlank(getLoginUser(request).getId())){
			if(!isAdmin(request))
			 wrapper.eq("create_by", getLoginUser(request).getId());
		}
				//根据表记录id模糊查询
		if(entity.getId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getId()))){
			wrapper.like("id", String.valueOf(entity.getId()));
		}
				//根据邮件内容记录id模糊查询
		if(entity.getEmailContentId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getEmailContentId()))){
			wrapper.like("email_content_id", String.valueOf(entity.getEmailContentId()));
		}
				//根据英文名模糊查询
		if(entity.getEnglishName()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getEnglishName()))){
			wrapper.like("english_name", String.valueOf(entity.getEnglishName()));
		}
				//根据中文名模糊查询
		if(entity.getChineseName()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getChineseName()))){
			wrapper.like("chinese_name", String.valueOf(entity.getChineseName()));
		}
				//根据字段顺序模糊查询
		if(entity.getFieldNo()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getFieldNo()))){
			wrapper.like("field_no", String.valueOf(entity.getFieldNo()));
		}
				//根据创建者模糊查询
		if(entity.getCreateBy()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getCreateBy()))){
			wrapper.like("create_by", String.valueOf(entity.getCreateBy()));
		}
				//根据创建时间模糊查询
		if(entity.getCreateDate()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getCreateDate()))){
			wrapper.like("create_date", String.valueOf(entity.getCreateDate()));
		}
				//根据更新者模糊查询
		if(entity.getUpdateBy()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getUpdateBy()))){
			wrapper.like("update_by", String.valueOf(entity.getUpdateBy()));
		}
				//根据更新时间模糊查询
		if(entity.getUpdateDate()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getUpdateDate()))){
			wrapper.like("update_date", String.valueOf(entity.getUpdateDate()));
		}
				//根据备注信息模糊查询
		if(entity.getRemarks()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getRemarks()))){
			wrapper.like("remarks", String.valueOf(entity.getRemarks()));
		}
				//根据删除标记模糊查询
		if(entity.getDelFlag()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getDelFlag()))){
			wrapper.like("del_flag", String.valueOf(entity.getDelFlag()));
		}
				wrapper.orderBy("create_date", true);
		System.out.println(wrapper.originalSql());
		return wrapper;
	}
}

