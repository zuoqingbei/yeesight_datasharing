package com.haier.datamart.service;

import java.util.List;

import com.haier.datamart.entity.Group;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 用户组 服务类
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-05-28
 */
public interface IGroupService extends IService<Group> {

	List<Group> getGroupByUser(String username);
}
