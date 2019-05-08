package com.haier.datamart.mapper;

import org.apache.ibatis.annotations.Param;

import com.haier.datamart.entity.AdminStrategyApply;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 策略申请记录（开放策略、数据策略） Mapper 接口
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-06-25
 */
public interface AdminStrategyApplyMapper extends BaseMapper<AdminStrategyApply> {
     String selectByuid(@Param("uid")String uid);
}
