package com.haier.datamart.service;

import java.util.List;
import java.util.Map;

import com.haier.datamart.entity.DataInterfaceExc;
import com.haier.datamart.entity.SysProduct;
import com.haier.datamart.entity.User;

/**
 * @author lzg 2018/08/03
 */
public interface IUserInterfaceManager {
	/**
	 * 更新数据接口
	 * @param dataIFC 
	 * @return
	 */
	boolean updateDataIFC(DataInterfaceExc dataIFC);
	/**
	 * 增加数据接口
	 * @return
	 */
	boolean addDataIFC(DataInterfaceExc dataIFC);
	/**
	 * 根据项目id获取其对应的所有接口集合
	 * @param productId
	 * @return
	 */
	List<DataInterfaceExc> getDataInterfaceList(String productId);
	/**
	 * 根据接口id获取其对应的接口
	 * @param productId
	 * @return 
	 */
	DataInterfaceExc getDataInterfaceById(Long dataInterfaceId);
	/**
	 * 根据项目id删除其对应的所有接口
	 * @param productId
	 * @return
	 */
	boolean deleteDataInterfaceList(String productId);
	/**
	 * 根据接口id删除对应的接口
	 * @param dataInterfaceId
	 * @return
	 */
	boolean deleteDataInterfaceById(Long dataInterfaceId,User user);
	
	/**
	 * 根据用户获取令牌
	 * @param user
	 * @param string 
	 * @return
	 */
	boolean getToken(User user, String sysProductId);
	/**
	 * 获取当前用户对应的项目列表
	 * @param user
	 * @return
	 */
	 List<SysProduct> getProductList(User user);
	 /**
	  * 根据用户
	  * @param user
	  * @return
	  */
	List<DataInterfaceExc> getDataInterfaceList(User user);
	/**
	 * 将项目和接口对应关系存入关系表中
	 * @param productId
	 * @param id
	 * @return
	 */
	boolean addForCorrelation(String productId,Long id);
	/**
	 * 检查接口是否被修改过
	 * @return
	 */
	DataInterfaceExc isModify(Long dataInterfaceId);
	/**
	 * 还原接口
	 * @param dataInterfaceId
	 * @param user 
	 * @return
	 */
	Long restore(Long dataInterfaceId, User user);
	/**
	 * 执行sql并返回结果集
	 * @param sql
	 * @return
	 */
	Map checkSql(String sql);
	/**
	 * 模糊查询
	 * @param dataSpace
	 * @param dataType
	 * @return
	 */
	List<DataInterfaceExc> fuzzySearch(String dataSpace, String dataType);
	/**
	 * 获取历史记录
	 * @param dataInterfaceId
	 * @param dataType 
	 * @param dataSpace 
	 * @return
	 */
	List<DataInterfaceExc> getHistoryEntry(Long dataInterfaceId, String dataSpace, String dataType);
	/**
	 * 获取所有项目列表
	 * @return
	 */
	List<SysProduct> getAllProductList();

}
