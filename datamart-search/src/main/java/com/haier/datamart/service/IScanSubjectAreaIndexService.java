package com.haier.datamart.service;

import com.haier.datamart.entity.ScanSubjectAreaIndex;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 主题域与指标对照 服务类
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-06-07
 */
public interface IScanSubjectAreaIndexService extends IService<ScanSubjectAreaIndex> {
  int addExcle(ScanSubjectAreaIndex areaIndex);
  ScanSubjectAreaIndex getAreaIdByIndexId(String indexId);
}
