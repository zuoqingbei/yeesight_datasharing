package com.haier.datamart.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 
 * @author ZhangJiang123
 * @date 2018-12-20
 */
@TableName("monitor_etl_email_content_query_info")
public class MonitorEtlEmailContentQueryInfo extends Model<MonitorEtlEmailContentQueryInfo> {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	private String id;
	@TableField("template_id")
	private String templateId;
	@TableField("datasource_id")
	private String datasourceId;
	@TableField("style")
	private String style;

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	@TableField("single_table_header_style")
	private String singleTableHeaderStyle;
	@TableField("single_table_header_str")
	private String singleTableHeaderStr;
	
	/**
	 * sql_执行序号
	 */
	@TableField("sql_no")
	private Integer sqlNo;
	
	/**
	 * 命名空间
	 */
	@TableField("data_space")
	private String dataSpace;
	
	public String getDataSpace() {
		return dataSpace;
	}

	public void setDataSpace(String dataSpace) {
		this.dataSpace = dataSpace;
	}

	/**
	 * sql字符串
	 */
	@TableField("sql_string")
	private String sqlString;
	/**
	 * 数据类型标识
	 */
	@TableField("data_type_marking")
	private String dataTypeMarking;
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
	
	/**
	 * 临时字段
	 */
	@TableField(exist=false)
	private String[] chineseName;
	
	public String[] getChineseName() {
		return chineseName;
	}

	public void setChineseName(String[] chineseName) {
		this.chineseName = chineseName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	 

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDatasourceId() {
		return datasourceId;
	}

	public void setDatasourceId(String datasourceId) {
		this.datasourceId = datasourceId;
	}

	public Integer getSqlNo() {
		return sqlNo;
	}

	public void setSqlNo(Integer sqlNo) {
		this.sqlNo = sqlNo;
	}

	public String getSqlString() {
		return sqlString;
	}

	public void setSqlString(String sqlString) {
		this.sqlString = sqlString;
	}

	public String getDataTypeMarking() {
		return dataTypeMarking;
	}

	public void setDataTypeMarking(String dataTypeMarking) {
		this.dataTypeMarking = dataTypeMarking;
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

	public String getSingleTableHeaderStyle() {
		return singleTableHeaderStyle;
	}

	public void setSingleTableHeaderStyle(String singleTableHeaderStyle) {
		this.singleTableHeaderStyle = singleTableHeaderStyle;
	}

	public String getSingleTableHeaderStr() {
		return singleTableHeaderStr;
	}

	public void setSingleTableHeaderStr(String singleTableHeaderStr) {
		this.singleTableHeaderStr = singleTableHeaderStr;
	}

	@Override
	public String toString() {
		return "MonitorEtlEmailContentQueryInfo [id=" + id + ", templateId=" + templateId + ", datasourceId="
				+ datasourceId + ", singleTableHeaderStyle=" + singleTableHeaderStyle + ", singleTableHeaderStr="
				+ singleTableHeaderStr + ", sqlNo=" + sqlNo + ", dataSpace=" + dataSpace + ", sqlString=" + sqlString
				+ ", dataTypeMarking=" + dataTypeMarking + ", createBy=" + createBy + ", createDate=" + createDate
				+ ", updateBy=" + updateBy + ", updateDate=" + updateDate + ", remarks=" + remarks + ", delFlag="
				+ delFlag + ", chineseName=" + Arrays.toString(chineseName) + "]";
	}

	 
}
