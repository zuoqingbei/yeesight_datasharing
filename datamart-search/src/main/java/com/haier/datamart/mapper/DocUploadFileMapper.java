package com.haier.datamart.mapper;
import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haier.datamart.entity.DocUploadFile;

/**
  * 文件上传记录表Mapper接口
 * @author zuoqb123
 * @date 2018-10-24
 */
public interface DocUploadFileMapper extends BaseMapper<DocUploadFile> {
	List<DocUploadFile> getFilesByMapper(DocUploadFile docUploadFile);
	List<DocUploadFile> getFilesByMapperNew(DocUploadFile docUploadFile);
}


