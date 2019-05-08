package com.haier.datamart.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haier.datamart.entity.Role;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-05-28
 */
public interface RoleMapper extends BaseMapper<Role> {
    List<Role> getRoleByGroup(String id);
    List<Role> getRoleByUserId(@Param("uid")String uid);
}
