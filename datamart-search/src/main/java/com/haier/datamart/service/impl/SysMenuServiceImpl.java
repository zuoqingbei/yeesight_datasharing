package com.haier.datamart.service.impl;

import java.util.List;

import com.haier.datamart.entity.DocUploadFile;
import com.haier.datamart.entity.DocUploadFileTemp;
import com.haier.datamart.entity.SysMenu;
import com.haier.datamart.mapper.SysMenuMapper;
import com.haier.datamart.service.ISysMenuService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
/**
 * 菜单表服务实现类
 * @author zuoqb123
 * @date 2018-09-30
 */
@Service
@Transactional
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

	@Override
	public List<SysMenu> findList(SysMenu sysMenu) {
		List<SysMenu> list=sysMenuMapper.findList(sysMenu);
		//查询目录下文件
		for(SysMenu t:list){
			//查询子目录
			SysMenu c=new SysMenu();
			c.setCreateBy(sysMenu.getCreateBy());
			c.setName(sysMenu.getName());
			c.setParentId(t.getId());
			List<SysMenu> children=findList(c);
			t.setChildren(children);
		}
		return list;
	}
   
}
