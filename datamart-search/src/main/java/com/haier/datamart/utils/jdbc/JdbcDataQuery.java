package com.haier.datamart.utils.jdbc;

import com.haier.datamart.service.impl.AirflowKettleSupportServiceImpl;
import com.haier.datamart.utils.jdbc.handle.ResultSetHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.sql.*;

/**
 * 2018年10月22日09:55:14
 * liuzhilong
 * jdbc的query 功能
 * Created by long on 2018/10/22.
 */
public class JdbcDataQuery implements AutoCloseable {
    private static Logger log = LoggerFactory.getLogger(AirflowKettleSupportServiceImpl.class);
    static {
        //在此注册所有的diver
        //oracle mysql sqlserver hive2

        //oracle mysql sqlserver hive2

        try {
            Class.forName("com.mysql.jdbc.Driver");//MySql
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");//Oracle
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Class.forName("org.apache.hive.jdbc.HiveDriver");//Hive&Impala
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); //SqlServer
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    Connection connection;

    public static JdbcDataQuery createJdbcDataQuery(String jdbcstring,String userName,String password){
        return  new JdbcDataQuery().createConnection(jdbcstring,userName,password);
    }

    /**
     * 2018年10月25日09:28:10
     * liuzhilong
     * 创建数据库连接
     * @param jdbcstring
     * @param userName
     * @param password
     */
    public  JdbcDataQuery createConnection(@NotNull String jdbcstring, String userName, String password){
        try {
            this.connection = DriverManager.getConnection(jdbcstring,userName,password);//创建数据库连接
        } catch (SQLException e) {
            log.error("创建数据库连接出错 "+jdbcstring+"  "+userName+" "+password,e);
            throw new RuntimeException("JdbcDataQuery,创建数据库连接出错");
        }
        return this;
    }
    /**
     * 执行查询操作
     */
    public  <T> T  query(ResultSetHandle<T> handle, String sql){

        Statement statement = null;
        ResultSet resutSet = null;
        T result = null;
        //
        try {
            log.info("执行sql "+sql);
            statement = this.connection.createStatement();
            resutSet = statement.executeQuery(sql);
            result = handle.handle(resutSet);
        } catch (SQLException e) {
            log.error("执行sql语句出错 sql: "+sql,e);
            throw  new RuntimeException("JdbcDataQuery,执行 query 操作出错");
        }finally {
            try {
                if(resutSet!=null){
                    resutSet.close();
                }
                if(statement!=null){
                    statement.close();
                }
            }catch (SQLException es){
                log.error("关闭 statement和 statement  异常: ",es);
            }
        }
        return result;
    }
    public int update(String sql){
        return 0;
    }
    /**
     * 关闭连接的操作
     * 后期可以优化成连接池 连接池的时候也要在这里关闭
     */
    @Override
    public void close() {
        try
        {
            if(connection != null){
                this.connection.close();
            }
        }catch (Exception e){

        }
    }
}
