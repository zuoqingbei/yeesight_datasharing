package com.haier.datamart.controller;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.haier.datamart.annotation.Log;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.entity.MonitorEtlMailRelation;
import com.haier.datamart.entity.User;
import com.haier.datamart.service.IMonitorEtlMailRelationService;
import com.haier.datamart.service.IUserService;
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
public class MonitorEtlMailRelationController extends BaseController{
	@Autowired
	private IMonitorEtlMailRelationService etlMailRelationService;
	@Autowired
	private IUserService userService;
	
	/**
	 * 邮箱管理 查询
	* @author doushuihai  
	* @date 2018年7月23日上午11:35:12  
	* @TODO
	 */
	@GetMapping(value = "/data/getEtlMailRelation", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/data/getEtlMailRelation")
	public Object getEtlMailRelation(HttpServletRequest request, HttpServletResponse response) {
		String subject = request.getParameter("subject");
		String sendAddr = request.getParameter("sendAddr");
		MonitorEtlMailRelation monitorEtlMailRelation = new MonitorEtlMailRelation();
		if(StringUtils.isNotEmpty(subject)){
			monitorEtlMailRelation.setSubject(subject);
		}
		if(StringUtils.isNotEmpty(sendAddr)){
			monitorEtlMailRelation.setSendAddr(sendAddr);
		}
		try {
			List<MonitorEtlMailRelation> etlMailRelationList = etlMailRelationService.getEtlMailRelation(monitorEtlMailRelation);
			if(etlMailRelationList!=null){//将收件人由用户id转化为id对应的名字
				for (MonitorEtlMailRelation monitorEtlMailRelation2 : etlMailRelationList) {
					if(monitorEtlMailRelation2.getSendUser()==null)
					continue;
					User user  = userService.selectOne(monitorEtlMailRelation2.getSendUser());
					if(user!=null)
					monitorEtlMailRelation2.setSendUser(user.getName());
				}
			}
			
			return new PublicResult<>(PublicResultConstant.SUCCESS, etlMailRelationList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED, null);
		}

		

	}
	/**
	 * 邮箱管理 新增
	* @author doushuihai  
	* @date 2018年7月23日下午4:32:50  
	* @TODO
	 */
	@RequestMapping(value = "/data/insertEtlMailRelation", produces = { "application/json;charset=UTF-8" },method=RequestMethod.POST)
	@Log(description = "API接口:/data/insertEtlMailRelation ")
	public Object insertEtlMailRelation(HttpServletRequest request, @RequestBody MonitorEtlMailRelation monitorEtlMailRelation) {

		 User user=getLoginUser(request);
		try {
			String id=GenerationSequenceUtil.getUUID();
			if(monitorEtlMailRelation ==null){
				return new PublicResult<>(PublicResultConstant.FAILED, null);
			}
			monitorEtlMailRelation.setId(id);
			monitorEtlMailRelation.setCreateBy(user.getId());
			monitorEtlMailRelation.setCreateDate(new Date());
			monitorEtlMailRelation.setUpdateBy(user.getId());
			monitorEtlMailRelation.setUpdateDate(new Date());
			etlMailRelationService.insertEtlMailRelation(monitorEtlMailRelation);
			return new PublicResult<>(PublicResultConstant.SUCCESS, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED, null);
		}
	}
	/**
	 * 邮箱管理 修改
	* @author doushuihai  
	* @date 2018年7月23日下午3:46:45  
	* @TODO
	 */
	@RequestMapping(value = "/data/updateEtlMailRelation", produces = { "application/json;charset=UTF-8" },method=RequestMethod.POST)
	@Log(description = "API接口:/data/updateEtlMailRelation")
	public Object updateEtlMailRelation(HttpServletRequest request, @RequestBody MonitorEtlMailRelation monitorEtlMailRelation) {
		User user=getLoginUser(request);
		try {
			if(monitorEtlMailRelation ==null){
				return new PublicResult<>(PublicResultConstant.FAILED, null);
			}
			monitorEtlMailRelation.setUpdateBy(user.getId());
			monitorEtlMailRelation.setUpdateDate(new Date());
			etlMailRelationService.update(monitorEtlMailRelation);
			return new PublicResult<>(PublicResultConstant.SUCCESS, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED, null);
		}
	}
	/**
	 * 获取要修改的邮箱数据信息
	* @author doushuihai  
	* @date 2018年7月27日上午10:52:26  
	* @TODO
	 */
	@GetMapping(value = "/data/getEtlMailRelation2Update", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/data/getEtlMailRelation2Update")
	public Object getEtlMailRelation2Update(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		MonitorEtlMailRelation etlMailRelationById = etlMailRelationService.getEtlMailRelationById(id);
		return new PublicResult<>(PublicResultConstant.SUCCESS, etlMailRelationById);
	}
	/**
	 * 邮箱管理 删除
	* @author doushuihai  
	* @date 2018年7月23日下午4:32:26  
	* @TODO
	 */
	@GetMapping(value = "/data/deleteEtlMailRelation", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/data/deleteEtlMailRelation")
	public Object deleteEtlMailIndex(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");

		try {
			etlMailRelationService.deleteById(id);
			return new PublicResult<>(PublicResultConstant.SUCCESS, null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED, null);
		}

	}
	@RequestMapping(value = "/data/zzz", produces = { "application/json;charset=UTF-8" } ,method=RequestMethod.POST)
	@Log(description = "API接口:/data/zzz")
	public Object insertEtlMailIndex(HttpServletRequest request) {
		String subject = request.getParameter("subject");
		String sendAddr = request.getParameter("sendAddr");
		MonitorEtlMailRelation monitorEtlMailRelation = new MonitorEtlMailRelation();
		if(StringUtils.isNotEmpty(subject)){
			monitorEtlMailRelation.setSubject(subject);
		}
		if(StringUtils.isNotEmpty(sendAddr)){
			monitorEtlMailRelation.setSendAddr(sendAddr);
		}
		try {
			List<MonitorEtlMailRelation> etlMailRelationList = etlMailRelationService.getEtlMailRelation(monitorEtlMailRelation);
			return new PublicResult<>(PublicResultConstant.SUCCESS, etlMailRelationList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED, null);
		}
		
	}

}

