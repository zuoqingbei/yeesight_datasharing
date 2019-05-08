package com.haier.datamart.mapper;
import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haier.datamart.entity.SysMenu;

/**
  * 菜单表Mapper接口
 * @author zuoqb123
 * @date 2018-09-30
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
	List<SysMenu> findList(SysMenu sysMenu);
}


