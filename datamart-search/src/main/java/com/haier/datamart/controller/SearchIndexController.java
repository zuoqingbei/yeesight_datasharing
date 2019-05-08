package com.haier.datamart.controller;

import io.swagger.annotations.ApiOperation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.stat.TableStat.Column;
import com.alibaba.druid.util.JdbcConstants;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.haier.datamart.annotation.Log;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.entity.AdminDataContent;
import com.haier.datamart.entity.AdminDataContentDetail;
import com.haier.datamart.entity.AdminDatasourceConfig;
import com.haier.datamart.entity.AltasNameHelp;
import com.haier.datamart.entity.Dict;
import com.haier.datamart.entity.ScanSubjectArea;
import com.haier.datamart.entity.SearchIndex;
import com.haier.datamart.entity.SearchReports;
import com.haier.datamart.entity.SearchReportsIndex;
import com.haier.datamart.entity.SysHelp;
import com.haier.datamart.entity.User;
import com.haier.datamart.entity.ViewSearch;
import com.haier.datamart.mapper.SearchIndexDimensionMapper;
import com.haier.datamart.mapper.SearchReportsIndexMapper;
import com.haier.datamart.mapper.SearchReportsMapper;
import com.haier.datamart.service.IAdminDataContentDetailService;
import com.haier.datamart.service.IAdminDataContentService;
import com.haier.datamart.service.IAdminDatasourceConfigService;
import com.haier.datamart.service.IDictService;
import com.haier.datamart.service.IScanSubjectAreaService;
import com.haier.datamart.service.ISearchIndexService;
import com.haier.datamart.service.ISearchReportsService;
import com.haier.datamart.service.IUserService;
import com.haier.datamart.service.IViewSearchService;
import com.haier.datamart.utils.ElasticSearchUtil;
import com.haier.datamart.utils.ExcelConnection;
import com.haier.datamart.utils.GenerationSequenceUtil;

/**
 * <p>
 * 指标表 前端控制器
 * </p>
 *
 * @author dsh123
 * @since 2018-05-23
 */
@RestController
public class SearchIndexController extends BaseController {
	@Autowired
	private ISearchIndexService isearchindexservice;
	@Autowired
	private IViewSearchService iViewSearchService;
	@Autowired
	private IUserService userServiceImpl;
	@Autowired
	private IDictService dictServiceImpl;
	@Autowired
	private IScanSubjectAreaService scanSubjectAreaService;
	@Autowired
	private IAdminDatasourceConfigService configService;
	@Autowired
	private IAdminDataContentService contentSercice;
	@Autowired
	private IAdminDataContentDetailService detailService;
	@Autowired
	private SearchReportsMapper reportMapper;
	@Autowired
	private ISearchReportsService searchReportsService;

	@Autowired
	private SearchIndexDimensionMapper searchIndexDimensionMapper;

	@Autowired
	private SearchReportsIndexMapper searchReportsIndexMapper;

