package com.haier.datamart.service.impl;

import com.haier.datamart.entity.HacResourceDto;
import com.haier.datamart.mapper.HacResourceDtoMapper;
import com.haier.datamart.service.IHacResourceDtoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限资源 服务实现类
 * </p>
 *
 * @author doushuihai123
 * @since 2018-08-08
 */
@Service
public class HacResourceDtoServiceImpl extends ServiceImpl<HacResourceDtoMapper, HacResourceDto> implements IHacResourceDtoService {
	@Autowired
	private HacResourceDtoMapper dtoMapper;
	@Override
	public int insertHac(HacResourceDto hacResourceDto) {
		// TODO Auto-generated method stub
		return dtoMapper.insertHac(hacResourceDto);
	}
	@Override
	public int updateHac(HacResourceDto hacResourceDto) {
		// TODO Auto-generated method stub
		return dtoMapper.updateHac(hacResourceDto);
	}
	@Override
	public int save(HacResourceDto hacResourceDto) {
		// TODO Auto-generated method stub
		HacResourceDto dto=dtoMapper.getByName(hacResourceDto);
		if(dto==null){
			return dtoMapper.insertHac(hacResourceDto);
		}else{
			hacResourceDto.setCreateBy(null);
			hacResourceDto.setCreateDate(null);
			return dtoMapper.updateHac(hacResourceDto);
		}
		
	}
	@Override
	public HacResourceDto getByName(HacResourceDto hacResourceDto) {
		// TODO Auto-generated method stub
		return dtoMapper.getByName(hacResourceDto);
	}

}
