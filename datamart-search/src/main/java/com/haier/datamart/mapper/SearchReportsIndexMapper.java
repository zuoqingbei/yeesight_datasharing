package com.haier.datamart.mapper;

import java.util.List;

import com.haier.datamart.entity.SearchReportsIndex;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 报表指标间表 Mapper 接口
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-05-23
 */
public interface SearchReportsIndexMapper extends BaseMapper<SearchReportsIndex> {
   int  add(SearchReportsIndex reportsIndex);
   List<SearchReportsIndex> getidByplat(String plat);
}
