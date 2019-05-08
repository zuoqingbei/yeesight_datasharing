package com.haier.datamart.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.github.pagehelper.PageInfo;
import com.haier.datamart.entity.MailForQueryBeanParent;
import com.haier.datamart.entity.MailForQueryBeanTableMap;
import com.haier.datamart.entity.MailTempleteInfo;
import com.haier.datamart.utils.email.EmailUtil;
import com.haier.datamart.utils.email.render.MultiEmailRender;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.haier.datamart.annotation.Log;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.entity.MonitorEtlEmailContentQueryInfo;
import com.haier.datamart.entity.User;
import com.haier.datamart.service.MonitorEtlEmailContentQueryInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 邮件内容数据查询信息 Controller层
 * @author: ZhangJiang
 * @date: 2018-12-10 下午4:56:17
 */
@Api(value = "邮件内容数据查询信息", description = "邮件内容数据查询信息 @author: ZhangJiang")
@RestController
public class MonitorEtlEmailContenQuerytInfoController extends BaseController {

	@Autowired
	private MonitorEtlEmailContentQueryInfoService emailContentQuerytInfoService;
	@Autowired
	private MailSettingInfoController mailSettingInfoController;
	/**
	 * 数据添加
	 * @param dataList
	 * @return
	 */
	/*@ApiOperation(value = "邮件内容数据添加", notes = "邮件内容数据添加")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "requestData", value = "请求数据对象", required = true, paramType = "query") 
	})
	@PostMapping(value = "/monitorEtlEmailContentQueryInfo/addByTheMail", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/monitorEtlEmailContentQueryInfo/addByTheMail")
	public Object addByTheMail(HttpServletRequest request,@RequestBody MonitorEtlEmailContenQuerytInfoRequestData requestData) {
		try {
			User user = getLoginUser(request);
			List<MonitorEtlEmailContentQueryInfo> list = requestData.getDataList();
			String tmplateId = requestData.getTemplateId();
			
			if (null == tmplateId || null == list) {
				return new PublicResult<>(PublicResultConstant.INVALID_PARAM_EMPTY, null);
			}
//			if (-1 == emailContentQuerytInfoService.deleteByMailId(mailId, user)) {
//				return new PublicResult<>(PublicResultConstant.SUCCESS, "以前模板数据删除失败!");
//			}
			emailContentQuerytInfoService.addByTheMail(tmplateId,list,user);
			return new PublicResult<>(PublicResultConstant.SUCCESS, "数据添加成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.ERROR, "数据添加失败!");
		}
	}*/
	
	
	/**
	 * 统一接口数据查询
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "统一接口数据查询", notes = "统一接口数据查询")
	@ApiImplicitParams({ @ApiImplicitParam(name = "MonitorEtlEmailContentQueryInfo", value = "查询条件对象", paramType = "query") })
	@GetMapping(value = "/monitorEtlEmailContentQueryInfo/unifiedInterfaceDataQuery", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/monitorEtlEmailContentQueryInfo/unifiedInterfaceDataQuery")
	public Object unifiedInterfaceDataQuery(HttpServletRequest request, String mailId) {
		try {
			MailForQueryBeanParent entity  =  emailContentQuerytInfoService.unifiedInterfaceDataQuery(mailId);
			return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.ERROR, "");
		}
	}

	
	/**
	 * 统一接口数据查询(通过templateId)
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "统一接口数据查询", notes = "统一接口数据查询")
	@ApiImplicitParams({ @ApiImplicitParam(name = "unifiedInterfaceDataQueryBtyTemplete", value = "统一接口数据查询", paramType = "query") })
	@GetMapping(value = "/monitorEtlEmailContentQueryInfo/unifiedInterfaceDataQueryBtyTemplete", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/monitorEtlEmailContentQueryInfo/unifiedInterfaceDataQueryBtyTemplete")
	public Object unifiedInterfaceDataQueryBtyTemplete(HttpServletRequest request, String templateId) {
		try { 
			List<MailForQueryBeanTableMap> entity =  emailContentQuerytInfoService.unifiedInterfaceDataQueryByTemplateId(templateId);
			return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.ERROR, "");
		}
	}
	/**
	 * @date   2019-01-03
	 * @author zuoqb123
	 * @todo   新增邮件模板信息（后台配置，给业务前台使用）
	 */
	@ApiOperation(value = "新增邮件模板信息（后台配置，给业务前台使用）", notes = "新增邮件模板信息（后台配置，给业务前台使用）", httpMethod = "POST")
	@RequestMapping(value = "/monitorEtlEmailContentQueryInfo/add", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<MonitorEtlEmailContentQueryInfo> add(HttpServletRequest request, MonitorEtlEmailContentQueryInfo entity) {
		try {
			if(StringUtils.isBlank(entity.getId())){
				//新增
				//创建人前台传入
//				entity.setCreateBy(getLoginUser(request).getId());
				if(emailContentQuerytInfoService.saveOrUpdate(entity)){
					return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
				}else{
					return new PublicResult<>(PublicResultConstant.ERROR, null);
				}
			}else{
				return new PublicResult<>(PublicResultConstant.PARAM_ERROR, "新增主键必须为空!",null);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
	}
	/**
	 * @date   2019-01-03
	 * @author zuoqb123
	 * @todo   更新邮件正文
	 */
	@ApiOperation(value = "更新邮件正文", notes = "更新邮件正文", httpMethod = "POST")
	@RequestMapping(value = "/monitorEtlEmailContentQueryInfo/update", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<MonitorEtlEmailContentQueryInfo> update(HttpServletRequest request,MonitorEtlEmailContentQueryInfo entity) {
		try {
			if(entity!=null&&StringUtils.isNotBlank(entity.getId())){
				//更新
				//从前台获取更新人
				if(emailContentQuerytInfoService.saveOrUpdate(entity)){
					return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
				}else{
					return new PublicResult<>(PublicResultConstant.ERROR, null);
				}
			}else{
				return new PublicResult<>(PublicResultConstant.PARAM_ERROR, "修改主键不能为空!",null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
	}

	
	@ApiOperation(value = "触发发送", notes = "触发发送")
	@ApiImplicitParams({ @ApiImplicitParam(name = "monitorEtlEmailContentQueryInfoTrigger", value = "触发发送", paramType = "query") })
	@GetMapping(value = "/monitorEtlEmailContentQueryInfo/trigger", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/monitorEtlEmailContentQueryInfo/trigger")
	public Object trigger(@RequestParam(required=true) String mailId,String mailSendTo,String mailCC,String messageSendTO) {
		try {
//			EmailUtil.sendEmail(Arrays.asList("longandai@163.com".split(",")),null,"测试","测试");
			
			emailContentQuerytInfoService.triggerSend(mailId,mailSendTo,mailCC,messageSendTO,null);//发送
			
			PublicResult result = (PublicResult) mailSettingInfoController.updateNextSendMailTime(mailId, "dmt_sys_diaodu");//更新调度
			if(!"success".equals(result.getMsg())) { return new PublicResult<>(PublicResultConstant.ERROR, "发送成功但是更新调度信息失败!"
					+result.getMsg()); }
			
			return new PublicResult<>(PublicResultConstant.SUCCESS, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED, "");
		}
	}
	
	
	@ApiOperation(value = "试发送", notes = "试发送")
	@ApiImplicitParams({ @ApiImplicitParam(name = "tryTrigger", value = "触发发送", paramType = "query") })
	@GetMapping(value = "/monitorEtlEmailContentQueryInfo/tryTrigger", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/monitorEtlEmailContentQueryInfo/tryTrigger")
	public Object tryTrigger(@RequestParam(required=true) String mailId,
			@RequestParam(required=true) String userId,
			String mailSendTo,
			String mailCC,
			String messageSendTO) {
		try {
			
			emailContentQuerytInfoService.triggerSend(mailId,mailSendTo,mailCC,messageSendTO,userId);//发送
			 
			return new PublicResult<>(PublicResultConstant.SUCCESS, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.ERROR, "");
		}
	}
	
	
	@ApiOperation(value = "单独发送邮件", notes = "单独发送邮件")
	@ApiImplicitParams({ @ApiImplicitParam(name = "单独发送邮件", value = "单独发送邮件", paramType = "query") })
	@GetMapping(value = "/monitorEtlEmailContentQueryInfo/singleTrigger", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/monitorEtlEmailContentQueryInfo/singleTrigger")
	public Object singleTrigger(@RequestParam(required=true) String content,
			@RequestParam(required=true) String userId,
			String mailSendTo,
			String mailCC,
			String messageSendTO) {
		try {
			
			emailContentQuerytInfoService.singleTriggerSend(
					  content,  mailSendTo,  mailCC,  userId,  "");
			 
			return new PublicResult<>(PublicResultConstant.SUCCESS, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.ERROR, "");
		}
	}
	/**
	 * 数据删除
	 * @param request
	 * @param tmplateId
	 * @return
	 */
	@ApiOperation(value = "根据模板Id删除数据", notes = "根据模板Id删除数据")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "tmplateId", value = "邮件Id", required = true, paramType = "query") 
	})
	@DeleteMapping(value = "/monitorEtlEmailContentQueryInfo/deleteBytemplateId", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/monitorEtlEmailContentQueryInfo/deleteBytemplateId")
	public Object deleteByMailId(HttpServletRequest request,String tmplateId) {
		
		User user = getLoginUser(request);
		
		if (null == tmplateId) {
			return new PublicResult<>(PublicResultConstant.INVALID_PARAM_EMPTY, "数据删除失败!");
		}
		
		int total = emailContentQuerytInfoService.deleteByTmplateId(tmplateId, user);
		if (total != -1) {
			return new PublicResult<>(PublicResultConstant.SUCCESS, "数据删除成功!");
		} else {
			return new PublicResult<>(PublicResultConstant.ERROR, "数据删除失败!");
		}
	}
	
	/**
	 * 数据查询
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "数据查询", notes = "数据查询")
	@ApiImplicitParams({ @ApiImplicitParam(name = "MonitorEtlEmailContentQueryInfo", value = "查询条件对象", paramType = "query") })
	@GetMapping(value = "/monitorEtlEmailContentQueryInfo/selectList", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/monitorEtlEmailContentQueryInfo/selectList")
	public Object selectList(HttpServletRequest request, MonitorEtlEmailContentQueryInfo monitorEtlEmailContentQueryInfo) {

		List<MonitorEtlEmailContentQueryInfo> list = emailContentQuerytInfoService.selectListCustom(monitorEtlEmailContentQueryInfo);

		return new PublicResult<>(PublicResultConstant.SUCCESS, list);
	}
	
	
	/**
	 *  查当前模板下所有语句
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "数据查询", notes = "数据查询")
	@ApiImplicitParams({ @ApiImplicitParam(name = "MonitorEtlEmailContentQueryInfo", value = "查询条件对象", paramType = "query") })
	@GetMapping(value = "/monitorEtlEmailContentQueryInfo/selectListByTemplateId", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/monitorEtlEmailContentQueryInfo/selectListByTemplateId")
	public Object selectListByTemplateId(HttpServletRequest request, String templateId) {
		try {
			Wrapper<MonitorEtlEmailContentQueryInfo> wrapper = new EntityWrapper<>();
			wrapper.eq("template_id", templateId).eq("del_flag", "0");
			List<MonitorEtlEmailContentQueryInfo> list = emailContentQuerytInfoService.selectList(wrapper);
			return new PublicResult<>(PublicResultConstant.SUCCESS, list);
		} catch (Exception e) {
			e.getMessage();
			return new PublicResult<>(PublicResultConstant.FAILED, null);
		}
	}
	
	
	/**
	 * @date   2019-01-09
	 * @author zuoqb123
	 * @todo   分页查询平台
	 */
	@ApiOperation(value = "分页查询平台", notes = "分页查询平台", httpMethod = "GET")
	@RequestMapping(value = "/monitorEtlEmailContentQueryInfo/list", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
	public Object list(MonitorEtlEmailContentQueryInfo entity,@RequestParam(value="pageNum",required = false,defaultValue="1") Integer pageNum,
					   @RequestParam(value="pageSize",required = false,defaultValue="1000") Integer pageSize,HttpServletRequest request) {
		try {
			PageInfo<MonitorEtlEmailContentQueryInfo> page=emailContentQuerytInfoService.pageList(this, request, entity, pageNum, pageSize);
			return new PublicResult<>(PublicResultConstant.SUCCESS, page);
		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(),null);
		}

	}
}
