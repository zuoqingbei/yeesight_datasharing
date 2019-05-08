package com.haier.datamart.utils.email.render.bean;

import java.util.List;

/**
 * 用于email模板中的上下文bean，便于用简单的语法设置html模板
 */
public class MultiEmailBean {
    private String hearder;//内容头
    private List<TableRenderBean> tableRenderBeanList;//表格的信息

    public String getHearder() {
        return hearder;
    }

    public void setHearder(String hearder) {
        this.hearder = hearder;
    }

    public List<TableRenderBean> getTableRenderBeanList() {
        return tableRenderBeanList;
    }

    public void setTableRenderBeanList(List<TableRenderBean> tableRenderBeanList) {
        this.tableRenderBeanList = tableRenderBeanList;
    }
}
