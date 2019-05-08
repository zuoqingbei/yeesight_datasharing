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
@TableName("monitor_etl_param_m5")
public class MonitorEtlParamM5 extends Model<MonitorEtlParamM5> {

    private static final long serialVersionUID = 1L;
    private MonitorEtlControlParam mecp;
  
    public MonitorEtlControlParam getMecp() {
		return mecp;
	}

	public void setMecp(MonitorEtlControlParam mecp) {
		this.mecp = mecp;
	}

	/**
     * 模板编号
     */
    @TableId("module_id")
    private String moduleId;
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
     * 表输入-sql语句
     */
    @TableField("tableinput_sql")
    private String tableinputSql;
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

    public String getTableinputSql() {
        return tableinputSql;
    }

    public void setTableinputSql(String tableinputSql) {
        this.tableinputSql = tableinputSql;
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
        return "MonitorEtlParamM5{" +
        "moduleId=" + moduleId +
        ", indexId=" + indexId +
        ", indexName=" + indexName +
        ", httpUrl=" + httpUrl +
        ", httpParamname=" + httpParamname +
        ", httpParamvalue=" + httpParamvalue +
        ", jsonName=" + jsonName +
        ", jsonPath=" + jsonPath +
        ", tableinputSql=" + tableinputSql +
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
