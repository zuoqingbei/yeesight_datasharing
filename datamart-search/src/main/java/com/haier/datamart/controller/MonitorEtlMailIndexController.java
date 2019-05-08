package com.haier.datamart.controller;


import io.swagger.annotations.ApiOperation;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.haier.datamart.annotation.Log;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.entity.MonitorEtlMailIndex;
import com.haier.datamart.entity.User;
import com.haier.datamart.service.IMonitorEtlMailIndexService;
import com.haier.datamart.utils.GenerationSequenceUtil;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author doushuihai123
 * @since 2018-07-23
 */
@RestController
public class MonitorEtlMailIndexController extends BaseController{
	@Autowired
	private IMonitorEtlMailIndexService etlMailIndexService;
	/**
	 * 指标组管理 查询获取指标组基本信息
	* @author doushuihai  
	* @date 2018年7月23日上午11:35:12  
	* @TODO
	 */
	@ApiOperation(value = "指标组管理 查询获取指标组基本信息", notes = "指标组管理 查询获取指标组基本信息", httpMethod = "GET")
	@GetMapping(value = "/data/getEtlMailIndex", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/data/getEtlMailIndex")
	public Object getEtlMailIndex(HttpServletRequest request,
			@RequestParam(value="groupName",required = false) String groupName,@RequestParam(value="createId",required = false) String createId) {
		MonitorEtlMailIndex monitorEtlMailIndex = new MonitorEtlMailIndex();
		if(StringUtils.isNotEmpty(groupName)){
			monitorEtlMailIndex.setGroupName(groupName);
		}
		if(StringUtils.isNotEmpty(createId)){
			monitorEtlMailIndex.setCreateBy(createId);
		}
		List<MonitorEtlMailIndex> etlMailIndexList = etlMailIndexService.getEtlMailIndex(monitorEtlMailIndex);
		for(MonitorEtlMailIndex index:etlMailIndexList){
			String groupId=index.getGroupId();
			List<MonitorEtlMailIndex> indexList = etlMailIndexService.getEtlMailIndex2Update(groupId);
			index.setEtlMailIndexList(indexList);
		}

		return new PublicResult<>(PublicResultConstant.SUCCESS, etlMailIndexList);

	}
	/**
	 * 指标组管理 新增
	* @author doushuihai  
	* @date 2018年7月23日下午4:32:50  
	* @TODO
	 */
	@ApiOperation(value = "指标组管理 新增", notes = "指标组管理 新增", httpMethod = "POST")
	@RequestMapping(value = "/data/insertEtlMailIndex", produces = { "application/json;charset=UTF-8" },method=RequestMethod.POST)
	@Log(description = "API接口:/data/insertEtlMailIndex ")
	public Object insertEtlMailIndex(HttpServletRequest request, @RequestBody MonitorEtlMailIndex monitorEtlMailIndex) {
		List<MonitorEtlMailIndex> etlMailIndexList=monitorEtlMailIndex.getEtlMailIndexList();
		if(CollectionUtils.isEmpty(etlMailIndexList)){
			return new PublicResult<>(PublicResultConstant.FAILED, null);
		}
		 User user=getLoginUser(request);
		try {
			String groupId=GenerationSequenceUtil.getUUID();
			for(MonitorEtlMailIndex etlMailIndex:etlMailIndexList){
				if(StringUtils.isBlank(etlMailIndex.getState())){
					etlMailIndex.setState("Y");
				}
				etlMailIndex.setId(GenerationSequenceUtil.getUUID());
				etlMailIndex.setGroupId(groupId);
				etlMailIndex.setCreateBy(user.getId());
				etlMailIndex.setCreateDate(new Date());
				etlMailIndex.setUpdateBy(user.getId());
				etlMailIndex.setUpdateDate(new Date());
				int re = etlMailIndexService.insertEtlMailIndex(etlMailIndex);
			}
			return new PublicResult<>(PublicResultConstant.SUCCESS, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED, null);
		}
	}
	/**
	 * 指标组管理 修改
	* @author doushuihai  
	* @date 2018年7月23日下午3:46:45  
	* @TODO
	 */
	@ApiOperation(value = "指标组管理 修改", notes = "指标组管理 修改", httpMethod = "POST")
	@RequestMapping(value = "/data/updateEtlMailIndex", produces = { "application/json;charset=UTF-8" },method=RequestMethod.POST)
	@Log(description = "API接口:/data/updateEtlMailIndex ")
	public Object updateEtlMailIndex(HttpServletRequest request, @RequestBody MonitorEtlMailIndex monitorEtlMailIndex) {
		List<MonitorEtlMailIndex> etlMailIndexList=monitorEtlMailIndex.getEtlMailIndexList();
		User user=getLoginUser(request);
		try {
			String groupId=monitorEtlMailIndex.getGroupId();//先删除再新增
			etlMailIndexService.deleteByGroupId(groupId);
			for(MonitorEtlMailIndex etlMailIndex:etlMailIndexList){
				if(StringUtils.isBlank(etlMailIndex.getState())){
					etlMailIndex.setState("Y");
				}
				etlMailIndex.setId(GenerationSequenceUtil.getUUID());
				etlMailIndex.setGroupId(groupId);
				etlMailIndex.setUpdateBy(user.getId());
				etlMailIndex.setUpdateDate(new Date());
				etlMailIndexService.insertEtlMailIndex(etlMailIndex);
			}
			return new PublicResult<>(PublicResultConstant.SUCCESS, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED, null);
		}
	}
	/**
	 * 指标组管理 删除
	* @author doushuihai  
	* @date 2018年7月23日下午4:32:26  
	* @TODO
	 */
	@ApiOperation(value = "指标组管理 删除", notes = "指标组管理 删除", httpMethod = "POST")
	@RequestMapping(value = "/data/deleteEtlMailIndex",method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/data/deleteEtlMailIndex")
	public Object deleteEtlMailIndex(HttpServletRequest request,@RequestParam(value="groupId") String groupId) {
		try {
			etlMailIndexService.deleteByGroupId(groupId);
			return new PublicResult<>(PublicResultConstant.SUCCESS, null);
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
	@GetMapping(value = "/data/getEtlMailIndex2Update", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/data/getEtlMailIndex2Update")
	public Object getEtlMailIndex2Update(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="groupId") String groupId){
		List<MonitorEtlMailIndex> etlMailIndexList = etlMailIndexService.getEtlMailIndex2Update(groupId);
		return new PublicResult<>(PublicResultConstant.SUCCESS, etlMailIndexList);
		
	}
	/**
	 * 校验指标组名是否已存在
	* @author doushuihai  
	* @date 2018年7月27日上午9:39:07  
	* @TODO
	 */
	@ApiOperation(value = "校验指标组名是否已存在", notes = "校验指标组名是否已存在", httpMethod = "GET")
	@GetMapping(value = "/data/getEtlMailIndex2Check", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/data/getEtlMailIndex2Check")
	public Object getEtlMailIndex2Check(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="groupName") String groupName){
		
		List<MonitorEtlMailIndex> etlMailIndexList = etlMailIndexService.getEtlMailIndexByGroupName(groupName);
		if(CollectionUtils.isEmpty(etlMailIndexList)){
			return new PublicResult<>(PublicResultConstant.SUCCESS, null);
		}else{
			return new PublicResult<>(PublicResultConstant.FAILED, "该指标组名称已存在！");
		}
		
		
	}
	
/*	@GetMapping(value = "/data/insertEtlMailIndex2", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/data/insertEtlMailIndex2")
	public Object insertEtlMailIndex(HttpServletRequest request) {
		
		//List<MonitorEtlMailIndex> etlMailIndexList=monitorEtlMailIndex.getEtlMailIndexList();
		List<MonitorEtlMailIndex> etlMailIndexList=new ArrayList<MonitorEtlMailIndex>();
		MonitorEtlMailIndex m=new MonitorEtlMailIndex();
		String groupId = request.getParameter("groupId");
		m.setCreateBy("iiiiiiiiii");
		m.setCreateDate(new Date());
		m.setGroupId("999999999");
		m.setGroupName("ceshi");
		m.setId("0b21baa4723d45d1b3aa5b561f4778d6");
		m.setIndexId("222");
		m.setIndexName("ttttttttttt");
		m.setModuleDesc("666");
		m.setTypeId("1");
		m.setUpdateBy("zzzzz");
		m.setUpdateDate(new Date());
		etlMailIndexList.add(m);
		try {
			for(MonitorEtlMailIndex etlMailIndex:etlMailIndexList){
				etlMailIndexService.update(etlMailIndex);
			}
			etlMailIndexService.deleteByGroupId(groupId);
			return new PublicResult<>(PublicResultConstant.SUCCESS, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED, null);
		}
	}*/

}

