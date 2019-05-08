package com.haier.datamart.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.datamart.entity.AdminDataContent;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 数据展示内容配置 Mapper 接口
 * </p>
 *
 * @author dsh123
 * @since 2018-05-23
 */
public interface AdminDataContentMapper extends BaseMapper<AdminDataContent> {
	List<AdminDataContent> findList(AdminDataContent admindatacontent);
	public void insertBatch(@Param(value="contents") List<AdminDataContent> contents);
	List<AdminDataContent> findListByScan(AdminDataContent admindatacontent);
	List<AdminDataContent> findListByScan2(AdminDataContent admindatacontent);
	void update(AdminDataContent admindatacontent);
	void updateListByScan(@Param("contents") List<AdminDataContent> contents);
	int add(AdminDataContent content);
	AdminDataContent getContent(AdminDataContent content);
	List<AdminDataContent> getByIndexid(String id);
	List<AdminDataContent> getAll();
	List<AdminDataContent> getAllby(AdminDataContent content);
	List<AdminDataContent> getAllbyName(AdminDataContent content);
	 int updataTable(AdminDataContent content);
	 AdminDataContent get(String id);
	 int delete(String id);
	String selectbyuid(@Param("uid")String uid);
	int deletebyConfigID(String id);
}
