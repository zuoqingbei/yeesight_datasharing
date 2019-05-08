package com.haier.datamart.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * 邮件抬头预设置语句中占位符与统一接口配置信息
 * @author zuoqb123
 * @date 2019-01-03
 */
@TableName("mail_header_module_placeholder")
public class MailHeaderModulePlaceholder extends BaseModel<MailHeaderModulePlaceholder> {

   public String getPlatId() {
      return platId;
   }

   public void setPlatId(String platId) {
      this.platId = platId;
   }

   public String getPlaceholderName() {
      return placeholderName;
   }

   public void setPlaceholderName(String placeholderName) {
      this.placeholderName = placeholderName;
   }

   public String getPlaceholderDesc() {
      return placeholderDesc;
   }

   public void setPlaceholderDesc(String placeholderDesc) {
      this.placeholderDesc = placeholderDesc;
   }

   private static final long serialVersionUID = 1L;

    /**
     * 字段ID
     */
   private String id;
   @TableField("plat_id")
   private String platId;
    /**
     * 抬头编码
     */
   @TableField("header_module_id")
   private String headerModuleId;
    /**
     * 占位符key
     */
   @TableField("placeholder_key")
   private String placeholderKey;
    /**
     * 占位符值，一般对应统一接口dataType
     */
   @TableField("placeholder_value")
   private String placeholderValue;
   /**
    * 占位符值，一般对应统一接口dataType
    */
   @TableField("placeholder_name")
   private String placeholderName;
   /**
    * 占位符值，一般对应统一接口dataType
    */
   @TableField("placeholder_desc")
   private String placeholderDesc;
    /**
     * 统一接口参数
     */
   private String params;
    /**
     * 统一接口数据中key，通过他读取返回数值
     */
   @TableField("data_key")
   private String dataKey;
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
   private List<MailHeaderModulePlaceholderThreshold> thresholds=new ArrayList<MailHeaderModulePlaceholderThreshold>();

   public List<MailHeaderModulePlaceholderThreshold> getThresholds() {
	return thresholds;
}

public void setThresholds(List<MailHeaderModulePlaceholderThreshold> thresholds) {
	this.thresholds = thresholds;
}

public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getHeaderModuleId() {
      return headerModuleId;
   }

   public void setHeaderModuleId(String headerModuleId) {
      this.headerModuleId = headerModuleId;
   }

   public String getPlaceholderKey() {
      return placeholderKey;
   }

   public void setPlaceholderKey(String placeholderKey) {
      this.placeholderKey = placeholderKey;
   }

   public String getPlaceholderValue() {
      return placeholderValue;
   }

   public void setPlaceholderValue(String placeholderValue) {
      this.placeholderValue = placeholderValue;
   }

   public String getParams() {
      return params;
   }

   public void setParams(String params) {
      this.params = params;
   }

   public String getDataKey() {
      return dataKey;
   }

   public void setDataKey(String dataKey) {
      this.dataKey = dataKey;
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
      return "MailHeaderModulePlaceholder{" +
         "id=" + id +
         ", headerModuleId=" + headerModuleId +
         ", placeholderKey=" + placeholderKey +
         ", placeholderValue=" + placeholderValue +
         ", params=" + params +
         ", dataKey=" + dataKey +
         ", createBy=" + createBy +
         ", createDate=" + createDate +
         ", updateBy=" + updateBy +
         ", updateDate=" + updateDate +
         ", remarks=" + remarks +
         ", delFlag=" + delFlag +
         "}";
   }
}
