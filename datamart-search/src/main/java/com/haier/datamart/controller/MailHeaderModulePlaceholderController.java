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

import com.haier.datamart.service.IMailHeaderModulePlaceholderService;
import com.haier.datamart.service.IMailHeaderModulePlaceholderThresholdService;
import com.haier.datamart.entity.MailHeaderModulePlaceholder;
import com.haier.datamart.entity.MailHeaderModulePlaceholderThreshold;
import com.haier.datamart.entity.SysUser;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.haier.datamart.utils.UUIDUtils;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.baomidou.mybatisplus.mapper.EntityWrapper;

import org.springframework.web.bind.annotation.RequestBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 *
 * @author zuoqb123
 * @date 2019-01-03
 * @todo 邮件抬头预设置语句中占位符与统一接口配置信息路由
 */
@RestController
@RequestMapping("/api//mailHeaderModulePlaceholder")
@Api(value = "邮件抬头预设置语句中占位符与统一接口配置信息",description="邮件抬头预设置语句中占位符与统一接口配置信息 @author zuoqb123")
public class MailHeaderModulePlaceholderController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(MailHeaderModulePlaceholderController.class);

    @Autowired
    public IMailHeaderModulePlaceholderService iMailHeaderModulePlaceholderService;
    
    @Autowired
    public IMailHeaderModulePlaceholderThresholdService iMailHeaderModulePlaceholderThresholdService;

    /**
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   新增邮件抬头预设置语句中占位符与统一接口配置信息
     */
  	@ApiOperation(value = "新增邮件抬头预设置语句中占位符与统一接口配置信息", notes = "新增邮件抬头预设置语句中占位符与统一接口配置信息", httpMethod = "POST")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public PublicResult<MailHeaderModulePlaceholder> add(HttpServletRequest request,@RequestBody  MailHeaderModulePlaceholder entity) {
		try {
			if(StringUtils.isBlank(entity.getId())){
				//新增
				//entity.setCreateBy(getLoginUser(request).getId());
				if(iMailHeaderModulePlaceholderService.saveOrUpdate(entity)){
					//操作占位符阈值
					iMailHeaderModulePlaceholderThresholdService.deleteAndSave(entity.getThresholds(), entity.getId());
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
     * @todo   删除邮件抬头预设置语句中占位符与统一接口配置信息
     */
  	@ApiOperation(value = "删除邮件抬头预设置语句中占位符与统一接口配置信息", notes = "删除邮件抬头预设置语句中占位符与统一接口配置信息", httpMethod = "POST")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<MailHeaderModulePlaceholder> delete(HttpServletRequest request,@PathVariable("id") String id) {
		try {
			 if(iMailHeaderModulePlaceholderService.deleteLogic(id)){
				//操作占位符阈值
				iMailHeaderModulePlaceholderThresholdService.deleteAndSave(new ArrayList<MailHeaderModulePlaceholderThreshold>(), id);
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
     * @todo   更新邮件抬头预设置语句中占位符与统一接口配置信息
     */
  	@ApiOperation(value = "更新邮件抬头预设置语句中占位符与统一接口配置信息", notes = "更新邮件抬头预设置语句中占位符与统一接口配置信息", httpMethod = "POST")
	@RequestMapping(value = "/update", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<MailHeaderModulePlaceholder> update(HttpServletRequest request,MailHeaderModulePlaceholder entity) {
		try {
			if(entity!=null&&StringUtils.isNotBlank(entity.getId())){
				//更新
				entity.setUpdateBy(getLoginUser(request).getId());
				if(iMailHeaderModulePlaceholderService.saveOrUpdate(entity)){
					//操作占位符阈值
					iMailHeaderModulePlaceholderThresholdService.deleteAndSave(entity.getThresholds(), entity.getId());
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
     * @todo   查询单个邮件抬头预设置语句中占位符与统一接口配置信息
     */
  	@ApiOperation(value = "查询单个邮件抬头预设置语句中占位符与统一接口配置信息", notes = "查询单个邮件抬头预设置语句中占位符与统一接口配置信息", httpMethod = "GET")
  	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET ,produces = { "application/json;charset=UTF-8" })
  	public PublicResult<MailHeaderModulePlaceholder> get(HttpServletRequest request,@PathVariable("id") String id) {
  		try {
  			MailHeaderModulePlaceholder h=iMailHeaderModulePlaceholderService.getById(id);
  			if(h!=null){
  				h.setThresholds(iMailHeaderModulePlaceholderThresholdService.thresholdListByPlaceholderId(id));
  			}
  			return new PublicResult<>(PublicResultConstant.SUCCESS, h);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
  	}
	
    /**
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   分页查询邮件抬头预设置语句中占位符与统一接口配置信息
     */
  	@ApiOperation(value = "分页查询邮件抬头预设置语句中占位符与统一接口配置信息", notes = "分页查询邮件抬头预设置语句中占位符与统一接口配置信息", httpMethod = "GET")
  	@RequestMapping(value = "/list", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
    public Object list(MailHeaderModulePlaceholder entity,@RequestParam(value="pageNum",required = false,defaultValue="1") Integer pageNum,
			@RequestParam(value="pageSize",required = false,defaultValue="1000") Integer pageSize,HttpServletRequest request) {
		try {
			PageInfo<MailHeaderModulePlaceholder> page=iMailHeaderModulePlaceholderService.pageList(this, request, entity, pageNum, pageSize);
			return new PublicResult<>(PublicResultConstant.SUCCESS, page);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(),null);
		}

	}
  	
  	
  	
  	
  
}

