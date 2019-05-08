package com.haier.datamart.entity;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * 邮件抬头信息
 * @author zuoqb123
 * @date 2019-01-03
 */
@TableName("mail_header_info")
public class MailHeaderInfo extends BaseModel<MailHeaderInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 字段ID
     */
   private String id;
   /**
    * 拼接好的字符
    */
   @TableField(exist=false)
   private String implementStr;
    /**
     * 邮件编码
     */
   @TableField("mail_id")
   private String mailId;
    /**
     * 原始语句范例
     */
   private String content;
    /**
     * 抬头预设置编码  如果是直接输入的值则为空
     */
   @TableField("header_module_id")
   private String headerModuleId;
    /**
     * 排序
     */
   @TableField("order_no")
   private Integer orderNo;
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
   @TableField(exist=false)
   private String isShow="1";
  

public String getIsShow() {
	return isShow;
}

public void setIsShow(String isShow) {
	this.isShow = isShow;
}

public String getImplementStr() {
		return implementStr;
	}

	public void setImplementStr(String implementStr) {
		this.implementStr = implementStr;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

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

   public String getContent() {
      return content;
   }

   public void setContent(String content) {
      this.content = content;
   }

   public String getHeaderModuleId() {
      return headerModuleId;
   }

   public void setHeaderModuleId(String headerModuleId) {
      this.headerModuleId = headerModuleId;
   }

   public Integer getOrderNo() {
      return orderNo;
   }

   public void setOrderNo(Integer orderNo) {
      this.orderNo = orderNo;
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
	return "MailHeaderInfo [id=" + id + ", implementStr=" + implementStr + ", mailId=" + mailId + ", content=" + content
			+ ", headerModuleId=" + headerModuleId + ", orderNo=" + orderNo + ", createBy=" + createBy + ", createDate="
			+ createDate + ", updateBy=" + updateBy + ", updateDate=" + updateDate + ", remarks=" + remarks
			+ ", delFlag=" + delFlag + "]";
}
}
