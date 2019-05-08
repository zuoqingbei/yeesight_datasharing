package com.haier.datamart.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.haier.datamart.entity.EnteringTableSettingHeader;


/**
 * 补录模块Excel头部信息服务类
 * @author zuoqb123
 * @date 2018-10-16
 */
public interface IEnteringTableSettingHeaderService extends IService<EnteringTableSettingHeader> {
	public int saveHeaders(List<EnteringTableSettingHeader> headers,String enteringSettingId);
	public List<EnteringTableSettingHeader> getHeadersByEnteringId(String enteringSettingId);
	public int deleteHeadersByEnteringId(String enteringSettingId);
	public int getHeadersRowNums(String enteringSettingId);
}
