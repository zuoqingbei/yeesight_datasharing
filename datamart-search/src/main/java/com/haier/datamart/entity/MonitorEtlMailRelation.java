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
 * <p>
 * 
 * </p>
 *
 * @author doushuihai123
 * @since 2018-07-24
 */
@TableName("monitor_etl_mail_relation")
public class MonitorEtlMailRelation extends Model<MonitorEtlMailRelation> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String loginName;
    
    public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@TableId(value = "id", type = IdType.AUTO)
    private String id;
    /**
     * 指标组组
     */
    @TableField("index_group_id")
    private String indexGroupId;
    /**
     * 指标组名称
     */
    @TableField("index_group_name")
    private String indexGroupName;
    /**
     * 发件人地址
     */
    @TableField("send_addr")
    private String sendAddr;
    /**
     * 发件人密码
     */
    @TableField("send_pwd")
    private String sendPwd;
    @TableField("send_user")
    private String sendUser;
    /**
     * 发件人服务器地址
     */
    private String smtp;
    /**
     * 端口
     */
    private String port;
    /**
     * 收件人组
     */
    @TableField("receive_id")
    private String receiveId;
    private String receiveName;
    
    public String getReceiveName() {
		return receiveName;
	}

	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	/**
     * 主题信息
     */
    private String subject;
    /**
     * 正文内容
     */
    private String content;
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
     * 备注信息
     */
    private String remarks;
    /**
     * 更新时间
     */
    @TableField("update_date")
    private Date updateDate;
    @TableField("del_flag")
    private String delFlag;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndexGroupId() {
        return indexGroupId;
    }

    public void setIndexGroupId(String indexGroupId) {
        this.indexGroupId = indexGroupId;
    }

    public String getIndexGroupName() {
        return indexGroupName;
    }

    public void setIndexGroupName(String indexGroupName) {
        this.indexGroupName = indexGroupName;
    }

    public String getSendAddr() {
        return sendAddr;
    }

    public void setSendAddr(String sendAddr) {
        this.sendAddr = sendAddr;
    }

    public String getSendPwd() {
        return sendPwd;
    }

    public void setSendPwd(String sendPwd) {
        this.sendPwd = sendPwd;
    }

    public String getSendUser() {
        return sendUser;
    }

    public void setSendUser(String sendUser) {
        this.sendUser = sendUser;
    }

    public String getSmtp() {
        return smtp;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
        return "MonitorEtlMailRelation{" +
        "id=" + id +
        ", indexGroupId=" + indexGroupId +
        ", indexGroupName=" + indexGroupName +
        ", sendAddr=" + sendAddr +
        ", sendPwd=" + sendPwd +
        ", sendUser=" + sendUser +
        ", smtp=" + smtp +
        ", port=" + port +
        ", receiveId=" + receiveId +
        ", subject=" + subject +
        ", content=" + content +
        ", createBy=" + createBy +
        ", createDate=" + createDate +
        ", updateBy=" + updateBy +
        ", remarks=" + remarks +
        ", updateDate=" + updateDate +
        ", delFlag=" + delFlag +
        "}";
    }
}
