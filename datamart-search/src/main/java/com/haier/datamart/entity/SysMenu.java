package com.haier.datamart.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * 菜单表
 * @author zuoqb123
 * @date 2018-09-30
 */
@TableName("sys_menu")
public class SysMenu extends Model<SysMenu> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
   private String id;
    /**
     * 父级编号
     */
   @TableField("parent_id")
   private String parentId;
    /**
     * 所有父级编号
     */
   @TableField("parent_ids")
   private String parentIds;
    /**
     * 名称
     */
   private String name;
    /**
     * 排序
     */
   private BigDecimal sort;
    /**
     * 菜单类型 0-后台菜单 1-补录菜单
     */
   @TableField("menu_type")
   private String menuType;
    /**
     * 链接
     */
   private String href;
    /**
     * 目标
     */
   private String target;
    /**
     * 图标
     */
   private String icon;
    /**
     * 是否在菜单中显示 0-显示 1-隐藏
     */
   @TableField("is_show")
   private String isShow;
    /**
     * 权限标识
     */
   private String permission;
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
     * 备注信息(存储类名)
     */
   private String remarks;
    /**
     * 删除标记
     */
   @TableField("del_flag")
   private String delFlag;
   @TableField(exist=false)
   private List<SysMenu> children=new ArrayList<SysMenu>();
   
   public List<SysMenu> getChildren() {
	return children;
}

public void setChildren(List<SysMenu> children) {
	this.children = children;
}

public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getParentId() {
      return parentId;
   }

   public void setParentId(String parentId) {
      this.parentId = parentId;
   }

   public String getParentIds() {
      return parentIds;
   }

   public void setParentIds(String parentIds) {
      this.parentIds = parentIds;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public BigDecimal getSort() {
      return sort;
   }

   public void setSort(BigDecimal sort) {
      this.sort = sort;
   }

   public String getMenuType() {
      return menuType;
   }

   public void setMenuType(String menuType) {
      this.menuType = menuType;
   }

   public String getHref() {
      return href;
   }

   public void setHref(String href) {
      this.href = href;
   }

   public String getTarget() {
      return target;
   }

   public void setTarget(String target) {
      this.target = target;
   }

   public String getIcon() {
      return icon;
   }

   public void setIcon(String icon) {
      this.icon = icon;
   }

   public String getIsShow() {
      return isShow;
   }

   public void setIsShow(String isShow) {
      this.isShow = isShow;
   }

   public String getPermission() {
      return permission;
   }

   public void setPermission(String permission) {
      this.permission = permission;
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
      return "SysMenu{" +
         "id=" + id +
         ", parentId=" + parentId +
         ", parentIds=" + parentIds +
         ", name=" + name +
         ", sort=" + sort +
         ", menuType=" + menuType +
         ", href=" + href +
         ", target=" + target +
         ", icon=" + icon +
         ", isShow=" + isShow +
         ", permission=" + permission +
         ", createBy=" + createBy +
         ", createDate=" + createDate +
         ", updateBy=" + updateBy +
         ", updateDate=" + updateDate +
         ", remarks=" + remarks +
         ", delFlag=" + delFlag +
         "}";
   }
}
