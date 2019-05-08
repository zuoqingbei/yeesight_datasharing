package com.haier.datamart.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 用户策略分配对照表
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-06-15
 */
@TableName("admin_user_strategy")
public class AdminUserStrategy extends Model<AdminUserStrategy> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    @TableField("user_id")
    private String userId;
    /**
     * 数据策略编号
     */
    @TableField("data_strategy_id")
    private String dataStrategyId;
    /**
     * 策略类型 0-数据策略 1-开放策略 2-列规则策略
     */
    @TableField("strategy_type")
    private String strategyType;
    /**
     * 数据源
     */
    @TableField("datasource_id")
    private String datasourceId;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDataStrategyId() {
        return dataStrategyId;
    }

    public void setDataStrategyId(String dataStrategyId) {
        this.dataStrategyId = dataStrategyId;
    }

    public String getStrategyType() {
        return strategyType;
    }

    public void setStrategyType(String strategyType) {
        this.strategyType = strategyType;
    }

    public String getDatasourceId() {
        return datasourceId;
    }

    public void setDatasourceId(String datasourceId) {
        this.datasourceId = datasourceId;
    }

   

    @Override
    public String toString() {
        return "AdminUserStrategy{" +
        "userId=" + userId +
        ", dataStrategyId=" + dataStrategyId +
        ", strategyType=" + strategyType +
        ", datasourceId=" + datasourceId +
        "}";
    }

	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return null;
	}
}
