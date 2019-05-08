package com.haier.datamart.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 角色表
 * 
 * @author zuoqb123
 * @date 2018-09-29
 */
@TableName("sys_role")
public class SysRole extends Model<SysRole> {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	private String id;
	/**
	 * 角色名称
	 */
	private String name;
	/**
	 * 英文名称
	 */
	private String enname;
	/**
	 * 角色类型 1-后台用户 2-补录用户
	 */
	@TableField("role_type")
	private String roleType;
	/**
	 * 数据范围
	 */
	@TableField("data_scope")
	private String dataScope;
	/**
	 * 是否系统数据
	 */
	@TableField("is_sys")
	private String isSys;
	/**
	 * 是否可用
	 */
	private String useable;
	/**
	 * 创建者
	 */
	@TableField("create_by")
	private String createBy;
	/**
	 * 创建时间
	 */
	@TableField("create_date")
	private Date createDate;
	/**
	 * 更新者
	 */
	@TableField("update_by")
	private String updateBy;
	/**
	 * 更新时间
	 */
	@TableField("update_date")
	private Date updateDate;
	/**
	 * 备注信息
	 */
	private String remarks;
	/**
	 * 删除标记
	 */
	@TableField("del_flag")
	private String delFlag;

	@TableField(exist = false)
	private List<SysRoleMenu> roleMenus = new ArrayList<SysRoleMenu>();
	@TableField(exist = false)
	private List<SysRoleReport> roleReports = new ArrayList<SysRoleReport>();
	@TableField(exist = false)
	private List<SysGroupRole> sysGroupRoles = new ArrayList<SysGroupRole>();
	@TableField(exist = false)
	private List<SysRoleFileTemp> sysRoleFileTemps = new ArrayList<SysRoleFileTemp>();

	public List<SysRoleFileTemp> getSysRoleFileTemps() {
		return sysRoleFileTemps;
	}

	public void setSysRoleFileTemps(List<SysRoleFileTemp> sysRoleFileTemps) {
		this.sysRoleFileTemps = sysRoleFileTemps;
	}

	public List<SysGroupRole> getSysGroupRoles() {
		return sysGroupRoles;
	}

	public void setSysGroupRoles(List<SysGroupRole> sysGroupRoles) {
		this.sysGroupRoles = sysGroupRoles;
	}

	public List<SysRoleMenu> getRoleMenus() {
		return roleMenus;
	}

	public void setRoleMenus(List<SysRoleMenu> roleMenus) {
		this.roleMenus = roleMenus;
	}

	public List<SysRoleReport> getRoleReports() {
		return roleReports;
	}

	public void setRoleReports(List<SysRoleReport> roleReports) {
		this.roleReports = roleReports;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnname() {
		return enname;
	}

	public void setEnname(String enname) {
		this.enname = enname;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getDataScope() {
		return dataScope;
	}

	public void setDataScope(String dataScope) {
		this.dataScope = dataScope;
	}

	public String getIsSys() {
		return isSys;
	}

	public void setIsSys(String isSys) {
		this.isSys = isSys;
	}

	public String getUseable() {
		return useable;
	}

	public void setUseable(String useable) {
		this.useable = useable;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "SysRole{" + "id=" + id + ", name=" + name + ", enname="
				+ enname + ", roleType=" + roleType + ", dataScope="
				+ dataScope + ", isSys=" + isSys + ", useable=" + useable
				+ ", createBy=" + createBy + ", createDate=" + createDate
				+ ", updateBy=" + updateBy + ", updateDate=" + updateDate
				+ ", remarks=" + remarks + ", delFlag=" + delFlag + "}";
	}
}
