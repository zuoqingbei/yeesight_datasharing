package com.haier.datamart.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.service.IEnteringTableSettingHeaderService;
import com.haier.datamart.utils.ExcelConnection;
/**
 * 
 * @author zuoqb
 * piwik统计
 */
@RestController
@RequestMapping("/api/piwik")
@Api(value = "piwik统计信息",description="piwik统计信息 @author zuoqb123")
public class PiwikController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(PiwikController.class);

    @Autowired
    public IEnteringTableSettingHeaderService iEnteringTableSettingHeaderService;

    /**
     * @date   2018-10-16
     * @author zuoqb123
     * @todo   Event Categories
     */
  	@ApiOperation(value = "Event Categories", notes = "Event Categories", httpMethod = "GET")
	@RequestMapping(value = "/eventCategories", method = RequestMethod.GET)
	public PublicResult<List<Static>> eventCategories(HttpServletRequest request,
			@RequestParam(value="pageNum",required = false,defaultValue="1") Integer pageNum,
			@RequestParam(value="pageSize",required = false,defaultValue="25") Integer pageSize,
			@RequestParam(value="siteId",required = false)  String siteId,
			@RequestParam(value="time",required = false)  String time,
			@RequestParam(value="isDesc",required = false,defaultValue="true")  boolean isDesc) {
		try {
			int startNum=(pageNum-1)*pageSize;
			StringBuffer sb=new StringBuffer();
			sb.append(" select a1.name,count(a1.name) as nums,'' as actionKey from log_link_visit_action va  ");
			sb.append(" left join log_action a1 on a1.idaction=va.idaction_event_category ");
			sb.append(" where 1=1 ");
			if(StringUtils.isNotBlank(siteId)){
				sb.append(" and va.idsite='"+siteId+"' ");
			}
			if(StringUtils.isNotBlank(time)){
				sb.append(" and date_format(va.server_time,'%Y%m%d')='"+time+"' ");
			}
			sb.append(" group by a1.name order by count(a1.name)  ");
			if(isDesc){
				sb.append(" desc ");
			}
			sb.append(" limit "+startNum+","+pageSize);
			List<Static> result = execePiwikSql(sb.toString());
			for(Static s:result){
				//页面分析: Event Categories 分事件统计
				StringBuffer sql=new StringBuffer();
				sql.append(" select count(a2.name) as nums,a2.name,'' as actionKey from `log_link_visit_action` va left join log_action a1 on a1.idaction=va.idaction_event_category ");
				sql.append(" left join log_action a2 on a2.idaction=va.idaction_event_action ");
				sql.append(" where 1=1 ");
				if(StringUtils.isNotBlank(siteId)){
					sql.append(" and va.idsite='"+siteId+"' ");
				}
				if(StringUtils.isNotBlank(time)){
					sql.append(" and date_format(va.server_time,'%Y%m%d')='"+time+"' ");
				}
				sql.append(" and a1.name='"+s.getName()+"' ");
				sql.append(" and a1.name is not null and a1.name!='触点迭代生态圈' ");
				sql.append(" group by a1.name order by count(a1.name)  ");
				if(isDesc){
					sql.append(" desc ");
				}
				s.setChildren(execePiwikSql(sql.toString()));
			}
			return new PublicResult<>(PublicResultConstant.SUCCESS, result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
	}
  	
  	
  	
  	 /**
     * @date   2018-10-16
     * @author zuoqb123
     * @todo   Event Actions
     */
  	@ApiOperation(value = "Event Actions", notes = "Event Actions", httpMethod = "GET")
	@RequestMapping(value = "/eventActions", method = RequestMethod.GET)
	public PublicResult<List<Static>> eventActions(HttpServletRequest request,
			@RequestParam(value="pageNum",required = false,defaultValue="1") Integer pageNum,
			@RequestParam(value="pageSize",required = false,defaultValue="25") Integer pageSize,
			@RequestParam(value="siteId",required = false)  String siteId,
			@RequestParam(value="time",required = false)  String time,
			@RequestParam(value="isDesc",required = false,defaultValue="true")  boolean isDesc) {
		try {
			int startNum=(pageNum-1)*pageSize;
			StringBuffer sb=new StringBuffer();
			sb.append(" select va.idaction_event_action as actionKey,a1.`name` as name,count(a1.`name`) as nums from `log_link_visit_action` va   ");
			sb.append(" LEFT JOIN log_action a1 on a1.idaction=va.idaction_event_action ");
			sb.append(" where 1=1 ");
			if(StringUtils.isNotBlank(siteId)){
				sb.append(" and va.idsite='"+siteId+"' ");
			}
			if(StringUtils.isNotBlank(time)){
				sb.append(" and date_format(va.server_time,'%Y%m%d')='"+time+"' ");
			}
			sb.append(" and a1.name is not null group by a1.`name`,a1.idaction  ");
			sb.append("  order by count(a1.`name`) ");
			if(isDesc){
				sb.append(" desc ");
			}
			sb.append(" limit "+startNum+","+pageSize);
			List<Static> result = execePiwikSql(sb.toString());
			for(Static s:result){
				//页面分析: Event Actions 按照页面统计
				StringBuffer sql=new StringBuffer();
				sql.append(" select count(a1.name) as nums,a1.name,'' as actionKey from `log_link_visit_action` va  ");
				sql.append(" left join log_action a1 on a1.idaction=va.idaction_name ");
				sql.append(" where 1=1 ");
				if(StringUtils.isNotBlank(siteId)){
					sql.append(" and va.idsite='"+siteId+"' ");
				}
				if(StringUtils.isNotBlank(time)){
					sql.append(" and date_format(va.server_time,'%Y%m%d')='"+time+"' ");
				}
				sql.append(" and va.idaction_event_action='"+s.getKey()+"' ");
				sql.append(" and a1.name is not null  ");
				sql.append(" group by a1.name order by count(a1.name)  ");
				if(isDesc){
					sql.append(" desc ");
				}
				s.setChildren(execePiwikSql(sql.toString()));
			}
			return new PublicResult<>(PublicResultConstant.SUCCESS, result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
	}
  	
  	
  	 /**
     * @date   2018-10-16
     * @author zuoqb123
     * @todo   Event Actions
     */
  	@ApiOperation(value = "Event Names", notes = "Event Names", httpMethod = "GET")
	@RequestMapping(value = "/eventNames", method = RequestMethod.GET)
	public PublicResult<List<Static>> eventNames(HttpServletRequest request,
			@RequestParam(value="pageNum",required = false,defaultValue="1") Integer pageNum,
			@RequestParam(value="pageSize",required = false,defaultValue="25") Integer pageSize,
			@RequestParam(value="siteId",required = false)  String siteId,
			@RequestParam(value="time",required = false)  String time,
			@RequestParam(value="isDesc",required = false,defaultValue="true")  boolean isDesc) {
		try {
			int startNum=(pageNum-1)*pageSize;
			StringBuffer sb=new StringBuffer();
			sb.append(" select '' as actionKey,count(a1.name) as nums,a1.name from `log_link_visit_action` va    ");
			sb.append(" left join log_action a1 on a1.idaction=va.idaction_name ");
			sb.append(" where 1=1 ");
			if(StringUtils.isNotBlank(siteId)){
				sb.append(" and va.idsite='"+siteId+"' ");
			}
			if(StringUtils.isNotBlank(time)){
				sb.append(" and date_format(va.server_time,'%Y%m%d')='"+time+"' ");
			}
			sb.append(" and a1.name is not null and a1.name!='触点迭代生态圈'  ");
			sb.append(" group by a1.name  ");
			sb.append("  order by count(a1.`name`) ");
			if(isDesc){
				sb.append(" desc ");
			}
			sb.append(" limit "+startNum+","+pageSize);
			List<Static> result = execePiwikSql(sb.toString());
			for(Static s:result){
				//页面分析: Event Names 按照页面统计
				StringBuffer sql=new StringBuffer();
				sql.append(" select count(a2.name) as nums,a2.name,'' as actionKey from `log_link_visit_action` va  ");
				sql.append(" left join log_action a1 on a1.idaction=va.idaction_name ");
				sql.append(" left join log_action a2 on a2.idaction=va.idaction_event_action ");
				sql.append(" where 1=1 ");
				if(StringUtils.isNotBlank(siteId)){
					sql.append(" and va.idsite='"+siteId+"' ");
				}
				if(StringUtils.isNotBlank(time)){
					sql.append(" and date_format(va.server_time,'%Y%m%d')='"+time+"' ");
				}
				sql.append(" and a1.name='"+s.getName()+"' ");
				sql.append(" and a1.name is not null and a1.name!='触点迭代生态圈' ");
				sql.append(" group by a2.name order by count(a1.name)  ");
				if(isDesc){
					sql.append(" desc ");
				}
				s.setChildren(execePiwikSql(sql.toString()));
			}
			return new PublicResult<>(PublicResultConstant.SUCCESS, result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
	}
  	
  	
  	 /**
     * @date   2018-10-16
     * @author zuoqb123
     * @todo   Event Actions
     */
  	@ApiOperation(value = "用户ID统计", notes = "用户ID统计", httpMethod = "GET")
	@RequestMapping(value = "/eventUserId", method = RequestMethod.GET)
	public PublicResult<List<Static>> eventUserId(HttpServletRequest request,
			@RequestParam(value="pageNum",required = false,defaultValue="1") Integer pageNum,
			@RequestParam(value="pageSize",required = false,defaultValue="25") Integer pageSize,
			@RequestParam(value="siteId",required = false)  String siteId,
			@RequestParam(value="time",required = false)  String time,
			@RequestParam(value="isDesc",required = false,defaultValue="true")  boolean isDesc) {
		try {
			int startNum=(pageNum-1)*pageSize;
			StringBuffer sb=new StringBuffer();
			sb.append(" select count(v.user_id) as nums,v.user_id as name,'' as actionKey from `log_visit` v    ");
			sb.append(" where 1=1 ");
			if(StringUtils.isNotBlank(siteId)){
				sb.append(" and v.idsite='"+siteId+"' ");
			}
			if(StringUtils.isNotBlank(time)){
				sb.append(" and date_format(v.visit_last_action_time, '%Y%m%d') = '"+time+"' ");
			}
			sb.append(" and v.user_id is not null  ");
			sb.append(" group by v.user_id order by count(v.user_id)   ");
			if(isDesc){
				sb.append(" desc ");
			}
			sb.append(" limit "+startNum+","+pageSize);
			List<Static> result = execePiwikSql(sb.toString());
			for(Static s:result){
				//用户ID 活动数量统计
				StringBuffer sql=new StringBuffer();
				sql.append(" select count(1) as nums from `log_link_visit_action` va where va.idvisit in(select v.idvisit from `log_visit` v where 1=1  ");
				if(StringUtils.isNotBlank(siteId)){
					sql.append(" and v.idsite='"+siteId+"' ");
				}
				if(StringUtils.isNotBlank(time)){
					sql.append(" and date_format(v.visit_last_action_time,'%Y%m%d')='"+time+"' ");
				}
				sql.append(" and v.user_id='"+s.getName()+"' ");
				sql.append(" ) ");
				Connection conn = ExcelConnection.getPiwikConn();
				PreparedStatement pstmt = null;
				pstmt = (PreparedStatement) conn.prepareStatement(sql.toString());
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					s.setKey(rs.getString("nums"));
				
				}
				conn.close();
				
			}
			return new PublicResult<>(PublicResultConstant.SUCCESS, result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
	}
  	
  	
  	 /**
     * @date   2018-10-16
     * @author zuoqb123
     * @todo   站点信息
     */
  	@ApiOperation(value = "站点信息", notes = "站点信息", httpMethod = "GET")
	@RequestMapping(value = "/sites", method = RequestMethod.GET)
	public PublicResult<List<Map<String,Object>>> sites(HttpServletRequest request) {
		try {
			List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
			StringBuffer sql=new StringBuffer();
			sql.append(" SELECT * FROM `site` ");
			Connection conn = ExcelConnection.getPiwikConn();
			PreparedStatement pstmt = null;
			pstmt = (PreparedStatement) conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("idsite", rs.getString("idsite"));
				map.put("name", rs.getString("name"));
				map.put("mainUrl", rs.getString("main_url"));
				result.add(map);
			}
			conn.close();
			return new PublicResult<>(PublicResultConstant.SUCCESS, result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
	}

	protected List<Static> execePiwikSql(String sql) throws SQLException {
		List<Static> result=new ArrayList<Static>();
		Connection conn = ExcelConnection.getPiwikConn();
		PreparedStatement pstmt = null;
		pstmt = (PreparedStatement) conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			Static data=new Static(rs.getString("name"),rs.getString("nums"),rs.getString("actionKey"));
			//System.out.println(data.toString());
			result.add(data);
		
		}
		conn.close();
		return result;
	}
    
}
class Static{
	private String name;
	private String nums;
	private String key;
	private List<Static> children=new ArrayList<Static>();
	public String getName() {
		return name;
	}
	public Static() {
		super();
	}
	public Static(String name, String nums) {
		super();
		this.name = name;
		this.nums = nums;
	}

	public Static(String name, String nums, String key) {
		super();
		this.name = name;
		this.nums = nums;
		this.key = key;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNums() {
		return nums;
	}
	public void setNums(String nums) {
		this.nums = nums;
	}
	public List<Static> getChildren() {
		return children;
	}
	public void setChildren(List<Static> children) {
		this.children = children;
	}
}