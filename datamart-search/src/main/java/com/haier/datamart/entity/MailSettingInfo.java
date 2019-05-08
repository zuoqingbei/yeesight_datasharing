package com.haier.datamart.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * 邮件设置信息
 * @author zuoqb123
 * @date 2019-01-03
 */
@TableName("mail_setting_info")
public class MailSettingInfo extends BaseModel<MailSettingInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 字段ID
     */
   private String id;
   /**
    * 下次发送时间
    */
   @TableField("next_send_time")
   private String nextSendTime;
   
   /**
    * cron表达式
    */
   private String cron;
   /**
      * 邮件发送次数
    */
   @TableField("mail_send_times")
   private String mailSendTimes;
    /**
     * 平台
     */
   @TableField("plat_id")
   private String platId;
    /**
     *模块id
     */
   @TableField("module_id")
   private String moduleId;
   
   /**
    *邮件抄送人Id
    */
  @TableField("mail_cs_reciver_id")
  private String mailCsReciverId;
   
    /**
     * 模板编码（后台配置）
     */
   @TableField("templete_id")
   private String templeteId;
   /**
    * 邮件抬头
    */
   @TableField("mail_first_paragraph")
   private String mailFirstParagraph;
   
    /**
     * 发送时间
     */
   @TableField("send_date")
   private String sendDate;
    /**
     * 短信发送开关 0-关闭 1-开启
     */
   @TableField("message_open")
   private String messageOpen;
    /**
     * 短信标题
     */
   @TableField("message_title")
   private String messageTitle;
    /**
     * 短信收件人组编码  引自monitor_etl_mail_receiver的reciver_id
     */
   @TableField("message_reciver_id")
   private String messageReciverId;
    /**
     * 邮箱发送开关 0-关闭 1-开启
     */
   @TableField("mail_open")
   private String mailOpen;
    /**
     * 邮件标题
     */
   @TableField("mail_title")
   private String mailTitle;
    
   /**
    * 邮件抬头来自临时编辑还是来自模板 0-临时编辑 1-模板'
    */
   @TableField("mail_title_from")
   private String mailTitleFrom;
   
   public String getMailTitleFrom() {
		return mailTitleFrom;
	}

	public void setMailTitleFrom(String mailTitleFrom) {
		this.mailTitleFrom = mailTitleFrom;
	}
	/**
     * 邮件收件人组编码  引自monitor_etl_mail_receiver的reciver_id
     */
@TableField("mail_reciver_id")
   private String mailReciverId;
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
   private List<MailHeaderInfo> mailHeaderInfoLists=new ArrayList<MailHeaderInfo>();//关联的邮件头信息
   @TableField(exist=false)
   private List<MailReceiverManager> messageRecevers=new ArrayList<MailReceiverManager>();//短信接收人
   
   @TableField(exist=false)
   private List<MailReceiverManager> mailJsRecevers=new ArrayList<MailReceiverManager>();//邮件接收人
   
   @TableField(exist=false)
   private List<MailReceiverManager> messageCsRecevers=new ArrayList<MailReceiverManager>();//邮件抄送人

   public List<MailHeaderInfo> getMailHeaderInfoLists() {
	return mailHeaderInfoLists;
}

public void setMailHeaderInfoLists(List<MailHeaderInfo> mailHeaderInfoLists) {
	this.mailHeaderInfoLists = mailHeaderInfoLists;
}

public List<MailReceiverManager> getMessageRecevers() {
	return messageRecevers;
}

public void setMessageRecevers(List<MailReceiverManager> messageRecevers) {
	this.messageRecevers = messageRecevers;
}

public List<MailReceiverManager> getMailJsRecevers() {
	return mailJsRecevers;
}

public void setMailJsRecevers(List<MailReceiverManager> mailJsRecevers) {
	this.mailJsRecevers = mailJsRecevers;
}

public List<MailReceiverManager> getMessageCsRecevers() {
	return messageCsRecevers;
}

public void setMessageCsRecevers(List<MailReceiverManager> messageCsRecevers) {
	this.messageCsRecevers = messageCsRecevers;
}

