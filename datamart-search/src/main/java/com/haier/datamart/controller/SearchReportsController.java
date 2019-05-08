package com.haier.datamart.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.haier.datamart.annotation.Log;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.entity.JenkinsSupportInfo;
import com.haier.datamart.entity.SearchIndex;
import com.haier.datamart.entity.SearchReports;
import com.haier.datamart.entity.SearchReportsFile;
import com.haier.datamart.service.IJenkinsSupportInfoService;
import com.haier.datamart.service.ISearchIndexService;
import com.haier.datamart.service.ISearchReportsFileService;
import com.haier.datamart.service.ISearchReportsService;

/**
 * <p>
 * 报表信息 前端控制器
 * </p>
 *
 * @author lixiaoyi123
 * @since 2018-05-23
 */
@RestController
public class SearchReportsController extends BaseController {
	@Autowired
	private ISearchReportsService searchReportsService;
	@Autowired
    public ISearchReportsFileService iSearchReportsFileService;
	@Autowired
	private ISearchIndexService searchIndex;
	@Autowired
	public IJenkinsSupportInfoService iJenkinsSupportInfoService;
	private final Logger logger = LoggerFactory
			.getLogger(SearchReportsController.class);

	@Autowired
	public ISearchReportsService iSearchReportsService;

	/**
	 * 报表详情 参数 ID
	 * 
	 * @author lixiaoyi
	 * @date 2018年5月24日 上午11:06:55
	 * @TODO
	 */
	@GetMapping(value = "/report/apimingxi", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/report/apimingxi")
	public PublicResult getId(HttpServletRequest request, Model model) {
		SearchReports search = new SearchReports();
		try {
			String id = request.getParameter("id");
			if (StringUtils.isNotBlank(id)) {
				search = searchReportsService.getById(id);
				if(search!=null){
					List<SearchIndex> indexs = searchIndex.getInclude(id);
					search.setIndexs(indexs);
					//报表文件
					EntityWrapper<SearchReportsFile> wrapper = new EntityWrapper<SearchReportsFile>();
					wrapper.where("del_flag={0}", UN_DEL_FLAG);
					wrapper.eq("report_id",search.getId());
					List<SearchReportsFile> files = iSearchReportsFileService.selectList(wrapper);
					search.setFiles(files);
					//发版信息
					EntityWrapper<JenkinsSupportInfo> jenkins = new EntityWrapper<JenkinsSupportInfo>();
					jenkins.where("del_flag={0}", UN_DEL_FLAG);
					jenkins.eq("type", 1);
					jenkins.eq("business_id", id);
					JenkinsSupportInfo j=iJenkinsSupportInfoService.selectOne(jenkins);
					search.setJenkins(j);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED, null);
		}
		return new PublicResult<>(PublicResultConstant.SUCCESS, search);
	}

	/**
	 * 报表关键词搜索
	 * 
	 * @author lixiaoyi
	 * @date 2018年5月24日 下午4:12:57
	 * @TODO
	 */
	@GetMapping(value = "/report/keyword", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/report/keyword")
	public Object getKeyword(HttpServletRequest request) {
		List<SearchReports> search = new ArrayList<SearchReports>();
		try {
			String keyword = request.getParameter("keyword");
			String pageNumstr = request.getParameter("pageNum");
			String sizestr = request.getParameter("size");
			int pageNum = 1;
			int pagesize = 10;
			if (StringUtils.isNotBlank(pageNumstr)) {

				pageNum = Integer.parseInt(pageNumstr);
			}
			if (StringUtils.isNotBlank(sizestr)) {

				pagesize = Integer.parseInt(sizestr);
			}

			int index = (pageNum - 1) * pagesize;
			SearchReports searchReports = new SearchReports();
			searchReports.setName(keyword);
			searchReports.setPageNum(index);
			searchReports.setSize(pagesize);
			search = searchReportsService.getKeyword(searchReports);

		} catch (Exception e) {
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED, null);
		}
		return new PublicResult<>(PublicResultConstant.SUCCESS, search);
	}
	/**
	 * 
	 * @time   2018年10月9日 下午12:59:51
	 * @author zuoqb
	 * @todo   导出报告申请流程文档
	 * @param  @param response
	 * @param  @param request
	 * @param  @return
	 * @return_type   Object
	 */
	@GetMapping(value = "/report/doc", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/report/doc")
	public void downloadFileFromSysDir(HttpServletResponse response,
			HttpServletRequest request) {
		String path = request.getParameter("path");
		String name=null;
		if(StringUtils.isBlank(path)){
			String id = request.getParameter("id");
			if(StringUtils.isBlank(id)){
				//return new PublicResult<>(PublicResultConstant.INVALID_PARAM_EMPTY, null);
				return;
			}
			EntityWrapper<SearchReportsFile> wrapper = new EntityWrapper<SearchReportsFile>();
  			wrapper.where("del_flag={0}", UN_DEL_FLAG);
  			wrapper.eq("id", id);
  			SearchReportsFile entity=iSearchReportsFileService.selectOne(wrapper);
			if(entity==null||StringUtils.isBlank(entity.getDocPath())){
				//return new PublicResult<>(PublicResultConstant.NOT_DATA, null);
				return;
			}
			path = entity.getDocPath();
			name=entity.getDocName();
		}
		byte[] buff = new byte[1024];
		BufferedInputStream bis = null;
		OutputStream os = null;
		try {
			File file = new File(path);
			String fileName=file.getName();    
			String fileTyle=fileName.substring(fileName.lastIndexOf("."),fileName.length()).toLowerCase(); 
			if("xls".equals(fileTyle)||"xlsx".equals(fileTyle)){
				response.setContentType("application/msexcel");
			}
			if("doc".equals(fileTyle)||"docx".equals(fileTyle)){
				response.setContentType("application/msword");
			}
			if("pdf".equals(fileTyle)){
				response.setContentType("application/pdf");
			}
			if("ppt".equals(fileTyle)){
				response.setContentType("application/ms-powerpoint");
			}
			if("txt".equals(fileTyle)){
				response.setContentType("text/txt");
			}
			if("zip".equals(fileTyle)){
				response.setContentType("application/zip");
			}
			if("htm".equals(fileTyle)||"html".equals(fileTyle)){
				response.setContentType("text/html");
			}
			if("rm".equals(fileTyle)||"ram".equals(fileTyle)){
				response.setContentType("audio/x-pn-realaudio");
			}
			response.setHeader("Content-disposition",
					"attachment; filename=" + new String(name==null?file.getName().getBytes():(name+"."+fileTyle).getBytes(), "iso-8859-1"));
			os = response.getOutputStream();
			bis = new BufferedInputStream(new FileInputStream(file));
			int i = bis.read(buff);
			while (i != -1) {
				os.write(buff, 0, buff.length);
				os.flush();
				i = bis.read(buff);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		//return new PublicResult<>(PublicResultConstant.SUCCESS, null);
	}

}
