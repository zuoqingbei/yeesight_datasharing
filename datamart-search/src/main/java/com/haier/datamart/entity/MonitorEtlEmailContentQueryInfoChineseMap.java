package com.haier.datamart.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 
 * @author ZhangJiang123
 * @date 2018-12-19
 */
@TableName("monitor_etl_email_content_query_info_chinese_map")
public class MonitorEtlEmailContentQueryInfoChineseMap extends Model<MonitorEtlEmailContentQueryInfoChineseMap> {

    private static final long serialVersionUID = 1L;

    /**
     * 表记录id
     */
   private String id;
    /**
     * 邮件内容记录id
     */
   @TableField("email_content_id")
   private String emailContentId;
    /**
     * 英文名
     */
   @TableField("english_name")
   private String englishName;
    /**
     * 中文名
     */
   @TableField("chinese_name")
   private String chineseName;
    /**
     * 字段顺序
     */
   @TableField("field_no")
   private Integer fieldNo;
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

   public String getEmailContentId() {
      return emailContentId;
   }

   public void setEmailContentId(String emailContentId) {
      this.emailContentId = emailContentId;
   }

   public String getEnglishName() {
      return englishName;
   }

   public void setEnglishName(String englishName) {
      this.englishName = englishName;
   }

   public String getChineseName() {
      return chineseName;
   }

   public void setChineseName(String chineseName) {
      this.chineseName = chineseName;
   }

   public Integer getFieldNo() {
      return fieldNo;
   }

   public void setFieldNo(Integer fieldNo) {
      this.fieldNo = fieldNo;
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
      return "MonitorEtlEmailContentQueryInfoChineseMap{" +
         "id=" + id +
         ", emailContentId=" + emailContentId +
         ", englishName=" + englishName +
         ", chineseName=" + chineseName +
         ", fieldNo=" + fieldNo +
         ", createBy=" + createBy +
         ", createDate=" + createDate +
         ", updateBy=" + updateBy +
         ", updateDate=" + updateDate +
         ", remarks=" + remarks +
         ", delFlag=" + delFlag +
         "}";
   }
}
