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
import com.github.pagehelper.PageInfo;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.entity.SysRoleReport;
import com.haier.datamart.entity.SysUser;
import com.haier.datamart.service.ISysUserService;
import com.haier.datamart.utils.UUIDUtils;
/**
 *
 * @author zuoqb123
 * @date 2018-09-29
 * @todo 用户表路由
 */
@RestController
@RequestMapping("/api/sysUser")
@Api(value = "用户表",description="用户表 @author zuoqb123")
public class SysUserController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(SysUserController.class);

    @Autowired
    public ISysUserService iSysUserService;

    /**
     * @date   2018-09-29
     * @author zuoqb123
     * @todo   新增用户表
     */
  	@ApiOperation(value = "新增用户表", notes = "新增用户表", httpMethod = "POST")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public PublicResult<SysUser> add(HttpServletRequest request,@RequestBody SysUser entity) {
		try {
			if(StringUtils.isBlank(entity.getId())){
				//新增
				entity.setId(UUIDUtils.getUuid());
				entity.setCreateDate(new Date());
				entity.setCreateBy(getLoginUser(request).getId());
				if(iSysUserService.insert(entity)){
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
     * @todo   删除用户表
     */
  	@ApiOperation(value = "删除用户表", notes = "删除用户表", httpMethod = "DELETE")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public PublicResult<SysUser> delete(HttpServletRequest request,@PathVariable("id") String id) {
		try {
			SysUser entity=new SysUser();
			entity.setId(id);
			entity.setDelFlag(DEL_FLAG);
			entity.setUpdateDate(new Date());
			entity.setUpdateBy(getLoginUser(request).getId());
			 if(iSysUserService.updateById(entity)){
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
     * @todo   更新用户表
     */
  	@ApiOperation(value = "更新用户表", notes = "更新用户表", httpMethod = "PUT")
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public PublicResult<SysUser> update(HttpServletRequest request,@RequestBody SysUser entity) {
		try {
			if(entity!=null&&StringUtils.isNotBlank(entity.getId())){
				//更新
				entity.setUpdateDate(new Date());
				entity.setUpdateBy(getLoginUser(request).getId());
				if(iSysUserService.updateById(entity)){
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
     * @todo   查询单个用户表
     */
  	@ApiOperation(value = "查询单个用户表", notes = "查询单个用户表", httpMethod = "GET")
  	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET )
  	public PublicResult<SysUser> get(HttpServletRequest request,@PathVariable("id") String id) {
  		SysUser entity=null;
  		try {
  			EntityWrapper<SysUser> wrapper = new EntityWrapper<SysUser>();
  			wrapper.where("del_flag={0}", UN_DEL_FLAG);
  			wrapper.eq("id", id);
  			entity=iSysUserService.selectOne(wrapper);
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
     * @todo   分页查询用户表
     */
  	@ApiOperation(value = "分页查询用户表", notes = "分页查询用户表", httpMethod = "GET")
  	@RequestMapping(value = "/list", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
    public Object list(SysUser entity,@RequestParam(value="pageNum",required = false,defaultValue="1") Integer pageNum,
			@RequestParam(value="pageSize",required = false,defaultValue="10") Integer pageSize,HttpServletRequest request) {
		try {
			EntityWrapper<SysUser> wrapper = searchWrapper(request, entity);
			PageHelper.startPage(pageNum, pageSize);
			List<SysUser> l=iSysUserService.selectList(wrapper);
			int cu=(pageNum-1)*pageSize;
			Page<SysUser> page=new Page<SysUser>(cu, pageSize);
			page.setRecords(l);
			page.setTotal(iSysUserService.selectCount(wrapper));
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
    private EntityWrapper<SysUser> searchWrapper(HttpServletRequest request, SysUser entity) {
		EntityWrapper<SysUser> wrapper = new EntityWrapper<SysUser>();
		wrapper.where("del_flag={0}", UN_DEL_FLAG);
		if(getLoginUser(request)!=null&&StringUtils.isNotBlank(getLoginUser(request).getId())){
			if(!isAdmin(request))
			 wrapper.and("create_by", getLoginUser(request).getId());
		}
				//根据编号模糊查询
		if(entity.getId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getId()))){
			wrapper.like("id", String.valueOf(entity.getId()));
		}
				//根据登录名模糊查询
		if(entity.getLoginName()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getLoginName()))){
			wrapper.like("login_name", String.valueOf(entity.getLoginName()));
		}
				//根据密码模糊查询
		if(entity.getPassword()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getPassword()))){
			wrapper.like("password", String.valueOf(entity.getPassword()));
		}
				//根据工号模糊查询
		if(entity.getNo()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getNo()))){
			wrapper.like("no", String.valueOf(entity.getNo()));
		}
				//根据姓名模糊查询
		if(entity.getName()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getName()))){
			wrapper.like("name", String.valueOf(entity.getName()));
		}
				//根据邮箱模糊查询
		if(entity.getEmail()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getEmail()))){
			wrapper.like("email", String.valueOf(entity.getEmail()));
		}
				//根据电话模糊查询
		if(entity.getPhone()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getPhone()))){
			wrapper.like("phone", String.valueOf(entity.getPhone()));
		}
				//根据手机模糊查询
		if(entity.getMobile()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getMobile()))){
			wrapper.like("mobile", String.valueOf(entity.getMobile()));
		}
				//根据用户类型模糊查询
		if(entity.getUserType()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getUserType()))){
			wrapper.like("user_type", String.valueOf(entity.getUserType()));
		}
				//根据用户头像模糊查询
		if(entity.getPhoto()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getPhoto()))){
			wrapper.like("photo", String.valueOf(entity.getPhoto()));
		}
				//根据最后登陆IP模糊查询
		if(entity.getLoginIp()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getLoginIp()))){
			wrapper.like("login_ip", String.valueOf(entity.getLoginIp()));
		}
				//根据最后登陆时间模糊查询
		if(entity.getLoginDate()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getLoginDate()))){
			wrapper.like("login_date", String.valueOf(entity.getLoginDate()));
		}
				//根据是否可登录模糊查询
		if(entity.getLoginFlag()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getLoginFlag()))){
			wrapper.like("login_flag", String.valueOf(entity.getLoginFlag()));
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

