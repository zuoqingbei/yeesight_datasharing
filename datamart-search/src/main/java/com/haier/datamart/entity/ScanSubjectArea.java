package com.haier.datamart.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 主题域
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-06-07
 */
@TableName("scan_subject_area")
public class ScanSubjectArea extends BaseModel<ScanSubjectArea> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;
    /**
     * 为需要前端人员要求添加此字段，值与id相同
     */
    private String key;
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
     * 描述
     */
    private String description;
    /**
     * 排序（升序）
     */
    private BigDecimal sort;
    /**
     * 用来存储子目录
     */
    private List<ScanSubjectArea> children;
    /**
     * 用来存储子目录
     */
    private List<ScanSubjectArea> parent;

    private List<SearchIndex> indexs;

    public List<SearchIndex> getIndexs() {
		return indexs;
	}

	public void setIndexs(List<SearchIndex> indexs) {
		this.indexs = indexs;
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

	public List<ScanSubjectArea> getChildren() {
		return children;
	}

	public void setChildren(List<ScanSubjectArea> children) {
		this.children = children;
	}
	
	public List<ScanSubjectArea> getParent() {
		return parent;
	}

	public void setParent(List<ScanSubjectArea> parent) {
		this.parent = parent;
	}
	public static List<ScanSubjectArea> getParentByPid(String zpid,List<ScanSubjectArea> list,boolean needNext){
		List<ScanSubjectArea> parent=new ArrayList<ScanSubjectArea>();
		for(ScanSubjectArea t:list){
			if(zpid.equals(t.getId())){
				List<ScanSubjectArea> l=new ArrayList<ScanSubjectArea>();
				if(needNext){
					l=getParentByPid(t.getParentId(), list,false);
					if(l!=null && l.size()>0){
						t.setParent(l);;
					}else{
						t.setParent(l);
					}
				}
				parent.add(t);
			}
		}
		return parent;
	}
	public static List<ScanSubjectArea> getChildrenByPid(String pid,List<ScanSubjectArea> list,boolean needNext){
		List<ScanSubjectArea> children=new ArrayList<ScanSubjectArea>();
		for(ScanSubjectArea t:list){
			if(pid.equals(t.getParentId())){
				List<ScanSubjectArea> l=new ArrayList<ScanSubjectArea>();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getSort() {
        return sort;
    }

    public void setSort(BigDecimal sort) {
        this.sort = sort;
    }



    public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ScanSubjectArea{" +
        "id=" + id +
        ", name=" + name +
        ", description=" + description +
        ", sort=" + sort +
        ", createDate=" + createDate +
        ", createBy=" + createBy +
        ", updateBy=" + updateBy +
        ", updateDate=" + updateDate +
        ", remarks=" + remarks +
        ", delFlag=" + delFlag +
        "}";
    }
}
