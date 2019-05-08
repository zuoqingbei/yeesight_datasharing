package com.haier.datamart.service;

import com.haier.datamart.entity.MailHeaderInfo;
import com.haier.datamart.entity.MailHeaderModule;
import com.baomidou.mybatisplus.service.IService;
import com.haier.datamart.controller.BaseController;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.PageInfo;

/**
 * @date 2019-01-03
 * @author zuoqb123
 * 邮件抬头预设置语句服务类
 */
public interface IMailHeaderModuleService extends IService<MailHeaderModule> {
 	 /**
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   邮件抬头预设置语句新增或者修改
     */
	boolean saveOrUpdate(MailHeaderModule entity);
	/**
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   邮件抬头预设置语句逻辑删除
     */
	boolean deleteLogic(String id);
	/**
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   邮件抬头预设置语句单条数据查询
     */
	MailHeaderModule getById(String id);
	/**
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   邮件抬头预设置语句分页查询
     */
	PageInfo<MailHeaderModule> pageList(BaseController c,HttpServletRequest request,MailHeaderModule entity,Integer pageNum,Integer pageSize);
	/**
	 * 
	 * @param mailId
	 * @return 根据邮件id获取邮寄配置头
	 */
	List<MailHeaderModule> getList(String mailId);
	/**
	 * 根据邮件id拼接对应邮件头
	 * @param id
	 * @return
	 */
	String getHeaderContent(String mailId);
	List<MailHeaderInfo> getHeaderInfoListByMailId(String mailId);
	Map<String,Object> dealHeaderModule(String sourceStr);
}
