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
import com.haier.datamart.entity.SysGroup;
import com.haier.datamart.entity.SysGroupRole;
import com.haier.datamart.entity.SysRole;
import com.haier.datamart.entity.SysRoleFileTemp;
import com.haier.datamart.entity.SysRoleMenu;
import com.haier.datamart.entity.SysRoleReport;
import com.haier.datamart.service.ISysGroupRoleService;
import com.haier.datamart.service.ISysRoleFileTempService;
import com.haier.datamart.service.ISysRoleMenuService;
import com.haier.datamart.service.ISysRoleReportService;
import com.haier.datamart.service.ISysRoleService;
import com.haier.datamart.utils.UUIDUtils;

/**
 *
 * @author zuoqb123
 * @date 2018-09-29
 * @todo 角色表路由
 */
@RestController
@RequestMapping("/api/sysRole")
@Api(value = "角色表", description = "角色表 @author zuoqb123")
public class SysRoleController extends BaseController {
	private final Logger logger = LoggerFactory
			.getLogger(SysRoleController.class);

	@Autowired
	public ISysRoleService iSysRoleService;

	@Autowired
	public ISysRoleReportService iSysRoleReportService;

	@Autowired
	public ISysRoleMenuService iSysRoleMenuService;
	@Autowired
	public ISysGroupRoleService iSysGroupRoleService;
	@Autowired
	public ISysRoleFileTempService iSysRoleFileTempService;

