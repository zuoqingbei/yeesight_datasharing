package com.haier.datamart.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-06-05
 */
@TableName("sys_dict")
public class Dict extends Model<Dict> {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	private String id;
	/**
	 * 数据值
	 */
	private String value;
	/**
	 * 标签名
	 */
	private String label;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 描述
	 */
	private String description;
	
	
	/**
	 * 系统名称
	 */
	@TableField("sys_name")
	private String sysName;
	
	/**
	 * 负责人
	 */
	 @TableField("responsible_person")
	private String responsiblePerson;
	
	/**
	 * 访问地址
	 */
	 @TableField("access_address")
	private String accessAddress;
	
	/**
	 * 发版平台
	 */
	 @TableField("terrace")
	private String terrace;
	
	/**
	 * 部署路径
	 */
	 @TableField("deployment_path")
	private String deploymentPath;
	
	/**
	 * 代码地址
	 */
	 @TableField("code_address")
	private String codeAddress;
	/**
	 * 报表数量
	 */
	 @TableField("report_number")
	private String reportNumber;
	
	 /**
	 * 相关调度
	 */ 
	 @TableField("related_operation")
	private String relatedOperation;
	/**
	 * 相关监控
	 */
	 @TableField("related_monitoring")
	private String relatedMonitoring;
	/**
	 * 相关数据表
	 */
	 @TableField("related_datasheet")
	private String relatedDatasheet;
	
	 
	 
	

	public String getReportNumber() {
		return reportNumber;
	}

	public void setReportNumber(String reportNumber) {
		this.reportNumber = reportNumber;
	}

	public String getRelatedOperation() {
		return relatedOperation;
	}

	public void setRelatedOperation(String relatedOperation) {
		this.relatedOperation = relatedOperation;
	}

	public String getRelatedMonitoring() {
		return relatedMonitoring;
	}

	public void setRelatedMonitoring(String relatedMonitoring) {
		this.relatedMonitoring = relatedMonitoring;
	}

	

	public String getRelatedDatasheet() {
		return relatedDatasheet;
	}

	public void setRelatedDatasheet(String relatedDatasheet) {
		this.relatedDatasheet = relatedDatasheet;
	}



	/**
	 * 操作（做了什么操作）
	 */
	private String operation;
	
	
	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(String responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public String getAccessAddress() {
		return accessAddress;
	}

	public void setAccessAddress(String accessAddress) {
		this.accessAddress = accessAddress;
	}

	public String getTerrace() {
		return terrace;
	}

	public void setTerrace(String terrace) {
		this.terrace = terrace;
	}

	public String getDeploymentPath() {
		return deploymentPath;
	}

	public void setDeploymentPath(String deploymentPath) {
		this.deploymentPath = deploymentPath;
	}

	public String getCodeAddress() {
		return codeAddress;
	}

	public void setCodeAddress(String codeAddress) {
		this.codeAddress = codeAddress;
	}



	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * 排序（升序）
	 */
	private BigDecimal sort;
	/**
	 * 父级编号
	 */
	@TableField("parent_id")
	private String parentId;
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

	
	
	
	
	private int indexcount;
	private int reportcount;

	List<SearchIndex> plat=new ArrayList<SearchIndex>();


	public List<SearchIndex> getPlat() {
		return plat;
	}

	public void setPlat(List<SearchIndex> plat) {
		this.plat = plat;
	}

	public int getIndexcount() {
		return indexcount;
	}

	public void setIndexcount(int indexcount) {
		this.indexcount = indexcount;
	}

	public int getReportcount() {
		return reportcount;
	}

	public void setReportcount(int reportcount) {
		this.reportcount = reportcount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getSort() {
		return sort;
	}

	public void setSort(BigDecimal sort) {
		this.sort = sort;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
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
		return "Dict{" + "id=" + id + ", value=" + value + ", label=" + label
				+ ", type=" + type + ", description=" + description + ", sort="
				+ sort + ", parentId=" + parentId + ", createBy=" + createBy
				+ ", createDate=" + createDate + ", updateBy=" + updateBy
				+ ", updateDate=" + updateDate + ", remarks=" + remarks
				+ ", delFlag=" + delFlag + "}";
	}
}
