package com.haier.datamart.controller;

import org.springframework.stereotype.Controller;

import com.haier.datamart.controller.BaseController;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import com.haier.datamart.service.IMailHeaderModuleService;
import com.haier.datamart.entity.MailHeaderModule;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.haier.datamart.utils.UUIDUtils;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;

import org.springframework.web.bind.annotation.RequestBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 *
 * @author zuoqb123
 * @date 2019-01-03
 * @todo 邮件抬头预设置语句路由
 */
@RestController
@RequestMapping("/api/mailHeaderModule")
@Api(value = "邮件抬头预设置语句",description="邮件抬头预设置语句 @author zuoqb123")
public class MailHeaderModuleController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(MailHeaderModuleController.class);

    @Autowired
    public IMailHeaderModuleService iMailHeaderModuleService;

    /**
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   新增邮件抬头预设置语句
     */
  	@ApiOperation(value = "新增邮件抬头预设置语句", notes = "新增邮件抬头预设置语句", httpMethod = "POST")
	@RequestMapping(value = "/add", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<MailHeaderModule> add(HttpServletRequest request, MailHeaderModule entity) {
		try {
			if(StringUtils.isBlank(entity.getId())){
				//新增
				//entity.setCreateBy(getLoginUser(request).getId());
				if(iMailHeaderModuleService.saveOrUpdate(entity)){
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
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   删除邮件抬头预设置语句
     */
  	@ApiOperation(value = "删除邮件抬头预设置语句", notes = "删除邮件抬头预设置语句", httpMethod = "POST")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<MailHeaderModule> delete(HttpServletRequest request,@PathVariable("id") String id) {
		try {
			 if(iMailHeaderModuleService.deleteLogic(id)){
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
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   更新邮件抬头预设置语句
     */
  	@ApiOperation(value = "更新邮件抬头预设置语句", notes = "更新邮件抬头预设置语句", httpMethod = "POST")
	@RequestMapping(value = "/update", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<MailHeaderModule> update(HttpServletRequest request,MailHeaderModule entity) {
		try {
			if(entity!=null&&StringUtils.isNotBlank(entity.getId())){
				//更新
				entity.setUpdateBy(getLoginUser(request).getId());
				if(iMailHeaderModuleService.saveOrUpdate(entity)){
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
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   查询单个邮件抬头预设置语句
     */
  	@ApiOperation(value = "查询单个邮件抬头预设置语句", notes = "查询单个邮件抬头预设置语句", httpMethod = "GET")
  	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET ,produces = { "application/json;charset=UTF-8" })
  	public PublicResult<MailHeaderModule> get(HttpServletRequest request,@PathVariable("id") String id) {
  		try {
  			return new PublicResult<>(PublicResultConstant.SUCCESS, iMailHeaderModuleService.getById(id));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
  	}
	
    /**
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   分页查询邮件抬头预设置语句
     */
  	@ApiOperation(value = "分页查询邮件抬头预设置语句", notes = "分页查询邮件抬头预设置语句", httpMethod = "GET")
  	@RequestMapping(value = "/list", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
    public Object list(MailHeaderModule entity,@RequestParam(value="pageNum",required = false,defaultValue="1") Integer pageNum,
			@RequestParam(value="pageSize",required = false,defaultValue="1000") Integer pageSize,HttpServletRequest request) {
		try {
			PageInfo<MailHeaderModule> page=iMailHeaderModuleService.pageList(this, request, entity, pageNum, pageSize);
			return new PublicResult<>(PublicResultConstant.SUCCESS, page);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(),null);
		}

	}
  
  	@ApiOperation(value = "处理带占位符的语句", notes = "处理带占位符的语句", httpMethod = "POST")
  	@RequestMapping(value = "/dealHeaderModule", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
    public Object dealHeaderModule(  @RequestParam(value="headerModuleId",required = true) String headerModuleId,
    		HttpServletRequest request) {
		try {
			
			 Wrapper<MailHeaderModule> wrapper = new EntityWrapper<>();
			 wrapper.eq("del_flag", "0").eq("id", headerModuleId);
			 MailHeaderModule entity = iMailHeaderModuleService.selectOne(wrapper);
			 
			 if(entity==null) {
				 return new PublicResult<>(PublicResultConstant.ERROR,"根据id找不到实体!",null);
			 }
			 	Map<String,Object> map=iMailHeaderModuleService.dealHeaderModule(entity.getDealContent());
			 	entity.setIsShow(map.get("isShow")+"");
		        entity.setDealStr(map.get("result")+"");
		      
			return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(),null);
		}

	}
}

