package com.haier.datamart.utils;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.commons.lang.StringUtils;

import com.haier.datamart.entity.AdminDatasourceConfig;
public class GetSqlConnection {
	static String HIVE_JDBC_DRIVER = "org.apache.hive.jdbc.HiveDriver";
	static String MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static String ORACLE_JDBC_DRIVER = "oracle.jdbc.OracleDriver";
	static String IMPALA_JDBC_DRIVER = "com.cloudera.impala.jdbc41.Driver";

	public Connection getConn(AdminDatasourceConfig sourceconfig) throws Exception {
		String db_type = sourceconfig.getDbType().trim();
		String jdbc_driver = sourceconfig.getDbDiver().trim();
		System.out.println(jdbc_driver+"----------------test---------------------");
		String connection_url = sourceconfig.getDbUrl().trim();
		if(StringUtils.isNotBlank(db_type)&&"mysql".equals(db_type)){
			connection_url+="?useUnicode=true&characterEncoding=utf8&useSSL=false";
		}
		if(StringUtils.isNotBlank(db_type)&&"impala".equals(db_type)){
			connection_url+="/;auth=noSasl";
		}
		if(StringUtils.isBlank(jdbc_driver)){
			if("mysql".equals(db_type)){
				jdbc_driver=MYSQL_JDBC_DRIVER;
			}else if("oracle".equals(db_type)){
				jdbc_driver=ORACLE_JDBC_DRIVER;
			}else if("hive".equals(db_type)){
				jdbc_driver=HIVE_JDBC_DRIVER;
			}
			else if("impala".equalsIgnoreCase(db_type)){
				connection_url+="/;auth=noSasl";
				jdbc_driver=HIVE_JDBC_DRIVER;
			}
		}
		System.out.println(connection_url);
		String user = sourceconfig.getDbName().trim();
		String pwd = sourceconfig.getDbPassword().trim();
		Connection conn = null;
		Class.forName(jdbc_driver);
		conn = DriverManager.getConnection(connection_url, user, pwd);
		return conn;
	}

}
