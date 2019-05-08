package com.haier.datamart.service;

import com.haier.datamart.entity.EnteringLogs;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 补录模块-补录数据操作历史 服务类
 * </p>
 *
 * @author leizhiguo123
 * @since 2018-07-17
 */
public interface IEnteringLogsService extends IService<EnteringLogs> {
		/**
		 * 增加一条日志
		 * @param log
		 */
		void addLog(EnteringLogs log);
}
