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
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.entity.SysUser;
import com.haier.datamart.entity.SysUserGroup;
import com.haier.datamart.service.ISysUserGroupService;
/**
 *
 * @author zuoqb123
 * @date 2018-09-29
 * @todo 用户-组路由
 */
@RestController
@RequestMapping("/api/sysUserGroup")
@Api(value = "用户-组",description="用户-组 @author zuoqb123")
public class SysUserGroupController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(SysUserGroupController.class);

    @Autowired
    public ISysUserGroupService iSysUserGroupService;

    /**
     * @date   2018-09-29
     * @author zuoqb123
     * @todo   新增用户-组
     */
  	@ApiOperation(value = "新增/修改用户-组", notes = "新增/修改用户-组", httpMethod = "POST")
	@RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST)
	public PublicResult<SysUserGroup> addOrUpdate(HttpServletRequest request,@RequestBody  SysUserGroup entity) {
		try {
			if(StringUtils.isNotBlank(entity.getUserId())&&StringUtils.isNotBlank(entity.getGroupId())){
				//先删除
				EntityWrapper<SysUserGroup> wrapper = new EntityWrapper<SysUserGroup>();
	  			wrapper.where("group_id", entity.getGroupId());
				if(iSysUserGroupService.delete(wrapper)){
					//新增
					if(iSysUserGroupService.insert(entity)){
						return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
					}else{
						return new PublicResult<>(PublicResultConstant.ERROR, null);
					}
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
     * @todo   删除用户-组
     */
  	@ApiOperation(value = "删除用户-组", notes = "删除用户-组", httpMethod = "DELETE")
    @RequestMapping(value = "/delete/{groupId}", method = RequestMethod.DELETE)
	public PublicResult<SysUserGroup> delete(HttpServletRequest request,@PathVariable("groupId") String groupId) {
		try {
			if(StringUtils.isNotBlank(groupId)){
				EntityWrapper<SysUserGroup> wrapper = new EntityWrapper<SysUserGroup>();
	  			wrapper.where("group_id", groupId);
				if(iSysUserGroupService.delete(wrapper)){
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
     * @todo   查询单个用户-组
     */
  	@ApiOperation(value = "查询单个用户-组", notes = "查询单个用户-组", httpMethod = "GET")
  	@RequestMapping(value = "/get/{groupId}", method = RequestMethod.GET )
  	public PublicResult<List<SysUserGroup>> get(HttpServletRequest request,@PathVariable("groupId") String groupId) {
  		List<SysUserGroup> entity=null;
  		try {
  			EntityWrapper<SysUserGroup> wrapper = new EntityWrapper<SysUserGroup>();
  			wrapper.where("group_id", groupId);
  			entity=iSysUserGroupService.selectList(wrapper);
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
     * @todo   分页查询用户-组
     */
  	@ApiOperation(value = "分页查询用户-组", notes = "分页查询用户-组", httpMethod = "GET")
  	@RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object list(SysUserGroup entity,@RequestParam(value="pageNum",required = false,defaultValue="1") Integer pageNum,
			@RequestParam(value="pageSize",required = false,defaultValue="10") Integer pageSize,HttpServletRequest request) {
		try {
			EntityWrapper<SysUserGroup> wrapper = searchWrapper(request, entity);
			PageHelper.startPage(pageNum, pageSize);
			int cu=(pageNum-1)*pageSize;
			Page<SysUserGroup> page=new Page<SysUserGroup>(cu, pageSize);
			page= iSysUserGroupService.selectPage(page, wrapper);
			page.setTotal(iSysUserGroupService.selectCount(wrapper));
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
    private EntityWrapper<SysUserGroup> searchWrapper(HttpServletRequest request, SysUserGroup entity) {
		EntityWrapper<SysUserGroup> wrapper = new EntityWrapper<SysUserGroup>();
		wrapper.where("del_flag={0}", UN_DEL_FLAG);
		if(getLoginUser(request)!=null&&StringUtils.isNotBlank(getLoginUser(request).getId())){
			if(!isAdmin(request))
			 wrapper.and("create_by", getLoginUser(request).getId());
		}
				//根据用户编号模糊查询
		if(entity.getUserId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getUserId()))){
			wrapper.like("user_id", String.valueOf(entity.getUserId()));
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

