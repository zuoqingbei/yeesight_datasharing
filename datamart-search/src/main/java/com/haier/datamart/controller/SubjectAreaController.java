package com.haier.datamart.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haier.datamart.annotation.Log;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.entity.ScanSubjectArea;
import com.haier.datamart.entity.ScanSubjectAreaIndex;
import com.haier.datamart.entity.User;
import com.haier.datamart.mapper.ScanSubjectAreaIndexMapper;
import com.haier.datamart.service.IScanSubjectAreaService;
import com.haier.datamart.service.IUserService;
import com.haier.datamart.utils.GenerationSequenceUtil;
@RestController
public class SubjectAreaController extends BaseController{
	@Autowired
	private IUserService userServiceImpl;
	
	@Autowired
	private IScanSubjectAreaService scanSubjectAreaService;
	@Autowired
	private ScanSubjectAreaIndexMapper areaIndexMapper;
	/**
	 * 后台 新增-回显主题域
	 * @author dsh
	 * @date 2018年6月13日 下午1:17:18
	 * @TODO
	 */
	@GetMapping(value = "/subjectArea/subject", produces = { "application/json;charset=UTF-8" })
	@Log(description="API接口:/subjectArea/subject")
	public Object reArea(HttpServletRequest request){ 
		String name=request.getParameter("name");
		List<ScanSubjectArea> r=new ArrayList<ScanSubjectArea>();
		//subjectAreaList=scanSubjectAreaService.getAll();
		ScanSubjectArea scansubjectarea=new ScanSubjectArea();
		
		scansubjectarea.setName(name);
		//subjectAreaList=scanSubjectAreaService.getAll();
		
		scansubjectarea.setName(name);
		//scansubjectarea.setParentId("0");
		/*List<ScanSubjectArea> subjectAreaList=scanSubjectAreaService.selectAllbyLikename(scansubjectarea);
		for(ScanSubjectArea s:subjectAreaList){
			scansubjectarea.setParentId(s.getId());
			List<ScanSubjectArea> children=scanSubjectAreaService.selectAllbyLikename(scansubjectarea);
			s.setChildren(children);
			s.setKey(s.getId());
		}
		return new PublicResult<>(PublicResultConstant.SUCCESS, subjectAreaList);*/	
		List<ScanSubjectArea> subjectAreaList=scanSubjectAreaService.selectAllbyLikename(scansubjectarea);
		
		ScanSubjectArea subjectarea=new ScanSubjectArea();

		List<ScanSubjectArea> subjectAreaListAll=scanSubjectAreaService.selectAllbyLikename(subjectarea);
		if(StringUtils.isBlank(name)){
			for(ScanSubjectArea t:subjectAreaList){
				t.setKey(t.getId());
				if("0".equals(t.getParentId())){
						t.setChildren(ScanSubjectArea.getChildrenByPid(t.getId(), subjectAreaList,true));
						r.add(t);
				}
			}
			//排序
			 Collections.sort(r, new Comparator<ScanSubjectArea>() {  
				  
		            @Override  
		            public int compare(ScanSubjectArea o1, ScanSubjectArea o2) {  
		                // 进行升序排列  
		                if (o1.getSort().intValue() > o2.getSort().intValue()) {  
		                    return 1;  
		                }  
		                if (o1.getSort() == o2.getSort()) {  
		                    return 0;  
		                }  
		                return -1;  
		            }  
		        });  
			return new PublicResult<>(PublicResultConstant.SUCCESS, r);	
		}else{
			for(ScanSubjectArea t:subjectAreaListAll){
				t.setKey(t.getId());
				for(ScanSubjectArea t2:subjectAreaList){
					if(t.getId().equals(t2.getParentId())){
							t2.setParent(ScanSubjectArea.getParentByPid(t2.getParentId(), subjectAreaListAll,true));
							r.add(t2);
					}
				}
				
			}
			System.out.println("进入模糊查询===========================================");
			return new PublicResult<>(PublicResultConstant.SUCCESS, r);	
		}
		
	}
	/**
	 * 新增主题域
	* @author doushuihai  
	* @date 2018年6月15日上午11:11:34  
	* @TODO
	 */
	@GetMapping(value = "/subjectArea/addSubject", produces = { "application/json;charset=UTF-8" })
	@Log(description="API接口:/subjectArea/addSubject")
	public Object addSubject(HttpServletRequest request){
		try {
			String parentId=request.getParameter("parentId");
			String name=request.getParameter("name");
			String sort=request.getParameter("sort");
			String description=request.getParameter("description");
			ScanSubjectArea subjectArea=new ScanSubjectArea();
		    
			User user=getLoginUser(request);
			
			if(user!=null&&StringUtils.isNotBlank(user.getId())){
				subjectArea.setCreateBy(user.getName());
				subjectArea.setUpdateBy(user.getName());
				}
			if(StringUtils.isNotBlank(name) && StringUtils.isNotBlank(parentId)){
				subjectArea.setName(name);
				subjectArea.setParentId(parentId);
				if(StringUtils.isNotBlank(sort)){
					subjectArea.setSort(new BigDecimal(sort));
				}else{
					subjectArea.setSort(new BigDecimal("0"));
				}
				if(StringUtils.isNotBlank(description)){
					subjectArea.setDescription(description);
				}
			}else{
				return new PublicResult<>(PublicResultConstant.FAILED, null);
			}
			subjectArea.setCreateDate(new Date());
			subjectArea.setUpdateDate(new Date());
			subjectArea.setId(GenerationSequenceUtil.getUUID());
			scanSubjectAreaService.addExcle(subjectArea);
			
		    	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED, null);
		}
		return new PublicResult<>(PublicResultConstant.SUCCESS, null);
	}
	
	
	/**
	 * 修改主题域
	* @author doushuihai  
	* @date 2018年6月15日上午11:11:34  
	* @TODO
	 */
	@GetMapping(value = "/subjectArea/updateSubject", produces = { "application/json;charset=UTF-8" })
	@Log(description="API接口:/subjectArea/updateSubject")
	public Object updateSubject(HttpServletRequest request){
		try {
			String id=	request.getParameter("id");
			String parentId=request.getParameter("parentId");
			String name=request.getParameter("name");
			String sort=request.getParameter("sort");
			String description=request.getParameter("description");
			ScanSubjectArea subjectArea=new ScanSubjectArea();
		    HttpSession session=request.getSession();
			String userId =(String) session.getAttribute("userId");
			User user=null;
			if (StringUtils.isNotBlank(userId)) {
					 user  = userServiceImpl.selectOne(userId);
				}
			if(user!=null&&StringUtils.isNotBlank(user.getId())){
				subjectArea.setUpdateBy(user.getName());
				}
			if(StringUtils.isNotBlank(id)){
				subjectArea.setId(id);
				subjectArea.setParentId(parentId);
				if(StringUtils.isNotBlank(name)){
					subjectArea.setName(name);
				}
				
				if(StringUtils.isNotBlank(sort)){
					subjectArea.setSort(new BigDecimal(sort));
				}else{
					subjectArea.setSort(new BigDecimal("0"));
				}
				subjectArea.setDescription(description);
				subjectArea.setUpdateDate(new Date());
				int status=scanSubjectAreaService.updateSubjectArea(subjectArea);
			}else{
				return new PublicResult<>(PublicResultConstant.FAILED, null);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED, null);
		}
		return new PublicResult<>(PublicResultConstant.SUCCESS, null);
	}
	
	

	/**
	 * 删除主题域
	* @author doushuihai  
	* @date 2018年6月15日上午11:12:11  
	* @TODO
	 */
	@GetMapping(value = "/subjectArea/deleteSubject", produces = { "application/json;charset=UTF-8" })
	@Log(description="API接口:/subjectArea/deleteSubject")
	public Object delete(HttpServletRequest request){
	try {
		String id=	request.getParameter("id");
		//查询该主题域是否绑定 ，绑定则不能删除
      List<ScanSubjectAreaIndex> areaIndexs=		areaIndexMapper.getArea(id);
		if (areaIndexs.size()==0) {
			 int status=scanSubjectAreaService.deleteSubject(id);
				List<ScanSubjectArea> subjectAreaList=scanSubjectAreaService.getAll();
				List<String> allchildren = getAll(id,subjectAreaList);//删除子级主题域
				for(String subjectid:allchildren){
					scanSubjectAreaService.deleteSubject(subjectid);
				}
					return new PublicResult<>(PublicResultConstant.SUCCESS, allchildren);
		}else {
			return new PublicResult<>(PublicResultConstant.FAILED, "该主题域已绑定关系，不可删除!");

		}
     
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return new PublicResult<>(PublicResultConstant.FAILED, null);
	}
	

	
	}
	/**
	 * 获取子级主题域
	 */
	List<String> list2 = new ArrayList<String>();
	public List<String> getAll(String id,List<ScanSubjectArea> list){
			Iterator iter = list.iterator();
			while(iter.hasNext()){
				ScanSubjectArea subjectArea = (ScanSubjectArea)iter.next();
				if(id.equals(subjectArea.getParentId())){
					list2.add(subjectArea.getId());
					getAll(subjectArea.getId(),list);
				}
			}
			return list2;
		}
}
