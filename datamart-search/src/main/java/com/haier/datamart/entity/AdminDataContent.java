package com.haier.datamart.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 数据展示内容配置
 * </p>
 *
 * @author dsh123
 * @since 2018-05-23
 */
@TableName("admin_data_content")
public class AdminDataContent extends BaseModel<AdminDataContent> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;
    /**
     * 数据源配置编码
     */
    @TableField("data_source_id")
    private String dataSourceId;
    /**
     * 名称 比如表明 视图名
     */
    @TableField("table_name")
    private String tableName;

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
    
    private String name;
    

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public AdminDataContent() {
  		super();
  	}

  	public AdminDataContent(String id){
  		super();
  	}

  	public AdminDataContent(String id,String types){
  		this.id=id;
  	}
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

	@Override
	public String toString() {
		return "AdminDataContent [id=" + id + ", dataSourceId=" + dataSourceId
				+ ", tableName=" + tableName + ", createDate=" + createDate
				+ ", createBy=" + createBy + ", updateBy=" + updateBy
				+ ", updateDate=" + updateDate + ", remarks=" + remarks
				+ ", delFlag=" + delFlag + "]";
	}

	
}
