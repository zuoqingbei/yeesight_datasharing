package com.haier.datamart.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * 用户组-角色
 * @author zuoqb123
 * @date 2018-09-29
 */
@TableName("sys_group_role")
public class SysGroupRole extends Model<SysGroupRole> {

    private static final long serialVersionUID = 1L;

    /**
     * 角色编号
     */
   @TableField("role_id")
   private String roleId;
    /**
     * 用户组编号
     */
   @TableField("group_id")
   private String groupId;


   public String getRoleId() {
      return roleId;
   }

   public void setRoleId(String roleId) {
      this.roleId = roleId;
   }

   public String getGroupId() {
      return groupId;
   }

   public void setGroupId(String groupId) {
      this.groupId = groupId;
   }

   @Override
   protected Serializable pkVal() {
      return null;
   }

   @Override
   public String toString() {
      return "SysGroupRole{" +
         "roleId=" + roleId +
         ", groupId=" + groupId +
         "}";
   }
}
