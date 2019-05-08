package com.haier.datamart.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 补录模块-补录数据表配置字段明细数据来源配置信息
 * @author zuoqb123
 * @date 2018-12-11
 */
@TableName("entering_table_setting_detail_data")
public class EnteringTableSettingDetailData extends Model<EnteringTableSettingDetailData> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
   private String id;
    /**
     * 配置编码
     */
   @TableField("detail_id")
   private String detailId;
    /**
     * sheet序号
     */
   @TableField("sheet_index")
   private Integer sheetIndex;//从1开始
    /**
     * 数据范围  格式D5:F10
     */
   @TableField("data_range")
   private String dataRange;
    /**
     * 排序
     */
   @TableField("order_no")
   private Integer orderNo;
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


   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getDetailId() {
      return detailId;
   }

   public void setDetailId(String detailId) {
      this.detailId = detailId;
   }

   public Integer getSheetIndex() {
      return sheetIndex;
   }

   public void setSheetIndex(Integer sheetIndex) {
      this.sheetIndex = sheetIndex;
   }

   public String getDataRange() {
      return dataRange;
   }

   public void setDataRange(String dataRange) {
      this.dataRange = dataRange;
   }

   public Integer getOrderNo() {
      return orderNo;
   }

   public void setOrderNo(Integer orderNo) {
      this.orderNo = orderNo;
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
   protected Serializable pkVal() {
      return this.id;
   }

   @Override
   public String toString() {
      return "EnteringTableSettingDetailData{" +
         "id=" + id +
         ", detailId=" + detailId +
         ", sheetIndex=" + sheetIndex +
         ", dataRange=" + dataRange +
         ", orderNo=" + orderNo +
         ", createDate=" + createDate +
         ", createBy=" + createBy +
         ", updateBy=" + updateBy +
         ", updateDate=" + updateDate +
         ", remarks=" + remarks +
         ", delFlag=" + delFlag +
         "}";
   }
}
