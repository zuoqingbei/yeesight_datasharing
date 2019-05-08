package com.haier.datamart.controller;


import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.entity.MailReceiverManager;
import com.haier.datamart.entity.MonitorEtlMailReceiver;
import com.haier.datamart.entity.User;
import com.haier.datamart.service.IMailReceiverManagerService;
import com.haier.datamart.service.IMonitorEtlMailReceiverService;
import com.haier.datamart.utils.GenerationSequenceUtil;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author doushuihai123
 * @since 2018-07-24
 */
@RestController
public class MonitorEtlMailReceiverController extends BaseController{
	@Autowired
	private IMonitorEtlMailReceiverService etlMailReceiverService;
	@Autowired
	private IMailReceiverManagerService iMailReceiverManagerService;
	/**
	 * 收件人管理组 查询
	* @author doushuihai  
	* @date 2018年7月23日上午11:35:12  
	* @TODO
	 */
	@ApiOperation(value = "收件人管理组 查询", notes = "收件人管理组 查询", httpMethod = "GET")
	@GetMapping(value = "/data/getEtlMailReceiver", produces = { "application/json;charset=UTF-8" })
	//@Log(description = "API接口:/data/getEtlMailReceiver")
	public Object getEtlMailReceiver(HttpServletRequest request,
			@RequestParam(value="receiveName",required = false) String receiveName,@RequestParam(value="createId",required = false) String createId) {
		MonitorEtlMailReceiver receiver = new MonitorEtlMailReceiver();
	
		if(StringUtils.isNotEmpty(receiveName)){
			receiver.setReceiveName(receiveName);
		}
		if(StringUtils.isNotEmpty(createId)){
			receiver.setCreateBy(createId);
		}
		List<MonitorEtlMailReceiver> etlMailReceiverList = etlMailReceiverService.getEtlMailReceiver(receiver);
		for(MonitorEtlMailReceiver mailreceiver:etlMailReceiverList){
			String receiveId=mailreceiver.getReceiveId();
			List<MonitorEtlMailReceiver> receiverList = etlMailReceiverService.getEtlMailReceiver2Update(receiveId);
			mailreceiver.setReceiverList(receiverList);
		}
		
		return new PublicResult<>(PublicResultConstant.SUCCESS, etlMailReceiverList);

	}
	/**
	 * 收件人管理组 新增
	* @author doushuihai  
	* @date 2018年7月23日下午4:32:50  
	* @TODO
	 */
	@ApiOperation(value = "收件人管理组 新增", notes = "收件人管理组 新增", httpMethod = "POST")
	@RequestMapping(value = "/data/insertEtlMailReceiver", produces = { "application/json;charset=UTF-8" },method=RequestMethod.POST)
	//@Log(description = "API接口:/data/insertEtlMailReceiver ")
	public Object insertEtlMailReceiver(HttpServletRequest request, @RequestBody MonitorEtlMailReceiver receiver) {
		List<MonitorEtlMailReceiver> receiverList = receiver.getReceiverList();
		if(CollectionUtils.isEmpty(receiverList)){
			return new PublicResult<>(PublicResultConstant.FAILED, null);
		}
		 User user=getLoginUser(request);
		try {
			String receiverId=GenerationSequenceUtil.getUUID();
			for(MonitorEtlMailReceiver etlMailReceiver:receiverList){
				if(StringUtils.isBlank(etlMailReceiver.getState())){
					etlMailReceiver.setState("on");
				}
				etlMailReceiver.setId(GenerationSequenceUtil.getUUID());
				etlMailReceiver.setReceiveId(receiverId);
				etlMailReceiver.setCreateBy(user.getId());
				etlMailReceiver.setCreateDate(new Date());
				etlMailReceiver.setUpdateBy(user.getId());
				etlMailReceiver.setUpdateDate(new Date());
				etlMailReceiverService.insertEtlMailReceiver(etlMailReceiver);
			}
			return new PublicResult<>(PublicResultConstant.SUCCESS, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED, null);
		}
	}
	/**
	 * 收件人管理组 修改
	* @author doushuihai  
	* @date 2018年7月23日下午3:46:45  
	* @TODO
	 */
	@ApiOperation(value = "收件人管理组  修改", notes = "收件人管理组  修改", httpMethod = "POST")
	@RequestMapping(value = "/data/updateEtlMailReceiver", produces = { "application/json;charset=UTF-8" },method=RequestMethod.POST)
	//@Log(description = "API接口:/data/updateEtlMailReceiver")
	public Object updateEtlMailReceiver(HttpServletRequest request, @RequestBody MonitorEtlMailReceiver receiver) {
		List<MonitorEtlMailReceiver> receiverList = receiver.getReceiverList();
		User user=getLoginUser(request);
		try {
			String receiveId=receiver.getReceiveId();
			etlMailReceiverService.deleteByReceiveId(receiveId);
			for(MonitorEtlMailReceiver etlMailReceiver:receiverList){
				etlMailReceiver.setId(GenerationSequenceUtil.getUUID());
				if(StringUtils.isBlank(etlMailReceiver.getState())){
					etlMailReceiver.setState("on");
				}
				etlMailReceiver.setReceiveId(receiveId);
				etlMailReceiver.setUpdateBy(user.getId());
				etlMailReceiver.setUpdateDate(new Date());
				etlMailReceiverService.insertEtlMailReceiver(etlMailReceiver);
			}
			return new PublicResult<>(PublicResultConstant.SUCCESS, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED, null);
		}
	}
	/**
	 * 收件人管理组 删除
	* @author doushuihai  
	* @date 2018年7月23日下午4:32:26  
	* @TODO
	 */
	@ApiOperation(value = "收件人管理组  删除", notes = "收件人管理组  删除", httpMethod = "POST")
	@GetMapping(value = "/data/deleteEtlMailReceiver", produces = { "application/json;charset=UTF-8" })
	//@Log(description = "API接口:/data/deleteEtlMailReceiver")
	public Object deleteEtlMailIndex(HttpServletRequest request, HttpServletResponse response,@RequestParam(value="receiveId") String receiveId) {
		try {
			if(etlMailReceiverService.deleteByReceiveId(receiveId)==1){
				return new PublicResult<>(PublicResultConstant.SUCCESS, null);
			}else{
				return new PublicResult<>(PublicResultConstant.ERROR, null);
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED, null);
		}

	}

