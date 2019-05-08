package com.haier.datamart.service.impl;

import java.util.List;

import com.haier.datamart.entity.EnteringTableSettingDetailData;
import com.haier.datamart.mapper.EnteringTableSettingDetailDataMapper;
import com.haier.datamart.service.IEnteringTableSettingDetailDataService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
/**
 * 补录模块-补录数据表配置字段明细数据来源配置信息服务实现类
 * @author zuoqb123
 * @date 2018-12-11
 */
@Service
@Transactional
public class EnteringTableSettingDetailDataServiceImpl extends ServiceImpl<EnteringTableSettingDetailDataMapper, EnteringTableSettingDetailData> implements IEnteringTableSettingDetailDataService {

    @Autowired
    private EnteringTableSettingDetailDataMapper enteringTableSettingDetailDataMapper;

	@Override
	public List<EnteringTableSettingDetailData> getDetailDataByDetailId(
			String detailId) {
		return enteringTableSettingDetailDataMapper.getDetailDataByDetailId(detailId);
	}
   
}
