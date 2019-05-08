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
import com.haier.datamart.service.IMailPlatInfoService;
import com.haier.datamart.entity.MailPlatInfo;
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
import org.springframework.web.bind.annotation.RequestBody;
import com.baomidou.mybatisplus.plugins.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 *
 * @author zuoqb123
 * @date 2019-01-09
 * @todo 平台路由
 */
@RestController
@RequestMapping("/api/mailPlatInfo")
@Api(value = "平台",description="平台 @author zuoqb123")
public class MailPlatInfoController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(MailPlatInfoController.class);

    @Autowired
    public IMailPlatInfoService iMailPlatInfoService;

    /**
     * @date   2019-01-09
     * @author zuoqb123
     * @todo   新增平台
     */
  	@ApiOperation(value = "新增平台", notes = "新增平台", httpMethod = "POST")
	@RequestMapping(value = "/add", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<MailPlatInfo> add(HttpServletRequest request,MailPlatInfo entity) {
		try {
			if(StringUtils.isBlank(entity.getId())){
				//新增
				entity.setCreateBy(getLoginUser(request).getId());
				//新增平台首先设置为未就绪状态
				//0 未就绪 1 审批通过 就绪
				entity.setIsComplete("0");
				if(iMailPlatInfoService.saveOrUpdate(entity)){
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
     * @date   2019-01-09
     * @author zuoqb123
     * @todo   删除平台
     */
  	@ApiOperation(value = "删除平台", notes = "删除平台", httpMethod = "POST")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<MailPlatInfo> delete(HttpServletRequest request,@PathVariable("id") String id) {
		try {
			 if(iMailPlatInfoService.deleteLogic(id)){
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
     * @date   2019-01-09
     * @author zuoqb123
     * @todo   更新平台
     */
  	@ApiOperation(value = "更新平台", notes = "更新平台", httpMethod = "POST")
	@RequestMapping(value = "/update", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<MailPlatInfo> update(HttpServletRequest request,MailPlatInfo entity,@RequestParam(required=true)String userId) {
		try {
			if(entity!=null&&StringUtils.isNotBlank(entity.getId())){
				//更新
				//entity.setUpdateBy(getLoginUser(request).getId());
				entity.setUpdateBy(userId);
				if(iMailPlatInfoService.saveOrUpdate(entity)){
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
     * @date   2019-01-09
     * @author zuoqb123
     * @todo   查询单个平台
     */
  	@ApiOperation(value = "查询单个平台", notes = "查询单个平台", httpMethod = "GET")
  	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET ,produces = { "application/json;charset=UTF-8" })
  	public PublicResult<MailPlatInfo> get(HttpServletRequest request,@PathVariable("id") String id) {
  		try {
  			return new PublicResult<>(PublicResultConstant.SUCCESS, iMailPlatInfoService.getById(id));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
  	}
	
    /**
     * @date   2019-01-09
     * @author zuoqb123
     * @todo   分页查询平台
     */
  	@ApiOperation(value = "分页查询平台", notes = "分页查询平台", httpMethod = "GET")
  	@RequestMapping(value = "/list", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
    public Object list(MailPlatInfo entity,@RequestParam(value="pageNum",required = false,defaultValue="1") Integer pageNum,
			@RequestParam(value="pageSize",required = false,defaultValue="1000") Integer pageSize,HttpServletRequest request) {
		try {
			PageInfo<MailPlatInfo> page=iMailPlatInfoService.pageList(this, request, entity, pageNum, pageSize);
			return new PublicResult<>(PublicResultConstant.SUCCESS, page);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(),null);
		}

	}
  
  	
  	
  	
}

