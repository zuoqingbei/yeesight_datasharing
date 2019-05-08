package com.haier.datamart.mapper;

import java.util.List;

import com.haier.datamart.entity.AdminRuleStrategyContentDetail;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 列规则策略-内容明细 Mapper 接口
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-06-15
 */
public interface AdminRuleStrategyContentDetailMapper extends BaseMapper<AdminRuleStrategyContentDetail> {
 
	List<AdminRuleStrategyContentDetail> getbyid(String id);
}
