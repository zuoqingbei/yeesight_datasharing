package com.haier.datamart.service.impl;


import java.util.List;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haier.datamart.entity.SearchReports;
import com.haier.datamart.mapper.SearchReportsMapper;
import com.haier.datamart.service.ISearchReportsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 报表信息 服务实现类
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-05-23
 */
@Service
public class SearchReportsServiceImpl extends ServiceImpl<SearchReportsMapper, SearchReports> implements ISearchReportsService {
    
	@Autowired
	private SearchReportsMapper searchReportsMapper;

	@Override
	public SearchReports getById(String id) {
		// TODO Auto-generated method stub
		return searchReportsMapper.getById(id);
	}
	@Override
	public List<SearchReports> getKeyword(SearchReports searchReports) {
		// TODO Auto-generated method stub
		return searchReportsMapper.getKeyword(searchReports);
	}
	@Transactional
	@Override
	public int addExcle(SearchReports reports) {
		// TODO Auto-generated method stub
		return searchReportsMapper.addExcel(reports);
	}
	@Override
	public List<SearchReports> getReportByIndexId(String indexId) {
		return searchReportsMapper.getReportByIndexId(indexId);
	}

}
