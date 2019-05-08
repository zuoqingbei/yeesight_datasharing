package com.haier.datamart.mapper;

import org.apache.ibatis.annotations.Param;

import com.haier.datamart.entity.AdminDataStrategy;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 数据分配策略（到表 字段） Mapper 接口
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-06-15
 */
public interface AdminDataStrategyMapper extends BaseMapper<AdminDataStrategy> {
     AdminDataStrategy getbyid(String id);
     String selectByuid(@Param("uid")String uid);
}
