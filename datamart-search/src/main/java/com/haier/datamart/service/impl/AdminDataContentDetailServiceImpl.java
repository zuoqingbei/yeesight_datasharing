package com.haier.datamart.service.impl;

import java.util.List;

import com.haier.datamart.entity.AdminDataContentDetail;
import com.haier.datamart.mapper.AdminDataContentDetailMapper;
import com.haier.datamart.service.IAdminDataContentDetailService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 数据展示内容配置明细 服务实现类
 * </p>
 *
 * @author dsh123
 * @since 2018-05-23
 */
@Service
public class AdminDataContentDetailServiceImpl extends ServiceImpl<AdminDataContentDetailMapper, AdminDataContentDetail> implements IAdminDataContentDetailService {
	@Autowired
	private AdminDataContentDetailMapper adminDataContentDetailMapper;
	/**
	 * 批量插入
	 */
	@Override
	public void insertByBatch(List<AdminDataContentDetail> contentDetails) {
		if (CollectionUtils.isEmpty(contentDetails)) {
            throw new IllegalArgumentException("Error: entityList must not be empty");
        }
		adminDataContentDetailMapper.insertBatch(contentDetails);
		
	}
	/**
	 * 根据表扫描功能需要进行特别查询
	 */
	@Override
	public List<AdminDataContentDetail> findListByScan(
			AdminDataContentDetail adminDataContentDetail) {
		
		return adminDataContentDetailMapper.findListByScan(adminDataContentDetail);
	}
	@Override
	public List<AdminDataContentDetail> findListByScan2(
			AdminDataContentDetail adminDataContentDetail) {
		
		return adminDataContentDetailMapper.findListByScan2(adminDataContentDetail);
	}
	@Override
	public void update(AdminDataContentDetail adminDataContentDetail) {
		adminDataContentDetailMapper.update(adminDataContentDetail);
		
	}
	@Override
	public void updateByBatch(List<AdminDataContentDetail> contents) {
		adminDataContentDetailMapper.updateListByScan(contents);
		
	}
	@Override
	public List<AdminDataContentDetail> getAllBy(String id) {
		// TODO Auto-generated method stub
		return adminDataContentDetailMapper.getAllbyName(id);
	}
	@Override
	public List<AdminDataContentDetail> getList(AdminDataContentDetail detail) {
		List<AdminDataContentDetail> list = adminDataContentDetailMapper.getList(detail);
		return list;
	}
	@Override
	public List<AdminDataContentDetail> getColumnByContent(
			AdminDataContentDetail detail) {
		return adminDataContentDetailMapper.getColumnByContent(detail);
	}
	@Override
	public List<AdminDataContentDetail> getAllByDb(String dataSourceId) {
		// TODO Auto-generated method stub
		return adminDataContentDetailMapper.getAllByDb(dataSourceId);
	}

}
