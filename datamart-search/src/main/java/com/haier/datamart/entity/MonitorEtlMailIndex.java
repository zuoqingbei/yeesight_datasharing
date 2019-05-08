package com.haier.datamart.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 
 * </p>
 *
 * @author doushuihai123
 * @since 2018-07-23
 */
@TableName("monitor_etl_mail_index")
public class MonitorEtlMailIndex extends Model<MonitorEtlMailIndex> {

    private static final long serialVersionUID = 1L;

    /**
     * 字段ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    /**
     * 入口标记
     */
    private String entrymark;
    /**
     * 指标组编码
     */
    @TableField("group_id")
    private String groupId;
    /**
     * 指标组名称
     */
    @TableField("group_name")
    private String groupName;
    /**
     * 根据关联查询得到的用户名
     */
    private String loginName;
    
    public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
     * 指标编码
     */
    @TableField("index_id")
    private String indexId;
    /**
     * 指标名称
     */
    @TableField("index_name")
    private String indexName;
    /**
     * 模板分类
     */
    @TableField("type_id")
    private String typeId;
    @TableField("module_desc")
    private String moduleDesc;
    /**
     * 指标状态
     */
    private String state;
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
    
    public String getEntrymark() {
		return entrymark;
	}

	public void setEntrymark(String entrymark) {
		this.entrymark = entrymark;
	}

	private List<MonitorEtlMailIndex> etlMailIndexList=new ArrayList<MonitorEtlMailIndex>();

    public List<MonitorEtlMailIndex> getEtlMailIndexList() {
		return etlMailIndexList;
	}

	public void setEtlMailIndexList(List<MonitorEtlMailIndex> etlMailIndexList) {
		this.etlMailIndexList = etlMailIndexList;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getIndexId() {
        return indexId;
    }

    public void setIndexId(String indexId) {
        this.indexId = indexId;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getModuleDesc() {
        return moduleDesc;
    }

    public void setModuleDesc(String moduleDesc) {
        this.moduleDesc = moduleDesc;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
        return "MonitorEtlMailIndex{" +
        "id=" + id +
        ", groupId=" + groupId +
        ", groupName=" + groupName +
        ", indexId=" + indexId +
        ", indexName=" + indexName +
        ", typeId=" + typeId +
        ", moduleDesc=" + moduleDesc +
        ", state=" + state +
        ", createBy=" + createBy +
        ", createDate=" + createDate +
        ", updateBy=" + updateBy +
        ", updateDate=" + updateDate +
        ", remarks=" + remarks +
        ", delFlag=" + delFlag +
        "}";
    }
}
