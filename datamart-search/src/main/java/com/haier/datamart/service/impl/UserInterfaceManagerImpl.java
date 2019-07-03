package com.haier.datamart.service.impl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.derby.iapi.sql.conn.ConnectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.util.StringUtil;
import com.haier.datamart.entity.DataInterfaceExc;
import com.haier.datamart.entity.SysProduct;
import com.haier.datamart.entity.SysProductGroup;
import com.haier.datamart.entity.SysUserGroup;
import com.haier.datamart.entity.User;
import com.haier.datamart.mapper.SysGroupMapper;
import com.haier.datamart.mapper.SysProductExcMapper;
import com.haier.datamart.mapper.SysProductGroupMapper;
import com.haier.datamart.mapper.SysProductMapper;
import com.haier.datamart.mapper.SysUserGroupMapper;
import com.haier.datamart.mapper.SysUserMapper;
import com.haier.datamart.service.IUserInterfaceManager;
import com.haier.datamart.utils.ExcelConnection;
import com.haier.datamart.utils.UUIDUtils;
@Service
@Transactional
public class UserInterfaceManagerImpl implements IUserInterfaceManager{
	@Autowired 
	private SysGroupMapper sysGroupMapper;
	@Autowired 
	private SysUserGroupMapper sysUserGroupMapper;
	@Autowired 
	private SysProductExcMapper sysProductExcMapper;
	@Autowired 
	private	SysProductGroupMapper sysProductGroupMapper;
	@Autowired 
	private	SysProductMapper sysProductMapper;
	@Autowired 
	private	SysUserMapper sysUserMapper;
	
	static final String  TABLE_NAME = "common_interface_exc";
	static final String HISTOR_TABLE_NAME = "common_interface_exc_history";
	static final String LOG_TABLE_NAME = "common_interface_exc_log";
	static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	

	private  class DataInterfaceCRUD{
			Connection conn;
			PreparedStatement pstmt;
			ResultSet rs;
			String dateStr;
			DataInterfaceCRUD() {
			 conn = ExcelConnection.getConn();
			 pstmt = null ;
			 rs = null;
			 dateStr = sdf.format(new Date());
			}
			
