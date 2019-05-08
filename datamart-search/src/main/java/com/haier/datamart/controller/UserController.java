package com.haier.datamart.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haier.datamart.annotation.Log;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.entity.HacResourceDto;
import com.haier.datamart.entity.MainPageNum;
import com.haier.datamart.entity.Menu;
import com.haier.datamart.entity.SysUserGroup;
import com.haier.datamart.entity.User;
import com.haier.datamart.mapper.SysUserGroupMapper;
import com.haier.datamart.service.IGroupService;
import com.haier.datamart.service.IHacResourceDtoService;
import com.haier.datamart.service.IMenuService;
import com.haier.datamart.service.IRoleService;
import com.haier.datamart.service.IUserService;
import com.haier.datamart.utils.GenerationSequenceUtil;
import com.haier.datamart.utils.MD5Util;
import com.haier.openplatform.hac.resource.service.HacResourceDTO;
import com.haier.openplatform.hac.resource.service.HacResourceServiceClient;
import com.haier.openplatform.hac.resource.service.HacResourceServiceClientPortType;
import com.haier.openplatform.hac.resource.service.UserSourceAndScode;
import com.haier.openplatform.hac.service.HacDataPrivilegeDTO;
import com.haier.openplatform.hac.service.UserMergeDTO;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-05-28
 */
