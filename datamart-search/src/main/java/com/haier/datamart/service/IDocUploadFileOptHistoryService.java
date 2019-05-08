package com.haier.datamart.service;

import com.haier.datamart.entity.DocUploadFileOptHistory;
import com.baomidou.mybatisplus.service.IService;


/**
 * 文件、文件夹操作记录表服务类
 * @author ZhangJiang123
 * @date 2018-12-17
 */
public interface IDocUploadFileOptHistoryService extends IService<DocUploadFileOptHistory> {

	Integer recentlyUsedAdd(DocUploadFileOptHistory entity);

	Integer deleteFile(String ids, String loginName);

	Integer recoveryFile(String ids,String loginName);

	Integer deleteRecycleBinRecord(DocUploadFileOptHistory history);
 	
}
