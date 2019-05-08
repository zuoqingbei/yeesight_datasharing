package com.haier.datamart.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 业务用户填写的指标业务逻辑订正表 需要审核
 * 
 * @author zuoqb123
 * @date 2018-10-30
 */
@TableName("search_index_magic_revise")
public class SearchIndexMagicRevise extends Model<SearchIndexMagicRevise> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private String id;
	/**
	 * 指标编码
	 */
	@TableField("index_id")
	private String indexId;
	/**
	 * 之前的指标业务逻辑
	 */
	@TableField("pre_magic_info")
	private String preMagicInfo;
	/**
	 * 指标业务逻辑
	 */
	@TableField("magic_info")
	private String magicInfo;
	/**
	 * 指标名称
	 */
	@TableField(exist = false)
	private String indexName;
	/**
	 * 指标编码
	 */
	@TableField(exist = false)
	private String indexCode;
	/**
	 * 审核状态 0-未审核 1-审核通过 2-驳回
	 */
	@TableField("sh_status")
	private String shStatus;
	/**
	 * 审核人
	 */
	@TableField("sh_user")
	private String shUser;
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

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getIndexCode() {
		return indexCode;
	}

	public void setIndexCode(String indexCode) {
		this.indexCode = indexCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIndexId() {
		return indexId;
	}

	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}

	public String getPreMagicInfo() {
		return preMagicInfo;
	}

	public void setPreMagicInfo(String preMagicInfo) {
		this.preMagicInfo = preMagicInfo;
	}

	public String getMagicInfo() {
		return magicInfo;
	}

	public void setMagicInfo(String magicInfo) {
		this.magicInfo = magicInfo;
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
		return "SearchIndexMagicRevise{" + "id=" + id + ", indexId=" + indexId
				+ ", preMagicInfo=" + preMagicInfo + ", magicInfo=" + magicInfo
				+ ", shStatus=" + shStatus + ", shUser=" + shUser
				+ ", createBy=" + createBy + ", createDate=" + createDate
				+ ", updateBy=" + updateBy + ", updateDate=" + updateDate
				+ ", remarks=" + remarks + ", delFlag=" + delFlag + "}";
	}
}
