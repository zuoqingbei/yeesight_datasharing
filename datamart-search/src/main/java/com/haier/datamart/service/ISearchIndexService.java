
package com.haier.datamart.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.haier.datamart.entity.AdminDataContent;
import com.haier.datamart.entity.AdminDataContentDetail;
import com.haier.datamart.entity.AdminDatasourceConfig;
import com.haier.datamart.entity.AltasNameHelp;
import com.haier.datamart.entity.ScanIndexTableRelative;
import com.haier.datamart.entity.SearchIndex;
import com.haier.datamart.entity.SearchIndexDimension;

/**
 * <p>
 * 指标表 服务类
 * </p>
 *
 * @author dsh123
 * @since 2018-05-23
 */
public interface ISearchIndexService extends IService<SearchIndex> {
	SearchIndex get(String id);
	
	List<SearchIndex> getInclude(String id);
	
	int addExcle(SearchIndex index);
	
	int add(SearchIndexDimension indexDimension);
	
	int addConfig(AdminDatasourceConfig config);
	AdminDatasourceConfig   getConfig(AdminDatasourceConfig config);
	AdminDataContent getContent(AdminDataContent content);
	int addContent(AdminDataContent content);
	AdminDataContentDetail getDetail(AdminDataContentDetail detail);
	int addDetail(AdminDataContentDetail detail);
	
	int addRelative(ScanIndexTableRelative relative);
	SearchIndex getByName(String name);
	
	int delete(String id);
	 int addIndex(SearchIndex index);
	 AltasNameHelp getbyid(String id);
	 List<SearchIndex> getAll(SearchIndex index);
	 SearchIndex details(String id);
	  int update(SearchIndex index);
	  
	 SearchIndex getName(String name);
	 
	List<SearchIndex> getEntriesByIndexCodes(List<String> list);
	//对应系统下的指标
	List<SearchIndex> getbySYS(String name);
	//根据报表id查询具体指标id
	List<SearchIndex> getbyReport(String reportId);
	
	List<SearchIndex> getbyReportandName(String reportId,String indexname);

	//查询是否有相同指标
	List<SearchIndex> getbyCode(String code);

	List<SearchIndex> getUserSeeIndex(String userId);
	
	
	List<SearchIndex> getplat();
	List<SearchIndex> getIndexBySubject(String subjectId);
}
