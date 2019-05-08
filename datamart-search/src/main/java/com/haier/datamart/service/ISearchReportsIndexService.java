package com.haier.datamart.service;

import com.haier.datamart.entity.SearchReportsIndex;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 报表指标间表 服务类
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-05-23
 */
public interface ISearchReportsIndexService extends IService<SearchReportsIndex> {
     int add(SearchReportsIndex reportsIndex);
}
