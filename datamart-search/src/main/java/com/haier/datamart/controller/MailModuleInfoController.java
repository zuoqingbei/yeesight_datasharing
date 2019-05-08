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
import com.haier.datamart.service.IMailModuleInfoService;
import com.haier.datamart.service.IMailPlatInfoService;
import com.haier.datamart.entity.MailModuleInfo;
import com.haier.datamart.entity.MailModuleInfoRequstData;
import com.haier.datamart.entity.MailPlatInfo;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hive.ql.parse.HiveParser.foreignKeyConstraint_return;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
 * @date 2019-01-10
 * @todo 邮件抬头信息路由
 */
@RestController
@RequestMapping("/api//mailModuleInfo")
@Api(value = "邮件抬头信息",description="邮件抬头信息 @author zuoqb123")
public class MailModuleInfoController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(MailModuleInfoController.class);

    @Autowired
    public IMailModuleInfoService iMailModuleInfoService;
    @Autowired
    public IMailPlatInfoService iMailPlatInfoService;

    /**
     * @date   2019-01-10
     * @author zuoqb123
     * @todo   新增邮件抬头信息
     */
  	@ApiOperation(value = "新增邮件抬头信息", notes = "新增邮件抬头信息", httpMethod = "POST")
	@RequestMapping(value = "/add", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<MailModuleInfo> add(HttpServletRequest request,  MailModuleInfo entity) {
		try {
			if(StringUtils.isBlank(entity.getId())){
				//新增
				//createBy 由前台传入
//				entity.setCreateBy(getLoginUser(request).getId());
				if(iMailModuleInfoService.saveOrUpdate(entity)){
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
     * @date   2019-01-10
     * @author zuoqb123
     * @todo   删除邮件抬头信息
     */
  	@ApiOperation(value = "删除邮件抬头信息", notes = "删除邮件抬头信息", httpMethod = "POST")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<MailModuleInfo> delete(HttpServletRequest request,@PathVariable("id") String id) {
		try {
			 if(iMailModuleInfoService.deleteLogic(id)){
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
     * @date   2019-01-10
     * @author zuoqb123
     * @todo   更新邮件抬头信息
     */
  	@ApiOperation(value = "更新邮件抬头信息", notes = "更新邮件抬头信息", httpMethod = "POST")
	@RequestMapping(value = "/update", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<MailModuleInfo> update(HttpServletRequest request,MailModuleInfo entity,@RequestParam(required=true)String userId) {
		try {
			if(entity!=null&&StringUtils.isNotBlank(entity.getId())){
				//更新
				entity.setUpdateBy(userId);
				if(iMailModuleInfoService.saveOrUpdate(entity)){
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
     * @date   2019-01-10
     * @author zuoqb123
     * @todo   查询单个邮件抬头信息
     */
  	@ApiOperation(value = "查询单个邮件抬头信息", notes = "查询单个邮件抬头信息", httpMethod = "GET")
  	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET ,produces = { "application/json;charset=UTF-8" })
  	public PublicResult<MailModuleInfo> get(HttpServletRequest request,@PathVariable("id") String id) {
  		try {
  			return new PublicResult<>(PublicResultConstant.SUCCESS, iMailModuleInfoService.getById(id));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
  	}
	
    /**
     * @date   2019-01-10
     * @author zuoqb123
     * @todo   分页查询邮件抬头信息
     */
  	@ApiOperation(value = "分页查询邮件抬头信息", notes = "分页查询邮件抬头信息", httpMethod = "GET")
  	@RequestMapping(value = "/list", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
    public Object list(MailModuleInfo entity,@RequestParam(value="pageNum",required = false,defaultValue="1") Integer pageNum,
			@RequestParam(value="pageSize",required = false,defaultValue="1000") Integer pageSize,HttpServletRequest request) {
		try {
			
			
			PageInfo<MailModuleInfo> page=iMailModuleInfoService.pageList(this, request, entity, pageNum, pageSize);
			
			
			return new PublicResult<>(PublicResultConstant.SUCCESS, page);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(),null);
		}

	}
  	

    /**
     * @date   2019-01-10
     * @author zuoqb123
     * @todo   查询模块、平台信息接口接口
     */
  	@ApiOperation(value = "查询模块、平台信息接口接口", notes = "查询模块、平台信息接口接口", httpMethod = "GET")
  	@RequestMapping(value = "/get/getModuleAndPlat", method = RequestMethod.GET ,produces = { "application/json;charset=UTF-8" })
  	public PublicResult<List<MailModuleInfoRequstData>>  get(HttpServletRequest request) {
  		try {
  			List<MailModuleInfoRequstData> resultList = new ArrayList<>();
  			
  			Wrapper<MailModuleInfo> wrapperModuleList = new EntityWrapper<>();
  			wrapperModuleList.eq("del_flag", "0");
  			List<MailModuleInfo> moduleList = iMailModuleInfoService.selectList(wrapperModuleList);
  			
  			if(moduleList!=null) {
  				//遍历模块
  				 for (MailModuleInfo mailModuleInfo : moduleList) {
  					 
  					MailModuleInfoRequstData result = new MailModuleInfoRequstData();
  					
					if(mailModuleInfo!=null) {
						result.setMailModuleInfo(mailModuleInfo);
						String moduleId = mailModuleInfo.getId();
						//根据模块id查平台
						if(!StringUtils.isEmpty(moduleId)) {
							Wrapper<MailPlatInfo> wrapperPlatList = new EntityWrapper<>();
							wrapperPlatList.eq("del_flag", "0")
									.eq("module_id", moduleId)
									.eq("is_complete","1");
							List<MailPlatInfo> platList = iMailPlatInfoService.selectList(wrapperPlatList);
							result.setMailPlatInfoList(platList);
						}
					}
					resultList.add(result);	
				}
  				 
  			}
  			return new PublicResult<>(PublicResultConstant.SUCCESS, resultList);	
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
  	}
  	
  	
  	
  	
  
}

