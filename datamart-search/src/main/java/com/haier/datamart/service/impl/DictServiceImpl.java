package com.haier.datamart.service.impl;

import java.util.List;

import com.haier.datamart.entity.Dict;
import com.haier.datamart.mapper.DictMapper;
import com.haier.datamart.service.IDictService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-06-05
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {
  
	@Autowired
	private DictMapper dictMapper;
	
	@Override
	public int add(Dict dict) {
		// TODO Auto-generated method stub
		return dictMapper.add(dict);
	}

	@Override
	public Dict selectByone(String name) {
		// TODO Auto-generated method stub
		return dictMapper.getByname(name);
	}

	@Override
	public List<Dict> getAll(String type) {
		// TODO Auto-generated method stub
		return dictMapper.getAll(type);
	}

	@Override
	public List<Dict> getdataType() {
		// TODO Auto-generated method stub
		return dictMapper.getDataType();
	}

	

}
