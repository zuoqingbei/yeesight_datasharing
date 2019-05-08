package com.haier.datamart.service.impl;

import java.util.List;

import com.haier.datamart.entity.AdminDataContent;
import com.haier.datamart.mapper.AdminDataContentMapper;
import com.haier.datamart.service.IAdminDataContentService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 数据展示内容配置 服务实现类
 * </p>
 *
 * @author dsh123
 * @since 2018-05-23
 */
@Service
public class AdminDataContentServiceImpl extends ServiceImpl<AdminDataContentMapper, AdminDataContent> implements IAdminDataContentService {
	@Autowired
	private AdminDataContentMapper adminDataContentMapper;
	public void insertByBatch(List<AdminDataContent> contents){
		if (CollectionUtils.isEmpty(contents)) {
            throw new IllegalArgumentException("Error: entityList must not be empty");
        }
		adminDataContentMapper.insertBatch(contents);
	}
	
	@Override
	public List<AdminDataContent> findListByScan(
			AdminDataContent admindatacontent) {
		return adminDataContentMapper.findListByScan(admindatacontent);
	}
	@Override
	public List<AdminDataContent> findListByScan2(
			AdminDataContent admindatacontent) {
		return adminDataContentMapper.findListByScan(admindatacontent);
	}
	@Override
	public void update(AdminDataContent adminDataContent) {
		// TODO Auto-generated method stub
		adminDataContentMapper.update(adminDataContent);
	}

	@Override
	public void updateByBatch(List<AdminDataContent> contents) {
		if (CollectionUtils.isEmpty(contents)) {
            throw new IllegalArgumentException("Error: entityList must not be empty");
        }
		adminDataContentMapper.updateListByScan(contents);
	}

	@Override
	public List<AdminDataContent> getAllBy(AdminDataContent content) {
	
		return adminDataContentMapper.getAllby(content);
	}

	@Override
	public int updateTable(AdminDataContent content) {
		// TODO Auto-generated method stub
		return adminDataContentMapper.updataTable(content);
	}

	@Override
	public AdminDataContent get(String id) {
		// TODO Auto-generated method stub
		return adminDataContentMapper.get(id);
	}

	@Override
	public int delete(String id) {
		// TODO Auto-generated method stub
		return adminDataContentMapper.delete(id);
	}

	@Override
	public List<AdminDataContent> getAllByName(AdminDataContent content) {
		// TODO Auto-generated method stub
		return  adminDataContentMapper.getAllbyName(content);
	}

}
