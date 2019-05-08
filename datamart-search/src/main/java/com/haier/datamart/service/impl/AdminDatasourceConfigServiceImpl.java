package com.haier.datamart.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.haier.datamart.entity.AdminDataContent;
import com.haier.datamart.entity.AdminDataContentDetail;
import com.haier.datamart.entity.AdminDatasourceConfig;
import com.haier.datamart.entity.AltasHelp;
import com.haier.datamart.entity.AltasNameHelp;
import com.haier.datamart.entity.NameHelp;
import com.haier.datamart.entity.TableScanError;
import com.haier.datamart.entity.User;
import com.haier.datamart.mapper.AdminDataContentDetailMapper;
import com.haier.datamart.mapper.AdminDataContentMapper;
import com.haier.datamart.mapper.AdminDatasourceConfigMapper;
import com.haier.datamart.service.IAdminDataContentDetailService;
import com.haier.datamart.service.IAdminDataContentService;
import com.haier.datamart.service.IAdminDatasourceConfigService;
import com.haier.datamart.service.ITableScanErrorService;
import com.haier.datamart.service.IUserService;
import com.haier.datamart.utils.DataContent;
import com.haier.datamart.utils.DataContentDetail;
import com.haier.datamart.utils.GetSqlConnection;
import com.haier.datamart.utils.ScanToInsertError;
import com.haier.datamart.utils.ScanToUpdateDetail;
import com.haier.datamart.utils.ScanToinsertDetail;

/**
 * <p>
 * 数据源配置 服务实现类
 * </p>
 *
 * @author dsh123
 * @since 2018-05-28
 */
@Service
public class AdminDatasourceConfigServiceImpl extends ServiceImpl<AdminDatasourceConfigMapper, AdminDatasourceConfig> implements IAdminDatasourceConfigService {
	@Autowired
	private AdminDatasourceConfigMapper adminDatasourceConfigMapper;
	@Autowired
	private AdminDataContentMapper contentMapper;
	@Autowired
	private AdminDataContentDetailMapper detailMapper;
	
	@Autowired
	private IAdminDatasourceConfigService  DatasourceConfigService;
	@Autowired
	private IAdminDataContentService aAdminDataContentService;
	@Autowired
	private IAdminDataContentDetailService contentDetailService;
	@Autowired
	private IUserService userServiceImpl;
	@Autowired
	private ITableScanErrorService EerrorService ;
	
