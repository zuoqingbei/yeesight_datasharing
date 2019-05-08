package com.haier.datamart.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haier.datamart.entity.DocUploadFile;
import com.haier.datamart.entity.DocUploadFileOptHistory;
import com.haier.datamart.entity.DocUploadFileTemp;
import com.haier.datamart.mapper.DocUploadFileMapper;
import com.haier.datamart.mapper.DocUploadFileOptHistoryMapper;
import com.haier.datamart.mapper.DocUploadFileTempMapper;
import com.haier.datamart.service.IDocUploadFileOptHistoryService;
import com.haier.datamart.utils.UUIDUtils;
import com.haier.datamart.utils.JWT.utils.GsonUtil;
/**
 * 文件、文件夹操作记录表服务实现类
 * @author ZhangJiang123
 * @date 2018-12-17
 */
@Service
@Transactional
public class DocUploadFileOptHistoryServiceImpl extends ServiceImpl<DocUploadFileOptHistoryMapper, DocUploadFileOptHistory> implements IDocUploadFileOptHistoryService {

    @Autowired
    private DocUploadFileOptHistoryMapper docUploadFileOptHistoryMapper;
    
    @Autowired
    private DocUploadFileMapper docUploadFileMapper;
    
    @Autowired
    private DocUploadFileTempMapper docUploadFileTempMapper;
    
    /**
     * 最近使用添加
     */
	@Override
	public Integer recentlyUsedAdd(DocUploadFileOptHistory entity) {
		
		entity.setId(UUIDUtils.getUuid());
		String fileId = entity.getFileId();
		// 根据id判断是目录或文件
		entity.setFileType(judgeFileType(fileId));
		Date date = new Date();
		entity.setCreateDate(date);
		entity.setUpdateDate(date);
		
		return docUploadFileOptHistoryMapper.insert(entity);
	}
	
	/**
	 * 删除文件或文件夹
	 */
	@Override
	public Integer deleteFile(String fileIds,String loginName) {
		String fileTempDelFlag = "1";
		String optHistoryOptType = "4";
		String optHistoryDelFlag = "0";
		String fileType = "0";
		String[] split = fileIds.split(",");
		Integer total = 0;
		Date date = new Date();
		
		List<DocUploadFile> list = docUploadFileMapper.selectBatchIds(Arrays.asList(split));
		
		for(int i = 0;i<split.length;i++) {
			String fileId = split[i];
			DocUploadFileTemp fileTemp = new DocUploadFileTemp();
			fileTemp.setId(fileId);
			fileTemp.setDelFlag(fileTempDelFlag);
			fileTemp.setUpdateBy(loginName);
			fileTemp.setUpdateDate(date);
			
			DocUploadFileOptHistory optHistory = new DocUploadFileOptHistory();
			for(DocUploadFile docUploadFile:list) {
				if(fileId.equals(docUploadFile.getId()) ) {
					fileType = "1";
					list.remove(docUploadFile);
					break;
				}
			}
			
			DocUploadFileTemp temp = docUploadFileTempMapper.selectById(fileId);
			optHistory.setJsonData(GsonUtil.objectToJsonStr(temp));
			optHistory.setFileId(fileId);
			optHistory.setFileType(fileType);
			optHistory.setId(UUIDUtils.getUuid());
			// 操作类型 0-预览 1-下载  2-修改 3-上传 4-删除 5-恢复	
			optHistory.setOptType(optHistoryOptType);
			optHistory.setDelFlag(optHistoryDelFlag);
			optHistory.setCreateBy(loginName);
			optHistory.setCreateDate(date);
			optHistory.setUpdateBy(loginName);
			optHistory.setUpdateDate(date);
			
			docUploadFileTempMapper.updateById(fileTemp);
			int j = docUploadFileOptHistoryMapper.insert(optHistory);
			total += j;
		}
		return total;
	}
	
	/**
	 * 恢复文件或文件夹
	 */
	@Override
	public Integer recoveryFile(String ids,String loginName) {
		Integer total = 0;
		Date date = new Date();
		List<DocUploadFileOptHistory> list = docUploadFileOptHistoryMapper.selectBatchIds(Arrays.asList(ids.split(",")));
		for(int i = 0;i<list.size();i++) {
			DocUploadFileOptHistory history = list.get(i);
			DocUploadFileTemp fileTemp = GsonUtil.jsonStrToObject(history.getJsonData(), DocUploadFileTemp.class);
			docUploadFileTempMapper.updateById(fileTemp);
			String id = history.getId();
			history = new DocUploadFileOptHistory();
			history.setId(id);
			history.setOptType("5");
			history.setDelFlag("1");
			history.setUpdateBy(loginName);
			history.setUpdateDate(date);
			int j = docUploadFileOptHistoryMapper.updateById(history);
			total += j;
		}
		return total;
	}
	
	/**
	 * 删除回收站记录
	 */
	@Override
	public Integer deleteRecycleBinRecord(DocUploadFileOptHistory history) {
		history.setUpdateDate(new Date());
		history.setDelFlag("1");
		return docUploadFileOptHistoryMapper.updateById(history);
	}
	 
	/**
	 * 判断文件类型
	 * @param id
	 * @return
	 */
	public String judgeFileType(String id) {
		// 根据id判断是目录或文件
		DocUploadFile selectById = docUploadFileMapper.selectById(id);
		if (null != selectById) {
			return "1";
		} else {
			return "0";
		}
	}
	
}