	/**
	 * 指标详细页面
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value = "/searchIndex/indexDetail", produces = { "application/json;charset=UTF-8" })
	// @Log(description="API接口:/searchIndex/indexDetail")
	public PublicResult indexDetail(HttpServletRequest request,
			HttpServletResponse response) {
		
		/*User user = getLoginUser(request);
		if(user.getId()==null){
			return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
		}*/
		String id = request.getParameter("id");
		SearchIndex entity = null;
		if (StringUtils.isNotBlank(id)) {
			try {
				entity = isearchindexservice.get(id);
				if (entity != null) {
					List<SearchReports> reports = searchReportsService
							.getReportByIndexId(id);
					entity.setReports(reports);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		if (entity == null) {
			entity = new SearchIndex();
		}

		return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
	}

	/**
	 * 后台-新增指标
	 * 
	 * @author lixiaoyi
	 * @date 2018年6月12日 上午10:15:33
	 * @TODO
	 */

	@RequestMapping(value = "/searchIndex/addIndex", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@Log(description = "API接口:/searchIndex/addIndex")
	public Object addIndex(HttpServletRequest request,
			@RequestBody SearchIndex index) {
		/*
		 * JSONObject jsonObject=
		 * JSONObject.fromObject(request.getParameter("jsonStr")); SearchIndex
		 * index=(SearchIndex) jsonObject.toBean(jsonObject, SearchIndex.class);
		 */
		User user = getLoginUser(request);
		/*if(user.getId()==null){
			return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
		}*/
		if (user != null && StringUtils.isNotBlank(user.getId())) {
			index.setCreateBy(user.getId());
			index.setUpdateBy(user.getId());
		}
		index.setCreateDate(new Date());
		index.setUpdateDate(new Date());
		index.setId(GenerationSequenceUtil.getUUID());
		index.setCategory1("指标");
		SearchIndex index2 = isearchindexservice.getName(index.getName());
		int re = 0;
		if (index2 != null) {
			isearchindexservice.delete(index2.getId());
		} else {
			List<Dict> dicts = dictServiceImpl.getAll("index_code");
			for (Dict dict : dicts) {
				if (dict.getDescription().equals(index.getAreaName())) {
					// 循环 查询指标中的编码是否重复
					for (int i = 0; i < 10000; i++) {
						// 生成随机4位数
						int a = (int) ((Math.random() * 9 + 1) * 1000);
						String indexCode = dict.getValue() + String.valueOf(a);
						// 查询数据库是否有一样的
						List<SearchIndex> indexsCode = isearchindexservice
								.getbyCode(indexCode);
						if (indexsCode.size() == 0) {
							index.setCode(indexCode);
							break;
						} else {
							continue;
						}
					}
					break;
				} else {
					continue;
				}
			}

			re = isearchindexservice.addIndex(index);
		}
		if (re <= 0) {
			return new PublicResult<>(PublicResultConstant.ERROR, null);
		} else {
			ViewSearch search = new ViewSearch();
			search.setPk("search_index_" + index.getId());
			ViewSearch viewsearch = iViewSearchService.getByPK(search);
			if (ElasticSearchUtil.getClient() != null) {
				ElasticSearchUtil.addOneRecordById(
						ElasticSearchUtil.getClient(), "view_search",
						"view_search", "search_index_" + index.getId(),
						ElasticSearchUtil.transBean2Map(viewsearch));
			}
			return new PublicResult<>(PublicResultConstant.SUCCESS, null);
		}

	}

	/**
	 * 后台-删除指标
	 * 
	 * @author lixiaoyi
	 * @date 2018年6月12日 上午10:16:19
	 * @TODO
	 */
	@GetMapping(value = "/searchIndex/delete", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/searchIndex/delete")
	public Object delete(HttpServletRequest request) {
		User user = getLoginUser(request);
		if(user.getId()==null){
			return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
		}
		String indexId = request.getParameter("indexId");
		int a = isearchindexservice.delete(indexId);
		if (a <= 0) {
			return new PublicResult<>(PublicResultConstant.ERROR, null);
		} else {
			if (ElasticSearchUtil.getClient() != null) {
				ElasticSearchUtil.deleteOneRecordById(
						ElasticSearchUtil.getClient(), "view_search",
						"view_search", "search_index_" + indexId);
			}
			return new PublicResult<>(PublicResultConstant.SUCCESS, null);
		}

	}

	@GetMapping(value = "/searchIndex/getAltas", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/searchIndex/getAltas")
	public Object altas(HttpServletRequest request) {
		User user = getLoginUser(request);
		/*if(user.getId()==null){
			return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
		}*/
		String indexId = request.getParameter("id");

		AltasNameHelp helps = isearchindexservice.getbyid(indexId);
		return new PublicResult<>(PublicResultConstant.SUCCESS, helps);
	}

	/**
	 * 后台-新增回显维度
	 * 
	 * @author lixiaoyi
	 * @date 2018年6月13日 上午11:00:39
	 * @TODO
	 */
	@GetMapping(value = "/searchIndex/reDict", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/searchIndex/reDict")
	public Object reWeidu(HttpServletRequest request) {
		/*User user = getLoginUser(request);
		if(user.getId()==null){
			return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
		}*/
		List<Dict> dicts = dictServiceImpl.getAll(request.getParameter("type"));
		return new PublicResult<>(PublicResultConstant.SUCCESS, dicts);
	}

	/**
	 * 后台 新增-回显主题域
	 * 
	 * @author lixiaoyi
	 * @date 2018年6月13日 下午1:17:18
	 * @TODO
	 */
	@GetMapping(value = "/searchIndex/reArea", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/searchIndex/reArea")
	public Object reArea(HttpServletRequest request) {
		/*User user = getLoginUser(request);
		if(user.getId()==null){
			return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
		}*/
		List<ScanSubjectArea> area = scanSubjectAreaService.getAll();
		return new PublicResult<>(PublicResultConstant.SUCCESS, area);
	}

	/**
	 * 后台 新增-回显数据库
	 * 
	 * @author lixiaoyi
	 * @date 2018年6月13日 下午1:25:28
	 * @TODO
	 */
	@GetMapping(value = "/searchIndex/reConfig", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/searchIndex/reConfig")
	public Object reConfig(HttpServletRequest request) {
		User user = getLoginUser(request);
		if(user.getId()==null){
			return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
		}
		List<AdminDatasourceConfig> configs = configService.getAll();
		return new PublicResult<>(PublicResultConstant.SUCCESS, configs);
	}

	/**
	 * 后台-新增-回显表（必选先选择了数据库）
	 * 
	 * @author lixiaoyi
	 * @date 2018年6月13日 下午1:25:15
	 * @TODO
	 */
	@GetMapping(value = "/searchIndex/reTable", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/searchIndex/reTable")
	public Object reTable(HttpServletRequest request) {
		/*User user = getLoginUser(request);
		if(user.getId()==null){
			return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
		}*/
		AdminDataContent content = new AdminDataContent();
		content.setDataSourceId(request.getParameter("dataId"));
		List<AdminDataContent> table = contentSercice.getAllByName(content);
		return new PublicResult<>(PublicResultConstant.SUCCESS, table);
	}

	/**
	 * 后台 新增-回显表字段（必选先选择了表）
	 * 
	 * @author lixiaoyi
	 * @date 2018年6月13日 下午1:25:41
	 * @TODO
	 */
	@GetMapping(value = "/searchIndex/reCloumn", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/searchIndex/reColumn")
	public Object reColumn(HttpServletRequest request) {
		/*User user = getLoginUser(request);
		if(user.getId()==null){
			return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
		}*/
		List<AdminDataContentDetail> details = detailService.getAllBy(request
				.getParameter("tId"));
		return new PublicResult<>(PublicResultConstant.SUCCESS, details);
	}

	/**
	 * 后台--指标列表
	 * 
	 * @author lixiaoyi
	 * @date 2018年6月14日 上午9:42:31
	 * @TODO
	 */
	@GetMapping(value = "/searchIndex/list", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/searchIndex/list")
	public Object list(HttpServletRequest request) {
		User user = getLoginUser(request);
		if(user.getId()==null){
			return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
		}
		String pageNumstr = request.getParameter("pageNum");
		String pageSizeStr = request.getParameter("pageSize");

		int pageNum = 1;
		int pagesize = 10;
		if (StringUtils.isNotBlank(pageNumstr)) {

			pageNum = Integer.parseInt(pageNumstr);
		}
		if (StringUtils.isNotBlank(pageSizeStr)) {

			pagesize = Integer.parseInt(pageSizeStr);
		}

		PageHelper.startPage(pageNum, pagesize);
		SearchIndex index = new SearchIndex();
		index.setName(request.getParameter("name"));
		index.setCode(request.getParameter("code"));
		index.setAreaId(request.getParameter("areaId"));
		index.setEntering(request.getParameter("entering"));
		if (user != null) {
			if (!"1".equals(user.getUserType())) {
				index.setCreateBy(user.getId());
			}
		}
		List<SearchIndex> indexs = isearchindexservice.getAll(index);
		PageInfo<SearchIndex> page = new PageInfo<SearchIndex>(indexs);
		return new PublicResult<>(PublicResultConstant.SUCCESS, page);
	}

	/**
	 * 后台--指标列表修改
	 * 
	 * @author lixiaoyi
	 * @date 2018年6月14日 上午9:42:31
	 * @TODO
	 */
	@RequestMapping(value = "/searchIndex/update", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@Log(description = "API接口:/searchIndex/update")
	public Object update(HttpServletRequest request,
			@RequestBody SearchIndex index) {
		User user = getLoginUser(request);
		/*if(user.getId()==null){
			return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
		}*/
		/*
		 * JSONObject jsonObject=
		 * JSONObject.fromObject(request.getParameter("jsonStr")); SearchIndex
		 * index=(SearchIndex) jsonObject.toBean(jsonObject, SearchIndex.class);
		 */
		/*if(user.getId()==null){
			return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
		}*/
		if (user != null && StringUtils.isNotBlank(user.getId())) {
			index.setCreateBy(user.getId());
			index.setUpdateBy(user.getId());
		}

		index.setUpdateDate(new Date());
		index.setCategory1("指标");

		int re = isearchindexservice.update(index);
		if (re <= 0) {
			return new PublicResult<>(PublicResultConstant.ERROR, null);
		} else {
			if (ElasticSearchUtil.getClient() != null) {
				ViewSearch search = new ViewSearch();
				search.setPk("search_index_" + index.getId());
				ViewSearch viewsearch = iViewSearchService.getByPK(search);
				ElasticSearchUtil.addOneRecordById(
						ElasticSearchUtil.getClient(), "view_search",
						"view_search", "search_index_" + index.getId(),
						ElasticSearchUtil.transBean2Map(viewsearch));
			}
			return new PublicResult<>(PublicResultConstant.SUCCESS, null);
		}

	}

	@GetMapping(value = "/searchIndex/details", produces = { "application/json;charset=UTF-8" })
	// @Log(description = "API接口:/searchIndex/details")
	public Object indexDetails(HttpServletRequest request) {
		String indexId = request.getParameter("id");
		/*User user = getLoginUser(request);
		if(user.getId()==null){
			return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
		}*/

		SearchIndex index = isearchindexservice.details(indexId);

		return new PublicResult<>(PublicResultConstant.SUCCESS, index);
	}

	/**
	 * 根据有规则的指标编码字符串获取其对应的指标实体
	 * 
	 * @author lzg 2018/8/20
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/searchIndex/getEntriesByIndexCodes", produces = { "application/json;charset=UTF-8" })
	// @Log(description = "API接口:/searchIndex/getEntriesByIndexCodes")
	public Object getEntriesByIndexCodes(String indexCodes,HttpServletRequest request) {
		/*User user = getLoginUser(request);
		if(user.getId()==null){
			return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
		}*/
		try {
			List<String> list = new ArrayList<>();
			if (StringUtil.isNotEmpty(indexCodes)) {
				for (String indexCode : indexCodes.trim().split(",")) {
					list.add(indexCode);
				}
			}
			List<SearchIndex> entries = new ArrayList<>();
			if (list != null && list.size() != 0) {
				entries = isearchindexservice.getEntriesByIndexCodes(list);
				for (SearchIndex index : entries) {
					index.setReports(searchReportsService
							.getReportByIndexId(index.getId()));
				}
			}
			return new PublicResult<>(PublicResultConstant.SUCCESS, entries);
		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED, "发生异常!");
		}
	}

	/**
	 * 指标中原数据库与目标数据库通过sql解析
	 * 
	 * @author lixiaoyi
	 * @date 2018年8月27日 上午9:31:04
	 * @TODO
	 */
	@GetMapping(value = "/searchIndex/sql", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/searchIndex/sql")
	public Object sql(HttpServletRequest request) {
		/*User user = getLoginUser(request);
		if(user.getId()==null){
			return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
		}*/
		String sql = request.getParameter("sql");
		String dbType = JdbcConstants.MYSQL;

		// 格式化输出
		String result = SQLUtils.format(sql, dbType);
		System.out.println(result); // 缺省大写格式
		List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);

		// 解析出的独立语句的个数
		System.out.println("size is:" + stmtList.size());
		List<String> colum = new ArrayList<String>();
		List<String> value = new ArrayList<String>();
		for (int i = 0; i < stmtList.size(); i++) {

			SQLStatement stmt = stmtList.get(i);
			MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
			stmt.accept(visitor);
			// 获取字段名称
			Collection<Column> column = visitor.getColumns();

			// 解析成功获取所有字段
			for (Column column2 : column) {
				String col = column2.getName();
				colum.add(col);
			}

		}
		// 对源数据进行jdbc查询出结果集
		String CONNECTION_URL = request.getParameter("ydburl");
		String user1 = request.getParameter("ydbName");
		String pwd = request.getParameter("ypassword");
		String sqls = "select * from " + request.getParameter("showTable")
				+ " where 1=1";
		Connection conn = null;
		PreparedStatement dropPstmt = null;
		ResultSet rs = null;
		conn = ExcelConnection.getConn(CONNECTION_URL, user1, pwd);
		try {
			dropPstmt = (PreparedStatement) conn.prepareStatement(sql);
			rs = dropPstmt.executeQuery();
			if (rs.first()) {
				// 循环列名 取值
				for (String str : colum) {
					String values = rs.getString(str);
					// value.add(values);
					sqls += " and " + str + "='" + values + "'";
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// 对目标数据进行jdbc查询出结果集
		String mUrl = request.getParameter("mdburl");
		String muser = request.getParameter("mdbName");
		String mpwd = request.getParameter("mpassword");
		Connection mconn = null;
		PreparedStatement mPstmt = null;
		ResultSet mrs = null;
		mconn = ExcelConnection.getConn(mUrl, muser, mpwd);

		try {
			mPstmt = (PreparedStatement) mconn.prepareStatement(sqls);
			mrs = mPstmt.executeQuery();
			if (mrs.next()) {
				return new PublicResult<>(PublicResultConstant.SUCCESS, "匹配成功");
			}

		} catch (SQLException e) {

			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.ERROR, "错误");
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return new PublicResult<>(PublicResultConstant.SUCCESS, null);
	}

	public static void main(String[] args) {
		String sql = "SELECT t.`name`,i.id,i.num,i.custom_id from credit_order_info i LEFT JOIN credit_report_type t on i.report_type=t.id LEFT JOIN(SELECT * from credit_agent) c on 1=1 ";
		String dbType = JdbcConstants.MYSQL;
		// 格式化输出
		String result = SQLUtils.format(sql, dbType);
		System.out.println(result); // 缺省大写格式
		List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);
		// 解析出的独立语句的个数
		System.out.println("size is:" + stmtList.size());
		for (int i = 0; i < stmtList.size(); i++) {

			SQLStatement stmt = stmtList.get(i);
			MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
			stmt.accept(visitor);

			Collection<Column> column = visitor.getColumns();
			List<String> colum = new ArrayList<String>();
			for (Column column2 : column) {
				String col = column2.getName();
				System.out.println(col);
				colum.add(col);
			}
		}
	}
	
	

	/**
	 * 
	 * @Description: 获取系统及系统下的指标，报表--指标管理-指标统计
	 * @date 2018年9月4日 上午10:36:55
	 * @version V1.0
	 * @return
	 */
	@RequestMapping(value = "/getSys/all", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/getSYS/all")
	public Object addplat(String indexCodes,HttpServletRequest request) {
		/*User user = getLoginUser(request);
		if(user.getId()==null){
			return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
		}*/
		List<SysHelp> sysHelps = new ArrayList<SysHelp>();
		try {
			// 查询系统 ，及系统下的指标，报表
			List<Dict> dicts = dictServiceImpl.getAll("userSystem");
			// 循环系统 找对应系统下的指标，报表
			for (Dict dict : dicts) {
				SysHelp sysHelp = new SysHelp();
				sysHelp.setDict(dict);
				List<SearchIndex> indexs = isearchindexservice.getbySYS(dict
						.getId());
				if (indexs == null) {
					List<SearchIndex> ind = null;
					sysHelp.setSearchIndexs(ind);
					sysHelp.setIndexsize(0);
				} else {
					sysHelp.setSearchIndexs(indexs);
					sysHelp.setIndexsize(indexs.size());
				}

				List<SearchReports> reports = reportMapper.getBysys(dict
						.getValue());
				if (reports == null) {
					List<SearchReports> repo = null;
					sysHelp.setReports(repo);
					sysHelp.setReportsize(0);
				} else {
					sysHelp.setReports(reports);
					sysHelp.setReportsize(reports.size());
					for (SearchReports searchReports : reports) {
						List<SearchIndex> searchIndexs = isearchindexservice
								.getbyReport(searchReports.getId());
						searchReports.setIndexs(searchIndexs);
						searchReports.setSize(searchIndexs.size());
					}
				}

				sysHelps.add(sysHelp);
			}

			return new PublicResult<>(PublicResultConstant.SUCCESS, sysHelps);
		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED, "发生异常!");
		}
	}

	/**
	 * 
	 * @Description: 系统下报表 平台 指标个数-指标统计使用
	 * @date 2018年10月18日 上午10:45:17
	 * @author: lxy
	 * @version V1.0
	 * @return
	 */
	@GetMapping(value = "/sys/catalogue", produces = { "application/json;charset=UTF-8" })
	// @Log(description = "API接口:/searchIndex/details")
	public Object catalogue(HttpServletRequest request) {
		/*User user = getLoginUser(request);
		if(user.getId()==null){
			return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
		}*/
		String sys = request.getParameter("sys");
		String plat = request.getParameter("plat");
		String rep = request.getParameter("report");
		// 查询系统 ，及系统下的指标，报表 加检索条件
		List<Dict> dicts = dictServiceImpl.getAll("userSystem");
		// 用来接收加了检索条件后的返回值
		List<Dict> result = new ArrayList<Dict>();
		for (Dict dict : dicts) {
			if (StringUtils.isNotBlank(sys)) {
				if (dict.getValue().indexOf(sys) == -1) {
					continue;
				}
			}
			// 查平台
			List<SearchIndex> platIndexs = isearchindexservice.getplat();
			List<SearchIndex> replat = new ArrayList<SearchIndex>();
			for (SearchIndex searchIndex : platIndexs) {
				if (StringUtils.isNotBlank(plat)) {
					if (searchIndex.getPlat().indexOf(plat) == -1) {
						continue;
					}
				}
				// 平台下报表id
				List<SearchReportsIndex> reportsIndexs = new ArrayList<SearchReportsIndex>();
				if (searchIndex != null && searchIndex.getPlat() != null
						&& StringUtils.isNotBlank(searchIndex.getPlat())) {
					reportsIndexs = searchReportsIndexMapper
							.getidByplat(searchIndex.getPlat());
					// 加检索条件后的接收正确返回值
					String[] idS = new String[20];
					int i = 0;
					for (SearchReportsIndex index : reportsIndexs) {
						idS[i] = index.getReportId();
						i++;
					}
					// 查报表名字
					List<SearchReports> reports = reportMapper.getidBYplat(idS);
					// 加检索条件后报表名字
					List<SearchReports> resultreport = new ArrayList<SearchReports>();
					for (SearchReports searchReports : reports) {
						if (StringUtils.isNotBlank(rep)) {
							if (searchReports.getName().indexOf(rep) == -1) {
								continue;
							}
						}
						List<SearchIndex> searchIndexs = isearchindexservice
								.getbyReport(searchReports.getId());
						searchReports.setViewNum(searchIndexs.size());
						searchReports.setIndexs(searchIndexs);
						resultreport.add(searchReports);
					}
					searchIndex.setReports(resultreport);
					replat.add(searchIndex);
				}

			}
			dict.setPlat(replat);
			result.add(dict);
		}

		return new PublicResult<>(PublicResultConstant.SUCCESS, result);
	}

	/**
	 * 
	 * @Description: 报表下系统
	 * @date 2018年10月18日 上午10:45:04
	 * @author: lxy
	 * @version V1.0
	 * @return
	 */
	@GetMapping(value = "/searchIndex/byreport", produces = { "application/json;charset=UTF-8" })
	// @Log(description = "API接口:/searchIndex/details")
	public Object indexByreport(HttpServletRequest request) {
		/*User user = getLoginUser(request);
		if(user.getId()==null){
			return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
		}*/
		String rep = request.getParameter("report");
		String indexname = request.getParameter("index");
		String pageNumstr = request.getParameter("pageNum");
		String pageSizeStr = request.getParameter("pageSize");
		int pageNum = 1;
		int pagesize = 10;
		if (StringUtils.isNotBlank(pageNumstr)) {
			pageNum = Integer.parseInt(pageNumstr);
		}
		if (StringUtils.isNotBlank(pageSizeStr)) {

			pagesize = Integer.parseInt(pageSizeStr);
		}

		PageHelper.startPage(pageNum, pagesize);
		List<Dict> dicts = dictServiceImpl.getAll("userSystem");
		List<SearchReports> reports = null;
		for (Dict dict : dicts) {

			if (StringUtils.isNotBlank(rep)) {
				reports = reportMapper.getBysysandName(dict.getValue(), rep);
			} else {
				reports = reportMapper.getBysys(dict.getValue());
			}

			for (SearchReports searchReports : reports) {
				List<SearchIndex> searchIndexs = null;
				if (StringUtils.isNotBlank(indexname)) {
					searchIndexs = isearchindexservice.getbyReportandName(
							searchReports.getId(), indexname);
				} else {
					searchIndexs = isearchindexservice
							.getbyReport(searchReports.getId());
				}
				searchReports.setIndexs(searchIndexs);
			}
		}

		PageInfo<SearchReports> page = new PageInfo<SearchReports>(reports);
		return new PublicResult<>(PublicResultConstant.SUCCESS, page);
	}
	
	
	
	/**
	 * 
	 * @time   2018年11月21日 上午9:56:50
	 * @author zuoqb
	 * @todo   查询系统-平台-指标
	 * @param  @param request
	 * @param  @return
	 * @return_type   Object
	 */
	@GetMapping(value = "/sys/getSystemPlatIndex", produces = { "application/json;charset=UTF-8" })
	// @Log(description = "API接口:/searchIndex/details")
	public Object getSystemPlatIndex(HttpServletRequest request) {
		// 查询系统 ，及系统下的指标，报表 加检索条件
		List<Dict> dicts = dictServiceImpl.getAll("userSystem");
		// 用来接收加了检索条件后的返回值
		List<Dict> result = new ArrayList<Dict>();
		for (Dict dict : dicts) {
			// 查平台
			List<SearchIndex> plats = isearchindexservice.getplat();
			for (SearchIndex plat : plats) {
				// 平台下报表id
				List<SearchReportsIndex> reportsIndexs = new ArrayList<SearchReportsIndex>();
				List<String> reportId=new ArrayList<String>();
				if (plat != null && plat.getPlat() != null
						&& StringUtils.isNotBlank(plat.getPlat())) {
					reportsIndexs = searchReportsIndexMapper.getidByplat(plat.getPlat());
					for(SearchReportsIndex i:reportsIndexs){
						reportId.add(i.getReportId());
					}
					//查询报表
					List<SearchReports> reports=new ArrayList<SearchReports>();
					if(reportId.size()>0){
						reports=searchReportsService.selectBatchIds(reportId);
						plat.setReports(reports);
					}
					//指标
					List<SearchIndex> platIndex=new ArrayList<SearchIndex>();
					SearchIndex i=new SearchIndex();
					i.setPlat(plat.getPlat());
					platIndex=isearchindexservice.getAll(i);
					plat.setIndexs(platIndex);
				}

			}
			dict.setPlat(plats);
			result.add(dict);
		}

		return new PublicResult<>(PublicResultConstant.SUCCESS, result);
	}
	
	/**
	 * 后台--指标列表
	 * 
	 * @author lixiaoyi
	 * @date 2018年6月14日 上午9:42:31
	 * @TODO
	 */
	@GetMapping(value = "/searchIndex/allIndexs", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/searchIndex/allIndexs")
	public Object allIndexs(HttpServletRequest request) {
		User user = getLoginUser(request);
		if(user.getId()==null){
			return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
		}
		List<ScanSubjectArea> areas = scanSubjectAreaService.getAll();
		for(ScanSubjectArea area:areas){
			SearchIndex index = new SearchIndex();
			if (user != null) {
				if (!"1".equals(user.getUserType())) {
					index.setCreateBy(user.getId());
				}
			}
			index.setAreaId(area.getId());
			List<SearchIndex> indexs = isearchindexservice.getAll(index);
			area.setIndexs(indexs);
		}
		return new PublicResult<>(PublicResultConstant.SUCCESS, areas);
	}
	/**
	 * 
	 * @time   2018年12月5日 下午4:24:59
	 * @author zuoqb
	 * @todo  查询主题域-指标
	 * @param  @param request
	 * @param  @return
	 * @return_type   Object
	 */
	@ApiOperation(value = "查询主题域-指标", notes = "查询主题域-指标", httpMethod = "GET")
	@GetMapping(value = "/searchIndex/getBySubject", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/searchIndex/getBySubject")
	public Object getBySubject(HttpServletRequest request) {
		return new PublicResult<>(PublicResultConstant.SUCCESS, scanSubjectAreaService.getAllSubject());
	}

}
