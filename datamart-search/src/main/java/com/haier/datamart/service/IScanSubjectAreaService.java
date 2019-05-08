package com.haier.datamart.service;

import java.util.List;

import com.haier.datamart.entity.ScanSubjectArea;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 主题域 服务类
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-06-07
 */
public interface IScanSubjectAreaService extends IService<ScanSubjectArea> {
  ScanSubjectArea selectbyname(String name);
  List<ScanSubjectArea> selectAllbyLikename(ScanSubjectArea scanSubjectArea);
  List<ScanSubjectArea> selectChildrenbyId(String id);
  int addExcle(ScanSubjectArea scanSubjectArea);
  
  List<ScanSubjectArea> getAll();
  int deleteSubject(String id);
  int updateSubjectArea(ScanSubjectArea subjectArea);
  List<ScanSubjectArea> getAllSubject();
}
