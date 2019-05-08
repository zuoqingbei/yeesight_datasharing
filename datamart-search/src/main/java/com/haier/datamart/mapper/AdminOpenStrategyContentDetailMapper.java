package com.haier.datamart.mapper;

import java.util.List;

import com.haier.datamart.entity.AdminOpenStrategyContentDetail;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 内容开放策略-内容明细 Mapper 接口
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-06-15
 */
public interface AdminOpenStrategyContentDetailMapper extends BaseMapper<AdminOpenStrategyContentDetail> {
 
	List<AdminOpenStrategyContentDetail> getbyid(String id);
}