@RestController
public class UserController extends BaseController {
	@Autowired
	private IGroupService groupService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IMenuService menuService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IHacResourceDtoService dtoService;
	@Autowired 
	private SysUserGroupMapper sysUserGroupMapper;
	private boolean needPwd = true;// 是否验证密码
	private String defalutGroupId="2";//默认用户权限-指标管理
	/**
	 * 
	 * @time   2018年9月19日 上午9:04:30
	 * @author zuoqb
	 * @todo   用户菜单
	 * @param  @param request
	 * @param  @return
	 * @return_type   Object
	 */
	@GetMapping(value = "/user/menu", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/user/menu")
	public Object getMuenByUser(HttpServletRequest request) {
		List<Menu> menus = new ArrayList<Menu>();
		try {
			String uid = request.getParameter("uid");
			String menuType = request.getParameter("menuType");
			if (StringUtils.isNotBlank(uid)) {
				User user = userService.selectById(uid);
				boolean isAdmin=("1".equals(user.getUserType()));
				if(!isAdmin){
					menus = menuService.getMenuByUserName(uid, "0", menuType);
					// 查询子菜单
					for (Menu m : menus) {
						List<Menu> cmenus = menuService.getMenuByUserName(uid,
								m.getId(), menuType);
						m.setChildrens(cmenus);
					}
				}else{
					menus=menuService.getAllMenu();
				}
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED, null);
		}
		return new PublicResult<>(PublicResultConstant.SUCCESS, menus);
	}
	/**
	 * 
	 * @time   2018年9月19日 上午9:04:40
	 * @author zuoqb
	 * @todo   用户登陆
	 * @param  @param request
	 * @param  @param response
	 * @param  @return
	 * @param  @throws Exception
	 * @return_type   Object
	 */
	@GetMapping(value = "/user/login", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/user/login")
	public Object loginRoot(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (needPwd) {
			User user = new User();
			String loginName = request.getParameter("loginName");
			String password = request.getParameter("password");
			String passwordByMD5 = MD5Util.getMd5(password);
			user.setLoginName(loginName);
			user.setPassword(passwordByMD5);
			HacResourceDto hacResourceDto = new HacResourceDto();

			try {
				/*ExecuteResult re = toYanZhengLogin(loginName, password);
				UserMergeDTO userMergeDTO = (UserMergeDTO) re.getResult();*/
				// String userId = userMergeDTO.getUserId()+"";
				// System.out.println(userId+"===========================");
				// 登录成功则保存用户相关信息
				if (true) {
					System.out
							.println("第三方登录成功==================================");
					User getUser = userService.getByLoginNameAndPwd(user);
					User temp = new User();
					temp.setLoginName(loginName);
					temp.setName(loginName);
					temp.setPassword(passwordByMD5);
					if (getUser == null) {
						temp.setId(GenerationSequenceUtil.getUUID());
						temp.setCreateBy(loginName);
						temp.setCreateDate(new Date());
						temp.setUpdateBy(loginName);
						temp.setUpdateDate(new Date());
						userService.addUser(temp);
						//默认添加用户指标录入权限
						List<SysUserGroup> sysUserGroups = sysUserGroupMapper.getGroupId(temp.getId());
						boolean has=false;
						for(SysUserGroup group:sysUserGroups){
							if(defalutGroupId.equals(group.getGroupId())){
								has=true;
							}
						}
						if(!has){
							sysUserGroupMapper.addSysUserGroup(new SysUserGroup(temp.getId(), defalutGroupId));
						}
					} else {
						temp.setId(getUser.getId());
						temp.setUpdateBy(loginName);
						temp.setUpdateDate(new Date());
						userService.updateUser(temp);
					}
					// 根据用户名获取权限资源并保存
				/*	List<HacResourceDTO> resourcesByAppAndUser = getHacResource(loginName);
					// 根据用户名获取数据维度并保存
					List<HacDataPrivilegeDTO> hacPrivilege = PrivilegeDataUtil
							.geprivilegeData(loginName);
					if (!CollectionUtils.isEmpty(resourcesByAppAndUser)
							|| !CollectionUtils.isEmpty(hacPrivilege)
							|| re.getResult() != null) {
						JSONArray json = JSONArray
								.fromObject(resourcesByAppAndUser);
						JSONArray json2 = JSONArray.fromObject(hacPrivilege);
						JSONObject userforjson = JSONObject
								.fromObject(userMergeDTO);// 保存登录用户信息
						hacResourceDto.setId(GenerationSequenceUtil.getUUID());
						hacResourceDto.setName(request
								.getParameter("loginName"));
						hacResourceDto.setHacReource(json.toString());
						hacResourceDto.setHacDataprivilege(json2.toString());
						hacResourceDto.setUserDetail(userforjson.toString());
						hacResourceDto.setCreateBy(loginName);
						hacResourceDto.setCreateDate(new Date());
						hacResourceDto.setUpdateBy(loginName);
						hacResourceDto.setUpdateDate(new Date());
						dtoService.save(hacResourceDto);
					}*/
					User user2 = userService.get(temp);
					if (user2!=null&&temp!=null&&!temp.getPassword().equals(user2.getPassword())) {
						clearSessionUser(request, response, USER_INFO);
						return new PublicResult<>(PublicResultConstant.FAILED,
								user2);
					}else{
						// 写入session
						if (StringUtils.isNotBlank(user2.getLoginName())
								&& StringUtils.isNotBlank(user2.getPassword())) {
							clearSessionUser(request, response, USER_INFO);
							setSession(request, response, USER_INFO, user2);
							return new PublicResult<>(PublicResultConstant.SUCCESS,
									user2);
						}
						return new PublicResult<>(PublicResultConstant.FAILED,
								user2);
					}
				} else {
					// 登录失败则根据用户名获取历史相关权限资源
					User user2 = getHacUser(user, loginName, hacResourceDto);
					// 写入session
					if (StringUtils.isNotBlank(user2.getLoginName())
							&& StringUtils.isNotBlank(user2.getPassword())) {
						clearSessionUser(request, response, USER_INFO);
						setSession(request, response, USER_INFO, user2);
						return new PublicResult<>(PublicResultConstant.SUCCESS,
								user2);
					}
					return new PublicResult<>(PublicResultConstant.FAILED,
							user2);
				}
			} catch (Exception e) {
				e.printStackTrace();
				User user2 = getHacUser(user, loginName, hacResourceDto);

				// 写入session
				setSession(request, response, USER_INFO, user2);
				if (StringUtils.isNotBlank(user2.getLoginName())
						&& StringUtils.isNotBlank(user2.getPassword())) {
					return new PublicResult<>(PublicResultConstant.SUCCESS,
							user2);
				}
				return new PublicResult<>(PublicResultConstant.FAILED, user2);

			}
		} else {
			User user = new User();
			String loginName = request.getParameter("loginName");
			String password = request.getParameter("password");
			String passwordByMD5 = MD5Util.getMd5(password);
			user.setLoginName(loginName);
			user.setPassword(passwordByMD5);
			User getUsers = userService.getByLoginNameAndPwd(user);
			setSession(request, response, USER_INFO, getUsers);
			return new PublicResult<>(PublicResultConstant.SUCCESS, getUsers);
		}
	}

	@SuppressWarnings("unchecked")
	private User getHacUser(User user, String loginName,
			HacResourceDto hacResourceDto) {
		User user2 = userService.get(user);
		if (user2!=null&&user!=null&&!user.getPassword().equals(user2.getPassword())) {
			return new User();
		} else {
			hacResourceDto.setName(loginName);
			HacResourceDto dto = dtoService.getByName(hacResourceDto);
			if (dto != null) {
				if (StringUtils.isNotBlank(dto.getHacReource())) {
					List<HacResourceDTO> resourceDTOlist = (List<HacResourceDTO>) JSONArray
							.fromObject(dto.getHacReource());
					user2.setResourceDTOList(resourceDTOlist);
				}
				if (StringUtils.isNotBlank(dto.getHacDataprivilege())) {
					List<HacDataPrivilegeDTO> privilegeDTOlist = (List<HacDataPrivilegeDTO>) JSONArray
							.fromObject(dto.getHacDataprivilege());
					user2.setPrivilegeDTOlist(privilegeDTOlist);
				}
				if (StringUtils.isNotBlank(dto.getUserDetail())) {
					JSONObject jsonObject = JSONObject.fromObject(dto
							.getUserDetail());
					UserMergeDTO usermergedto = (UserMergeDTO) JSONObject
							.toBean(jsonObject, UserMergeDTO.class);
					user2.setUserMerge(usermergedto);
				}
			}

			return user2;
		}
	}

	
	// 根据登录用户调用wenservice接口获取权限资源
	private List<HacResourceDTO> getHacResource(String loginName) {
		// 调用webservice接口获取权限资源
		HacResourceServiceClient ss = new HacResourceServiceClient();
		HacResourceServiceClientPortType port = ss
				.getHacResourceServiceClientPort();
		UserSourceAndScode s = new UserSourceAndScode();

		s.setSCode("1.0");
		s.setUserSource("portal");
		List<HacResourceDTO> resourcesByAppAndUser = port
				.getResourcesByAppAndUser("S00870", loginName, "zh_CN", "1.0",
						s);
		port.getResourcesByAppAndUser("S00870", loginName, "zh_CN", "1.0", s);
		return resourcesByAppAndUser;
	}
    /**
     * 后台首页 展示个数
     * @author lixiaoyi
     * @date 2018年6月25日 上午9:42:55
     * @TODO
     */
 /*   @GetMapping(value = "/main/count", produces = { "application/json;charset=UTF-8" })
   	@Log(description="API接口:/main/count")
       public Object mainCount(HttpServletRequest request){
          User user=	 getLoginUser(request);
          MainPageNum num=userService.getNum("1");
    	return new PublicResult<>(PublicResultConstant.SUCCESS, num);
       }*/

    
    @GetMapping(value = "/user/register", produces = { "application/json;charset=UTF-8" })
   	@Log(description="API接口:/user/register") 
    public Object registerRoot(HttpServletRequest request,HttpServletResponse response){
    	
		String loginName=request.getParameter("loginName");//登录用户名
		String password=request.getParameter("password");//密码
		String password2=request.getParameter("password2");
		String worknumber=request.getParameter("worknumber");//工号
		String name=request.getParameter("name");//姓名
		String email=request.getParameter("email");//邮箱
		String phone=request.getParameter("phone");//手机号
    	
    	
         if(password.equals(password2)) {
             User user=userService.getByLoginName(loginName);
             if(user==null) {
             User registerUser=new User();
             registerUser.setId(GenerationSequenceUtil.getUUID());
             registerUser.setLoginName(loginName);
             registerUser.setPassword(MD5Util.getMd5(password));
             registerUser.setName(name);
             registerUser.setNo(worknumber);
             registerUser.setEmail(email);
             registerUser.setPhone(phone);
             registerUser.setCreateBy(registerUser.getId());
             registerUser.setCreateDate(new Date());
             registerUser.setUpdateBy(registerUser.getId());
             registerUser.setUpdateDate(new Date());
             userService.addUser(registerUser);
             }else {
            	 return new PublicResult<>(PublicResultConstant.FAILED, "用户名已经被使用");
             }
         }else {
        	 return new PublicResult<>(PublicResultConstant.FAILED, "请校验两次输入的密码是否一致");
         } 
    	return new PublicResult<>(PublicResultConstant.SUCCESS, null);	
    }
	/**
	 * 后台首页 展示个数
	 * 
	 * @author lixiaoyi
	 * @date 2018年6月25日 上午9:42:55
	 * @TODO
	 */
	@GetMapping(value = "/main/count", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/main/count")
	public Object mainCount(HttpServletRequest request) {
		User user = getLoginUser(request);
		MainPageNum num = new MainPageNum();
		if ("1".equals(user.getUserType())) {// 用户Type为1表示超级管理 可以看全部
			num = userService.getNum("");
		} else {
			num = userService.getNum(user.getId());
		}
		return new PublicResult<>(PublicResultConstant.SUCCESS, num);
	}



	/**
	 * 用户登陆
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value = "/user/logout", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/user/logout")
	public Object logout(HttpServletRequest request,
			HttpServletResponse response) {
		clearSessionUser(request, response, USER_INFO);
		return new PublicResult<>(PublicResultConstant.SUCCESS, null);
	}
}
