package com.haier.datamart.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * 用户组-报告
 * @author zuoqb123
 * @date 2018-09-29
 */
@TableName("sys_role_report")
public class SysRoleReport extends Model<SysRoleReport> {

    private static final long serialVersionUID = 1L;

    /**
     * 角色编号
     */
   @TableField("role_id")
   private String roleId;
    /**
     * 报告编号
     */
   @TableField("report_id")
   private String reportId;


   public String getRoleId() {
      return roleId;
   }

   public void setRoleId(String roleId) {
      this.roleId = roleId;
   }

   public String getReportId() {
      return reportId;
   }

   public void setReportId(String reportId) {
      this.reportId = reportId;
   }

   @Override
   protected Serializable pkVal() {
      return null;
   }

   @Override
   public String toString() {
      return "SysRoleReport{" +
         "roleId=" + roleId +
         ", reportId=" + reportId +
         "}";
   }
}
