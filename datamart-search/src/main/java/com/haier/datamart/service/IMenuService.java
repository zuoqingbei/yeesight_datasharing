package com.haier.datamart.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.haier.datamart.entity.Menu;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-05-28
 */
public interface IMenuService extends IService<Menu> {
 
	List<Menu> getMenuByRole(String id);
	List<Menu> getMenuByUserName(String uid,String parentId,String menuType);
	List<Menu>  getAllMenu();
}
