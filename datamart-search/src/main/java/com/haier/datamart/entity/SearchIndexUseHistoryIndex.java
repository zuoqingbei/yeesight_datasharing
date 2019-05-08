package com.haier.datamart.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 指标使用记录与指标中间表
 * </p>
 *
 * @author zuoqb123
 * @since 2018-09-26
 */
@TableName("search_index_use_history_index")
public class SearchIndexUseHistoryIndex extends Model<SearchIndexUseHistoryIndex> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 使用历史编码
     */
    @TableField("use_histoty_id")
    private String useHistotyId;
    /**
     * 指标编码
     */
    @TableField("index_id")
    private String indexId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUseHistotyId() {
        return useHistotyId;
    }

    public void setUseHistotyId(String useHistotyId) {
        this.useHistotyId = useHistotyId;
    }

    public String getIndexId() {
        return indexId;
    }

    public void setIndexId(String indexId) {
        this.indexId = indexId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SearchIndexUseHistoryIndex{" +
        "id=" + id +
        ", useHistotyId=" + useHistotyId +
        ", indexId=" + indexId +
        "}";
    }
}
