package com.haier.datamart.mapper;

import java.util.List;

import com.haier.datamart.entity.ScanIndexTableRelative;
import com.haier.datamart.entity.SearchIndexDimension;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 指标维度中间表 Mapper 接口
 * </p>
 *
 * @author dsh123
 * @since 2018-05-23
 */
public interface SearchIndexDimensionMapper extends BaseMapper<SearchIndexDimension> {
	List<SearchIndexDimension> findList(SearchIndexDimension searchindexdimension);
	List<SearchIndexDimension> getInclude(String id);
	
	int add(SearchIndexDimension indexDimension);
	
	int updateDelete(SearchIndexDimension indexDimension);
	
     List<SearchIndexDimension> getIndex(String id);
     
     int update(SearchIndexDimension indexDimension);
}
