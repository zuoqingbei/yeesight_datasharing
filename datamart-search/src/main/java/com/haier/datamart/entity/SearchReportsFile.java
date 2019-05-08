package com.haier.datamart.entity;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * 报表指标间表
 * @author zuoqb123
 * @date 2018-10-09
 */
@TableName("search_reports_file")
public class SearchReportsFile extends Model<SearchReportsFile> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
   private String id;
    /**
     * 报表id
     */
   @TableField("report_id")
   private String reportId;
    /**
     * 文件路径
     */
   @TableField("doc_path")
   private String docPath;
    /**
     * 文件名称
     */
   @TableField("doc_name")
   private String docName;
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

   public String getReportId() {
      return reportId;
   }

   public void setReportId(String reportId) {
      this.reportId = reportId;
   }

   public String getDocPath() {
      return docPath;
   }

   public void setDocPath(String docPath) {
      this.docPath = docPath;
   }

   public String getDocName() {
      return docName;
   }

   public void setDocName(String docName) {
      this.docName = docName;
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
      return "SearchReportsFile{" +
         "id=" + id +
         ", reportId=" + reportId +
         ", docPath=" + docPath +
         ", docName=" + docName +
         ", createBy=" + createBy +
         ", createDate=" + createDate +
         ", updateBy=" + updateBy +
         ", updateDate=" + updateDate +
         ", remarks=" + remarks +
         ", delFlag=" + delFlag +
         "}";
   }
}