	public AdminDatasourceConfig get(String id){
		return adminDatasourceConfigMapper.get(id);
	}
	@Override
	public AdminDatasourceConfig getConfig(AdminDatasourceConfig config) {
		// TODO Auto-generated method stub
		return adminDatasourceConfigMapper.getConfig(config);
	}
	@Override
	public AdminDatasourceConfig getConfigByDb(AdminDatasourceConfig config) {
		// TODO Auto-generated method stub
		return adminDatasourceConfigMapper.getConfigByDb(config);
	}
	@Override
	public List<AdminDatasourceConfig> getAll() {
		// TODO Auto-generated method stub
		return adminDatasourceConfigMapper.getAll();
	}
	@Override
	public int add(AdminDatasourceConfig config) {
		// TODO Auto-generated method stub
		return adminDatasourceConfigMapper.add(config);
	}
	@Override
	public int update(AdminDatasourceConfig config) {
		// TODO Auto-generated method stub
		return adminDatasourceConfigMapper.update(config);
	}
	@Override
	public int delete(String id) {
	  int t=  contentMapper.deletebyConfigID(id);
		//删详情
	  int d=	 detailMapper.deletebyconfigID(id);
		return adminDatasourceConfigMapper.delete(id);
	}
	@Override
	public AltasNameHelp getbyid(String id,String uid) {
		AltasNameHelp nameHelps=new AltasNameHelp();
		List<AltasHelp> helps=new ArrayList<AltasHelp>();
		List<NameHelp> name=new ArrayList<NameHelp>();
		
		if (StringUtils.isNotBlank(id)) {
			AdminDataContent content=new AdminDataContent();
			content.setDataSourceId(id);
			  List<AdminDataContent> dataContents= contentMapper.getAllby(content);
			  for (AdminDataContent adminDataContent : dataContents) {
				  AltasHelp altasHelp=new AltasHelp();
				       altasHelp.setSouce("T"+adminDataContent.getId());
				       altasHelp.setTarget(id);
				       helps.add(altasHelp);
				   NameHelp help=new NameHelp();
				    help.setId("T"+adminDataContent.getId());
				    help.setName(adminDataContent.getTableName());
				    name.add(help);
				    List<AdminDataContentDetail> details=    detailMapper.getByCid(adminDataContent.getId());
				    for (AdminDataContentDetail adminDataContentDetail : details) {
				    	 AltasHelp altasHelp2=new AltasHelp();
					       altasHelp2.setSouce("C"+adminDataContentDetail.getId());
					       altasHelp2.setTarget("T"+adminDataContent.getId());
					       helps.add(altasHelp2);
					   NameHelp help2=new NameHelp();
					    help2.setId("C"+adminDataContentDetail.getId());
					    help2.setName(adminDataContentDetail.getColumnName());
					    name.add(help2);
					}
			}
			  AdminDatasourceConfig config=  adminDatasourceConfigMapper.get(id);
			  NameHelp help3=new NameHelp();
			    help3.setId(config.getId());
			    help3.setName(config.getName());
			    name.add(help3);
			 nameHelps.setHelpsList(helps);
			 nameHelps.setNameList(name);
			
		}else {
			if (StringUtils.isNotBlank(uid)) {
				AdminDatasourceConfig con=new AdminDatasourceConfig();
				  con.setCreateBy(uid);
			List<AdminDatasourceConfig> configs=	adminDatasourceConfigMapper.getAllbyuid(con);
			if (configs!=null) {
				for (AdminDatasourceConfig adminDatasourceConfig : configs) {
					AdminDataContent content=new AdminDataContent();
					content.setDataSourceId(adminDatasourceConfig.getId());
					  List<AdminDataContent> dataContents= contentMapper.getAllby(content);
					  for (AdminDataContent adminDataContent : dataContents) {
						  AltasHelp altasHelp=new AltasHelp();
						       altasHelp.setSouce("T"+adminDataContent.getId());
						       altasHelp.setTarget(adminDatasourceConfig.getId());
						       helps.add(altasHelp);
						   NameHelp help=new NameHelp();
						    help.setId("T"+adminDataContent.getId());
						    help.setName(adminDataContent.getTableName());
						    name.add(help);
						    List<AdminDataContentDetail> details=    detailMapper.getByCid(adminDataContent.getId());
						    for (AdminDataContentDetail adminDataContentDetail : details) {
						    	 AltasHelp altasHelp2=new AltasHelp();
							       altasHelp2.setSouce("C"+adminDataContentDetail.getId());
							       altasHelp2.setTarget("T"+adminDataContent.getId());
							       helps.add(altasHelp2);
							   NameHelp help2=new NameHelp();
							    help2.setId("C"+adminDataContentDetail.getId());
							    help2.setName(adminDataContentDetail.getColumnName());
							    name.add(help2);
							}
					}
					  AdminDatasourceConfig config=  adminDatasourceConfigMapper.get(adminDatasourceConfig.getId());
					  NameHelp help3=new NameHelp();
					    help3.setId(config.getId());
					    help3.setName(config.getName());
					    name.add(help3);
					 nameHelps.setHelpsList(helps);
					 nameHelps.setNameList(name);
					
				}
			}
			}
		}
		return nameHelps;
	}
	
