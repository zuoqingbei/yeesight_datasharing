package com.haier.datamart.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haier.datamart.entity.AdminDataContentDetail;
import com.haier.datamart.entity.SearchIndex;

/**
 * <p>
 * 指标表 Mapper 接口
 * </p>
 *
 * @author dsh123
 * @since 2018-05-23
 */
public interface SearchIndexMapper extends BaseMapper<SearchIndex> {
	SearchIndex get(String id);
	List<SearchIndex> getInclude(String id);
	List<AdminDataContentDetail> getInclude2(String id);
	
	int addExcle(SearchIndex index);
	SearchIndex getByName(String name);
	int updateDelete(SearchIndex index);
	List<SearchIndex> getAll(SearchIndex index);
	
	int  update(SearchIndex index);
	 SearchIndex getName(String name);
	 /**
	  * 根据指标编码集合获取对应实体集合
	  */
	List<SearchIndex> getEntriesByIndexCodes(List<String> list);
	List<SearchIndex> getplat();
	//对应系统下的指标
	List<SearchIndex> getbySYS(String dicId);
	//根据报表id查找对应指标
	List<SearchIndex> getbyReport(String reportId);
	//根据报表id查找对应指标 加检索条件
	List<SearchIndex> getbyReportandName(@Param("reportId")String reportId,@Param("indexname")String indexname);

	//查询指标编码
	List<SearchIndex> getbyCode(String code);

	List<SearchIndex> getUserSeeIndex(String userId);

	List<SearchIndex> getIndexBySubject(String subjectId);
}
