package com.haier.datamart.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haier.datamart.entity.AdminColumnRuleStrategy;
import com.haier.datamart.entity.AdminContentOpenStrategy;
import com.haier.datamart.entity.AdminDataStrategy;
import com.haier.datamart.entity.AdminDataStrategyDetail;
import com.haier.datamart.entity.AdminOpenStrategyContentDetail;
import com.haier.datamart.entity.AdminRuleStrategyContentDetail;
import com.haier.datamart.entity.AdminUserStrategy;
import com.haier.datamart.entity.MainPageNum;
import com.haier.datamart.entity.Menu;
import com.haier.datamart.entity.Role;
import com.haier.datamart.entity.User;
import com.haier.datamart.mapper.AdminColumnRuleStrategyMapper;
import com.haier.datamart.mapper.AdminContentOpenStrategyMapper;
import com.haier.datamart.mapper.AdminDataContentDetailMapper;
import com.haier.datamart.mapper.AdminDataContentMapper;
import com.haier.datamart.mapper.AdminDataStrategyDetailMapper;
import com.haier.datamart.mapper.AdminDataStrategyMapper;
import com.haier.datamart.mapper.AdminDatasourceConfigMapper;
import com.haier.datamart.mapper.AdminExceptionMessageMapper;
import com.haier.datamart.mapper.AdminMonitorLeaderMapper;
import com.haier.datamart.mapper.AdminOpenStrategyContentDetailMapper;
import com.haier.datamart.mapper.AdminRuleStrategyContentDetailMapper;
import com.haier.datamart.mapper.AdminStrategyApplyMapper;
import com.haier.datamart.mapper.AdminUserStrategyMapper;
import com.haier.datamart.mapper.MenuMapper;
import com.haier.datamart.mapper.RoleMapper;
import com.haier.datamart.mapper.ScanIndexTableRelativeMapper;
import com.haier.datamart.mapper.SearchIndexMapper;
import com.haier.datamart.mapper.UserMapper;
import com.haier.datamart.service.IMenuService;
import com.haier.datamart.service.IUserService;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-05-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements
		IUserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private AdminUserStrategyMapper userStrategyMapper;
	@Autowired
	private AdminDataStrategyMapper dataStrategyMapper;
	@Autowired
	private AdminColumnRuleStrategyMapper ruleStrategyMapper;
	@Autowired
	private AdminContentOpenStrategyMapper openStrategyMapper;
	@Autowired
	private AdminDataStrategyDetailMapper dataStrategyDetailMapper;
	@Autowired
	private AdminRuleStrategyContentDetailMapper ruleDetailMapper;
	@Autowired
	private AdminOpenStrategyContentDetailMapper openDetailMapper;
	@Autowired
	private AdminDatasourceConfigMapper configMapper;
	@Autowired
	private AdminDataContentMapper contentMapper;
	@Autowired
	private AdminDataContentDetailMapper detailMapper;
	@Autowired
	private AdminStrategyApplyMapper applyMapper;
	@Autowired
	private AdminMonitorLeaderMapper monitorMapper;
	@Autowired
	private AdminExceptionMessageMapper exceptionMapper;
	@Autowired
	private ScanIndexTableRelativeMapper relativeMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private SearchIndexMapper searchIndexMapper;
    @Autowired
	private IMenuService menuService;

	@Override
	public int addUser(User user) {
		// TODO Auto-generated method stub
		return userMapper.adduser(user);
	}

	@Override
	public int updateUser(User user) {
		// TODO Auto-generated method stub
		return userMapper.updateUser(user);
	}

	@Override
	public int deleteUser(String id) {
		// TODO Auto-generated method stub
		return userMapper.deleteUser(id);
	}

	@Override
	public User selectOne(String id) {
		// TODO Auto-generated method stub
		return userMapper.selectOne(id);
	}

	@Override
	public User get(User user) {
		User user2 = userMapper.get(user);

		List<AdminDataStrategy> data = new ArrayList<AdminDataStrategy>();
		List<AdminContentOpenStrategy> open = new ArrayList<AdminContentOpenStrategy>();
		List<AdminColumnRuleStrategy> rule = new ArrayList<AdminColumnRuleStrategy>();

		if (user2 != null) {
			user2.setLoginDate(new Date());
			// 用户存在 则查询用户策略关联表
			List<AdminUserStrategy> userStrategies = userStrategyMapper
					.getByuserId(user2.getId());
			// 循环 策略 判断是哪一种策略 则查询用户的 列策略，行策略，开放策略
			for (AdminUserStrategy adminUserStrategy : userStrategies) {
				if ("数据策略".equals(adminUserStrategy.getStrategyType())) {
					AdminDataStrategy dataStrategy = dataStrategyMapper
							.getbyid(adminUserStrategy.getDataStrategyId());

					// 查询策略详情列表
					List<AdminDataStrategyDetail> dataDetails = dataStrategyDetailMapper
							.getbyid(dataStrategy.getId());
					// 定义策略详情集合
					List<AdminDataStrategyDetail> details = new ArrayList<AdminDataStrategyDetail>();
					// 循环策略详情列表
					for (AdminDataStrategyDetail adminDataStrategyDetail : dataDetails) {
						// 添加策略详情集合
						details.add(adminDataStrategyDetail);
					}
					dataStrategy.setDetails(dataDetails);
					data.add(dataStrategy);
				}
				if ("开放策略".equals(adminUserStrategy.getStrategyType())) {
					AdminContentOpenStrategy openStrategy = openStrategyMapper
							.getbyid(adminUserStrategy.getDataStrategyId());

					// 根据开放策略id查询开放策略详情
					List<AdminOpenStrategyContentDetail> openDetails = openDetailMapper
							.getbyid(openStrategy.getId());
					// 定义开放策略详情的集合
					List<AdminOpenStrategyContentDetail> openD = new ArrayList<AdminOpenStrategyContentDetail>();
					// 循环开放策略详情list
					for (AdminOpenStrategyContentDetail adminOpenStrategyContentDetail : openD) {
						// 添加开放策略到集合
						openD.add(adminOpenStrategyContentDetail);
					}
					// 把策略详情集合添加到开放策略
					openStrategy.setOpenStrategyContentDetails(openDetails);
					open.add(openStrategy);
				}
				if ("列规则策略".equals(adminUserStrategy.getStrategyType())) {
					AdminColumnRuleStrategy columnRuleStrategy = ruleStrategyMapper
							.getbyid(adminUserStrategy.getDataStrategyId());

					// 获取列规则策略详情的list
					List<AdminRuleStrategyContentDetail> ruleStrategyContentDetails = ruleDetailMapper
							.getbyid(columnRuleStrategy.getId());
					// 定义列规则策略详情的集合
					List<AdminRuleStrategyContentDetail> ruleDetails = new ArrayList<AdminRuleStrategyContentDetail>();
					for (AdminRuleStrategyContentDetail adminRuleStrategyContentDetail : ruleStrategyContentDetails) {
						ruleDetails.add(adminRuleStrategyContentDetail);
					}
					columnRuleStrategy
							.setRuleStrategyContentDetails(ruleDetails);

					rule.add(columnRuleStrategy);
				}
			}

		} else {
			user2 = new User();
		}
		user2.setOpenStrategies(open);
		user2.setRuleStrategy(rule);
		user2.setDataStrategies(data);
		//用户角色
		List<Role> roles=roleMapper.getRoleByUserId(user2.getId());
		user2.setRoles(roles);
		boolean hasEntering=false;//是否拥有补录权限
	    boolean hasAdmin=false;//是否拥有后台管理权限
		for(Role r:roles){
			if("1".equals(r.getRoleType())){
				hasAdmin=true;
				break;
			}
		}
		user2.setHasAdmin(hasAdmin);
		for(Role r:roles){
			if("2".equals(r.getRoleType())){
				hasEntering=true;
				break;
			}
		}
		user2.setHasEntering(hasEntering);
		//用户菜单
		List<Menu> menus = new ArrayList<Menu>();
		if (StringUtils.isNotBlank(user2.getId())) {
			if(hasAdmin){
				menus=menuService.getAllMenu();
			}else{
				menus=menuMapper.getMenuByUserName(user2.getId(), "0","");
				//查询子菜单
				for(Menu m:menus){
					List<Menu> cmenus =menuMapper.getMenuByUserName(user2.getId(), m.getId(),"");
					m.setChildrens(cmenus);
				}
			}
		}
		user2.setMenus(menus);
		//用户指标
		user2.setIndexList(searchIndexMapper.getUserSeeIndex(user2.getId()));
		return user2;
	}

	@Override
	public MainPageNum getNum(String uid) {
		MainPageNum num = new MainPageNum();
		num.setDataNum(configMapper.selectByuid(uid));
		num.setTableNum(contentMapper.selectbyuid(uid));
		num.setColumnNum(detailMapper.selectByuid(uid));
		num.setOpenStrategyNum(openStrategyMapper.selectByuid(uid));
		num.setDataStrategyNum(dataStrategyMapper.selectByuid(uid));
		num.setColumnStrategyNum(ruleStrategyMapper.selectByuid(uid));
		num.setApplyNum(applyMapper.selectByuid(uid));
		num.setJiankongNum(monitorMapper.selectByuid(uid));
		num.setJiankongexception(exceptionMapper.selectByuid(uid));
		num.setIndexNum(relativeMapper.selectByuid(uid));
		num.setDataUseNum(userStrategyMapper.selectByuid(uid));
		return num;
	}

	@Override
	public List<User> getUserIdByLoginName(String loginName) {
		return userMapper.getUserIdByLoginName(loginName);
	}

	@Override
	public User getByLoginName(String loginName) {
		// TODO Auto-generated method stub
		return userMapper.getByLoginName(loginName);
	}

	@Override
	public User getByLoginNameAndPwd(User user) {
		// TODO Auto-generated method stub
		User user2=userMapper.get(user);
		if(user2!=null){
			//用户菜单
			List<Menu> menus = new ArrayList<Menu>();
			if (StringUtils.isNotBlank(user2.getId())) {
				boolean isAdmin=("1".equals(user.getUserType()));
				if(isAdmin){
					menus=menuService.getAllMenu();
				}else{
					menus=menuMapper.getMenuByUserName(user2.getId(), "0","");
					//查询子菜单
					for(Menu m:menus){
						List<Menu> cmenus =menuMapper.getMenuByUserName(user2.getId(), m.getId(),"");
						m.setChildrens(cmenus);
					}
				}
				
			}
			user2.setMenus(menus);
			//用户角色
			List<Role> roles=roleMapper.getRoleByUserId(user2.getId());
			user2.setRoles(roles);
			boolean hasEntering=false;//是否拥有补录权限
			boolean hasAdmin=false;//是否拥有后台管理权限
			for(Role r:roles){
				if("1".equals(r.getRoleType())){
					hasAdmin=true;
					break;
				}
			}
			user2.setHasAdmin(hasAdmin);
			for(Role r:roles){
				if("2".equals(r.getRoleType())){
					hasEntering=true;
					break;
				}
			}
			user2.setHasEntering(hasEntering);
		}
		return user2;
	}

}
