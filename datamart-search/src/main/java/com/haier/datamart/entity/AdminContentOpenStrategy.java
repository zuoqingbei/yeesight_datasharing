package com.haier.datamart.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 内容开放策略
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-06-15
 */
@TableName("admin_content_open_strategy")
public class AdminContentOpenStrategy extends Model<AdminContentOpenStrategy> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;
    /**
     * 数据源
     */
    @TableField("datasource_id")
    private String datasourceId;
    /**
     * 内容开放策略名称 
     */
    private String name;
    /**
     * 内容开放策略英文名称
     */
    private String enname;
    /**
     * 策略审核状态  源管理用户添加的不需要审核 目标用户需要审核 0-未审核 1-审核通过 2-未通过
     */
    @TableField("review_status")
    private String reviewStatus;
    /**
     * 内容开放策略类型 0-全开放 1-不开放 2-白名单 3-黑名单
     */
    private String type;
    /**
     * 是否可用
     */
    private String useable;
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
    //开放策略详情
    private List<AdminOpenStrategyContentDetail> openStrategyContentDetails;

    
    
    public List<AdminOpenStrategyContentDetail> getOpenStrategyContentDetails() {
		return openStrategyContentDetails;
	}

	public void setOpenStrategyContentDetails(
			List<AdminOpenStrategyContentDetail> openStrategyContentDetails) {
		this.openStrategyContentDetails = openStrategyContentDetails;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDatasourceId() {
        return datasourceId;
    }

    public void setDatasourceId(String datasourceId) {
        this.datasourceId = datasourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnname() {
        return enname;
    }

    public void setEnname(String enname) {
        this.enname = enname;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUseable() {
        return useable;
    }

    public void setUseable(String useable) {
        this.useable = useable;
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
        return "AdminContentOpenStrategy{" +
        "id=" + id +
        ", datasourceId=" + datasourceId +
        ", name=" + name +
        ", enname=" + enname +
        ", reviewStatus=" + reviewStatus +
        ", type=" + type +
        ", useable=" + useable +
        ", createBy=" + createBy +
        ", createDate=" + createDate +
        ", updateBy=" + updateBy +
        ", updateDate=" + updateDate +
        ", remarks=" + remarks +
        ", delFlag=" + delFlag +
        "}";
    }
}
