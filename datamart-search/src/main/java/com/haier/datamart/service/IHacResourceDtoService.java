package com.haier.datamart.service;

import com.haier.datamart.entity.HacResourceDto;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 权限资源 服务类
 * </p>
 *
 * @author doushuihai123
 * @since 2018-08-08
 */
public interface IHacResourceDtoService extends IService<HacResourceDto> {
	int insertHac(HacResourceDto hacResourceDto);
	int updateHac(HacResourceDto hacResourceDto);
	int save(HacResourceDto hacResourceDto);
	HacResourceDto getByName(HacResourceDto hacResourceDto);
}
