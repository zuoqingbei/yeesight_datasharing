package com.haier.datamart.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.datamart.entity.AdminDataContent;
import com.haier.datamart.entity.AdminDataContentDetail;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 数据展示内容配置明细 服务类
 * </p>
 *
 * @author dsh123
 * @since 2018-05-23
 */
public interface IAdminDataContentDetailService extends IService<AdminDataContentDetail> {
	/**
	 * 批量插入
	* @author doushuihai  
	* @date 2018年6月7日下午2:59:57  
	* @TODO
	 */
	public void insertByBatch(List<AdminDataContentDetail> contentDetails);
	public List<AdminDataContentDetail> findListByScan(AdminDataContentDetail adminDataContentDetail);
	public List<AdminDataContentDetail> findListByScan2(AdminDataContentDetail adminDataContentDetail);
	public void update(AdminDataContentDetail adminDataContentDetail);
	public void updateByBatch(List<AdminDataContentDetail> contents);
     List<AdminDataContentDetail> getAllBy(String id);
     
     List<AdminDataContentDetail> getList(AdminDataContentDetail detail);
     List<AdminDataContentDetail> getColumnByContent(AdminDataContentDetail detail);
     List<AdminDataContentDetail> getAllByDb(String dataSourceId);
}
