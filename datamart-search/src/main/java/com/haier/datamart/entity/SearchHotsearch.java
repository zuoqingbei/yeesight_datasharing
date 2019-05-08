package com.haier.datamart.entity;


import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 热搜表
 * </p>
 *
 * @author dsh123
 * @since 2018-05-25
 */
@TableName("search_hotsearch")
public class SearchHotsearch extends BaseModel<SearchHotsearch> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 搜索关键字
     */
    private String keyword;
    /**
     * 搜索次数
     */
    private Integer nums;
   


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getNums() {
        return nums;
    }

    public void setNums(Integer nums) {
        this.nums = nums;
    }

   

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

	@Override
	public String toString() {
		return "SearchHotsearch [id=" + id + ", keyword=" + keyword + ", nums="
				+ nums + "]";
	}

 
}
