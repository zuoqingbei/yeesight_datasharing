package com.haier.datamart.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.datamart.entity.Dict;
import com.haier.datamart.entity.SearchReports;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 报表信息 Mapper 接口
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-05-23
 */
public interface SearchReportsMapper extends BaseMapper<SearchReports> {

	public  SearchReports getById(String id);
	List<SearchReports> getKeyword(SearchReports searchReports);
	int addExcel(SearchReports reports);
	int add(Dict dict);
	Dict getByname(String name);

	//根据系统名称查询报表名称
	List<SearchReports> getBysys(String name);
	//根据系统名称查询报表名称 加检索条件
	List<SearchReports> getBysysandName(@Param("name")String name,@Param("reportName")String reportName);

	List<SearchReports> getReportByIndexId(String indexId);
	
	List<SearchReports> getidBYplat(String[] array);
	

}
