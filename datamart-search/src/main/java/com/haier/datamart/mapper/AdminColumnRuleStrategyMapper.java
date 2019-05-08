package com.haier.datamart.mapper;

import org.apache.ibatis.annotations.Param;

import com.haier.datamart.entity.AdminColumnRuleStrategy;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 列规则策略 Mapper 接口
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-06-15
 */
public interface AdminColumnRuleStrategyMapper extends BaseMapper<AdminColumnRuleStrategy> {
      AdminColumnRuleStrategy getbyid(String  id);
      String selectByuid(@Param("uid")String uid);
}
