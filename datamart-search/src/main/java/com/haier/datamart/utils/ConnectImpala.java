package com.haier.datamart.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class ConnectImpala {

	static String JDBC_DRIVER = "org.apache.hive.jdbc.HiveDriver";
	static String CONNECTION_URL = "jdbc:hive2://10.138.42.211:21050/dl_wl"+ "/;auth=noSasl";
	
    public static void main(String[] args)
    {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        Statement stat=null;

        try
        {
            Class.forName(JDBC_DRIVER);
            System.out.println(con+"========================");
            con = DriverManager.getConnection(CONNECTION_URL,"readonly_1169","read@1169");
           
            System.out.println(con+"========================");
           
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            //关闭rs、ps和con
        }
    }
}
