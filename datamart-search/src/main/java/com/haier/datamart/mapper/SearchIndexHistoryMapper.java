package com.haier.datamart.mapper;

import java.util.List;

import com.haier.datamart.entity.SearchIndexHistory;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 指标历史表 Mapper 接口
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-07-09
 */
public interface SearchIndexHistoryMapper extends BaseMapper<SearchIndexHistory> {
  int  add(SearchIndexHistory history); 
   List<SearchIndexHistory> getbytime(String indexid);
}
