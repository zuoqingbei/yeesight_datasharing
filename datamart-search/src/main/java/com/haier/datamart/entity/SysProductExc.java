package com.haier.datamart.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 项目接口
 * </p>
 *
 * @author leizhiguo123
 * @since 2018-08-03
 */
@TableName("sys_product_exc")
public class SysProductExc extends Model<SysProductExc> {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 项目编号
     */
    @TableField("product_id")
    private String productId;
    /**
     * 接口编号
     */
    @TableField("exc_id")
    private Long excId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Long getExcId() {
        return excId;
    }

    public void setExcId(Long excId) {
        this.excId = excId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SysProductExc{" +
        "id=" + id +
        ", productId=" + productId +
        ", excId=" + excId +
        "}";
    }
}
