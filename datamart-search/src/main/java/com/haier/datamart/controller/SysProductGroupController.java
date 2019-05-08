package com.haier.datamart.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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
import com.github.pagehelper.PageInfo;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.entity.SysProduct;
import com.haier.datamart.entity.SysProductGroup;
import com.haier.datamart.service.ISysProductGroupService;
/**
 *
 * @author zuoqb123
 * @date 2018-09-29
 * @todo 项目用户路由
 */
@RestController
@RequestMapping("/api/sysProductGroup")
@Api(value = "项目用户",description="项目用户 @author zuoqb123")
public class SysProductGroupController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(SysProductGroupController.class);

    @Autowired
    public ISysProductGroupService iSysProductGroupService;

    /**
     * @date   2018-09-29
     * @author zuoqb123
     * @todo   新增项目用户
     */
  	@ApiOperation(value = "新增/修改项目用户", notes = "新增/修改项目用户", httpMethod = "POST")
	@RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<SysProductGroup> addOrUpdate(HttpServletRequest request,@RequestBody SysProductGroup entity) {
		try {
			if(StringUtils.isNotBlank(entity.getProductId())&&StringUtils.isNotBlank(entity.getGroupId())){
				//先删除
				EntityWrapper<SysProductGroup> wrapper = new EntityWrapper<SysProductGroup>();
	  			wrapper.where("group_id", entity.getGroupId());
				if(iSysProductGroupService.delete(wrapper)){
					//新增
					if(iSysProductGroupService.insert(entity)){
						return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
					}else{
						return new PublicResult<>(PublicResultConstant.ERROR, null);
					}
				}else{
					return new PublicResult<>(PublicResultConstant.ERROR, null);
				}
			}else{
				return new PublicResult<>(PublicResultConstant.PARAM_ERROR, "信息不能为空!",null);
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
     * @todo   删除项目用户
     */
  	@ApiOperation(value = "删除项目用户", notes = "删除项目用户", httpMethod = "DELETE")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE,produces = { "application/json;charset=UTF-8" })
	public PublicResult<SysProductGroup> delete(HttpServletRequest request,@PathVariable("groupId") String groupId) {
		try {
			if(StringUtils.isNotBlank(groupId)){
				EntityWrapper<SysProductGroup> wrapper = new EntityWrapper<SysProductGroup>();
	  			wrapper.where("group_id", groupId);
				if(iSysProductGroupService.delete(wrapper)){
					return new PublicResult<>(PublicResultConstant.SUCCESS, null);
				}else{
					return new PublicResult<>(PublicResultConstant.ERROR, null);
				}
			}else{
				return new PublicResult<>(PublicResultConstant.INVALID_PARAM_EMPTY, null);
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
     * @todo   查询单个项目用户
     */
  	@ApiOperation(value = "查询单个项目用户", notes = "查询单个项目用户", httpMethod = "GET")
  	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" } )
  	public PublicResult<List<SysProductGroup>> get(HttpServletRequest request,@PathVariable("groupId") String groupId) {
  		List<SysProductGroup> entity=null;
  		try {
  			EntityWrapper<SysProductGroup> wrapper = new EntityWrapper<SysProductGroup>();
  			wrapper.where("group_id", groupId);
  			entity=iSysProductGroupService.selectList(wrapper);
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
     * @todo   分页查询项目用户
     */
  	@ApiOperation(value = "分页查询项目用户", notes = "分页查询项目用户", httpMethod = "GET")
  	@RequestMapping(value = "/list", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
    public Object list(SysProductGroup entity,@RequestParam(value="pageNum",required = false,defaultValue="1") Integer pageNum,
			@RequestParam(value="pageSize",required = false,defaultValue="10") Integer pageSize,HttpServletRequest request) {
		try {
			EntityWrapper<SysProductGroup> wrapper = searchWrapper(request, entity);
			PageHelper.startPage(pageNum, pageSize);
			int cu=(pageNum-1)*pageSize;
			Page<SysProductGroup> page=new Page<SysProductGroup>(cu, pageSize);
			page= iSysProductGroupService.selectPage(page, wrapper);
			page.setTotal(iSysProductGroupService.selectCount(wrapper));
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
    private EntityWrapper<SysProductGroup> searchWrapper(HttpServletRequest request, SysProductGroup entity) {
		EntityWrapper<SysProductGroup> wrapper = new EntityWrapper<SysProductGroup>();
		wrapper.where("del_flag={0}", UN_DEL_FLAG);
		if(getLoginUser(request)!=null&&StringUtils.isNotBlank(getLoginUser(request).getId())){
			if(!isAdmin(request))
			 wrapper.and("create_by", getLoginUser(request).getId());
		}
				//根据项目编号模糊查询
		if(entity.getProductId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getProductId()))){
			wrapper.like("product_id", String.valueOf(entity.getProductId()));
		}
				//根据用户组编号模糊查询
		if(entity.getGroupId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getGroupId()))){
			wrapper.like("group_id", String.valueOf(entity.getGroupId()));
		}
				wrapper.orderBy("create_date", true);
		System.out.println(wrapper.originalSql());
		return wrapper;
	}
}

