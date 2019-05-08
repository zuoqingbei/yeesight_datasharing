package com.haier.datamart.service;

import com.haier.datamart.entity.SearchReportsDimension;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 报表维度中间表 服务类
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-05-23
 */
public interface ISearchReportsDimensionService extends IService<SearchReportsDimension> {
    int  add(SearchReportsDimension reportsDimension);
}
