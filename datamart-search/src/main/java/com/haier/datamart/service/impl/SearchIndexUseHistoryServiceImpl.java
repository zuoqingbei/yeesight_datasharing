package com.haier.datamart.service.impl;

import java.util.List;

import com.haier.datamart.entity.SearchIndexUseHistory;
import com.haier.datamart.mapper.SearchIndexUseHistoryMapper;
import com.haier.datamart.service.ISearchIndexUseHistoryService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 指标使用记录表 服务实现类
 * </p>
 *
 * @author zuoqb123
 * @since 2018-09-26
 */
@Service
public class SearchIndexUseHistoryServiceImpl extends ServiceImpl<SearchIndexUseHistoryMapper, SearchIndexUseHistory> implements ISearchIndexUseHistoryService {
	@Autowired
	private SearchIndexUseHistoryMapper searchIndexUseHistoryMapper;
	@Override
	public List<SearchIndexUseHistory> pageList(SearchIndexUseHistory e) {
		return searchIndexUseHistoryMapper.pageList(e);
	}

}
