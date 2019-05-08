package com.haier.datamart.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.haier.datamart.entity.SysMenu;


/**
 * 菜单表服务类
 * @author zuoqb123
 * @date 2018-09-30
 */
public interface ISysMenuService extends IService<SysMenu> {
	List<SysMenu> findList(SysMenu sysMenu);
}
