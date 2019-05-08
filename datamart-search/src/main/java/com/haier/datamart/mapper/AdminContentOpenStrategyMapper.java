package com.haier.datamart.mapper;

import org.apache.ibatis.annotations.Param;

import com.haier.datamart.entity.AdminContentOpenStrategy;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 内容开放策略 Mapper 接口
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-06-15
 */
public interface AdminContentOpenStrategyMapper extends BaseMapper<AdminContentOpenStrategy> {
        AdminContentOpenStrategy getbyid(String id);
        String selectByuid(@Param("uid")String uid);
}
