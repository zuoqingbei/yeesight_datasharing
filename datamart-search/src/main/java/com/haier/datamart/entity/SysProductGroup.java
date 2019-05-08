package com.haier.datamart.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 项目用户
 * </p>
 *
 * @author leizhiguo123
 * @since 2018-08-03
 */
@TableName("sys_product_group")
public class SysProductGroup extends Model<SysProductGroup> {

    private static final long serialVersionUID = 1L;

    /**
     * 项目编号
     */
    @TableField("product_id")
    private String productId;
    /**
     * 用户组编号
     */
    @TableField("group_id")
    private String groupId;


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "SysProductGroup{" +
        "productId=" + productId +
        ", groupId=" + groupId +
        "}";
    }
}
