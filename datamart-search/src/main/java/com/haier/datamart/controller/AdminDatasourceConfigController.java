package com.haier.datamart.controller;

import io.swagger.annotations.ApiOperation;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haier.datamart.annotation.Log;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.entity.AdminDataContent;
import com.haier.datamart.entity.AdminDataContentDetail;
import com.haier.datamart.entity.AdminDatasourceConfig;
import com.haier.datamart.entity.AltasNameHelp;
import com.haier.datamart.entity.Dict;
import com.haier.datamart.entity.User;
import com.haier.datamart.service.IAdminDataContentDetailService;
import com.haier.datamart.service.IAdminDataContentService;
import com.haier.datamart.service.IAdminDatasourceConfigService;
import com.haier.datamart.service.IDictService;
import com.haier.datamart.service.ISysUserService;
import com.haier.datamart.service.IUserService;
import com.haier.datamart.utils.GenerationSequenceUtil;
import com.haier.datamart.utils.GetSqlConnection;
import com.haier.datamart.utils.ITableScanUtils;

/**
 * <p>
 * 数据源配置 前端控制器
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-06-07
 */
@Configuration
@Component
@EnableScheduling
@RestController
public class AdminDatasourceConfigController extends BaseController {
	@Autowired
	private IAdminDatasourceConfigService configService;
	@Autowired
	private IAdminDataContentService contentService;
	@Autowired
	private IAdminDataContentDetailService detailService;
	@Autowired
	private IUserService userServiceImpl;
	@Autowired
	private IDictService dictService;
	@Autowired
	private IAdminDatasourceConfigService datasourceConfigService;
	@Autowired
	private ISysUserService iSysUserService;

