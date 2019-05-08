package com.haier.datamart.entity;

import com.alibaba.fastjson.JSONObject;


public class MCellStyle {
	/**
	 * 宽度
	 */
	private Integer width;
	//水平对齐
	private Integer align;
	//垂直对齐
	private Integer verticalAlignment;
	private Integer indention;
	// 设置左边框;
	private Integer borderLeft;
	// 设置右边框;
	private Integer borderRight;
	// 设置顶边框;
	private Integer borderTop;
	// 设置底边框;
	private Integer borderBottom;
	// 设置左边框颜色;
	private Integer borderLeftColor;
	// 设置右边框颜色;
	private Integer borderRightColor;
	// 设置顶边框颜色;
	private Integer borderTopColor;
    //设置底边框颜色;
	private Integer borderBottomColor;
	//bg  color
	private Integer fillBackgroundColor;
	private Integer fillPattern;
	//set the foreground fill color
	private Integer fillForegroundColor;
	//字体名字
	private String fontName;
	private Integer fontHeight;
	private Integer fontColor;
	private Integer boldweight;
	public Integer getBoldweight() {
		return boldweight;
	}

	public void setBoldweight(Integer boldweight) {
		this.boldweight = boldweight;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}




	public Integer getAlign() {
		return align;
	}

	public void setAlign(Integer align) {
		this.align = align;
	}


	public Integer getVerticalAlignment() {
		return verticalAlignment;
	}

	public void setVerticalAlignment(Integer verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
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

	public static void main(String[] args) {
		MCellStyle m=new MCellStyle();
		m.setFontName("方正姚体");
		m.setFillForegroundColor(12);
		m.setFillPattern(1);
		m.setFillBackgroundColor(64);
		m.setFillForegroundColor(30);
		m.setFontColor(9);
		m.setAlign(2);
		m.setBoldweight(3);
		System.out.println(JSONObject.toJSONString(m));
	}
	
}
