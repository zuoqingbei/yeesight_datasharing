package com.haier.datamart.mapper;
import java.util.List;

import com.haier.datamart.entity.EnteringTableSettingDetailData;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
  * 补录模块-补录数据表配置字段明细数据来源配置信息Mapper接口
 * @author zuoqb123
 * @date 2018-12-11
 */
public interface EnteringTableSettingDetailDataMapper extends BaseMapper<EnteringTableSettingDetailData> {
	public List<EnteringTableSettingDetailData> getDetailDataByDetailId(String detailId);

}