	/**
	 * 后台-数据库列表
	 * 
	 * @author lixiaoyi
	 * @date 2018年6月13日 下午2:56:40
	 * @TODO
	 */
	@GetMapping(value = "/config/list", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/config/list")
	public Object Configlist(HttpServletRequest request) {
		User user = getLoginUser(request);
		//user.setId(null);
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
		AdminDatasourceConfig config = new AdminDatasourceConfig();
		if (StringUtils.isNotBlank(user.getId())&&!"1".equals(user.getUserType())) {
			config.setCreateBy(user.getId());
		}
		List<AdminDatasourceConfig> configs = configService.getAllbyuid(config);
		PageInfo<AdminDatasourceConfig> page = new PageInfo(configs);

		return new PublicResult<>(PublicResultConstant.SUCCESS, page);
	}

	/**
	 * 后台-数据库新增
	 * 
	 * @author lixiaoyi
	 * @date 2018年6月13日 下午2:56:24
	 * @TODO
	 */
	@GetMapping(value = "/config/add", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/config/add")
	public Object addConfig(HttpServletRequest request,
			HttpServletResponse response, AdminDatasourceConfig config) {
		User user = getLoginUser(request);
		config.setId(GenerationSequenceUtil.getUUID());
		config.setCreateDate(new Date());
		config.setUpdateDate(new Date());
		if (user != null) {
			config.setCreateBy(user.getId());
			config.setUpdateBy(user.getId());
		}


		try {
			GetSqlConnection connection = new GetSqlConnection();
			Connection c = connection.getConn(config);
			if (c != null) {
				AdminDatasourceConfig adminDatasourceConfig = datasourceConfigService
						.getConfig(config);
				if (adminDatasourceConfig != null) {
					return new PublicResult<>(PublicResultConstant.FAILED,
							"数据源已经存在，请重新填写！");
				} else {
					int re = configService.add(config);
					if (re != 0) {
						// 进行表扫描
						/*
						 * new Thread() { public void run() {
						 * 
						 * DatasourceConfigService.toTableScan(config.getId(),
						 * user); } }.start();
						 */
						Thread rthread = new Thread(new ITableScanUtils(
								config.getId(), user, datasourceConfigService));
						rthread.start();
					} else {
						return new PublicResult<>(PublicResultConstant.ERROR, null);
					}

				}
				return new PublicResult<>(PublicResultConstant.SUCCESS, null);
			} else {
				return new PublicResult<>(PublicResultConstant.FAILED, "数据库连接失败！");
			}
		} catch (Exception e) {
			// TODO: handle exception
			return new PublicResult<>(PublicResultConstant.FAILED, e.getMessage());
		}

	}
	@ApiOperation(value = "校验数据库连接是否正常", notes = "校验数据库连接是否正常", httpMethod = "GET")
	@GetMapping(value = "/config/check", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/config/check")
	public Object checkConfig(HttpServletRequest request,
			HttpServletResponse response, AdminDatasourceConfig config) {
		GetSqlConnection connection = new GetSqlConnection();

		try {
			Connection c = connection.getConn(config);
			if (c != null) {
				return new PublicResult<>(PublicResultConstant.SUCCESS, "校验成功！");
			} else {
				return new PublicResult<>(PublicResultConstant.FAILED, "数据库连接异常!");
			}
		} catch (Exception e) {
			// TODO: handle exception
			return new PublicResult<>(PublicResultConstant.FAILED, e.getMessage());
		}

	}

	@GetMapping(value = "/config/regData", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/config/regData")
	public Object regData(HttpServletRequest request,
			AdminDatasourceConfig config) {
		User user = getLoginUser(request);
		GetSqlConnection connection = new GetSqlConnection();
		try {
			Connection c = connection.getConn(config);
			if (c != null) {

				AdminDatasourceConfig adminDatasourceConfig = datasourceConfigService
						.getConfig(config);
				if (adminDatasourceConfig != null) {
					return new PublicResult<>(PublicResultConstant.FAILED,
							"Already existed");
				} else {
					// 进行表扫描
					Thread rthread = new Thread(new ITableScanUtils(config.getId(),
							user, datasourceConfigService));
					rthread.start();

				}

				return new PublicResult<>(PublicResultConstant.SUCCESS, null);
			} else {
				return new PublicResult<>(PublicResultConstant.FAILED, null);
			}
		} catch (Exception e) {
			// TODO: handle exception
			return new PublicResult<>(PublicResultConstant.FAILED, e.getMessage());
		}
		

	}

	@GetMapping(value = "/config/dataType", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/config/dataType")
	public Object DataType() {
		List<Dict> dicts = dictService.getdataType();
		return new PublicResult<>(PublicResultConstant.SUCCESS, dicts);
	}

	/**
	 * 后台-数据库详情即修改回显
	 * 
	 * @author lixiaoyi
	 * @date 2018年6月13日 下午2:56:24
	 * @TODO
	 */
	@GetMapping(value = "/config/details", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/config/details")
	public Object Details(HttpServletRequest request) {
		String id = request.getParameter("id");
		AdminDatasourceConfig config = configService.get(id);
		AdminDataContent content = new AdminDataContent();
		content.setDataSourceId(id);
		List<AdminDataContent> contents = contentService.getAllBy(content);
		config.setTableNum(contents.size());
		int a = 0;
		/*for (AdminDataContent adminDataContent : contents) {
			List<AdminDataContentDetail> details = detailService
					.getAllBy(adminDataContent.getId());
			a += details.size();
		}*/
		 List<AdminDataContentDetail> details=detailService.getAllByDb(id);
		 if(details!=null){
			 a=details.size();
		 }
		config.setCloumnNum(a);
		return new PublicResult<>(PublicResultConstant.SUCCESS, config);
	}

	/**
	 * 后台-数据库修改
	 * 
	 * @author lixiaoyi
	 * @date 2018年6月13日 下午2:56:24
	 * @TODO
	 */
	@GetMapping(value = "/config/update", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/config/update")
	public Object Update(HttpServletRequest request,
			AdminDatasourceConfig config) {
		User user = getLoginUser(request);
		config.setUpdateDate(new Date());
		config.setUpdateBy(user.getId());
		try {
			GetSqlConnection connection = new GetSqlConnection();
			Connection c = connection.getConn(config);
			if (c != null) {
				// 修改
				int re = configService.update(config);
				if (re != 0) {
					// 进行表扫描
					// 修改 只有数据源链接信息发生变化才会重新扫描表
					Thread rthread = new Thread(new ITableScanUtils(
							config.getId(), user, datasourceConfigService));
					rthread.start();
				} else {
					return new PublicResult<>(PublicResultConstant.ERROR, null);
				}

				return new PublicResult<>(PublicResultConstant.SUCCESS, null);
			} else {
				return new PublicResult<>(PublicResultConstant.FAILED, null);
			}
		} catch (Exception e) {
			// TODO: handle exception
			return new PublicResult<>(PublicResultConstant.FAILED, e.getMessage());
		}

	}

	/**
	 * 后台数据库删除
	 * 
	 * @author lixiaoyi
	 * @date 2018年6月13日 下午5:11:47
	 * @TODO
	 */
	@GetMapping(value = "/config/delete", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/config/delete")
	public Object delete(HttpServletRequest request) {
		int re = configService.delete(request.getParameter("id"));

		return new PublicResult<>(PublicResultConstant.SUCCESS, null);
	}

	/**
	 * 后台-表列表
	 * 
	 * @author lixiaoyi
	 * @date 2018年6月13日 下午3:45:02
	 * @TODO
	 */
	@GetMapping(value = "/config/tableList", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/config/tableList")
	public Object Mingxi(HttpServletRequest request) {
		User user = getLoginUser(request);
		String pageNumstr = request.getParameter("paegNum");
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
		AdminDataContent content = new AdminDataContent();
		content.setTableName(request.getParameter("tableName"));
		content.setDataSourceId(request.getParameter("dataId"));
		content.setRemarks(request.getParameter("remarks"));
		if (StringUtils.isNotBlank(user.getId())) {
			content.setCreateBy(user.getId());
		}
		List<AdminDataContent> contents = contentService.getAllBy(content);
		PageInfo<AdminDataContent> page = new PageInfo<AdminDataContent>(
				contents);

		return new PublicResult<>(PublicResultConstant.SUCCESS, page);
	}

	/**
	 * 后台-表更新
	 * 
	 * @author lixiaoyi
	 * @date 2018年6月13日 下午4:51:32
	 * @TODO
	 */
	@GetMapping(value = "/config/tableUpdate", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/config/tableUpdate")
	public Object tableUpdate(AdminDataContent content) {
		content.setUpdateDate(new Date());
		int re = contentService.updateTable(content);

		return new PublicResult<>(PublicResultConstant.SUCCESS, null);
	}

	/**
	 * 后台表更新前回显
	 * 
	 * @author lixiaoyi
	 * @date 2018年6月13日 下午4:51:32
	 * @TODO
	 */
	@GetMapping(value = "/config/tableDetail", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/config/tableDetail")
	public Object tableDetail(HttpServletRequest request) {
		AdminDataContent content = contentService.get(request
				.getParameter("id"));

		return new PublicResult<>(PublicResultConstant.SUCCESS, content);
	}

	/**
	 * 后台表删除
	 * 
	 * @author lixiaoyi
	 * @date 2018年6月13日 下午4:51:32
	 * @TODO
	 */
	@GetMapping(value = "/config/tableDelete", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/config/tableDelete")
	public Object tableDetel(HttpServletRequest request) {
		int re = contentService.delete(request.getParameter("id"));

		return new PublicResult<>(PublicResultConstant.SUCCESS, null);
	}

	/**
	 * 后台-字段详情
	 * 
	 * @author lixiaoyi
	 * @date 2018年6月13日 下午4:51:32
	 * @TODO
	 */
	@GetMapping(value = "/config/cloumnlist", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/config/cloumlist")
	public Object columnList(HttpServletRequest request) {
		User user = getLoginUser(request);
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
		AdminDataContentDetail detail = new AdminDataContentDetail();
		detail.setColumnName(request.getParameter("columnName"));
		detail.setColumnType(request.getParameter("columnType"));
		detail.setRemarks(request.getParameter("remarks"));
		detail.setId(request.getParameter("dataId"));
		detail.setContentId(request.getParameter("contentId"));
		boolean isAdmin=("1".equals(user.getUserType()));

//		if (StringUtils.isNotBlank(user.getId())) {
//			detail.setCreateBy(user.getId());
//		}
		if (!isAdmin) {
			detail.setCreateBy(user.getId());
		}
		
		List<AdminDataContentDetail> details = detailService.getList(detail);
		PageInfo<AdminDataContentDetail> page = new PageInfo<AdminDataContentDetail>(
				details);

		return new PublicResult<>(PublicResultConstant.SUCCESS, page);
	}

	@GetMapping(value = "/config/getAltas", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/config/getAltas")
	public Object altas(HttpServletRequest request) {
		User user = getLoginUser(request);
		String dataId = request.getParameter("dataId");
		AltasNameHelp helps = configService.getbyid(dataId, user.getId());
		return new PublicResult<>(PublicResultConstant.SUCCESS, helps);
	}
	
	/**
	 * 
	 * @time   2018年12月5日 上午11:15:28
	 * @author zuoqb
	 * @todo   获取表中字段明细
	 * @param  @param request
	 * @param  @return
	 * @return_type   Object
	 */
	@ApiOperation(value = "表中字段详情", notes = "表中字段详情", httpMethod = "GET")
	@GetMapping(value = "/config/cloumns", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/config/cloumns")
	public Object cloumns(HttpServletRequest request,@RequestParam(value="contentId") String contentId) {
		AdminDataContentDetail detail=new AdminDataContentDetail();
		detail.setId(contentId);
		List<AdminDataContentDetail> details = detailService.getColumnByContent(detail);
		return new PublicResult<>(PublicResultConstant.SUCCESS, details);
	}

}
