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

import com.haier.datamart.service.IMailReceiverUserInfoService;
import com.haier.datamart.service.IMailSettingInfoService;
import com.haier.datamart.entity.MailReceiverUserInfo;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
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
 * @todo 邮件模块收件人原始信息信息路由
 */
@RestController
@RequestMapping("/api//mailReceiverUserInfo")
@Api(value = "邮件模块收件人原始信息信息",description="邮件模块收件人原始信息信息 @author zuoqb123")
public class MailReceiverUserInfoController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(MailReceiverUserInfoController.class);

    @Autowired
    public IMailReceiverUserInfoService iMailReceiverUserInfoService;
    @Autowired
    public IMailSettingInfoService iMailSettingInfoService;
    /**
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   新增邮件模块收件人原始信息信息
     */
  	@ApiOperation(value = "新增邮件模块收件人原始信息信息", notes = "新增邮件模块收件人原始信息信息", httpMethod = "POST")
	@RequestMapping(value = "/add", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<MailReceiverUserInfo> add(HttpServletRequest request,  MailReceiverUserInfo entity) {
		try {
			if(StringUtils.isBlank(entity.getId())){
				//新增
				entity.setCreateBy(getLoginUser(request).getId());
				if(StringUtils.isEmpty(entity.getModuleId())) {
					String moduleId = iMailSettingInfoService.getModuleIdByPlatId(entity.getPlatId());
					entity.setModuleId(moduleId);
				}
				if(iMailReceiverUserInfoService.saveOrUpdate(entity)){
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
     * @todo   创建短信组
     */
  	@ApiOperation(value = "创建短信组", notes = "创建短信组", httpMethod = "POST")
    @RequestMapping(value = "/createMessageGroup", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<MailReceiverUserInfo> createMessageGroup(HttpServletRequest request,String userInfoIds,String type) {
		try {
			 List<MailReceiverUserInfo> users=iMailReceiverUserInfoService.selectBatchIds(Arrays.asList(userInfoIds.split(",")));
			 if(users!=null&&users.size()>0){
				 
			 }
			 return new PublicResult<>(PublicResultConstant.SUCCESS, null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
	}
    
    /**
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   删除邮件模块收件人原始信息信息
     */
  	@ApiOperation(value = "删除邮件模块收件人原始信息信息", notes = "删除邮件模块收件人原始信息信息", httpMethod = "POST")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<MailReceiverUserInfo> delete(HttpServletRequest request,@PathVariable("id") String id) {
		try {
			 if(iMailReceiverUserInfoService.deleteLogic(id)){
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
     * @todo   更新邮件模块收件人原始信息信息
     */
  	@ApiOperation(value = "更新邮件模块收件人原始信息信息", notes = "更新邮件模块收件人原始信息信息", httpMethod = "POST")
	@RequestMapping(value = "/update", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<MailReceiverUserInfo> update(HttpServletRequest request,MailReceiverUserInfo entity) {
		try {
			if(entity!=null&&StringUtils.isNotBlank(entity.getId())){
				//更新
				entity.setUpdateBy(getLoginUser(request).getId());
				if(iMailReceiverUserInfoService.saveOrUpdate(entity)){
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
     * @todo   查询单个邮件模块收件人原始信息信息
     */
  	@ApiOperation(value = "查询单个邮件模块收件人原始信息信息", notes = "查询单个邮件模块收件人原始信息信息", httpMethod = "GET")
  	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET ,produces = { "application/json;charset=UTF-8" })
  	public PublicResult<MailReceiverUserInfo> get(HttpServletRequest request,@PathVariable("id") String id) {
  		try {
  			return new PublicResult<>(PublicResultConstant.SUCCESS, iMailReceiverUserInfoService.getById(id));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
  	}
	
    /**
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   分页查询邮件模块收件人原始信息信息
     */
  	@ApiOperation(value = "分页查询邮件模块收件人原始信息信息", notes = "分页查询邮件模块收件人原始信息信息", httpMethod = "GET")
  	@RequestMapping(value = "/list", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
    public Object list(MailReceiverUserInfo entity,@RequestParam(value="pageNum",required = false,defaultValue="1") Integer pageNum,
			@RequestParam(value="pageSize",required = false,defaultValue="10") Integer pageSize,HttpServletRequest request) {
		try {
			PageInfo<MailReceiverUserInfo> page=iMailReceiverUserInfoService.pageList(this, request, entity, pageNum, pageSize);
			return new PublicResult<>(PublicResultConstant.SUCCESS, page);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(),null);
		}

	}
  
}

