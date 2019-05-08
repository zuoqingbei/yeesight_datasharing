package com.haier.datamart.mapper;

import com.haier.datamart.entity.EnteringLogs;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 补录模块-补录数据操作历史 Mapper 接口
 * </p>
 *
 * @author leizhiguo123
 * @since 2018-07-17
 */
public interface EnteringLogsMapper extends BaseMapper<EnteringLogs> {
	/**
	 * 增加一条日志
	 * @param log
	 */
	void addLog(EnteringLogs log);
	
}