	/**
	 * @date 2018-09-29
	 * @author zuoqb123
	 * @todo 新增角色表
	 */
	@ApiOperation(value = "新增/修改角色表", notes = "新增/修改角色表", httpMethod = "POST")
	@RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public PublicResult<SysRole> addOrUpdate(HttpServletRequest request,
			@RequestBody SysRole entity) {
		try {
			if (entity != null && StringUtils.isNotBlank(entity.getId())) {
				// 更新
				entity.setUpdateDate(new Date());
				entity.setUpdateBy(getLoginUser(request).getId() == null ? "暂无"
						: getLoginUser(request).getId());
				iSysRoleService.updateById(entity);
			} else {
				// 新增
				entity.setId(UUIDUtils.getUuid());
				entity.setCreateDate(new Date());
				entity.setCreateBy(getLoginUser(request).getId() == null ? "暂无"
						: getLoginUser(request).getId());
				entity.setUpdateDate(new Date());
				entity.setUpdateBy(getLoginUser(request).getId() == null ? "暂无"
						: getLoginUser(request).getId());
				iSysRoleService.insert(entity);
			}
			String roleId = entity.getId();
			// 保存角色与菜单中间表数据
			// 先删除
			EntityWrapper<SysRoleMenu> roleMenuGroupWrapper = new EntityWrapper<SysRoleMenu>();
			roleMenuGroupWrapper.where("1=1");
			roleMenuGroupWrapper.eq("role_id", roleId);
			if (iSysRoleMenuService.selectList(roleMenuGroupWrapper).size() > 0) {
				iSysRoleMenuService.delete(roleMenuGroupWrapper);
			}
			for (SysRoleMenu sysRoleMenu : entity.getRoleMenus()) {
				// 新增
				sysRoleMenu.setRoleId(roleId);
				iSysRoleMenuService.insert(sysRoleMenu);
			}
			// 保存角色与报表
			EntityWrapper<SysRoleReport> roleReportWrapper = new EntityWrapper<SysRoleReport>();
			roleReportWrapper.where("1=1");
			roleReportWrapper.eq("role_id", roleId);
			if (iSysRoleReportService.selectList(roleReportWrapper).size() > 0) {
				iSysRoleReportService.delete(roleReportWrapper);
			}
			for (SysRoleReport sysRoleReport : entity.getRoleReports()) {
				// 新增
				sysRoleReport.setRoleId(roleId);
				iSysRoleReportService.insert(sysRoleReport);
			}
			// 保存角色与用户组
			// 先删除
			EntityWrapper<SysGroupRole> wrapper = new EntityWrapper<SysGroupRole>();
			wrapper.where("1=1");
			wrapper.eq("role_id", roleId);
			// 新增
			iSysGroupRoleService.delete(wrapper);
			for (SysGroupRole g : entity.getSysGroupRoles()) {
				g.setRoleId(roleId);
				iSysGroupRoleService.insert(g);
			}
			// 保存角色与目录
			for (SysRoleFileTemp roleTemp : entity.getSysRoleFileTemps()) {
				roleTemp.setRoleId(roleId);
				iSysRoleFileTempService.saveOrUpdate(roleTemp);
			}
			return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,
					e.getMessage(), null);
		}
	}

	/**
	 * @date 2018-09-29
	 * @author zuoqb123
	 * @todo 删除角色表
	 */
	@ApiOperation(value = "删除角色表", notes = "删除角色表", httpMethod = "DELETE")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = { "application/json;charset=UTF-8" })
	public PublicResult<SysRole> delete(HttpServletRequest request,
			@PathVariable("id") String roleId) {
		try {
			SysRole entity = new SysRole();
			entity.setId(roleId);
			entity.setDelFlag(DEL_FLAG);
			entity.setUpdateDate(new Date());
			entity.setUpdateBy(getLoginUser(request).getId() == null ? "暂无"
					: getLoginUser(request).getId());
			if (iSysRoleService.updateById(entity)) {
				// 先删除
				EntityWrapper<SysRoleMenu> roleMenuGroupWrapper = new EntityWrapper<SysRoleMenu>();
				roleMenuGroupWrapper.where("1=1");
				roleMenuGroupWrapper.eq("role_id", roleId);
				if (iSysRoleMenuService.selectList(roleMenuGroupWrapper).size() > 0) {
					iSysRoleMenuService.delete(roleMenuGroupWrapper);
				}
				// 删除角色与报表
				EntityWrapper<SysRoleReport> roleReportWrapper = new EntityWrapper<SysRoleReport>();
				roleReportWrapper.where("1=1");
				roleReportWrapper.eq("role_id", roleId);
				if (iSysRoleReportService.selectList(roleReportWrapper).size() > 0) {
					iSysRoleReportService.delete(roleReportWrapper);
				}
				//删除角色与用户组
				// 先删除
				EntityWrapper<SysGroupRole> wrapper = new EntityWrapper<SysGroupRole>();
				wrapper.where("1=1");
				wrapper.eq("role_id", roleId);
				// 新增
				iSysGroupRoleService.delete(wrapper);
				// 删除角色与目录
				iSysRoleFileTempService.deleteByRoleId(roleId);
				return new PublicResult<>(PublicResultConstant.SUCCESS, null);
			} else {
				return new PublicResult<>(PublicResultConstant.ERROR, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,
					e.getMessage(), null);
		}
	}

	/**
	 * @date 2018-09-29
	 * @author zuoqb123
	 * @todo 查询单个角色表
	 */
	@ApiOperation(value = "查询单个角色表", notes = "查询单个角色表", httpMethod = "GET")
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	public PublicResult<SysRole> get(HttpServletRequest request,
			@PathVariable("id") String roleId) {
		SysRole entity = null;
		try {
			EntityWrapper<SysRole> wrapper = new EntityWrapper<SysRole>();
			wrapper.where("del_flag={0}", UN_DEL_FLAG);
			wrapper.eq("id", roleId);
			entity = iSysRoleService.selectOne(wrapper);
			if (entity != null) {
				EntityWrapper<SysRoleMenu> roleMenuGroupWrapper = new EntityWrapper<SysRoleMenu>();
				roleMenuGroupWrapper.where("1=1");
				roleMenuGroupWrapper.eq("role_id", roleId);
				List<SysRoleMenu> roleMenus = iSysRoleMenuService
						.selectList(roleMenuGroupWrapper);
				if (roleMenus != null && roleMenus.size() > 0) {
					entity.setRoleMenus(roleMenus);
				}
				EntityWrapper<SysRoleReport> roleReportWrapper = new EntityWrapper<SysRoleReport>();
				roleReportWrapper.where("1=1");
				roleReportWrapper.eq("role_id", roleId);
				List<SysRoleReport> roleReports = iSysRoleReportService
						.selectList(roleReportWrapper);
				if (roleReports != null && roleReports.size() > 0) {
					entity.setRoleReports(roleReports);
				}
				
				//角色与用户组
				EntityWrapper<SysGroupRole> wrapper1 = new EntityWrapper<SysGroupRole>();
				wrapper1.where("1=1");
				wrapper1.eq("role_id", roleId);
				List<SysGroupRole> sysGroupRoles=iSysGroupRoleService.selectList(wrapper1);
				if(sysGroupRoles.size()>0){
					entity.setSysGroupRoles(sysGroupRoles);
				}
				//角色与目录
				List<SysRoleFileTemp> sysRoleFileTemps=iSysRoleFileTempService.getSysRoleFileTempByRoleId(roleId);
				entity.setSysRoleFileTemps(sysRoleFileTemps);
				return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
			} else {
				return new PublicResult<>(PublicResultConstant.FAILED, "暂无数据!",
						null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,
					e.getMessage(), null);
		}
	}

	/**
	 * @date 2018-09-29
	 * @author zuoqb123
	 * @todo 分页查询角色表
	 */
	@ApiOperation(value = "分页查询角色表", notes = "分页查询角色表", httpMethod = "GET")
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	public Object list(
			SysRole entity,
			@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
			HttpServletRequest request) {
		try {
			EntityWrapper<SysRole> wrapper = searchWrapper(request, entity);
			PageHelper.startPage(pageNum, pageSize);
			int cu = (pageNum - 1) * pageSize;
			Page<SysRole> page = new Page<SysRole>(cu, pageSize);
			List<SysRole> l=iSysRoleService.selectList(wrapper);
			page.setRecords(l);
			for (SysRole r : page.getRecords()) {
				String roleId = r.getId();
				EntityWrapper<SysRoleMenu> roleMenuGroupWrapper = new EntityWrapper<SysRoleMenu>();
				roleMenuGroupWrapper.where("1=1");
				roleMenuGroupWrapper.eq("role_id", roleId);
				List<SysRoleMenu> roleMenus = iSysRoleMenuService
						.selectList(roleMenuGroupWrapper);
				if (roleMenus != null && roleMenus.size() > 0) {
					r.setRoleMenus(roleMenus);
				}
				EntityWrapper<SysRoleReport> roleReportWrapper = new EntityWrapper<SysRoleReport>();
				roleReportWrapper.where("1=1");
				roleReportWrapper.eq("role_id", roleId);
				List<SysRoleReport> roleReports = iSysRoleReportService
						.selectList(roleReportWrapper);
				if (roleReports != null && roleReports.size() > 0) {
					r.setRoleReports(roleReports);
				}
			}
			page.setTotal(iSysRoleService.selectCount(wrapper));
			return new PublicResult<>(PublicResultConstant.SUCCESS, page);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,
					e.getMessage(), null);
		}

	}

	/**
	 * @date 2018年9月25日 下午5:36:10
	 * @author zuoqb123
	 * @todo 构建查询条件-以后扩展
	 */
	private EntityWrapper<SysRole> searchWrapper(HttpServletRequest request,
			SysRole entity) {
		EntityWrapper<SysRole> wrapper = new EntityWrapper<SysRole>();
		wrapper.where("del_flag={0}", UN_DEL_FLAG);
		if (getLoginUser(request) != null
				&& StringUtils.isNotBlank(getLoginUser(request).getId())) {
			if (!isAdmin(request))
				wrapper.and("create_by", getLoginUser(request).getId());
		}
		// 根据编号模糊查询
		if (entity.getId() != null
				&& StringUtils.isNotBlank(String.valueOf(entity.getId()))) {
			wrapper.like("id", String.valueOf(entity.getId()));
		}
		// 根据角色名称模糊查询
		if (entity.getName() != null
				&& StringUtils.isNotBlank(String.valueOf(entity.getName()))) {
			wrapper.like("name", String.valueOf(entity.getName()));
		}
		// 根据英文名称模糊查询
		if (entity.getEnname() != null
				&& StringUtils.isNotBlank(String.valueOf(entity.getEnname()))) {
			wrapper.like("enname", String.valueOf(entity.getEnname()));
		}
		// 根据角色类型 1-后台用户 2-补录用户模糊查询
		if (entity.getRoleType() != null
				&& StringUtils.isNotBlank(String.valueOf(entity.getRoleType()))) {
			wrapper.like("role_type", String.valueOf(entity.getRoleType()));
		}
		// 根据数据范围模糊查询
		if (entity.getDataScope() != null
				&& StringUtils
						.isNotBlank(String.valueOf(entity.getDataScope()))) {
			wrapper.like("data_scope", String.valueOf(entity.getDataScope()));
		}
		// 根据是否系统数据模糊查询
		if (entity.getIsSys() != null
				&& StringUtils.isNotBlank(String.valueOf(entity.getIsSys()))) {
			wrapper.like("is_sys", String.valueOf(entity.getIsSys()));
		}
		// 根据是否可用模糊查询
		if (entity.getUseable() != null
				&& StringUtils.isNotBlank(String.valueOf(entity.getUseable()))) {
			wrapper.like("useable", String.valueOf(entity.getUseable()));
		}
		// 根据创建者模糊查询
		if (entity.getCreateBy() != null
				&& StringUtils.isNotBlank(String.valueOf(entity.getCreateBy()))) {
			wrapper.like("create_by", String.valueOf(entity.getCreateBy()));
		}
		// 根据创建时间模糊查询
		if (entity.getCreateDate() != null
				&& StringUtils
						.isNotBlank(String.valueOf(entity.getCreateDate()))) {
			wrapper.like("create_date", String.valueOf(entity.getCreateDate()));
		}
		// 根据更新者模糊查询
		if (entity.getUpdateBy() != null
				&& StringUtils.isNotBlank(String.valueOf(entity.getUpdateBy()))) {
			wrapper.like("update_by", String.valueOf(entity.getUpdateBy()));
		}
		// 根据更新时间模糊查询
		if (entity.getUpdateDate() != null
				&& StringUtils
						.isNotBlank(String.valueOf(entity.getUpdateDate()))) {
			wrapper.like("update_date", String.valueOf(entity.getUpdateDate()));
		}
		// 根据备注信息模糊查询
		if (entity.getRemarks() != null
				&& StringUtils.isNotBlank(String.valueOf(entity.getRemarks()))) {
			wrapper.like("remarks", String.valueOf(entity.getRemarks()));
		}
		// 根据删除标记模糊查询
		if (entity.getDelFlag() != null
				&& StringUtils.isNotBlank(String.valueOf(entity.getDelFlag()))) {
			wrapper.like("del_flag", String.valueOf(entity.getDelFlag()));
		}
		wrapper.orderBy("create_date", true);
		System.out.println(wrapper.originalSql());
		return wrapper;
	}
}
