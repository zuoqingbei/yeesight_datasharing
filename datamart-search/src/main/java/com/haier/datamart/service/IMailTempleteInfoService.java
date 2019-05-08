package com.haier.datamart.service;

import com.haier.datamart.entity.MailTempleteInfo;
import com.baomidou.mybatisplus.service.IService;
import com.haier.datamart.controller.BaseController;
import javax.servlet.http.HttpServletRequest;
import com.github.pagehelper.PageInfo;

/**
 * @date 2019-01-03
 * @author zuoqb123
 * 邮件模板信息（后台配置，给业务前台使用）服务类
 */
public interface IMailTempleteInfoService extends IService<MailTempleteInfo> {
 	 /**
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   邮件模板信息（后台配置，给业务前台使用）新增或者修改
     */
	boolean saveOrUpdate(MailTempleteInfo entity);
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
	MailTempleteInfo getById(String id);
	/**
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   邮件模板信息（后台配置，给业务前台使用）分页查询
     */
	PageInfo<MailTempleteInfo> pageList(BaseController c,HttpServletRequest request,MailTempleteInfo entity,Integer pageNum,Integer pageSize);
}
