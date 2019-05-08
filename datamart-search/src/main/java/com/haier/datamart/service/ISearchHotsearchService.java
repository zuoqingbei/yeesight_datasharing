package com.haier.datamart.service;


import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.haier.datamart.entity.SearchHotsearch;

/**
 * <p>
 * 热搜表 服务类
 * </p>
 *
 * @author dsh123
 * @since 2018-05-25
 */
public interface ISearchHotsearchService extends IService<SearchHotsearch> {
	List<SearchHotsearch> findList();
	SearchHotsearch getOne(String keyword);
	 int updateOne(SearchHotsearch hotsearch);
	 int addhot(SearchHotsearch hotsearch);
	
}
