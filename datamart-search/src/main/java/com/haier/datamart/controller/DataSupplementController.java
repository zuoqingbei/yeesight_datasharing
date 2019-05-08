package com.haier.datamart.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.util.StringUtil;
import com.haier.datamart.annotation.Log;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.entity.AdminDatasourceConfig;
import com.haier.datamart.entity.EnteringTableSettingDetail;
import com.haier.datamart.entity.EnteringTableSettingHeader;
import com.haier.datamart.entity.EnteringTableSettingInfo;
import com.haier.datamart.entity.SysProduct;
import com.haier.datamart.entity.User;
import com.haier.datamart.service.IAdminDatasourceConfigService;
import com.haier.datamart.service.IEnteringTableSettingHeaderService;
import com.haier.datamart.service.IEnteringTableSettingInfoService;
import com.haier.datamart.service.ISearchIndexService;
import com.haier.datamart.service.IUserInterfaceManager;
import com.haier.datamart.utils.AddFormulaAndDataValidationForExcel;
import com.haier.datamart.utils.Cron;
import com.haier.datamart.utils.ExcelConnection;
import com.haier.datamart.utils.ExportExcel;
import com.haier.datamart.utils.InterfaceDataUtils;

/**
 * <p>
 * VIEW 前端控制器
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-05-24
 */
@RestController
public class DataSupplementController extends BaseController {

