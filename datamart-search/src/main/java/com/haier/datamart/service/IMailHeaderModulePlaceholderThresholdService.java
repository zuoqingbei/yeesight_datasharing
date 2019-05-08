package com.haier.datamart.service;

import java.util.List;

import com.haier.datamart.entity.MailHeaderModulePlaceholderThreshold;
import com.baomidou.mybatisplus.service.IService;
import com.haier.datamart.controller.BaseController;

import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.PageInfo;

/**
 * @date 2019-02-13
 * @author zuoqb123
 * 邮件抬头预设置语句中占位符阈值控制服务类
 */
public interface IMailHeaderModulePlaceholderThresholdService extends IService<MailHeaderModulePlaceholderThreshold> {
 	 /**
     * @date   2019-02-13
     * @author zuoqb123
     * @todo   邮件抬头预设置语句中占位符阈值控制新增或者修改
     */
	boolean saveOrUpdate(MailHeaderModulePlaceholderThreshold entity);
	/**
     * @date   2019-02-13
     * @author zuoqb123
     * @todo   邮件抬头预设置语句中占位符阈值控制逻辑删除
     */
	boolean deleteLogic(String id);
	/**
     * @date   2019-02-13
     * @author zuoqb123
     * @todo   邮件抬头预设置语句中占位符阈值控制单条数据查询
     */
	MailHeaderModulePlaceholderThreshold getById(String id);
	/**
     * @date   2019-02-13
     * @author zuoqb123
     * @todo   邮件抬头预设置语句中占位符阈值控制分页查询
     */
	PageInfo<MailHeaderModulePlaceholderThreshold> pageList(BaseController c,HttpServletRequest request,MailHeaderModulePlaceholderThreshold entity,Integer pageNum,Integer pageSize);
	
	
	List<MailHeaderModulePlaceholderThreshold> thresholdListByPlaceholderId(String placeHolderId);
	public boolean deleteAndSave(List<MailHeaderModulePlaceholderThreshold> list,String placeHolderId);
}
