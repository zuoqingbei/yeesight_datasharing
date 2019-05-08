package com.haier.datamart.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 指标维度中间表
 * </p>
 *
 * @author dsh123
 * @since 2018-05-23
 */
@TableName("search_index_dimension")
public class SearchIndexDimension extends BaseModel<SearchIndexDimension> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 报表id
     */
    /**
     * sys_dict表中的两个字段
     */
    private String label;
    private String value;
    private String description;
    
    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@TableField("report_id")
    private String reportId;
    /**
     * 维度id-字典数据
     */
    @TableField("dic_id")
    private String dicId;
    

    public SearchIndexDimension() {
  		super();
  	}

  	public SearchIndexDimension(String id){
  		super();
  	}

  	public SearchIndexDimension(String reportId,String types){
  		this.reportId=reportId;
  	}
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getDicId() {
        return dicId;
    }

    public void setDicId(String dicId) {
        this.dicId = dicId;
    }

   

    public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
    protected Serializable pkVal() {
        return this.id;
    }

	@Override
	public String toString() {
		return "SearchIndexDimension [id=" + id + ", reportId=" + reportId
				+ ", dicId=" + dicId + "]";
	}

   
}
