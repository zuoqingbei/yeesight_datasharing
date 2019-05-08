package com.haier.datamart.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.druid.util.JdbcConstants;
import com.github.pagehelper.util.StringUtil;
import com.haier.datamart.entity.EnteringTableSettingDetail;
import com.haier.datamart.entity.EnteringTableSettingInfo;
public class ExcelConnection {
	
	private static String interface_jdbc_driver= JdbcConstants.MYSQL_DRIVER;
	public static String defaultDataSourceId = "1";//默认数据源id
	//测试环境
	/*public static String interface_connECTION_URL = "jdbc:mysql://10.130.96.74:3306/datamart?characterEncoding=utf8";
	public static String IF_user = "root";
	public static String IF_pwd = "hl123456";
	public static String INTERFACE_URL="http://10.138.42.215:19820/common/inter";//测试
	*/
	/*private static String interface_connECTION_URL = "jdbc:mysql://10.138.22.188:3306/datamart?characterEncoding=utf8";
	private static String IF_user = "datamart";
	private static String IF_pwd = "datamart";
	public static String INTERFACE_URL="http://10.138.42.215:19821/common/inter";//正式
*/	
	private static String interface_db_url =FileUtil.bundle.getString("interface_db_url");
	private static String interface_db_user = FileUtil.bundle.getString("interface_db_user");
	private static String interface_db_pwd = FileUtil.bundle.getString("interface_db_pwd");
	public static String interface_url=FileUtil.bundle.getString("interface_url");
	private static Connection interface_conn = null;
	
