package com.haier.datamart.entity;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * 邮件模板信息（后台配置，给业务前台使用）
 * @author zuoqb123
 * @date 2019-01-03
 */
@TableName("mail_templete_info")
public class MailTempleteInfo extends BaseModel<MailTempleteInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 字段ID
     */
   private String id;
    /**
     * 模板名称
     */
   @TableField("templete_name")
   private String templeteName;
    /**
     * 收件人信息 monitor_etl_mail_receiver的reciver_id
     */
   @TableField("reciver_id")
   private String reciverId;
    /**
     * 模板样式
     */
   private String style;
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

   public String getTempleteName() {
      return templeteName;
   }

   public void setTempleteName(String templeteName) {
      this.templeteName = templeteName;
   }

   public String getReciverId() {
      return reciverId;
   }

   public void setReciverId(String reciverId) {
      this.reciverId = reciverId;
   }

   public String getStyle() {
      return style;
   }

   public void setStyle(String style) {
      this.style = style;
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
      return "MailTempleteInfo{" +
         "id=" + id +
         ", templeteName=" + templeteName +
         ", reciverId=" + reciverId +
         ", style=" + style +
         ", createBy=" + createBy +
         ", createDate=" + createDate +
         ", updateBy=" + updateBy +
         ", updateDate=" + updateDate +
         ", remarks=" + remarks +
         ", delFlag=" + delFlag +
         "}";
   }
}
