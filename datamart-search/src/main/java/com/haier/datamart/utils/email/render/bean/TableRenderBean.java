package com.haier.datamart.utils.email.render.bean;


import java.util.List;

/**
 * 用于通用邮件发送的转化bean
 * 在模板中的语法不好转化 需要重新设置bean的结构
 */
public class TableRenderBean {
    private String style;//样式
    private String title;//表头
    private List<String> tableHeader;//列名
    private List<List<String>> tableContent;//表内容
    private List<List<String>>  bodyStyle;//表样式
    
    public List<List<String>> getBodyStyle() {
		return bodyStyle;
	}

	public void setBodyStyle(List<List<String>> bodyStyle) {
		this.bodyStyle = bodyStyle;
	}

	public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getTableHeader() {
        return tableHeader;
    }

    public void setTableHeader(List<String> tableHeader) {
        this.tableHeader = tableHeader;
    }

    public List<List<String>> getTableContent() {
        return tableContent;
    }

    public void setTableContent(List<List<String>> tableContent) {
        this.tableContent = tableContent;
    }
    //    private
}
