package com.haier.datamart.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 指标使用记录表
 * @author zuoqb123
 * @date 2018-10-31
 */
@TableName("search_index_use_history")
public class SearchIndexUseHistory extends Model<SearchIndexUseHistory> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
   private String id;
    /**
     * 使用人姓名
     */
   @TableField("user_name")
   private String userName;
    /**
     * 使用人邮箱
     */
   @TableField("user_email")
   private String userEmail;
    /**
     * 使用人手机号
     */
   @TableField("user_tel")
   private String userTel;
    /**
     * 使用人工号
     */
   @TableField("user_num")
   private String userNum;
    /**
     * 使用人部门
     */
   @TableField("user_dept")
   private String userDept;
    /**
     * 领导姓名
     */
   @TableField("leader_name")
   private String leaderName;
    /**
     * 领导邮箱
     */
   @TableField("leader_email")
   private String leaderEmail;
    /**
     * 领导电话
     */
   @TableField("leader_tel")
   private String leaderTel;
    /**
     * 领导工号
     */
   @TableField("leader_num")
   private String leaderNum;
    /**
     * 领导部门
     */
   @TableField("leader_dept")
   private String leaderDept;
    /**
     * 源数据库
     */
   @TableField("source_db")
   private String sourceDb;
    /**
     * 目标数据库
     */
   @TableField("target_db")
   private String targetDb;
    /**
     * 调度方式
     */
   @TableField("manage_type")
   private String manageType;
    /**
     * 调度sql
     */
   @TableField("manage_sql")
   private String manageSql;
    /**
     * 调度地址
     */
   @TableField("manage_url")
   private String manageUrl;
    /**
     * 调度账号
     */
   @TableField("manage_name")
   private String manageName;
    /**
     * 调度密码
     */
   @TableField("manage_pwd")
   private String managePwd;
    /**
     * 用途
     */
   @TableField("use_reason")
   private String useReason;
    /**
     * 使用系统-报表编码
     */
   @TableField("system_name")
   private String systemName;
    /**
     * 发送邮件状态 1-发送成功
     */
   @TableField("mail_status")
   private String mailStatus;
    /**
     * 审核状态 
     */
   @TableField("sh_status")
   private String shStatus;
    /**
     * 审核人
     */
   @TableField("sh_user")
   private String shUser;
    /**
     * 审核反馈
     */
   @TableField("sh_backs")
   private String shBacks;
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
	@TableField(exist = false)
	private List<SearchIndexUseHistoryIndex> useIndexList = new ArrayList<SearchIndexUseHistoryIndex>();
	@TableField(exist = false)
	private String shUserName;
	@TableField(exist = false)
	private int startNum;
	@TableField(exist = false)
	private int pageSize;
	@TableField(exist = false)
	private String keyWord;
    public int getStartNum() {
		return startNum;
	}

	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

public String getShUserName() {
		return shUserName;
	}

	public void setShUserName(String shUserName) {
		this.shUserName = shUserName;
	}

public List<SearchIndexUseHistoryIndex> getUseIndexList() {
		return useIndexList;
	}

	public void setUseIndexList(List<SearchIndexUseHistoryIndex> useIndexList) {
		this.useIndexList = useIndexList;
	}

public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getUserName() {
      return userName;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }

   public String getUserEmail() {
      return userEmail;
   }

   public void setUserEmail(String userEmail) {
      this.userEmail = userEmail;
   }

   public String getUserTel() {
      return userTel;
   }

   public void setUserTel(String userTel) {
      this.userTel = userTel;
   }

   public String getUserNum() {
      return userNum;
   }

   public void setUserNum(String userNum) {
      this.userNum = userNum;
   }

   public String getUserDept() {
      return userDept;
   }

   public void setUserDept(String userDept) {
      this.userDept = userDept;
   }

   public String getLeaderName() {
      return leaderName;
   }

   public void setLeaderName(String leaderName) {
      this.leaderName = leaderName;
   }

   public String getLeaderEmail() {
      return leaderEmail;
   }

   public void setLeaderEmail(String leaderEmail) {
      this.leaderEmail = leaderEmail;
   }

   public String getLeaderTel() {
      return leaderTel;
   }

   public void setLeaderTel(String leaderTel) {
      this.leaderTel = leaderTel;
   }

   public String getLeaderNum() {
      return leaderNum;
   }

   public void setLeaderNum(String leaderNum) {
      this.leaderNum = leaderNum;
   }

   public String getLeaderDept() {
      return leaderDept;
   }

   public void setLeaderDept(String leaderDept) {
      this.leaderDept = leaderDept;
   }

   public String getSourceDb() {
      return sourceDb;
   }

   public void setSourceDb(String sourceDb) {
      this.sourceDb = sourceDb;
   }

   public String getTargetDb() {
      return targetDb;
   }

   public void setTargetDb(String targetDb) {
      this.targetDb = targetDb;
   }

   public String getManageType() {
      return manageType;
   }

   public void setManageType(String manageType) {
      this.manageType = manageType;
   }

   public String getManageSql() {
      return manageSql;
   }

   public void setManageSql(String manageSql) {
      this.manageSql = manageSql;
   }

   public String getManageUrl() {
      return manageUrl;
   }

   public void setManageUrl(String manageUrl) {
      this.manageUrl = manageUrl;
   }

   public String getManageName() {
      return manageName;
   }

   public void setManageName(String manageName) {
      this.manageName = manageName;
   }

   public String getManagePwd() {
      return managePwd;
   }

   public void setManagePwd(String managePwd) {
      this.managePwd = managePwd;
   }

   public String getUseReason() {
      return useReason;
   }

   public void setUseReason(String useReason) {
      this.useReason = useReason;
   }

   public String getSystemName() {
      return systemName;
   }

   public void setSystemName(String systemName) {
      this.systemName = systemName;
   }

   public String getMailStatus() {
      return mailStatus;
   }

   public void setMailStatus(String mailStatus) {
      this.mailStatus = mailStatus;
   }

   public String getShStatus() {
      return shStatus;
   }

   public void setShStatus(String shStatus) {
      this.shStatus = shStatus;
   }

   public String getShUser() {
      return shUser;
   }

   public void setShUser(String shUser) {
      this.shUser = shUser;
   }

   public String getShBacks() {
      return shBacks;
   }

   public void setShBacks(String shBacks) {
      this.shBacks = shBacks;
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
      return "SearchIndexUseHistory{" +
         "id=" + id +
         ", userName=" + userName +
         ", userEmail=" + userEmail +
         ", userTel=" + userTel +
         ", userNum=" + userNum +
         ", userDept=" + userDept +
         ", leaderName=" + leaderName +
         ", leaderEmail=" + leaderEmail +
         ", leaderTel=" + leaderTel +
         ", leaderNum=" + leaderNum +
         ", leaderDept=" + leaderDept +
         ", sourceDb=" + sourceDb +
         ", targetDb=" + targetDb +
         ", manageType=" + manageType +
         ", manageSql=" + manageSql +
         ", manageUrl=" + manageUrl +
         ", manageName=" + manageName +
         ", managePwd=" + managePwd +
         ", useReason=" + useReason +
         ", systemName=" + systemName +
         ", mailStatus=" + mailStatus +
         ", shStatus=" + shStatus +
         ", shUser=" + shUser +
         ", shBacks=" + shBacks +
         ", createBy=" + createBy +
         ", createDate=" + createDate +
         ", updateBy=" + updateBy +
         ", updateDate=" + updateDate +
         ", remarks=" + remarks +
         ", delFlag=" + delFlag +
         "}";
   }
}
