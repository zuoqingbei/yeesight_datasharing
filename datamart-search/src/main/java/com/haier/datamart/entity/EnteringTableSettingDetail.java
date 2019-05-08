package com.haier.datamart.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 补录模块-补录数据表配置字段明细信息
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-07-02
 */
@TableName("entering_table_setting_detail")
public class EnteringTableSettingDetail extends
		Model<EnteringTableSettingDetail> {

	private static final long serialVersionUID = 1L;
	/**
	 * 新增的属性(指标id)
	 */
	private String indexId;
	/**
	 * 新增的属性(指标名)
	 */
	@TableField(exist=false)
	private String indexName;
	private String isIndex;// 是否是索引,1为是
	private String functions;// 交给前端执行的js代码
	/**
	 * 是否锁死 0-锁死 1-不锁死
	 */
	@TableField("is_lock")
	private String isLock;
	/**
	 * 是否导出到模板 0-导出 1-不导出
	 */
	@TableField("is_export")
	private String isExport;
	/**
	 * 默认值
	 */
	@TableField("default_value")
	private String defaultValue;
	/**
	 * 精度
	 */
	@TableField("col_accuracy")
	private Integer colAccuracy;
	/**
	 * 当前列样式
	 */
	@TableField("column_style")
	private String columnStyle;
	@TableField(exist=false)
	private List<EnteringTableSettingDetailData> detailData=new ArrayList<EnteringTableSettingDetailData>();

	public List<EnteringTableSettingDetailData> getDetailData() {
		return detailData;
	}

	public void setDetailData(List<EnteringTableSettingDetailData> detailData) {
		this.detailData = detailData;
	}

	public String getIsLock() {
		return isLock;
	}

	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}

	public String getColumnStyle() {
		return columnStyle;
	}

	public void setColumnStyle(String columnStyle) {
		this.columnStyle = columnStyle;
	}

	public String getIsExport() {
		return isExport;
	}

	public void setIsExport(String isExport) {
		this.isExport = isExport;
	}

	public String getIndexId() {
		return indexId;
	}

	public String getIsIndex() {
		return isIndex;
	}

	public void setIsIndex(String isIndex) {
		this.isIndex = isIndex;
	}

	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Integer getColAccuracy() {
		return colAccuracy;
	}

	public void setColAccuracy(Integer colAccuracy) {
		this.colAccuracy = colAccuracy;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	/**
	 * 编号
	 */
	private String id;
	/**
	 * 配置编码
	 */
	@TableField("entering_setting_id")
	private String enteringSettingId;
	/**
	 * 列名称
	 */
	@TableField("col_name")
	private String colName;
	/**
	 * 列类型
	 */
	@TableField("col_type")
	private String colType;
	/**
	 * 列长度
	 */
	@TableField("col_length")
	private String colLength;
	/**
	 * 列小数点
	 */
	@TableField("col_xsd")
	private String colXsd;
	/**
	 * 是否是主键 0-不是 1-是
	 */
	@TableField("col_pk")
	private String colPk;
	/**
	 * 字段描述 备注
	 */
	private String comments;
	@TableField("order_no")
	private Integer orderNo;
	/**
	 * Excel模板中显示名称 唯一的
	 */
	@TableField("excel_col_name")
	private String excelColName;
	/**
	 * 创建时间
	 */
	@TableField("create_date")
	private Date createDate;
	/**
	 * 创建者
	 */
	@TableField("create_by")
	private String createBy;
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
	 * 
	 * @return
	 */
	// @TableField("excel_gs")
	private String excelGs;

	@TableField("data_type")
	private String dataType;
	@TableField("data_space")
	private String dataSpace;
	@TableField("data_key")
	private String dataKey;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	public String getId() {
		return id;
	}

	public String getDataKey() {
		return dataKey;
	}

	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDataSpace() {
		return dataSpace;
	}

	public void setDataSpace(String dataSpace) {
		this.dataSpace = dataSpace;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEnteringSettingId() {
		return enteringSettingId;
	}

	public void setEnteringSettingId(String enteringSettingId) {
		this.enteringSettingId = enteringSettingId;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public String getColType() {
		return colType;
	}

	public void setColType(String colType) {
		this.colType = colType;
	}

	public String getColLength() {
		return colLength;
	}

	public void setColLength(String colLength) {
		this.colLength = colLength;
	}

	public String getColXsd() {
		return colXsd;
	}

	public void setColXsd(String colXsd) {
		this.colXsd = colXsd;
	}

	public String getColPk() {
		return colPk;
	}

	public void setColPk(String colPk) {
		this.colPk = colPk;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getExcelColName() {
		return excelColName;
	}

	public void setExcelColName(String excelColName) {
		this.excelColName = excelColName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
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

	public String getExcelGs() {
		return excelGs;
	}

	public void setExcelGs(String excelGs) {
		this.excelGs = excelGs;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getFunctions() {
		return functions;
	}

	public void setFunctions(String functions) {
		this.functions = functions;
	}

	@Override
	public String toString() {
		return "EnteringTableSettingDetail [indexId=" + indexId
				+ ", indexName=" + indexName + ", functions=" + functions
				+ ", id=" + id + ", enteringSettingId=" + enteringSettingId
				+ ", colName=" + colName + ", colType=" + colType
				+ ", colLength=" + colLength + ", colXsd=" + colXsd
				+ ", colPk=" + colPk + ", comments=" + comments + ", orderNo="
				+ orderNo + ", excelColName=" + excelColName + ", createDate="
				+ createDate + ", createBy=" + createBy + ", updateBy="
				+ updateBy + ", updateDate=" + updateDate + ", remarks="
				+ remarks + ", delFlag=" + delFlag + ", excelGs=" + excelGs
				+ "]";
	}

}
