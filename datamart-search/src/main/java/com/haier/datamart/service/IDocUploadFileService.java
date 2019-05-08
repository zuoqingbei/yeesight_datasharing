package com.haier.datamart.service;

import java.util.List;

import com.haier.datamart.entity.DocUploadFile;
import com.baomidou.mybatisplus.service.IService;


/**
 * 文件上传记录表服务类
 * @author zuoqb123
 * @date 2018-10-24
 */
public interface IDocUploadFileService extends IService<DocUploadFile> {
	List<DocUploadFile> getFilesByMapper(DocUploadFile docUploadFile);
	List<DocUploadFile> getFilesByMapperNew(DocUploadFile docUploadFile);
	
}
