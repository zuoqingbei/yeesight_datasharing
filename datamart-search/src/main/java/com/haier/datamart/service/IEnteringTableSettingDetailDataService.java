package com.haier.datamart.service;

import java.util.List;

import com.haier.datamart.entity.EnteringTableSettingDetailData;
import com.baomidou.mybatisplus.service.IService;


/**
 * 补录模块-补录数据表配置字段明细数据来源配置信息服务类
 * @author zuoqb123
 * @date 2018-12-11
 */
public interface IEnteringTableSettingDetailDataService extends IService<EnteringTableSettingDetailData> {
	public List<EnteringTableSettingDetailData> getDetailDataByDetailId(String detailId);
 	
}
