package com.haier.datamart.service;

import java.util.List;

import com.haier.datamart.entity.Dict;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-06-05
 */
public interface IDictService extends IService<Dict> {
	Dict selectByone(String name);
	int add(Dict dict);
	
	List<Dict> getAll(String type);
    List<Dict> getdataType();
}
