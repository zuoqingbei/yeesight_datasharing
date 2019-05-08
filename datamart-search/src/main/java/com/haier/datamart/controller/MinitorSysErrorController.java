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

import com.haier.datamart.service.IMinitorSysErrorFileService;
import com.haier.datamart.service.IMinitorSysErrorService;
import com.haier.datamart.entity.MinitorSysError;
import com.haier.datamart.entity.MinitorSysErrorFile;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.haier.datamart.utils.UUIDUtils;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 *
 * @author zuoqb123
 * @date 2018-11-21
 * @todo 路由
 */
@RestController
@RequestMapping("/api/minitorSysError")
@Api(value = "",description=" @author zuoqb123")
public class MinitorSysErrorController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(MinitorSysErrorController.class);

    @Autowired
    public IMinitorSysErrorService iMinitorSysErrorService;
    @Autowired
    public IMinitorSysErrorFileService iMinitorSysErrorFileService;

    /**
     * @date   2018-11-21
     * @author zuoqb123
     * @todo   新增
     */
  	@ApiOperation(value = "新增", notes = "新增", httpMethod = "POST")
	@RequestMapping(value = "/add", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<MinitorSysError> add(HttpServletRequest request,MinitorSysError entity) {
		try {
			if(StringUtils.isBlank(entity.getId())){
				//新增
				entity.setId(UUIDUtils.getUuid());
				entity.setCreateDate(new Date());
				entity.setCreateBy(getLoginUser(request).getId());
				if(iMinitorSysErrorService.insert(entity)){
					 EntityWrapper<MinitorSysErrorFile> filesWap = new EntityWrapper<MinitorSysErrorFile>();
		  			 filesWap.eq("error_id", entity.getId());
		  			 iMinitorSysErrorFileService.delete(filesWap);
		  			 for(MinitorSysErrorFile e:entity.getErrorFiles()){
		  				 e.setErrorId(entity.getId());
		  				 e.setId(UUIDUtils.getUuid());
		  				iMinitorSysErrorFileService.insert(e);
		  			 }
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
     * @date   2018-11-21
     * @author zuoqb123
     * @todo   删除
     */
  	@ApiOperation(value = "删除", notes = "删除", httpMethod = "POST")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<MinitorSysError> delete(HttpServletRequest request,@PathVariable("id") String id) {
		try {
			MinitorSysError entity=new MinitorSysError();
			entity.setId(id);
			entity.setDelFlag(DEL_FLAG);
			entity.setUpdateDate(new Date());
			entity.setUpdateBy(getLoginUser(request).getId());
			 if(iMinitorSysErrorService.updateById(entity)){
				 EntityWrapper<MinitorSysErrorFile> filesWap = new EntityWrapper<MinitorSysErrorFile>();
	  			 filesWap.eq("error_id", entity.getId());
	  			 iMinitorSysErrorFileService.delete(filesWap);
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
     * @date   2018-11-21
     * @author zuoqb123
     * @todo   更新
     */
  	@ApiOperation(value = "更新", notes = "更新", httpMethod = "POST")
	@RequestMapping(value = "/update", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<MinitorSysError> update(HttpServletRequest request,MinitorSysError entity) {
		try {
			if(entity!=null&&StringUtils.isNotBlank(entity.getId())){
				//更新
				entity.setUpdateDate(new Date());
				entity.setUpdateBy(getLoginUser(request).getId());
				if(iMinitorSysErrorService.updateById(entity)){
					 EntityWrapper<MinitorSysErrorFile> filesWap = new EntityWrapper<MinitorSysErrorFile>();
		  			 filesWap.eq("error_id", entity.getId());
		  			 iMinitorSysErrorFileService.delete(filesWap);
		  			 for(MinitorSysErrorFile e:entity.getErrorFiles()){
		  				 e.setErrorId(entity.getId());
		  				 e.setId(UUIDUtils.getUuid());
		  				iMinitorSysErrorFileService.insert(e);
		  			 }
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
     * @date   2018-11-21
     * @author zuoqb123
     * @todo   查询单个
     */
  	@ApiOperation(value = "查询单个", notes = "查询单个", httpMethod = "GET")
  	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET ,produces = { "application/json;charset=UTF-8" })
  	public PublicResult<MinitorSysError> get(HttpServletRequest request,@PathVariable("id") String id) {
  		MinitorSysError entity=null;
  		try {
  			EntityWrapper<MinitorSysError> wrapper = new EntityWrapper<MinitorSysError>();
  			wrapper.where("del_flag={0}", UN_DEL_FLAG);
  			wrapper.eq("id", id);
  			entity=iMinitorSysErrorService.selectOne(wrapper);
  			if(entity!=null){
  				EntityWrapper<MinitorSysErrorFile> filesWap = new EntityWrapper<MinitorSysErrorFile>();
  				filesWap.eq("error_id", entity.getId());
  				List<MinitorSysErrorFile> errorFiles = iMinitorSysErrorFileService.selectList(filesWap);
  				entity.setErrorFiles(errorFiles);
  			}
  			return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
  	}
	
    /**
     * @date   2018-11-21
     * @author zuoqb123
     * @todo   分页查询
     */
  	@ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "GET")
  	@RequestMapping(value = "/list", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
    public PublicResult<PageInfo<MinitorSysError>> list(MinitorSysError entity,@RequestParam(value="pageNum",required = false,defaultValue="1") Integer pageNum,
			@RequestParam(value="pageSize",required = false,defaultValue="10") Integer pageSize,HttpServletRequest request) {
		try {
			EntityWrapper<MinitorSysError> wrapper = searchWrapper(request, entity);
			PageHelper.startPage(pageNum, pageSize);
			List<MinitorSysError> list = iMinitorSysErrorService.selectList(wrapper);
			for(MinitorSysError e:list){
				EntityWrapper<MinitorSysErrorFile> filesWap = new EntityWrapper<MinitorSysErrorFile>();
  				filesWap.eq("error_id", entity.getId());
  				List<MinitorSysErrorFile> errorFiles = iMinitorSysErrorFileService.selectList(filesWap);
  				e.setErrorFiles(errorFiles);
			}
			PageInfo<MinitorSysError> page = new PageInfo<MinitorSysError>(list);
			return new PublicResult<>(PublicResultConstant.SUCCESS, page);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(),null);
		}

	}
    /**
     * @date   2018年9月25日 下午5:36:10
     * @author zuoqb123
     * @todo   构建查询条件-以后扩展
     */
    private EntityWrapper<MinitorSysError> searchWrapper(HttpServletRequest request, MinitorSysError entity) {
		EntityWrapper<MinitorSysError> wrapper = new EntityWrapper<MinitorSysError>();
		wrapper.where("del_flag={0}", UN_DEL_FLAG);
		if(getLoginUser(request)!=null&&StringUtils.isNotBlank(getLoginUser(request).getId())){
			if(!isAdmin(request))
			 wrapper.and("create_by", getLoginUser(request).getId());
		}
				//根据模糊查询
		if(entity.getId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getId()))){
			wrapper.like("id", String.valueOf(entity.getId()));
		}
				//根据系统模糊查询
		if(entity.getSystem()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getSystem()))){
			wrapper.like("system", String.valueOf(entity.getSystem()));
		}
				//根据平台模糊查询
		if(entity.getPlat()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getPlat()))){
			wrapper.like("plat", String.valueOf(entity.getPlat()));
		}
				//根据报表模糊查询
		if(entity.getReportId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getReportId()))){
			wrapper.like("report_id", String.valueOf(entity.getReportId()));
		}
				//根据指标模糊查询
		if(entity.getIndexId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getIndexId()))){
			wrapper.like("index_id", String.valueOf(entity.getIndexId()));
		}
				//根据错误信息描述模糊查询
		if(entity.getDescs()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getDescs()))){
			wrapper.like("descs", String.valueOf(entity.getDescs()));
		}
				//根据模糊查询
		if(entity.getRemarks()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getRemarks()))){
			wrapper.like("remarks", String.valueOf(entity.getRemarks()));
		}
				//根据模糊查询
		if(entity.getCreateBy()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getCreateBy()))){
			wrapper.like("create_by", String.valueOf(entity.getCreateBy()));
		}
				//根据模糊查询
		if(entity.getCreateDate()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getCreateDate()))){
			wrapper.like("create_date", String.valueOf(entity.getCreateDate()));
		}
				//根据模糊查询
		if(entity.getUpdateBy()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getUpdateBy()))){
			wrapper.like("update_by", String.valueOf(entity.getUpdateBy()));
		}
				//根据模糊查询
		if(entity.getUpdateDate()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getUpdateDate()))){
			wrapper.like("update_date", String.valueOf(entity.getUpdateDate()));
		}
				//根据模糊查询
		if(entity.getDelFlag()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getDelFlag()))){
			wrapper.like("del_flag", String.valueOf(entity.getDelFlag()));
		}
				wrapper.orderBy("create_date", true);
		System.out.println(wrapper.originalSql());
		return wrapper;
	}
}

