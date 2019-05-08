package com.haier.datamart.service.impl;

import com.haier.datamart.entity.ScanSubjectAreaIndex;
import com.haier.datamart.mapper.ScanSubjectAreaIndexMapper;
import com.haier.datamart.service.IScanSubjectAreaIndexService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 主题域与指标对照 服务实现类
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-06-07
 */
@Service
public class ScanSubjectAreaIndexServiceImpl extends ServiceImpl<ScanSubjectAreaIndexMapper, ScanSubjectAreaIndex> implements IScanSubjectAreaIndexService {
   @Autowired
   private ScanSubjectAreaIndexMapper areaIndexMapper;
	
	@Override
	public int addExcle(ScanSubjectAreaIndex areaIndex) {
		// TODO Auto-generated method stub
		return areaIndexMapper.add(areaIndex);
	}

	@Override
	public ScanSubjectAreaIndex getAreaIdByIndexId(String indexId) {
		// TODO Auto-generated method stub
		return areaIndexMapper.getAreaIdByIndexId(indexId);
	}

}
