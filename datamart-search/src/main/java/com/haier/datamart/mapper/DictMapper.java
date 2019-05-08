package com.haier.datamart.mapper;

import java.util.List;

import com.haier.datamart.entity.Dict;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 字典表 Mapper 接口
 * </p>
 *
 * @author dsh123
 * @since 2018-05-29
 */
public interface DictMapper extends BaseMapper<Dict> {
	Dict get(Dict dict);
	int add(Dict dict);
	Dict getByname(String name);
	List<Dict> getAll(String type);
	List<Dict> getDataType();
}
