package com.haier.datamart.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 项目
 * </p>
 *
 * @author leizhiguo123
 * @since 2018-08-03
 */
@TableName("sys_product")
public class SysProduct extends Model<SysProduct> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;
    /**
     * 项目名称
     */
    private String name;
    /**
     * 英文名称
     */
    private String enname;
    /**
     * 项目管理员
     */
    private String manager;
    /**
     * 是否可用
     */
    private String useable;
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
    @TableField(exist=false)
    private List<SysProductGroup> sysProductGroup=new ArrayList<SysProductGroup>();
    @TableField(exist=false)
    private List<SysProductExc> sysProductExc=new ArrayList<SysProductExc>();


    public List<SysProductGroup> getSysProductGroup() {
		return sysProductGroup;
	}

	public void setSysProductGroup(List<SysProductGroup> sysProductGroup) {
		this.sysProductGroup = sysProductGroup;
	}


	public List<SysProductExc> getSysProductExc() {
		return sysProductExc;
	}

	public void setSysProductExc(List<SysProductExc> sysProductExc) {
		this.sysProductExc = sysProductExc;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getUseable() {
        return useable;
    }

    public void setUseable(String useable) {
        this.useable = useable;
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
        return "SysProduct{" +
        "id=" + id +
        ", name=" + name +
        ", enname=" + enname +
        ", manager=" + manager +
        ", useable=" + useable +
        ", createBy=" + createBy +
        ", createDate=" + createDate +
        ", updateBy=" + updateBy +
        ", updateDate=" + updateDate +
        ", remarks=" + remarks +
        ", delFlag=" + delFlag +
        "}";
    }
}
