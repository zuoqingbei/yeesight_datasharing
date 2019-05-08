package com.haier.datamart.mapper;

import org.apache.ibatis.annotations.Param;

import com.haier.datamart.entity.AdminExceptionMessage;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 监控异常信息表 Mapper 接口
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-06-25
 */
public interface AdminExceptionMessageMapper extends BaseMapper<AdminExceptionMessage> {
    String selectByuid(@Param("uid")String uid);
}
