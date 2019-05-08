package com.haier.datamart.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 报表信息
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-05-23
 */
@TableName("search_reports")
public class SearchReports extends Model<SearchReports> {

 

	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 显示系统
     */
    @TableField("system_name")
    private String systemName;
    /**
     * 显示系统连接地址
     */
    private String link;
    /**
     * 打开类型 _balnk _target
     */
    @TableField("open_type")
    private String openType;
    /**
     * 屏幕截图
     */
    @TableField("screen_url")
    private String screenUrl;
    /**
     * 名称
     */
    private String name;
    /**
     * 报表类型 字典表
     */
    private String types;
    /**
     * 报表路径
     */
    private String path;
    
    /**
     * 报表地址
     */
    private String url;
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
    /**
     * 创建人
     */
    @TableField("create_by")
    private String createBy;
    /**
     * 创建时间
     */
    @TableField("create_date")
    private Date createDate;
    /**
     * 更新人
     */
    @TableField("update_by")
    private String updateBy;
    /**
     * 更新时间
     */
    @TableField("update_date")
    private Date updateDate;
    /**
     * 备注
     */
    private String remarks;
    @TableField("del_flag")
    private String delFlag;
    /**
     * 网络类型
     */
    @TableField("net_type")
    private String netType;  
    /**
     * 报表名称
     */
    @TableField("report_name")
    private String reportName;
    /**
     * 部署服务器
     */
    @TableField("deployment_server")
    private String deploymentServer;
    
    /**
     * 项目管理地址
     */
    @TableField("project_management_address")
    private String projectManagementAddress;
    
    /**
     * 简述
     */
    private String sketch;
    
    /**
     * 指标数量
     */
    @TableField("target_amount")
    private String targetAmount;
    
    /**
     * 相关调度
     */
    @TableField("related_operation")
    private String relatedOperation;
    
    /**
     * 相关监控
     */
    @TableField("related_monitor")
    private String relatedMonitor;
    
    /**
     * 相关报表数
     */
    @TableField("related_reports")
    private String relatedReports;
     /**
      * 操作 
      */
    private String operation;
    /**
     * 描述
     */
    private String descs;
    public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getDeploymentServer() {
		return deploymentServer;
	}

	public void setDeploymentServer(String deploymentServer) {
		this.deploymentServer = deploymentServer;
	}

	public String getProjectManagementAddress() {
		return projectManagementAddress;
	}

	public void setProjectManagementAddress(String projectManagementAddress) {
		this.projectManagementAddress = projectManagementAddress;
	}

	public String getSketch() {
		return sketch;
	}

	public void setSketch(String sketch) {
		this.sketch = sketch;
	}

	public String getTargetAmount() {
		return targetAmount;
	}

	public void setTargetAmount(String targetAmount) {
		this.targetAmount = targetAmount;
	}

	public String getRelatedOperation() {
		return relatedOperation;
	}

	public void setRelatedOperation(String relatedOperation) {
		this.relatedOperation = relatedOperation;
	}

	public String getRelatedMonitor() {
		return relatedMonitor;
	}

	public void setRelatedMonitor(String relatedMonitor) {
		this.relatedMonitor = relatedMonitor;
	}

	public String getRelatedReports() {
		return relatedReports;
	}

	public void setRelatedReports(String relatedReports) {
		this.relatedReports = relatedReports;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	@TableField(exist = false)
    private String names;
    
    @TableField(exist = false)
    private List<SearchIndex> indexs;
    @TableField(exist = false)
    private int pageNum;
    @TableField(exist = false)
    private int size;
    
    @TableField(exist = false)
    private List<SearchReportsFile> files;
    @TableField(exist = false)
    private JenkinsSupportInfo jenkins=new JenkinsSupportInfo();

    public JenkinsSupportInfo getJenkins() {
		return jenkins;
	}

	public void setJenkins(JenkinsSupportInfo jenkins) {
		this.jenkins = jenkins;
	}

	public List<SearchReportsFile> getFiles() {
		return files;
	}

	public void setFiles(List<SearchReportsFile> files) {
		this.files = files;
	}


	public String getDescs() {
		return descs;
	}

	public void setDescs(String descs) {
		this.descs = descs;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	

	public List<SearchIndex> getIndexs() {
		return indexs;
	}

	public void setIndexs(List<SearchIndex> indexs) {
		this.indexs = indexs;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getOpenType() {
        return openType;
    }

    public void setOpenType(String openType) {
        this.openType = openType;
    }

    public String getScreenUrl() {
        return screenUrl;
    }

    public void setScreenUrl(String screenUrl) {
        this.screenUrl = screenUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
    public String getNetType() {
 		return netType;
 	}

 	public void setNetType(String netType) {
 		this.netType = netType;
 	}
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SearchReports{" +
        "id=" + id +
        ", systemName=" + systemName +
        ", link=" + link +
        ", openType=" + openType +
        ", screenUrl=" + screenUrl +
        ", name=" + name +
        ", types=" + types +
        ", path=" + path +
        ", url=" + url +
        ", category1=" + category1 +
        ", category2=" + category2 +
        ", category3=" + category3 +
        ", tags=" + tags +
        ", viewNum=" + viewNum +
        ", zanNum=" + zanNum +
        ", shareNum=" + shareNum +
        ", createBy=" + createBy +
        ", createDate=" + createDate +
        ", updateBy=" + updateBy +
        ", updateDate=" + updateDate +
        ", remarks=" + remarks +
        ", delFlag=" + delFlag +
        "}";
    }
}
