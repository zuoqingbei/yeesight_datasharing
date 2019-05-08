package com.haier.datamart.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 用户-数据权限（策略明细 到表 字段）
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-06-15
 */
@TableName("admin_data_strategy_detail")
public class AdminDataStrategyDetail extends Model<AdminDataStrategyDetail> {

    private static final long serialVersionUID = 1L;

    /**
     * 数据策略编号
     */
    @TableField("data_strategy_id")
    private String dataStrategyId;
    /**
     * 内容明细编码
     */
    @TableField("content_detail_id")
    private String contentDetailId;
    /**
     * 备注
     */
    private String remarks;
    @TableField("del_flag")
    private String delFlag;

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

	public String getDataStrategyId() {
        return dataStrategyId;
    }

    public void setDataStrategyId(String dataStrategyId) {
        this.dataStrategyId = dataStrategyId;
    }

    public String getContentDetailId() {
        return contentDetailId;
    }

    public void setContentDetailId(String contentDetailId) {
        this.contentDetailId = contentDetailId;
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
    public String toString() {
        return "AdminDataStrategyDetail{" +
        "dataStrategyId=" + dataStrategyId +
        ", contentDetailId=" + contentDetailId +
        ", remarks=" + remarks +
        ", delFlag=" + delFlag +
        "}";
    }

	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return null;
	}
}
