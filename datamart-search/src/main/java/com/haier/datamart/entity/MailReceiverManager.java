package com.haier.datamart.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * 邮件收件人管理
 * @author zuoqb123
 * @date 2019-01-16
 */
@TableName("mail_receiver_manager")
public class MailReceiverManager extends BaseModel<MailReceiverManager> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
   private String id;
    /**
     * 模块id
     */
   @TableField("module_id")
   private String moduleId;
    /**
     * 平台id
     */
   @TableField("plat_id")
   private String platId;
    /**
     * 用户名
     */
   private String name;
    /**
     * 电话
     */
   private String tel;
    /**
     * 邮箱
     */
   private String email;
    /**
     * 工号
     */
   private String numbers;
    /**
     * 部门
     */
   private String dept;
    /**
     *  导入状态 0-成功 1 -失败
     */
   @TableField("is_success")
   private String isSuccess;
    /**
     * 错误列 逗号拼接
     */
   @TableField("error_col")
   private String errorCol;
    /**
     * 错误信息 逗号拼接
     */
   @TableField("error_msg")
   private String errorMsg;
    /**
     * 排序
     */
   @TableId(value="order_no", type= IdType.AUTO)
   private Integer orderNo;
    /**
     * 创建时间
     */
   @TableField("create_date")
   private Date createDate;
    /**
     * 创建者
     */
   @TableField("create_by")
   private String createBy;
    /**
     * 更新时间
     */
   @TableField("update_date")
   private Date updateDate;
    /**
     * 更新人
     */
   @TableField("update_by")
   private String updateBy;
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

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getTel() {
      return tel;
   }

   public void setTel(String tel) {
      this.tel = tel;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
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

   public String getIsSuccess() {
      return isSuccess;
   }

   public void setIsSuccess(String isSuccess) {
      this.isSuccess = isSuccess;
   }

   public String getErrorCol() {
      return errorCol;
   }

   public void setErrorCol(String errorCol) {
      this.errorCol = errorCol;
   }

   public String getErrorMsg() {
      return errorMsg;
   }

   public void setErrorMsg(String errorMsg) {
      this.errorMsg = errorMsg;
   }

   public Integer getOrderNo() {
      return orderNo;
   }

   public void setOrderNo(Integer orderNo) {
      this.orderNo = orderNo;
   }

   public Date getCreateDate() {
      return createDate;
   }

   public void setCreateDate(Date createDate) {
      this.createDate = createDate;
   }

   public String getCreateBy() {
      return createBy;
   }

   public void setCreateBy(String createBy) {
      this.createBy = createBy;
   }

   public Date getUpdateDate() {
      return updateDate;
   }

   public void setUpdateDate(Date updateDate) {
      this.updateDate = updateDate;
   }

   public String getUpdateBy() {
      return updateBy;
   }

   public void setUpdateBy(String updateBy) {
      this.updateBy = updateBy;
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
      return this.orderNo;
   }

   @Override
   public String toString() {
      return "MailReceiverManager{" +
         "id=" + id +
         ", moduleId=" + moduleId +
         ", platId=" + platId +
         ", name=" + name +
         ", tel=" + tel +
         ", email=" + email +
         ", numbers=" + numbers +
         ", dept=" + dept +
         ", isSuccess=" + isSuccess +
         ", errorCol=" + errorCol +
         ", errorMsg=" + errorMsg +
         ", orderNo=" + orderNo +
         ", createDate=" + createDate +
         ", createBy=" + createBy +
         ", updateDate=" + updateDate +
         ", updateBy=" + updateBy +
         ", remarks=" + remarks +
         ", delFlag=" + delFlag +
         "}";
   }
}
