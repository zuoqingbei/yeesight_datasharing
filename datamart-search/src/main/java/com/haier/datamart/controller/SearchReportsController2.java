package com.haier.datamart.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.derby.tools.sysinfo;
import org.apache.logging.log4j.core.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.entity.JenkinsSupportInfo;
import com.haier.datamart.entity.SearchIndex;
import com.haier.datamart.entity.SearchReports2;
import com.haier.datamart.entity.SearchReportsFile;
import com.haier.datamart.entity.SearchReportsIndex;
import com.haier.datamart.service.IJenkinsSupportInfoService;
import com.haier.datamart.service.ISearchIndexService;
import com.haier.datamart.service.ISearchReportsFileService;
import com.haier.datamart.service.ISearchReportsIndexService;
import com.haier.datamart.service.ISearchReportsService2;
import com.haier.datamart.utils.UUIDUtils;
/**
 *
 * @author zuoqb123
 * @date 2018-09-29
 * @todo 报表信息路由
 */
@RestController
@RequestMapping("/api/SearchReports2")
@Api(value = "报表信息",description="报表信息 @author zuoqb123")
public class SearchReportsController2 extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(SearchReportsController2.class);

    @Autowired
    public ISearchReportsService2 iSearchReportsService;
    @Autowired
    public ISearchReportsFileService iSearchReportsFileService;
	@Autowired
	private ISearchIndexService searchIndex;
	@Autowired
	private ISearchReportsIndexService searchReportsIndexService;
	
	@Autowired
	public IJenkinsSupportInfoService iJenkinsSupportInfoService;

    /**
     * @date   2018-09-29
     * @author zuoqb123
     * @todo   新增报表信息
     */
  	@ApiOperation(value = "新增报表信息", notes = "新增报表信息", httpMethod = "POST")
	@RequestMapping(value = "/add", method = RequestMethod.POST,produces = { "application/json;charset=UTF-8" })
	public PublicResult<SearchReports2> add(HttpServletRequest request,SearchReports2 entity) {
		try {
			if(StringUtils.isBlank(entity.getId())){
				//新增
				entity.setId(UUIDUtils.getUuid());
				entity.setCreateDate(new Date());
				entity.setCreateBy(getLoginUser(request).getId());
				if(iSearchReportsService.insert(entity)){
					for(SearchReportsFile file:entity.getFiles()){
						file.setId(UUIDUtils.getUuid());
						file.setCreateDate(new Date());
						file.setCreateBy(getLoginUser(request).getId());
						file.setReportId(entity.getId());
						iSearchReportsFileService.insert(file);
					}
					for(String indexId:entity.getIndexs()){
						SearchReportsIndex r=new SearchReportsIndex();
						r.setId(UUIDUtils.getUuid());
						r.setIndexId(indexId);
						r.setReportId(entity.getId());
						searchReportsIndexService.insert(r);
					}
					return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
				}else{
					return new PublicResult<>(PublicResultConstant.ERROR, null);
				}
			}else{
				return new PublicResult<>(PublicResultConstant.PARAM_ERROR, "新增主键必须为空!",null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
	}
    
    /**
     * @date   2018-09-29
     * @author zuoqb123
     * @todo   删除报表信息
     */
  	@ApiOperation(value = "删除报表信息", notes = "删除报表信息", httpMethod = "DELETE")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE,produces = { "application/json;charset=UTF-8" })
	public PublicResult<SearchReports2> delete(HttpServletRequest request,@PathVariable("id") String id) {
		try {
			SearchReports2 entity=new SearchReports2();
			entity.setId(id);
			entity.setDelFlag(DEL_FLAG);
			entity.setUpdateDate(new Date());
			entity.setUpdateBy(getLoginUser(request).getId());
			 if(iSearchReportsService.updateById(entity)){
				 EntityWrapper<SearchReportsFile> wrapper = new EntityWrapper<SearchReportsFile>();
				 wrapper.eq("report_id", entity.getId());
				 iSearchReportsFileService.delete(wrapper);
				 
				 //删除对照
				 EntityWrapper<SearchReportsIndex> rwrapper = new EntityWrapper<SearchReportsIndex>();
				 rwrapper.eq("report_id", entity.getId());
				 searchReportsIndexService.delete(rwrapper);
				 return new PublicResult<>(PublicResultConstant.SUCCESS, null);
			 }else{
				 return new PublicResult<>(PublicResultConstant.ERROR, null);
			 }
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
	}
	
	 /**
     * @date   2018-09-29
     * @author zuoqb123
     * @todo   更新报表信息
     */
  	@ApiOperation(value = "更新报表信息", notes = "更新报表信息", httpMethod = "PUT")
	@RequestMapping(value = "/update", method = RequestMethod.PUT,produces = { "application/json;charset=UTF-8" })
	public PublicResult<SearchReports2> update(HttpServletRequest request,SearchReports2 entity) {
		try {
			if(entity!=null&&StringUtils.isNotBlank(entity.getId())){
				//更新
				entity.setUpdateDate(new Date());
				entity.setUpdateBy(getLoginUser(request).getId());
				if(iSearchReportsService.updateById(entity)){
					//
					EntityWrapper<SearchReportsFile> wrapper = new EntityWrapper<SearchReportsFile>();
					wrapper.eq("report_id", entity.getId());
					iSearchReportsFileService.delete(wrapper);
					for(SearchReportsFile file:entity.getFiles()){
						file.setId(UUIDUtils.getUuid());
						file.setCreateDate(new Date());
						file.setCreateBy(getLoginUser(request).getId());
						file.setReportId(entity.getId());
						iSearchReportsFileService.insert(file);
					}
					
					 //删除对照
					 EntityWrapper<SearchReportsIndex> rwrapper = new EntityWrapper<SearchReportsIndex>();
					 rwrapper.eq("report_id", entity.getId());
					 searchReportsIndexService.delete(rwrapper);
					for(String indexId:entity.getIndexs()){
						SearchReportsIndex r=new SearchReportsIndex();
						r.setId(UUIDUtils.getUuid());
						r.setIndexId(indexId);
						r.setReportId(entity.getId());
						searchReportsIndexService.insert(r);
					}
					return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
				}else{
					return new PublicResult<>(PublicResultConstant.ERROR, null);
				}
			}else{
				return new PublicResult<>(PublicResultConstant.PARAM_ERROR, "修改主键不能为空!",null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
	}
    
    
    /**
     * @date   2018-09-29
     * @author zuoqb123
     * @todo   查询单个报表信息
     */
  	@ApiOperation(value = "查询单个报表信息", notes = "查询单个报表信息", httpMethod = "GET")
  	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" } )
  	public PublicResult<SearchReports2> get(HttpServletRequest request,@PathVariable("id") String id) {
  		SearchReports2 entity=null;
  		try {
  			EntityWrapper<SearchReports2> wrapper = new EntityWrapper<SearchReports2>();
  			wrapper.where("del_flag={0}", UN_DEL_FLAG);
  			wrapper.eq("id", id);
  			entity=iSearchReportsService.selectOne(wrapper);
  			if(entity!=null){
				EntityWrapper<SearchReportsIndex> w1 = new EntityWrapper<SearchReportsIndex>();
				w1.where("del_flag={0}", UN_DEL_FLAG);
				w1.eq("report_id",entity.getId());
				List<SearchReportsIndex> indexs=searchReportsIndexService.selectList(w1);
				List<String> ids=new ArrayList<String>();
				for(SearchReportsIndex s:indexs){
					ids.add(s.getIndexId());
				}
				entity.setIndexs(ids);
				//报表文件
				EntityWrapper<SearchReportsFile> filewrapper = new EntityWrapper<SearchReportsFile>();
				filewrapper.where("del_flag={0}", UN_DEL_FLAG);
				filewrapper.eq("report_id",entity.getId());
				List<SearchReportsFile> files = iSearchReportsFileService.selectList(filewrapper);
				entity.setFiles(files);
			}
  			return new PublicResult<>(PublicResultConstant.SUCCESS, entity);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(), null);
		}
  	}
	
    /**
     * @date   2018-09-29
     * @author zuoqb123
     * @todo   分页查询报表信息
     */
  	@ApiOperation(value = "分页查询报表信息", notes = "分页查询报表信息", httpMethod = "GET")
  	@RequestMapping(value = "/list", method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
    public PublicResult<Page<SearchReports2>> list(SearchReports2 entity,@RequestParam(value="pageNum",required = false,defaultValue="1") Integer pageNum,
			@RequestParam(value="pageSize",required = false,defaultValue="10") Integer pageSize,HttpServletRequest request) {
		try {
			EntityWrapper<SearchReports2> wrapper = searchWrapper(request, entity);
			int cu=(pageNum-1)*pageSize;
			Page<SearchReports2> pages=new Page<SearchReports2>(cu, pageSize);
			//Page<SearchReports2> page = iSearchReportsService.selectPage(pages, wrapper);
			
			List<SearchReports2> data=new ArrayList<SearchReports2>();
			PageHelper.startPage(pageNum, pageSize);
			List<SearchReports2> list = iSearchReportsService.selectList(wrapper);
			PageInfo<SearchReports2> pagess = new PageInfo<SearchReports2>(list,pageSize);
			pages.setTotal(pagess.getList().size());
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for(int x=0;x<pagess.getList().size();x++){
				if(x>=cu&&x<pageNum*pageSize){
					SearchReports2 s=pagess.getList().get(x);
					s.setDateStr(sdf.format(s.getCreateDate()));
					//发版信息
					EntityWrapper<JenkinsSupportInfo> jenkins = new EntityWrapper<JenkinsSupportInfo>();
					jenkins.where("del_flag={0}", UN_DEL_FLAG);
					jenkins.eq("type", 1);
					jenkins.eq("business_id", s.getId());
					JenkinsSupportInfo j=iJenkinsSupportInfoService.selectOne(jenkins);
					s.setJenkins(j);
					data.add(s);
				}
			}
			pages.setRecords(data);
			return new PublicResult<>(PublicResultConstant.SUCCESS, pages);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new PublicResult<>(PublicResultConstant.FAILED,e.getMessage(),null);
		}

	}
    /**
     * @date   2018年9月25日 下午5:36:10
     * @author zuoqb123
     * @todo   构建查询条件-以后扩展
     */
    private EntityWrapper<SearchReports2> searchWrapper(HttpServletRequest request, SearchReports2 entity) {
		EntityWrapper<SearchReports2> wrapper = new EntityWrapper<SearchReports2>();
		wrapper.where("del_flag={0}", UN_DEL_FLAG);
		if(getLoginUser(request)!=null&&StringUtils.isNotBlank(getLoginUser(request).getId())){
			if(!isAdmin(request))
			 wrapper.and("create_by", getLoginUser(request).getId());
		}
				//根据主键模糊查询
		if(entity.getId()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getId()))){
			wrapper.like("id", String.valueOf(entity.getId()));
		}
				//根据显示系统模糊查询
		if(entity.getSystemName()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getSystemName()))){
			wrapper.like("system_name", String.valueOf(entity.getSystemName()));
		}
				//根据显示系统连接地址模糊查询
		if(entity.getLink()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getLink()))){
			wrapper.like("link", String.valueOf(entity.getLink()));
		}
				//根据打开类型 _balnk _target模糊查询
		if(entity.getOpenType()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getOpenType()))){
			wrapper.like("open_type", String.valueOf(entity.getOpenType()));
		}
				//根据屏幕截图模糊查询
		if(entity.getScreenUrl()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getScreenUrl()))){
			wrapper.like("screen_url", String.valueOf(entity.getScreenUrl()));
		}
				//根据名称模糊查询
		if(entity.getName()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getName()))){
			wrapper.like("name", String.valueOf(entity.getName()));
		}
				//根据报表类型 字典表模糊查询
		if(entity.getTypes()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getTypes()))){
			wrapper.like("types", String.valueOf(entity.getTypes()));
		}
				//根据报表路径模糊查询
		if(entity.getPath()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getPath()))){
			wrapper.like("path", String.valueOf(entity.getPath()));
		}
				//根据报表地址模糊查询
		if(entity.getUrl()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getUrl()))){
			wrapper.like("url", String.valueOf(entity.getUrl()));
		}
				//根据模糊查询
		if(entity.getCategory1()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getCategory1()))){
			wrapper.like("category1", String.valueOf(entity.getCategory1()));
		}
				//根据模糊查询
		if(entity.getCategory2()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getCategory2()))){
			wrapper.like("category2", String.valueOf(entity.getCategory2()));
		}
				//根据模糊查询
		if(entity.getCategory3()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getCategory3()))){
			wrapper.like("category3", String.valueOf(entity.getCategory3()));
		}
				//根据标签模糊查询
		if(entity.getTags()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getTags()))){
			wrapper.like("tags", String.valueOf(entity.getTags()));
		}
				//根据浏览量模糊查询
		if(entity.getViewNum()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getViewNum()))){
			wrapper.like("view_num", String.valueOf(entity.getViewNum()));
		}
				//根据点赞量模糊查询
		if(entity.getZanNum()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getZanNum()))){
			wrapper.like("zan_num", String.valueOf(entity.getZanNum()));
		}
				//根据分享数模糊查询
		if(entity.getShareNum()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getShareNum()))){
			wrapper.like("share_num", String.valueOf(entity.getShareNum()));
		}
				//根据创建人模糊查询
		if(entity.getCreateBy()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getCreateBy()))){
			wrapper.like("create_by", String.valueOf(entity.getCreateBy()));
		}
				//根据创建时间模糊查询
		if(entity.getCreateDate()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getCreateDate()))){
			wrapper.like("create_date", String.valueOf(entity.getCreateDate()));
		}
				//根据更新人模糊查询
		if(entity.getUpdateBy()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getUpdateBy()))){
			wrapper.like("update_by", String.valueOf(entity.getUpdateBy()));
		}
				//根据更新时间模糊查询
		if(entity.getUpdateDate()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getUpdateDate()))){
			wrapper.like("update_date", String.valueOf(entity.getUpdateDate()));
		}
				//根据备注模糊查询
		if(entity.getRemarks()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getRemarks()))){
			wrapper.like("remarks", String.valueOf(entity.getRemarks()));
		}
				//根据模糊查询
		if(entity.getDelFlag()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getDelFlag()))){
			wrapper.like("del_flag", String.valueOf(entity.getDelFlag()));
		}
				//根据网络类型模糊查询
		if(entity.getNetType()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getNetType()))){
			wrapper.like("net_type", String.valueOf(entity.getNetType()));
		}
				//根据描述模糊查询
		if(entity.getDescs()!=null&&StringUtils.isNotBlank(String.valueOf(entity.getDescs()))){
			wrapper.like("descs", String.valueOf(entity.getDescs()));
		}
				wrapper.orderBy("create_date", true);
		System.out.println(wrapper.originalSql());
		return wrapper;
	}
}

