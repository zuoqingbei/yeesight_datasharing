package com.haier.datamart.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;


/**
 * <p>
 * 
 * </p>
 *
 * @author leizhiguo123
 * @since 2018-08-03
 */
@TableName("data_interface_exc")
public class DataInterfaceExc extends Model<DataInterfaceExc> {

    private static final long serialVersionUID = 1L;
    @TableField(exist=false)
	private SysProduct sysProduct;
    @TableField(exist=false)
  	private String  dbName;
    
    public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public SysProduct getSysProduct() {
		return sysProduct;
	}

	public void setSysProduct(SysProduct sysProduct) {
		this.sysProduct = sysProduct;
	}
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	 @TableId(value="id", type= IdType.AUTO)
	   private Long id;
	 @TableField("del_flag")
	   private String delFlag;
    /**
     * 查询指标标识
     */
    @TableField("data_type")
    private String dataType;
    /**
     * 命名空间
     */
    @TableField("data_space")
    private String dataSpace;
    /**
     * 需要执行的sql语句，需要传参的位置使用#{参数名}，动态日期类型date_dt_kpi固定参数名称。
     */
    @TableField("data_sql")
    private String dataSql;
    /**
     * sql需要传入的参数，如果需要动态日期date_dt_kpi无需配置
     */
    @TableField("param_id")
    private Long paramId;
    /**
     * 需要历史记录放入缓存的开始时间,根据更新类型（cache_type）
     */
    @TableField("begin_date")
    private String beginDate;
    /**
     * 需要查询的时间格式，例如：yyyyMMdd
     */
    @TableField("date_format")
    private String dateFormat;
    /**
     * 是否需要定时刷新的标识,0不刷新，1刷新
     */
    @TableField("fresh_flag")
    private String freshFlag;
    /**
     * 数据缓存天数，根据更新类型（cache_type）判断
     */
    @TableField("update_days")
    private Integer updateDays;
    /**
     * 定任务执行时间偏移量，默认执行时间减一天。注：该设置只对定时任务有效。
     */
    @TableField("timer_offset")
    private Integer timerOffset;
    /**
     * Cron表达式是一个字符串配置定时任务执行时间
     */
    @TableField("exc_time")
    private String excTime;
    /**
     * 放入缓存的方式，按天数存放（0），或者按开始时间存放（1）
     */
    @TableField("cache_type")
    private Integer cacheType;
    /**
     * 横竖数据格式转换，默认纵向排列（V：垂直排列，H水平排列）
     */
    @TableField("transform_data")
    private String transformData;
    /**
     * 创建主体
     */
    @TableField("create_by")
    private String createBy;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 最后更新主体
     */
    @TableField("last_update_by")
    private String lastUpdateBy;
    /**
     * 最后更新时间
     */
    @TableField("last_update_time")
    private Date lastUpdateTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 对应datasourceid 可以为空
     */
   private String dataSourceId;

    public String getDataSourceId() {
		return dataSourceId;
	}

