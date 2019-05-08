package com.haier.datamart.service.impl;

import java.util.List;

import com.haier.datamart.entity.MonitorEtlMailIndex;
import com.haier.datamart.mapper.MonitorEtlMailIndexMapper;
import com.haier.datamart.service.IMonitorEtlMailIndexService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author doushuihai123
 * @since 2018-07-23
 */
@Service
public class MonitorEtlMailIndexServiceImpl extends ServiceImpl<MonitorEtlMailIndexMapper, MonitorEtlMailIndex> implements IMonitorEtlMailIndexService {
	@Autowired
	private MonitorEtlMailIndexMapper etlMailIndexMapper;
	@Override
	public List<MonitorEtlMailIndex> getEtlMailIndex(
			MonitorEtlMailIndex etlMailIndex) {
		// TODO Auto-generated method stub
		return etlMailIndexMapper.getEtlMailIndex(etlMailIndex);
	}
	@Override
	public int insertEtlMailIndex(MonitorEtlMailIndex etlMailIndex) {
		// TODO Auto-generated method stub
		return etlMailIndexMapper.insertEtlMailIndex(etlMailIndex);
	}
	@Override
	public int update(MonitorEtlMailIndex etlMailIndex) {
		// TODO Auto-generated method stub
		return etlMailIndexMapper.updateByGroupId(etlMailIndex);
	}
	@Override
	public int deleteByGroupId(String groupId) {
		// TODO Auto-generated method stub
		return etlMailIndexMapper.deleteByGroupId(groupId);
	}
	@Override
	public List<MonitorEtlMailIndex> getEtlMailIndex2Update(String groupId) {
		// TODO Auto-generated method stub
		return etlMailIndexMapper.getEtlMailIndex2Update(groupId);
	}
	@Override
	public List<MonitorEtlMailIndex> getEtlMailIndexByGroupName(String groupName) {
		// TODO Auto-generated method stub
		return etlMailIndexMapper.getEtlMailIndexByGroupName(groupName);
	}

}
