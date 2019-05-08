package com.haier.datamart.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * 机构表
 * @author zuoqb123
 * @date 2018-12-05
 */
@TableName("sys_office")
public class SysOffice extends Model<SysOffice> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
   private String id;
    /**
     * 父级编号
     */
   @TableField("parent_id")
   private String parentId;
    /**
     * 所有父级编号
     */
   @TableField("parent_ids")
   private String parentIds;
    /**
     * 名称
     */
   private String name;
    /**
     * 排序
     */
   private BigDecimal sort;
    /**
     * 归属区域
     */
   @TableField("area_id")
   private String areaId;
    /**
     * 区域编码
     */
   private String code;
    /**
     * 机构类型
     */
   private String type;
    /**
     * 机构等级
     */
   private String grade;
    /**
     * 联系地址
     */
   private String address;
    /**
     * 邮政编码
     */
   @TableField("zip_code")
   private String zipCode;
    /**
     * 负责人
     */
   private String master;
    /**
     * 电话
     */
   private String phone;
    /**
     * 传真
     */
   private String fax;
    /**
     * 邮箱
     */
   private String email;
    /**
     * 是否启用
     */
   @TableField("USEABLE")
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
   private List<SysOffice> children=new ArrayList<SysOffice>();
   @TableField(exist=false)
   private List<AdminDatasourceConfig> datasourceConfig=new ArrayList<AdminDatasourceConfig>();
   public List<SysOffice> getChildren() {
		return children;
	}
	
	public void setChildren(List<SysOffice> children) {
		this.children = children;
	}

public List<AdminDatasourceConfig> getDatasourceConfig() {
		return datasourceConfig;
	}

	public void setDatasourceConfig(List<AdminDatasourceConfig> datasourceConfig) {
		this.datasourceConfig = datasourceConfig;
	}

public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getParentId() {
      return parentId;
   }

   public void setParentId(String parentId) {
      this.parentId = parentId;
   }

   public String getParentIds() {
      return parentIds;
   }

   public void setParentIds(String parentIds) {
      this.parentIds = parentIds;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public BigDecimal getSort() {
      return sort;
   }

   public void setSort(BigDecimal sort) {
      this.sort = sort;
   }

   public String getAreaId() {
      return areaId;
   }

   public void setAreaId(String areaId) {
      this.areaId = areaId;
   }

   public String getCode() {
      return code;
   }

   public void setCode(String code) {
      this.code = code;
   }

   public String getType() {
      return type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public String getGrade() {
      return grade;
   }

   public void setGrade(String grade) {
      this.grade = grade;
   }

   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   public String getZipCode() {
      return zipCode;
   }

   public void setZipCode(String zipCode) {
      this.zipCode = zipCode;
   }

   public String getMaster() {
      return master;
   }

   public void setMaster(String master) {
      this.master = master;
   }

   public String getPhone() {
      return phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   public String getFax() {
      return fax;
   }

   public void setFax(String fax) {
      this.fax = fax;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
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
      return "SysOffice{" +
         "id=" + id +
         ", parentId=" + parentId +
         ", parentIds=" + parentIds +
         ", name=" + name +
         ", sort=" + sort +
         ", areaId=" + areaId +
         ", code=" + code +
         ", type=" + type +
         ", grade=" + grade +
         ", address=" + address +
         ", zipCode=" + zipCode +
         ", master=" + master +
         ", phone=" + phone +
         ", fax=" + fax +
         ", email=" + email +
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
