package com.haier.datamart.entity;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * 邮件抬头预设置语句中占位符阈值控制
 * @author zuoqb123
 * @date 2019-02-13
 */
@TableName("mail_header_module_placeholder_threshold")
public class MailHeaderModulePlaceholderThreshold extends BaseModel<MailHeaderModulePlaceholderThreshold> {

    private static final long serialVersionUID = 1L;

    /**
     * 字段ID
     */
   private String id;
    /**
     * 占位符编码
     */
   @TableField("placeholder_id")
   private String placeholderId;
    /**
     * 运算符  >; < ;>= ;<=
     */
   private String yunsuanfu;
    /**
     * 阈值 
     */
   @TableField("threshold_value")
   private String thresholdValue;
    /**
     * 对比之后的操作 0-显示 1-隐藏
     */
   private String opt;
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


   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getPlaceholderId() {
      return placeholderId;
   }

   public void setPlaceholderId(String placeholderId) {
      this.placeholderId = placeholderId;
   }

   public String getYunsuanfu() {
      return yunsuanfu;
   }

   public void setYunsuanfu(String yunsuanfu) {
      this.yunsuanfu = yunsuanfu;
   }

   public String getThresholdValue() {
      return thresholdValue;
   }

   public void setThresholdValue(String thresholdValue) {
      this.thresholdValue = thresholdValue;
   }

   public String getOpt() {
      return opt;
   }

   public void setOpt(String opt) {
      this.opt = opt;
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
      return "MailHeaderModulePlaceholderThreshold{" +
         "id=" + id +
         ", placeholderId=" + placeholderId +
         ", yunsuanfu=" + yunsuanfu +
         ", thresholdValue=" + thresholdValue +
         ", opt=" + opt +
         ", createBy=" + createBy +
         ", createDate=" + createDate +
         ", updateBy=" + updateBy +
         ", updateDate=" + updateDate +
         ", remarks=" + remarks +
         ", delFlag=" + delFlag +
         "}";
   }
}
