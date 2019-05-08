package com.haier.datamart.service;

import com.haier.datamart.entity.MailHeaderModulePlaceholder;
import com.baomidou.mybatisplus.service.IService;
import com.haier.datamart.controller.BaseController;
import javax.servlet.http.HttpServletRequest;
import com.github.pagehelper.PageInfo;

/**
 * @date 2019-01-03
 * @author zuoqb123
 * 邮件抬头预设置语句中占位符与统一接口配置信息服务类
 */
public interface IMailHeaderModulePlaceholderService extends IService<MailHeaderModulePlaceholder> {
 	 /**
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   邮件抬头预设置语句中占位符与统一接口配置信息新增或者修改
     */
	boolean saveOrUpdate(MailHeaderModulePlaceholder entity);
	/**
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   邮件抬头预设置语句中占位符与统一接口配置信息逻辑删除
     */
	boolean deleteLogic(String id);
	/**
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   邮件抬头预设置语句中占位符与统一接口配置信息单条数据查询
     */
	MailHeaderModulePlaceholder getById(String id);
	/**
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   邮件抬头预设置语句中占位符与统一接口配置信息分页查询
     */
	PageInfo<MailHeaderModulePlaceholder> pageList(BaseController c,HttpServletRequest request,MailHeaderModulePlaceholder entity,Integer pageNum,Integer pageSize);
}
