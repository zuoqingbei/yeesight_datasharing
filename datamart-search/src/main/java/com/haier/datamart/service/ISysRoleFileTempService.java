package com.haier.datamart.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.haier.datamart.entity.SysRoleFileTemp;


/**
 * 角色与文件目录对照表服务类
 * @author zuoqb123
 * @date 2018-12-10
 */
public interface ISysRoleFileTempService extends IService<SysRoleFileTemp> {
	public int deleteByRoleId(String roleId);
	public List<SysRoleFileTemp> getSysRoleFileTempByRoleId(String roleId);
	public int saveOrUpdate(SysRoleFileTemp entity);
}
