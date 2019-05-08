package com.haier.datamart.mapper;

import java.util.List;

import com.haier.datamart.entity.SearchTags;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 标签中间表 Mapper 接口
 * </p>
 *
 * @author dsh123
 * @since 2018-05-29
 */
public interface SearchTagsMapper extends BaseMapper<SearchTags> {
	List<SearchTags> getInclude(SearchTags searchTags);

}
