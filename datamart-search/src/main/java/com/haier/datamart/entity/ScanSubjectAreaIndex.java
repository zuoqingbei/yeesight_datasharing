package com.haier.datamart.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.haier.datamart.entity.BaseModel;

import java.io.Serializable;

/**
 * <p>
 * 主题域与指标对照
 * </p>
 *
 * @author dsh123
 * @since 2018-05-30
 */
@TableName("scan_subject_area_index")
public class ScanSubjectAreaIndex extends BaseModel<ScanSubjectAreaIndex> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;
    /**
     * 主题域编号
     */
    @TableField("subject_area_id")
    private String subjectAreaId;
    /**
     * 指标编码
     */
    @TableField("index_id")
    private String indexId;
    private String areaName;
 
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

  

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}


	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubjectAreaId() {
        return subjectAreaId;
    }

    public void setSubjectAreaId(String subjectAreaId) {
        this.subjectAreaId = subjectAreaId;
    }

    public String getIndexId() {
        return indexId;
    }

    public void setIndexId(String indexId) {
        this.indexId = indexId;
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
		return "ScanSubjectAreaIndex [id=" + id + ", subjectAreaId="
				+ subjectAreaId + ", indexId=" + indexId + "]";
	}

    
}
