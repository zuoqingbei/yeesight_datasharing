package com.haier.datamart.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haier.datamart.entity.EnteringTableSettingInfo;

/**
 * <p>
 * 补录模块-补录数据表配置基础信息 Mapper 接口
 * </p>
 *
 * @author dsh
 * @since 2018-07-02
 */
public interface EnteringTableSettingInfoMapper extends BaseMapper<EnteringTableSettingInfo> {
	public EnteringTableSettingInfo getByName(EnteringTableSettingInfo settingInfo);
	public EnteringTableSettingInfo getById(String id);
	public Integer insert(EnteringTableSettingInfo settingInfo);
	Integer updateById(EnteringTableSettingInfo settinginfo);
	/**
	 * 根据表名和id删除记录
	 * @param tableName
	 * @param idOfEntry
	 */
	void deleteOneEntry(@Param("tableName")String tableName,@Param("idOfEntry") String idOfEntry);
	public List<EnteringTableSettingInfo> getSettingInfoDetail(EnteringTableSettingInfo settinginfo);
	int insertMethod(EnteringTableSettingInfo settinginfo);
    //定时任务改变状态
    int	 changestatus(EnteringTableSettingInfo settinginfo);
    //查询所有 返回给定时任务
    List<EnteringTableSettingInfo> getAll();
    EnteringTableSettingInfo getSettingInfoByDesc(String desc);
}
