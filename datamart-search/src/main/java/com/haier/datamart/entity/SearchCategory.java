package com.haier.datamart.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 分类表
 * </p>
 *
 * @author dsh123
 * @since 2018-05-28
 */
@TableName("search_category")
public class SearchCategory extends BaseModel<SearchCategory> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 父键
     */
    @TableField("parent_id")
    private String parentId;
    /**
     * 全部父键
     */
    @TableField("parent_ids")
    private String parentIds;
    /**
     * 名称
     */
    private String name;
    /**
     * 排序
     */
    private BigDecimal sort;
    /**
     * 用来存储子目录
     */
    private List<SearchCategory> children;
    private String all;
    

    public String getAll() {
		return all;
	}
	public void setAll(String all) {
		this.all = all;
	}
	public SearchCategory() {
    	super();
	}
    public SearchCategory(String id) {
    	this.id=id;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSort() {
        return sort;
    }

    public void setSort(BigDecimal sort) {
        this.sort = sort;
    }

   
    public List<SearchCategory> getChildren() {
		return children;
	}

	public void setChildren(List<SearchCategory> children) {
		this.children = children;
	}
	public static List<SearchCategory> getChildrenByPid(String pid,List<SearchCategory> list,boolean needNext){
		List<SearchCategory> children=new ArrayList<SearchCategory>();
		for(SearchCategory t:list){
			if(pid.equals(t.getParentId())){
				List<SearchCategory> l=new ArrayList<SearchCategory>();
				if(needNext){
					l=getChildrenByPid(t.getId(), list,false);
					if(l!=null&&l.size()>0){
						t.setChildren(l);
					}else{
						t.setChildren(l);
					}
				}
				children.add(t);
			}
		}
		return children;
	}
	@Override
    protected Serializable pkVal() {
        return this.id;
    }
	@Override
	public String toString() {
		return "SearchCategory [id=" + id + ", parentId=" + parentId
				+ ", parentIds=" + parentIds + ", name=" + name + ", sort="
				+ sort + ", children=" + children + "]";
	}

	


}
