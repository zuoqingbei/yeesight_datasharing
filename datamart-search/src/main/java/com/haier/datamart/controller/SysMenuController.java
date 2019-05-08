package com.haier.datamart.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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
import com.github.pagehelper.PageInfo;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.entity.SysGroupRole;
import com.haier.datamart.entity.SysMenu;
import com.haier.datamart.service.ISysMenuService;
import com.haier.datamart.utils.UUIDUtils;
/**
 *
 * @author zuoqb123
 * @date 2018-09-30
 * @todo 菜单表路由
 */
@RestController
@RequestMapping("/api/sysMenu")
@Api(value = "菜单表",description="菜单表 @author zuoqb123")
public class SysMenuController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(SysMenuController.class);

    @Autowired
    public ISysMenuService iSysMenuService;

    /**
     * @date   2018-09-30
     * @author zuoqb123
     * @todo   新增菜单表
     */
  	@ApiOperation(value = "新增菜单表", notes = "新增菜单表", httpMethod = "POST")
	@RequestMapping(value = "/add", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<SysMenu> add(HttpServletRequest request, SysMenu entity) {
		try {
			if(StringUtils.isBlank(entity.getId())){
				//新增
				entity.setId(UUIDUtils.getUuid());
				entity.setCreateDate(new Date());
				entity.setCreateBy(getLoginUser(request).getId());
				if(iSysMenuService.insert(entity)){
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
     * @date   2018-09-30
     * @author zuoqb123
     * @todo   删除菜单表
     */
  	@ApiOperation(value = "删除菜单表", notes = "删除菜单表", httpMethod = "DELETE")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE,produces = { "application/json;charset=UTF-8" })
	public PublicResult<SysMenu> delete(HttpServletRequest request,@PathVariable("id") String id) {
		try {
			SysMenu entity=new SysMenu();
			entity.setId(id);
			entity.setDelFlag(DEL_FLAG);
			entity.setUpdateDate(new Date());
			entity.setUpdateBy(getLoginUser(request).getId());
			 if(iSysMenuService.updateById(entity)){
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
     * @date   2018-09-30
     * @author zuoqb123
     * @todo   更新菜单表
     */
  	@ApiOperation(value = "更新菜单表", notes = "更新菜单表", httpMethod = "PUT")
	@RequestMapping(value = "/update", method = RequestMethod.PUT,produces = { "application/json;charset=UTF-8" })
	public PublicResult<SysMenu> update(HttpServletRequest request,SysMenu entity) {
		try {
			if(entity!=null&&StringUtils.isNotBlank(entity.getId())){
				//更新
				entity.setUpdateDate(new Date());
				entity.setUpdateBy(getLoginUser(request).getId());
				if(iSysMenuService.updateById(entity)){
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
     * @date   2018-09-30
     * @author zuoqb123
     * @todo   查询单个菜单表
     */
  	@ApiOperation(value = "查询单个菜单表", notes = "查询单个菜单表", httpMethod = "GET")
  	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" } )
  	public PublicResult<SysMenu> get(HttpServletRequest request,@PathVariable("id") String id) {
  		SysMenu entity=null;
  		try {
  			EntityWrapper<SysMenu> wrapper = new EntityWrapper<SysMenu>();
  			wrapper.where("del_flag={0}", UN_DEL_FLAG);
  			wrapper.eq("id", id);
  			entity=iSysMenuService.selectOne(wrapper);
  			return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
  	}
	
    /**
     * @date   2018-09-30
     * @author zuoqb123
     * @todo   分页查询菜单表
     */
  	@ApiOperation(value = "分页查询菜单表", notes = "分页查询菜单表", httpMethod = "GET")
  	@RequestMapping(value = "/list", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
    public Object list(SysMenu entity,@RequestParam(value="pageNum",required = false,defaultValue="1") Integer pageNum,
			@RequestParam(value="pageSize",required = false,defaultValue="10") Integer pageSize,HttpServletRequest request) {
		try {
			EntityWrapper<SysMenu> wrapper = searchWrapper(request, entity);
			PageHelper.startPage(pageNum, pageSize);
			int cu=(pageNum-1)*pageSize;
			Page<SysMenu> page=new Page<SysMenu>(cu, pageSize);
			page= iSysMenuService.selectPage(page, wrapper);
			page.setTotal(iSysMenuService.selectCount(wrapper));
			return new PublicResult<>(PublicResultConstant.SUCCESS, page);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(),null);
		}

	}
	@ApiOperation(value = "分页查询菜单表", notes = "分页查询菜单表", httpMethod = "GET")
  	@RequestMapping(value = "/allMenus", method = RequestMethod.GET)
    public Object allMenus(SysMenu entity,HttpServletRequest request) {
		try {
			if(StringUtils.isBlank(entity.getParentId())){
				entity.setParentId("0");
			}
			List<SysMenu> list = iSysMenuService.findList(entity);
			return new PublicResult<>(PublicResultConstant.SUCCESS, list);
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
    private EntityWrapper<SysMenu> searchWrapper(HttpServletRequest request, SysMenu entity) {
		EntityWrapper<SysMenu> wrapper = new EntityWrapper<SysMenu>();
		wrapper.where("del_flag={0}", UN_DEL_FLAG);
		if(getLoginUser(request)!=null&&StringUtils.isNotBlank(getLoginUser(request).getId())){
			if(!isAdmin(request))
			 wrapper.and("create_by", getLoginUser(request).getId());
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
				//根据菜单类型 0-后台菜单 1-补录菜单模糊查询
		if(entity.getMenuType()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getMenuType()))){
			wrapper.like("menu_type", String.valueOf(entity.getMenuType()));
		}
				//根据链接模糊查询
		if(entity.getHref()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getHref()))){
			wrapper.like("href", String.valueOf(entity.getHref()));
		}
				//根据目标模糊查询
		if(entity.getTarget()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getTarget()))){
			wrapper.like("target", String.valueOf(entity.getTarget()));
		}
				//根据图标模糊查询
		if(entity.getIcon()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getIcon()))){
			wrapper.like("icon", String.valueOf(entity.getIcon()));
		}
				//根据是否在菜单中显示 0-显示 1-隐藏模糊查询
		if(entity.getIsShow()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getIsShow()))){
			wrapper.like("is_show", String.valueOf(entity.getIsShow()));
		}
				//根据权限标识模糊查询
		if(entity.getPermission()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getPermission()))){
			wrapper.like("permission", String.valueOf(entity.getPermission()));
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
				//根据备注信息(存储类名)模糊查询
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

