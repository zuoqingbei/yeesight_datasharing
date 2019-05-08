package com.haier.datamart.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class ConnectImpala {

	static String JDBC_DRIVER = "org.apache.hive.jdbc.HiveDriver";
	static String CONNECTION_URL = "jdbc:hive2://10.138.42.212:21050/;auth=noSasl";
	//static String CONNECTION_URL = "jdbc:hive2://192.168.1.151:10000";
    //static String username="test";
    //static String passwd="123456";
//1:kerberos ,2:,3LDAP:
    public static void main(String[] args)
    {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        Statement stat=null;

        try
        {
            Class.forName(JDBC_DRIVER);
            con = DriverManager.getConnection(CONNECTION_URL,"hdfs","hdfs");
            stat=con.createStatement();
            rs=stat.executeQuery("SELECT IFNULL(ROUND(SUM(t2.d1)/SUM(t2.d2)*100,2),-1) AS num, t1.trade_name AS tradename FROM dl_wl.l_rrs_region_dim t1 LEFT JOIN (SELECT SUM(b1.jss) AS d1, SUM(b1.zs) AS d2, b1.ssc_center FROM dw_wl.w_fact_wl_b2c_kpi_1d b1 LEFT JOIN dl_wl.l_rrs_sourcetype_dim b2 ON b1.source = b2.source WHERE b1.ytm = '20170901' AND b1.index_id = 'WL0001000001' AND b2.model_code = '3W' GROUP BY b1.ssc_center) t2 ON t1.center_id = t2.ssc_center GROUP BY t1.trade_name ORDER BY IFNULL(ROUND(SUM(t2.d1)/SUM(t2.d2)*100,2),-1) DESC");
            //ps = con.prepareStatement("show databases");
            //ps.setQueryTimeout(0);
           // rs = ps.executeQuery();
            System.out.println(rs);
           while (rs.next())
            {
              System.out.println(rs.getString(1) + '\t' + rs.getString(2));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            //关闭rs、ps和con
        }
    }
}
