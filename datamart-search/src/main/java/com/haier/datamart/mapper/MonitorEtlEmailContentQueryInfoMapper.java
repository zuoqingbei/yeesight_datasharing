package com.haier.datamart.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haier.datamart.entity.MonitorEtlEmailContentQueryInfo;

public interface MonitorEtlEmailContentQueryInfoMapper extends BaseMapper<MonitorEtlEmailContentQueryInfo> {

	int insertSelective(MonitorEtlEmailContentQueryInfo record);

	int deleteByMailId(MonitorEtlEmailContentQueryInfo record);

	List<MonitorEtlEmailContentQueryInfo> selectListCustom(MonitorEtlEmailContentQueryInfo record);
}