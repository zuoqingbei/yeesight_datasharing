package com.haier.datamart.entity;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * 统一接口中解析的sql中表关系
 * @author zuoqb123
 * @date 2018-12-07
 */
@TableName("data_interface_table_relative")
public class DataInterfaceTableRelative extends Model<DataInterfaceTableRelative> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
   private String id;
    /**
     * 接口编码
     */
   @TableField("exc_id")
   private String excId;
    /**
     * 数据库名称
     */
   @TableField("db_name")
   private String dbName;
    /**
     * 表
     */
   @TableField("content_id")
   private String contentId;
    /**
     * 字段
     */
   @TableField("content_detail")
   private String contentDetail;
    /**
     * 表所在数据源 展示关系不需要做控制 看明细如果没有相应权限需要申请
     */
   @TableField("data_source_id")
   private String dataSourceId;
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

   public String getExcId() {
      return excId;
   }

   public void setExcId(String excId) {
      this.excId = excId;
   }

   public String getDbName() {
      return dbName;
   }

   public void setDbName(String dbName) {
      this.dbName = dbName;
   }

   public String getContentId() {
      return contentId;
   }

   public void setContentId(String contentId) {
      this.contentId = contentId;
   }

   public String getContentDetail() {
      return contentDetail;
   }

   public void setContentDetail(String contentDetail) {
      this.contentDetail = contentDetail;
   }

   public String getDataSourceId() {
      return dataSourceId;
   }

   public void setDataSourceId(String dataSourceId) {
      this.dataSourceId = dataSourceId;
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
      return "DataInterfaceTableRelative{" +
         "id=" + id +
         ", excId=" + excId +
         ", dbName=" + dbName +
         ", contentId=" + contentId +
         ", contentDetail=" + contentDetail +
         ", dataSourceId=" + dataSourceId +
         ", createBy=" + createBy +
         ", createDate=" + createDate +
         ", updateBy=" + updateBy +
         ", updateDate=" + updateDate +
         ", remarks=" + remarks +
         ", delFlag=" + delFlag +
         "}";
   }
}