	public static String PIWIK_CONNECTION_URL =FileUtil.bundle.getString("piwik_connection_url");
	public static String PIWIK_USER = FileUtil.bundle.getString("piwik_user");
	public static String PIWIK_PWD= FileUtil.bundle.getString("piwik_pwd");
	/**
	 * 统一接口jdbc连接配置
	 */
	public static Connection getConn() {
		try {
			Class.forName(interface_jdbc_driver);
			interface_conn = DriverManager.getConnection(interface_db_url, interface_db_user, interface_db_pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return interface_conn;
	}
	
	/**
	 * 统一接口jdbc连接配置
	 */
	public static Connection getPiwikConn() {
		try {
			Class.forName(interface_jdbc_driver);
			interface_conn = DriverManager.getConnection(PIWIK_CONNECTION_URL, PIWIK_USER, PIWIK_PWD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return interface_conn;
	}
	
	public static void close(Connection conn
			,Statement stat,ResultSet rs){
		 try {
			 if (rs!=null) {
				rs.close();
			 }
			 if (stat!=null) {
				stat.close();
			 }
			 if (conn!=null) {
				 //保证从池当中拿出的连接都是自动提交的
				conn.setAutoCommit(true);
				conn.close();
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private static Connection conn = null;
	public static Connection getConn(String url,String name,String password) {
		url = url.replace("?characterEncoding=utf8", "");
		url += "?characterEncoding=utf8";/*&zeroDateTimeBehavior=convertToNull*/
		try {
			/*if(conn==null){
				Class.forName(JDBC_DRIVER);
				conn = DriverManager.getConnection(CONNECTION_URL, user, pwd);
			}*/
			Class.forName(interface_jdbc_driver);
			conn = DriverManager.getConnection(url, name, password);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return conn;
	}
	public static void main(String[] args) {
		//测试新增
		EnteringTableSettingInfo setting=new EnteringTableSettingInfo();
		setting.setName("zuo_test");
		setting.setDescs("我是测试");
		/*666*/
		List<EnteringTableSettingDetail> list=new ArrayList<EnteringTableSettingDetail>();
		EnteringTableSettingDetail detail=new EnteringTableSettingDetail();
		detail.setColName("names");
		detail.setColLength("65");
		detail.setComments("姓名");
		detail.setId("1");
		EnteringTableSettingDetail detail2=new EnteringTableSettingDetail();
		detail2.setColName("addresssss");
		detail2.setColLength("255");
		detail2.setComments("地址");
		detail2.setId("2");
		list.add(detail);
		list.add(detail2);
		setting.setEtDetail(list);
		/*boolean optSuccess=creatEnteringTable(null,setting);
		System.out.println("创建表"+optSuccess);*/
		
		
		//修改
		EnteringTableSettingInfo preSetting=new EnteringTableSettingInfo();
		setting.setId("1");
		preSetting.setId("1");
		preSetting.setName("zuo_test");
		preSetting.setDescs("我是测试修改");
		/*preSetting.setIndexId("1");666*/
		List<EnteringTableSettingDetail> prelist=new ArrayList<EnteringTableSettingDetail>();
		EnteringTableSettingDetail predetail=new EnteringTableSettingDetail();
		predetail.setColName("na");
		predetail.setColLength("65");
		predetail.setComments("姓名高规格");
		predetail.setId("1");
		EnteringTableSettingDetail predetail2=new EnteringTableSettingDetail();
		predetail2.setColName("ad");
		predetail2.setColLength("255");
		predetail2.setComments("地址是的撒大大");
		predetail2.setId("2");
		
		EnteringTableSettingDetail predetail3=new EnteringTableSettingDetail();
		predetail3.setColName("email");
		predetail3.setColLength("");
		predetail3.setComments("我是油性");
		//predetail3.setId("3");
		prelist.add(predetail);
		prelist.add(predetail2);
		prelist.add(predetail3);
		preSetting.setEtDetail(prelist);
		boolean preoptSuccess=false;//creatEnteringTable(setting,preSetting);
		System.out.println(preoptSuccess);
	}
	/**
	 * 根据补录参数创建补录数据表及备份表
	 * @param preEntering
	 * @param entering
	 * @param bakTableName
	 * 以下为修改时传入的数据源信息
	 * @param url
	 * @param name
	 * @param pwd
	 * 以下为修改时传入的备份数据源信息
	 * @param bakUrl
	 * @param bakName
	 * @param bakPwd
	 * 以下为修改前的数据源信息
	 * @param preUrl
	 * @param preName
	 * @param prePwd
	 * 以下为修改前的备份数据源信息
	 * @param preBakUrl
	 * @param preBakName
	 * @param preBakPwd
	 * @return
	 */
	public static boolean creatEnteringTable(EnteringTableSettingInfo preEntering,EnteringTableSettingInfo entering,String bakTableName,String url,String name,String pwd, String bakUrl, String bakName, String bakPwd,
			String preUrl,String preName,String prePwd, String preBakUrl, String preBakName, String preBakPwd){
		boolean optSuccess=false;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmm");
		//未选择指标或者没有表名称 退出操作
		if(entering==null||StringUtils.isBlank(entering.getName())/*666||StringUtils.isBlank(entering.getIndexId())*/
				||entering.getEtDetail().size()==0){
			return false;
		};
		
		//判断新增还是修改
		if(entering.getId()==null||StringUtils.isBlank(entering.getId())){
			/**
			 * 新增
			 */
			optSuccess=ExcelConnection.createTablesBySetting(entering, entering.getName(),url,name,pwd);
			optSuccess=ExcelConnection.createTablesBySetting(entering, bakTableName/*entering.getName()+"_bak"*/,bakUrl,bakName,bakPwd);
		}else{
			//修改.
			/**
			 * 新建表
			 */
			//(使用修改后数据源)
			optSuccess=ExcelConnection.createTablesBySetting(entering, entering.getName()+"_zuo",url,name,pwd);
			optSuccess=ExcelConnection.createTablesBySetting(entering,bakTableName+"_zuo"/*entering.getName()+"_bak_zuo"*/,bakUrl,bakName,bakPwd);
			//获取修改前配置的数据源
			preEntering.getDatasourceConfigId();
			//迁移数据
			copyTableData(preEntering,entering, entering.getName(), entering.getName()+"_zuo",url,name,pwd,preUrl,preName,prePwd);
			copyTableData(preEntering,entering, bakTableName/*entering.getName()+"_bak"*/,bakTableName+"_zuo"/*entering.getName()+"_bak_zuo"*/,bakUrl,bakName,bakPwd,preBakUrl,preBakName,preBakPwd);
			//deleteTable(bakTableName/*entering.getName()+"_bak"*/,preBakUrl,preBakName,preBakPwd);
			//备份原来的源表
			boolean flag2 = dropTable(entering.getName(), entering.getName()+sdf.format(new Date()) ,preUrl, preName, prePwd);
			if(!flag2){
				return false;
			}
			//备份原来的备份表
			boolean flag = dropTable(bakTableName, bakTableName+sdf.format(new Date()) ,preBakUrl, preBakName, preBakPwd);
			if(!flag){
				return false;
			}
			//删除源表(使用修改前数据源)
			//deleteTable(entering.getName(),preUrl,preName,prePwd);
			//修改表名(使用修改后数据源)
			dropTable(entering.getName()+"_zuo",entering.getName(),url,name,pwd);
			dropTable(bakTableName+"_zuo"/*entering.getName()+"_bak_zuo"*/,bakTableName/*entering.getName()+"_bak"*/,bakUrl,bakName,bakPwd);
			/*optSuccess=ExcelConnection.alertTablesBySetting(preEntering, entering, entering.getName());
			optSuccess=ExcelConnection.alertTablesBySetting(preEntering, entering, entering.getName()+"_bak");*/
		}
		return optSuccess;
	}
	public static boolean dropTable(String tableName){
		return dropTable(tableName, null,null,null,null);
	}
	/**
	 * 
	 * 删除表-实际不是物理删除 而是重命名
	 * @return
	 */
	public static boolean dropTable(String tableName,String newName,String url,String name,String pwd){
		boolean optSuccess=false;
		PreparedStatement  dropPstmt =null;
		String sql=" select table_name from information_schema.tables where table_name ='"+tableName+"' ";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmm");
		if(newName==null){
			newName=tableName+sdf.format(new Date());
		}
		//String dropSql=" drop table if exists "+tableName; 
		try {
			conn=ExcelConnection.getConn(url,name,pwd);
			dropPstmt=(PreparedStatement) conn.prepareStatement(sql);
			ResultSet rs=dropPstmt.executeQuery();
			String tables=null;
			while (rs.next()){
				tables=rs.getString("table_name");
		    }
			if(tables!=null){
				//之前表存在
				String rename=" alter table "+tableName+" rename to "+newName;
				dropPstmt.execute(rename);
				optSuccess=true;
			}else{
				optSuccess=true;
			}
			if(dropPstmt!=null){
				dropPstmt.close();
			}
			if(conn!=null){
				conn.close();
			}
			return optSuccess;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean deleteTable(String tableName,String url,String name,String pwd){
		boolean optSuccess=false;
		PreparedStatement  dropPstmt =null;
		String sql=" select table_name from information_schema.tables where table_name ='"+tableName+"' ";
		String deleteSql=" drop table if exists "+tableName; 
		try {
			conn=ExcelConnection.getConn(url,name,pwd);
			dropPstmt=(PreparedStatement) conn.prepareStatement(sql);
			ResultSet rs=dropPstmt.executeQuery();
			String tables=null;
			while (rs.next()){
				tables=rs.getString("table_name");
		    }
			if(tables!=null){
				//之前表存在
				dropPstmt.execute(deleteSql);
				optSuccess=true;
			}else{
				optSuccess=true;
			}
			if(dropPstmt!=null){
				dropPstmt.close();
			}
			if(conn!=null){
				conn.close();
			}
			return optSuccess;
		} catch (Exception e) {
		}
		return optSuccess;
	}
	/**
	 * 
	 * @param entering
	 * @param tableName
	 * @return 根据要求生产建表SQL语句
	 */
	public static boolean createTablesBySetting(EnteringTableSettingInfo entering,String tableName,String url,String name,String pwd){
		/*==============================================================*/
		/* Table: search_index_history                                  */
		/*==============================================================
			DROP TABLE IF EXISTS `search_index`;
			CREATE TABLE `search_index` (
			  `id` varchar(64) DEFAULT NULL COMMENT '主键',
			  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
			  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
			  `update_by` varchar(64) DEFAULT NULL COMMENT '更新人',
			  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
			  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
			  `del_flag` char(1) DEFAULT '0'
			) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='指标表';

		 */
		PreparedStatement  pstmt =null;
		StringBuffer sb=new StringBuffer();
		sb.append(" create table "+tableName);
		sb.append(" ( `id` varchar(64) default null comment '主键',");
		StringBuffer indexStr = new StringBuffer();//存储规定的索引
		for(EnteringTableSettingDetail detail:entering.getEtDetail()){
			if(StringUtils.isNoneBlank(detail.getColName())){
				sb.append(" `"+detail.getColName().toLowerCase()+"` ");
				String colType=detail.getColType();
				if(StringUtils.isBlank(colType)){
					colType="varchar";
				}
				if("1".equals(detail.getIsIndex())){//若规定索引
					indexStr.append(detail.getColName()+",");
				}
				//设置字段类型
				if("datetime".equals(colType) || "timestamp".equals(colType) ||  
	            		"date".equals(colType) ||"time".equals(colType)||"year".equals(colType)){
					sb.append(colType);
				}
				if( "double".equals(colType)  || "float".equals(colType)||"decimal".equals(colType)){
					sb.append(colType);
					if(StringUtils.isNotBlank(detail.getColLength())){
						sb.append(" ("+detail.getColLength());
						if(detail.getColAccuracy()!=null){
							//浮点需要添加精度
							sb.append(","+detail.getColAccuracy()+"");
						}else{
							sb.append(",2");
						}
						sb.append(")  ");
					};
				}
				if("int".equals(colType) || "integer".equals(colType)  || "bigint".equals(colType) ||
	            		 "numeric".equals(colType)||
	            		 "tinyint".equals(colType)||"smallint".equals(colType)){
					sb.append(colType);
					if(StringUtils.isNotBlank(detail.getColLength())){
						sb.append(" ("+detail.getColLength()+")  ");
					};
				}
				if("tinytext".equals(colType)||"text".equals(colType)
						||"mediumblob".equals(colType)||"longblob".equals(colType)
						||"longtext".equals(colType)||"blob".equals(colType)){
					sb.append(colType);
				}
				if("varchar".equals(colType) || "char".equals(colType) ){
					sb.append(colType);
					if(detail.getColLength()==null||StringUtils.isBlank(detail.getColLength())){
						detail.setColLength("255");
					};
					sb.append(" ("+detail.getColLength()+")  ");
				}
				//设置默认值
				if(StringUtils.isNotBlank(detail.getDefaultValue())){
					sb.append(" default "+detail.getDefaultValue()+" ");
				}
				//设置备注
				sb.append(" comment '"+(detail.getComments()==null||StringUtils.isBlank(detail.getComments())?detail.getExcelColName():detail.getComments())+"',");
			}
		};
		//设置索引
		if(StringUtil.isNotEmpty(indexStr.toString())){
			String targetStr = indexStr.substring(0, indexStr.length()-1);
			sb.append("INDEX  indexs ("+targetStr+ "),");
		}
		
		//添加公共字段
		sb.append(" `is_success`           char(1) comment ' 导入状态 0-成功 1 -失败', ");
		sb.append(" `error_col`            varchar(255) comment '错误列 逗号拼接', ");
		sb.append(" `error_msg`            text comment '错误信息 逗号拼接', ");
		sb.append(" `order_no`          int(11) not null auto_increment  comment '排序', ");
		sb.append(" `create_date`          datetime  comment '创建时间', ");
		sb.append(" `create_by`            varchar(64)  comment '创建者', ");
		sb.append(" `update_date`          datetime  comment '更新时间', ");
		sb.append(" `update_by` varchar(64) default null comment '更新人', ");
		sb.append(" `remarks`              varchar(255) comment '备注信息', ");
		sb.append(" `del_flag`             char(1)  default '0' comment '删除标记' ");
		sb.append(" ,primary key (`order_no`)) engine=InnoDB default charset=utf8 comment='"+entering.getDescs()+"'");
		System.out.println(sb.toString());
		try {
			conn=ExcelConnection.getConn(url,name,pwd);
			pstmt = (PreparedStatement) conn.prepareStatement(sb.toString());
			//删除之前表（逻辑删除 改名）
			dropTable(tableName,null,url,name,pwd);
			if(pstmt.executeUpdate()==0){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(pstmt!=null){
					pstmt.close();
				}
				if(conn!=null){
					conn.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		};
		return false;
	}
	
	/**
	 * 修改补录表字段:只能增加或者修改字段 不能删除字段
	 * @return
	 */
	public static boolean alertTablesBySetting(EnteringTableSettingInfo preEntering,EnteringTableSettingInfo entering,String tableName,String url,String name,String pwd){
		PreparedStatement  pstmt =null;
		conn=ExcelConnection.getConn(url,name,pwd);
		boolean optSuccess=true;
		for(EnteringTableSettingDetail detail:entering.getEtDetail()){
			if(StringUtils.isNotBlank(detail.getColName())){
				//判断当前列之前是否存在
				EnteringTableSettingDetail pd=judgeUpdate(preEntering, detail);
				if(detail.getColLength()==null||StringUtils.isBlank(detail.getColLength())){
					detail.setColLength("255");
				};
				if(pd!=null&&!pd.getColName().equals(detail.getColName())){
					//修改字段
					String alert=" alter table "+tableName+" change "+pd.getColName()+" "+detail.getColName()+" varchar("+detail.getColLength()+") default null comment '"+detail.getComments()+"'";
					System.out.println(alert);
					try {
						pstmt = (PreparedStatement) conn.prepareStatement(alert);
						pstmt.execute();
						if(pstmt!=null){
							pstmt.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
						optSuccess=false;
					}
				}else if(pd==null){
					//新增字段
					String alert=" alter table "+tableName+" add `"+detail.getColName()+"` varchar("+detail.getColLength()+") default null comment '"+detail.getComments()+"' ";
					System.out.println(alert);
					try {
						pstmt = (PreparedStatement) conn.prepareStatement(alert);
						pstmt.execute();
						if(pstmt!=null){
							pstmt.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
						optSuccess=false;
					}
				}
			}
		};
		return optSuccess;
	}
	/**
	 * @param 判断是否更新操作
	 * @return
	 */
	public static EnteringTableSettingDetail judgeUpdate(EnteringTableSettingInfo preEntering,EnteringTableSettingDetail detail){
		EnteringTableSettingDetail isUpdate=null;
		for(EnteringTableSettingDetail d:preEntering.getEtDetail()){
			if(d.getId().equals(detail.getId())){
				isUpdate=d;
			}
		};
		return isUpdate;
	}
	/**
	 * 迁移表数据
	 * @param preEntering
	 * @param entering
	 * @param fromTable
	 * @param toTable
	 * @param url
	 * @param name
	 * @param pwd
	 * @param preDbUrl
	 * @param preDbName
	 * @param preDbPwd
	 * @return
	 */
	public static boolean copyTableData(EnteringTableSettingInfo preEntering,EnteringTableSettingInfo entering,String fromTable,String toTable,
					String url,String name,String pwd,
					String preDbUrl,String preDbName,String preDbPwd){
		PreparedStatement  pstmt = null;
		ResultSet rs = null;
		/*先从之前的库里查数据*/
		StringBuffer preSb=new StringBuffer();
		preSb.append(" select `id`,");
		int countCol = 10+0;//存储结果集总数
		for(EnteringTableSettingDetail detail:entering.getEtDetail()){//遍历修改后的字段信息
			//判断当前列之前是否存在
			EnteringTableSettingDetail pd=judgeUpdate(preEntering, detail);
			if(pd!=null){
				preSb.append(" `"+pd.getColName().toLowerCase()+"`, ");
				countCol++;
			}
		};
		//添加公共字段
		preSb.append(" `is_success`, ");
		preSb.append(" `error_col`, ");
		preSb.append(" `error_msg`, ");
		preSb.append(" `create_date`, ");
		preSb.append(" `create_by`, ");
		preSb.append(" `update_date`, ");
		preSb.append(" `update_by`, ");
		preSb.append(" `remarks`, ");
		preSb.append(" `del_flag`");
		preSb.append(" from  "+fromTable); 
		System.out.println(preSb.toString());
		String valueStr = "";
		try {
			conn=ExcelConnection.getConn(preDbUrl,preDbName,preDbPwd);
			pstmt = (PreparedStatement) conn.prepareStatement(preSb.toString());
			rs = pstmt.executeQuery();
			preSb.replace(0, preSb.length(), "");
			System.out.println(preSb.toString());
			boolean isHave = false;
			List<Object> resourceValues = new ArrayList<>();
			while(rs.next()){
				isHave = true;
				preSb.append("(");
				for (int i = 1; i < countCol+1; i++) {
					String cell = rs.getString(i);
					if(cell==null){
						resourceValues.add(null);
					}else{
						resourceValues.add(cell);
					}
					//System.out.println("====["+cell+"]====");
					/*if(cell!=null&&cell.endsWith(".0")){
						cell = cell.substring(0, cell.length()-2);
					}
					if(cell==null){
						preSb.append("");
					}else{
						preSb.append("'"+cell+"'");
					}
					if(i<countCol){
						preSb.append(",");
					}*/
					preSb.append("?,");
				}
				preSb = new StringBuffer(preSb.substring(0,preSb.length()-1));
				preSb.append("),");
			}
			if(!isHave){//若原表无数据,返回true
				return true;
			}else{
				 valueStr = preSb.toString().substring(0,preSb.length()-1);
			}
			System.out.println(preSb.toString());
						
		StringBuffer sb=new StringBuffer();
		sb.append(" insert into "+toTable);
		sb.append(" ( `id`,");
		for(EnteringTableSettingDetail detail:entering.getEtDetail()){//遍历修改后的字段信息
			if(StringUtils.isNotBlank(detail.getColName())&&StringUtils.isNotBlank(detail.getId())){
				sb.append(" `"+detail.getColName().toLowerCase()+"`, ");
			}
		};
		//添加公共字段
		sb.append(" `is_success`, ");
		sb.append(" `error_col`, ");
		sb.append(" `error_msg`, ");
		sb.append(" `create_date`, ");
		sb.append(" `create_by`, ");
		sb.append(" `update_date`, ");
		sb.append(" `update_by`, ");
		sb.append(" `remarks`, ");
		sb.append(" `del_flag`");
		sb.append(" ) ");
		sb.append(" values ");
		sb.append(valueStr);
		System.out.println(sb.toString());
			conn=ExcelConnection.getConn(url,name,pwd);
			pstmt = (PreparedStatement) conn.prepareStatement(sb.toString());
			for (int i = 0; i < resourceValues.size(); i++) {
				pstmt.setObject(i+1, resourceValues.get(i));
			}
			if(pstmt.executeUpdate()==0){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				if(pstmt!=null){
					pstmt.close();
				}
				if(conn!=null){
					conn.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		};
		return false;
	}
	

}