public String getMailCsReciverId() {
	return mailCsReciverId;
}

public void setMailCsReciverId(String mailCsReciverId) {
	this.mailCsReciverId = mailCsReciverId;
}

public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

    

   public String getMailFirstParagraph() {
	return mailFirstParagraph;
}

public void setMailFirstParagraph(String mailFirstParagraph) {
	this.mailFirstParagraph = mailFirstParagraph;
}

public String getPlatId() {
	return platId;
}

public void setPlatId(String platId) {
	this.platId = platId;
}

public String getModuleId() {
	return moduleId;
}

public void setModuleId(String moduleId) {
	this.moduleId = moduleId;
}

public static long getSerialversionuid() {
	return serialVersionUID;
}

public String getNextSendTime() {
	return nextSendTime;
}
public Date getNextSendTime2(String formatStr) {
	SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
	Date date = null;
	try {
		if(nextSendTime==null) {
			return null;
		}
		date = sdf.parse(nextSendTime);
	} catch (ParseException e) {
		e.printStackTrace();
		return null;
	}
	return date;
}

public void setNextSendTime2(Date date) {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	this.nextSendTime = sdf.format(date);
}
public void setNextSendTime(String nextSendTime) {
	this.nextSendTime = nextSendTime;
}

public String getCron() {
	return cron;
}

public void setCron(String cron) {
	this.cron = cron;
}

public String getTempleteId() {
      return templeteId;
   }

   public void setTempleteId(String templeteId) {
      this.templeteId = templeteId;
   }

   public String getSendDate() {
      return sendDate;
   }

   public void setSendDate(String sendDate) {
      this.sendDate = sendDate;
   }

   public String getMessageOpen() {
      return messageOpen;
   }

   public void setMessageOpen(String messageOpen) {
      this.messageOpen = messageOpen;
   }

   public String getMessageTitle() {
      return messageTitle;
   }

   public void setMessageTitle(String messageTitle) {
      this.messageTitle = messageTitle;
   }

   public String getMessageReciverId() {
      return messageReciverId;
   }

   public void setMessageReciverId(String messageReciverId) {
      this.messageReciverId = messageReciverId;
   }

   public String getMailOpen() {
      return mailOpen;
   }

   public void setMailOpen(String mailOpen) {
      this.mailOpen = mailOpen;
   }

   public String getMailTitle() {
      return mailTitle;
   }

   public void setMailTitle(String mailTitle) {
      this.mailTitle = mailTitle;
   }

   public String getMailReciverId() {
      return mailReciverId;
   }

   public void setMailReciverId(String mailReciverId) {
      this.mailReciverId = mailReciverId;
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
   
   public String getMailSendTimes() {
	return mailSendTimes;
}

public void setMailSendTimes(String mailSendTimes) {
	this.mailSendTimes = mailSendTimes;
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
	return "MailSettingInfo [id=" + id + ", nextSendTime=" + nextSendTime + ", cron=" + cron + ", mailSendTimes="
			+ mailSendTimes + ", platId=" + platId + ", moduleId=" + moduleId + ", mailCsReciverId=" + mailCsReciverId
			+ ", templeteId=" + templeteId + ", mailFirstParagraph=" + mailFirstParagraph + ", sendDate=" + sendDate
			+ ", messageOpen=" + messageOpen + ", messageTitle=" + messageTitle + ", messageReciverId="
			+ messageReciverId + ", mailOpen=" + mailOpen + ", mailTitle=" + mailTitle + ", mailTitleFrom="
			+ mailTitleFrom + ", mailReciverId=" + mailReciverId + ", createBy=" + createBy + ", createDate="
			+ createDate + ", updateBy=" + updateBy + ", updateDate=" + updateDate + ", remarks=" + remarks
			+ ", delFlag=" + delFlag + ", mailHeaderInfoLists=" + mailHeaderInfoLists + ", messageRecevers="
			+ messageRecevers + ", mailJsRecevers=" + mailJsRecevers + ", messageCsRecevers=" + messageCsRecevers + "]";
}

  
}
