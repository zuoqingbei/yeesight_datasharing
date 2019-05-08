package com.haier.datamart.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestHive {
        private static String Hive_driverName = "org.apache.hive.jdbc.HiveDriver";//jdbc驱动路径
        private static String Hive_url = "jdbc:hive2://10.138.42.216:10000/default";//hive库地址+库名
        private static String Hive_user = "readonly_1169";//用户名
        private static String Hive_password = "read@2018!";//密码
        

        public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = getConn();
            System.out.println(conn+"========================");
            

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static Connection getConn() throws ClassNotFoundException,
            SQLException {
        Class.forName(Hive_driverName);
        Connection conn = DriverManager.getConnection(Hive_url, Hive_user, Hive_password);
        return conn;
    }
}