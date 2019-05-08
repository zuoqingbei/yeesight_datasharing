package com.haier.datamart.mapper;

import com.haier.datamart.entity.SearchReportsDimension;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 报表维度中间表 Mapper 接口
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-05-23
 */
public interface SearchReportsDimensionMapper extends BaseMapper<SearchReportsDimension> {
  int add(SearchReportsDimension reportsDimension);
}