	/**
	 * 
	* @author doushuihai  
	* @date 2018年6月20日下午1:11:10  
	* @TODO 表扫描工具类
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean toTableScan(String id, User user) {
		if(StringUtils.isNotBlank(id)){
			AdminDatasourceConfig datasourceConfig = DatasourceConfigService.get(id);
			DataContent dataContent = new DataContent();
			DataContentDetail dataContentDetail2 = new DataContentDetail();
			List<AdminDataContent> tableList = new ArrayList<AdminDataContent>();// 获取新增的表名
			List<TableScanError> errors = new ArrayList<TableScanError>();// 获取异常信息
			List<AdminDataContent> updatetableList = new ArrayList<AdminDataContent>();// 获取已存在的表名
			List<AdminDataContentDetail> dataContentDetailList = new ArrayList<AdminDataContentDetail>();// 获取新增的表信息
			List<AdminDataContentDetail> updatedataContentDetailList = new ArrayList<AdminDataContentDetail>();// 获取已存在的表信息
			List<AdminDataContentDetail> updatedataContentDetailListTemp = new ArrayList<AdminDataContentDetail>();// 获取已存在的表信息
			Connection conn=null;
			try {
			    conn = new GetSqlConnection().getConn(datasourceConfig);
				AdminDataContent admindatacontent = new AdminDataContent();
				admindatacontent.setDataSourceId(id);
				List<AdminDataContent> JXcontentList = aAdminDataContentService.findListByScan2(admindatacontent);
				if (user != null) {
					datasourceConfig.setCreateBy(user.getId());
					datasourceConfig.setUpdateBy(user.getId());
				}
				tableList = dataContent.getDataContent(datasourceConfig,conn);// 获取所有表
				if (CollectionUtils.isEmpty(JXcontentList)) {
					if (CollectionUtils.isNotEmpty(tableList)) {
						aAdminDataContentService.insertByBatch(tableList);// 对admin_data_content表进行维护
					}
					// 获取所有表字段信息
					if (CollectionUtils.isNotEmpty(tableList)) {
						//conn = new GetSqlConnection().getConn(datasourceConfig);
						Map<String, List> map = dataContentDetail2
								.getDataContentDetail(datasourceConfig,conn, tableList);
						dataContentDetailList = map.get("contentDetailsList");
						errors = map.get("errorsList");
						
					}

					if (CollectionUtils.isNotEmpty(dataContentDetailList)) {
						System.out.println("开始多线程插入=================");
						insertDetailByXiancheng(dataContentDetailList);
						//contentDetailService.insertByBatch(dataContentDetailList);
					}
					if (CollectionUtils.isNotEmpty(errors)) {
						Iterator<TableScanError> it = errors.iterator();
						while(it.hasNext()){
							TableScanError error = it.next();
							List<TableScanError> list=EerrorService.selectForValidate(error);
						    if(CollectionUtils.isNotEmpty(list)){
						        it.remove();
						    }
						}
						inserErrorByXiancheng(errors);
						//EerrorService.insertByBatch(errors);
					}

				} else {
					List<AdminDataContent> temp = new ArrayList<AdminDataContent>();

					for (AdminDataContent a : JXcontentList) {

						for (AdminDataContent adminDataContent : tableList) {
							if (a.getDataSourceId().equalsIgnoreCase(
									adminDataContent.getDataSourceId())
									&& a.getTableName().equalsIgnoreCase(
											adminDataContent.getTableName())) {
								a.setCreateBy(adminDataContent.getCreateBy());
								a.setCreateDate(adminDataContent.getCreateDate());
								a.setUpdateBy(adminDataContent.getUpdateBy());
								a.setUpdateDate(adminDataContent.getUpdateDate());
								a.setRemarks(adminDataContent.getRemarks());
								a.setDelFlag("0");
								updatetableList.add(a);
								temp.add(adminDataContent);
							}
						}
					}
					if (CollectionUtils.isNotEmpty(temp)) {
						for (AdminDataContent content : temp) {
							tableList.remove(content);
						}

					}
					if (CollectionUtils.isNotEmpty(tableList)) {
						aAdminDataContentService.insertByBatch(tableList);// 对admin_data_content表进行维护
						
					}

					if (CollectionUtils.isNotEmpty(updatetableList)) {
						aAdminDataContentService.updateByBatch(updatetableList);
					}
					if (CollectionUtils.isNotEmpty(tableList)) {
						Map<String, List> map = dataContentDetail2
								.getDataContentDetail(datasourceConfig,conn, tableList);
						dataContentDetailList = map.get("contentDetailsList");
						errors = map.get("errorsList");
					}

					if (CollectionUtils.isNotEmpty(updatetableList)) {
						Map<String, List> map = dataContentDetail2
								.getDataContentDetail(datasourceConfig,conn, updatetableList);
						updatedataContentDetailListTemp = map.get("contentDetailsList");
						errors.addAll(map.get("errorsList"));
					}
					AdminDataContentDetail ad=new AdminDataContentDetail();
					ad.setDatasourceId(datasourceConfig.getId());
					List<AdminDataContentDetail> allContentDetailListByScan = contentDetailService.findListByScan(ad);
					for (AdminDataContentDetail detail : updatedataContentDetailListTemp) {
						//判断之前是否存在
						boolean isExist=false;
						AdminDataContentDetail preDetail=new AdminDataContentDetail();
						for(AdminDataContentDetail pre:allContentDetailListByScan){
							if(detail.getDatasourceId().equals(pre.getDatasourceId())&&detail.getContentId().equals(pre.getContentId())
									&&detail.getColumnName().equals(pre.getColumnName())){
								isExist=true;
								preDetail=pre;
								break;
							}
						};
						if(isExist){
							preDetail.setColumnType(detail.getColumnType());
							preDetail.setCreateBy(detail.getCreateBy());
							preDetail.setCreateDate(new Date());
							preDetail.setUpdateBy(detail.getUpdateBy());
							preDetail.setUpdateDate(new Date());
							preDetail.setRemarks(detail.getRemarks());
							updatedataContentDetailList.add(preDetail);
						}else{
							detail.setCreateDate(new Date());
							detail.setUpdateDate(new Date());
							dataContentDetailList.add(detail);
						};
						/*List<AdminDataContentDetail> contentDetailListByScan = contentDetailService.findListByScan(detail);
						if (CollectionUtils.isNotEmpty(contentDetailListByScan)) {
							AdminDataContentDetail a = contentDetailListByScan.get(0);
							a.setCreateBy(detail.getCreateBy());
							a.setCreateDate(detail.getCreateDate());
							a.setUpdateBy(detail.getUpdateBy());
							a.setUpdateDate(detail.getUpdateDate());
							a.setRemarks(detail.getRemarks());
							updatedataContentDetailList.add(a);
						} else {
							dataContentDetailList.add(detail);
						}*/
					}
					if (CollectionUtils.isNotEmpty(dataContentDetailList)) {
						//contentDetailService.insertByBatch(dataContentDetailList);// 对admin_data_content表进行维护
						insertDetailByXiancheng(dataContentDetailList);
					}
					if (CollectionUtils.isNotEmpty(updatedataContentDetailList)) {
						updateByXiancheng(updatedataContentDetailList);
						//contentDetailService.updateByBatch(updatedataContentDetailList);
								
					}
					if (CollectionUtils.isNotEmpty(errors)) {
						Iterator<TableScanError> it = errors.iterator();
						while(it.hasNext()){
							TableScanError error = it.next();
							List<TableScanError> list=EerrorService.selectForValidate(error);
						    if(CollectionUtils.isNotEmpty(list)){
						        it.remove();
						    }
						}
						//EerrorService.insertByBatch(errors);
						inserErrorByXiancheng(errors);
					}
				}
				conn.close();
				System.out.println("表扫描结束===================");

			} catch (Exception e) {
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				return false;
			}
			Map<String, List> map = new HashMap<String, List>();
			map.put("updatedataContentDetailListTemp",updatedataContentDetailListTemp);
			return true;
		}
		return false;
		
	}
	private void updateByXiancheng(
			List<AdminDataContentDetail> updatedataContentDetailList) {
		int pageSize = 50, totalPage = 0;
		totalPage = updatedataContentDetailList.size() / pageSize;
		if (updatedataContentDetailList.size() % pageSize > 0) {
			totalPage++;
		}
		for (int page = 0; page < totalPage; page++) {
			List<AdminDataContentDetail> currentData = new ArrayList<AdminDataContentDetail>();
			if (page == totalPage - 1) {
				currentData = updatedataContentDetailList.subList(page * pageSize, updatedataContentDetailList.size());
			} else {
				currentData = updatedataContentDetailList.subList(page * pageSize, page * pageSize + pageSize);
			}
			//通过线程 并发执行  但是由于并发太多 需要主动休眠（效率比顺序执行高）

			Thread rthread = new Thread(new ScanToUpdateDetail(currentData,contentDetailService));
			rthread.start();
			//必须休眠 不然线程太多会报错
			//Thread.sleep(2000);
		}
	}
	private void insertDetailByXiancheng(
			List<AdminDataContentDetail> dataContentDetailList) {
		int pageSize = 50, totalPage = 0;
		totalPage = dataContentDetailList.size() / pageSize;
		if (dataContentDetailList.size() % pageSize > 0) {
			totalPage++;
		}
		for (int page = 0; page < totalPage; page++) {
			List<AdminDataContentDetail> currentData = new ArrayList<AdminDataContentDetail>();
			if (page == totalPage - 1) {
				currentData = dataContentDetailList.subList(page * pageSize, dataContentDetailList.size());
			} else {
				currentData = dataContentDetailList.subList(page * pageSize, page * pageSize + pageSize);
			}
			//通过线程 并发执行  但是由于并发太多 需要主动休眠（效率比顺序执行高）

			Thread rthread = new Thread(new ScanToinsertDetail(currentData,contentDetailService));
			rthread.start();
			//必须休眠 不然线程太多会报错
			//Thread.sleep(2000);
		}
	}
	private void inserErrorByXiancheng(List<TableScanError> errors) {
		int pageSize = 50, totalPage = 0;
		totalPage = errors.size() / pageSize;
		if (errors.size() % pageSize > 0) {
			totalPage++;
		}
		for (int page = 0; page < totalPage; page++) {
			List<TableScanError> currentData = new ArrayList<TableScanError>();
			if (page == totalPage - 1) {
				currentData = errors.subList(page * pageSize, errors.size());
			} else {
				currentData = errors.subList(page * pageSize, page * pageSize + pageSize);
			}
			//通过线程 并发执行  但是由于并发太多 需要主动休眠（效率比顺序执行高）

			Thread rthread = new Thread(new ScanToInsertError(currentData,EerrorService));
			rthread.start();
			//必须休眠 不然线程太多会报错
			//Thread.sleep(2000);
		}
	}
	
	@Override
	public List<AdminDatasourceConfig> getAllbyuid(AdminDatasourceConfig config) {
		// TODO Auto-generated method stub
		return adminDatasourceConfigMapper.getAllbyuid(config);
	}
	@Override
	public List<AdminDatasourceConfig> getAllByOfficeId(String officeId) {
		List<AdminDatasourceConfig> configs=adminDatasourceConfigMapper.getAllByOfficeId(officeId);
		//查询表
		for(AdminDatasourceConfig config:configs){
			AdminDataContent content=new AdminDataContent();
			content.setDataSourceId(config.getId());
			List<AdminDataContent> dataContents= contentMapper.getAllby(content);
			config.setContents(dataContents);
		}
		return configs;
	}


}
