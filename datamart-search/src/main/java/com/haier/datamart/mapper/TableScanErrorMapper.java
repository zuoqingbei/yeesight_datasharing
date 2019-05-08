package com.haier.datamart.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haier.datamart.entity.TableScanError;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-06-14
 */
public interface TableScanErrorMapper extends BaseMapper<TableScanError> {
	public void insertBatchby(@Param(value="contents") List<TableScanError> contents);
	public List<TableScanError> selectForValidate(TableScanError tablescanerror);
}
