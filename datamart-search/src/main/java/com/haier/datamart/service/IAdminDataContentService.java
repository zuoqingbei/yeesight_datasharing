package com.haier.datamart.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.datamart.entity.AdminDataContent;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 数据展示内容配置 服务类
 * </p>
 *
 * @author dsh123
 * @since 2018-05-23
 */
public interface IAdminDataContentService extends IService<AdminDataContent> {
	/**
	 * 批量插入
	* @author doushuihai  
	* @date 2018年6月7日下午3:00:13  
	* @TODO
	 */
	public void insertByBatch(List<AdminDataContent> contents);
	public List<AdminDataContent> findListByScan(AdminDataContent admindatacontent);
	public List<AdminDataContent> findListByScan2(AdminDataContent admindatacontent);
	public void update(AdminDataContent adminDataContent);
	public void updateByBatch(List<AdminDataContent> contents);
	
	List<AdminDataContent> getAllBy(AdminDataContent content);
	List<AdminDataContent> getAllByName(AdminDataContent content);
	int updateTable(AdminDataContent content);
	
	AdminDataContent get(String id);
	 int delete(String id);
}
