package com.haier.datamart.mapper;

import org.apache.ibatis.annotations.Param;

import com.haier.datamart.entity.AdminMonitorLeader;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 监控负责人配置 Mapper 接口
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-06-25
 */
public interface AdminMonitorLeaderMapper extends BaseMapper<AdminMonitorLeader> {
     String selectByuid(@Param("uid")String uid);
}
