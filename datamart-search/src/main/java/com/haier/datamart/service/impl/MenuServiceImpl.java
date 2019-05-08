package com.haier.datamart.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haier.datamart.entity.Menu;
import com.haier.datamart.mapper.MenuMapper;
import com.haier.datamart.service.IMenuService;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-05-28
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
  @Autowired
  private MenuMapper menuMapper;
	
	@Override
	public List<Menu> getMenuByRole(String id) {
		return menuMapper.getMenuByRole(id);
	}

	@Override
	public List<Menu> getMenuByUserName(String userName, String parentId,String menuType) {
		return menuMapper.getMenuByUserName(userName, parentId,menuType);
	}

	@Override
	public List<Menu> getAllMenu() {
		List<Menu> menus=new ArrayList<Menu>();
		EntityWrapper<Menu> wrapper=new EntityWrapper<Menu>();
		wrapper.where("del_flag={0}", 0);
		wrapper.eq("parent_id", "0");
		wrapper.eq("menu_type", "0");
		wrapper.orderBy("sort", true);
		menus=menuMapper.selectList(wrapper);
		for(Menu m:menus){
			EntityWrapper<Menu> wra=new EntityWrapper<Menu>();
			wra.where("del_flag={0}", 0);
			wra.eq("parent_id", m.getId());
			wra.eq("menu_type", "0");
			wra.orderBy("sort", true);
			m.setChildrens(menuMapper.selectList(wra));
		}
		return menus;
	}

}
