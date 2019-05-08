package com.haier.datamart.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * <p>
 * 补录模块-补录数据表配置基础信息
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-07-02
 */
@TableName("entering_table_setting_info")
public class EnteringTableSettingInfo extends Model<EnteringTableSettingInfo> {

    private static final long serialVersionUID = 1L;
    
    private String productId;//所属项目id
    private String bakTableName;//备份表名
    private String bakDatasourceConfigId;//备份表数据源
    private String enteringType;//补录类型补录类型  0-老版补录页面配置  1-通过excel模板配置，支持多行表头
    private String headerJson;//excel模板头部json数据
    private String excelModelName;//entering_type 为1时模板名称

	/**
     * 编号
     */
    private String id;
    /**
     * 数据表名称
     */
    private String name;
    /**
     * 数据表描述
     */
    private String descs;
    /**
     * 允许操作数据开始时间
     */
    @TableField("valid_begin_time")
    private Date validBeginTime;
    private String validBeginTimeStr;
    /**
     * 允许操作数据结束时间
     */
    @TableField("valid_end_time")
    private Date validEndTime;
    private String validEndTimeStr;
    /**
     * 当出现错误信息的时候 是否允许正确的继续导入 0-允许 1-不允许  默认允许
     */
    @TableField("error_continue")
    private String errorContinue;
    /**
     * 数据源id
     */
    @TableField("datasource_config_id")
    private String datasourceConfigId;
    /**
     * 动态获取时间开始cron
     */
    @TableField("start_cron")
    private String startCron;
    /**
     * 动态获取时间结束cron
     */
    @TableField("end_cron")
    private String endCron;
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
    @TableField("status")
    private String status;
    private int pageNum;
    
    private int size;
    private List<EnteringTableSettingDetail> etDetail=new ArrayList<EnteringTableSettingDetail>();
    
    
    
    public String getErrorContinue() {
		return errorContinue;
	}




	public void setErrorContinue(String errorContinue) {
		this.errorContinue = errorContinue;
	}




	public String getProductId() {
		return productId;
	}




	public void setProductId(String productId) {
		this.productId = productId;
	}



    
    
	public String getStatus() {
		return status;
	}




	public void setStatus(String status) {
		this.status = status;
	}




	public List<EnteringTableSettingDetail> getEtDetail() {
		return etDetail;
	}




	public void setEtDetail(List<EnteringTableSettingDetail> etDetail) {
		this.etDetail = etDetail;
	}




	public EnteringTableSettingInfo(String id, String indexId, String name, String descs, Date validBeginTime,
			Date validEndTime, Date createDate, String createBy, String updateBy, Date updateDate, String remarks,
			String delFlag) {
		super();
		this.id = id;
		this.name = name;
		this.descs = descs;
		this.validBeginTime = validBeginTime;
		this.validEndTime = validEndTime;
		this.createDate = createDate;
		this.createBy = createBy;
		this.updateBy = updateBy;
		this.updateDate = updateDate;
		this.remarks = remarks;
		this.delFlag = delFlag;
	}

	


	public EnteringTableSettingInfo() {
		super();
	}




	public String getId() {
		return id;
	}




	public void setId(String id) {
		this.id = id;
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public String getDescs() {
		return descs;
	}




	public void setDescs(String descs) {
		this.descs = descs;
	}




	public Date getValidBeginTime() {
		return validBeginTime;
	}




	public void setValidBeginTime(Date validBeginTime) {
		this.validBeginTime = validBeginTime;
	}




	public Date getValidEndTime() {
		return validEndTime;
	}




	public void setValidEndTime(Date validEndTime) {
		this.validEndTime = validEndTime;
	}




	public String getValidBeginTimeStr() {
		return validBeginTimeStr;
	}




	public void setValidBeginTimeStr(String validBeginTimeStr) {
		this.validBeginTimeStr = validBeginTimeStr;
	}




	public String getValidEndTimeStr() {
		return validEndTimeStr;
	}




	public void setValidEndTimeStr(String validEndTimeStr) {
		this.validEndTimeStr = validEndTimeStr;
	}




	public String getDatasourceConfigId() {
		return datasourceConfigId;
	}




	public void setDatasourceConfigId(String datasourceConfigId) {
		this.datasourceConfigId = datasourceConfigId;
	}




	public String getStartCron() {
		return startCron;
	}




	public void setStartCron(String startCron) {
		this.startCron = startCron;
	}




	public String getEndCron() {
		return endCron;
	}




	public void setEndCron(String endCron) {
		this.endCron = endCron;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	

	public int getPageNum() {
		return pageNum;
	}




	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}




	public int getSize() {
		return size;
	}




	public void setSize(int size) {
		this.size = size;
	}


	








	public String getBakTableName() {
		return bakTableName;
	}




	public void setBakTableName(String bakTableName) {
		this.bakTableName = bakTableName;
	}




	public String getBakDatasourceConfigId() {
		return bakDatasourceConfigId;
	}




	public void setBakDatasourceConfigId(String bakDatasourceConfigId) {
		this.bakDatasourceConfigId = bakDatasourceConfigId;
	}




	public String getEnteringType() {
		return enteringType;
	}




	public void setEnteringType(String enteringType) {
		this.enteringType = enteringType;
	}




	public String getHeaderJson() {
		return headerJson;
	}




	public void setHeaderJson(String headerJson) {
		this.headerJson = headerJson;
	}




	public String getExcelModelName() {
		return excelModelName;
	}




	public void setExcelModelName(String excelModelName) {
		this.excelModelName = excelModelName;
	}




	@Override
	public String toString() {
		return "EnteringTableSettingInfo [productId=" + productId + ", bakTableName=" + bakTableName
				+ ", bakDatasourceConfigId=" + bakDatasourceConfigId + ", enteringType=" + enteringType
				+ ", headerJson=" + headerJson + ", excelModelName=" + excelModelName + ", id=" + id + ", name=" + name
				+ ", descs=" + descs + ", validBeginTime=" + validBeginTime + ", validBeginTimeStr=" + validBeginTimeStr
				+ ", validEndTime=" + validEndTime + ", validEndTimeStr=" + validEndTimeStr + ", datasourceConfigId="
				+ datasourceConfigId + ", startCron=" + startCron + ", endCron=" + endCron + ", createDate="
				+ createDate + ", createBy=" + createBy + ", updateBy=" + updateBy + ", updateDate=" + updateDate
				+ ", remarks=" + remarks + ", delFlag=" + delFlag + ", status=" + status + ", pageNum=" + pageNum
				+ ", size=" + size + ", etDetail=" + etDetail + "]";
	}




	@Override
	protected Serializable pkVal() {
		return this.id;
	}




	

	

	
    
    

    
}
