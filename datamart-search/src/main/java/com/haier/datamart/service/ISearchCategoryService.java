package com.haier.datamart.service;

import java.util.List;

import com.haier.datamart.entity.SearchCategory;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 分类表 服务类
 * </p>
 *
 * @author dsh123
 * @since 2018-05-28
 */
public interface ISearchCategoryService extends IService<SearchCategory> {
	List<SearchCategory> findList(SearchCategory searchcategory);
}
