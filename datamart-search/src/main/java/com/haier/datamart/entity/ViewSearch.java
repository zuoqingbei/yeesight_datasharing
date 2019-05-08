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
 * VIEW
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-05-24
 */
@TableName("view_search")
public class ViewSearch extends Model<ViewSearch> {

    private static final long serialVersionUID = 1L;
    private String pk;
    private String id;
    @TableField("NAME")
    private List<String> keywords;
    private String name;
    private String type;
    private String url;
    @TableField("screen_url")
    private String screenUrl;
    private String descs;
    private String category1;
    private String category2;
    private String category3;
    private String tags;
    @TableField("view_num")
    private Integer viewNum;
    @TableField("zan_num")
    private Integer zanNum;
    @TableField("share_num")
    private Integer shareNum;
    @TableField("create_by")
    private String createBy;
    @TableField("create_date")
    private Date createDate;
    private String remarks;
    private String subName;

    private int pageNum;
    
    private int size;
    
    private String count;
    

    
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
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

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	
	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

	@Override
	public String toString() {
		return "ViewSearch [pk=" + pk + ", id=" + id + ", keywords=" + keywords + ", name=" + name + ", type=" + type
				+ ", url=" + url + ", screenUrl=" + screenUrl + ", descs=" + descs + ", category1=" + category1
				+ ", category2=" + category2 + ", category3=" + category3 + ", tags=" + tags + ", viewNum=" + viewNum
				+ ", zanNum=" + zanNum + ", shareNum=" + shareNum + ", createBy=" + createBy + ", createDate="
				+ createDate + ", remarks=" + remarks + ", subName=" + subName + ", pageNum=" + pageNum + ", size="
				+ size + ", count=" + count + "]";
	}


	
   
}
