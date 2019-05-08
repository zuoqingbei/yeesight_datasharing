package com.haier.datamart.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.haier.datamart.entity.DocUploadFile;
import com.haier.datamart.entity.SysProduct;
import com.haier.datamart.entity.SysProductExc;
import com.haier.datamart.entity.SysProductGroup;
import com.haier.datamart.entity.SysUser;
import com.haier.datamart.service.ISysGroupService;
import com.haier.datamart.service.ISysProductExcService;
import com.haier.datamart.service.ISysProductGroupService;
import com.haier.datamart.service.ISysProductService;
import com.haier.datamart.utils.UUIDUtils;
/**
 *
 * @author zuoqb123
 * @date 2018-09-29
 * @todo 项目路由
 */
@RestController
@RequestMapping("/api/sysProduct")
@Api(value = "项目",description="项目 @author zuoqb123")
public class SysProductController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(SysProductController.class);

    @Autowired
    public ISysProductService iSysProductService;
    @Autowired
    public ISysProductExcService iSysProductExcService;
    @Autowired
    public ISysProductGroupService iSysProductGroupService;

    /**
     * @date   2018-09-29
     * @author zuoqb123
     * @todo   新增项目
     */
  	@ApiOperation(value = "新增项目", notes = "新增项目", httpMethod = "POST")
	@RequestMapping(value = "/add", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<SysProduct> add(HttpServletRequest request,@RequestBody SysProduct entity) {
		try {
			if(StringUtils.isBlank(entity.getId())){
				//新增
				entity.setId(UUIDUtils.getUuid());
				entity.setCreateDate(new Date());
				entity.setCreateBy(getLoginUser(request).getId());
				if(iSysProductService.insert(entity)){
					//保存项目与接口
					for(SysProductExc ex:entity.getSysProductExc()){
						ex.setId(UUIDUtils.getUuid());
						ex.setProductId(entity.getId());
						iSysProductExcService.insert(ex);
					};
					//保存项目与用户组
					for(SysProductGroup pg:entity.getSysProductGroup()){
						pg.setProductId(entity.getId());
						iSysProductGroupService.insert(pg);
					};
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
     * @date   2018-09-29
     * @author zuoqb123
     * @todo   删除项目
     */
  	@ApiOperation(value = "删除项目", notes = "删除项目", httpMethod = "DELETE")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE,produces = { "application/json;charset=UTF-8" })
	public PublicResult<SysProduct> delete(HttpServletRequest request,@PathVariable("id") String id) {
		try {
			SysProduct entity=new SysProduct();
			entity.setId(id);
			entity.setDelFlag(DEL_FLAG);
			entity.setUpdateDate(new Date());
			entity.setUpdateBy(getLoginUser(request).getId());
			 if(iSysProductService.updateById(entity)){
				//删除项目与接口
				EntityWrapper<SysProductExc> wrapperSysProductExc = new EntityWrapper<SysProductExc>();
				wrapperSysProductExc.where("1=1");
				wrapperSysProductExc.eq("product_id", entity.getId());
				iSysProductExcService.delete(wrapperSysProductExc);
				
				//删除项目与用户组
	  			EntityWrapper<SysProductGroup> wrapperSysProductGroup = new EntityWrapper<SysProductGroup>();
	  			wrapperSysProductGroup.where("1=1");
	  			wrapperSysProductGroup.eq("product_id", entity.getId());
	  			iSysProductGroupService.delete(wrapperSysProductGroup);
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
     * @date   2018-09-29
     * @author zuoqb123
     * @todo   更新项目
     */
  	@ApiOperation(value = "更新项目", notes = "更新项目", httpMethod = "PUT")
	@RequestMapping(value = "/update", method = RequestMethod.PUT,produces = { "application/json;charset=UTF-8" })
	public PublicResult<SysProduct> update(HttpServletRequest request,@RequestBody SysProduct entity) {
		try {
			if(entity!=null&&StringUtils.isNotBlank(entity.getId())){
				//更新
				entity.setUpdateDate(new Date());
				entity.setUpdateBy(getLoginUser(request).getId());
				if(iSysProductService.updateById(entity)){
					//删除项目与接口
					EntityWrapper<SysProductExc> wrapperSysProductExc = new EntityWrapper<SysProductExc>();
					wrapperSysProductExc.where("1=1");
					wrapperSysProductExc.eq("product_id", entity.getId());
					iSysProductExcService.delete(wrapperSysProductExc);
					
					//删除项目与用户组
		  			EntityWrapper<SysProductGroup> wrapperSysProductGroup = new EntityWrapper<SysProductGroup>();
		  			wrapperSysProductGroup.where("1=1");
		  			wrapperSysProductGroup.eq("product_id", entity.getId());
		  			iSysProductGroupService.delete(wrapperSysProductGroup);
		  			
		  			//保存项目与接口
					for(SysProductExc ex:entity.getSysProductExc()){
						ex.setId(UUIDUtils.getUuid());
						ex.setProductId(entity.getId());
						iSysProductExcService.insert(ex);
					};
					//保存项目与用户组
					for(SysProductGroup pg:entity.getSysProductGroup()){
						pg.setProductId(entity.getId());
						iSysProductGroupService.insert(pg);
					};
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
     * @date   2018-09-29
     * @author zuoqb123
     * @todo   查询单个项目
     */
  	@ApiOperation(value = "查询单个项目", notes = "查询单个项目", httpMethod = "GET")
  	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" } )
  	public PublicResult<SysProduct> get(HttpServletRequest request,@PathVariable("id") String id) {
  		SysProduct entity=null;
  		try {
  			EntityWrapper<SysProduct> wrapper = new EntityWrapper<SysProduct>();
  			wrapper.where("del_flag={0}", UN_DEL_FLAG);
  			wrapper.eq("id", id);
  			entity=iSysProductService.selectOne(wrapper);
  			if(entity!=null){
	  			//项目与接口
				EntityWrapper<SysProductExc> wrapperSysProductExc = new EntityWrapper<SysProductExc>();
				wrapperSysProductExc.where("1=1");
				wrapperSysProductExc.eq("product_id", id);
	  			entity.setSysProductExc(iSysProductExcService.selectList(wrapperSysProductExc));
				//项目与用户组
	  			EntityWrapper<SysProductGroup> wrapperSysProductGroup = new EntityWrapper<SysProductGroup>();
	  			wrapperSysProductGroup.where("1=1");
	  			wrapperSysProductGroup.eq("product_id", id);
	  			entity.setSysProductGroup(iSysProductGroupService.selectList(wrapperSysProductGroup));
  			}
  			return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
  	}
	
    /**
     * @date   2018-09-29
     * @author zuoqb123
     * @todo   分页查询项目
     */
  	@ApiOperation(value = "分页查询项目", notes = "分页查询项目", httpMethod = "GET")
  	@RequestMapping(value = "/list", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
    public Object list(SysProduct entity,@RequestParam(value="pageNum",required = false,defaultValue="1") Integer pageNum,
			@RequestParam(value="pageSize",required = false,defaultValue="10") Integer pageSize,HttpServletRequest request) {
		try {
			EntityWrapper<SysProduct> wrapper = searchWrapper(request, entity);
			PageHelper.startPage(pageNum, pageSize);
			int cu=(pageNum-1)*pageSize;
			List<SysProduct> l=iSysProductService.selectList(wrapper);
			Page<SysProduct> page=new Page<SysProduct>(cu, pageSize);
			page.setRecords(l);
			page.setTotal(iSysProductService.selectCount(wrapper));
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
    private EntityWrapper<SysProduct> searchWrapper(HttpServletRequest request, SysProduct entity) {
		EntityWrapper<SysProduct> wrapper = new EntityWrapper<SysProduct>();
		wrapper.where("del_flag={0}", UN_DEL_FLAG);
		if(getLoginUser(request)!=null&&StringUtils.isNotBlank(getLoginUser(request).getId())){
			if(!isAdmin(request))
			 wrapper.and("create_by", getLoginUser(request).getId());
		}
				//根据编号模糊查询
		if(entity.getId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getId()))){
			wrapper.like("id", String.valueOf(entity.getId()));
		}
				//根据项目名称模糊查询
		if(entity.getName()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getName()))){
			wrapper.like("name", String.valueOf(entity.getName()));
		}
				//根据英文名称模糊查询
		if(entity.getEnname()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getEnname()))){
			wrapper.like("enname", String.valueOf(entity.getEnname()));
		}
				//根据项目管理员模糊查询
		if(entity.getManager()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getManager()))){
			wrapper.like("manager", String.valueOf(entity.getManager()));
		}
				//根据是否可用模糊查询
		if(entity.getUseable()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getUseable()))){
			wrapper.like("useable", String.valueOf(entity.getUseable()));
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

