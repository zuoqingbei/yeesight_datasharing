package com.haier.datamart.service;

import java.util.List;

import com.haier.datamart.entity.SearchIndexUseHistory;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 指标使用记录表 服务类
 * </p>
 *
 * @author zuoqb123
 * @since 2018-09-26
 */
public interface ISearchIndexUseHistoryService extends IService<SearchIndexUseHistory> {
	List<SearchIndexUseHistory> pageList(SearchIndexUseHistory e);
}