			void close(){
				try {
					if(!conn.isClosed()){
						ExcelConnection.close(conn, pstmt, rs);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			/**
			 * 修改和增加操作
			 * @param dataIFC
			 * @return
			 */
			boolean alter(DataInterfaceExc dataIFC) {
				conn = ExcelConnection.getConn();
				String sqlForAdd = "insert into "+TABLE_NAME+
						  " (data_type,data_space,data_sql,"
						  + "transform_data,create_by,create_date,"
						  + "update_by,update_date,remarks,db_datasource_id,id) "
						  +"VALUES(?,?,?,?,?,?,"
						  + "?,?,?,?,?)";
				
			   String sqlForUpdate = "update "+TABLE_NAME+
						  " SET data_type=?,data_space=?,data_sql=?,"
						  + "transform_data=?,create_by=?,create_date=?,update_by=?,"
						  + "update_date=?,remarks=?,db_datasource_id=?"
						  + " WHERE id=?";
			    //查询刚刚插入实体的id
				String forGetIdSql = "select id As id from "+TABLE_NAME+" where data_type='"+dataIFC.getDataType()+"' and data_space='"+dataIFC.getDataSpace()+"'";
				int resultConut = 0;//记录结果集实体数
			    Long targetId = -1L;
			   boolean isInsert=false;
				 try {
					 pstmt = conn.prepareStatement(forGetIdSql);
					 rs = pstmt.executeQuery();
					 while (rs.next()) {
							resultConut++;
							targetId = rs.getLong(1);
						}
					 if(dataIFC.getId()!=null){//若id不为空视为修改操作
						 if(targetId==-1L||resultConut!=1)
						 throw  new RuntimeException("无对应接口信息!");
						 pstmt = conn.prepareStatement(sqlForUpdate);
						 pstmt.setLong(11, targetId);
						
					 }else{
						 if(resultConut>0)//若重复命名
						 throw  new RuntimeException("同一指标下命名空间不可重复!");
						 pstmt = conn.prepareStatement(sqlForAdd);
						 isInsert=true;
					 }
					
					pstmt.setString(1, dataIFC.getDataType());
					pstmt.setString(2, dataIFC.getDataSpace());
					pstmt.setString(3, dataIFC.getDataSql());
					pstmt.setString(4, dataIFC.getTransformData());
					pstmt.setString(5, dataIFC.getCreateBy());
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					pstmt.setString(6,sdf.format(new Date()));
					pstmt.setString(7, dataIFC.getLastUpdateBy());
					pstmt.setString(8,sdf.format(new Date()));
					pstmt.setString(9, dataIFC.getRemark());
					pstmt.setString(10, dataIFC.getDataSourceId());
					if(isInsert){
						pstmt.setString(11, UUIDUtils.get8UUID());
					}
					
					
					int count = pstmt.executeUpdate();//执行增加或者更新语句
					
					 pstmt = conn.prepareStatement(forGetIdSql);
					 rs = pstmt.executeQuery();//查询刚刚插入的记录的id
					 while (rs.next()) {
							targetId = rs.getLong(1);
						}
					dataIFC.setId(targetId);
					String productId = "1";//目前写死
					int isSuccess = sysProductExcMapper.addForCorrelation(productId,dataIFC.getId());//将项目和接口对应关系存入关系表中
					try {
						dataIFC.setCreateTime(sdf.parse(dateStr));
						dataIFC.setLastUpdateTime(sdf.parse(dateStr));
					} catch (ParseException e) {
					}
					int historySqlCount = addHistorySql(dataIFC);//插入历史表的操作
					if(!(count==1&&historySqlCount==1&&isSuccess==1)){//执行失败
						throw  new RuntimeException("执行失败!");
					}
				}catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException("sql语句异常!");
				}finally {
					close();
				}
				return true;
			}
			
			 /**
			  * 增加历史记录
			  * @return
			 * @throws SQLException 
			  */
			Integer addHistorySql(DataInterfaceExc dataIFC) {
				//增加操作历史的sql语句(同为更新操作的历史sql
				String addHistorySql = "insert into "+HISTOR_TABLE_NAME+
						  " (data_type,data_space,data_sql,"
						  + "transform_data,create_by,create_time,last_update_by,"
						  + "last_update_time,remark,data_exc_id,del_flag,id) "
						  +"VALUES(?,?,?,?,?,?,"
						  + "?,?,?,?,?,?)";
				int historySqlCount = -1;
				try {
				/*	close();
					conn = DataInterfaseConnection.getConn();*/
					pstmt = conn.prepareStatement(addHistorySql);
					pstmt.setString(1, dataIFC.getDataType());
					pstmt.setString(2, dataIFC.getDataSpace());
					pstmt.setString(3, dataIFC.getDataSql());
					pstmt.setString(4, dataIFC.getTransformData());
					pstmt.setString(5, dataIFC.getCreateBy());
					if(dataIFC.getCreateTime()!=null){
						pstmt.setString(6, sdf.format(dataIFC.getCreateTime()));
					}else{
						pstmt.setString(6,sdf.format(new Date()));
					}
					pstmt.setString(7, dataIFC.getLastUpdateBy());
					if(dataIFC.getLastUpdateTime()!=null){
						pstmt.setString(8, sdf.format(dataIFC.getLastUpdateTime()));
					}else{
						pstmt.setString(8, "");
					}
					
					pstmt.setString(9, dataIFC.getRemark());
					pstmt.setLong(10, dataIFC.getId());
					pstmt.setString(11, dataIFC.getDelFlag());
					pstmt.setString(12, UUIDUtils.get8UUID().toLowerCase());
					historySqlCount = pstmt.executeUpdate();
				}catch(SQLException e){
					e.printStackTrace();
					//throw new RuntimeException("历史表sql异常导致插入错误!");
				}catch (Exception e) {
					e.printStackTrace();
					//throw new RuntimeException("历史表运行异常导致插入错误!");
				}finally{
					close();
				}
				return historySqlCount;
			}
			/**
			 * 获取单个接口
			 * @param dataInterfaceId
			 * @return
			 */
			 DataInterfaceExc getDataInterfaceById(Long dataInterfaceId) {
				 List<Long> excIdlist = new ArrayList<>();
				 excIdlist.add(dataInterfaceId);
				 List<DataInterfaceExc> list = getList(excIdlist);//根据id获取结果集
				 DataInterfaceExc targetEntry =new DataInterfaceExc();
				 if(list!=null&&list.size()>0){
					 targetEntry = list.get(0);
				 }
				 close();
				return targetEntry;
			}
			 /**
			  * 删除单个接口
			  * @param dataInterfaceId
			  * @param user
			  * @return
			  */
			 boolean deleteDataInterfaceById(Long dataInterfaceId, User user) {
				 String handler = user.getId();//删除者
				 String sqlForEntryTable = "update "+TABLE_NAME+" set del_flag=1"
				 							+ ",last_update_time=?"
				 							+" ,create_by= '"+ handler
				 							+"' where id="+dataInterfaceId;
				 List<Long> excIdlist = new ArrayList<>();
				 excIdlist.add(dataInterfaceId);
				 List<DataInterfaceExc> list = getList(excIdlist);//根据id获取结果集
				 conn = ExcelConnection.getConn();//重新获取连接
				 DataInterfaceExc targetEntry =new DataInterfaceExc();
				 if(list!=null&&list.size()>0){
					 targetEntry = list.get(0);
				 }
				 targetEntry.setLastUpdateBy(handler);//设置历史表删除者
				 targetEntry.setDelFlag("1");//设置历史表删除标记
				 try {
					targetEntry.setLastUpdateTime(sdf.parse(dateStr));
				} catch (ParseException e1) {
				}//设置历史表删除时间
				 try {
					pstmt = conn.prepareStatement(sqlForEntryTable);
					pstmt.setString(1,dateStr);
					int count = pstmt.executeUpdate();//执行删除
					int historySqlCount = addHistorySql(targetEntry);//插入历史表的操作
					if(!(count==1&&historySqlCount==1))
					throw  new RuntimeException("执行失败!");
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException("sql语句异常!");
				}finally {
					close();
				}
				return true;
			}
			 List<DataInterfaceExc> getAllInterfaceList() {
					List<DataInterfaceExc> list = new ArrayList<>();
					StringBuffer sql = new StringBuffer("select t.*, d.`name` as dbName from "+TABLE_NAME+" t left join db_datasource_config d on d.id=t.db_datasource_id where  t.del_flag=0 order by t.create_date desc") ;
					String sqlStr = sql+"";
					try {
						pstmt = conn.prepareStatement(sqlStr);
						/*for (int i = 0; i < excIdlist.size(); i++) {//遍历得到接口id
							pstmt.setLong(i+1, excIdlist.get(i));//替换sqkStr中的问号
						}*/
							rs = pstmt.executeQuery();//执行查询
								while(rs.next()){
									DataInterfaceExc dataInterface = new DataInterfaceExc();
									dataInterface.setId(rs.getLong(1));
									dataInterface.setDataSourceId(rs.getString(13));
									dataInterface.setDataType(rs.getString(3));
									dataInterface.setDataSpace(rs.getString(4));
									dataInterface.setDataSql(rs.getString(5));
									
									dataInterface.setTransformData(rs.getString(6));
									dataInterface.setCreateBy(rs.getString(7));
									dataInterface.setCreateTime(rs.getDate(8));
									dataInterface.setLastUpdateBy(rs.getString(9));
									dataInterface.setLastUpdateTime(rs.getDate(10));
									dataInterface.setRemark(rs.getString(11));
									dataInterface.setDbName(rs.getString(13));
									list.add(dataInterface);
								}
					} catch (SQLException e) {
						e.printStackTrace();
						throw new RuntimeException("sql语句异常!");
					}finally {
						close();
					}
					
					return list;
				 }
			/**
			 * 根据id集合获取对应的接口实体的集合
			 * @param excIdlist
			 * @return
			 */
			 List<DataInterfaceExc> getList(List<Long> excIdlist) {
				List<DataInterfaceExc> list = new ArrayList<>();
				StringBuffer sql = new StringBuffer("select t.*,d.`name` as dbName from "+TABLE_NAME+" t left join db_datasource_config d on d.id=t.db_datasource_id where t.id in(-1 ") ;
				for (int i = 0; i < excIdlist.size(); i++) {
						sql.append(",'"+excIdlist.get(i)+"'");
				}
				sql.append(") and t.del_flag=0 order by t.create_date desc") ;
				String sqlStr = sql+"";
				try {
					pstmt = conn.prepareStatement(sqlStr);
					/*for (int i = 0; i < excIdlist.size(); i++) {//遍历得到接口id
						pstmt.setLong(i+1, excIdlist.get(i));//替换sqkStr中的问号
					}*/
						rs = pstmt.executeQuery();//执行查询
							while(rs.next()){
								DataInterfaceExc dataInterface = new DataInterfaceExc();
								dataInterface.setId(rs.getLong(1));
								//dataInterface.setDataSourceId(rs.getString(2));
								dataInterface.setDataSourceId(rs.getString(13));
								dataInterface.setDataType(rs.getString(3));
								dataInterface.setDataSpace(rs.getString(4));
								dataInterface.setDataSql(rs.getString(5));
								
								dataInterface.setTransformData(rs.getString(6));
								dataInterface.setCreateBy(rs.getString(7));
								dataInterface.setCreateTime(rs.getDate(8));
								dataInterface.setLastUpdateBy(rs.getString(9));
								dataInterface.setLastUpdateTime(rs.getDate(10));
								dataInterface.setRemark(rs.getString(11));
								dataInterface.setDbName(rs.getString(13));
								list.add(dataInterface);
							}
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException("sql语句异常!");
				}finally {
					close();
				}
				
				return list;
			 }

			
			 boolean deleteList(List<Long> excIdlist) {
				// TODO Auto-generated method stub
				return false;
			 }
			
			 /**
			  * 将当前接口和倒数第二条历史接口对比,如果被修改过返回true
			  * @param dataInterfaceId
			  * @return
			  */
			 DataInterfaceExc isModify(Long dataInterfaceId) {
				DataInterfaceExc d2 = null;
				DataInterfaceExc d1 = getDataInterfaceById(dataInterfaceId);
				try {
					d2 = getHistorySecond(dataInterfaceId);
				} catch (IndexOutOfBoundsException e) {//下标越界代表未找到倒数第二条历史记录
					return null;//未修改
				}
				close();
				boolean flag = d1.equals(d2);
				if(!flag)//若修改过
				return d2;
				return null;
				
			 }
			
			 /**
			  * @param dataInterfaceId
			  * @return 历史记录表中本接口的的倒数第二条历史
			  */
			 DataInterfaceExc getHistorySecond(Long dataInterfaceId) {
				 List<DataInterfaceExc> list = new ArrayList<>();
				 String sql = "select * from "+HISTOR_TABLE_NAME
						 	+" where data_exc_id="+dataInterfaceId
						 	+" order by last_update_time desc ";
				 try {
					conn = ExcelConnection.getConn();//重新获取连接
					pstmt = conn.prepareStatement(sql);
					rs = pstmt.executeQuery();
					int count = 0;
					while(rs.next()){
						DataInterfaceExc dataInterface = new DataInterfaceExc();
						dataInterface.setId(rs.getLong(2));
						dataInterface.setDataType(rs.getString(3));
						dataInterface.setDataSpace(rs.getString(4));
						dataInterface.setDataSql(rs.getString(5));
						dataInterface.setParamId(rs.getLong(6));
						dataInterface.setBeginDate(rs.getString(7));
						dataInterface.setDateFormat(rs.getString(8));
						dataInterface.setFreshFlag(rs.getInt(9)+"");
						dataInterface.setUpdateDays(rs.getInt(10));
						dataInterface.setTimerOffset(rs.getInt(11));
						dataInterface.setExcTime(rs.getString(12));
						dataInterface.setTransformData(rs.getString(13));
						dataInterface.setTransformData(rs.getString(14));
						dataInterface.setCreateBy(rs.getString(15));
						dataInterface.setCreateTime(rs.getDate(16));
						dataInterface.setLastUpdateBy(rs.getString(17));
						dataInterface.setLastUpdateTime(rs.getDate(18));
						dataInterface.setRemark(rs.getString(19));
						list .add(dataInterface);
						if(count>0)
							break;
						count++;
					}
					return list.get(1);
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException("还原接口失败!");
				}finally{
					close();
				}
			 }
			 /**
			  * @param dataInterfaceId
			  * @return 还原后的接口
			  */
			 DataInterfaceExc restore(Long dataInterfaceId,User user){
				 DataInterfaceExc historySconde = getHistorySecond(dataInterfaceId);
				 historySconde.setLastUpdateBy(user.getId());
				 this.alter(historySconde);
				return historySconde;
			 }
			 /**
			  * 执行sql返回结果集
			  * @param sql
			  * @return
			  */
			public Map checkSql(String sql) {
				Map<Integer,Object> map = new HashMap<>();
				int count = 1;
				try {
					pstmt = conn.prepareStatement(sql);
					rs = pstmt.executeQuery();
					while(rs.next()){
						//TODO
						map.put(count, rs.getObject(count));
						count++;
					}
					return map;
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException("执行sql失败!");
				}finally{
					close();
				}
				
			}
			/**
			 * 模糊搜索
			 * @param dataSpace
			 * @param dataType
			 * @return
			 */
			 List<DataInterfaceExc> fuzzySearch(String dataSpace, String dataType) {
				 List<DataInterfaceExc> list = new ArrayList<>();
				 StringBuffer sqlBuff = new StringBuffer("select * from "+TABLE_NAME+" where 1=1 ");
				 if(StringUtil.isNotEmpty(dataSpace)){
					 sqlBuff.append(" and data_space like '%"+dataSpace+"%'");
				 }
				 
				 if(StringUtil.isNotEmpty(dataType)){
					 sqlBuff.append(" and data_type like '%"+dataType+"%'");
				 }
				 String sql = sqlBuff+"";
				 try {
					 	pstmt = conn.prepareStatement(sql);
						rs = pstmt.executeQuery();
						while(rs.next()){
							DataInterfaceExc dataInterface = new DataInterfaceExc();
							dataInterface.setId(rs.getLong(1));
							dataInterface.setDataType(rs.getString(2));
							dataInterface.setDataSpace(rs.getString(3));
							dataInterface.setDataSql(rs.getString(4));
							dataInterface.setParamId(rs.getLong(5));
							dataInterface.setBeginDate(rs.getString(6));
							dataInterface.setDateFormat(rs.getString(7));
							dataInterface.setFreshFlag(rs.getInt(8)+"");
							dataInterface.setUpdateDays(rs.getInt(9));
							dataInterface.setTimerOffset(rs.getInt(10));
							dataInterface.setExcTime(rs.getString(11));
							dataInterface.setCacheType(rs.getInt(12));
							dataInterface.setTransformData(rs.getString(13));
							dataInterface.setCreateBy(rs.getString(14));
							dataInterface.setCreateTime(rs.getDate(15));
							dataInterface.setLastUpdateBy(rs.getString(16));
							dataInterface.setLastUpdateTime(rs.getDate(17));
							dataInterface.setRemark(rs.getString(18));
							dataInterface.setDataSourceId(rs.getString(19));
							list .add(dataInterface);
						}
						return list;
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("模糊搜索失败!");
				}finally{
					close();
				}
				
			}
			 /**
			  * 获取历史记录
			  * @param dataInterfaceId
			 * @param dataType 
			 * @param dataSpace 
			  * @return
			  */
			 List<DataInterfaceExc> getHistoryEntry(Long dataInterfaceId, String dataSpace, String dataType) {
				 List<DataInterfaceExc> list = new ArrayList<>();
				 String sql = "";
				 if(dataInterfaceId!=null){
					 sql = "select * from "+HISTOR_TABLE_NAME
							 	+" where del_flag=0 and data_exc_id="+dataInterfaceId
							 	+" order by last_update_time desc ";
				 }else if(dataSpace!=null||dataType!=null){
					 sql = "select * from "+HISTOR_TABLE_NAME
							 	+" where del_flag=0 ";
					 if(StringUtil.isNotEmpty(dataSpace)){
						 sql += "and data_space like '%"+dataSpace+"%' ";
					 }
					 if(StringUtil.isNotEmpty(dataType)){
						 sql += "and data_type like '%"+dataType+"%' ";
					 }
					 sql += " order by last_update_time desc ";
				 }else{
					 sql = "select * from "+HISTOR_TABLE_NAME
							 	+" where del_flag=0 "
							 	+" order by last_update_time desc ";
				 }
				  
				 try {
					conn = ExcelConnection.getConn();//重新获取连接
					pstmt = conn.prepareStatement(sql);
					rs = pstmt.executeQuery();
					while(rs.next()){
						DataInterfaceExc dataInterface = new DataInterfaceExc();
						dataInterface.setId(rs.getLong(2));
						dataInterface.setDataType(rs.getString(3));
						dataInterface.setDataSpace(rs.getString(4));
						dataInterface.setDataSql(rs.getString(5));
						dataInterface.setParamId(rs.getLong(6));
						dataInterface.setBeginDate(rs.getString(7));
						dataInterface.setDateFormat(rs.getString(8));
						dataInterface.setFreshFlag(rs.getInt(9)+"");
						dataInterface.setUpdateDays(rs.getInt(10));
						dataInterface.setTimerOffset(rs.getInt(11));
						dataInterface.setExcTime(rs.getString(12));
						dataInterface.setCacheType(rs.getInt(13));
						dataInterface.setTransformData(rs.getString(14));
						dataInterface.setCreateBy(rs.getString(15));
						dataInterface.setCreateTime(rs.getDate(16));
						dataInterface.setLastUpdateBy(rs.getString(17));
						dataInterface.setLastUpdateTime(rs.getDate(18));
						dataInterface.setRemark(rs.getString(19));
						list .add(dataInterface);
					}
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException("还原接口失败!");
				}finally{
					close();
				}
				return list;
			}
			
			

	}
	
	/**
	 * 更新操作
	 */
	@Override
	public boolean updateDataIFC(DataInterfaceExc dataIFC) {
		 DataInterfaceCRUD dataInterfaceCRUD = new DataInterfaceCRUD();
		return dataInterfaceCRUD.alter(dataIFC);
	}
	/**
	 * 增加操作
	 */
	@Override
	public boolean addDataIFC(DataInterfaceExc dataIFC) {
		DataInterfaceCRUD dataInterfaceCRUD = new DataInterfaceCRUD();
		return dataInterfaceCRUD.alter(dataIFC);
	}
	/**
	 * 获取接口集合(通过项目id获取其下对应的接口id集合)
	 */
	@Override
	public List<DataInterfaceExc> getDataInterfaceList(String productId) {
		//通过项目id获取其下对应的接口id集合
		List<Long> excIdlist = sysProductExcMapper.getDataInterfaceList(productId);
		DataInterfaceCRUD dataInterfaceCRUD = new DataInterfaceCRUD();
		return dataInterfaceCRUD.getList(excIdlist);
	}
	/**
	 * 通过接口id查询对应接口
	 */
	@Override
	public DataInterfaceExc getDataInterfaceById(Long dataInterfaceId) {
		DataInterfaceCRUD dataInterfaceCRUD = new DataInterfaceCRUD();
		return dataInterfaceCRUD.getDataInterfaceById(dataInterfaceId);
	}
	/**
	 * 删除项目下所有接口
	 */
	@Override
	public boolean deleteDataInterfaceList(String productId) {
		//通过项目id获取其下对应的接口id集合
		List<Long> excIdlist = sysProductExcMapper.getDataInterfaceList(productId);
		DataInterfaceCRUD dataInterfaceCRUD = new DataInterfaceCRUD();
		return dataInterfaceCRUD.deleteList(excIdlist);
	}
	/**
	 * 删除单个接口
	 */
	@Override
	public boolean deleteDataInterfaceById(Long dataInterfaceId,User user) {
		DataInterfaceCRUD dataInterfaceCRUD = new DataInterfaceCRUD();
		return dataInterfaceCRUD.deleteDataInterfaceById(dataInterfaceId,user);
	}
	/**
	 * 查询用户对项目是否有支配权
	 */
	@Override
	public boolean getToken(User user, String sysProductId) {
		/*String userId = user.getId();
		if(StringUtil.isEmpty(sysProductId)||StringUtil.isEmpty(userId))
		throw new RuntimeException("用户id和项目id不能为空");
		//根据用户id获取对应的用户组id
		SysUserGroup sysUserGroup = sysUserGroupMapper.getGroupId(userId);
		String groupId = sysUserGroup.getGroupId();
		//根据组用户组id获取对应的项目id集合
		List<SysProductGroup> sysProductIdList = sysProductGroupMapper.getSysProductIdList(groupId);
		String alternativeSysProductId;
		boolean result = false;//结果默认为false
		for (SysProductGroup sysProductGroup : sysProductIdList) {//若查到的项目包含用户传入的项目id,则获取令牌
			alternativeSysProductId = sysProductGroup.getProductId();
			if(sysProductId.equals(alternativeSysProductId)){//比较
				result = true;
				break;
			}
		}*/
		return false;
	}
	/**
	 * 获取用户下可支配的项目集合
	 */
	@Override
	public List<SysProduct> getProductList(User user) {
		String userId = user.getId();
		//根据用户id获取对应的用户组对象
		List<SysUserGroup> sysUserGroups = sysUserGroupMapper.getGroupId(userId);
		if(sysUserGroups.size()==0){
			return null;
		}
		//根据组用户组id获取对应的项目id集合
		List<SysProductGroup> sysProductIdList = sysProductGroupMapper.getSysProductIdList(sysUserGroups);
		//根据项目id集合查找对应的项目detail
		List<SysProduct> sysProductList = null;
		if(sysProductIdList.size()!=0){
			 sysProductList = sysProductMapper.getSysProductDetailList(sysProductIdList);
		}
		
		return sysProductList;
	}
	/**
	 * 获取当前用户下的所有接口
	 */
	@Override
	public List<DataInterfaceExc> getDataInterfaceList(User user) {
		List<DataInterfaceExc> dataInterfaceExcList = new ArrayList<DataInterfaceExc>();
		if(user!=null&user.isHasAdmin()){
			DataInterfaceCRUD dataInterfaceCRUD = new DataInterfaceCRUD();
			dataInterfaceExcList.addAll(dataInterfaceCRUD.getAllInterfaceList());
		}else{
			List<SysProduct> sysProductList = this.getProductList(user);//根据用户获取可支配项目集合
			if(sysProductList!=null&&sysProductList.size()!=0){
				for (SysProduct sysProduct : sysProductList) {//遍历项目集合
					if(StringUtil.isNotEmpty(sysProduct.getId())){//若项目id不为空
						dataInterfaceExcList.addAll(getDataInterfaceList(sysProduct.getId()));//根据项目id获取接口集合
					}
				}
			}
		}
		return dataInterfaceExcList;
	}
	/**
	 * 将项目和接口对应关系存入关系表中
	 */
	@Override
	public boolean addForCorrelation(String productId, Long id) {
		return sysProductExcMapper.addForCorrelation(productId,id)==1?true:false;
	}
	/**
	 * 检查接口是否修改过
	 */
	@Override
	public DataInterfaceExc isModify(Long dataInterfaceId) {
		DataInterfaceCRUD dataInterfaceCRUD = new DataInterfaceCRUD();
		return dataInterfaceCRUD.isModify(dataInterfaceId);
	}
	/**
	 * 还原接口
	 */
	@Override
	public Long restore(Long dataInterfaceId,User user) {
		DataInterfaceCRUD dataInterfaceCRUD = new DataInterfaceCRUD();
		return dataInterfaceCRUD.restore(dataInterfaceId,user).getId();
	}
	/**
	 * 执行sql并返回结果集
	 */
	@Override
	public Map checkSql(String sql) {
		DataInterfaceCRUD dataInterfaceCRUD = new DataInterfaceCRUD();
		return dataInterfaceCRUD.checkSql(sql);
	}
	/**
	 * 模糊搜索
	 */
	@Override
	public List<DataInterfaceExc> fuzzySearch(String dataSpace, String dataType) {
		DataInterfaceCRUD dataInterfaceCRUD = new DataInterfaceCRUD();
		return dataInterfaceCRUD.fuzzySearch(dataSpace,dataType);
	}
	/**
	 * 历史记录的查询
	 */
	@Override
	public List<DataInterfaceExc> getHistoryEntry(Long dataInterfaceId, String dataSpace, String dataType){
		DataInterfaceCRUD dataInterfaceCRUD = new DataInterfaceCRUD();
		return dataInterfaceCRUD.getHistoryEntry(dataInterfaceId,  dataSpace,  dataType);
	}
	
	@Override
	public List<SysProduct> getAllProductList() {
		return sysProductMapper.getAllProductList();
	}
	

}
