package com.haier.datamart.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.util.StringUtil;
import com.haier.datamart.annotation.Log;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.entity.DataInterfaceExc;
import com.haier.datamart.entity.SysProduct;
import com.haier.datamart.entity.User;
import com.haier.datamart.service.IUserInterfaceManager;

@RestController
public class UserInterfaceManager extends BaseController{ 
	@Autowired
	private IUserInterfaceManager UIMservice;
	
	/**
	 * 修改和新增功能
	 * @param request
	 * @param dataIFC
	 * @return
	 */
	@RequestMapping(value = "/userInterfaceManager/alertInterface", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/userInterfaceManager/alertInterface ")
	public Object alertInterface(HttpServletRequest request,@RequestBody DataInterfaceExc dataIFC){
		try {
			User user = getLoginUser(request);
			
			if(user.getId()==null){
				return new PublicResult<>(PublicResultConstant.UNAUTHORIZED, "登录过期,请重新登录!");
			}
			/*boolean token = UIMservice.getToken(user,dataIFC.getSysProduct().getId());//获取令牌
			if(!token){
				return new PublicResult<>(PublicResultConstant.UNAUTHORIZED, "权限不足!");
			}*/
			dataIFC.setLastUpdateBy(user.getId());
			dataIFC.setDelFlag("0");
			boolean isSuccess;
			if(dataIFC.getId()!=null){//修改功能
				dataIFC.setCreateBy(UIMservice.getDataInterfaceById(dataIFC.getId()).getCreateBy());
				isSuccess =	UIMservice.updateDataIFC(dataIFC);
			}else{//新增功能
				dataIFC.setCreateBy(user.getId());
				isSuccess = UIMservice.addDataIFC(dataIFC);
			}
			return new PublicResult<>(isSuccess?PublicResultConstant.SUCCESS:PublicResultConstant.ERROR,isSuccess?"操作成功":"操作失败");
		} catch (Exception e) {
			System.out.println("自定义异常原因:"+e.getMessage());
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED, e.getMessage());
		}
	}
	
