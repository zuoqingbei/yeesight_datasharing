package com.haier.datamart.service;

import java.util.List;

import com.haier.datamart.entity.SearchReports;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 报表信息 服务类
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-05-23
 */
public interface ISearchReportsService extends IService<SearchReports> {
    /**
     * 
     * @author lixiaoyi
     * @date 2018年5月24日 上午9:54:12
     * @TODO 根据ID查询报表详情
     */
	public SearchReports  getById(String id);
	
	public  List<SearchReports> getKeyword(SearchReports searchReports);
	
	int addExcle(SearchReports reports);
	List<SearchReports> getReportByIndexId(String indexId);
}
