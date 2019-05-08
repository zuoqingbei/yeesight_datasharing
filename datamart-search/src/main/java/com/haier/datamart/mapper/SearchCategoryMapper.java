package com.haier.datamart.mapper;

import java.util.List;

import com.haier.datamart.entity.SearchCategory;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 分类表 Mapper 接口
 * </p>
 *
 * @author dsh123
 * @since 2018-05-28
 */
public interface SearchCategoryMapper extends BaseMapper<SearchCategory> {
	List<SearchCategory> findList(SearchCategory searchcategory);
}