	/**
	 * 查询功能
	 * @param request
	 * @param productId
	 * @param dataInterfaceId
	 * @return
	 */
	@RequestMapping(value = "/userInterfaceManager/getInterface", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/userInterfaceManager/getInterface ")
	public Object getInterface(HttpServletRequest request,
				String productId,Long dataInterfaceId,
				String dataSpace,String dataType){
		try {
			User user = getLoginUser(request);
		/*	
			if(user.getId()==null){
				return new PublicResult<>(PublicResultConstant.UNAUTHORIZED, "登录过期,请重新登录!");
			}*/
			List<DataInterfaceExc> resultList = new ArrayList<DataInterfaceExc>();
			if(StringUtil.isNotEmpty(productId)){//若项目id不为空,查询项目id对应的所有接口信息
				resultList = UIMservice.getDataInterfaceList(productId);
			}else if(dataInterfaceId!=null){//根据接口id查询对应的信息
				DataInterfaceExc dataInterface = UIMservice.getDataInterfaceById(dataInterfaceId);
				resultList.add(dataInterface);
			}else if(StringUtil.isNotEmpty(dataSpace)||StringUtil.isNotEmpty(dataType)){//模糊查询
				resultList = UIMservice.fuzzySearch(dataSpace,dataType);
			}else{//根据用户id查询
				resultList = UIMservice.getDataInterfaceList(user);
			}
			return new PublicResult<>(PublicResultConstant.SUCCESS,resultList);
		}catch (Exception e) {
			System.out.println("自定义异常原因:"+e.getMessage());
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED, "参数错误!");
		}
	}
	@RequestMapping(value = "/userInterfaceManager/getInterface/v2", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/userInterfaceManager/getInterface/v2")
	public Object getInterfaceV2(HttpServletRequest request,
				String productId,Long dataInterfaceId,
				String dataSpace,String dataType,@RequestParam(value="userId",required = true) String userId){
		try {
			User user =getUserByUid(userId);
			if(user==null){
				return new PublicResult<>(PublicResultConstant.UNAUTHORIZED, "用户不存在!");
			}
			List<DataInterfaceExc> resultList = new ArrayList<DataInterfaceExc>();
			if(StringUtil.isNotEmpty(productId)){//若项目id不为空,查询项目id对应的所有接口信息
				resultList = UIMservice.getDataInterfaceList(productId);
			}else if(dataInterfaceId!=null){//根据接口id查询对应的信息
				DataInterfaceExc dataInterface = UIMservice.getDataInterfaceById(dataInterfaceId);
				resultList.add(dataInterface);
			}else if(StringUtil.isNotEmpty(dataSpace)||StringUtil.isNotEmpty(dataType)){//模糊查询
				resultList = UIMservice.fuzzySearch(dataSpace,dataType);
			}else{//根据用户id查询
				resultList = UIMservice.getDataInterfaceList(user);
			}
			return new PublicResult<>(PublicResultConstant.SUCCESS,resultList);
		}catch (Exception e) {
			System.out.println("自定义异常原因:"+e.getMessage());
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED, "参数错误!");
		}
	}
	/**
	 * 删除接口信息
	 * @param request
	 * @param productId
	 * @param dataInterfaceId
	 * @return
	 */
	@RequestMapping(value = "/userInterfaceManager/deleteInterface", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/userInterfaceManager/deleteInterface ")
	public Object deleteInterface(HttpServletRequest request,String productId,Long dataInterfaceId){
		try {
			User user = getLoginUser(request);
			
			if(user.getId()==null){
				return new PublicResult<>(PublicResultConstant.UNAUTHORIZED, "登录过期,请重新登录!");
			}
			/*boolean token = UIMservice.getToken(user,productId);//获取令牌
			if(!token){
				return new PublicResult<>(PublicResultConstant.UNAUTHORIZED, "权限不足!");
			}*/
			boolean isRemove = false;
			if(StringUtil.isNotEmpty(productId)){//若项目id不为空,删除项目id对应的所有接口信息
				isRemove = UIMservice.deleteDataInterfaceList(productId);
			}else{//根据接口id删除对应的信息
				isRemove = UIMservice.deleteDataInterfaceById(dataInterfaceId,user);
			}
			return new PublicResult<>(isRemove?PublicResultConstant.SUCCESS:PublicResultConstant.ERROR,isRemove?"删除成功":"删除失败");
		} catch (Exception e) {
			System.out.println("自定义异常原因:{"+e.getMessage()+"}");
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED, "参数错误!");
		}
	}
	
	
	/**
	 * 检查接口是否被修改过
	 * @param request
	 * @param dataInterfaceId
	 * @return
	 */
	@RequestMapping(value = "/userInterfaceManager/isModify", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/userInterfaceManager/isModify ")
	public Object isModify(HttpServletRequest request,Long dataInterfaceId){
			try {
				User user = getLoginUser(request);
				
				if(user.getId()==null){
					return new PublicResult<>(PublicResultConstant.UNAUTHORIZED, "登录过期,请重新登录!");
				}
				DataInterfaceExc isModify = UIMservice.isModify(dataInterfaceId);
				boolean flag = !(isModify==null);//不为null则修改过
				return new PublicResult<>(flag?PublicResultConstant.SUCCESS:PublicResultConstant.SUCCESS,flag?isModify:null);
			} catch (Exception e) {
				e.printStackTrace();
				return new PublicResult<>(PublicResultConstant.FAILED,"参数错误!");
			}
	}
	
	/**
	 * 还原接口
	 * @param request
	 * @param dataInterfaceId
	 * @return
	 */
	@RequestMapping(value = "/userInterfaceManager/restore", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/userInterfaceManager/restore")
	public Object restore(HttpServletRequest request,Long dataInterfaceId){
		try {
			User user = getLoginUser(request);
			
			if(user.getId()==null){
				return new PublicResult<>(PublicResultConstant.UNAUTHORIZED, "登录过期,请重新登录!");
			}
			dataInterfaceId = UIMservice.restore(dataInterfaceId,user);//还原接口
			return new PublicResult<>(PublicResultConstant.SUCCESS,dataInterfaceId);
		} catch (Exception e) {
			return new PublicResult<>(PublicResultConstant.FAILED,"参数错误!");
		}
	}
	
	/**
	 * 校验sql并返回结果集
	 * @param request
	 * @param sql
	 * @return
	 */
	@RequestMapping(value = "/userInterfaceManager/checkSql", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/userInterfaceManager/checkSql ")
	public Object checkSql(HttpServletRequest request,String sql){
		try {
			User user = getLoginUser(request);
			
			if(user.getId()==null){
				return new PublicResult<>(PublicResultConstant.UNAUTHORIZED, "登录过期,请重新登录!");
			}
			Map resultMap = UIMservice.checkSql(sql);
			return new PublicResult<>(PublicResultConstant.SUCCESS,resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED,"参数错误!");
		}
	}
	/**
	 * 获取历史记录
	 * @param request
	 * @param dataInterfaceId 接口id
	 * @return
	 */
	@RequestMapping(value = "/userInterfaceManager/getHistoryEntry", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/userInterfaceManager/getHistoryEntry ")
	public Object getHistoryEntry(HttpServletRequest request,Long dataInterfaceId,String dataSpace,String dataType){
		try {
			User user = getLoginUser(request);
			
			if(user.getId()==null){
				return new PublicResult<>(PublicResultConstant.UNAUTHORIZED, "登录过期,请重新登录!");
			}
			List<DataInterfaceExc> list = UIMservice.getHistoryEntry(dataInterfaceId, dataSpace, dataType);
			return new PublicResult<>(PublicResultConstant.SUCCESS,list);
		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED,"参数错误!");
		}
	}
	
}
