package com.haier.datamart.service;

import java.util.List;

import com.haier.datamart.entity.DocUploadFile;
import com.haier.datamart.entity.DocUploadFileTemp;
import com.baomidou.mybatisplus.service.IService;


/**
 * 文件上传目录表服务类
 * @author zuoqb123
 * @date 2018-11-10
 */
public interface IDocUploadFileTempService extends IService<DocUploadFileTemp> {
	List<DocUploadFileTemp> findList(DocUploadFileTemp temp);
	List<DocUploadFileTemp> findAllList(DocUploadFileTemp temp);
	List<DocUploadFileTemp> findAllListNew(DocUploadFileTemp entity);
}
