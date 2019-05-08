package com.haier.datamart.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.util.StringUtil;
import com.haier.datamart.entity.MonitorEtlControlParam;
import com.haier.datamart.entity.MonitorEtlParamM1;
import com.haier.datamart.entity.MonitorEtlParamM2;
import com.haier.datamart.entity.MonitorEtlParamM3;
import com.haier.datamart.entity.MonitorEtlParamM4;
import com.haier.datamart.entity.MonitorEtlParamM5;
import com.haier.datamart.entity.MonitorEtlParamM6;
import com.haier.datamart.mapper.MinitorEtlControlParamMapper;
import com.haier.datamart.mapper.MonitorEtlParamM1Mapper;
import com.haier.datamart.mapper.MonitorEtlParamM2Mapper;
import com.haier.datamart.mapper.MonitorEtlParamM3Mapper;
import com.haier.datamart.mapper.MonitorEtlParamM4Mapper;
import com.haier.datamart.mapper.MonitorEtlParamM5Mapper;
import com.haier.datamart.mapper.MonitorEtlParamM6Mapper;
import com.haier.datamart.service.IMonitorService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author leizhiguo123
 * @since 2018-07-19
 */
@Service
@Transactional
public class MonitorServiceImpl  implements IMonitorService {
	@Resource
	private MonitorEtlParamM1Mapper paramM1Mapper;
	@Resource
	private MonitorEtlParamM2Mapper paramM2Mapper;
	@Resource
	private MonitorEtlParamM3Mapper paramM3Mapper;
	@Resource
	private MonitorEtlParamM4Mapper paramM4Mapper;
	@Resource
	private MonitorEtlParamM5Mapper paramM5Mapper;
	@Resource
	private MonitorEtlParamM6Mapper paramM6Mapper;
	@Resource
	private MinitorEtlControlParamMapper minitorEtlControlParamMapper;

	@Override
	public MonitorEtlControlParam getConfigById(String configId) {
		return minitorEtlControlParamMapper.getConfigById(configId);
	}
	@Override
	public void deleteModel(String moduleId,String etlTypeId) {
		String type = etlTypeId.charAt(etlTypeId.length()-1)+"";
		
		switch (type) {
		case "1":
			paramM1Mapper.deleteModel1(moduleId);
			break;
		case "2":
			paramM2Mapper.deleteModel2(moduleId);
			break;
		case "3":
			paramM3Mapper.deleteModel3(moduleId);
			break;
		case "4":
			paramM4Mapper.deleteModel4(moduleId);
			break;
		case "5":
			 paramM5Mapper.deleteModel5(moduleId);
			break;
		case "6":
			paramM6Mapper.deleteModel6(moduleId);
			break;
		default:
		}
		minitorEtlControlParamMapper.delete(moduleId);
	}
	
	@Override
	public MonitorEtlControlParam getConfigBymoduleId(String moduleId) {
		return minitorEtlControlParamMapper.getConfigBymoduleId(moduleId);
	}
	
	
	

	
	/*模板一*/
	@Override
	public boolean addModel1One(MonitorEtlParamM1 paramM1, MonitorEtlControlParam mecp) {
		if(StringUtil.isNotEmpty(mecp.getModuleName())&&StringUtil.isNotEmpty(mecp.getIndexId())){
			return paramM1Mapper.addParam1(paramM1)==1&&minitorEtlControlParamMapper.addOne(mecp)==1;
		}
		return false;
	}

	@Override
	public boolean updateParamM1(MonitorEtlParamM1 paramM1, MonitorEtlControlParam mecp) {
		if(StringUtil.isNotEmpty(mecp.getModuleName())&&StringUtil.isNotEmpty(mecp.getIndexId())){
			return paramM1Mapper.updateParamM1(paramM1)==1&&minitorEtlControlParamMapper.updateMECP(mecp)==1;
		}
		return false;
	}

	@Override
	public MonitorEtlParamM1 getModel1ById(String moudelId) {
		return paramM1Mapper.getModel1ById(moudelId);
	}

	/*模板2*/
	@Override
	public boolean addModel2One(MonitorEtlParamM2 paramM2, MonitorEtlControlParam mecp) {
		if(StringUtil.isNotEmpty(mecp.getModuleName())&&StringUtil.isNotEmpty(mecp.getIndexId())){
			return paramM2Mapper.addParam2(paramM2)==1&&minitorEtlControlParamMapper.addOne(mecp)==1;
		}
		return false;
	}

	@Override
	public boolean updateParamM2(MonitorEtlParamM2 paramM2, MonitorEtlControlParam mecp) {
		if(StringUtil.isNotEmpty(mecp.getModuleName())&&StringUtil.isNotEmpty(mecp.getIndexId())){
			return paramM2Mapper.updateParamM2(paramM2)==1&&minitorEtlControlParamMapper.updateMECP(mecp)==1;
		}
		return false;
	}

	@Override
	public MonitorEtlParamM2 getModel2ById(String moudelId) {
		return paramM2Mapper.getModel2ById(moudelId);
	}
	
