package com.haier.datamart.service.impl;

import com.haier.datamart.entity.SearchReportsDimension;
import com.haier.datamart.mapper.SearchReportsDimensionMapper;
import com.haier.datamart.service.ISearchReportsDimensionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 报表维度中间表 服务实现类
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-05-23
 */
@Service
public class SearchReportsDimensionServiceImpl extends ServiceImpl<SearchReportsDimensionMapper, SearchReportsDimension> implements ISearchReportsDimensionService {
  @Autowired
  private SearchReportsDimensionMapper searchReportsDimensionService;
	
	@Override
	public int add(SearchReportsDimension reportsDimension) {
		// TODO Auto-generated method stub
		return searchReportsDimensionService.add(reportsDimension);
	}

}
