package com.haier.datamart.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haier.datamart.entity.Menu;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-05-28
 */
public interface MenuMapper extends BaseMapper<Menu> {
  List<Menu> getMenuByRole(String id);
  List<Menu> getMenuByUserName(@Param("uid")String uid,@Param("parentId")String parentId,@Param("menuType")String menuType);
}
