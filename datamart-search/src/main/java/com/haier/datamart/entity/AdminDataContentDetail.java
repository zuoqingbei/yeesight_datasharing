package com.haier.datamart.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 数据展示内容配置明细
 * </p>
 *
 * @author dsh123
 * @since 2018-05-23
 */
@TableName("admin_data_content_detail")
public class AdminDataContentDetail extends BaseModel<AdminDataContentDetail> {

    private static final long serialVersionUID = 1L;
    private String userName;
    //取数源
    private String name;
    //加工涉及表
    private String tableName;
    
    private String rid;
    
    
    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}



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

	public AdminDataContentDetail() {
  		super();
  	}

  	public AdminDataContentDetail(String id){
  		super();
  	}

  	public AdminDataContentDetail(String id,String types){
  		this.id=id;
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

  

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

	@Override
	public String toString() {
		return "AdminDataContentDetail [name=" + name + ", tableName="
				+ tableName + ", id=" + id + ", datasourceId=" + datasourceId
				+ ", contentId=" + contentId + ", columnName=" + columnName
				+ ", columnType=" + columnType + ", createDate=" + createDate
				+ ", createBy=" + createBy + ", updateBy=" + updateBy
				+ ", updateDate=" + updateDate + ", remarks=" + remarks
				+ ", delFlag=" + delFlag + "]";
	}

	

}
