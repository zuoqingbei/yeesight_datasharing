package com.haier.datamart.controller;
import com.haier.datamart.controller.BaseController;
import com.haier.datamart.entity.MailModuleInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import com.haier.datamart.service.IMailHeaderInfoService;
import com.haier.datamart.service.IMailHeaderModuleService;
import com.haier.datamart.service.IMailSettingInfoService;
import com.haier.datamart.utils.UUIDUtils;
import com.haier.datamart.entity.MailHeaderInfo;
import com.haier.datamart.entity.MailHeaderModule;
import com.haier.datamart.entity.MailSettingInfo;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.fielddata.UidIndexFieldData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 *
 * @author zuoqb123
 * @date 2019-01-03
 * @todo 邮件抬头信息路由
 */
@RestController
@RequestMapping("/api/mailHeaderInfo")
@Api(value = "邮件抬头信息",description="邮件抬头信息 @author zuoqb123")
public class MailHeaderInfoController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(MailHeaderInfoController.class);

    @Autowired
    public IMailHeaderInfoService iMailHeaderInfoService;
    @Autowired
    public IMailHeaderModuleService iMailHeaderModuleService;
    @Autowired
    public IMailSettingInfoService iMailSettingInfoService;
    
    /**
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   新增邮件抬头信息
     */
  	@ApiOperation(value = "新增邮件抬头信息", notes = "新增邮件抬头信息", httpMethod = "POST")
	@RequestMapping(value = "/add", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<MailHeaderInfo> add(HttpServletRequest request,MailHeaderInfo entity) {
		try {
			if(StringUtils.isBlank(entity.getId())){
				//新增
				entity.setCreateBy(getLoginUser(request).getId());
				if(iMailHeaderInfoService.saveOrUpdate(entity)){
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
     * @todo   删除邮件抬头信息
     */
  	@ApiOperation(value = "删除邮件抬头信息", notes = "删除邮件抬头信息", httpMethod = "POST")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<MailHeaderInfo> delete(HttpServletRequest request,@PathVariable("id") String id) {
		try {
			 if(iMailHeaderInfoService.deleteLogic(id)){
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
     * @todo   更新邮件抬头信息
     */
  	@ApiOperation(value = "更新邮件抬头信息", notes = "更新邮件抬头信息", httpMethod = "POST")
	@RequestMapping(value = "/update", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<MailHeaderInfo> update(HttpServletRequest request,MailHeaderInfo entity) {
		try {
			if(entity!=null&&StringUtils.isNotBlank(entity.getId())){
				//更新
				entity.setUpdateBy(getLoginUser(request).getId());
				if(iMailHeaderInfoService.saveOrUpdate(entity)){
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
     * @todo   查询单个邮件抬头信息
     */
  	@ApiOperation(value = "查询单个邮件抬头信息", notes = "查询单个邮件抬头信息", httpMethod = "GET")
  	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET ,produces = { "application/json;charset=UTF-8" })
  	public PublicResult<MailHeaderInfo> get(HttpServletRequest request,@PathVariable("id") String id) {
  		try {
  			return new PublicResult<>(PublicResultConstant.SUCCESS, iMailHeaderInfoService.getById(id));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
  	}
	
    /**
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   拼接好的邮件头列表
     */
  	@ApiOperation(value = "拼接好的邮件头列表", notes = "拼接好的邮件头列表", httpMethod = "GET")
  	@RequestMapping(value = "/list", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
    public Object list( @RequestParam(value="platId",required = true) String platId){
		try {
			List<MailHeaderModule> list  = iMailHeaderModuleService.getList(platId);
			return new PublicResult<>(PublicResultConstant.SUCCESS, list);
		} catch (Exception e) {
			e.printStackTrace();  
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(),null);
		}

	}
  
  	/**
  	 * mailTitleFrom  0-临时编辑 1-模板      
  	 * headerModuleIds 头模板id,以逗号拼接
  	 *  mailFirstParagraph邮件标题
  	 *   userId 用户id
   	 * @return
  	 */
  	@SuppressWarnings("unchecked")
	@ApiOperation(value = "邮件抬头编辑接口", notes = "邮件抬头编辑接口", httpMethod = "POST")
  	@RequestMapping(value = "/save", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
    public Object save(@RequestParam(value="mailTitleFrom",required = true) String mailTitleFrom,  /*0-临时编辑 1-模板 */
    					@RequestParam(value="userId",required = true) String userId,
    					@RequestParam(value="mailId",required = true) String mailId,
    					@RequestParam(value="headerModules",required = false) String headerModulesJson,String mailFirstParagraph){
		try {
			MailSettingInfo entity = new MailSettingInfo();
			entity.setUpdateBy(userId);
			entity.setId(mailId);
			entity.setMailTitleFrom(mailTitleFrom);
			boolean result = false;
			if("1".equals(mailTitleFrom)) {
				//获取实体集合
				ArrayList<MailHeaderInfo> headerModules = new ArrayList<>();
				JSONArray jsonArray = JSON.parseArray(headerModulesJson);
				
				if(jsonArray!=null&&jsonArray.size()>0) {
					int order = 0;
					for (Object tempJson : jsonArray) {
						order++;
						MailHeaderModule headerModule  = JSONObject.toJavaObject((JSON)tempJson, MailHeaderModule.class);
						MailHeaderInfo targetEntiry = new MailHeaderInfo();
						targetEntiry.setId(UUIDUtils.getUuid());
						targetEntiry.setUpdateBy(userId);
						targetEntiry.setCreateBy(userId);
						targetEntiry.setUpdateDate(new Date());
						targetEntiry.setCreateDate(new Date());
						targetEntiry.setMailId(mailId);
						targetEntiry.setHeaderModuleId(headerModule.getId());
						targetEntiry.setOrderNo(order);
						headerModules.add(targetEntiry);
					}
				}
				if(headerModules!=null&&headerModules.size()>0) {
					result = iMailHeaderInfoService.save(mailId,headerModules);
				}else {
					result = true;
				}
				
				for (MailHeaderInfo mailHeaderModule : headerModules) {
					mailHeaderModule.setCreateBy(userId);
				}
			}else if("0".equals(mailTitleFrom)) {
				entity.setMailFirstParagraph(mailFirstParagraph);
			}
			result = iMailSettingInfoService.saveOrUpdate(entity);
			if(result) {
				return new PublicResult<>(PublicResultConstant.SUCCESS, null);
			}else {
				return new PublicResult<>(PublicResultConstant.ERROR, null);
			}
			//List<MailHeaderModule> list  = iMailHeaderModuleService.getList(platId);
			
		} catch (Exception e) {
			e.printStackTrace();  
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(),null);
		}

	}
}

