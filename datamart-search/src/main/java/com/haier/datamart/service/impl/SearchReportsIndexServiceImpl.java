package com.haier.datamart.service.impl;

import com.haier.datamart.entity.SearchReportsIndex;
import com.haier.datamart.mapper.SearchReportsIndexMapper;
import com.haier.datamart.service.ISearchReportsIndexService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 报表指标间表 服务实现类
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-05-23
 */
@Service
public class SearchReportsIndexServiceImpl extends ServiceImpl<SearchReportsIndexMapper, SearchReportsIndex> implements ISearchReportsIndexService {
   @Autowired
   private SearchReportsIndexMapper reportIndexMapper;
	
	@Override
	public int add(SearchReportsIndex reportsIndex) {
		// TODO Auto-generated method stub
		return reportIndexMapper.add(reportsIndex);
	}

}
