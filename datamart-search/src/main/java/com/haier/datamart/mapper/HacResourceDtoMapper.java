package com.haier.datamart.mapper;

import com.haier.datamart.entity.HacResourceDto;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 权限资源 Mapper 接口
 * </p>
 *
 * @author doushuihai123
 * @since 2018-08-08
 */
public interface HacResourceDtoMapper extends BaseMapper<HacResourceDto> {
	int insertHac(HacResourceDto hacResourceDto);
	int updateHac(HacResourceDto hacResourceDto);
	HacResourceDto getByName(HacResourceDto hacResourceDto);
 
}
