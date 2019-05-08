package com.haier.datamart.service.impl;

import java.util.List;

import com.haier.datamart.entity.SearchCategory;
import com.haier.datamart.mapper.SearchCategoryMapper;
import com.haier.datamart.service.ISearchCategoryService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 分类表 服务实现类
 * </p>
 *
 * @author dsh123
 * @since 2018-05-28
 */
@Service
public class SearchCategoryServiceImpl extends ServiceImpl<SearchCategoryMapper, SearchCategory> implements ISearchCategoryService {
	@Autowired
	private SearchCategoryMapper categoryMapper;
	public List<SearchCategory> findList(SearchCategory searchcategory){
		System.out.println(searchcategory);
		return categoryMapper.findList(searchcategory);
	}
}
