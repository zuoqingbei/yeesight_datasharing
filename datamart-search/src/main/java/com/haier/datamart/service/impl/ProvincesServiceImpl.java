package com.haier.datamart.service.impl;

import com.haier.datamart.entity.Provinces;
import com.haier.datamart.mapper.ProvincesMapper;
import com.haier.datamart.service.IProvincesService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zuoqb123
 * @since 2018-05-11
 */
@Service
public class ProvincesServiceImpl extends
		ServiceImpl<ProvincesMapper, Provinces> implements IProvincesService {

}
