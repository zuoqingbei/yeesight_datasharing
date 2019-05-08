package com.haier.datamart.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 角色与文件目录对照表
 * @author zuoqb123
 * @date 2018-12-10
 */
@TableName("sys_role_file_temp")
public class SysRoleFileTemp extends Model<SysRoleFileTemp> {

    private static final long serialVersionUID = 1L;

    /**
     * 角色编码
     */
   @TableField("role_id")
   private String roleId;
    /**
     * 目录编码
     */
   @TableField("temp_id")
   private String tempId;
    /**
     * 权限 add/update/delete/download/view   逗号分开
     */
   private String opt;


   public String getRoleId() {
      return roleId;
   }

   public void setRoleId(String roleId) {
      this.roleId = roleId;
   }

   public String getTempId() {
      return tempId;
   }

   public void setTempId(String tempId) {
      this.tempId = tempId;
   }

   public String getOpt() {
      return opt;
   }

   public void setOpt(String opt) {
      this.opt = opt;
   }

   @Override
   protected Serializable pkVal() {
      return null;
   }

   @Override
   public String toString() {
      return "SysRoleFileTemp{" +
         "roleId=" + roleId +
         ", tempId=" + tempId +
         ", opt=" + opt +
         "}";
   }
}
