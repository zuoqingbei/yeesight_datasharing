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
import com.haier.datamart.entity.MinitorSysErrorFile;

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
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 *
 * @author zuoqb123
 * @date 2018-11-21
 * @todo 路由
 */
@RestController
@RequestMapping("/api//minitorSysErrorFile")
@Api(value = "",description=" @author zuoqb123")
public class MinitorSysErrorFileController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(MinitorSysErrorFileController.class);

    @Autowired
    public IMinitorSysErrorFileService iMinitorSysErrorFileService;

    /**
     * @date   2018-11-21
     * @author zuoqb123
     * @todo   新增
     */
  	@ApiOperation(value = "新增", notes = "新增", httpMethod = "POST")
	@RequestMapping(value = "/add", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<MinitorSysErrorFile> add(HttpServletRequest request,MinitorSysErrorFile entity) {
		try {
			if(StringUtils.isBlank(entity.getId())){
				//新增
				entity.setId(UUIDUtils.getUuid());
				if(iMinitorSysErrorFileService.insert(entity)){
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
	public PublicResult<MinitorSysErrorFile> delete(HttpServletRequest request,@PathVariable("id") String id) {
		try {
			MinitorSysErrorFile entity=new MinitorSysErrorFile();
			entity.setId(id);
			 if(iMinitorSysErrorFileService.updateById(entity)){
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
	public PublicResult<MinitorSysErrorFile> update(HttpServletRequest request,MinitorSysErrorFile entity) {
		try {
			if(entity!=null&&StringUtils.isNotBlank(entity.getId())){
				//更新
				if(iMinitorSysErrorFileService.updateById(entity)){
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
  	public PublicResult<MinitorSysErrorFile> get(HttpServletRequest request,@PathVariable("id") String id) {
  		MinitorSysErrorFile entity=null;
  		try {
  			EntityWrapper<MinitorSysErrorFile> wrapper = new EntityWrapper<MinitorSysErrorFile>();
  			wrapper.where("del_flag={0}", UN_DEL_FLAG);
  			wrapper.eq("id", id);
  			entity=iMinitorSysErrorFileService.selectOne(wrapper);
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
    public PublicResult<PageInfo<MinitorSysErrorFile>> list(MinitorSysErrorFile entity,@RequestParam(value="pageNum",required = false,defaultValue="1") Integer pageNum,
			@RequestParam(value="pageSize",required = false,defaultValue="10") Integer pageSize,HttpServletRequest request) {
		try {
			EntityWrapper<MinitorSysErrorFile> wrapper = searchWrapper(request, entity);
			PageHelper.startPage(pageNum, pageSize);
			List<MinitorSysErrorFile> list = iMinitorSysErrorFileService.selectList(wrapper);
			PageInfo<MinitorSysErrorFile> page = new PageInfo<MinitorSysErrorFile>(list);
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
    private EntityWrapper<MinitorSysErrorFile> searchWrapper(HttpServletRequest request, MinitorSysErrorFile entity) {
		EntityWrapper<MinitorSysErrorFile> wrapper = new EntityWrapper<MinitorSysErrorFile>();
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
		if(entity.getErrorId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getErrorId()))){
			wrapper.like("error_id", String.valueOf(entity.getErrorId()));
		}
				//根据平台模糊查询
		if(entity.getFileId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getFileId()))){
			wrapper.like("file_id", String.valueOf(entity.getFileId()));
		}
				wrapper.orderBy("create_date", true);
		System.out.println(wrapper.originalSql());
		return wrapper;
	}
}

