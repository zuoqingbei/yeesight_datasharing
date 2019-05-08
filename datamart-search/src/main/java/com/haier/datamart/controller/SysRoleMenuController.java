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
import com.haier.datamart.entity.SysProductGroup;
import com.haier.datamart.entity.SysRoleMenu;
import com.haier.datamart.service.ISysRoleMenuService;
/**
 *
 * @author zuoqb123
 * @date 2018-09-29
 * @todo 角色-菜单路由
 */
@RestController
@RequestMapping("/api/sysRoleMenu")
@Api(value = "角色-菜单",description="角色-菜单 @author zuoqb123")
public class SysRoleMenuController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(SysRoleMenuController.class);

    @Autowired
    public ISysRoleMenuService iSysRoleMenuService;

    /**
     * @date   2018-09-29
     * @author zuoqb123
     * @todo   新增角色-菜单
     */
  	@ApiOperation(value = "新增、修改角色-菜单", notes = "新增角色-菜单", httpMethod = "POST")
	@RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<SysRoleMenu> addOrUpdate(HttpServletRequest request,@RequestBody SysRoleMenu entity) {
		try {
			if(StringUtils.isNotBlank(entity.getRoleId())&&StringUtils.isNotBlank(entity.getMenuId())){
				//先删除
				EntityWrapper<SysRoleMenu> wrapper = new EntityWrapper<SysRoleMenu>();
	  			wrapper.where("role_id", entity.getRoleId());
	  			if(iSysRoleMenuService.delete(wrapper)){
					//新增
					if(iSysRoleMenuService.insert(entity)){
						return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
					}else{
						return new PublicResult<>(PublicResultConstant.ERROR, null);
					}
				}else{
					return new PublicResult<>(PublicResultConstant.ERROR, null);
				}
			}else{
				return new PublicResult<>(PublicResultConstant.PARAM_ERROR, "参数不能为空!",null);
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
     * @todo   删除角色-菜单
     */
  	@ApiOperation(value = "删除角色-菜单", notes = "删除角色-菜单", httpMethod = "DELETE")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE,produces = { "application/json;charset=UTF-8" })
	public PublicResult<SysRoleMenu> delete(HttpServletRequest request,@PathVariable("roleId") String roleId) {
		try {
			if(StringUtils.isNotBlank(roleId)){
				EntityWrapper<SysRoleMenu> wrapper = new EntityWrapper<SysRoleMenu>();
	  			wrapper.where("role_id", roleId);
				if(iSysRoleMenuService.delete(wrapper)){
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
     * @todo   查询单个角色-菜单
     */
  	@ApiOperation(value = "查询单个角色-菜单", notes = "查询单个角色-菜单", httpMethod = "GET")
  	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" } )
  	public PublicResult<List<SysRoleMenu>> get(HttpServletRequest request,@PathVariable("roleId") String roleId) {
  		List<SysRoleMenu> entity=null;
  		try {
  			EntityWrapper<SysRoleMenu> wrapper = new EntityWrapper<SysRoleMenu>();
  			wrapper.where("group_id", roleId);
  			entity=iSysRoleMenuService.selectList(wrapper);
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
     * @todo   分页查询角色-菜单
     */
  	@ApiOperation(value = "分页查询角色-菜单", notes = "分页查询角色-菜单", httpMethod = "GET")
  	@RequestMapping(value = "/list", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
    public Object list(SysRoleMenu entity,@RequestParam(value="pageNum",required = false,defaultValue="1") Integer pageNum,
			@RequestParam(value="pageSize",required = false,defaultValue="10") Integer pageSize,HttpServletRequest request) {
		try {
			EntityWrapper<SysRoleMenu> wrapper = searchWrapper(request, entity);
			PageHelper.startPage(pageNum, pageSize);
			int cu=(pageNum-1)*pageSize;
			Page<SysRoleMenu> page=new Page<SysRoleMenu>(cu, pageSize);
			page= iSysRoleMenuService.selectPage(page, wrapper);
			page.setTotal(iSysRoleMenuService.selectCount(wrapper));
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
    private EntityWrapper<SysRoleMenu> searchWrapper(HttpServletRequest request, SysRoleMenu entity) {
		EntityWrapper<SysRoleMenu> wrapper = new EntityWrapper<SysRoleMenu>();
		wrapper.where("del_flag={0}", UN_DEL_FLAG);
		if(getLoginUser(request)!=null&&StringUtils.isNotBlank(getLoginUser(request).getId())){
			if(!isAdmin(request))
			 wrapper.and("create_by", getLoginUser(request).getId());
		}
				//根据角色编号模糊查询
		if(entity.getRoleId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getRoleId()))){
			wrapper.like("role_id", String.valueOf(entity.getRoleId()));
		}
				//根据菜单编号模糊查询
		if(entity.getMenuId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getMenuId()))){
			wrapper.like("menu_id", String.valueOf(entity.getMenuId()));
		}
				wrapper.orderBy("create_date", true);
		System.out.println(wrapper.originalSql());
		return wrapper;
	}
}

