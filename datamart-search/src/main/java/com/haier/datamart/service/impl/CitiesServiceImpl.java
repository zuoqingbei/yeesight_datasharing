package com.haier.datamart.service.impl;

import com.haier.datamart.entity.Cities;
import com.haier.datamart.mapper.CitiesMapper;
import com.haier.datamart.service.ICitiesService;
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
public class CitiesServiceImpl extends ServiceImpl<CitiesMapper, Cities>
		implements ICitiesService {

}
