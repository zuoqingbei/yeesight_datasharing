package com.haier.datamart.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.service.IService;
import com.github.pagehelper.PageInfo;
import com.haier.datamart.controller.BaseController;
import com.haier.datamart.entity.MailSettingInfo;

/**
 * @date 2019-01-03
 * @author zuoqb123
 * 邮件设置信息服务类
 */
public interface IMailSettingInfoService extends IService<MailSettingInfo> {
 	 /**
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   邮件设置信息新增或者修改
     */
	boolean saveOrUpdate(MailSettingInfo entity);
	/**
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   邮件设置信息逻辑删除
     */
	boolean deleteLogic(String id);
	/**
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   邮件设置信息单条数据查询
     */
	MailSettingInfo getById(String id);
	/**
     * @date   2019-01-03
     * @author zuoqb123
     * @todo   邮件设置信息分页查询
     */
	PageInfo<MailSettingInfo> pageList(BaseController c,HttpServletRequest request,MailSettingInfo entity,Integer pageNum,Integer pageSize);
	/**
	 * 根据平台id找所在模块id
	 */
	public String getModuleIdByPlatId(String platId) ;
	Map<String,List<String>> getEmailControl(Date startDate, Date endDate);
}
