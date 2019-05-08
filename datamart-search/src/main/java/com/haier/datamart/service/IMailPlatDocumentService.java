package com.haier.datamart.service;

import com.haier.datamart.entity.MailPlatDocument;
import com.baomidou.mybatisplus.service.IService;
import com.haier.datamart.controller.BaseController;
import javax.servlet.http.HttpServletRequest;
import com.github.pagehelper.PageInfo;

/**
 * @date 2019-01-09
 * @author zuoqb123
 * 平台文件服务类
 */
public interface IMailPlatDocumentService extends IService<MailPlatDocument> {
 	 /**
     * @date   2019-01-09
     * @author zuoqb123
     * @todo   平台文件新增或者修改
     */
	boolean saveOrUpdate(MailPlatDocument entity);
	/**
     * @date   2019-01-09
     * @author zuoqb123
     * @todo   平台文件逻辑删除
     */
	boolean deleteLogic(String id);
	/**
     * @date   2019-01-09
     * @author zuoqb123
     * @todo   平台文件单条数据查询
     */
	MailPlatDocument getById(String id);
	/**
     * @date   2019-01-09
     * @author zuoqb123
     * @todo   平台文件分页查询
     */
	PageInfo<MailPlatDocument> pageList(BaseController c,HttpServletRequest request,MailPlatDocument entity,Integer pageNum,Integer pageSize);
}
