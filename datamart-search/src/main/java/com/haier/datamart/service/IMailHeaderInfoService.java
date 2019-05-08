package com.haier.datamart.service;

import com.haier.datamart.entity.MailHeaderInfo;
import com.baomidou.mybatisplus.service.IService;
import com.haier.datamart.controller.BaseController;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import com.github.pagehelper.PageInfo;

/**
 * @date 2019-01-03
 * @author zuoqb123
 * 邮件抬头信息服务类
 */
public interface IMailHeaderInfoService extends IService<MailHeaderInfo> {
 	 /**
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   邮件抬头信息新增或者修改
     */
	boolean saveOrUpdate(MailHeaderInfo entity);
	/**
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   邮件抬头信息逻辑删除
     */
	boolean deleteLogic(String id);
	/**
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   邮件抬头信息单条数据查询
     */
	MailHeaderInfo getById(String id);
	/**
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   邮件抬头信息分页查询
     */
	PageInfo<MailHeaderInfo> pageList(BaseController c,HttpServletRequest request,MailHeaderInfo entity,Integer pageNum,Integer pageSize);
	/**
	 * 先删除mailId下面的列表,然后再增加
	 * @param mialId
	 * @param headerModules
	 * @return
	 */
	boolean save(String maiId, List<MailHeaderInfo> headerModules);
}
