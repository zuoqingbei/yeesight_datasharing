package com.haier.datamart.entity;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * 邮件模块收件人原始信息信息
 * @author zuoqb123
 * @date 2019-01-03
 */
@TableName("mail_receiver_user_info")
public class MailReceiverUserInfo extends BaseModel<MailReceiverUserInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 字段ID
     */
   private String id;
   
    /**
     * 模块
     */
   private String moduleId;
   /**
    * 平台
    */
   private String platId;
    /**
     * 用户名称
     */
   private String name;
    /**
     * 电话
     */
   private String telphone;
    /**
     * 邮箱
     */
   private String mail;
    /**
     * 工号
     */
   private String numbers;
    /**
     * 部门
     */
   private String dept;
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

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getTelphone() {
      return telphone;
   }

   public void setTelphone(String telphone) {
      this.telphone = telphone;
   }

   public String getMail() {
      return mail;
   }

   public void setMail(String mail) {
      this.mail = mail;
   }

   public String getNumbers() {
      return numbers;
   }

   public void setNumbers(String numbers) {
      this.numbers = numbers;
   }

   public String getDept() {
      return dept;
   }

   public void setDept(String dept) {
      this.dept = dept;
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


public String getModuleId() {
	return moduleId;
}

public void setModuleId(String moduleId) {
	this.moduleId = moduleId;
}

public String getPlatId() {
	return platId;
}

public void setPlatId(String platId) {
	this.platId = platId;
}

@Override
public String toString() {
	return "MailReceiverUserInfo [id=" + id + ", moduleId=" + moduleId + ", platId=" + platId + ", name=" + name
			+ ", telphone=" + telphone + ", mail=" + mail + ", numbers=" + numbers + ", dept=" + dept + ", createBy="
			+ createBy + ", createDate=" + createDate + ", updateBy=" + updateBy + ", updateDate=" + updateDate
			+ ", remarks=" + remarks + ", delFlag=" + delFlag + "]";
}

public static long getSerialversionuid() {
	return serialVersionUID;
}

   
}
