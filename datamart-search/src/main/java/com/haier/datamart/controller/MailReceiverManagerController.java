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

import com.haier.datamart.mapper.MonitorEtlMailReceiverMapper;
import com.haier.datamart.service.IMailReceiverManagerService;
import com.haier.datamart.service.IMailSettingInfoService;
import com.haier.datamart.entity.MailReceiverManager;
import com.haier.datamart.entity.MonitorEtlMailReceiver;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

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
 * @date 2019-01-17
 * @todo 邮件收件人管理路由
 */
@RestController
@RequestMapping("/api//mailReceiverManager")
@Api(value = "邮件收件人管理",description="邮件收件人管理 @author zuoqb123")
public class MailReceiverManagerController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(MailReceiverManagerController.class);

    @Autowired
    public IMailReceiverManagerService iMailReceiverManagerService;
    @Autowired
    public IMailSettingInfoService iMailSettingInfoService;
    @Autowired
	private MonitorEtlMailReceiverMapper etlMailReceiverMapper;
    /**
     * @date   2019-01-17
     * @author zuoqb123
     * @todo   新增邮件收件人管理
     */
  	@ApiOperation(value = "新增邮件收件人管理", notes = "新增邮件收件人管理", httpMethod = "POST")
	@RequestMapping(value = "/add", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<MailReceiverManager> add(HttpServletRequest request,MailReceiverManager entity) {try {
		if(StringUtils.isBlank(entity.getId())){
			//新增
			entity.setCreateBy(getLoginUser(request).getId());
			MailReceiverManager entity2 = new MailReceiverManager();
			entity2.setEmail(entity.getEmail());
			entity2.setTel(entity.getTel());
			entity2.setName(entity.getName());
			//判断是否重复
			Wrapper<MailReceiverManager> warapper = new EntityWrapper<MailReceiverManager>(entity);
			if(iMailReceiverManagerService.selectOne(warapper)!=null) {return new PublicResult<>(PublicResultConstant.PARAM_ERROR, "邮箱,电话,姓名不能同时相同!",null); }
			if(StringUtils.isEmpty(entity.getModuleId())) {
				String moduleId = iMailSettingInfoService.getModuleIdByPlatId(entity.getPlatId());
				if(!StringUtils.isEmpty( moduleId)) entity.setModuleId(moduleId);
			}
			if(iMailReceiverManagerService.saveOrUpdate(entity)){
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
	}}
    
    /**
     * @date   2019-01-17
     * @author zuoqb123
     * @todo   删除邮件收件人管理
     */
  	@ApiOperation(value = "删除邮件收件人管理", notes = "删除邮件收件人管理", httpMethod = "POST")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<MailReceiverManager> delete(HttpServletRequest request,@PathVariable("id") String id) {
		try {
			 if(iMailReceiverManagerService.deleteLogic(id)){
				 //同时删除已经选择的组中用户monitor_etl_mail_receiver
				 Wrapper<MonitorEtlMailReceiver> wrapper =  new EntityWrapper<>();
				 wrapper.eq("mail_receiver_manager_id", id);
				 etlMailReceiverMapper.delete(wrapper);
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
     * @date   2019-01-17
     * @author zuoqb123
     * @todo   更新邮件收件人管理
     */
  	@ApiOperation(value = "更新邮件收件人管理", notes = "更新邮件收件人管理", httpMethod = "POST")
	@RequestMapping(value = "/update", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<MailReceiverManager> update(HttpServletRequest request,MailReceiverManager entity) {
		try {
			if(entity!=null&&StringUtils.isNotBlank(entity.getId())){
				//更新
				entity.setUpdateBy(getLoginUser(request).getId());
				if(iMailReceiverManagerService.saveOrUpdate(entity)){
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
     * @date   2019-01-17
     * @author zuoqb123
     * @todo   查询单个邮件收件人管理
     */
  	@ApiOperation(value = "查询单个邮件收件人管理", notes = "查询单个邮件收件人管理", httpMethod = "GET")
  	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET ,produces = { "application/json;charset=UTF-8" })
  	public PublicResult<MailReceiverManager> get(HttpServletRequest request,@PathVariable("id") String id) {
  		try {
  			return new PublicResult<>(PublicResultConstant.SUCCESS, iMailReceiverManagerService.getById(id));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
  	}
	
    /**
     * @date   2019-01-17
     * @author zuoqb123
     * @todo   分页查询邮件收件人管理
     */
  	@ApiOperation(value = "分页查询邮件收件人管理", notes = "分页查询邮件收件人管理", httpMethod = "GET")
  	@RequestMapping(value = "/list", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
    public Object list(MailReceiverManager entity,@RequestParam(value="pageNum",required = false,defaultValue="1") Integer pageNum,
			@RequestParam(value="pageSize",required = false,defaultValue="1000") Integer pageSize,HttpServletRequest request) {
		try {
			PageInfo<MailReceiverManager> page=iMailReceiverManagerService.pageList(this, request, entity, pageNum, pageSize);
			return new PublicResult<>(PublicResultConstant.SUCCESS, page);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(),null);
		}

	}
  
}

