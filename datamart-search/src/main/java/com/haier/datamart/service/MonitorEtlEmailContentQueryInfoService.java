package com.haier.datamart.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.github.pagehelper.PageInfo;
import com.haier.datamart.controller.BaseController;
import com.haier.datamart.entity.MailForQueryBeanParent;
import com.haier.datamart.entity.MailForQueryBeanTableMap;
import com.haier.datamart.entity.MonitorEtlEmailContentQueryInfo;
import com.haier.datamart.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 邮件内容数据查询信息 Service层
 * @author: ZhangJiang
 * @date: 2018-12-10 下午4:55:54
 */
public interface MonitorEtlEmailContentQueryInfoService extends IService<MonitorEtlEmailContentQueryInfo> {
	/**
	 * 数据添加
	 */									 
	//int addByTheMail(String tmplateId, List<MonitorEtlEmailContentQueryInfo> dataList, User user) throws Exception;
	
	/**
	 * 数据删除
	 */
	int deleteByTmplateId(String tmplateId, User user);
	
	/**
	 *	根据模板id获取列表 
	 */
	List<MonitorEtlEmailContentQueryInfo> selectListCustom(MonitorEtlEmailContentQueryInfo monitorEtlEmailContentQueryInfo);
	
	/**
	 * 根据模板id获取数据，执行数据每一条sql语句，返回结果集
	 */
	Map<Integer, List<List<String>>> getResultLists(MonitorEtlEmailContentQueryInfo monitorEtlEmailContentQueryInfo);
	
	/**
	 * 统一接口数据查询
	 */
	MailForQueryBeanParent unifiedInterfaceDataQuery(String mailId);
	public void triggerSend(String mailId,String mailSendTo,String mailCC,String messageSendTO, String userId);
	/**
	 * @date   2019-01-03
	 * @author zuoqb123
	 * @todo   邮件模板信息（后台配置，给业务前台使用）新增或者修改
	 */
	boolean saveOrUpdate(MonitorEtlEmailContentQueryInfo entity);
	/**
	 * @date   2019-01-03
	 * @author zuoqb123
	 * @todo   邮件模板信息（后台配置，给业务前台使用）逻辑删除
	 */
	boolean deleteLogic(String id);
	/**
	 * @date   2019-01-03
	 * @author zuoqb123
	 * @todo   邮件模板信息（后台配置，给业务前台使用）单条数据查询
	 */
	MonitorEtlEmailContentQueryInfo getById(String id);
	/**
	 * @date   2019-01-09
	 * @author zuoqb123
	 * @todo   邮件抬头信息分页查询
	 */
	PageInfo<MonitorEtlEmailContentQueryInfo> pageList(BaseController c, HttpServletRequest request, MonitorEtlEmailContentQueryInfo entity, Integer pageNum, Integer pageSize);

	List<MailForQueryBeanTableMap> unifiedInterfaceDataQueryByTemplateId(String templateId);

	void singleTriggerSend(String content, String mailSendTo, String mailCC, String userId, String string) throws Exception;
}
