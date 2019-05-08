
package com.haier.datamart.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.datamart.entity.AdminDatasourceConfig;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 数据源配置 Mapper 接口
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-06-07
 */
public interface AdminDatasourceConfigMapper extends BaseMapper<AdminDatasourceConfig> {

	AdminDatasourceConfig getConfig(AdminDatasourceConfig config);
	AdminDatasourceConfig getConfigByDb(AdminDatasourceConfig config);
	int add(AdminDatasourceConfig config);
	AdminDatasourceConfig get(String id);
	List<AdminDatasourceConfig> getAll();
	List<AdminDatasourceConfig> getAllbyuid(AdminDatasourceConfig config);
	int  update(AdminDatasourceConfig config);
	int delete(String id);
	String selectByuid(@Param("uid")String uid);
	List<AdminDatasourceConfig> getAllByOfficeId(String officeId);
}

