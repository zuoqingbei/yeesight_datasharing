package com.haier.datamart.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 指标表
 * </p>
 *
 * @author dsh123
 * @since 2018-05-23
 */
@TableName("search_index")
public class SearchIndex extends BaseModel<SearchIndex> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 指标编码
     */
    private String code;
    /**
     * 屏幕编号
     */
    @TableField("screen_no")
    private String screenNo;
    /**
     * 屏幕名称
     */
    @TableField("screen_name")
    private String screenName;
    /**
     * 屏幕地址
     */
    @TableField("screen_url")
    private String screenUrl;
    /**
     * 指标描述
     */
    private String descs;
    /**
     * 源表所在数据库id
     */
    @TableField("source_table_id")
    private String sourceTableId;
    
    /**
     * 源表所在数据库id
     */
    @TableField("target_db_id")
    private String targetDbId;
    /**
     * 显示表
     */
    @TableField("show_table")
    private String showTable;
    /**
     * 源表到目标表抽数sql
     */
    @TableField("sql")
    private String sql;
    /**
     * 平台
     */
    private String plat;
    /**
     * 定义
     */
    private String dingyi;
    /**
     * 指标类型
     */
    @TableField("index_type")
    private String indexType;
    /**
     * 指标分类
     */
    @TableField("index_fenlei")
    private String indexFenlei;
    /**
     * 计算公式
     */
    private String expression;
    /**
     * 工作流
     */
    private String workflow;
    /**
     * 调度
     */
    private String coordinator;
    /**
     * 取数逻辑
     */
    @TableField("get_data_magic")
    private String getDataMagic;
    /**
     * 取数时间
     */
    @TableField("get_data_time")
    private String getDataTime;
    /**
     * 取数频次 支持正则
     */
    @TableField("get_data_quart")
    private String getDataQuart;
    /**
     * 取数时长
     */
    @TableField("time_long")
    private String timeLong;
    /**
     * 取数方式 全量 增量
     */
    @TableField("get_data_type")
    private String getDataType;
    /**
     * 业务接口人
     */
    @TableField("mask_interface_user")
    private String maskInterfaceUser;
    @TableField("mask_interface_user_worknum")
    private String maskInterfaceUserWorknum;
    @TableField("mask_interface_user_email")
    private String maskInterfaceUserEmail;
    /**
     * 技术接口人
     */
    @TableField("it_interface_user")
    private String itInterfaceUser;
    @TableField("it_interface_user_worknum")
    private String itInterfaceUserWorknum;
    @TableField("it_interface_user_email")
    private String itInterfaceUserEmail;
    
    @TableField("offer_user")
    private String offerUser;
    @TableField("accuracy_standard")
    private String accuracyStandard;
    
    
    /**
     * 数据状态 系统/手工
     */
    @TableField("data_status")
    private String dataStatus;
    private String category1;
    private String category2;
    private String category3;
    /**
     * 标签
     */
    private String tags;
    /**
     * 浏览量
     */
    @TableField("view_num")
    private Integer viewNum;
    /**
     * 点赞量
     */
    @TableField("zan_num")
    private Integer zanNum;
    /**
     * 分享数
     */
    @TableField("share_num")
    private Integer shareNum;
    @TableField(exist=false)
    private String areaId;
    @TableField(exist=false)
    private String weiduId;
    @TableField(exist=false)
    private String weidu;
    @TableField(exist=false)
    private String areaName;
    @TableField(exist=false)
    private String entering;

    @TableField(exist=false)
    private String sourceTableName;
    @TableField(exist=false)
    private  String targetDbName;
    
    @TableField(exist=false)
    private String dimensionNames;
    

    @TableField(exist=false)
    private List<SearchIndex> indexs=new ArrayList<SearchIndex>();
    
	public List<SearchIndex> getIndexs() {
		return indexs;
	}

	public void setIndexs(List<SearchIndex> indexs) {
		this.indexs = indexs;
	}

	public String getDimensionNames() {
		return dimensionNames;
	}

	public void setDimensionNames(String dimensionNames) {
		this.dimensionNames = dimensionNames;
	}

	public String getSourceTableName() {
		return sourceTableName;
	}

	public void setSourceTableName(String sourceTableName) {
		this.sourceTableName = sourceTableName;
	}

	public String getTargetDbName() {
		return targetDbName;
	}

	public void setTargetDbName(String targetDbName) {
		this.targetDbName = targetDbName;
	}

	public String getSourceTableId() {
		return sourceTableId;
	}

	public void setSourceTableId(String sourceTableId) {
		this.sourceTableId = sourceTableId;
	}

	public String getTargetDbId() {
		return targetDbId;
	}

	public void setTargetDbId(String targetDbId) {
		this.targetDbId = targetDbId;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	
	@TableField(exist=false)
    private List<SearchReports> reports; //归属报告
    public String getEntering() {

		return entering;
	}

	public List<SearchReports> getReports() {
		return reports;
	}

	public void setReports(List<SearchReports> reports) {
		this.reports = reports;
	}

	public void setEntering(String entering) {
		this.entering = entering;
	}

	public String getAccuracyStandard() {
		return accuracyStandard;
	}

	public void setAccuracyStandard(String accuracyStandard) {
		this.accuracyStandard = accuracyStandard;
	}

	public String getOfferUser() {
		return offerUser;
	}

	public void setOfferUser(String offerUser) {
		this.offerUser = offerUser;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getWeidu() {
		return weidu;
	}

	public void setWeidu(String weidu) {
		this.weidu = weidu;
	}

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
    /**
     * 应用系统
     */
    @TableField("use_system")
    private String useSystem;
    
    @TableField(exist=false)
    private String catalogue;
    
    
	public String getCatalogue() {
		return catalogue;
	}

	public void setCatalogue(String catalogue) {
		this.catalogue = catalogue;
	}

	public String getUseSystem() {
		return useSystem;
	}

	public void setUseSystem(String useSystem) {
		this.useSystem = useSystem;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getWeiduId() {
		return weiduId;
	}

	public void setWeiduId(String weiduId) {
		this.weiduId = weiduId;
	}
	@TableField(exist=false)
	private List<IndexAddHelp> helps;
    

    public List<IndexAddHelp> getHelps() {
		return helps;
	}

	public void setHelps(List<IndexAddHelp> helps) {
		this.helps = helps;
	}
	@TableField(exist=false)
	private List<AdminDataContentDetail> adminDataContentDetailList;
	@TableField(exist=false)
    private List<AdminDataContent> adminDataContentList;
	@TableField(exist=false)
    private List<SearchIndexDimension> searchIndexDimensionList=new ArrayList<SearchIndexDimension>();
	@TableField(exist=false)
    private List<AdminDatasourceConfig> datasourceConfigList;
	@TableField(exist=false)
    private List<Dict> dictList;
	@TableField(exist=false)
    private List<SearchTags> tagsList;
	@TableField(exist=false)
    private List<ScanSubjectAreaIndex> subjectAreaIndexsList;
	@TableField(exist=false)
    private List<SearchIndexPlat>  plats;
    
    public List<SearchIndexPlat> getPlats() {
		return plats;
	}

	public void setPlats(List<SearchIndexPlat> plats) {
		this.plats = plats;
	}

	public SearchIndex() {
		super();
	}

	public SearchIndex(String id){
		super();
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getScreenNo() {
        return screenNo;
    }

    public void setScreenNo(String screenNo) {
        this.screenNo = screenNo;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getScreenUrl() {
        return screenUrl;
    }

    public void setScreenUrl(String screenUrl) {
        this.screenUrl = screenUrl;
    }

    public String getDescs() {
        return descs;
    }

    public void setDescs(String descs) {
        this.descs = descs;
    }

    public String getShowTable() {
        return showTable;
    }

    public void setShowTable(String showTable) {
        this.showTable = showTable;
    }

    public String getPlat() {
        return plat;
    }

    public void setPlat(String plat) {
        this.plat = plat;
    }

    public String getDingyi() {
        return dingyi;
    }

    public void setDingyi(String dingyi) {
        this.dingyi = dingyi;
    }

    public String getIndexType() {
        return indexType;
    }

    public void setIndexType(String indexType) {
        this.indexType = indexType;
    }

    public String getIndexFenlei() {
        return indexFenlei;
    }

    public void setIndexFenlei(String indexFenlei) {
        this.indexFenlei = indexFenlei;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getWorkflow() {
        return workflow;
    }

    public void setWorkflow(String workflow) {
        this.workflow = workflow;
    }

    public String getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(String coordinator) {
        this.coordinator = coordinator;
    }

    public String getGetDataMagic() {
        return getDataMagic;
    }

    public void setGetDataMagic(String getDataMagic) {
        this.getDataMagic = getDataMagic;
    }

    public String getGetDataTime() {
        return getDataTime;
    }

    public void setGetDataTime(String getDataTime) {
        this.getDataTime = getDataTime;
    }

    public String getGetDataQuart() {
        return getDataQuart;
    }

    public void setGetDataQuart(String getDataQuart) {
        this.getDataQuart = getDataQuart;
    }

    public String getTimeLong() {
        return timeLong;
    }

    public void setTimeLong(String timeLong) {
        this.timeLong = timeLong;
    }

    public String getGetDataType() {
        return getDataType;
    }

    public void setGetDataType(String getDataType) {
        this.getDataType = getDataType;
    }

    public String getMaskInterfaceUser() {
        return maskInterfaceUser;
    }

    public void setMaskInterfaceUser(String maskInterfaceUser) {
        this.maskInterfaceUser = maskInterfaceUser;
    }

    public String getItInterfaceUser() {
        return itInterfaceUser;
    }

    public void setItInterfaceUser(String itInterfaceUser) {
        this.itInterfaceUser = itInterfaceUser;
    }

    public String getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus;
    }

    public String getCategory1() {
        return category1;
    }

    public void setCategory1(String category1) {
        this.category1 = category1;
    }

    public String getCategory2() {
        return category2;
    }

    public void setCategory2(String category2) {
        this.category2 = category2;
    }

    public String getCategory3() {
        return category3;
    }

    public void setCategory3(String category3) {
        this.category3 = category3;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    public Integer getZanNum() {
        return zanNum;
    }

    public void setZanNum(Integer zanNum) {
        this.zanNum = zanNum;
    }

    public Integer getShareNum() {
        return shareNum;
    }

    public void setShareNum(Integer shareNum) {
        this.shareNum = shareNum;
    }

	public List getAdminDataContentDetailList() {
		return adminDataContentDetailList;
	}

	public void setAdminDataContentDetailList(List adminDataContentDetailList) {
		this.adminDataContentDetailList = adminDataContentDetailList;
	}

	public List getAdminDataContentList() {
		return adminDataContentList;
	}

	public void setAdminDataContentList(List adminDataContentList) {
		this.adminDataContentList = adminDataContentList;
	}


	
	
	public List<AdminDatasourceConfig> getDatasourceConfigList() {
		return datasourceConfigList;
	}

	public void setDatasourceConfigList(
			List<AdminDatasourceConfig> datasourceConfigList) {
		this.datasourceConfigList = datasourceConfigList;
	}

	public List<SearchIndexDimension> getSearchIndexDimensionList() {
		return searchIndexDimensionList;
	}

	public void setSearchIndexDimensionList(
			List<SearchIndexDimension> searchIndexDimensionList) {
		this.searchIndexDimensionList = searchIndexDimensionList;
	}
	
	public List<Dict> getDictList() {
		return dictList;
	}

	public void setDictList(List<Dict> dictList) {
		this.dictList = dictList;
	}

	public List<SearchTags> getTagsList() {
		return tagsList;
	}

	public void setTagsList(List<SearchTags> tagsList) {
		this.tagsList = tagsList;
	}

	public List<ScanSubjectAreaIndex> getSubjectAreaIndexsList() {
		return subjectAreaIndexsList;
	}

	public void setSubjectAreaIndexsList(
			List<ScanSubjectAreaIndex> subjectAreaIndexsList) {
		this.subjectAreaIndexsList = subjectAreaIndexsList;
	}

	@Override
    protected Serializable pkVal() {
        return this.id;
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

	public String getMaskInterfaceUserWorknum() {
		return maskInterfaceUserWorknum;
	}

	public void setMaskInterfaceUserWorknum(String maskInterfaceUserWorknum) {
		this.maskInterfaceUserWorknum = maskInterfaceUserWorknum;
	}

	public String getMaskInterfaceUserEmail() {
		return maskInterfaceUserEmail;
	}

	public void setMaskInterfaceUserEmail(String maskInterfaceUserEmail) {
		this.maskInterfaceUserEmail = maskInterfaceUserEmail;
	}

	public String getItInterfaceUserWorknum() {
		return itInterfaceUserWorknum;
	}

	public void setItInterfaceUserWorknum(String itInterfaceUserWorknum) {
		this.itInterfaceUserWorknum = itInterfaceUserWorknum;
	}

	public String getItInterfaceUserEmail() {
		return itInterfaceUserEmail;
	}

	public void setItInterfaceUserEmail(String itInterfaceUserEmail) {
		this.itInterfaceUserEmail = itInterfaceUserEmail;
	}

	@Override
	public String toString() {
		return "SearchIndex [id=" + id + ", name=" + name + ", code=" + code
				+ ", screenNo=" + screenNo + ", screenName=" + screenName
				+ ", screenUrl=" + screenUrl + ", descs=" + descs
				+ ", showTable=" + showTable + ", plat=" + plat + ", dingyi="
				+ dingyi + ", indexType=" + indexType + ", indexFenlei="
				+ indexFenlei + ", expression=" + expression + ", workflow="
				+ workflow + ", coordinator=" + coordinator + ", getDataMagic="
				+ getDataMagic + ", getDataTime=" + getDataTime
				+ ", getDataQuart=" + getDataQuart + ", timeLong=" + timeLong
				+ ", getDataType=" + getDataType + ", maskInterfaceUser="
				+ maskInterfaceUser + ", maskInterfaceUserWorknum="
				+ maskInterfaceUserWorknum + ", maskInterfaceUserEmail="
				+ maskInterfaceUserEmail + ", itInterfaceUser="
				+ itInterfaceUser + ", itInterfaceUserWorknum="
				+ itInterfaceUserWorknum + ", itInterfaceUserEmail="
				+ itInterfaceUserEmail + ", offerUser=" + offerUser
				+ ", accuracyStandard=" + accuracyStandard + ", dataStatus="
				+ dataStatus + ", category1=" + category1 + ", category2="
				+ category2 + ", category3=" + category3 + ", tags=" + tags
				+ ", viewNum=" + viewNum + ", zanNum=" + zanNum + ", shareNum="
				+ shareNum + ", areaId=" + areaId + ", weiduId=" + weiduId
				+ ", weidu=" + weidu + ", areaName=" + areaName
				+ ", createDate=" + createDate + ", createBy=" + createBy
				+ ", updateBy=" + updateBy + ", updateDate=" + updateDate
				+ ", remarks=" + remarks + ", delFlag=" + delFlag
				+ ", useSystem=" + useSystem + ", catalogue=" + catalogue
				+ ", helps=" + helps + ", adminDataContentDetailList="
				+ adminDataContentDetailList + ", adminDataContentList="
				+ adminDataContentList + ", searchIndexDimensionList="
				+ searchIndexDimensionList + ", datasourceConfigList="
				+ datasourceConfigList + ", dictList=" + dictList
				+ ", tagsList=" + tagsList + ", subjectAreaIndexsList="
				+ subjectAreaIndexsList + ", plats=" + plats + "]";
	}



   
}
