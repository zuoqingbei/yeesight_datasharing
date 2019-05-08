package com.haier.datamart.service;

import com.haier.datamart.entity.MailModuleInfo;
import com.baomidou.mybatisplus.service.IService;
import com.haier.datamart.controller.BaseController;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import com.github.pagehelper.PageInfo;

/**
 * @date 2019-01-10
 * @author zuoqb123
 * 邮件抬头信息服务类
 */
public interface IMailModuleInfoService extends IService<MailModuleInfo> {
 	 /**
     * @date   2019-01-10
     * @author zuoqb123
     * @todo   邮件抬头信息新增或者修改
     */
	boolean saveOrUpdate(MailModuleInfo entity);
	/**
     * @date   2019-01-10
     * @author zuoqb123
     * @todo   邮件抬头信息逻辑删除
     */
	boolean deleteLogic(String id);
	/**
     * @date   2019-01-10
     * @author zuoqb123
     * @todo   邮件抬头信息单条数据查询
     */
	MailModuleInfo getById(String id);
	/**
     * @date   2019-01-10
     * @author zuoqb123
     * @todo   邮件抬头信息分页查询
     */
	PageInfo<MailModuleInfo> pageList(BaseController c,HttpServletRequest request,MailModuleInfo entity,Integer pageNum,Integer pageSize);
}
