package com.haier.datamart.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haier.datamart.entity.ScanSubjectArea;
import com.haier.datamart.mapper.ScanSubjectAreaMapper;
import com.haier.datamart.mapper.SearchIndexMapper;
import com.haier.datamart.service.IScanSubjectAreaService;

/**
 * <p>
 * 主题域 服务实现类
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-06-07
 */
@Service
public class ScanSubjectAreaServiceImpl extends ServiceImpl<ScanSubjectAreaMapper, ScanSubjectArea> implements IScanSubjectAreaService {

	@Autowired
	private ScanSubjectAreaMapper scanSubjectAreaMapper;
	@Autowired
	private SearchIndexMapper searchIndexMapper;
	
	@Override
	public ScanSubjectArea selectbyname(String name) {
		// TODO Auto-generated method stub
		return scanSubjectAreaMapper.selectbyname(name);
	}
	@Override
	public  List<ScanSubjectArea> selectAllbyLikename(ScanSubjectArea scanSubjectArea) {
		// TODO Auto-generated method stub
		return scanSubjectAreaMapper.selectAllbyLikename(scanSubjectArea);
	}
	@Override
	public int addExcle(ScanSubjectArea scanSubjectArea) {
		// TODO Auto-generated method stub
		return scanSubjectAreaMapper.add(scanSubjectArea);
	}
	
	@Override
	public List<ScanSubjectArea> getAll() {
		// TODO Auto-generated method stub
		return scanSubjectAreaMapper.getAll();
	}

	@Override
	public int deleteSubject(String id) {
		ScanSubjectArea scanSubjectArea=new ScanSubjectArea();
		scanSubjectArea.setId(id);
		return scanSubjectAreaMapper.deleteById(scanSubjectArea);
	}

	@Override
	public int updateSubjectArea(ScanSubjectArea subjectArea) {
		return scanSubjectAreaMapper.updateSubjectArea(subjectArea);
	}

	@Override
	public List<ScanSubjectArea> selectChildrenbyId(String id) {
		// TODO Auto-generated method stub
		return scanSubjectAreaMapper.selectChildrenbyId(id);
	}
	@Override
	public List<ScanSubjectArea> getAllSubject() {
		List<ScanSubjectArea>  list=getAllSubject("0");
		for(ScanSubjectArea s:list){
			s.setChildren(getAllSubject(s.getId()));
		}
		return list;
	}

	public List<ScanSubjectArea> getAllSubject(String pid) {
		List<ScanSubjectArea>  list=scanSubjectAreaMapper.selectChildrenbyId(pid);
		for(ScanSubjectArea s:list){
			s.setChildren(getAllSubject(s.getId()));
			s.setIndexs(searchIndexMapper.getIndexBySubject(s.getId()));
		}
		return list;
	}

}
