package com.haier.datamart.service.impl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.datamart.entity.AdminDatasourceConfig;
import com.haier.datamart.service.IAdminDatasourceConfigService;
import com.haier.datamart.service.SqlStringHandleService;
import com.haier.datamart.utils.ExcelConnection;


/**
 * <p>
 * sql字符串处理 ServiceImpl层
 * </p>
 *
 * @author ZhangJiang
 */
@Service
public class SqlStringHandleServiceImpl implements SqlStringHandleService {
	
	@Autowired
	private IAdminDatasourceConfigService configService;

	@Override
	public List<String> getFieldNamesBySql(String id, String sql) {
		try {
			List<String> list = null;
			// 1.获取连接数据库对象
			Connection conn = getConnection(id);
			// 2.通过数据库的连接操作数据库，实现增删改查（使用Statement类）
			Statement st = conn.createStatement();
			// 3.执行sql操作
			ResultSet rs = st.executeQuery(sql);
			// 4.处理数据库的返回结果(使用ResultSet类)
			ResultSetMetaData rsmd = rs.getMetaData();// rs为查询结果集
			int count = rsmd.getColumnCount();
			list = new ArrayList<String>();
			for (int i = 1; i <= count; i++) {
				list.add(rsmd.getColumnName(i));// 把列名存入向量list中
			}
			// 关闭连接资源
			JDBCClose(rs, st, conn);

			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<List<String>> getSqlQueryReturnList(String id, String sql) {
		try {
			List<List<String>> list = null;
			// 1.获取连接数据库对象
			Connection conn = getConnection(id);
			// 2.通过数据库的连接操作数据库，实现增删改查（使用Statement类）
			Statement st = conn.createStatement();
			// 3.执行sql操作
			ResultSet rs = st.executeQuery(sql);
			// 4.处理数据库的返回结果(使用ResultSet类)
			ResultSetMetaData rsmd = rs.getMetaData();// rs为查询结果集
			int count = rsmd.getColumnCount()+ 1;

			list = new ArrayList<List<String>>();

			List<String> columnNameList = new ArrayList<String>();
			for (int i = 1; i <= count; i++) {
				columnNameList.add(rsmd.getColumnName(i));// 把列名存入向量list中
			}
			list.add(columnNameList);
			while (rs.next()) {
				List<String> record = new ArrayList<String>();
				for (int i = 1; i < count; i++) {
					record.add(rs.getString(i));
				}
				list.add(record);
			}
			// 关闭连接资源
			JDBCClose(rs, st, conn);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Map<String, Object>> getSqlQueryReturnList(String id, String sql,String Flag) {
		try {
			List<Map<String, Object>> list =  new ArrayList<Map<String, Object>>();;
			
			// 1.获取连接数据库对象
			Connection conn = getConnection(id);
			// 2.通过数据库的连接操作数据库，实现增删改查（使用Statement类）
			Statement st = conn.createStatement();
			// 3.执行sql操作
			ResultSet rs = st.executeQuery(sql);
			// 4.处理数据库的返回结果(使用ResultSet类)
			ResultSetMetaData rsmd = rs.getMetaData();// rs为查询结果集
			int count = rsmd.getColumnCount() + 1;
			while (rs.next()) {
				Map<String,Object> map = new HashMap<String,Object>();
				for (int i = 1; i < count; i++) {
					String string = rs.getString(i);
					if(StringUtils.isBlank(string)) {
						string = "";
					}
					map.put(rsmd.getColumnName(i), string);
				}
				list.add(map);
			}
			// 关闭连接资源
			JDBCClose(rs, st, conn);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// 获取连接数据库对象
	public Connection getConnection(String id) throws Exception {
		AdminDatasourceConfig config = configService.get(id);
		// 1.设置驱动 通过反射设置interface_jdbc_driver的值
		Class<ExcelConnection> connectionClass = ExcelConnection.class;
		Field field = connectionClass.getDeclaredField("interface_jdbc_driver");
		field.setAccessible(true);
		field.set(null, config.getDbDiver());
		field.setAccessible(false);
		// 2.连接数据库
		Connection conn = ExcelConnection.getConn(config.getDbUrl(), config.getDbName(), config.getDbPassword());
		return conn;
	}

	// 关闭连接资源
	public void JDBCClose(ResultSet rs, java.sql.Statement statement, Connection connection) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
