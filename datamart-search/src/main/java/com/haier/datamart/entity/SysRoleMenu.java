package com.haier.datamart.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * 角色-菜单
 * @author zuoqb123
 * @date 2018-09-29
 */
@TableName("sys_role_menu")
public class SysRoleMenu extends Model<SysRoleMenu> {

    private static final long serialVersionUID = 1L;

    /**
     * 角色编号
     */
   @TableField("role_id")
   private String roleId;
    /**
     * 菜单编号
     */
   @TableField("menu_id")
   private String menuId;


   public String getRoleId() {
      return roleId;
   }

   public void setRoleId(String roleId) {
      this.roleId = roleId;
   }

   public String getMenuId() {
      return menuId;
   }

   public void setMenuId(String menuId) {
      this.menuId = menuId;
   }

   @Override
   protected Serializable pkVal() {
      return null;
   }

   @Override
   public String toString() {
      return "SysRoleMenu{" +
         "roleId=" + roleId +
         ", menuId=" + menuId +
         "}";
   }
}
