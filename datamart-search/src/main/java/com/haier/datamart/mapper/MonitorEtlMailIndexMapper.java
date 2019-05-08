package com.haier.datamart.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haier.datamart.entity.MonitorEtlMailIndex;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author doushuihai123
 * @since 2018-07-23
 */
public interface MonitorEtlMailIndexMapper extends BaseMapper<MonitorEtlMailIndex> {
	
	List<MonitorEtlMailIndex> getEtlMailIndex(MonitorEtlMailIndex etlMailIndex);//指标组管理  查询指标组基本信息
	int insertEtlMailIndex(MonitorEtlMailIndex etlMailIndex);//指标组管理  新增
	Integer updateByGroupId(MonitorEtlMailIndex etlMailIndex);//指标组管理  修改
	Integer deleteByGroupId(String groupId);//指标组管理  删除
	List<MonitorEtlMailIndex> getEtlMailIndex2Update(String groupId);//获取要修改的指标组数据
	List<MonitorEtlMailIndex> getEtlMailIndexByGroupName(String groupName);//根据groupName查询数据以校验
		
}
