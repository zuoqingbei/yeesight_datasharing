package com.haier.datamart.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.haier.datamart.annotation.Log;
import com.haier.datamart.annotation.Pass;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.entity.User;
import com.haier.datamart.service.IMenuService;
import com.haier.datamart.service.IUserService;
import com.haier.datamart.service.IUserToRoleService;
import com.haier.datamart.utils.ComUtil;

/**
 *  登录接口
 * @author zuoqb
 * @since 2018-05-03
 */
@RestController
public class LoginController  extends BaseController{
    private Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserToRoleService userToRoleService;
    @Autowired
    private IMenuService menuService;

    @GetMapping(value = "/login", produces = { "application/json;charset=UTF-8" })
    @Log(description="登录接口:/login")
    @Pass
    public PublicResult<Map<String, Object>> login(
    		HttpServletRequest request,HttpServletResponse response) throws Exception{
       
    	String userName = request.getParameter("userName");
        String passWord = request.getParameter("passWord");
        if (ComUtil.isEmpty(userName) || ComUtil.isEmpty(passWord)) {
            return new PublicResult<>(PublicResultConstant.PARAM_ERROR, null);
        }
        EntityWrapper<User> ew = new EntityWrapper<>();
        ew.where("login_name={0}", userName);
        User user = userService.selectOne(ew);
       /* if (ComUtil.isEmpty(user) || !BCrypt.checkpw(passWord, user.getPassWord())) {
            return new PublicResult<>(PublicResultConstant.INVALID_USERNAME_PASSWORD, null);
        }*/
        Map<String, Object> result = new HashMap<>();
       // UserToRole userToRole = userToRoleService.selectByUserId(user.getUserId());
      /*  UserToRole userToRole = new UserToRole();
        result.put("user", new NgUserModel(JWTUtil.sign(user.getUserName(), user.getPassWord()),
                user.getUserName(), user.getPhone(), user.getUserId(), System.currentTimeMillis(),
                ComUtil.isEmpty(userToRole)? null: userToRole.getRoleId(), user.getPhoto(), user.getPhone()));

        //根据角色查询权限
        List<Menu> menuList = new ArrayList<Menu>();
        List<Menu> buttonList = new ArrayList<Menu>();
        //根据角色主键查询启用的菜单权限
        List<Menu> sysMenuList = menuService.findMenuByRoleId(userToRole.getRoleId(), Constant.ENABLE);
        if (!ComUtil.isEmpty(sysMenuList)){
            for (Menu sysMenu : sysMenuList) {
                if (sysMenu.getIsmenu() == 0) {
                    buttonList.add(sysMenu);
                }else {
                    menuList.add(sysMenu);
                }
            }
        }
        result.put("menuList",menuList);
        result.put("buttonList",buttonList);*/
        result.put("test","张三数据库方式开发就");
        PublicResult r= new PublicResult<>(PublicResultConstant.SUCCESS, result);
        return r;
    }

  /*  @PostMapping("/register")
    @Log(description="注册接口:/register")
    @Pass
    public PublicResult<User> register(@ValidationParam("userName,passWord,rePassWord,realName,telephone,sex,email,unit,cityId")
                                       @RequestBody JSONObject requestJson,
                                       BindingResult error) {
        if (error.hasErrors()) {
            return new PublicResult<>(error.getAllErrors().get(0).getDefaultMessage(), null);
        }
        if(!StringUtil.checkMobileNumber(requestJson.getString("telephone"))){
            return new PublicResult<>(PublicResultConstant.PARAM_ERROR, null);
        }
        if(!StringUtil.checkEmail(requestJson.getString("email"))){
            return new PublicResult<>(PublicResultConstant.PARAM_ERROR, null);
        }
        if (!requestJson.getString("passWord").equals(requestJson.getString("rePassWord"))) {
            return new PublicResult<>(PublicResultConstant.INVALID_RE_PASSWORD, null);
        }
        EntityWrapper<User> ew = new EntityWrapper<>();
        ew.where("user_name={0}", requestJson.getString("userName"));
        List<User> userList = userService.selectList(ew);
        if (! ComUtil.isEmpty(userList)) {
            return new PublicResult<>(PublicResultConstant.USERNAME_ALREADY_IN, null);
        }
        User users = new User( requestJson.getString("userName"), requestJson.getString("realName"),
                BCrypt.hashpw(requestJson.getString("passWord"),BCrypt.gensalt()),
                requestJson.getString("telephone"),  requestJson.getString("unit"),
                System.currentTimeMillis(),requestJson.getString("email"),requestJson.getString("cityId"));
        boolean result = userService.register(users, Constant.DEFAULT_REGISTER_ROLE);
        return result? new PublicResult<>(PublicResultConstant.SUCCESS, null):
                new PublicResult<>(PublicResultConstant.FAILED, null);
    }*/



    @RequestMapping(path = "/401",produces = "application/json;charset=utf-8")
    public PublicResult<String> unauthorized() {
        return new PublicResult<>(PublicResultConstant.UNAUTHORIZED, null);
    }
    
   
}
