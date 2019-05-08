package com.haier.datamart.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 
 * @author zuoqb123
 * @date 2018-11-21
 */
@TableName("minitor_sys_error")
public class MinitorSysError extends Model<MinitorSysError> {

	private static final long serialVersionUID = 1L;

	private String id;
	/**
	 * 系统
	 */
	private String system;
	/**
	 * 平台
	 */
	private String plat;
	/**
	 * 报表
	 */
	@TableField("report_id")
	private String reportId;
	/**
	 * 指标
	 */
	@TableField("index_id")
	private String indexId;
	/**
	 * 错误信息描述
	 */
	private String descs;
	private String remarks;
	@TableField("create_by")
	private String createBy;
	@TableField("create_date")
	private Date createDate;
	@TableField("update_by")
	private String updateBy;
	@TableField("update_date")
	private Date updateDate;
	@TableField("del_flag")
	private String delFlag;
	@TableField(exist=false)
	private List<MinitorSysErrorFile> errorFiles = new ArrayList<MinitorSysErrorFile>();

	public List<MinitorSysErrorFile> getErrorFiles() {
		return errorFiles;
	}

	public void setErrorFiles(List<MinitorSysErrorFile> errorFiles) {
		this.errorFiles = errorFiles;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getPlat() {
		return plat;
	}

	public void setPlat(String plat) {
		this.plat = plat;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getIndexId() {
		return indexId;
	}

	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}

	public String getDescs() {
		return descs;
	}

	public void setDescs(String descs) {
		this.descs = descs;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
		return "MinitorSysError{" + "id=" + id + ", system=" + system
				+ ", plat=" + plat + ", reportId=" + reportId + ", indexId="
				+ indexId + ", descs=" + descs + ", remarks=" + remarks
				+ ", createBy=" + createBy + ", createDate=" + createDate
				+ ", updateBy=" + updateBy + ", updateDate=" + updateDate
				+ ", delFlag=" + delFlag + "}";
	}
}
