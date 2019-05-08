package com.haier.datamart.service.impl;

import java.util.List;

import com.haier.datamart.entity.MonitorEtlMailRelation;
import com.haier.datamart.mapper.MonitorEtlMailRelationMapper;
import com.haier.datamart.service.IMonitorEtlMailRelationService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author doushuihai123
 * @since 2018-07-24
 */
@Service
public class MonitorEtlMailRelationServiceImpl extends ServiceImpl<MonitorEtlMailRelationMapper, MonitorEtlMailRelation> implements IMonitorEtlMailRelationService {
	@Autowired
	private MonitorEtlMailRelationMapper etlMailRelationMapper;
	@Override
	public List<MonitorEtlMailRelation> getEtlMailRelation(
			MonitorEtlMailRelation etlMailRelation) {
		// TODO Auto-generated method stub
		return etlMailRelationMapper.getEtlMailRelation(etlMailRelation);
	}

	@Override
	public int insertEtlMailRelation(MonitorEtlMailRelation etlMailRelation) {
		// TODO Auto-generated method stub
		return etlMailRelationMapper.insertEtlMailRelation(etlMailRelation);
	}

	@Override
	public int update(MonitorEtlMailRelation etlMailRelation) {
		// TODO Auto-generated method stub
		return etlMailRelationMapper.updateById(etlMailRelation);
	}

	@Override
	public int deleteById(String id) {
		// TODO Auto-generated method stub
		return etlMailRelationMapper.deleteById(id);
	}

	@Override
	public MonitorEtlMailRelation getEtlMailRelationById(String id) {
		// TODO Auto-generated method stub
		return etlMailRelationMapper.getEtlMailRelationById(id);
	}

}
