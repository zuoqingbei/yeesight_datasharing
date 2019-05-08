package com.haier.datamart.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 指标与列行对应表
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-06-07
 */
@TableName("scan_index_table_relative")
public class ScanIndexTableRelative extends Model<ScanIndexTableRelative> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;
    /**
     * 指标编码
     */
    @TableField("index_id")
    private String indexId;
    /**
     * 表名称编码
     */
    @TableField("content_id")
    private String contentId;
    /**
     * 列编码
     */
    @TableField("content_deatil_id")
    private String contentDeatilId;
    /**
     * 校验规则 
     */
    @TableField("check_rule")
    private String checkRule;
    /**
     * 表所在数据源 展示关系不需要做控制 看明细如果没有相应权限需要申请
     */
    @TableField("data_source_id")
    private String dataSourceId;
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

    private String tableName;
    private String dbName;
    
    public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
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

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getContentDeatilId() {
        return contentDeatilId;
    }

    public void setContentDeatilId(String contentDeatilId) {
        this.contentDeatilId = contentDeatilId;
    }

    public String getCheckRule() {
        return checkRule;
    }

    public void setCheckRule(String checkRule) {
        this.checkRule = checkRule;
    }

    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
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
		return "ScanIndexTableRelative [id=" + id + ", indexId=" + indexId
				+ ", contentId=" + contentId + ", contentDeatilId="
				+ contentDeatilId + ", checkRule=" + checkRule
				+ ", dataSourceId=" + dataSourceId + ", createBy=" + createBy
				+ ", createDate=" + createDate + ", updateBy=" + updateBy
				+ ", updateDate=" + updateDate + ", remarks=" + remarks
				+ ", delFlag=" + delFlag + ", tableName=" + tableName
				+ ", dbName=" + dbName + "]";
	}

   
}
