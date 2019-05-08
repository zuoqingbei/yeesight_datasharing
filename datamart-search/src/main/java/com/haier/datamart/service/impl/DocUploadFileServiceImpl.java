package com.haier.datamart.service.impl;

import java.util.List;

import com.haier.datamart.entity.DocUploadFile;
import com.haier.datamart.mapper.DocUploadFileMapper;
import com.haier.datamart.service.IDocUploadFileService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
/**
 * 文件上传记录表服务实现类
 * @author zuoqb123
 * @date 2018-10-24
 */
@Service
@Transactional
public class DocUploadFileServiceImpl extends ServiceImpl<DocUploadFileMapper, DocUploadFile> implements IDocUploadFileService {

    @Autowired
    private DocUploadFileMapper docUploadFileMapper;

	@Override
	public List<DocUploadFile> getFilesByMapper(DocUploadFile docUploadFile) {
		return docUploadFileMapper.getFilesByMapper(docUploadFile);
	}

	@Override
	public List<DocUploadFile> getFilesByMapperNew(DocUploadFile docUploadFile) {
		return docUploadFileMapper.getFilesByMapperNew(docUploadFile);
	}
   
}
