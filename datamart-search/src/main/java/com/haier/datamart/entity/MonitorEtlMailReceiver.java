package com.haier.datamart.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author doushuihai123
 * @since 2018-07-24
 */
@TableName("monitor_etl_mail_receiver")
public class MonitorEtlMailReceiver extends Model<MonitorEtlMailReceiver> {

    private static final long serialVersionUID = 1L;
    @TableField(exist=false)
    private String loginName;
    
    public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * mail_receiver_manager的id
     */

      @TableField("mail_receiver_manager_id")
    private String mailReceiverManagerId;
    /**
     * 字段ID
     */
    private String id;
    public String getMailReceiverManagerId() {
		return mailReceiverManagerId;
	}

	public void setMailReceiverManagerId(String mailReceiverManagerId) {
		this.mailReceiverManagerId = mailReceiverManagerId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
     * 入口标记
     */
	 @TableField(exist=false)
    private String entrymark;
    /**
     * 收件人组合编码
     */
    @TableField("receive_id")
    private String receiveId;
    /**
     * 收件人组合名称
     */
    @TableField("receive_name")
    private String receiveName;
    /**
     * 收件人类型：1-收件人/2-抄送人
     */
    @TableField("receive_type")
    private String receiveType;
    /**
     * 收件人地址
     */
    @TableField("receive_addr")
    private String receiveAddr;
    @TableField("receive_user")
    private String receiveUser;
    /**
     * 收件人状态：on-发送/off-不发送
     */
    private String state;
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
    
    public String getEntrymark() {
		return entrymark;
	}

	public void setEntrymark(String entrymark) {
		this.entrymark = entrymark;
	}
	@TableField(exist=false)
	private List<MonitorEtlMailReceiver> receiverList;

    public List<MonitorEtlMailReceiver> getReceiverList() {
		return receiverList;
	}

	public void setReceiverList(List<MonitorEtlMailReceiver> receiverList) {
		this.receiverList = receiverList;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(String receiveType) {
        this.receiveType = receiveType;
    }

    public String getReceiveAddr() {
        return receiveAddr;
    }

    public void setReceiveAddr(String receiveAddr) {
        this.receiveAddr = receiveAddr;
    }

    public String getReceiveUser() {
        return receiveUser;
    }

    public void setReceiveUser(String receiveUser) {
        this.receiveUser = receiveUser;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
		return "MonitorEtlMailReceiver [loginName=" + loginName + ", mailReceiverManagerId=" + mailReceiverManagerId
				+ ", id=" + id + ", entrymark=" + entrymark + ", receiveId=" + receiveId + ", receiveName="
				+ receiveName + ", receiveType=" + receiveType + ", receiveAddr=" + receiveAddr + ", receiveUser="
				+ receiveUser + ", state=" + state + ", createBy=" + createBy + ", createDate=" + createDate
				+ ", updateBy=" + updateBy + ", updateDate=" + updateDate + ", remarks=" + remarks + ", delFlag="
				+ delFlag + ", receiverList=" + receiverList + "]";
	}

     
}
