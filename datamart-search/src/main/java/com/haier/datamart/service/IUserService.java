package com.haier.datamart.service;


import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.haier.datamart.entity.MainPageNum;
import com.haier.datamart.entity.User;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-05-28
 */
public interface IUserService extends IService<User> {
    int addUser(User user);
    int updateUser(User user);
    int deleteUser(String id);
     
    User selectOne(String id);
    User get(User user);
    User getByLoginNameAndPwd(User user);
    MainPageNum getNum(String uid);
    /**
     * 通过登录名获取用户id
     * @param loginName
     * @return
     */
	List<User> getUserIdByLoginName(String loginName);
	User getByLoginName(String loginName);
	
}
