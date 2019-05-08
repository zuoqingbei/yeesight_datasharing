package com.haier.datamart.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 文件、文件夹操作记录表
 * @author ZhangJiang123
 * @date 2018-12-17
 */
@TableName("doc_upload_file_opt_history")
public class DocUploadFileOptHistory extends Model<DocUploadFileOptHistory> {

    private static final long serialVersionUID = 1L;

    /**
     * 编码
     */
   private String id;
    /**
     * 文件类型 0-目录 1-文件
     */
   @TableField("file_type")
   private String fileType;
    /**
     * 文件/目标编码
     */
   @TableField("file_id")
   private String fileId;
    /**
     * 源数据json
     */
   @TableField("json_data")
   private String jsonData;
    /**
     * 操作类型 0-预览 1-下载  2-修改 3-上传 4-删除 5-恢复
     */
   @TableField("opt_type")
   private String optType;
    /**
     * 备注信息
     */
   private String remarks;
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
     * 删除标记（0：正常；1：删除）
     */
   @TableField("del_flag")
   private String delFlag;


   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getFileType() {
      return fileType;
   }

   public void setFileType(String fileType) {
      this.fileType = fileType;
   }

   public String getFileId() {
      return fileId;
   }

   public void setFileId(String fileId) {
      this.fileId = fileId;
   }

   public String getJsonData() {
      return jsonData;
   }

   public void setJsonData(String jsonData) {
      this.jsonData = jsonData;
   }

   public String getOptType() {
      return optType;
   }

   public void setOptType(String optType) {
      this.optType = optType;
   }

   public String getRemarks() {
      return remarks;
   }

   public void setRemarks(String remarks) {
      this.remarks = remarks;
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
      return "DocUploadFileOptHistory{" +
         "id=" + id +
         ", fileType=" + fileType +
         ", fileId=" + fileId +
         ", jsonData=" + jsonData +
         ", optType=" + optType +
         ", remarks=" + remarks +
         ", createBy=" + createBy +
         ", createDate=" + createDate +
         ", updateBy=" + updateBy +
         ", updateDate=" + updateDate +
         ", delFlag=" + delFlag +
         "}";
   }
}
