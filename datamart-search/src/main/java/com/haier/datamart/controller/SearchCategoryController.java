package com.haier.datamart.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.haier.datamart.annotation.Log;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.entity.SearchCategory;
import com.haier.datamart.service.ISearchCategoryService;
import com.haier.datamart.service.impl.SearchCategoryServiceImpl;


/**
 * <p>
 * 分类 前端控制器
 * </p>
 *
 * @author dsh123
 * @since 2018-05-25
 */
@RestController
public class SearchCategoryController {
	@Autowired
	private ISearchCategoryService isearchcategoryservice;

	@GetMapping(value = "/searchCategory/categoryDetail", produces = { "application/json;charset=UTF-8" })
	@Log(description="API接口:/searchCategory/categoryDetail")
	public PublicResult treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<SearchCategory> r=new ArrayList<SearchCategory>();
		List<SearchCategory> list=null;

		
			list =isearchcategoryservice.findList(new SearchCategory());
		
		for(SearchCategory t:list){
			
			if("0".equals(t.getParentId())){
					t.setChildren(SearchCategory.getChildrenByPid(t.getId(), list,true));
					r.add(t);
			}
		}
		return new PublicResult<>(PublicResultConstant.SUCCESS, r);

	}
}

