package com.haier.datamart.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.github.pagehelper.PageInfo;
import com.google.inject.servlet.RequestParameters;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.entity.MailPlatInfo;
import com.haier.datamart.entity.MailReceiverManager;
import com.haier.datamart.entity.MailSettingInfo;
import com.haier.datamart.entity.MonitorEtlMailReceiver;
import com.haier.datamart.service.IMailPlatInfoService;
import com.haier.datamart.service.IMailReceiverManagerService;
import com.haier.datamart.service.IMailSettingInfoService;
import com.haier.datamart.service.IMailTempleteInfoService;
import com.haier.datamart.service.IMonitorEtlMailReceiverService;
/**
 *
 * @author zuoqb123
 * @date 2019-01-03
 * @todo 邮件设置信息路由
 */
@RestController
@RequestMapping("/api//mailSettingInfo")
@Api(value = "邮件设置信息",description="邮件设置信息 @author zuoqb123")
public class MailSettingInfoController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(MailSettingInfoController.class);

    @Autowired
    public IMailSettingInfoService iMailSettingInfoService;
    @Autowired
    public IMailPlatInfoService iMailPlatInfoService;
    @Autowired
    public IMonitorEtlMailReceiverService iMonitorEtlMailReceiverService;
    @Autowired
    public IMailReceiverManagerService iMailReceiverManagerService;
    @Autowired
    public com.haier.datamart.service.IMailHeaderModuleService IMailHeaderModuleService;
    @Autowired
    public IMailTempleteInfoService iMailTempleteInfoService;
    /**
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   新增邮件设置信息
     */
  	@ApiOperation(value = "新增邮件设置信息", notes = "新增邮件设置信息", httpMethod = "POST")
	@RequestMapping(value = "/add", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<MailSettingInfo> add(HttpServletRequest request, MailSettingInfo entity) {
		try {
			 
			if(StringUtils.isBlank(entity.getId())){
				//新增
				entity.setCreateBy(getLoginUser(request).getId());
				if(iMailSettingInfoService.saveOrUpdate(entity)){
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
  	 * 根据账号创建发送任务接口
  	 * @param request
  	 * @param entity
  	 * @param userId
  	 * @return
  	 */
  	@ApiOperation(value = "根据账号创建发送任务接口", notes = "根据账号创建发送任务接口", httpMethod = "POST")
	@RequestMapping(value = "/addByUser", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<MailSettingInfo> add(HttpServletRequest request,
				@RequestParam("userId")String userId,
				MailSettingInfo entity   ) {
		try {
			if(!StringUtils.isEmpty(entity.getCron())) {//若cron表达式不为空
				List<Date> targetDateList  =   com.haier.datamart.utils.CornShowUtil.getRecent(entity.getCron(), new Date(), 1);
				if(targetDateList==null||targetDateList.size()!=1) {throw new RuntimeException("解析cron表达式错误!");}
				Date nextSendTime = targetDateList.get(0);
				entity.setNextSendTime2(nextSendTime);
			}
			
			String platId = entity.getPlatId();
			if(StringUtils.isBlank(userId)||StringUtils.isBlank( platId)) {
				return new PublicResult<>(PublicResultConstant.PARAM_ERROR, "用户信息或者平台信息不能为空!",null);
			}
			//测重
			 Wrapper<MailSettingInfo> wrapeper0 = new EntityWrapper<>();
			 wrapeper0.eq("plat_id", platId).eq("create_by", userId);
			 MailSettingInfo setting = iMailSettingInfoService.selectOne(wrapeper0);
			 if(setting!=null) {
				 return new PublicResult<>(PublicResultConstant.ERROR, "该平台下已存在您创建的邮件任务了!",null);
			 }
			 
			 //插入ModuleId
			 entity.setCreateBy(userId);
			 Wrapper<MailPlatInfo> wrapeper = new EntityWrapper<>();
			 wrapeper.eq("id", platId);
			 MailPlatInfo plat = iMailPlatInfoService.selectOne(wrapeper);
			 if(plat==null) {
				 return new PublicResult<>(PublicResultConstant.ERROR, "找不到该平台!",null);
			 }
			 //插入templateId
			 String templateId = plat.getTempleteId();
			 if(StringUtils.isEmpty(templateId)) { return new PublicResult<>(PublicResultConstant.ERROR, "平台下没有模板!",null);}
			 entity.setTempleteId(templateId);
			 
			/* Wrapper<MailTempleteInfo> wrapperTemplate = new EntityWrapper<>();
			 wrapperTemplate.eq("del_flag", "0").eq("plat_id", platId).orderDesc(Arrays.asList(new String[] {"create_date"}));
			 MailTempleteInfo  templateEntity = iMailTempleteInfoService.selectOne(wrapperTemplate);
			 if(templateEntity!=null) {
				 entity.setTempleteId(templateEntity.getId());
			 }*/
			 
			 if(plat.getModuleId()!=null) {
				 entity.setModuleId(plat.getModuleId());
			 }
			 
			if(StringUtils.isBlank(entity.getId())){
				//新增
				//entity.setCreateBy(getLoginUser(request).getId());
				if(iMailSettingInfoService.saveOrUpdate(entity)){
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
     * @todo   删除邮件设置信息
     */
  	@ApiOperation(value = "删除邮件设置信息", notes = "删除邮件设置信息", httpMethod = "POST")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<MailSettingInfo> delete(HttpServletRequest request,@PathVariable("id") String id) {
		try {
			 if(iMailSettingInfoService.deleteLogic(id)){
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
     * @todo   更新邮件设置信息
     */
  	@ApiOperation(value = "更新邮件设置信息", notes = "更新邮件设置信息", httpMethod = "POST")
	@RequestMapping(value = "/update", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<MailSettingInfo> update(HttpServletRequest request,MailSettingInfo entity) {
		try {
			
			if(!StringUtils.isEmpty(entity.getCron())&&!"0 0 18 ? ? ?".equals(entity.getCron())) {//若cron表达式不为空
				List<Date> targetDateList  =   com.haier.datamart.utils.CornShowUtil.getRecent(entity.getCron(), new Date(), 1);
				if(targetDateList==null||targetDateList.size()!=1) {throw new RuntimeException("解析cron表达式错误!");}
				Date nextSendTime = targetDateList.get(0);
				entity.setNextSendTime2(nextSendTime);
			}
			
			if(entity!=null&&StringUtils.isNotBlank(entity.getId())){
				//更新
				entity.setUpdateBy(getLoginUser(request).getId());
				if(iMailSettingInfoService.saveOrUpdate(entity)){
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
     * @todo   查询单个邮件设置信息
     */
  	@ApiOperation(value = "查询单个邮件设置信息", notes = "查询单个邮件设置信息", httpMethod = "GET")
  	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET ,produces = { "application/json;charset=UTF-8" })
  	public PublicResult<MailSettingInfo> get(HttpServletRequest request,@PathVariable("id") String id) {
  		try {
  			return new PublicResult<>(PublicResultConstant.SUCCESS, iMailSettingInfoService.getById(id));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
  	}
	
    /**
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   分页查询邮件设置信息
     */
  	@ApiOperation(value = "分页查询邮件设置信息", notes = "分页查询邮件设置信息", httpMethod = "GET")
  	@RequestMapping(value = "/list", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
    public Object list(MailSettingInfo entity,@RequestParam(value="pageNum",required = false,defaultValue="1") Integer pageNum,
			@RequestParam(value="pageSize",required = false,defaultValue="10") Integer pageSize,HttpServletRequest request) {
		try {
			PageInfo<MailSettingInfo> page=iMailSettingInfoService.pageList(this, request, entity, pageNum, pageSize);
			return new PublicResult<>(PublicResultConstant.SUCCESS, page);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(),null);
		}

	}
  
  	
  	 /**
  	  * 基于平台及userId查询邮件任务信息接口接口
     * @author zuoqb123
     * @todo   分页查询邮件设置信息
     */
  	@ApiOperation(value = "基于平台及userId查询邮件任务信息接口接口", notes = "基于平台及userId查询邮件任务信息接口接口", httpMethod = "GET")
  	@RequestMapping(value = "/getByUserAndPlat", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
  	public PublicResult<MailSettingInfo> getByUserAndPlat(HttpServletRequest request,@RequestParameters String platId,@RequestParameters String userId) {
  		try {
  			Wrapper<MailSettingInfo> wrapper = new EntityWrapper<>();
  			wrapper.eq("create_by",userId).eq("plat_id", platId).orderDesc(Arrays.asList(new String[] {"create_date","update_date"}));
  			MailSettingInfo entity = iMailSettingInfoService.selectOne(wrapper);
  			mailSettingInfo(entity);

  			
  			return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
  	}
  	
	@ApiOperation(value = "基于平台及userId查询邮件任务信息接口接口", notes = "基于平台及userId查询邮件任务信息接口接口", httpMethod = "GET")
  	@RequestMapping(value = "/getAllByUserAndPlat", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
  	public Object getAllByUserAndPlat(HttpServletRequest request,@RequestParameters String platId) {
  		try {
  			Wrapper<MailSettingInfo> wrapper = new EntityWrapper<>();
  			wrapper.eq("plat_id", platId).orderDesc(Arrays.asList(new String[] {"create_date","update_date"}));
  			List<MailSettingInfo> list = iMailSettingInfoService.selectList(wrapper);
  			for(MailSettingInfo entity:list){
  				mailSettingInfo(entity);
  			}
  			return new PublicResult<>(PublicResultConstant.SUCCESS, list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
  	}

	/**
	 * @time   2019年2月12日 下午1:59:50
	 * @author zuoqb
	 * @todo   TODO
	 * @param  @param entity
	 * @return_type   void
	 */
	public void mailSettingInfo(MailSettingInfo entity) {
		if(entity!=null) {
		//找到对应的联系人
			String messageReceiverId = entity.getMessageReciverId();//短信收件人
			entity.setMessageRecevers(getReceiverList( messageReceiverId));
			String mailReceiverId = entity.getMailReciverId();//邮件联系人
			entity.setMailJsRecevers(getReceiverList( mailReceiverId));
			String mailCsReceiverId = entity.getMailCsReciverId();//邮件抄送人组
			entity.setMessageCsRecevers(getReceiverList( mailCsReceiverId));
			if("1".equals(entity.getMailTitleFrom())) {// 0-临时编辑 1-模板
				//拼接好邮件抬头
				String headerContent = IMailHeaderModuleService.getHeaderContent(entity.getId());
				entity.setMailFirstParagraph(headerContent);
				//返回邮件头列表
				entity.setMailHeaderInfoLists(IMailHeaderModuleService.getHeaderInfoListByMailId(entity.getId()));
			}
		}
	}
  	
  	
  	
  	
  	
  	
  	public List<MailReceiverManager> getReceiverList(String messageReceiverId) {
  		List<MailReceiverManager> targetList = new ArrayList<>();
  		if(!StringUtils.isEmpty(messageReceiverId)) {
				Wrapper<MonitorEtlMailReceiver> wrapper2 = new EntityWrapper<>();
				wrapper2.eq("receive_id", messageReceiverId);
				List<MonitorEtlMailReceiver> tempList = iMonitorEtlMailReceiverService.selectList(wrapper2);
				//找到基表id
				List<String> tempIds = new ArrayList<>();
				if(tempList!=null) {
					for (MonitorEtlMailReceiver monitorEtlMailReceiver : tempList) {
						tempIds.add(monitorEtlMailReceiver.getMailReceiverManagerId());
				}
				}
				
				if(tempIds!=null&&tempIds.size()>0) {
					//根据基表id查找实体放入结果
					Wrapper<MailReceiverManager> wrapper3 = new EntityWrapper<>();
  	  				wrapper3.in("id", tempIds);
  	  				targetList = iMailReceiverManagerService.selectList(wrapper3);
				}
		}
		return targetList;
  	}
  	
   /**
    * lzg
    * @param request
    * @param startTime
    * @param endTime
    * @return
    */
   	@ApiOperation(value = "获取将发送的邮件的id", notes = "获取将发送的邮件的id", httpMethod = "GET")
   	@RequestMapping(value = "/emailControl", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
   	public Object getEmailControl(HttpServletRequest request,@RequestParam(required=true) String startTime,
   			@RequestParam(required=true) String endTime) {
   		
   		 try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
			Date startDate = sdf.parse(startTime);
			Date endDate = sdf.parse(endTime);
		    Map<String,List<String>> result  = iMailSettingInfoService.getEmailControl(startDate,endDate);
		    return new PublicResult<>(PublicResultConstant.SUCCESS,result);
		 } catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
   		 
  	 }
  	
	@ApiOperation(value = "更新下次发送邮件的时间", notes = "更新下次发送邮件的时间", httpMethod = "POST")
   	@RequestMapping(value = "/updateNextSendMailTime", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
   	public Object updateNextSendMailTime(@RequestParam(required=true) String mailId, @RequestParam(required=true) String userId) {
   		
   		 try {
   			if(mailId!=null&&StringUtils.isNotBlank(mailId)){
				
   				Wrapper<MailSettingInfo> wrapper = new EntityWrapper<>();
   				wrapper.eq("id", mailId).eq("del_flag", 0);
   				MailSettingInfo entity = iMailSettingInfoService.selectOne(wrapper);
   				
   				if(entity==null) {throw new RuntimeException("根据id找不到实体!");}
   				
   				String cronStr = entity.getCron();
   				
   				if(StringUtils.isEmpty(cronStr)) {throw new RuntimeException("cron表达式不能为空!");}
   				
   				String mailSendTimesStr = entity.getMailSendTimes();
   				
   				int mailSendTimes = 0;
   				if(StringUtils.isNotBlank(mailSendTimesStr)) {
   					  mailSendTimes = Integer.parseInt(mailSendTimesStr);
   				} 
   				
   				Date nextSendTime = null;
   				
   				Date date = entity.getNextSendTime2("yyyy-MM-dd HH:mm:ss");
   				
   				if(date==null) { date=new Date(); }
   				
   				List<Date> targetDateList  =   com.haier.datamart.utils.CornShowUtil.getRecent(cronStr, date, 1);
   				
   				if(targetDateList==null||targetDateList.size()!=1) {throw new RuntimeException("寻找下次发送日期时出错!");}
   				
   				nextSendTime = targetDateList.get(0);
   				
   				if(targetDateList.get(0).getTime()==date.getTime()) {
   					targetDateList  =   com.haier.datamart.utils.CornShowUtil.getRecent(cronStr, date, 2);
   					if(targetDateList==null||targetDateList.size()!=2) {throw new RuntimeException("寻找下次发送日期时出错!");}
   					nextSendTime = targetDateList.get(1);
   				}
   				
   				//重新赋值并保存
   				MailSettingInfo resultEntity = new MailSettingInfo();
   				resultEntity.setUpdateBy(userId);
   				resultEntity.setId(mailId);
   				resultEntity.setMailSendTimes(++mailSendTimes +"");
   				resultEntity.setNextSendTime2(nextSendTime);
   				
				if(iMailSettingInfoService.saveOrUpdate(resultEntity)){
					return new PublicResult<>(PublicResultConstant.SUCCESS, resultEntity);
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
  	
}

