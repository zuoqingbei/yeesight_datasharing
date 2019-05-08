package com.haier.datamart.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haier.datamart.entity.ScanIndexTableRelative;
import com.haier.datamart.entity.ScanSubjectAreaIndex;

/**
 * <p>
 * 主题域与指标对照 Mapper 接口
 * </p>
 *
 * @author dsh123
 * @since 2018-05-30
 */
public interface ScanSubjectAreaIndexMapper extends BaseMapper<ScanSubjectAreaIndex> {
	List<ScanSubjectAreaIndex> getInclude();
	ScanSubjectAreaIndex getAreaIdByIndexId(String indexId);
	int add(ScanSubjectAreaIndex areaIndex);
	
	int updateDelete(ScanSubjectAreaIndex areaIndex);
	
	List<ScanSubjectAreaIndex> getIndex(String id);
	int update(ScanSubjectAreaIndex areaIndex);
	int updatebyid(ScanSubjectAreaIndex areaIndex);
    List<ScanSubjectAreaIndex> getArea(String id);
}
