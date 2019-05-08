package com.haier.datamart.service.impl;

import java.util.List;

import com.haier.datamart.entity.Group;
import com.haier.datamart.mapper.GroupMapper;
import com.haier.datamart.service.IGroupService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户组 服务实现类
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-05-28
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements IGroupService {
 
	@Autowired
	private GroupMapper groupMapper;
	@Override
	public List<Group> getGroupByUser(String username) {
		// TODO Auto-generated method stub
		return groupMapper.getGroupByUser(username);
	}

}
