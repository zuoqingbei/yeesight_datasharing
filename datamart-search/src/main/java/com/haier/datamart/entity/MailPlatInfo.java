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
 * @date 2019-01-09
 */
@TableName("mail_plat_info")
public class MailPlatInfo extends BaseModel<MailPlatInfo> {

    private static final long serialVersionUID = 1L;
    /**
     * 申请人邮箱
     */
    @TableField("proposer_mail")
    private String proposerMail;
    /**
     * 字段ID
     */
   private String id;
   //文档url,以@#号隔开
   @TableField("document_url")
   private String documentUrl;
    /**
     * 模板id(弃用)
     */
   @TableField("templete_id")
   private String templeteId;
   //申请通过-1 ,反之-0
   @TableField("is_complete")
   private String isComplete;
   
    /**
     * 模块id
     */
   @TableField("module_id")
   private String moduleId;
   /**
    * 模块名称
    */
   @TableField(exist = false)
   private String moduleName;
   
    /**
     * 平台名称
     */
   @TableField("plat_name")
   private String platName;
    public String getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(String isComplete) {
		this.isComplete = isComplete;
	}
	
	public String getProposerMail() {
		return proposerMail;
	}

	public void setProposerMail(String proposerMail) {
		this.proposerMail = proposerMail;
	}

	/**
     * 申请人名称
     */
   @TableField("proposer_name")
   private String proposerName;
    /**
     * 申请人联系方式
     */
   @TableField("proposer_contact_way")
   private String proposerContactWay;
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

   
   public String getDocumentUrl() {
		return documentUrl;
	}

	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
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

   public String getTempleteId() {
      return templeteId;
   }

   public void setTempleteId(String templeteId) {
      this.templeteId = templeteId;
   }

   public String getModuleId() {
      return moduleId;
   }

   public void setModuleId(String moduleId) {
      this.moduleId = moduleId;
   }

   public String getPlatName() {
      return platName;
   }

   public void setPlatName(String platName) {
      this.platName = platName;
   }

   public String getProposerName() {
      return proposerName;
   }

   public void setProposerName(String proposerName) {
      this.proposerName = proposerName;
   }

   public String getProposerContactWay() {
      return proposerContactWay;
   }

   public void setProposerContactWay(String proposerContactWay) {
      this.proposerContactWay = proposerContactWay;
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
   
   public String getModuleName() {
	return moduleName;
}

public void setModuleName(String moduleName) {
	this.moduleName = moduleName;
}

@Override
   protected Serializable pkVal() {
      return this.id;
   }
   
@Override
public String toString() {
	return "MailPlatInfo [proposerMail=" + proposerMail + ", id=" + id + ", documentUrl=" + documentUrl
			+ ", templeteId=" + templeteId + ", isComplete=" + isComplete + ", moduleId=" + moduleId + ", moduleName="
			+ moduleName + ", platName=" + platName + ", proposerName=" + proposerName + ", proposerContactWay="
			+ proposerContactWay + ", createBy=" + createBy + ", createDate=" + createDate + ", updateBy=" + updateBy
			+ ", updateDate=" + updateDate + ", remarks=" + remarks + ", delFlag=" + delFlag + "]";
}

   
}
