package com.haier.datamart.service;

import java.util.List;

import com.haier.datamart.entity.EnteringTableSettingDetail;
import com.haier.datamart.entity.EnteringTableSettingInfo;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 补录模块-补录数据表配置基础信息 服务类
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-07-02
 */
public interface IEnteringTableSettingInfoService extends IService<EnteringTableSettingInfo> {
	//根据settinginfo的id去查询另一张表的字段详细信息
	public List<EnteringTableSettingDetail> getBySettingId(String settingId);
	public List<EnteringTableSettingInfo> getSettingInfoDetail(EnteringTableSettingInfo settinginfo);
	public int deleteById(String id) ;
	public EnteringTableSettingInfo getById(String id) ;
	public EnteringTableSettingInfo getByName(String name,String dataId) ;
	public Integer getRelOrder(Integer defineOrder, String entrySettingId);
	/**
	 * 通过id获取order_no
	 * @param idParameter
	 * @return order_no
	 */
	public Integer getOrderById(String idParameter,String entrySettingId);
	public int save(EnteringTableSettingInfo settingInfo);
	public void deleteOneEntry(String tableName, String idOfEntry);
	
   int changestatus(String id,String status);
    //查询表中所有返回给定时任务
   List<EnteringTableSettingInfo> getAll();
   EnteringTableSettingInfo getSettingInfoByDesc(String desc);
   List<EnteringTableSettingDetail> getBySettingId(String enteringSettingId,String isExport);
   public List<EnteringTableSettingDetail> getBySettingIdIncludeData(String settingId);
}
