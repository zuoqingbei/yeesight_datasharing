package com.haier.datamart.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.datamart.entity.EnteringTableSettingDetail;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 补录模块-补录数据表配置字段明细信息 Mapper 接口
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-07-02
 */
public interface EnteringTableSettingDetailMapper extends BaseMapper<EnteringTableSettingDetail> {
	List<EnteringTableSettingDetail> getBySettingId(String settingId);
	/**
	 * @param defineOrder
	 * @param entrySettingId 
	 * @return 当前列真实顺序
	 */
	Integer getRelOrder(@Param("defineOrder")Integer defineOrder, @Param("entrySettingId")String entrySettingId);
	/**
	 * 根据id获取order_no
	 * @param idParameter
	 * @param entrySettingId 
	 * @return order_no
	 */
	Integer getOrderById(@Param("idParameter")String idParameter, @Param("entrySettingId")String entrySettingId);
	int insertBatch(@Param(value="etDetail") List<EnteringTableSettingDetail> etDetail);
	Integer insert(EnteringTableSettingDetail detail);
	int deleteBySettingId(String settingId);//根据settingId删除表数据
	Integer updateById(EnteringTableSettingDetail id);//根据id修改表数据
}
