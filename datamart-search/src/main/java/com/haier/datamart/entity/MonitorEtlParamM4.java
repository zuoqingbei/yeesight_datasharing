package com.haier.datamart.entity;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author leizhiguo123
 * @since 2018-07-19
 */
@TableName("monitor_etl_param_m4")
public class MonitorEtlParamM4 extends Model<MonitorEtlParamM4> {

    private static final long serialVersionUID = 1L;
    private MonitorEtlControlParam mecp;
  
    public MonitorEtlControlParam getMecp() {
		return mecp;
	}

	public void setMecp(MonitorEtlControlParam mecp) {
		this.mecp = mecp;
	}

	@TableId("module_id")
    private String moduleId;
    @TableField("index_id")
    private String indexId;
    @TableField("index_name")
    private String indexName;
    /**
     * http-url地址
     */
    @TableField("http_url")
    private String httpUrl;
    /**
     * http-访问参数名称
     */
    @TableField("http_paramname")
    private String httpParamname;
    /**
     * http-访问参数值
     */
    @TableField("http_paramvalue")
    private String httpParamvalue;
    /**
     * 界面字段别名
     */
    @TableField("json_name")
    private String jsonName;
    /**
     * 界面字段路径
     */
    @TableField("json_path")
    private String jsonPath;
    /**
     * http-url地址
     */
    @TableField("http_url1")
    private String httpUrl1;
    /**
     * http-访问参数名称
     */
    @TableField("http_paramname1")
    private String httpParamname1;
    /**
     * http-访问参数值
     */
    @TableField("http_paramvalue1")
    private String httpParamvalue1;
    /**
     * 界面字段别名
     */
    @TableField("json_name1")
    private String jsonName1;
    /**
     * 界面字段路径
     */
    @TableField("json_path1")
    private String jsonPath1;
    /**
     * 字段排序
     */
    private String sortmeta;
    /**
     * 字段排序1
     */
    private String sortmeta1;
    /**
     * 记录集合并
     */
    private String mergejoinmeta;
    /**
     * 记录集合并1
     */
    private String mergejoinmeta1;
    /**
     * 记录筛选-左值
     */
    @TableField("filter_leftvalue")
    private String filterLeftvalue;
    /**
     * '=' = 0;'<>' = 1;'<' = 2;'<=' = 3;'>' = 4;'>=' = 5;'REGEXP' = 6;'IS NULL' = 7;'IS NOT NULL' = 8;'IN LIST' = 9;'CONTAINS' = 10;'STARTS WITH' = 11;'ENDS WITH' = 12;'LIKE' = 13;'TRUE' = 14;
     */
    @TableField("filter_function")
    private Integer filterFunction;
    /**
     * 记录筛选-右值
     */
    @TableField("filter_rightvalue")
    private String filterRightvalue;
    /**
     * 表输出-数据库连接
     */
    @TableField("tableoutput_database")
    private String tableoutputDatabase;
    /**
     * 表输出-表名
     */
    @TableField("tableoutput_table")
    private String tableoutputTable;
    /**
     * 表输出-表中字段
     */
    @TableField("tableoutput_column")
    private String tableoutputColumn;
    /**
     * 表输出-流中字段
     */
    @TableField("streamoutput_column")
    private String streamoutputColumn;
    @TableField("type_id")
    private String typeId;
    @TableField("module_desc")
    private String moduleDesc;
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


    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
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