	public void setDataSourceId(String dataSourceId) {
		this.dataSourceId = dataSourceId;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDataSql() {
        return dataSql;
    }

    public void setDataSql(String dataSql) {
        this.dataSql = dataSql;
    }

    public Long getParamId() {
        return paramId;
    }

    public void setParamId(Long paramId) {
        this.paramId = paramId;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getFreshFlag() {
        return freshFlag;
    }

    public void setFreshFlag(String freshFlag) {
        this.freshFlag = freshFlag;
    }

    public Integer getUpdateDays() {
        return updateDays;
    }

    public void setUpdateDays(Integer updateDays) {
        this.updateDays = updateDays;
    }

    public Integer getTimerOffset() {
        return timerOffset;
    }

    public void setTimerOffset(Integer timerOffset) {
        this.timerOffset = timerOffset;
    }

    public String getExcTime() {
        return excTime;
    }

    public void setExcTime(String excTime) {
        this.excTime = excTime;
    }

    public Integer getCacheType() {
        return cacheType;
    }

    public void setCacheType(Integer cacheType) {
        this.cacheType = cacheType;
    }

    public String getTransformData() {
        return transformData;
    }

    public void setTransformData(String transformData) {
        this.transformData = transformData;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "DataInterfaceExc{" +
        "id=" + id +
        ", dataType=" + dataType +
        ", dataSpace=" + dataSpace +
        ", dataSql=" + dataSql +
        ", paramId=" + paramId +
        ", beginDate=" + beginDate +
        ", dateFormat=" + dateFormat +
        ", freshFlag=" + freshFlag +
        ", updateDays=" + updateDays +
        ", timerOffset=" + timerOffset +
        ", excTime=" + excTime +
        ", cacheType=" + cacheType +
        ", transformData=" + transformData +
        ", createBy=" + createBy +
        ", createTime=" + createTime +
        ", lastUpdateBy=" + lastUpdateBy +
        ", lastUpdateTime=" + lastUpdateTime +
        ", remark=" + remark +
        "}";
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((beginDate == null) ? 0 : beginDate.hashCode());
		result = prime * result + ((cacheType == null) ? 0 : cacheType.hashCode());
		result = prime * result + ((createBy == null) ? 0 : createBy.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((dataSpace == null) ? 0 : dataSpace.hashCode());
		result = prime * result + ((dataSql == null) ? 0 : dataSql.hashCode());
		result = prime * result + ((dataType == null) ? 0 : dataType.hashCode());
		result = prime * result + ((dateFormat == null) ? 0 : dateFormat.hashCode());
		result = prime * result + ((excTime == null) ? 0 : excTime.hashCode());
		result = prime * result + ((freshFlag == null) ? 0 : freshFlag.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastUpdateBy == null) ? 0 : lastUpdateBy.hashCode());
		result = prime * result + ((lastUpdateTime == null) ? 0 : lastUpdateTime.hashCode());
		result = prime * result + ((paramId == null) ? 0 : paramId.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((sysProduct == null) ? 0 : sysProduct.hashCode());
		result = prime * result + ((timerOffset == null) ? 0 : timerOffset.hashCode());
		result = prime * result + ((transformData == null) ? 0 : transformData.hashCode());
		result = prime * result + ((updateDays == null) ? 0 : updateDays.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataInterfaceExc other = (DataInterfaceExc) obj;
		if (beginDate == null) {
			if (other.beginDate != null)
				return false;
		} else if (!beginDate.equals(other.beginDate))
			return false;
		if (cacheType == null) {
			if (other.cacheType != null)
				return false;
		} else if (!cacheType.equals(other.cacheType))
			return false;
		if (createBy == null) {
			if (other.createBy != null)
				return false;
		} else if (!createBy.equals(other.createBy))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (dataSpace == null) {
			if (other.dataSpace != null)
				return false;
		} else if (!dataSpace.equals(other.dataSpace))
			return false;
		if (dataSql == null) {
			if (other.dataSql != null)
				return false;
		} else if (!dataSql.equals(other.dataSql))
			return false;
		if (dataType == null) {
			if (other.dataType != null)
				return false;
		} else if (!dataType.equals(other.dataType))
			return false;
		if (dateFormat == null) {
			if (other.dateFormat != null)
				return false;
		} else if (!dateFormat.equals(other.dateFormat))
			return false;
		if (excTime == null) {
			if (other.excTime != null)
				return false;
		} else if (!excTime.equals(other.excTime))
			return false;
		if (freshFlag == null) {
			if (other.freshFlag != null)
				return false;
		} else if (!freshFlag.equals(other.freshFlag))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastUpdateBy == null) {
			if (other.lastUpdateBy != null)
				return false;
		} else if (!lastUpdateBy.equals(other.lastUpdateBy))
			return false;
		if (lastUpdateTime == null) {
			if (other.lastUpdateTime != null)
				return false;
		} else if (!lastUpdateTime.equals(other.lastUpdateTime))
			return false;
		if (paramId == null) {
			if (other.paramId != null)
				return false;
		} else if (!paramId.equals(other.paramId))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (sysProduct == null) {
			if (other.sysProduct != null)
				return false;
		} else if (!sysProduct.equals(other.sysProduct))
			return false;
		if (timerOffset == null) {
			if (other.timerOffset != null)
				return false;
		} else if (!timerOffset.equals(other.timerOffset))
			return false;
		if (transformData == null) {
			if (other.transformData != null)
				return false;
		} else if (!transformData.equals(other.transformData))
			return false;
		if (updateDays == null) {
			if (other.updateDays != null)
				return false;
		} else if (!updateDays.equals(other.updateDays))
			return false;
		return true;
	}
    
}
