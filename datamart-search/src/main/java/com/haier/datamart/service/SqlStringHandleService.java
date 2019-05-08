package com.haier.datamart.service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * sql字符串处理 Service层
 * </p>
 *
 * @author ZhangJiang
 */
public interface SqlStringHandleService {
	List<String> getFieldNamesBySql(String id, String sql);

	List<List<String>> getSqlQueryReturnList(String id, String sql);

	List<Map<String, Object>> getSqlQueryReturnList(String id, String sql, String Flag);
}
