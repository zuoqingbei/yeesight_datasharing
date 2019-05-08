package com.haier.datamart.entity;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * jenkins信息
 * @author zuoqb123
 * @date 2018-11-30
 */
@TableName("jenkins_support_info")
public class JenkinsSupportInfo extends Model<JenkinsSupportInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
   private String id;
    /**
     * 0-系统 1-报表
     */
   private String type;
    /**
     * 业务编码
     */
   @TableField("business_id")
   private String businessId;
    /**
     * 调度名称
     */
   private String name;
   private String fullName;
    /**
     * 显示名
     */
   private String displayName;
   private String fullDisplayName;
    /**
     * 调度地址
     */
   private String url;
    /**
     * 是否可以构建 0-可以 1-不可以
     */
   private String buildable;
    /**
     * 创建人
     */
   @TableField("create_by")
   private String createBy;
    /**
     * 创建时间
     */
   @TableField("create_date")
   private Date createDate;
    /**
     * 更新人
     */
   @TableField("update_by")
   private String updateBy;
    /**
     * 更新时间
     */
   @TableField("update_date")
   private Date updateDate;
    /**
     * 备注
     */
   private String remarks;
   @TableField("del_flag")
   private String delFlag;


   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getType() {
      return type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public String getBusinessId() {
      return businessId;
   }

   public void setBusinessId(String businessId) {
      this.businessId = businessId;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getFullName() {
      return fullName;
   }

   public void setFullName(String fullName) {
      this.fullName = fullName;
   }

   public String getDisplayName() {
      return displayName;
   }

   public void setDisplayName(String displayName) {
      this.displayName = displayName;
   }

   public String getFullDisplayName() {
      return fullDisplayName;
   }

   public void setFullDisplayName(String fullDisplayName) {
      this.fullDisplayName = fullDisplayName;
   }

   public String getUrl() {
      return url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   public String getBuildable() {
      return buildable;
   }

   public void setBuildable(String buildable) {
      this.buildable = buildable;
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
      return "JenkinsSupportInfo{" +
         "id=" + id +
         ", type=" + type +
         ", businessId=" + businessId +
         ", name=" + name +
         ", fullName=" + fullName +
         ", displayName=" + displayName +
         ", fullDisplayName=" + fullDisplayName +
         ", url=" + url +
         ", buildable=" + buildable +
         ", createBy=" + createBy +
         ", createDate=" + createDate +
         ", updateBy=" + updateBy +
         ", updateDate=" + updateDate +
         ", remarks=" + remarks +
         ", delFlag=" + delFlag +
         "}";
   }
}
