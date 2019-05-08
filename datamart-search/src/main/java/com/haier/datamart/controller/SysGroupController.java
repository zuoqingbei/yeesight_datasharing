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
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.entity.SysGroup;
import com.haier.datamart.entity.SysGroupRole;
import com.haier.datamart.entity.SysProductGroup;
import com.haier.datamart.entity.SysUser;
import com.haier.datamart.entity.SysUserGroup;
import com.haier.datamart.service.ISysGroupRoleService;
import com.haier.datamart.service.ISysGroupService;
import com.haier.datamart.service.ISysProductGroupService;
import com.haier.datamart.service.ISysUserGroupService;
import com.haier.datamart.utils.UUIDUtils;
/**
 *
 * @author zuoqb123
 * @date 2018-09-29
 * @todo 用户组路由
 */
@RestController
@RequestMapping("/api/sysGroup")
@Api(value = "用户组",description="用户组 @author zuoqb123")
public class SysGroupController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(SysGroupController.class);

    @Autowired
    public ISysGroupService iSysGroupService;
    
    @Autowired
    public ISysGroupRoleService iSysGroupRoleService;
    
    @Autowired
    public ISysUserGroupService iSysUserGroupService;
    
    @Autowired
    public ISysProductGroupService iSysProductGroupService;
    
    

    /**
     * @date   2018-09-29
     * @author zuoqb123
     * @todo   新增用户组
     */
  	@ApiOperation(value = "新增/修改用户组", notes = "新增/修改用户组", httpMethod = "POST")
	@RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<SysGroup> addOrUpdate(HttpServletRequest request,@RequestBody SysGroup entity) {
		try {
			if(entity!=null&&StringUtils.isNotBlank(entity.getId())){
				//更新
				entity.setUpdateDate(new Date());
				entity.setUpdateBy(getLoginUser(request).getId()==null?"暂无":getLoginUser(request).getId());
				iSysGroupService.updateById(entity);
			}else{
				//新增
				entity.setId(UUIDUtils.getUuid());
				entity.setCreateDate(new Date());
				entity.setCreateBy(getLoginUser(request).getId()==null?"暂无":getLoginUser(request).getId());
				entity.setUpdateDate(new Date());
				entity.setUpdateBy(getLoginUser(request).getId()==null?"暂无":getLoginUser(request).getId());
				iSysGroupService.insert(entity);
			}
            String groupId=entity.getId();
			//保存用户组与用户中间表数据
			//先删除
			EntityWrapper<SysUserGroup> userGroupWrapper = new EntityWrapper<SysUserGroup>();
			userGroupWrapper.where("1=1");
			userGroupWrapper.eq("group_id", groupId);
			if(iSysUserGroupService.selectList(userGroupWrapper).size()>0){
				iSysUserGroupService.delete(userGroupWrapper);
			}
			for(SysUserGroup sysUserGroup:entity.getGroupUsers()){
	  			//新增
				sysUserGroup.setGroupId(groupId);
	  			iSysUserGroupService.insert(sysUserGroup);
			}
			//保存用户组相关角色
			EntityWrapper<SysGroupRole> groupRoleWrapper = new EntityWrapper<SysGroupRole>();
			groupRoleWrapper.where("1=1");
			groupRoleWrapper.eq("group_id", groupId);
			if(iSysGroupRoleService.selectList(groupRoleWrapper).size()>0){
				iSysGroupRoleService.delete(groupRoleWrapper);
			}
			for(SysGroupRole sysGroupRole:entity.getGroupRoles()){
				//新增
				sysGroupRole.setGroupId(groupId);
				iSysGroupRoleService.insert(sysGroupRole);
			}
			//保存用户组对应的项目
			//先删除
			EntityWrapper<SysProductGroup> productGroupWrapper = new EntityWrapper<SysProductGroup>();
			productGroupWrapper.where("1=1");
			productGroupWrapper.eq("group_id", groupId);
			if(iSysProductGroupService.selectList(productGroupWrapper).size()>0){
				iSysProductGroupService.delete(productGroupWrapper);
			}
			for(SysProductGroup sysProductGroup:entity.getGroupProducts()){
				sysProductGroup.setGroupId(groupId);
				iSysProductGroupService.insert(sysProductGroup);
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
     * @todo   删除用户组
     */
  	@ApiOperation(value = "删除用户组", notes = "删除用户组", httpMethod = "DELETE")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE,produces = { "application/json;charset=UTF-8" })
	public PublicResult<SysGroup> delete(HttpServletRequest request,@PathVariable("id") String groupId) {
		try {
			SysGroup entity=new SysGroup();
			entity.setId(groupId);
			entity.setDelFlag(DEL_FLAG);
			entity.setUpdateDate(new Date());
			entity.setUpdateBy(getLoginUser(request).getId()==null?"暂无":getLoginUser(request).getId());
			 if(iSysGroupService.updateById(entity)){
				 EntityWrapper<SysUserGroup> userGroupWrapper = new EntityWrapper<SysUserGroup>();
				 userGroupWrapper.where("1=1");
				 userGroupWrapper.eq("group_id", groupId);
				 if(iSysUserGroupService.selectList(userGroupWrapper).size()>0){
					 iSysUserGroupService.delete(userGroupWrapper);
				 }
				 EntityWrapper<SysGroupRole> groupRoleWrapper = new EntityWrapper<SysGroupRole>();
				groupRoleWrapper.where("1=1");
				groupRoleWrapper.eq("group_id", groupId);
				if(iSysGroupRoleService.selectList(groupRoleWrapper).size()>0){
					iSysGroupRoleService.delete(groupRoleWrapper);
				}
				EntityWrapper<SysProductGroup> productGroupWrapper = new EntityWrapper<SysProductGroup>();
				productGroupWrapper.where("1=1");
				productGroupWrapper.eq("group_id", groupId);
				if(iSysProductGroupService.selectList(productGroupWrapper).size()>0){
					iSysProductGroupService.delete(productGroupWrapper);
				}
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
     * @todo   查询单个用户组
     */
  	@ApiOperation(value = "查询单个用户组", notes = "查询单个用户组", httpMethod = "GET")
  	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" } )
  	public PublicResult<SysGroup> get(HttpServletRequest request,@PathVariable("id") String groupId) {
  		SysGroup entity=null;
  		try {
  			EntityWrapper<SysGroup> wrapper = new EntityWrapper<SysGroup>();
  			wrapper.where("del_flag={0}", UN_DEL_FLAG);
  			wrapper.eq("id", groupId);
  			entity=iSysGroupService.selectOne(wrapper);
  			//
  			if(entity!=null){
  				EntityWrapper<SysUserGroup> userGroupWrapper = new EntityWrapper<SysUserGroup>();
  				userGroupWrapper.where("1=1");
  				userGroupWrapper.eq("group_id", groupId);
  				entity.setGroupUsers(iSysUserGroupService.selectList(userGroupWrapper)); 
  				EntityWrapper<SysGroupRole> groupRoleWrapper = new EntityWrapper<SysGroupRole>();
  				groupRoleWrapper.where("1=1");
  				groupRoleWrapper.eq("group_id", groupId);
  				entity.setGroupRoles(iSysGroupRoleService.selectList(groupRoleWrapper));
  				EntityWrapper<SysProductGroup> productGroupWrapper = new EntityWrapper<SysProductGroup>();
  				productGroupWrapper.where("1=1");
  				productGroupWrapper.eq("group_id", groupId);
  				entity.setGroupProducts(iSysProductGroupService.selectList(productGroupWrapper));
  				return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
  			}else{
  				return new PublicResult<>(PublicResultConstant.FAILED,"暂无数据!", null);
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
     * @todo   分页查询用户组
     */
  	@ApiOperation(value = "分页查询用户组", notes = "分页查询用户组", httpMethod = "GET")
  	@RequestMapping(value = "/list", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
    public Object list(SysGroup entity,@RequestParam(value="pageNum",required = false,defaultValue="1") Integer pageNum,
			@RequestParam(value="pageSize",required = false,defaultValue="10") Integer pageSize,HttpServletRequest request) {
		try {
			EntityWrapper<SysGroup> wrapper = searchWrapper(request, entity);
			PageHelper.startPage(pageNum, pageSize);
			int cu=(pageNum-1)*pageSize;
			Page<SysGroup> page=new Page<SysGroup>(cu, pageSize);
			List<SysGroup> l=iSysGroupService.selectList(wrapper);
			List<SysGroup> u=new ArrayList<SysGroup>();
			for(int x=cu;x<cu+pageSize;x++){
				if(l.size()>x)
				u.add(l.get(x));
			}
			page.setRecords(u);
			for(SysGroup e:page.getRecords()){
				String groupId=e.getId();
				EntityWrapper<SysUserGroup> userGroupWrapper = new EntityWrapper<SysUserGroup>();
				userGroupWrapper.where("1=1");
				userGroupWrapper.eq("group_id", groupId);
				e.setGroupUsers(iSysUserGroupService.selectList(userGroupWrapper)); 
				EntityWrapper<SysGroupRole> groupRoleWrapper = new EntityWrapper<SysGroupRole>();
				groupRoleWrapper.where("1=1");
				groupRoleWrapper.eq("group_id", groupId);
				e.setGroupRoles(iSysGroupRoleService.selectList(groupRoleWrapper));
				EntityWrapper<SysProductGroup> productGroupWrapper = new EntityWrapper<SysProductGroup>();
				productGroupWrapper.where("1=1");
				productGroupWrapper.eq("group_id", groupId);
				e.setGroupProducts(iSysProductGroupService.selectList(productGroupWrapper));
			}
			page.setTotal(iSysGroupService.selectCount(wrapper));
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
    private EntityWrapper<SysGroup> searchWrapper(HttpServletRequest request, SysGroup entity) {
		EntityWrapper<SysGroup> wrapper = new EntityWrapper<SysGroup>();
		wrapper.where("del_flag={0}", UN_DEL_FLAG);
		if(getLoginUser(request)!=null&&StringUtils.isNotBlank(getLoginUser(request).getId()==null?"暂无":getLoginUser(request).getId())){
			if(!isAdmin(request))
			 wrapper.and("create_by", getLoginUser(request).getId()==null?"暂无":getLoginUser(request).getId());
		}
				//根据编号模糊查询
		if(entity.getId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getId()))){
			wrapper.like("id", String.valueOf(entity.getId()));
		}
				//根据用户组名称模糊查询
		if(entity.getName()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getName()))){
			wrapper.like("name", String.valueOf(entity.getName()));
		}
				//根据英文名称模糊查询
		if(entity.getEnname()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getEnname()))){
			wrapper.like("enname", String.valueOf(entity.getEnname()));
		}
				//根据组管理员模糊查询
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

