package com.haier.datamart.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.haier.datamart.annotation.Log;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.entity.SearchHotsearch;
import com.haier.datamart.entity.SearchIndex;
import com.haier.datamart.entity.ViewSearch;
import com.haier.datamart.entity.ViewSearchHelp;
import com.haier.datamart.service.ISearchHotsearchService;
import com.haier.datamart.service.ISearchIndexService;
import com.haier.datamart.service.ISearchReportsService;
import com.haier.datamart.service.IViewSearchService;
import com.haier.datamart.utils.ElasticSearchUtil;
import com.haier.datamart.utils.GenerationSequenceUtil;

/**
 * <p>
 * VIEW 前端控制器
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-05-24
 */
@RestController
public class ViewSearchController {
	@Autowired
	private ISearchIndexService isearchindexservice;
	@Autowired
	private IViewSearchService viewSearchService;
	@Autowired
	private ISearchReportsService searchReportsService;
	@Autowired
	private ISearchHotsearchService hotsearchService;

	/**
	 * 全局维护es数据，先全部删除再重新全部维护
	 * 
	 * @author doushuihai
	 * @date 2018年6月29日下午5:28:48
	 * @TODO
	 */
	@GetMapping(value = "/search/Datamaintenance", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/search/Datamaintenance")
	public Object Datamaintenance(HttpServletRequest request) {
		try {
			if (ElasticSearchUtil.getClient() != null) {
				ElasticSearchUtil.deleteAllIndex(ElasticSearchUtil.getClient(),
						"view_search");// 对es下deview_search索引进行全部删除
				ViewSearch viewSearch = new ViewSearch();// 重新维护视图es数据
				List<ViewSearch> viewSearchs = viewSearchService
						.getAll(viewSearch);
				Iterator<ViewSearch> iterator = viewSearchs.iterator();
				while (iterator.hasNext()) {
					ViewSearch search = iterator.next();
					ElasticSearchUtil.addOneRecordById(
							ElasticSearchUtil.getClient(), "view_search",
							"view_search", search.getPk(),
							ElasticSearchUtil.transBean2Map(search));

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED, null);
		}
		return new PublicResult<>(PublicResultConstant.SUCCESS, null);
	}

	/**
	 * 搜索查询
	 * 
	 * @author lixiaoyi
	 * @date 2018年5月24日 下午2:52:27
	 * @TODO
	 */
	@GetMapping(value = "/search/apimingxi", produces = { "application/json;charset=UTF-8" })
	//@Log(description = "API接口:/search/apimingxi")
	public Object getKeyword(HttpServletRequest request) {
		Map<String, Object> result = null;
		ViewSearchHelp viewhelp = new ViewSearchHelp();
		try {

			String keyword = request.getParameter("keyword");
			String pageNumstr = request.getParameter("pageNum");
			String sizestr = request.getParameter("size");
			String type = request.getParameter("type");
			String type1 = request.getParameter("type1");
			//String type3 = request.getParameter("type3");
			String hot = request.getParameter("hot");
			String time = request.getParameter("time");
			int pageNum = 1;
			int pagesize = 10;
			if (StringUtils.isNotBlank(pageNumstr)) {

				pageNum = Integer.parseInt(pageNumstr);
			}
			if (StringUtils.isNotBlank(sizestr)) {

				pagesize = Integer.parseInt(sizestr);
			}

			int index = (pageNum - 1) * pagesize;
			ViewSearch viewSearch = new ViewSearch();
			//viewSearch.setCreateBy("1");
			List<String> keywords = new ArrayList<>();//存储关键字
			if(StringUtil.isNotEmpty(keyword)){
				for (String str : keyword.split(" ")) {//参数用空格分开
					keywords.add(str);
				}
			}
			if(keywords.size()==0){
				keywords = null;
			}
			viewSearch.setKeywords(keywords);
			viewSearch.setPageNum(index);
			viewSearch.setSize(pagesize);
			viewSearch.setCategory1(type);//指标 报表 API类型
			viewSearch.setSubName(type1);//主题域

			if (StringUtils.isNotBlank(keyword)) {
				SearchHotsearch hotsarch = hotsearchService.getOne(keyword);//根据关键字获取热搜实体
				if (hotsarch == null || "".equals(hotsarch)) {
					SearchHotsearch hott = new SearchHotsearch();
					hott.setId(GenerationSequenceUtil.getUUID());
					hott.setKeyword(keyword);
					hott.setNums(1);
					hott.setCreateDate(new Date());
					hott.setUpdateDate(new Date());
					hotsearchService.addhot(hott);//如热搜词不存在,则添加之
				} else {//否则搜索次数加一并更新数据库
					hotsarch.setNums(hotsarch.getNums() + 1);
					hotsearchService.updateOne(hotsarch);
				}

			}
			if (StringUtils.isNotBlank(hot)) {
				viewSearch.setViewNum(1);
			}else{
				viewSearch.setCreateBy("1");
			}
			if (ElasticSearchUtil.getClient() != null) {
				if (StringUtils.isBlank(keyword) && StringUtils.isBlank(type)) {

					result = ElasticSearchUtil.fenye(
							ElasticSearchUtil.getClient(), index, pagesize);
					String count = (String) result.get("count");
					List<ViewSearch> list = (List<ViewSearch>) result
							.get("data");
					viewhelp.setViewSearchs(list);
					viewhelp.setCount(count);

				} else {
					result = ElasticSearchUtil.getByKeyword2(
							ElasticSearchUtil.getClient(), "view_search",
							viewSearch, index, pagesize);
					String count = (String) result.get("count");
					List<ViewSearch> list = (List<ViewSearch>) result
							.get("data");
					viewhelp.setViewSearchs(list);
					viewhelp.setCount(count);
				}
			} else {
				/*List<ViewSearch> viewSearchs = viewSearchService.getBykeyword(viewSearch);//通过视图查询结果
				ViewSearch view = viewSearchService.getCount(viewSearch);*/
				PageHelper.startPage(pageNum, pagesize);
				List<ViewSearch> list = viewSearchService.getByKeywordList(viewSearch);
				PageInfo<ViewSearch> page = new PageInfo(list);
				viewhelp.setViewSearchs(page.getList());
				viewhelp.setCount(page.getTotal()+"");
				
			}

		} catch (Exception e) {
			e.printStackTrace();

			return new PublicResult<>(PublicResultConstant.FAILED, null);
		}
		// JSONObject jsStr = JSONObject.parseObject(result);
		return new PublicResult<>(PublicResultConstant.SUCCESS, viewhelp);
	}

	/**
	 * 获取推荐
	 * 
	 * @author doushuihai
	 * @date 2018年6月23日上午10:53:03
	 * @TODO
	 */
	@GetMapping(value = "/all/tuijian", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/all/tuijian")
	public Object getTuijian(HttpServletRequest request, Model model) {
		List<ViewSearch> vList = new ArrayList<ViewSearch>();
		String result = "";
		try {
			String type = request.getParameter("type");

			// result=esUtil.fenye(index,pagesize);
			if (StringUtils.isNotBlank(type)) {
				if (ElasticSearchUtil.getClient() != null) {
					vList = ElasticSearchUtil.getTuiJian(
							ElasticSearchUtil.getClient(), "view_search", type,
							0, 3);
				} else {
					vList = viewSearchService.gettuijian(type);
				}

			} else {
				if (ElasticSearchUtil.getClient() != null) {
					vList = ElasticSearchUtil.getTallTuiJian(
							ElasticSearchUtil.getClient(), "view_search", 0, 5);
				} else {
					vList = viewSearchService.getalltuijian();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED, null);
		}
		return new PublicResult<>(PublicResultConstant.SUCCESS, vList);
	}

	@GetMapping(value = "/changeVZSNum", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/changeVZSNum")
	public PublicResult changeVZSNum(HttpServletRequest request,
			HttpServletResponse httpServletResponse) {

		try {
			String numType = request.getParameter("numType");
			String id = request.getParameter("id");
			String type = request.getParameter("type");
			String Esid = "";
			if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(type)
					&& StringUtils.isNotBlank(numType)) {

				viewSearchService.changeVZSNum(type, id, numType);
				if ("index".equals(type)) {
					Esid = "search_index_" + id;

				} else if ("api".equals(type)) {
					Esid = "search_api_" + id;
				} else if ("report".equals(type)) {
					Esid = "search_reports_" + id;
				}
				SearchIndex entity = isearchindexservice.get(id);
				if (ElasticSearchUtil.getClient() != null) {
					ElasticSearchUtil.addOneRecordById(
							ElasticSearchUtil.getClient(), "view_search",
							"view_search", Esid,
							ElasticSearchUtil.transBean2Map(entity));
				}
			} else {
				return new PublicResult<>(PublicResultConstant.FAILED, null);
			}

		} catch (Exception e) {
			e.printStackTrace();

			return new PublicResult<>(PublicResultConstant.FAILED, null);
		}

		return new PublicResult<>(PublicResultConstant.SUCCESS, null);
	}

	/**
	 * 首页新增数据
	 * 
	 * @author lixiaoyi
	 * @date 2018年6月15日 上午9:46:06
	 * @TODO
	 */
	@GetMapping(value = "/main/add", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/main/add")
	public Object Newadd() {

		List<ViewSearch> viewSearchs = viewSearchService.getAdd();

		return new PublicResult<>(PublicResultConstant.SUCCESS, viewSearchs);

	}

}
