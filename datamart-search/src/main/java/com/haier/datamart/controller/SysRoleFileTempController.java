package com.haier.datamart.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.entity.SysRoleFileTemp;
import com.haier.datamart.service.ISysRoleFileTempService;
/**
 *
 * @author zuoqb123
 * @date 2018-12-10
 * @todo 角色与文件目录对照表路由
 */
@RestController
@RequestMapping("/api//sysRoleFileTemp")
@Api(value = "角色与文件目录对照表",description="角色与文件目录对照表 @author zuoqb123")
public class SysRoleFileTempController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(SysRoleFileTempController.class);

    @Autowired
    public ISysRoleFileTempService iSysRoleFileTempService;

    /**
     * @date   2018-12-10
     * @author zuoqb123
     * @todo   新增角色与文件目录对照表
     */
  	@ApiOperation(value = "新增角色与文件目录对照表", notes = "新增角色与文件目录对照表", httpMethod = "POST")
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<SysRoleFileTemp> saveOrUpdate(HttpServletRequest request,@RequestBody SysRoleFileTemp entity) {
		try {
			//新增
			if(iSysRoleFileTempService.saveOrUpdate(entity)>0){
				return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
			}else{
				return new PublicResult<>(PublicResultConstant.FAILED, "请选择角色",null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
	}
    
    /**
     * @date   2018-12-10
     * @author zuoqb123
     * @todo   删除角色与文件目录对照表
     */
  	@ApiOperation(value = "删除角色与文件目录对照表", notes = "删除角色与文件目录对照表", httpMethod = "POST")
    @RequestMapping(value = "/delete/{roleId}", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<SysRoleFileTemp> delete(HttpServletRequest request,@PathVariable("roleId") String roleId) {
		try {
			 if(iSysRoleFileTempService.deleteByRoleId(roleId)>0){
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
     * @date   2018-12-10
     * @author zuoqb123
     * @todo   查询单个角色与文件目录对照表
     */
  	@ApiOperation(value = "查询角色与文件目录对照表", notes = "查询单个角色与文件目录对照表", httpMethod = "GET")
  	@RequestMapping(value = "/get/{roleId}", method = RequestMethod.GET ,produces = { "application/json;charset=UTF-8" })
  	public Object get(HttpServletRequest request,@PathVariable("roleId") String roleId) {
  		List<SysRoleFileTemp> entity=null;
  		try {
  			entity=iSysRoleFileTempService.getSysRoleFileTempByRoleId(roleId);
  			return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
  	}
	
   /* *//**
     * @date   2018-12-10
     * @author zuoqb123
     * @todo   分页查询角色与文件目录对照表
     *//*
  	@ApiOperation(value = "分页查询角色与文件目录对照表", notes = "分页查询角色与文件目录对照表", httpMethod = "GET")
  	@RequestMapping(value = "/list", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
    public Object list(SysRoleFileTemp entity,@RequestParam(value="pageNum",required = false,defaultValue="1") Integer pageNum,
			@RequestParam(value="pageSize",required = false,defaultValue="10") Integer pageSize,HttpServletRequest request) {
		try {
			EntityWrapper<SysRoleFileTemp> wrapper = searchWrapper(request, entity);
			PageHelper.startPage(pageNum, pageSize);
			int cu=(pageNum-1)*pageSize;
			Page<SysRoleFileTemp> page=new Page<SysRoleFileTemp>(cu, pageSize);
			page = iSysRoleFileTempService.selectPage(page,wrapper);
			page.setTotal(iSysRoleFileTempService.selectCount(wrapper));
			return new PublicResult<>(PublicResultConstant.SUCCESS, page);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(),null);
		}

	}
    *//**
     * @date   2018年9月25日 下午5:36:10
     * @author zuoqb123
     * @todo   构建查询条件-以后扩展
     *//*
    private EntityWrapper<SysRoleFileTemp> searchWrapper(HttpServletRequest request, SysRoleFileTemp entity) {
		EntityWrapper<SysRoleFileTemp> wrapper = new EntityWrapper<SysRoleFileTemp>();
		wrapper.where("del_flag={0}", UN_DEL_FLAG);
		if(getLoginUser(request)!=null&&StringUtils.isNotBlank(getLoginUser(request).getId())){
			if(!isAdmin(request))
			 wrapper.eq("create_by", getLoginUser(request).getId());
		}
				//根据角色编码模糊查询
		if(entity.getRoleId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getRoleId()))){
			wrapper.like("role_id", String.valueOf(entity.getRoleId()));
		}
				//根据目录编码模糊查询
		if(entity.getTempId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getTempId()))){
			wrapper.like("temp_id", String.valueOf(entity.getTempId()));
		}
				wrapper.orderBy("create_date", true);
		System.out.println(wrapper.originalSql());
		return wrapper;
	}*/
}

