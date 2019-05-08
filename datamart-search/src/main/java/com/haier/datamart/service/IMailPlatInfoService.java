package com.haier.datamart.service;

import com.haier.datamart.entity.MailPlatInfo;
import com.baomidou.mybatisplus.service.IService;
import com.haier.datamart.controller.BaseController;
import javax.servlet.http.HttpServletRequest;
import com.github.pagehelper.PageInfo;

/**
 * @date 2019-01-09
 * @author zuoqb123
 * 邮件抬头信息服务类
 */
public interface IMailPlatInfoService extends IService<MailPlatInfo> {
 	 /**
     * @date   2019-01-09
     * @author zuoqb123
     * @todo   邮件抬头信息新增或者修改
     */
	boolean saveOrUpdate(MailPlatInfo entity);
	/**
     * @date   2019-01-09
     * @author zuoqb123
     * @todo   邮件抬头信息逻辑删除
     */
	boolean deleteLogic(String id);
	/**
     * @date   2019-01-09
     * @author zuoqb123
     * @todo   邮件抬头信息单条数据查询
     */
	MailPlatInfo getById(String id);
	/**
     * @date   2019-01-09
     * @author zuoqb123
     * @todo   邮件抬头信息分页查询
     */
	PageInfo<MailPlatInfo> pageList(BaseController c,HttpServletRequest request,MailPlatInfo entity,Integer pageNum,Integer pageSize);
}
