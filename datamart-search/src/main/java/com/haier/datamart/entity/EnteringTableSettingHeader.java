package com.haier.datamart.entity;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * 补录模块Excel头部信息
 * @author zuoqb123
 * @date 2018-10-18
 */
@TableName("entering_table_setting_header")
public class EnteringTableSettingHeader extends Model<EnteringTableSettingHeader> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
   private String id;
    /**
     * 配置编码
     */
   @TableField("entering_setting_id")
   private String enteringSettingId;
    /**
     * 行号
     */
   private Integer rownum;
    /**
     * haeder名称
     */
   private String name;
    /**
     * 宽度
     */
   private Integer width;
    /**
     * 高度
     */
   private Integer hight;
    /**
     * haeder样式
     */
   private Integer style;
    /**
     * 行开始位置
     */
   @TableField("row_from")
   private Integer rowFrom;
    /**
     * 列开始位置
     */
   @TableField("column_from")
   private Integer columnFrom;
    /**
     * 行结束位置
     */
   @TableField("row_to")
   private Integer rowTo;
    /**
     * 列结束位置
     */
   @TableField("column_to")
   private Integer columnTo;
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
   @TableField("data_format")
   private Integer dataFormat;
   @TableField("data_format_string")
   private String dataFormatString;
   private Integer locked;
   private Integer hiidden;
   private Integer align;
   @TableField("wrap_text")
   private Integer wrapText;
   @TableField("vertical_alignment")
   private Integer verticalAlignment;
   private Integer rotation;
   private Integer indention;
   @TableField("border_left")
   private Integer borderLeft;
   @TableField("border_right")
   private Integer borderRight;
   @TableField("border_top")
   private Integer borderTop;
   @TableField("border_bottom")
   private Integer borderBottom;
   @TableField("border_left_color")
   private Integer borderLeftColor;
   @TableField("border_right_color")
   private Integer borderRightColor;
   @TableField("border_top_color")
   private Integer borderTopColor;
   @TableField("border_bottom_color")
   private Integer borderBottomColor;
   @TableField("fill_background_color")
   private Integer fillBackgroundColor;
   @TableField("fill_pattern")
   private Integer fillPattern;
   @TableField("fill_foreground_color")
   private Integer fillForegroundColor;
   @TableField("font_name")
   private String fontName;
   @TableField("font_height")
   private Integer fontHeight;
   @TableField("font_color")
   private Integer fontColor;
   @TableField("row_height")
   private Integer rowHeight;


   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getEnteringSettingId() {
      return enteringSettingId;
   }

   public void setEnteringSettingId(String enteringSettingId) {
      this.enteringSettingId = enteringSettingId;
   }

   public Integer getRownum() {
      return rownum;
   }

   public void setRownum(Integer rownum) {
      this.rownum = rownum;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Integer getWidth() {
      return width;
   }

   public void setWidth(Integer width) {
      this.width = width;
   }

   public Integer getHight() {
      return hight;
   }

   public void setHight(Integer hight) {
      this.hight = hight;
   }

   public Integer getStyle() {
      return style;
   }

   public void setStyle(Integer style) {
      this.style = style;
   }

   public Integer getRowFrom() {
      return rowFrom;
   }

   public void setRowFrom(Integer rowFrom) {
      this.rowFrom = rowFrom;
   }

   public Integer getColumnFrom() {
      return columnFrom;
   }

   public void setColumnFrom(Integer columnFrom) {
      this.columnFrom = columnFrom;
   }

   public Integer getRowTo() {
      return rowTo;
   }

   public void setRowTo(Integer rowTo) {
      this.rowTo = rowTo;
   }

   public Integer getColumnTo() {
      return columnTo;
   }

   public void setColumnTo(Integer columnTo) {
      this.columnTo = columnTo;
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

   public Integer getDataFormat() {
      return dataFormat;
   }

   public void setDataFormat(Integer dataFormat) {
      this.dataFormat = dataFormat;
   }

   public String getDataFormatString() {
      return dataFormatString;
   }

   public void setDataFormatString(String dataFormatString) {
      this.dataFormatString = dataFormatString;
   }

   public Integer getLocked() {
      return locked;
   }

   public void setLocked(Integer locked) {
      this.locked = locked;
   }

   public Integer getHiidden() {
      return hiidden;
   }

   public void setHiidden(Integer hiidden) {
      this.hiidden = hiidden;
   }

   public Integer getAlign() {
      return align;
   }

   public void setAlign(Integer align) {
      this.align = align;
   }

   public Integer getWrapText() {
      return wrapText;
   }

   public void setWrapText(Integer wrapText) {
      this.wrapText = wrapText;
   }

   public Integer getVerticalAlignment() {
      return verticalAlignment;
   }

   public void setVerticalAlignment(Integer verticalAlignment) {
      this.verticalAlignment = verticalAlignment;
   }

   public Integer getRotation() {
      return rotation;
   }

   public void setRotation(Integer rotation) {
      this.rotation = rotation;
   }

   public Integer getIndention() {
      return indention;
   }

   public void setIndention(Integer indention) {
      this.indention = indention;
   }

   public Integer getBorderLeft() {
      return borderLeft;
   }

   public void setBorderLeft(Integer borderLeft) {
      this.borderLeft = borderLeft;
   }

   public Integer getBorderRight() {
      return borderRight;
   }

   public void setBorderRight(Integer borderRight) {
      this.borderRight = borderRight;
   }

   public Integer getBorderTop() {
      return borderTop;
   }

   public void setBorderTop(Integer borderTop) {
      this.borderTop = borderTop;
   }

   public Integer getBorderBottom() {
      return borderBottom;
   }

   public void setBorderBottom(Integer borderBottom) {
      this.borderBottom = borderBottom;
   }

   public Integer getBorderLeftColor() {
      return borderLeftColor;
   }

   public void setBorderLeftColor(Integer borderLeftColor) {
      this.borderLeftColor = borderLeftColor;
   }

   public Integer getBorderRightColor() {
      return borderRightColor;
   }

   public void setBorderRightColor(Integer borderRightColor) {
      this.borderRightColor = borderRightColor;
   }

   public Integer getBorderTopColor() {
      return borderTopColor;
   }

   public void setBorderTopColor(Integer borderTopColor) {
      this.borderTopColor = borderTopColor;
   }

   public Integer getBorderBottomColor() {
      return borderBottomColor;
   }

   public void setBorderBottomColor(Integer borderBottomColor) {
      this.borderBottomColor = borderBottomColor;
   }

   public Integer getFillBackgroundColor() {
      return fillBackgroundColor;
   }

   public void setFillBackgroundColor(Integer fillBackgroundColor) {
      this.fillBackgroundColor = fillBackgroundColor;
   }

   public Integer getFillPattern() {
      return fillPattern;
   }

   public void setFillPattern(Integer fillPattern) {
      this.fillPattern = fillPattern;
   }

   public Integer getFillForegroundColor() {
      return fillForegroundColor;
   }

   public void setFillForegroundColor(Integer fillForegroundColor) {
      this.fillForegroundColor = fillForegroundColor;
   }

   public String getFontName() {
      return fontName;
   }

   public void setFontName(String fontName) {
      this.fontName = fontName;
   }

   public Integer getFontHeight() {
      return fontHeight;
   }

   public void setFontHeight(Integer fontHeight) {
      this.fontHeight = fontHeight;
   }

   public Integer getFontColor() {
      return fontColor;
   }

   public void setFontColor(Integer fontColor) {
      this.fontColor = fontColor;
   }

   public Integer getRowHeight() {
      return rowHeight;
   }

   public void setRowHeight(Integer rowHeight) {
      this.rowHeight = rowHeight;
   }

   @Override
   protected Serializable pkVal() {
      return this.id;
   }

   @Override
   public String toString() {
      return "EnteringTableSettingHeader{" +
         "id=" + id +
         ", enteringSettingId=" + enteringSettingId +
         ", rownum=" + rownum +
         ", name=" + name +
         ", width=" + width +
         ", hight=" + hight +
         ", style=" + style +
         ", rowFrom=" + rowFrom +
         ", columnFrom=" + columnFrom +
         ", rowTo=" + rowTo +
         ", columnTo=" + columnTo +
         ", orderNo=" + orderNo +
         ", createDate=" + createDate +
         ", createBy=" + createBy +
         ", updateBy=" + updateBy +
         ", updateDate=" + updateDate +
         ", remarks=" + remarks +
         ", delFlag=" + delFlag +
         ", dataFormat=" + dataFormat +
         ", dataFormatString=" + dataFormatString +
         ", locked=" + locked +
         ", hiidden=" + hiidden +
         ", align=" + align +
         ", wrapText=" + wrapText +
         ", verticalAlignment=" + verticalAlignment +
         ", rotation=" + rotation +
         ", indention=" + indention +
         ", borderLeft=" + borderLeft +
         ", borderRight=" + borderRight +
         ", borderTop=" + borderTop +
         ", borderBottom=" + borderBottom +
         ", borderLeftColor=" + borderLeftColor +
         ", borderRightColor=" + borderRightColor +
         ", borderTopColor=" + borderTopColor +
         ", borderBottomColor=" + borderBottomColor +
         ", fillBackgroundColor=" + fillBackgroundColor +
         ", fillPattern=" + fillPattern +
         ", fillForegroundColor=" + fillForegroundColor +
         ", fontName=" + fontName +
         ", fontHeight=" + fontHeight +
         ", fontColor=" + fontColor +
         ", rowHeight=" + rowHeight +
         "}";
   }
}
