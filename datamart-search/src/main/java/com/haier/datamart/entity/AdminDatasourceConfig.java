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
 * 数据源配置
 * </p>
 *
 * @author dsh123
 * @since 2018-05-28
 */
@TableName("admin_datasource_config")
public class AdminDatasourceConfig extends Model<AdminDatasourceConfig> {

    private static final long serialVersionUID = 1L;
    @TableField(exist=false)
    private List<AdminDatasourceConfig> datasourceConfigList;
    /**
     * 编号
     */
    private String id;
    /**
     * 创建用户
     */
    @TableField("user_id")
    private String userId;
    /**
     * 数据源名称
     */
    private String name;
    /**
     * 数据源英文名称
     */
    private String enname;
    /**
     * 数据源类型
     */
    @TableField("db_type")
    private String dbType;
    /**
     * 数据源驱动
     */
    @TableField("db_diver")
    private String dbDiver;
    /**
     * 数据连接地址
     */
    @TableField("db_url")
    private String dbUrl;
    /**
     * 用户名
     */
    @TableField("db_name")
    private String dbName;
    /**
     * 连接密码
     */
    @TableField("db_password")
    private String dbPassword;
    /**
     * 最大连接数
     */
    @TableField("max_num")
    private Integer maxNum;
    /**
     * 是否可用
     */
    private String useable;
    
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
    @TableField(exist=false)
    private int tableNum;
    @TableField(exist=false)
    private int cloumnNum;
    
    
    @TableField(exist=false)
    private List<AdminDataContent> contents;
    @TableField(exist=false)
    private List<AdminDataContentDetail> details;
    
    @TableField("office_id")
    private String officeId;

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public List<AdminDataContent> getContents() {
		return contents;
	}

	public void setContents(List<AdminDataContent> contents) {
		this.contents = contents;
	}

	public List<AdminDataContentDetail> getDetails() {
		return details;
	}

	public void setDetails(List<AdminDataContentDetail> details) {
		this.details = details;
	}

	public int getTableNum() {
		return tableNum;
	}

	public void setTableNum(int tableNum) {
		this.tableNum = tableNum;
	}

	public int getCloumnNum() {
		return cloumnNum;
	}

	public void setCloumnNum(int cloumnNum) {
		this.cloumnNum = cloumnNum;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getDbDiver() {
        return dbDiver;
    }

    public void setDbDiver(String dbDiver) {
        this.dbDiver = dbDiver;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }

    public String getUseable() {
        return useable;
    }

    public void setUseable(String useable) {
        this.useable = useable;
    }

  

    public List<AdminDatasourceConfig> getDatasourceConfigList() {
		return datasourceConfigList;
	}

	public void setDatasourceConfigList(
			List<AdminDatasourceConfig> datasourceConfigList) {
		this.datasourceConfigList = datasourceConfigList;
	}

	@Override
    protected Serializable pkVal() {
        return this.id;
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

	@Override
	public String toString() {
		return "AdminDatasourceConfig [datasourceConfigList="
				+ datasourceConfigList + ", id=" + id + ", userId=" + userId
				+ ", name=" + name + ", enname=" + enname + ", dbType="
				+ dbType + ", dbDiver=" + dbDiver + ", dbUrl=" + dbUrl
				+ ", dbName=" + dbName + ", dbPassword=" + dbPassword
				+ ", maxNum=" + maxNum + ", useable=" + useable
				+ ", createDate=" + createDate + ", createBy=" + createBy
				+ ", updateBy=" + updateBy + ", updateDate=" + updateDate
				+ ", remarks=" + remarks + ", delFlag=" + delFlag + "]";
	}
	
	

   
}
