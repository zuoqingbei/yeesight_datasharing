package com.haier.datamart.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haier.datamart.annotation.Log;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.entity.SearchHotsearch;
import com.haier.datamart.service.ISearchHotsearchService;


/**
 * <p>
 * 热搜表 前端控制器
 * </p>
 *
 * @author dsh123
 * @since 2018-05-25
 */
@RestController
public class SearchHotsearchController {
	@Autowired
	private ISearchHotsearchService searchService;


	/**
	 * 热搜
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value = "/hot/search/list", produces = { "application/json;charset=UTF-8" })
	@Log(description="API接口:/hot/search/list")
	public PublicResult searchHotResult(Model model,HttpServletRequest request, HttpServletResponse response){
		List<SearchHotsearch> list=null;
	try {
		list=searchService.findList();
		System.out.println("========================="+list);
		model.addAttribute("hotsearch", list);
	} catch (Exception e) {
		return new PublicResult<>(PublicResultConstant.FAILED, null);
	}
		return new PublicResult<>(PublicResultConstant.SUCCESS, list);
	}
}

