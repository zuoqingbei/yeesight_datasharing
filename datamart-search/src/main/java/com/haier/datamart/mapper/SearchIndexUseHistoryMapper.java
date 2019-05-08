package com.haier.datamart.mapper;

import java.util.List;

import com.haier.datamart.entity.SearchIndexUseHistory;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 指标使用记录表 Mapper 接口
 * </p>
 *
 * @author zuoqb123
 * @since 2018-09-26
 */
public interface SearchIndexUseHistoryMapper extends BaseMapper<SearchIndexUseHistory> {
	List<SearchIndexUseHistory> pageList(SearchIndexUseHistory e);

}
