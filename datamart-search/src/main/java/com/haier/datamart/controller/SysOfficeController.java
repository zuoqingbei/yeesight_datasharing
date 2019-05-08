package com.haier.datamart.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Date;

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
import com.haier.datamart.entity.SysOffice;
import com.haier.datamart.service.ISysOfficeService;
import com.haier.datamart.utils.UUIDUtils;
/**
 *
 * @author zuoqb123
 * @date 2018-12-05
 * @todo 机构表路由
 */
@RestController
@RequestMapping("/api//sysOffice")
@Api(value = "机构表",description="机构表 @author zuoqb123")
public class SysOfficeController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(SysOfficeController.class);

    @Autowired
    public ISysOfficeService iSysOfficeService;

    /**
     * @date   2018-12-05
     * @author zuoqb123
     * @todo   新增机构表
     */
  	@ApiOperation(value = "新增机构表", notes = "新增机构表", httpMethod = "POST")
	@RequestMapping(value = "/add", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<SysOffice> add(HttpServletRequest request,@RequestBody SysOffice entity) {
		try {
			if(StringUtils.isBlank(entity.getId())){
				//新增
				entity.setId(UUIDUtils.getUuid());
				entity.setCreateDate(new Date());
				entity.setCreateBy(getLoginUser(request).getId());
				if(iSysOfficeService.insert(entity)){
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
     * @date   2018-12-05
     * @author zuoqb123
     * @todo   删除机构表
     */
  	@ApiOperation(value = "删除机构表", notes = "删除机构表", httpMethod = "POST")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<SysOffice> delete(HttpServletRequest request,@PathVariable("id") String id) {
		try {
			SysOffice entity=new SysOffice();
			entity.setId(id);
			entity.setDelFlag(DEL_FLAG);
			entity.setUpdateDate(new Date());
			entity.setUpdateBy(getLoginUser(request).getId());
			 if(iSysOfficeService.updateById(entity)){
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
     * @date   2018-12-05
     * @author zuoqb123
     * @todo   更新机构表
     */
  	@ApiOperation(value = "更新机构表", notes = "更新机构表", httpMethod = "POST")
	@RequestMapping(value = "/update", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<SysOffice> update(HttpServletRequest request,SysOffice entity) {
		try {
			if(entity!=null&&StringUtils.isNotBlank(entity.getId())){
				//更新
				entity.setUpdateDate(new Date());
				entity.setUpdateBy(getLoginUser(request).getId());
				if(iSysOfficeService.updateById(entity)){
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
     * @date   2018-12-05
     * @author zuoqb123
     * @todo   查询单个机构表
     */
  	@ApiOperation(value = "查询单个机构表", notes = "查询单个机构表", httpMethod = "GET")
  	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET ,produces = { "application/json;charset=UTF-8" })
  	public PublicResult<SysOffice> get(HttpServletRequest request,@PathVariable("id") String id) {
  		SysOffice entity=null;
  		try {
  			EntityWrapper<SysOffice> wrapper = new EntityWrapper<SysOffice>();
  			wrapper.where("del_flag={0}", UN_DEL_FLAG);
  			wrapper.eq("id", id);
  			entity=iSysOfficeService.selectOne(wrapper);
  			return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
  	}
  	 /**
     * @date   2018-12-05
     * @author zuoqb123
     * @todo   查询机构表
     */
  	@ApiOperation(value = "查询机构表-按照层级", notes = "查询机构表-层级", httpMethod = "GET")
  	@RequestMapping(value = "/allList", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
    public Object list(SysOffice entity,HttpServletRequest request) {
		try {
			return new PublicResult<>(PublicResultConstant.SUCCESS, iSysOfficeService.getAllSysOffice(entity));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(),null);
		}

	}
	
    /**
     * @date   2018-12-05
     * @author zuoqb123
     * @todo   分页查询机构表
     */
  	@ApiOperation(value = "分页查询机构表", notes = "分页查询机构表", httpMethod = "GET")
  	@RequestMapping(value = "/list", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
    public Object list(SysOffice entity,@RequestParam(value="pageNum",required = false,defaultValue="1") Integer pageNum,
			@RequestParam(value="pageSize",required = false,defaultValue="10") Integer pageSize,HttpServletRequest request) {
		try {
			EntityWrapper<SysOffice> wrapper = searchWrapper(request, entity);
			PageHelper.startPage(pageNum, pageSize);
			int cu=(pageNum-1)*pageSize;
			Page<SysOffice> page=new Page<SysOffice>(cu, pageSize);
			page = iSysOfficeService.selectPage(page,wrapper);
			page.setTotal(iSysOfficeService.selectCount(wrapper));
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
    private EntityWrapper<SysOffice> searchWrapper(HttpServletRequest request, SysOffice entity) {
		EntityWrapper<SysOffice> wrapper = new EntityWrapper<SysOffice>();
		wrapper.where("del_flag={0}", UN_DEL_FLAG);
		if(getLoginUser(request)!=null&&StringUtils.isNotBlank(getLoginUser(request).getId())){
			if(!isAdmin(request))
			 wrapper.eq("create_by", getLoginUser(request).getId());
		}
				//根据编号模糊查询
		if(entity.getId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getId()))){
			wrapper.like("id", String.valueOf(entity.getId()));
		}
				//根据父级编号模糊查询
		if(entity.getParentId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getParentId()))){
			wrapper.like("parent_id", String.valueOf(entity.getParentId()));
		}
				//根据所有父级编号模糊查询
		if(entity.getParentIds()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getParentIds()))){
			wrapper.like("parent_ids", String.valueOf(entity.getParentIds()));
		}
				//根据名称模糊查询
		if(entity.getName()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getName()))){
			wrapper.like("name", String.valueOf(entity.getName()));
		}
				//根据排序模糊查询
		if(entity.getSort()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getSort()))){
			wrapper.like("sort", String.valueOf(entity.getSort()));
		}
				//根据归属区域模糊查询
		if(entity.getAreaId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getAreaId()))){
			wrapper.like("area_id", String.valueOf(entity.getAreaId()));
		}
				//根据区域编码模糊查询
		if(entity.getCode()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getCode()))){
			wrapper.like("code", String.valueOf(entity.getCode()));
		}
				//根据机构类型模糊查询
		if(entity.getType()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getType()))){
			wrapper.like("type", String.valueOf(entity.getType()));
		}
				//根据机构等级模糊查询
		if(entity.getGrade()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getGrade()))){
			wrapper.like("grade", String.valueOf(entity.getGrade()));
		}
				//根据联系地址模糊查询
		if(entity.getAddress()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getAddress()))){
			wrapper.like("address", String.valueOf(entity.getAddress()));
		}
				//根据邮政编码模糊查询
		if(entity.getZipCode()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getZipCode()))){
			wrapper.like("zip_code", String.valueOf(entity.getZipCode()));
		}
				//根据负责人模糊查询
		if(entity.getMaster()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getMaster()))){
			wrapper.like("master", String.valueOf(entity.getMaster()));
		}
				//根据电话模糊查询
		if(entity.getPhone()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getPhone()))){
			wrapper.like("phone", String.valueOf(entity.getPhone()));
		}
				//根据传真模糊查询
		if(entity.getFax()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getFax()))){
			wrapper.like("fax", String.valueOf(entity.getFax()));
		}
				//根据邮箱模糊查询
		if(entity.getEmail()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getEmail()))){
			wrapper.like("email", String.valueOf(entity.getEmail()));
		}
				//根据是否启用模糊查询
		if(entity.getUseable()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getUseable()))){
			wrapper.like("USEABLE", String.valueOf(entity.getUseable()));
		}
				//根据创建者模糊查询
		if(entity.getCreateBy()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getCreateBy()))){
			wrapper.like("create_by", String.valueOf(entity.getCreateBy()));
		}
				//根据创建时间模糊查询
		if(entity.getCreateDate()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getCreateDate()))){
			wrapper.like("create_date", String.valueOf(entity.getCreateDate()));
		}
				//根据更新者模糊查询
		if(entity.getUpdateBy()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getUpdateBy()))){
			wrapper.like("update_by", String.valueOf(entity.getUpdateBy()));
		}
				//根据更新时间模糊查询
		if(entity.getUpdateDate()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getUpdateDate()))){
			wrapper.like("update_date", String.valueOf(entity.getUpdateDate()));
		}
				//根据备注信息模糊查询
		if(entity.getRemarks()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getRemarks()))){
			wrapper.like("remarks", String.valueOf(entity.getRemarks()));
		}
				//根据删除标记模糊查询
		if(entity.getDelFlag()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getDelFlag()))){
			wrapper.like("del_flag", String.valueOf(entity.getDelFlag()));
		}
				wrapper.orderBy("create_date", true);
		System.out.println(wrapper.originalSql());
		return wrapper;
	}
}

