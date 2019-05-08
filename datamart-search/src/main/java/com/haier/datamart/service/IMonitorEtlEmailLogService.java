package com.haier.datamart.service;

import com.haier.datamart.entity.MonitorEtlEmailLog;
import com.baomidou.mybatisplus.service.IService;
import com.haier.datamart.controller.BaseController;
import javax.servlet.http.HttpServletRequest;
import com.github.pagehelper.PageInfo;

/**
 * @date 2019-01-25
 * @author zuoqb123
 * 服务类
 */
public interface IMonitorEtlEmailLogService extends IService<MonitorEtlEmailLog> {
 	 /**
     * @date   2019-01-25
     * @author zuoqb123
     * @todo   新增或者修改
     */
	boolean saveOrUpdate(MonitorEtlEmailLog entity);
	/**
     * @date   2019-01-25
     * @author zuoqb123
     * @todo   逻辑删除
     */
	boolean deleteLogic(String id);
	/**
     * @date   2019-01-25
     * @author zuoqb123
     * @todo   单条数据查询
     */
	MonitorEtlEmailLog getById(String id);
	/**
     * @date   2019-01-25
     * @author zuoqb123
     * @todo   分页查询
     */
	PageInfo<MonitorEtlEmailLog> pageList(BaseController c,HttpServletRequest request,MonitorEtlEmailLog entity,Integer pageNum,Integer pageSize);
}
