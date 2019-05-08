package com.haier.datamart.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 内容开放策略-内容明细
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-06-15
 */
@TableName("admin_open_strategy_content_detail")
public class AdminOpenStrategyContentDetail extends Model<AdminOpenStrategyContentDetail> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;
    /**
     * 开放策略编码
     */
    @TableField("open_strategy_id")
    private String openStrategyId;
    /**
     * 内容编码
     */
    @TableField("content_detail_id")
    private String contentDetailId;
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


    
    
    /**
     * 数据源
     */
    @TableField("datasource_id")
    private String datasourceId;
    /**
     * 内容编码
     */
    @TableField("content_id")
    private String contentId;
    /**
     * 列名
     */
    @TableField("column_name")
    private String columnName;
    /**
     * 列类型
     */
    @TableField("column_type")
    private String columnType;
    
    

   

	public String getDatasourceId() {
		return datasourceId;
	}

	public void setDatasourceId(String datasourceId) {
		this.datasourceId = datasourceId;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpenStrategyId() {
        return openStrategyId;
    }

    public void setOpenStrategyId(String openStrategyId) {
        this.openStrategyId = openStrategyId;
    }

    public String getContentDetailId() {
        return contentDetailId;
    }

    public void setContentDetailId(String contentDetailId) {
        this.contentDetailId = contentDetailId;
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
        return "AdminOpenStrategyContentDetail{" +
        "id=" + id +
        ", openStrategyId=" + openStrategyId +
        ", contentDetailId=" + contentDetailId +
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