	@Autowired
	private IEnteringTableSettingInfoService infoService;
	@Autowired
	private ISearchIndexService isearchindexservice;
	@Autowired
	private IAdminDatasourceConfigService adminDatasourceConfigService;
	@Autowired
	private IUserInterfaceManager UIMservice;
	@Autowired
	public IEnteringTableSettingHeaderService iEnteringTableSettingHeaderService;
	/**
	 * 补录模板导出
	 * 
	 * @author doushuihai
	 * @date 2018年7月2日上午11:29:18
	 * @TODO
	 */
	@GetMapping(value = "/data/toDataExport", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/data/toDataExport")
	public Object toDataExport(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="settingId",required = true) String settingId,
			@RequestParam(value="isExportData",required = false) String isExportData,//是否导出外部接口数据
			@RequestParam(value="params",required = false) String params) {
		
		EnteringTableSettingInfo settingInfo = infoService.getById(settingId);
		if (settingInfo == null) {
			return new PublicResult<>(PublicResultConstant.FAILED, "无此指标信息");
		}
		String entrySettingId = settingInfo.getId();
		List<EnteringTableSettingDetail> list = infoService
				.getBySettingId(entrySettingId,"0");

		if (CollectionUtils.isEmpty(list)) {
			return new PublicResult<>(PublicResultConstant.FAILED,
					"根据此指标id没有找到表字段详细信息");
		}

		List<String> dataList = new ArrayList<String>();
		// 用于存放公式和其对应的位置(顺序)
		Map<Integer, String> map = new HashMap<Integer, String>();
		Map<Integer, Integer> maping = new HashMap<Integer, Integer>();
		List<List<String>> interfaceData=new ArrayList<List<String>>();//外部统一接口存放数据
		Integer defineOrder;
		Integer relOrder;// 真实顺序
		String excelGs;// 传入的excel公式
		String[] idParameters;// 公式中包含的id参数
		// 存放公式中包含的id参数定义位置与真实位置的映射关系
		// Map<Integer,Integer> maping = new HashMap<Integer,Integer>();
		for (EnteringTableSettingDetail settingDetail : list) {

			excelGs = settingDetail.getExcelGs();

			if (excelGs != null && excelGs.length() != 0) {
				// 公式自适应位置
				relOrder = infoService.getRelOrder(settingDetail.getOrderNo(),
						entrySettingId) + 1;
				System.out.println(excelGs + "的真实顺序:" + relOrder);

				map.put(relOrder, excelGs);

				// excelGs中的id参数位置自适应
				try {
					idParameters = AddFormulaAndDataValidationForExcel
							.getIdFroFormula(excelGs);
				} catch (Exception e) {
					e.printStackTrace();
					return new PublicResult<>(PublicResultConstant.FAILED,
							"公式格式错误!请重新输入");
				}
				if (idParameters != null && idParameters.length != 0) {
					for (int i = 0; i < idParameters.length; i++) {
						System.out.println("解析后的id参数,idParameters" + i + ":"
								+ idParameters[i]);
						defineOrder = infoService.getOrderById(idParameters[i],
								entrySettingId);
						if (defineOrder != null) {
							System.out.println("根据id参数获取order_no:"
									+ defineOrder);
							relOrder = infoService.getRelOrder(defineOrder,
									entrySettingId) + 1;

							/**
							 * 老版存放maping.put(Integer.parseInt(idParameters[i]),
							 * relOrder); 新版存放maping.put(relOrder, relOrder);
							 */
							maping.put(relOrder, relOrder);
							System.out.println("参数中id顺序映射:(" + idParameters[i]
									+ ":" + relOrder + ")");
						}
					}
				}
			}
			//存放对应的外部数据
			if(StringUtils.isNotBlank(isExportData)&&"true".equals(isExportData)){
				List<String> result=new ArrayList<String>();
				result=InterfaceDataUtils.getInterfaceData(settingDetail, params);
				interfaceData.add(result);
			}
			dataList.add(settingDetail.getExcelColName());
			// System.out.println("2222:" + settingDetail.getExcelColName());
		}
		// System.out.println(dataList);
		//获取补录配置header信息
		List<EnteringTableSettingHeader> headers = iEnteringTableSettingHeaderService.getHeadersByEnteringId(settingInfo.getId());
		String[] toBeStored = dataList.toArray(new String[dataList.size()]);

		ExportExcel ex = new ExportExcel(settingInfo.getDescs(), toBeStored,
				interfaceData, response, maping, map,headers,list);
		try {
			ex.export();
		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED, "excel导出失败!");
		}
		return new PublicResult<>(PublicResultConstant.SUCCESS, null);
	}

	@GetMapping(value = "/data/getDataColDetail", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/data/getDataColDetail")
	public Object getDataColDetail(HttpServletRequest request,
			HttpServletResponse response) {
		String settingId = request.getParameter("settingId");// 666
																// 改为通过settingId获取参数
		EnteringTableSettingInfo settingInfo = infoService.getById(settingId);
		if (settingInfo == null) {
			return new PublicResult<>(PublicResultConstant.FAILED, "无此指标信息");
		}
		List<EnteringTableSettingDetail> list = infoService
				.getBySettingId(settingInfo.getId());
		if (CollectionUtils.isEmpty(list)) {
			return new PublicResult<>(PublicResultConstant.FAILED,
					"根据此指标id没有找到表字段详细信息");
		}
		return new PublicResult<>(PublicResultConstant.SUCCESS, list);

	}

	@GetMapping(value = "/data/getDataDetail", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/data/getDataDetail")
	public PublicResult getDataDetail(HttpServletRequest request,
			HttpServletResponse response) {
		User user = getLoginUser(request);
		String keyword = request.getParameter("keyword");
		String pageNumstr = request.getParameter("pageNum");
		String sizestr = request.getParameter("size");
		int pageNum = 1;
		int pagesize = 10;
		if (StringUtils.isNotBlank(pageNumstr)) {
			pageNum = Integer.parseInt(pageNumstr);
		}
		if (StringUtils.isNotBlank(sizestr)) {
			pagesize = Integer.parseInt(sizestr);
		}
		int index = (pageNum - 1) * pagesize;
		String settingId = request.getParameter("settingId");// 666
																// 改为通过settingId获取参数
		EnteringTableSettingInfo settingInfo = infoService.getById(settingId);
		if (settingInfo == null) {
			return new PublicResult<>(PublicResultConstant.FAILED, "无此指标信息");
		}

		List<EnteringTableSettingDetail> list = infoService
				.getBySettingId(settingInfo.getId());
		if (CollectionUtils.isEmpty(list)) {
			return new PublicResult<>(PublicResultConstant.FAILED,
					"根据此指标id没有找到表字段详细信息");
		}

		String importTable = settingInfo.getName();
		List detailList = new ArrayList<HashMap<String, String>>();

		// 根据补录表的id查询info表的补录时间字段
		String sql = "select * from " + importTable + " where 1=1 ";
		System.out.println(list.size() + "------------------------");
		if (StringUtils.isNotBlank(keyword)) {
			sql = "select * from " + importTable + " where 1=1 and ";
			for (int i = 0; i < list.size(); i++) {
				if (i == (list.size() - 1)) {
					sql += " " + list.get(i).getColName() + " like '%"
							+ keyword + "%' ";
				} else {
					sql += " " + list.get(i).getColName() + " like '%"
							+ keyword + "%' or ";
				}
			}

		}
		/*if (user != null && StringUtils.isNotBlank(user.getId())) {
			sql += "and create_by='" + user.getId() + "' ";
		}*/
		sql += " and del_flag=0 order by create_date desc ";					/*order by create_date desc 
															 * limit "+index+",+
															 * pagesize666
															 */
		System.out.println(sql + "==========================================");
		try {

			AdminDatasourceConfig config = adminDatasourceConfigService
					.get(settingInfo.getDatasourceConfigId());
			Connection conn = ExcelConnection.getConn(config.getDbUrl(),
					config.getDbName(), config.getDbPassword());
			PreparedStatement pstmt = null;
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				HashMap<String, String> map = new HashMap<String, String>();
				for (EnteringTableSettingDetail detail : list) {
					String colName = detail.getColName();
					String colValue = rs.getString(colName);
					if(StringUtil.isNotEmpty(colValue)&&colValue.endsWith(".0")){
						map.put(detail.getExcelColName(), colValue.substring(0, colValue.length()-2));
					}else{
						map.put(detail.getExcelColName(), colValue);
					}
					
					map.put("colValue", colValue);
					map.put("colName", detail.getColName());
					map.put("excelColName", detail.getExcelColName());
				}
				map.put("key", rs.getString("id")==null?"":rs.getString("id"));
				map.put("is_success", rs.getString("is_success")==null?"":rs.getString("is_success"));
				map.put("error_col", rs.getString("error_col")==null?"":rs.getString("error_col"));
				map.put("error_msg", rs.getString("error_msg")==null?"":rs.getString("error_msg"));
				/*Date data = rs.getDate("create_date");
				Date data2 = settingInfo.getValidBeginTime();
				if (data2 == null) {
					map.put("edit", "0");// 可编辑
				} else {
					if (data.getTime() >= data2.getTime()) {
						map.put("edit", "0");// 可编辑

					} else if (data.getTime() < data2.getTime()) {
						map.put("edit", "1");// 不可编辑

					}
				}*/

				detailList.add(map);
			}
			conn.close();
			System.out.println(detailList.size() + ":" + detailList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED, null);
		}
		return new PublicResult<>(PublicResultConstant.SUCCESS, detailList);

	}
	
	/**
	 * 
	 * @time   2018年9月18日 上午11:50:33
	 * @author zuoqb
	 * @todo  校验补录配置名称是否存在
	 */
	@RequestMapping(value = "/data/checkSettingName", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.GET)
	@Log(description = "API接口:/data/checkSettingName ")
	public Object checkSettingName(HttpServletRequest request,@RequestParam(value="name",required = true) String name){
		if(StringUtils.isBlank(name)){
			return new PublicResult<>(PublicResultConstant.INVALID_PARAM_EMPTY,"请填写配置名称!");
		}
		//根据配置名称查询补录配置
		EnteringTableSettingInfo checkSetting=infoService.getSettingInfoByDesc(name);
		if(checkSetting!=null){
			return new PublicResult<>(PublicResultConstant.FAILED,"配置名称‘"+checkSetting.getDescs()+"’已存在！");
		}else{
			return new PublicResult<>(PublicResultConstant.SUCCESS,"校验成功，可以操作！");
		}
	}
	
	/**
	 * 
	 * @time   2018年9月18日 上午11:50:33
	 * @author zuoqb
	 * @todo  校验补录配置表再库中是否存在
	 */
	@RequestMapping(value = "/data/checkTableByDb", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.GET)
	@Log(description = "API接口:/data/checkTableByDb ")
	public Object checkTableByDb(HttpServletRequest request,@RequestParam(value="tableName",required = true) String tableName,
			@RequestParam(value="datasourceConfigId",required = true) String datasourceConfigId){
		if(StringUtils.isBlank(datasourceConfigId)||StringUtils.isBlank(tableName)){
			return new PublicResult<>(PublicResultConstant.INVALID_PARAM_EMPTY,"请传入校验参数!");
		}
		EnteringTableSettingInfo byName = infoService.getByName(
				tableName, datasourceConfigId);
		//根据配置名称查询补录配置
		if (byName != null) {
			return new PublicResult<>(PublicResultConstant.FAILED,"已存在此表名！");
		}else{
			return new PublicResult<>(PublicResultConstant.SUCCESS,"校验成功，可以操作！");
		}
	}
	
	/**
	 * 
	 * @time   2018年9月18日 上午11:50:33
	 * @author zuoqb
	 * @todo   保存补录配置
	 * @param  @param request
	 * @param  @param settingInfo
	 * @param  @return
	 * @param  @throws ParseException
	 * @return_type   PublicResult
	 */
	@RequestMapping(value = "/data/datamaintenance", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@Log(description = "API接口:/data/datamaintenance ")
	public PublicResult datamaintenance(HttpServletRequest request,
			@RequestBody EnteringTableSettingInfo settingInfo)
			throws ParseException {
		User user = getLoginUser(request);
		if (user != null && StringUtils.isNotBlank(user.getId())) {
			settingInfo.setCreateBy(user.getId());
			settingInfo.setUpdateBy(user.getId());
		}
		// settingInfo.setValidBeginTime(format.parse(settingInfo.getValidBeginTimeStr()));
		// settingInfo.setValidEndTime(format.parse(settingInfo.getValidEndTimeStr()));
		settingInfo.setCreateDate(new Date());
		settingInfo.setUpdateDate(new Date());
		String settingInfoTableName = settingInfo.getName();

		String settingId = settingInfo.getId();// 666 改为通过settingId获取参数

		EnteringTableSettingInfo byName = infoService.getByName(
				settingInfoTableName, settingInfo.getDatasourceConfigId());
		//根据配置名称查询补录配置
		EnteringTableSettingInfo checkSetting=infoService.getSettingInfoByDesc(settingInfo.getDescs());
		EnteringTableSettingInfo preSettingInfo = new EnteringTableSettingInfo();
		AdminDatasourceConfig preConfig = new AdminDatasourceConfig();
		AdminDatasourceConfig preConfigBak = new AdminDatasourceConfig();
		if (StringUtils.isBlank(settingInfo.getId())) {
			if(checkSetting!=null){
				return new PublicResult<>(PublicResultConstant.FAILED,
						"配置名称‘"+checkSetting.getDescs()+"’已存在！");
			}
			// 新增时才校验表明 指标是否之前维护过
			if (byName != null) {
				return new PublicResult<>(PublicResultConstant.FAILED,
						"已存在此表名！");
			}
			EnteringTableSettingInfo byindexId = infoService.getById(settingId);
			if (byindexId != null) {
				return new PublicResult<>(PublicResultConstant.FAILED,
						"已经维护过补录数据表！");
			}
		}else{
			if(checkSetting!=null&&!checkSetting.getId().equals(settingInfo.getId())){
				return new PublicResult<>(PublicResultConstant.FAILED,
						"配置名称‘"+checkSetting.getDescs()+"’已存在！");
			}
			//获取修改前数据源
			preSettingInfo = new EnteringTableSettingInfo();
			if (StringUtils.isNotBlank(settingInfo.getId())) {
				preSettingInfo = infoService.getById(settingInfo.getId());//通过id获取配置信息
				preSettingInfo.setEtDetail(infoService
						.getBySettingId(preSettingInfo.getId()));
			}
			
			if(StringUtil.isNotEmpty(preSettingInfo.getDatasourceConfigId())){
				 preConfig = adminDatasourceConfigService.get(preSettingInfo.getDatasourceConfigId());//获取数据源信息
				 Connection conn1 = ExcelConnection.getConn(preConfig.getDbUrl(),preConfig.getDbName(),preConfig.getDbPassword());
				if(conn1==null){
					return new PublicResult<>(PublicResultConstant.FAILED, "此配置数据源信息连接失败,请联系管理员!");
				}else{
					try {
						if(!conn1.isClosed())
						conn1.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				 preConfigBak = adminDatasourceConfigService.get(preSettingInfo.getBakDatasourceConfigId());//获取备份数据源信息
				 Connection conn2 = ExcelConnection.getConn(preConfigBak.getDbUrl(),preConfigBak.getDbName(),preConfigBak.getDbPassword());
				if(conn2==null){
					return new PublicResult<>(PublicResultConstant.FAILED, "此配置备份数据源信息连接失败,请联系管理员!");
				}else{
					try {
						if(!conn2.isClosed())
						conn2.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

				
			}else{
				return new PublicResult<>(PublicResultConstant.FAILED, "此配置数据源信息不存在,请联系管理员!");
			}
		}
		try {
			 
			
			
			AdminDatasourceConfig config = adminDatasourceConfigService
					.get(settingInfo.getDatasourceConfigId());//获取数据源信息
			 //备份数据源默认值
			AdminDatasourceConfig configBak = new AdminDatasourceConfig();
			if(StringUtil.isNotEmpty(settingInfo.getBakDatasourceConfigId())){
				configBak = adminDatasourceConfigService.get(settingInfo.getBakDatasourceConfigId());
			}else{
				configBak = adminDatasourceConfigService.get(ExcelConnection.defaultDataSourceId);//获取备份数据源信息
				settingInfo.setBakDatasourceConfigId(ExcelConnection.defaultDataSourceId);
			}
			if(configBak==null){
				return new PublicResult<>(PublicResultConstant.FAILED, "此配置备份数据源信息未被找到,请联系管理员!"); 
			}
	        //备份表名
			String bakTableName = settingInfo.getBakTableName();
			if(StringUtil.isEmpty(bakTableName)){
				bakTableName = settingInfo.getName()+"_bak";
				settingInfo.setBakTableName(bakTableName);
			}
			
			
	        if(StringUtil.isEmpty(settingInfo.getBakDatasourceConfigId())){//默认备份表数据源
				
			}
	        
			if (!"mysql".equals(config.getDbType())) {
				return new PublicResult<>(PublicResultConstant.ERROR,
						"暂时只能导入mysql");
			} else {
				boolean optSuccess = ExcelConnection.creatEnteringTable(preSettingInfo, settingInfo,bakTableName, 
						config.getDbUrl(),config.getDbName(), config.getDbPassword(),
						configBak.getDbUrl(),configBak.getDbName(),configBak.getDbPassword(),
						preConfig.getDbUrl(),preConfig.getDbName(),preConfig.getDbPassword(),
						preConfigBak.getDbUrl(),preConfigBak.getDbName(),preConfigBak.getDbPassword());//创建实体表
				if (optSuccess) {
					int a = infoService.save(settingInfo);
					if (a == 3) {
						return new PublicResult<>(PublicResultConstant.FAILED,
								"cron表达式不符合规则");
					}
				} else {
					return new PublicResult<>(PublicResultConstant.FAILED, null);
				}
				return new PublicResult<>(PublicResultConstant.SUCCESS, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED, null);
		}

	}

	@GetMapping(value = "/data/getUpdateSettingInfoDetail", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/data/getUpdateSettingInfoDetail")
	public Object getUpdateSettingInfoDetail(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		EnteringTableSettingInfo settingInfo = infoService.getById(id);
		if (settingInfo == null) {
			return new PublicResult<>(PublicResultConstant.FAILED, "无此配置信息");
		}
		List<EnteringTableSettingDetail> list = infoService
				.getBySettingId(settingInfo.getId());
		/*for (EnteringTableSettingDetail enteringTableSettingDetail : list) {
			String indexName;
			try {
				indexName = isearchindexservice.get(
						enteringTableSettingDetail.getIndexId()).getName();
				enteringTableSettingDetail.setIndexName(indexName);
			} catch (Exception e) {
				enteringTableSettingDetail.setIndexName("未查到指标名");
			}
		}*/

		if (CollectionUtils.isEmpty(list)) {
			return new PublicResult<>(PublicResultConstant.FAILED,
					"根据此指标id没有找到表字段详细信息");
		}

		settingInfo.setEtDetail(list);
		return new PublicResult<>(PublicResultConstant.SUCCESS, settingInfo);
	}

	@GetMapping(value = "/data/getSettingInfoDetail", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/data/getSettingInfoDetail")
	public Object getSettingInfoDetail(HttpServletRequest request,
			HttpServletResponse response) throws ParseException {
		User user = getLoginUser(request);
		/* if(user.getId()==null){
			return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,"登录过期,请重新登录!");
		} */		
		String desc = request.getParameter("desc");
		String name = request.getParameter("name");
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");
		String pageNumstr = request.getParameter("pageNum");
		String sizestr = request.getParameter("size");
		String productId = request.getParameter("productId");
		int pageNum = 1;
		int pagesize = 10;
		if (StringUtils.isNotBlank(pageNumstr)) {
			pageNum = Integer.parseInt(pageNumstr);
		}
		if (StringUtils.isNotBlank(sizestr)) {
			pagesize = Integer.parseInt(sizestr);
		}
		int index = (pageNum - 1) * pagesize;
		EnteringTableSettingInfo settingInfo = new EnteringTableSettingInfo();
		settingInfo.setDescs(desc);
		settingInfo.setName(name);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (StringUtils.isNotBlank(startdate)) {
			settingInfo.setValidBeginTime(format.parse(startdate));
		}
		if (StringUtils.isNotBlank(enddate)) {
			settingInfo.setValidEndTime(format.parse(enddate));
		}
		settingInfo.setPageNum(index);
		settingInfo.setSize(pagesize);
		settingInfo.setProductId(productId);
		List<EnteringTableSettingInfo> settingInfoDetailList = infoService
				.getSettingInfoDetail(settingInfo);
	
		return new PublicResult<>(PublicResultConstant.SUCCESS,
				settingInfoDetailList);
	
	}

	@GetMapping(value = "/data/deleteSettingInfo", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/data/deleteSettingInfo")
	public Object deleteSettingInfo(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");

		try {

			EnteringTableSettingInfo info = infoService.getById(id);
			infoService.changestatus(id, "0");
			Cron.remove(info.getName());

			infoService.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED, null);
		}

		return new PublicResult<>(PublicResultConstant.SUCCESS, null);
	}

	/**
	 * 重启项目 调度所有定时任务
	 * 
	 * @author lixiaoyi
	 * @date 2018年8月4日 下午3:19:25
	 * @TODO
	 */
	@GetMapping(value = "/timed/task", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/timed/task")
	public Object timetask(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List<EnteringTableSettingInfo> infos = infoService.getAll();
			for (EnteringTableSettingInfo settingInfo : infos) {
				if (settingInfo.getStartCron() == null
						|| settingInfo.getEndCron() == null
						|| StringUtils.isBlank(settingInfo.getStartCron())) {
					continue;
				}
				// 循环所有配置表，调度起定时任务
				Cron.task(settingInfo.getId(), settingInfo.getStartCron(),
						settingInfo.getName() + UUID.randomUUID(), "1");
				Cron.task(settingInfo.getId(), settingInfo.getEndCron(),
						settingInfo.getName() + UUID.randomUUID(), "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED, null);
		}

		return new PublicResult<>(PublicResultConstant.SUCCESS, null);
	}

	/**
	 * 控制补录时间
	 * 
	 * @author lixiaoyi
	 * @date 2018年8月6日 下午12:35:11
	 * @TODO
	 */
	@GetMapping(value = "/controller/timedtask", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/controller/timedtask")
	public Object controllertimetask(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			SchedulerFactory schedulerFactory = new StdSchedulerFactory();
			Scheduler scheduler = schedulerFactory.getScheduler();
			EnteringTableSettingInfo info = infoService.getById(request
					.getParameter("id"));
			if (info.getStartCron() == null) {
				return new PublicResult<>(PublicResultConstant.ERROR,
						"没有配置调度信息");
			}
			// 开关状态
			if ("0".equals(request.getParameter("status"))) {// 开

				infoService.changestatus(request.getParameter("id"), "1");
				Cron.task(request.getParameter("id"), info.getStartCron(),
						info.getName() + UUID.randomUUID(), "1");
			} else {

				infoService.changestatus(request.getParameter("id"), "0");

				scheduler.deleteJob(new JobKey(info.getName()));// 删除任

			}

		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED, null);
		}

		return new PublicResult<>(PublicResultConstant.SUCCESS, null);
	}

	/**
	 * 查看是否能导入
	 * 
	 * @author lixiaoyi
	 * @date 2018年8月11日 上午10:04:36
	 * @TODO
	 */
	@GetMapping(value = "/look/status", produces = { "application/json;charset=UTF-8" })
	// @Log(description = "API接口:/look/status")
	public Object lookstatus(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String id = request.getParameter("id");
			EnteringTableSettingInfo info = infoService.getById(id);
			if (info == null) {
				return new PublicResult<>(PublicResultConstant.FAILED, "无此表");
			} else {
				if ("0".equals(info.getStatus())) {
					return new PublicResult<>(PublicResultConstant.FAILED,
							"不允许补录");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED, null);
		}

		return new PublicResult<>(PublicResultConstant.SUCCESS, "允许补录");
	}

	/**
	 * 获当项目列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/data/getProductList", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/data/getProductList ")
	public Object getProductList(HttpServletRequest request, String isAll) {
		try {
			User user = getLoginUser(request);
			if (user.getId() == null) {
				return new PublicResult<>(PublicResultConstant.UNAUTHORIZED,
						"登录过期,请重新登录!");
			}
			List<SysProduct> sysProductList = new ArrayList<>();
			if ("all".equals(isAll) || "1".equals(user.getId())) {// 若isAll标识为all,则查询所有项目
				sysProductList = UIMservice.getAllProductList();
			} else {// 若没有isAll标识为all,则查询用户下项目
				sysProductList = UIMservice.getProductList(user);
			}
			return new PublicResult<>(PublicResultConstant.SUCCESS,
					sysProductList);
		} catch (Exception e) {
			System.out.println("自定义异常原因:" + e.getMessage());
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED,
					e.getMessage());
		}
	}

}
