package com.haier.datamart.shiro;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.haier.datamart.config.SpringContextBean;
import com.haier.datamart.entity.Group;
import com.haier.datamart.entity.Menu;
import com.haier.datamart.entity.Role;
import com.haier.datamart.entity.User;
import com.haier.datamart.exception.UnauthorizedException;
import com.haier.datamart.service.IGroupService;
import com.haier.datamart.service.IMenuService;
import com.haier.datamart.service.IRoleService;
import com.haier.datamart.service.IUserService;
import com.haier.datamart.utils.JWTUtil;

/**
 * @author zuoqb
 * @since 2018-05-03
 */
public class MyRealm extends AuthorizingRealm {
    private Logger logger = LoggerFactory.getLogger(MyRealm.class);
    @Autowired
    private IUserService userInfoService;
    @Autowired
	 private IGroupService groupService;
	 @Autowired
	 private IRoleService roleService;
	 @Autowired
	 private IMenuService menuService;
    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (roleService == null) {
            this.roleService = SpringContextBean.getBean(IRoleService.class);
        }
        if (menuService == null) {
            this.menuService = SpringContextBean.getBean(IMenuService.class);
        }

        String username = JWTUtil.getUsername(principals.toString());
      /*  User user = userService.getUserByUserName(username);
        UserToRole userToRole = userToRoleService.selectByUserId(user.getUserId());*/

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        ArrayList<String> pers = new ArrayList<>();
        User user  = (User)principals.getPrimaryPrincipal();
        List<Menu> menus=new ArrayList<Menu>();
 		List<Role> roles=new ArrayList<Role>();
 	
 		if (StringUtils.isNotBlank(username)) {
 		   List<Group> groups =	groupService.getGroupByUser(username);
 		   user.setGroups(groups);
 		    if (groups!=null) {
 			   for (Group group : groups) {
 			   List<Role> ro = roleService.getRoleByGroup(group.getId());
 			      if (ro!=null) {
 				roles.addAll(ro);
 			        for (Role role : ro) {
 			  	 List<Menu> m = menuService.getMenuByRole(role.getId());
 				   menus.addAll(m);
 		         	}	
 			   }
 			}
 		}
 		    user.setRoles(roles);
 		    user.setMenus(menus);
 			
 		}
 		for (Role role : user.getRoles()) {
 			simpleAuthorizationInfo.addRole(role.getName());
 		}	
 		for (Menu menu : user.getMenus()) {
 			simpleAuthorizationInfo.addStringPermission(menu.getName());
 		}
        /* List<Menu> menuList = menuService.findMenuByRoleId(userToRole.getRoleId(), Constant.ENABLE);
        for (Menu per : menuList) {
            if (!ComUtil.isEmpty(per.getCode())) {
                if (!ComUtil.isEmpty(per.getCode().replace(" ", ""))) {
                    pers.add(per.getCode());
                }
            }
        }
        Set<String> permission = new HashSet<>(pers);
        simpleAuthorizationInfo.addStringPermissions(permission);*/
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws UnauthorizedException {
        if (userInfoService == null) {
            this.userInfoService = SpringContextBean.getBean(IUserService.class);
        }
        String token = (String) auth.getCredentials();

        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(token);
       
        if (username == null) {
            throw new UnauthorizedException("token invalid");
        }
        User user=new User();
        user.setLoginName(username);
        User userInfo = userInfoService.get(user);
        //User userBean = userService.getUserByUserName(username);
       // User userBean = null;
        if (userInfo == null) {
            throw new UnauthorizedException("User didn't existed!");
        }
     /*   if (! JWTUtil.verify(token, username, userBean.getPassWord())) {
            throw new UnauthorizedException("Username or password error");
        }*/
        return new SimpleAuthenticationInfo(token, token, this.getName());
    }
}
