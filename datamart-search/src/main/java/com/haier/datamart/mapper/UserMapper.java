package com.haier.datamart.mapper;

import com.haier.datamart.entity.User;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-05-28
 */
public interface UserMapper extends BaseMapper<User> {
	
	int  adduser(User user);
	
	int updateUser(User user);
	
	int deleteUser(String id);
	
	User selectOne(@Param("id")String  id);
	
	User get(User user);
	/**
	 * @param loginName
	 * @return
	 */
	List<User> getUserIdByLoginName(@Param("loginName")String loginName);
	User getByLoginName(String loginName);

}

	
