package com.haier.datamart.entity;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * sql执行日志
 * @author zuoqb123
 * @date 2019-01-02
 */
@TableName("admin_data_sql_exce_logs")
public class AdminDataSqlExceLogs extends Model<AdminDataSqlExceLogs> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
   private String id;
    /**
     * 数据源编码
     */
   @TableField("data_source_id")
   private String dataSourceId;
    /**
     * 表编码
     */
   @TableField("content_id")
   private String contentId;
   private String sqls;
   private String params;
    /**
     * 执行结果 0-失败 1-成功
     */
   @TableField("exc_status")
   private String excStatus;
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

   public String getDataSourceId() {
      return dataSourceId;
   }

   public void setDataSourceId(String dataSourceId) {
      this.dataSourceId = dataSourceId;
   }

   public String getContentId() {
      return contentId;
   }

   public void setContentId(String contentId) {
      this.contentId = contentId;
   }

   public String getSqls() {
      return sqls;
   }

   public void setSqls(String sqls) {
      this.sqls = sqls;
   }

   public String getParams() {
      return params;
   }

   public void setParams(String params) {
      this.params = params;
   }

   public String getExcStatus() {
      return excStatus;
   }

   public void setExcStatus(String excStatus) {
      this.excStatus = excStatus;
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
      return "AdminDataSqlExceLogs{" +
         "id=" + id +
         ", dataSourceId=" + dataSourceId +
         ", contentId=" + contentId +
         ", sqls=" + sqls +
         ", params=" + params +
         ", excStatus=" + excStatus +
         ", createBy=" + createBy +
         ", createDate=" + createDate +
         ", updateBy=" + updateBy +
         ", updateDate=" + updateDate +
         ", remarks=" + remarks +
         ", delFlag=" + delFlag +
         "}";
   }
}
