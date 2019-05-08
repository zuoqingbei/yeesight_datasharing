package com.haier.datamart.service.impl;

import java.util.List;

import com.haier.datamart.entity.ViewSearch;
import com.haier.datamart.mapper.ViewSearchMapper;
import com.haier.datamart.service.IViewSearchService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * VIEW 服务实现类
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-05-24
 */
@Service
public class ViewSearchServiceImpl extends ServiceImpl<ViewSearchMapper, ViewSearch> implements IViewSearchService {
    @Autowired
	private ViewSearchMapper viewSearchMapper;
	@Override
	public List<ViewSearch> getBykeyword(ViewSearch view) {
		// TODO Auto-generated method stub
		return viewSearchMapper.getByKeyword(view);
	}
	@Override
	public List<ViewSearch> gettuijian(String type) {
		// TODO Auto-generated method stub
		return viewSearchMapper.getTuijian(type);
	}
	@Override
	public List<ViewSearch> getalltuijian() {
		// TODO Auto-generated method stub
		return viewSearchMapper.getallTuijian();
	}
	@Override
	public ViewSearch getCount(ViewSearch view) {
		// TODO Auto-generated method stub
		return viewSearchMapper.getCount(view);
	}
	public int changeVZSNum(String type,String id,String numType){
		
		return viewSearchMapper.changeVZSNum(type, id, numType);
	}
	@Override
	public List<ViewSearch> getAdd() {
		// TODO Auto-generated method stub
		return  viewSearchMapper.getNewadd();
	}
	@Override
	public ViewSearch getByPK(ViewSearch view) {
		// TODO Auto-generated method stub
		return  viewSearchMapper.getByPK(view);
	}
	@Override
	public List<ViewSearch> getAll(ViewSearch view) {
		// TODO Auto-generated method stub
		return viewSearchMapper.getAll(view);
	}
	@Override
	public List<ViewSearch> getByKeywordList(ViewSearch view) {
		// TODO Auto-generated method stub
		return viewSearchMapper.getByKeywordList(view);
	}

}