	/*模板3*/
	@Override
	public boolean addModel3One(MonitorEtlParamM3 paramM3, MonitorEtlControlParam mecp) {
		if(StringUtil.isNotEmpty(mecp.getModuleName())&&StringUtil.isNotEmpty(mecp.getIndexId())){
			return paramM3Mapper.addParam3(paramM3)==1&&minitorEtlControlParamMapper.addOne(mecp)==1;
		}
		return false;
	}

	@Override
	public boolean updateParamM3(MonitorEtlParamM3 paramM3, MonitorEtlControlParam mecp) {
		if(StringUtil.isNotEmpty(mecp.getModuleName())&&StringUtil.isNotEmpty(mecp.getIndexId())){
			return paramM3Mapper.updateParamM3(paramM3)==1&&minitorEtlControlParamMapper.updateMECP(mecp)==1;
		}
		return false;
	}

	@Override
	public MonitorEtlParamM3 getModel3ById(String moudelId) {
		return paramM3Mapper.getModel3ById(moudelId);
	}
	
	/*模板4*/
	@Override
	public boolean addModel4One(MonitorEtlParamM4 paramM4, MonitorEtlControlParam mecp) {
		if(StringUtil.isNotEmpty(mecp.getModuleName())&&StringUtil.isNotEmpty(mecp.getIndexId())){
			return paramM4Mapper.addParam4(paramM4)==1&&minitorEtlControlParamMapper.addOne(mecp)==1;
		}
		return false;
	}

	@Override
	public boolean updateParamM4(MonitorEtlParamM4 paramM4, MonitorEtlControlParam mecp) {
		if(StringUtil.isNotEmpty(mecp.getModuleName())&&StringUtil.isNotEmpty(mecp.getIndexId())){
			return paramM4Mapper.updateParamM4(paramM4)==1&&minitorEtlControlParamMapper.updateMECP(mecp)==1;
		}
		return false;
	}

	@Override
	public MonitorEtlParamM4 getModel4ById(String moudelId) {
		return paramM4Mapper.getModel4ById(moudelId);
	}
	
	/*模板5*/
	@Override
	public boolean addModel5One(MonitorEtlParamM5 paramM5, MonitorEtlControlParam mecp) {
		if(StringUtil.isNotEmpty(mecp.getModuleName())&&StringUtil.isNotEmpty(mecp.getIndexId())){
			return paramM5Mapper.addParam5(paramM5)==1&&minitorEtlControlParamMapper.addOne(mecp)==1;
		}
		return false;
	}

	@Override
	public boolean updateParamM5(MonitorEtlParamM5 paramM5, MonitorEtlControlParam mecp) {
		if(StringUtil.isNotEmpty(mecp.getModuleName())&&StringUtil.isNotEmpty(mecp.getIndexId())){
			return paramM5Mapper.updateParamM5(paramM5)==1&&minitorEtlControlParamMapper.updateMECP(mecp)==1;
		}
		return false;
	}

	@Override
	public MonitorEtlParamM5 getModel5ById(String moudelId) {
		return paramM5Mapper.getModel5ById(moudelId);
	}
	
	/*模板6*/
	@Override
	public boolean addModel6One(MonitorEtlParamM6 paramM6, MonitorEtlControlParam mecp) {
		if(StringUtil.isNotEmpty(mecp.getModuleName())&&StringUtil.isNotEmpty(mecp.getIndexId())){
			return paramM6Mapper.addParam6(paramM6)==1&&minitorEtlControlParamMapper.addOne(mecp)==1;
		}
		return false;
	}

	@Override
	public boolean updateParamM6(MonitorEtlParamM6 paramM6, MonitorEtlControlParam mecp) {
		if(StringUtil.isNotEmpty(mecp.getModuleName())&&StringUtil.isNotEmpty(mecp.getIndexId())){
			return paramM6Mapper.updateParamM6(paramM6)==1&&minitorEtlControlParamMapper.updateMECP(mecp)==1;
		}
		return false;
		
	}

	@Override
	public MonitorEtlParamM6 getModel6ById(String moudelId) {
		return paramM6Mapper.getModel6ById(moudelId);
	}
	@Override
	public boolean checkIsExist(String indexId) {
		return minitorEtlControlParamMapper.checkIsExist(indexId)>=1;
	}
	
	/*获取所有控制参数的集合*/
	@Override
	public List<MonitorEtlControlParam> getControllerParam() {
		return minitorEtlControlParamMapper.getControllerParam();
	}
	
	@Override
	public List<MonitorEtlControlParam> fuzzSearch(String moduleName, String remarks, String userId) {
		return minitorEtlControlParamMapper.fuzzSearch(moduleName,remarks,userId);
	}
	@Override
	public List<MonitorEtlControlParam> getModuleIdByIndexId(String index) {
		return minitorEtlControlParamMapper.getModuleIdByIndexId(index);
	}
	@Override
	public List<MonitorEtlControlParam> getMonitorListByContentId(
			String contentId) {
		return minitorEtlControlParamMapper.getMonitorListByContentId(contentId);
	}
	@Override
	public List<MonitorEtlControlParam> getMonitorListByReportId(String reportId) {
		return minitorEtlControlParamMapper.getMonitorListByReportId(reportId);
	}
	@Override
	public List<MonitorEtlControlParam> getMonitorListByIndexId(String indexId) {
		return minitorEtlControlParamMapper.getMonitorListByIndexId(indexId);
	}
	
}
