package com.haier.datamart.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * 
 * @author zuoqb123
 * @date 2018-11-21
 */
@TableName("minitor_sys_error_file")
public class MinitorSysErrorFile extends Model<MinitorSysErrorFile> {

    private static final long serialVersionUID = 1L;

   private String id;
    /**
     * 系统
     */
   @TableField("error_id")
   private String errorId;
    /**
     * 平台
     */
   @TableField("file_id")
   private String fileId;


   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getErrorId() {
      return errorId;
   }

   public void setErrorId(String errorId) {
      this.errorId = errorId;
   }

   public String getFileId() {
      return fileId;
   }

   public void setFileId(String fileId) {
      this.fileId = fileId;
   }

   @Override
   protected Serializable pkVal() {
      return this.id;
   }

   @Override
   public String toString() {
      return "MinitorSysErrorFile{" +
         "id=" + id +
         ", errorId=" + errorId +
         ", fileId=" + fileId +
         "}";
   }
}