    public String getHttpUrl() {
        return httpUrl;
    }

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }

    public String getHttpParamname() {
        return httpParamname;
    }

    public void setHttpParamname(String httpParamname) {
        this.httpParamname = httpParamname;
    }

    public String getHttpParamvalue() {
        return httpParamvalue;
    }

    public void setHttpParamvalue(String httpParamvalue) {
        this.httpParamvalue = httpParamvalue;
    }

    public String getJsonName() {
        return jsonName;
    }

    public void setJsonName(String jsonName) {
        this.jsonName = jsonName;
    }

    public String getJsonPath() {
        return jsonPath;
    }

    public void setJsonPath(String jsonPath) {
        this.jsonPath = jsonPath;
    }

    public String getHttpUrl1() {
        return httpUrl1;
    }

    public void setHttpUrl1(String httpUrl1) {
        this.httpUrl1 = httpUrl1;
    }

    public String getHttpParamname1() {
        return httpParamname1;
    }

    public void setHttpParamname1(String httpParamname1) {
        this.httpParamname1 = httpParamname1;
    }

    public String getHttpParamvalue1() {
        return httpParamvalue1;
    }

    public void setHttpParamvalue1(String httpParamvalue1) {
        this.httpParamvalue1 = httpParamvalue1;
    }

    public String getJsonName1() {
        return jsonName1;
    }

    public void setJsonName1(String jsonName1) {
        this.jsonName1 = jsonName1;
    }

    public String getJsonPath1() {
        return jsonPath1;
    }

    public void setJsonPath1(String jsonPath1) {
        this.jsonPath1 = jsonPath1;
    }

    public String getSortmeta() {
        return sortmeta;
    }

    public void setSortmeta(String sortmeta) {
        this.sortmeta = sortmeta;
    }

    public String getSortmeta1() {
        return sortmeta1;
    }

    public void setSortmeta1(String sortmeta1) {
        this.sortmeta1 = sortmeta1;
    }

    public String getMergejoinmeta() {
        return mergejoinmeta;
    }

    public void setMergejoinmeta(String mergejoinmeta) {
        this.mergejoinmeta = mergejoinmeta;
    }

    public String getMergejoinmeta1() {
        return mergejoinmeta1;
    }

    public void setMergejoinmeta1(String mergejoinmeta1) {
        this.mergejoinmeta1 = mergejoinmeta1;
    }

    public String getFilterLeftvalue() {
        return filterLeftvalue;
    }

    public void setFilterLeftvalue(String filterLeftvalue) {
        this.filterLeftvalue = filterLeftvalue;
    }

    public Integer getFilterFunction() {
        return filterFunction;
    }

    public void setFilterFunction(Integer filterFunction) {
        this.filterFunction = filterFunction;
    }

    public String getFilterRightvalue() {
        return filterRightvalue;
    }

    public void setFilterRightvalue(String filterRightvalue) {
        this.filterRightvalue = filterRightvalue;
    }

    public String getTableoutputDatabase() {
        return tableoutputDatabase;
    }

    public void setTableoutputDatabase(String tableoutputDatabase) {
        this.tableoutputDatabase = tableoutputDatabase;
    }

    public String getTableoutputTable() {
        return tableoutputTable;
    }

    public void setTableoutputTable(String tableoutputTable) {
        this.tableoutputTable = tableoutputTable;
    }

    public String getTableoutputColumn() {
        return tableoutputColumn;
    }

    public void setTableoutputColumn(String tableoutputColumn) {
        this.tableoutputColumn = tableoutputColumn;
    }

    public String getStreamoutputColumn() {
        return streamoutputColumn;
    }

    public void setStreamoutputColumn(String streamoutputColumn) {
        this.streamoutputColumn = streamoutputColumn;
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
        return this.moduleId;
    }

    @Override
    public String toString() {
        return "MonitorEtlParamM4{" +
        "moduleId=" + moduleId +
        ", indexId=" + indexId +
        ", indexName=" + indexName +
        ", httpUrl=" + httpUrl +
        ", httpParamname=" + httpParamname +
        ", httpParamvalue=" + httpParamvalue +
        ", jsonName=" + jsonName +
        ", jsonPath=" + jsonPath +
        ", httpUrl1=" + httpUrl1 +
        ", httpParamname1=" + httpParamname1 +
        ", httpParamvalue1=" + httpParamvalue1 +
        ", jsonName1=" + jsonName1 +
        ", jsonPath1=" + jsonPath1 +
        ", sortmeta=" + sortmeta +
        ", sortmeta1=" + sortmeta1 +
        ", mergejoinmeta=" + mergejoinmeta +
        ", mergejoinmeta1=" + mergejoinmeta1 +
        ", filterLeftvalue=" + filterLeftvalue +
        ", filterFunction=" + filterFunction +
        ", filterRightvalue=" + filterRightvalue +
        ", tableoutputDatabase=" + tableoutputDatabase +
        ", tableoutputTable=" + tableoutputTable +
        ", tableoutputColumn=" + tableoutputColumn +
        ", streamoutputColumn=" + streamoutputColumn +
        ", typeId=" + typeId +
        ", moduleDesc=" + moduleDesc +
        ", createBy=" + createBy +
        ", createDate=" + createDate +
        ", updateBy=" + updateBy +
        ", updateDate=" + updateDate +
        ", remarks=" + remarks +
        ", delFlag=" + delFlag +
        "}";
    }
}
