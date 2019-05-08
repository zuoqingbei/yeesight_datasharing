package com.haier.datamart.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haier.datamart.entity.SysRoleFileTemp;
import com.haier.datamart.mapper.SysRoleFileTempMapper;
import com.haier.datamart.service.ISysRoleFileTempService;
/**
 * 角色与文件目录对照表服务实现类
 * @author zuoqb123
 * @date 2018-12-10
 */
@Service
@Transactional
public class SysRoleFileTempServiceImpl extends ServiceImpl<SysRoleFileTempMapper, SysRoleFileTemp> implements ISysRoleFileTempService {

    @Autowired
    private SysRoleFileTempMapper sysRoleFileTempMapper;

	@Override
	public int deleteByRoleId(String roleId) {
		EntityWrapper<SysRoleFileTemp> wrapper = new EntityWrapper<SysRoleFileTemp>();
		wrapper.eq("role_id", roleId);
		return sysRoleFileTempMapper.delete(wrapper);
	}

	@Override
	public List<SysRoleFileTemp> getSysRoleFileTempByRoleId(String roleId) {
		EntityWrapper<SysRoleFileTemp> wrapper = new EntityWrapper<SysRoleFileTemp>();
		wrapper.eq("role_id", roleId);
		return sysRoleFileTempMapper.selectList(wrapper);
	}

	@Override
	public int saveOrUpdate(SysRoleFileTemp entity) {
		if(StringUtils.isNotBlank(entity.getRoleId())){
			deleteByRoleId(entity.getRoleId());
			return sysRoleFileTempMapper.insert(entity);
		}else{
			return -1;
		}
	}
   
}
