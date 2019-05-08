package com.haier.datamart.service;

import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.service.IService;
import com.github.pagehelper.PageInfo;
import com.haier.datamart.controller.BaseController;
import com.haier.datamart.entity.MailReceiverManager;

/**
 * @date 2019-01-17
 * @author zuoqb123
 * 邮件收件人管理服务类
 */
public interface IMailReceiverManagerService extends IService<MailReceiverManager> {
 	 /**
     * @date   2019-01-17
     * @author zuoqb123
     * @todo   邮件收件人管理新增或者修改
     */
	boolean saveOrUpdate(MailReceiverManager entity);
	/**
     * @date   2019-01-17
     * @author zuoqb123
     * @todo   邮件收件人管理逻辑删除
     */
	boolean deleteLogic(String id);
	/**
     * @date   2019-01-17
     * @author zuoqb123
     * @todo   邮件收件人管理单条数据查询
     */
	MailReceiverManager getById(String id);
	/**
     * @date   2019-01-17
     * @author zuoqb123
     * @todo   邮件收件人管理分页查询
     */
	PageInfo<MailReceiverManager> pageList(BaseController c,HttpServletRequest request,MailReceiverManager entity,Integer pageNum,Integer pageSize);
	
}
