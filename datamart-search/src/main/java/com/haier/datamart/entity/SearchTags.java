package com.haier.datamart.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 标签中间表
 * </p>
 *
 * @author dsh123
 * @since 2018-05-29
 */
@TableName("search_tags")
public class SearchTags extends BaseModel<SearchTags> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    private String label;
    private String value;
    /**
     * api  报表 指标类型
     */
    @TableField("data_type")
    private String dataType;
    /**
     * 数据id
     */
    @TableField("data_id")
    private String dataId;
    /**
     * 维度id-字典数据
     */
    @TableField("dic_id")
    private String dicId;
    
    public SearchTags() {
		super();
	}

	public SearchTags(String id){
		super();
	}
    public SearchTags(String dataId,String dataType){
    	this.dataId=dataId;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
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

   
}
