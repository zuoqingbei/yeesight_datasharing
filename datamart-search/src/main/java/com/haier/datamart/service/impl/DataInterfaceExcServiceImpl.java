package com.haier.datamart.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haier.datamart.entity.DataInterfaceExc;
import com.haier.datamart.mapper.DataInterfaceExcMapper;
import com.haier.datamart.service.IDataInterfaceExcService;
/**
 * 服务实现类
 * @author zuoqb123
 * @date 2018-12-06
 */
@Service
@Transactional
public class DataInterfaceExcServiceImpl extends ServiceImpl<DataInterfaceExcMapper, DataInterfaceExc> implements IDataInterfaceExcService {

    @Autowired
    private DataInterfaceExcMapper dataInterfaceExcMapper;

	@Override
	public List<DataInterfaceExc> findExcByDbAndContentId(String dataSourceId,
			String contentId, int start, int end,String userId,String isAdmin) {
		return dataInterfaceExcMapper.findExcByDbAndContentId(dataSourceId, contentId, start, end,userId,isAdmin);
	}

	@Override
	public List<DataInterfaceExc> findExcByIndexId(String indexId, int start,
			int end,String userId,String isAdmin) {
		return dataInterfaceExcMapper.findExcByIndexId(indexId, start, end,userId,isAdmin);
	}

	@Override
	public List<DataInterfaceExc> findExcByReportId(String indexId, int start,
			int end,String userId,String isAdmin) {
		return dataInterfaceExcMapper.findExcByReportId(indexId, start, end,userId,isAdmin);
	}
   
}
