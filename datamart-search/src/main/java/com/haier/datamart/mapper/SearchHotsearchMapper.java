package com.haier.datamart.mapper;


import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haier.datamart.entity.SearchHotsearch;

/**
 * <p>
 * 热搜表 Mapper 接口
 * </p>
 *
 * @author dsh123
 * @since 2018-05-25
 */
public interface SearchHotsearchMapper extends BaseMapper<SearchHotsearch> {
	List<SearchHotsearch> findList();
	SearchHotsearch getOne(String keyword);
	int updateOne(SearchHotsearch hotsearch);
	int addhot(SearchHotsearch hotsearch);
	

}
