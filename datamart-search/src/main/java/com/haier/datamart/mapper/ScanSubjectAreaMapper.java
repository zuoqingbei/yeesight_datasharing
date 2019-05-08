package com.haier.datamart.mapper;

import java.util.List;

import com.haier.datamart.entity.ScanSubjectArea;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 主题域 Mapper 接口
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-06-07
 */
public interface ScanSubjectAreaMapper extends BaseMapper<ScanSubjectArea> {
    ScanSubjectArea selectbyname(String name);
    List<ScanSubjectArea> selectAllbyLikename(ScanSubjectArea scanSubjectArea);
    List<ScanSubjectArea> selectChildrenbyId(String id);
    int  add(ScanSubjectArea scanSubjectArea);
    List<ScanSubjectArea> getAll();
	int deleteById(ScanSubjectArea scanSubjectArea);
	int updateSubjectArea(ScanSubjectArea subjectArea);
}
