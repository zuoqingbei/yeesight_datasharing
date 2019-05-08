package com.haier.datamart.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.github.pagehelper.PageHelper;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.entity.SysProductExc;
import com.haier.datamart.service.ISysProductExcService;
import com.haier.datamart.utils.UUIDUtils;
/**
 *
 * @author zuoqb123
 * @date 2018-12-11
 * @todo 项目接口路由
 */
@RestController
@RequestMapping("/api//sysProductExc")
@Api(value = "项目接口",description="项目接口 @author zuoqb123")
public class SysProductExcController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(SysProductExcController.class);

    @Autowired
    public ISysProductExcService iSysProductExcService;

    /**
     * @date   2018-12-11
     * @author zuoqb123
     * @todo   新增项目接口
     */
  	@ApiOperation(value = "新增项目接口", notes = "新增项目接口", httpMethod = "POST")
	@RequestMapping(value = "/add", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<SysProductExc> add(HttpServletRequest request,@RequestBody SysProductExc entity) {
		try {
			if(StringUtils.isBlank(entity.getId())){
				//新增
				entity.setId(UUIDUtils.getUuid());
				if(iSysProductExcService.insert(entity)){
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
     * @date   2018-12-11
     * @author zuoqb123
     * @todo   删除项目接口
     */
  	@ApiOperation(value = "删除项目接口", notes = "删除项目接口", httpMethod = "POST")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<SysProductExc> delete(HttpServletRequest request,@PathVariable("id") String id) {
		try {
			SysProductExc entity=new SysProductExc();
			entity.setId(id);
			 if(iSysProductExcService.updateById(entity)){
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
     * @date   2018-12-11
     * @author zuoqb123
     * @todo   更新项目接口
     */
  	@ApiOperation(value = "更新项目接口", notes = "更新项目接口", httpMethod = "POST")
	@RequestMapping(value = "/update", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<SysProductExc> update(HttpServletRequest request,SysProductExc entity) {
		try {
			if(entity!=null&&StringUtils.isNotBlank(entity.getId())){
				//更新
				if(iSysProductExcService.updateById(entity)){
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
     * @date   2018-12-11
     * @author zuoqb123
     * @todo   查询单个项目接口
     */
  	@ApiOperation(value = "查询单个项目接口", notes = "查询单个项目接口", httpMethod = "GET")
  	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET ,produces = { "application/json;charset=UTF-8" })
  	public PublicResult<SysProductExc> get(HttpServletRequest request,@PathVariable("id") String id) {
  		SysProductExc entity=null;
  		try {
  			EntityWrapper<SysProductExc> wrapper = new EntityWrapper<SysProductExc>();
  			wrapper.where("del_flag={0}", UN_DEL_FLAG);
  			wrapper.eq("id", id);
  			entity=iSysProductExcService.selectOne(wrapper);
  			return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
  	}
	
    /**
     * @date   2018-12-11
     * @author zuoqb123
     * @todo   分页查询项目接口
     */
  	@ApiOperation(value = "分页查询项目接口", notes = "分页查询项目接口", httpMethod = "GET")
  	@RequestMapping(value = "/list", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
    public Object list(SysProductExc entity,@RequestParam(value="pageNum",required = false,defaultValue="1") Integer pageNum,
			@RequestParam(value="pageSize",required = false,defaultValue="10") Integer pageSize,HttpServletRequest request) {
		try {
			EntityWrapper<SysProductExc> wrapper = searchWrapper(request, entity);
			PageHelper.startPage(pageNum, pageSize);
			int cu=(pageNum-1)*pageSize;
			Page<SysProductExc> page=new Page<SysProductExc>(cu, pageSize);
			page = iSysProductExcService.selectPage(page,wrapper);
			page.setTotal(iSysProductExcService.selectCount(wrapper));
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
    private EntityWrapper<SysProductExc> searchWrapper(HttpServletRequest request, SysProductExc entity) {
		EntityWrapper<SysProductExc> wrapper = new EntityWrapper<SysProductExc>();
		wrapper.where("del_flag={0}", UN_DEL_FLAG);
		if(getLoginUser(request)!=null&&StringUtils.isNotBlank(getLoginUser(request).getId())){
			if(!isAdmin(request))
			 wrapper.eq("create_by", getLoginUser(request).getId());
		}
				//根据模糊查询
		if(entity.getId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getId()))){
			wrapper.like("id", String.valueOf(entity.getId()));
		}
				//根据项目编号模糊查询
		if(entity.getProductId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getProductId()))){
			wrapper.like("product_id", String.valueOf(entity.getProductId()));
		}
				//根据接口编号模糊查询
		if(entity.getExcId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getExcId()))){
			wrapper.like("exc_id", String.valueOf(entity.getExcId()));
		}
				wrapper.orderBy("create_date", true);
		System.out.println(wrapper.originalSql());
		return wrapper;
	}
}

