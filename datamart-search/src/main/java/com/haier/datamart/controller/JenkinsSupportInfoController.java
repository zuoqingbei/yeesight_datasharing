package com.haier.datamart.controller;

import org.springframework.stereotype.Controller;

import com.haier.datamart.controller.BaseController;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import com.haier.datamart.service.IJenkinsSupportInfoService;
import com.haier.datamart.entity.JenkinsSupportInfo;
import com.haier.datamart.entity.SysUserGroup;
import com.haier.datamart.entity.User;
import com.haier.datamart.jenkinssupport.JenkinsHandler;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.haier.datamart.utils.UUIDUtils;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 *
 * @author zuoqb123
 * @date 2018-11-30
 * @todo jenkins信息路由
 */
@RestController
@RequestMapping("/api/jenkinsSupportInfo")
@Api(value = "jenkins信息",description="jenkins信息 @author zuoqb123")
public class JenkinsSupportInfoController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(JenkinsSupportInfoController.class);

    @Autowired
    public IJenkinsSupportInfoService iJenkinsSupportInfoService;
    
    /**
     * 
     * @time   2018年11月30日 下午3:05:14
     * @author zuoqb
     * @todo   TODO
     * @param  @param request
     * @param  @param type:0-系统 1-报表
     * @param  @param id:业务编码
     * @param  @return
     * @return_type   Object
     */
    @ApiOperation(value = "Jenkins构建", notes = "Jenkins构建", httpMethod = "GET")
    @RequestMapping(value = "/jenkins/build", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
   	public Object jenkinsBuild(HttpServletRequest request,@RequestParam(value="type",required = true) String type,
   			@RequestParam(value="businessId",required = true) String businessId) {
    	User user = getLoginUser(request);
	/*	if (user.getId() == null || "".equals(user.getId())) {
			return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
		}*/
   		try {
   			EntityWrapper<JenkinsSupportInfo> wrapper = new EntityWrapper<JenkinsSupportInfo>();
  			wrapper.where("del_flag={0}", UN_DEL_FLAG);
  			wrapper.eq("type", type);
  			wrapper.eq("business_id", businessId);
  			JenkinsSupportInfo entity=iJenkinsSupportInfoService.selectOne(wrapper);
  			if(entity==null||StringUtils.isBlank(entity.getName())){
  				return new PublicResult<>(PublicResultConstant.FAILED,"暂无调度信息，请联系管理员进行配置");
  			}else{
  				if("0".equals(entity.getBuildable())){
  					int result=JenkinsHandler.build(entity.getName());
  					if(result==0){
  						return new PublicResult<>(PublicResultConstant.SUCCESS, null);
  					}else{
  						return new PublicResult<>(PublicResultConstant.FAILED, "服务异常！");
  					}
  				}else{
  					return new PublicResult<>(PublicResultConstant.FAILED,"调度已被暂停！");
  				}
  			}
   		} catch (Exception e) {
   			e.printStackTrace();
   			logger.error(e.getMessage());
   			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
   		}
   	}
    /**
     * @date   2018-11-30
     * @author zuoqb123
     * @todo   新增jenkins信息
     */
  	@ApiOperation(value = "新增jenkins信息", notes = "新增jenkins信息", httpMethod = "POST")
	@RequestMapping(value = "/add", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<JenkinsSupportInfo> add(HttpServletRequest request,@RequestBody JenkinsSupportInfo entity) {
		try {
			if(StringUtils.isBlank(entity.getId())){
				//新增
				entity.setId(UUIDUtils.getUuid());
				entity.setCreateDate(new Date());
				entity.setCreateBy(getLoginUser(request).getId());
				if(iJenkinsSupportInfoService.insert(entity)){
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
     * @date   2018-11-30
     * @author zuoqb123
     * @todo   删除jenkins信息
     */
  	@ApiOperation(value = "删除jenkins信息", notes = "删除jenkins信息", httpMethod = "POST")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<JenkinsSupportInfo> delete(HttpServletRequest request,@PathVariable("id") String id) {
		try {
			JenkinsSupportInfo entity=new JenkinsSupportInfo();
			entity.setId(id);
			entity.setDelFlag(DEL_FLAG);
			entity.setUpdateDate(new Date());
			entity.setUpdateBy(getLoginUser(request).getId());
			 if(iJenkinsSupportInfoService.updateById(entity)){
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
     * @date   2018-11-30
     * @author zuoqb123
     * @todo   更新jenkins信息
     */
  	@ApiOperation(value = "更新jenkins信息", notes = "更新jenkins信息", httpMethod = "POST")
	@RequestMapping(value = "/update", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<JenkinsSupportInfo> update(HttpServletRequest request,JenkinsSupportInfo entity) {
		try {
			if(entity!=null&&StringUtils.isNotBlank(entity.getId())){
				//更新
				entity.setUpdateDate(new Date());
				entity.setUpdateBy(getLoginUser(request).getId());
				if(iJenkinsSupportInfoService.updateById(entity)){
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
     * @date   2018-11-30
     * @author zuoqb123
     * @todo   查询单个jenkins信息
     */
  	@ApiOperation(value = "查询单个jenkins信息", notes = "查询单个jenkins信息", httpMethod = "GET")
  	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET ,produces = { "application/json;charset=UTF-8" })
  	public PublicResult<JenkinsSupportInfo> get(HttpServletRequest request,@PathVariable("id") String id) {
  		JenkinsSupportInfo entity=null;
  		try {
  			EntityWrapper<JenkinsSupportInfo> wrapper = new EntityWrapper<JenkinsSupportInfo>();
  			wrapper.where("del_flag={0}", UN_DEL_FLAG);
  			wrapper.eq("id", id);
  			entity=iJenkinsSupportInfoService.selectOne(wrapper);
  			return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
  	}
	
    /**
     * @date   2018-11-30
     * @author zuoqb123
     * @todo   分页查询jenkins信息
     */
  	@ApiOperation(value = "分页查询jenkins信息", notes = "分页查询jenkins信息", httpMethod = "GET")
  	@RequestMapping(value = "/list", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
    public Object list(JenkinsSupportInfo entity,@RequestParam(value="pageNum",required = false,defaultValue="1") Integer pageNum,
			@RequestParam(value="pageSize",required = false,defaultValue="10") Integer pageSize,HttpServletRequest request) {
		try {
			/*EntityWrapper<JenkinsSupportInfo> wrapper = searchWrapper(request, entity);
			PageHelper.startPage(pageNum, pageSize);
			List<JenkinsSupportInfo> list = iJenkinsSupportInfoService.selectList(wrapper);
			PageInfo<JenkinsSupportInfo> page = new PageInfo<JenkinsSupportInfo>(list);
			return new PublicResult<>(PublicResultConstant.SUCCESS, page);*/
			EntityWrapper<JenkinsSupportInfo> wrapper = searchWrapper(request, entity);
			PageHelper.startPage(pageNum, pageSize);
			int cu=(pageNum-1)*pageSize;
			Page<JenkinsSupportInfo> page=new Page<JenkinsSupportInfo>(cu, pageSize);
			page= iJenkinsSupportInfoService.selectPage(page, wrapper);
			page.setTotal(iJenkinsSupportInfoService.selectCount(wrapper));
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
    private EntityWrapper<JenkinsSupportInfo> searchWrapper(HttpServletRequest request, JenkinsSupportInfo entity) {
		EntityWrapper<JenkinsSupportInfo> wrapper = new EntityWrapper<JenkinsSupportInfo>();
		wrapper.where("del_flag={0}", UN_DEL_FLAG);
		if(getLoginUser(request)!=null&&StringUtils.isNotBlank(getLoginUser(request).getId())){
			if(!isAdmin(request))
			 wrapper.eq("create_by", getLoginUser(request).getId());
		}
				//根据编号模糊查询
		if(entity.getId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getId()))){
			wrapper.like("id", String.valueOf(entity.getId()));
		}
				//根据0-系统 1-报表模糊查询
		if(entity.getType()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getType()))){
			wrapper.like("type", String.valueOf(entity.getType()));
		}
				//根据业务编码模糊查询
		if(entity.getBusinessId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getBusinessId()))){
			wrapper.like("business_id", String.valueOf(entity.getBusinessId()));
		}
				//根据调度名称模糊查询
		if(entity.getName()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getName()))){
			wrapper.like("name", String.valueOf(entity.getName()));
		}
				//根据模糊查询
		if(entity.getFullName()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getFullName()))){
			wrapper.like("fullName", String.valueOf(entity.getFullName()));
		}
				//根据显示名模糊查询
		if(entity.getDisplayName()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getDisplayName()))){
			wrapper.like("displayName", String.valueOf(entity.getDisplayName()));
		}
				//根据模糊查询
		if(entity.getFullDisplayName()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getFullDisplayName()))){
			wrapper.like("fullDisplayName", String.valueOf(entity.getFullDisplayName()));
		}
				//根据调度地址模糊查询
		if(entity.getUrl()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getUrl()))){
			wrapper.like("url", String.valueOf(entity.getUrl()));
		}
				//根据是否可以构建 0-可以 1-不可以模糊查询
		if(entity.getBuildable()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getBuildable()))){
			wrapper.like("buildable", String.valueOf(entity.getBuildable()));
		}
				//根据创建人模糊查询
		if(entity.getCreateBy()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getCreateBy()))){
			wrapper.like("create_by", String.valueOf(entity.getCreateBy()));
		}
				//根据创建时间模糊查询
		if(entity.getCreateDate()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getCreateDate()))){
			wrapper.like("create_date", String.valueOf(entity.getCreateDate()));
		}
				//根据更新人模糊查询
		if(entity.getUpdateBy()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getUpdateBy()))){
			wrapper.like("update_by", String.valueOf(entity.getUpdateBy()));
		}
				//根据更新时间模糊查询
		if(entity.getUpdateDate()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getUpdateDate()))){
			wrapper.like("update_date", String.valueOf(entity.getUpdateDate()));
		}
				//根据备注模糊查询
		if(entity.getRemarks()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getRemarks()))){
			wrapper.like("remarks", String.valueOf(entity.getRemarks()));
		}
				//根据模糊查询
		if(entity.getDelFlag()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getDelFlag()))){
			wrapper.like("del_flag", String.valueOf(entity.getDelFlag()));
		}
				wrapper.orderBy("create_date", true);
		System.out.println(wrapper.originalSql());
		return wrapper;
	}
}

