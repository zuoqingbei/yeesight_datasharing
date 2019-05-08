package com.haier.datamart.service;

import com.haier.datamart.entity.MailHeaderModuleImg;
import com.baomidou.mybatisplus.service.IService;
import com.haier.datamart.controller.BaseController;
import javax.servlet.http.HttpServletRequest;
import com.github.pagehelper.PageInfo;

/**
 * @date 2019-01-03
 * @author zuoqb123
 * 邮件抬头预设置图片信息服务类
 */
public interface IMailHeaderModuleImgService extends IService<MailHeaderModuleImg> {
 	 /**
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   邮件抬头预设置图片信息新增或者修改
     */
	boolean saveOrUpdate(MailHeaderModuleImg entity);
	/**
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   邮件抬头预设置图片信息逻辑删除
     */
	boolean deleteLogic(String id);
	/**
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   邮件抬头预设置图片信息单条数据查询
     */
	MailHeaderModuleImg getById(String id);
	/**
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   邮件抬头预设置图片信息分页查询
     */
	PageInfo<MailHeaderModuleImg> pageList(BaseController c,HttpServletRequest request,MailHeaderModuleImg entity,Integer pageNum,Integer pageSize);
}
