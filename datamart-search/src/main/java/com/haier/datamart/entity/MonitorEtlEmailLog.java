package com.haier.datamart.entity;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * 
 * @author zuoqb123
 * @date 2019-01-25
 */
@TableName("monitor_etl_email_log")
public class MonitorEtlEmailLog extends BaseModel<MonitorEtlEmailLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
   private String id;
    /**
     * 触发邮件的ID
     */
   @TableField("mail_id")
   private String mailId;
    /**
     * 发送邮件的结果 1 成功 0 失败
     */
   @TableField("mail_result")
   private String mailResult;
    /**
     * 发送邮件的结果描述
     */
   @TableField("mail_result_desc")
   private String mailResultDesc;
    /**
     * 发送短信的结果 1 成功 0 失败
     */
   @TableField("message_result")
   private String messageResult;
    /**
     * 发送短信的结果描述
     */
   @TableField("message_result_desc")
   private String messageResultDesc;
    /**
     * 发送消息的上下文 把消息序列化为sjon
     */
   private String context;
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

   public String getMailId() {
      return mailId;
   }

   public void setMailId(String mailId) {
      this.mailId = mailId;
   }

   public String getMailResult() {
      return mailResult;
   }

   public void setMailResult(String mailResult) {
      this.mailResult = mailResult;
   }

   public String getMailResultDesc() {
      return mailResultDesc;
   }

   public void setMailResultDesc(String mailResultDesc) {
      this.mailResultDesc = mailResultDesc;
   }

   public String getMessageResult() {
      return messageResult;
   }

   public void setMessageResult(String messageResult) {
      this.messageResult = messageResult;
   }

   public String getMessageResultDesc() {
      return messageResultDesc;
   }

   public void setMessageResultDesc(String messageResultDesc) {
      this.messageResultDesc = messageResultDesc;
   }

   public String getContext() {
      return context;
   }

   public void setContext(String context) {
      this.context = context;
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
      return "MonitorEtlEmailLog{" +
         "id=" + id +
         ", mailId=" + mailId +
         ", mailResult=" + mailResult +
         ", mailResultDesc=" + mailResultDesc +
         ", messageResult=" + messageResult +
         ", messageResultDesc=" + messageResultDesc +
         ", context=" + context +
         ", createBy=" + createBy +
         ", createDate=" + createDate +
         ", updateBy=" + updateBy +
         ", updateDate=" + updateDate +
         ", remarks=" + remarks +
         ", delFlag=" + delFlag +
         "}";
   }
}
