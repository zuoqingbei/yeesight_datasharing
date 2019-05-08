package com.haier.datamart.mapper;

import java.util.List;

import com.haier.datamart.entity.Group;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 用户组 Mapper 接口
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-05-28
 */
public interface GroupMapper extends BaseMapper<Group> {
       List<Group> getGroupByUser(String username);
}
