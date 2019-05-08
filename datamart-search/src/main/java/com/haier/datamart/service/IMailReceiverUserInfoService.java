package com.haier.datamart.service;

import com.haier.datamart.entity.MailReceiverUserInfo;
import com.baomidou.mybatisplus.service.IService;
import com.haier.datamart.controller.BaseController;
import javax.servlet.http.HttpServletRequest;
import com.github.pagehelper.PageInfo;

/**
 * @date 2019-01-03
 * @author zuoqb123
 * 邮件模块收件人原始信息信息服务类
 */
public interface IMailReceiverUserInfoService extends IService<MailReceiverUserInfo> {
 	 /**
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   邮件模块收件人原始信息信息新增或者修改
     */
	boolean saveOrUpdate(MailReceiverUserInfo entity);
	/**
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   邮件模块收件人原始信息信息逻辑删除
     */
	boolean deleteLogic(String id);
	/**
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   邮件模块收件人原始信息信息单条数据查询
     */
	MailReceiverUserInfo getById(String id);
	/**
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   邮件模块收件人原始信息信息分页查询
     */
	PageInfo<MailReceiverUserInfo> pageList(BaseController c,HttpServletRequest request,MailReceiverUserInfo entity,Integer pageNum,Integer pageSize);
}
