package com.haier.datamart.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 用户组
 * 
 * @author zuoqb123
 * @date 2018-09-29
 */
@TableName("sys_group")
public class SysGroup extends Model<SysGroup> {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	private String id;
	/**
	 * 用户组名称
	 */
	private String name;
	/**
	 * 英文名称
	 */
	private String enname;
	/**
	 * 组管理员
	 */
	private String manager;
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
	private List<SysGroupRole> groupRoles = new ArrayList<SysGroupRole>();// 组-角色中间

	@TableField(exist = false)
	private List<SysProductGroup> groupProducts = new ArrayList<SysProductGroup>();// 组-项目

	@TableField(exist = false)
	private List<SysUserGroup> groupUsers = new ArrayList<SysUserGroup>();// 组-用户

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<SysGroupRole> getGroupRoles() {
		return groupRoles;
	}

	public void setGroupRoles(List<SysGroupRole> groupRoles) {
		this.groupRoles = groupRoles;
	}

	public List<SysProductGroup> getGroupProducts() {
		return groupProducts;
	}

	public void setGroupProducts(List<SysProductGroup> groupProducts) {
		this.groupProducts = groupProducts;
	}

	public List<SysUserGroup> getGroupUsers() {
		return groupUsers;
	}

	public void setGroupUsers(List<SysUserGroup> groupUsers) {
		this.groupUsers = groupUsers;
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

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
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
		return "SysGroup{" + "id=" + id + ", name=" + name + ", enname="
				+ enname + ", manager=" + manager + ", useable=" + useable
				+ ", createBy=" + createBy + ", createDate=" + createDate
				+ ", updateBy=" + updateBy + ", updateDate=" + updateDate
				+ ", remarks=" + remarks + ", delFlag=" + delFlag + "}";
	}
}
