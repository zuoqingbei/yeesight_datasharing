package com.haier.datamart.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haier.datamart.entity.SearchHotsearch;
import com.haier.datamart.mapper.SearchHotsearchMapper;
import com.haier.datamart.service.ISearchHotsearchService;

/**
 * <p>
 * 热搜表 服务实现类
 * </p>
 *
 * @author dsh123
 * @since 2018-05-25
 */
@Service
public class SearchHotsearchServiceImpl extends ServiceImpl<SearchHotsearchMapper, SearchHotsearch> implements ISearchHotsearchService {
	@Autowired
	private SearchHotsearchMapper hotsearchMapper;
	public List<SearchHotsearch> findList(){
		return hotsearchMapper.findList();
	}
	@Override
	public SearchHotsearch getOne(String keyword) {
		// TODO Auto-generated method stub
		return hotsearchMapper.getOne(keyword);
	}
	@Override
	public int updateOne(SearchHotsearch hotsearch) {
		// TODO Auto-generated method stub
		return hotsearchMapper.updateOne(hotsearch);
	}
	@Override
	public int addhot(SearchHotsearch hotsearch) {
		// TODO Auto-generated method stub
		return hotsearchMapper.addhot(hotsearch);
	}
}
