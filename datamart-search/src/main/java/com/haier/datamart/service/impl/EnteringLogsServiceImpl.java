package com.haier.datamart.service.impl;

import com.haier.datamart.entity.EnteringLogs;
import com.haier.datamart.mapper.EnteringLogsMapper;
import com.haier.datamart.service.IEnteringLogsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 补录模块-补录数据操作历史 服务实现类
 * </p>
 *
 * @author leizhiguo123
 * @since 2018-07-17
 */
@Service
public class EnteringLogsServiceImpl extends ServiceImpl<EnteringLogsMapper, EnteringLogs> implements IEnteringLogsService {
	@Autowired
	private EnteringLogsMapper enteringLogsMapper;
	@Override
	public void addLog(EnteringLogs log) {
		System.out.println("service层:"+log);
		enteringLogsMapper.addLog(log);
	}

}