	/**
	 * 获取要修改的指标组信息
	* @author doushuihai  
	* @date 2018年7月27日上午9:39:07  
	* @TODO
	 */
	@ApiOperation(value = "获取要修改的指标组信息", notes = "获取要修改的指标组信息", httpMethod = "GET")
	@GetMapping(value = "/data/getEtlMailReceiverToUpdate", produces = { "application/json;charset=UTF-8" })
	//@Log(description = "API接口:/data/getEtlMailReceiverToUpdate")
	public Object getEtlMailReceiverToUpdate(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="receiveId") String receiveId){
		List<MonitorEtlMailReceiver> etlMailReceiver2Update = etlMailReceiverService.getEtlMailReceiver2Update(receiveId);
		return new PublicResult<>(PublicResultConstant.SUCCESS, etlMailReceiver2Update);
		
	}
	/**
	 * 根据receiveName查询以校验收件组名称是否存在
	* @author doushuihai  
	* @date 2018年7月27日上午9:39:07  
	* @TODO
	 */
	@ApiOperation(value = "根据receiveName查询以校验收件组名称是否存在", notes = "根据receiveName查询以校验收件组名称是否存在", httpMethod = "GET")
	@GetMapping(value = "/data/getEtlMailReceiverToCheck", produces = { "application/json;charset=UTF-8" })
	//@Log(description = "API接口:/data/getEtlMailReceiverToCheck")
	public Object getEtlMailReceiverToCheck(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="receiveName") String receiveName){
		List<MonitorEtlMailReceiver> etlMailReceiver = etlMailReceiverService.getByReceiveName(receiveName);
		if(CollectionUtils.isEmpty(etlMailReceiver)){
			return new PublicResult<>(PublicResultConstant.SUCCESS, null);
		}else{
			return new PublicResult<>(PublicResultConstant.FAILED, "该收件组名称已存在！");
		}
	}
	
	@ApiOperation(value = "邮件管理系统联系人的维护", notes = "邮件管理系统联系人的维护", httpMethod = "POST")
	@PostMapping(value = "/data/alterMailReceiver", produces = { "application/json;charset=UTF-8" })
	//@Log(description = "API接口:/data/getEtlMailReceiverToCheck")
	public Object alterMailReceiver(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("mailReceiverManagerIds")String mailReceiverManagerIds,@RequestParam("receiveId")String receiveId
			,@RequestParam("userId")String userId,@RequestParam("receiveType")String receiveType,
			@RequestParam("mailId")String mailId){
			/**
			 * receiveType 0-短信收件人 1-邮件收件人 2-邮件抄送人
			 */
		try {
			 List<MailReceiverManager> list = new ArrayList<>();
			String newReceiveId =  etlMailReceiverService.alterMailReceiver(mailReceiverManagerIds,receiveId,userId,receiveType,mailId);
			if(!StringUtils.isEmpty(newReceiveId)) {
				if(StringUtils.isNotBlank(mailReceiverManagerIds)){
					Wrapper<MailReceiverManager> wrapper = new EntityWrapper<MailReceiverManager>();
					wrapper.in("id", mailReceiverManagerIds);
					list = iMailReceiverManagerService.selectList(wrapper); 
				}
				return new PublicResult<>(PublicResultConstant.SUCCESS, list);
			}else{
				return new PublicResult<>(PublicResultConstant.FAILED, "操作失败!");
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED, e.getMessage());
		}
		
	}
}

