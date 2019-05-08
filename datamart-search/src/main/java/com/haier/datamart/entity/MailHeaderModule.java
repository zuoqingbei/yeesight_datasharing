package com.haier.datamart.entity;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * 邮件抬头预设置语句
 * @author zuoqb123
 * @date 2019-01-03
 */
@TableName("mail_header_module")
public class MailHeaderModule extends BaseModel<MailHeaderModule> {

    private static final long serialVersionUID = 1L;

    /**
     * 字段ID
     */
   private String id;
    /**
     * 平台编码
     */
   @TableField("plat_id")
   private String platId;
    /**
     * 原始语句范例
     */
   @TableField("org_content")
   private String orgContent;
   
   //该语句被运维处理过-1,反之-0
   @TableField("is_complete")
   private String isComplete;
   // 是快速插入语句(无占位符) -0 反之-1
   @TableField("is_fast_content")
   private String isFastContent;
   /**
    * 文档id,以逗号隔开
    */
   @TableField("doument_ids")
   private String doumentIds;
   
    
   @TableField(exist=false)
   private String dealStr;
    /**
     * 处理完后语句范例（带有占位符）
     */
   @TableField("deal_content")
   private String dealContent;
   @TableField(exist=false)
   private String isShow;
   public String getIsShow() {
	return isShow;
}

public void setIsShow(String isShow) {
	this.isShow = isShow;
}

public String getDealStr() {
		return dealStr;
	}

	public void setDealStr(String dealStr) {
		this.dealStr = dealStr;
	}

public String getIsComplete() {
      return isComplete;
   }

   public void setIsComplete(String isComplete) {
      this.isComplete = isComplete;
   }

   public String getIsFastContent() {
      return isFastContent;
   }

   public void setIsFastContent(String isFastContent) {
      this.isFastContent = isFastContent;
   }

   public String getDoumentIds() {
		return doumentIds;
	}

	public void setDoumentIds(String doumentIds) {
		this.doumentIds = doumentIds;
	}

	/**
     * 参数来源描述
     */
   @TableField("params_desc")
   private String paramsDesc;
    /**
     * 短信标题
     */
   @TableField("message_title")
   private String messageTitle;
    /**
     * 申请人姓名
     */
   @TableField("apply_user")
   private String applyUser;
    /**
     * 申请人电话
     */
   @TableField("apply_tel")
   private String applyTel;
    /**
     * 申请人邮箱
     */
   @TableField("apply_mail")
   private String applyMail;
    /**
     * 申请人工号
     */
   @TableField("apply_number")
   private String applyNumber;
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

  

   public String getPlatId() {
	return platId;
}

public void setPlatId(String platId) {
	this.platId = platId;
}

public static long getSerialversionuid() {
	return serialVersionUID;
}

public String getOrgContent() {
      return orgContent;
   }

   public void setOrgContent(String orgContent) {
      this.orgContent = orgContent;
   }

   public String getDealContent() {
      return dealContent;
   }

   public void setDealContent(String dealContent) {
      this.dealContent = dealContent;
   }

   public String getParamsDesc() {
      return paramsDesc;
   }

   public void setParamsDesc(String paramsDesc) {
      this.paramsDesc = paramsDesc;
   }

   public String getMessageTitle() {
      return messageTitle;
   }

   public void setMessageTitle(String messageTitle) {
      this.messageTitle = messageTitle;
   }

   public String getApplyUser() {
      return applyUser;
   }

   public void setApplyUser(String applyUser) {
      this.applyUser = applyUser;
   }

   public String getApplyTel() {
      return applyTel;
   }

   public void setApplyTel(String applyTel) {
      this.applyTel = applyTel;
   }

   public String getApplyMail() {
      return applyMail;
   }

   public void setApplyMail(String applyMail) {
      this.applyMail = applyMail;
   }

   public String getApplyNumber() {
      return applyNumber;
   }

   public void setApplyNumber(String applyNumber) {
      this.applyNumber = applyNumber;
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
	return "MailHeaderModule [id=" + id + ", platId=" + platId + ", orgContent=" + orgContent + ", isComplete="
			+ isComplete + ", isFastContent=" + isFastContent + ", doumentIds=" + doumentIds + ", dealStr=" + dealStr
			+ ", dealContent=" + dealContent + ", paramsDesc=" + paramsDesc + ", messageTitle=" + messageTitle
			+ ", applyUser=" + applyUser + ", applyTel=" + applyTel + ", applyMail=" + applyMail + ", applyNumber="
			+ applyNumber + ", createBy=" + createBy + ", createDate=" + createDate + ", updateBy=" + updateBy
			+ ", updateDate=" + updateDate + ", remarks=" + remarks + ", delFlag=" + delFlag + "]";
}

   
}
