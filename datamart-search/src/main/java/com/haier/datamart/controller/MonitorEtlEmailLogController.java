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
import com.haier.datamart.service.IMonitorEtlEmailLogService;
import com.haier.datamart.entity.MonitorEtlEmailLog;
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
 * @date 2019-01-25
 * @todo 路由
 */
@RestController
@RequestMapping("/api//monitorEtlEmailLog")
@Api(value = "",description=" @author zuoqb123")
public class MonitorEtlEmailLogController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(MonitorEtlEmailLogController.class);

    @Autowired
    public IMonitorEtlEmailLogService iMonitorEtlEmailLogService;

    /**
     * @date   2019-01-25
     * @author zuoqb123
     * @todo   新增
     */
  	@ApiOperation(value = "新增", notes = "新增", httpMethod = "POST")
	@RequestMapping(value = "/add", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<MonitorEtlEmailLog> add(HttpServletRequest request,@RequestBody MonitorEtlEmailLog entity) {
		try {
			if(StringUtils.isBlank(entity.getId())){
				//新增
				entity.setCreateBy(getLoginUser(request).getId());
				if(iMonitorEtlEmailLogService.saveOrUpdate(entity)){
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
     * @date   2019-01-25
     * @author zuoqb123
     * @todo   删除
     */
  	@ApiOperation(value = "删除", notes = "删除", httpMethod = "POST")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<MonitorEtlEmailLog> delete(HttpServletRequest request,@PathVariable("id") String id) {
		try {
			 if(iMonitorEtlEmailLogService.deleteLogic(id)){
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
     * @date   2019-01-25
     * @author zuoqb123
     * @todo   更新
     */
  	@ApiOperation(value = "更新", notes = "更新", httpMethod = "POST")
	@RequestMapping(value = "/update", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<MonitorEtlEmailLog> update(HttpServletRequest request,MonitorEtlEmailLog entity) {
		try {
			if(entity!=null&&StringUtils.isNotBlank(entity.getId())){
				//更新
				entity.setUpdateBy(getLoginUser(request).getId());
				if(iMonitorEtlEmailLogService.saveOrUpdate(entity)){
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
     * @date   2019-01-25
     * @author zuoqb123
     * @todo   查询单个
     */
  	@ApiOperation(value = "查询单个", notes = "查询单个", httpMethod = "GET")
  	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET ,produces = { "application/json;charset=UTF-8" })
  	public PublicResult<MonitorEtlEmailLog> get(HttpServletRequest request,@PathVariable("id") String id) {
  		try {
  			return new PublicResult<>(PublicResultConstant.SUCCESS, iMonitorEtlEmailLogService.getById(id));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
  	}
	
    /**
     * @date   2019-01-25
     * @author zuoqb123
     * @todo   分页查询
     */
  	@ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "GET")
  	@RequestMapping(value = "/list", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
    public Object list(MonitorEtlEmailLog entity,@RequestParam(value="pageNum",required = false,defaultValue="1") Integer pageNum,
			@RequestParam(value="pageSize",required = false,defaultValue="10") Integer pageSize,HttpServletRequest request) {
		try {
			if(pageNum==0)
				pageNum=1;
			PageInfo<MonitorEtlEmailLog> page=iMonitorEtlEmailLogService.pageList(this, request, entity, pageNum, pageSize);
			return new PublicResult<>(PublicResultConstant.SUCCESS, page);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(),null);
		}

	}
  
}

