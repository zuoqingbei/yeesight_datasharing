package com.haier.datamart.mapper;

import java.util.List;

import com.haier.datamart.entity.AdminDataStrategyDetail;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 用户-数据权限（策略明细 到表 字段） Mapper 接口
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-06-15
 */
public interface AdminDataStrategyDetailMapper extends BaseMapper<AdminDataStrategyDetail> {
     List<AdminDataStrategyDetail> getbyid(String id);
	
	
	
}
