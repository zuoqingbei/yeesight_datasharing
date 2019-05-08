package com.haier.datamart.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.datamart.entity.AdminUserStrategy;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 用户策略分配对照表 Mapper 接口
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-06-15
 */
public interface AdminUserStrategyMapper extends BaseMapper<AdminUserStrategy> {

	List<AdminUserStrategy> getByuserId(String id);
	String selectByuid(@Param("uid")String uid);
}
