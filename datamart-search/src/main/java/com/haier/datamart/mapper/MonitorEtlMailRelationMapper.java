package com.haier.datamart.mapper;

import java.util.List;

import com.haier.datamart.entity.MonitorEtlMailIndex;
import com.haier.datamart.entity.MonitorEtlMailRelation;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author doushuihai123
 * @since 2018-07-24
 */
public interface MonitorEtlMailRelationMapper extends BaseMapper<MonitorEtlMailRelation> {
	List<MonitorEtlMailRelation> getEtlMailRelation(MonitorEtlMailRelation etlMailRelation);//邮箱管理  查询
	MonitorEtlMailRelation getEtlMailRelationById(String id);//邮箱管理  根据id查询
	int insertEtlMailRelation(MonitorEtlMailRelation etlMailRelation);//邮箱管理  新增
	Integer updateById(MonitorEtlMailRelation etlMailRelation);//邮箱管理  修改
	Integer deleteById(String id);//邮箱管理  删除
}
