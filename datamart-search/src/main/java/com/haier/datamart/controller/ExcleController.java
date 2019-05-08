package com.haier.datamart.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.haier.datamart.annotation.Log;
import com.haier.datamart.base.PublicResult;
import com.haier.datamart.base.PublicResultConstant;
import com.haier.datamart.entity.AdminDataContent;
import com.haier.datamart.entity.AdminDataContentDetail;
import com.haier.datamart.entity.AdminDatasourceConfig;
import com.haier.datamart.entity.Dict;
import com.haier.datamart.entity.ScanIndexTableRelative;
import com.haier.datamart.entity.ScanSubjectArea;
import com.haier.datamart.entity.ScanSubjectAreaIndex;
import com.haier.datamart.entity.SearchIndex;
import com.haier.datamart.entity.SearchIndexDimension;
import com.haier.datamart.entity.SearchIndexPlat;
import com.haier.datamart.entity.SearchReports;
import com.haier.datamart.entity.SearchReportsDimension;
import com.haier.datamart.entity.SearchReportsIndex;
import com.haier.datamart.entity.User;
import com.haier.datamart.mapper.SearchIndexMapper;
import com.haier.datamart.mapper.SearchIndexPlatMapper;
import com.haier.datamart.mapper.SearchReportsMapper;
import com.haier.datamart.service.IDictService;
import com.haier.datamart.service.IEnteringTableSettingInfoService;
import com.haier.datamart.service.IScanSubjectAreaIndexService;
import com.haier.datamart.service.IScanSubjectAreaService;
import com.haier.datamart.service.ISearchIndexService;
import com.haier.datamart.service.ISearchReportsDimensionService;
import com.haier.datamart.service.ISearchReportsIndexService;
import com.haier.datamart.service.ISearchReportsService;
import com.haier.datamart.service.IUserService;
import com.haier.datamart.utils.ExcleUtils;
import com.haier.datamart.utils.GenerationSequenceUtil;
import com.haier.datamart.utils.SearchIndexExport;
import com.haier.datamart.utils.SysIndexReportExport;


@RestController
public class ExcleController extends BaseController{
	@Autowired
	private IEnteringTableSettingInfoService infoService;
  @Autowired
  private IUserService userService;
  @Autowired
  private ISearchReportsService searchReportsService;
  @Autowired
  private IDictService dictService;
  @Autowired
  private ISearchReportsDimensionService ReportsDimensionService;
  @Autowired
  private SearchReportsMapper reportMapper;
  @Autowired
  private ISearchIndexService indexService;
  @Autowired
  private IScanSubjectAreaService scanSubjectAreaService;
  @Autowired
  private IScanSubjectAreaIndexService areaIndexService;
  @Autowired
  private ISearchReportsIndexService reportIndexService;
  @Autowired
  private SearchIndexPlatMapper platMapper;
  @Autowired
  private SearchIndexMapper indexMapper;
  @Autowired
  private IDictService dictServiceImpl;
 
 
	
	@RequestMapping(value = "/report/excle", produces = { "application/json;charset=UTF-8" },method=RequestMethod.POST)
	@Log(description="API接口:/report/excle")
	public Object addReportExcle(HttpServletRequest request,@RequestParam("file") MultipartFile file) throws IOException{
	User user=	 getLoginUser(request);
		  //根据指定的文件输入流导入Excel从而产生Workbook对象
		  Workbook wb0 =null;
		  int re=0;
		     if (file!=null) {
		    	//根据指定的文件输入流导入Excel从而产生Workbook对象
		    	 if (ExcleUtils.isExcel2007(file.getOriginalFilename())) {
		    		 wb0 = new XSSFWorkbook(file.getInputStream());
				}else {
					wb0 = new HSSFWorkbook(file.getInputStream());
				}
		     
		  //获取Excel文档中的第一个表单
		  Sheet sht0 = wb0.getSheetAt(0);
		  
		  
		  //对Sheet中的每一行进行迭代
		  for (Row r : sht0) {
		          //如果当前行的行号（从0开始）未达到2（第三行）则从新循环
		  if(r.getRowNum()<1){
		  continue;
		  }
		  String ids=GenerationSequenceUtil.getUUID();
		  SearchReports reports=new SearchReports();
		    reports.setId(ids);
		    if (r.getCell(0)!=null) {
		    	 reports.setScreenUrl(r.getCell(0).getStringCellValue());
				  	
			}
			if (r.getCell(1)!=null) {
				  reports.setSystemName(r.getCell(1).getStringCellValue());
				   					
	    	}
			if (r.getCell(2)!=null) {
				 reports.setLink(r.getCell(2).getStringCellValue());
				  	
			}
			if (r.getCell(3)!=null) {
				  reports.setOpenType(r.getCell(3).getStringCellValue());
				   	
			}
			if (r.getCell(4)!=null) {
				 reports.setName(r.getCell(4).getStringCellValue());
				  	
			}
			if (r.getCell(5)!=null) {
				  reports.setTypes(r.getCell(5).getStringCellValue());
			}
			
			if (r.getCell(7)!=null) {
				reports.setViewNum((int)r.getCell(7).getNumericCellValue());		
			}
			if (r.getCell(8)!=null) {
				reports.setZanNum((int)r.getCell(8).getNumericCellValue());	
			}
			if (r.getCell(9)!=null) {
				 reports.setShareNum((int)r.getCell(9).getNumericCellValue());	
			}
			if (r.getCell(10)!=null) {
				  reports.setPath(r.getCell(10).getStringCellValue());
				  
			}
			if (r.getCell(11)!=null) {
				  reports.setUrl(r.getCell(11).getStringCellValue());
				 	
			}
			if (r.getCell(12)!=null) {
				   reports.setCategory1(r.getCell(12).getStringCellValue());
				  
			}
			if (r.getCell(13)!=null) {
				  reports.setCategory2(r.getCell(13).getStringCellValue());
				  	
			}
			if (r.getCell(14)!=null) {
				  reports.setCategory3(r.getCell(14).getStringCellValue());
				   
			}
		
			if (r.getCell(16)!=null) {
				 reports.setTags(r.getCell(16).getStringCellValue());
				  
			}
			if (r.getCell(17)!=null) {
				  reports.setRemarks(r.getCell(17).getStringCellValue());
				    
			}
			if (r.getCell(18)!=null) {
				reports.setNetType(r.getCell(18).getStringCellValue());		
			}
		   reports.setCreateBy(user.getId());	
		    reports.setUpdateBy(user.getId());
		    reports.setCreateDate(new Date());
		    reports.setUpdateDate(new Date());		      
	    re=	     searchReportsService.addExcle(reports);
	    if (r.getCell(15)!=null) {
		     if (StringUtils.isNotBlank(r.getCell(15).getStringCellValue())) {
		    	 String [] dim=r.getCell(15).getStringCellValue().split("/");
		    	 for (String dimm : dim) {//循环维度，查单个维度是否存在表中
					Dict dict= reportMapper.getByname(dimm);
					if (dict!=null) {
						SearchReportsDimension reportsDimension= new SearchReportsDimension();
						 reportsDimension.setId(GenerationSequenceUtil.getUUID());
						 reportsDimension.setReportId(ids);
						 reportsDimension.setDicId(dict.getId());
						 if(user!=null&&StringUtils.isNotBlank(user.getId())){
						 reportsDimension.setCreateBy(user.getId());
						 reportsDimension.setUpdateBy(user.getId());
						}
						
						 reportsDimension.setCreateDate(new Date());
						
						 reportsDimension.setUpdateDate(new Date());
					re=	ReportsDimensionService.add(reportsDimension);
					
					}else {
						Dict dictt=new Dict();
						dictt.setId(GenerationSequenceUtil.getUUID());
						dictt.setValue(dimm);
						dictt.setLabel(dimm);
						dictt.setType("weidu_type");
						dictt.setDescription(dimm);
						if(user!=null&&StringUtils.isNotBlank(user.getId())){
						dictt.setCreateBy(user.getId());
						dictt.setUpdateBy(user.getId());
						}
						
						dictt.setCreateDate(new Date());
						
						dictt.setUpdateDate(new Date());
				re=		reportMapper.add(dictt);
					 SearchReportsDimension reportsDimension= new SearchReportsDimension();
						 reportsDimension.setId(GenerationSequenceUtil.getUUID());
						 reportsDimension.setReportId(ids);
						 reportsDimension.setDicId(dictt.getId());
						
						 reportsDimension.setCreateDate(new Date());
						 if(user!=null&&StringUtils.isNotBlank(user.getId())){
						 reportsDimension.setCreateBy(user.getId());	
						 reportsDimension.setUpdateBy(user.getId());
						}
						 
						 reportsDimension.setUpdateDate(new Date());
				re=		ReportsDimensionService.add(reportsDimension);
					}
				}
				
			}
		   
		  }
	    if (r.getCell(6)!=null) {
		     if (StringUtils.isNotBlank(r.getCell(6).getStringCellValue())) {
		    	 String index []=r.getCell(6).getStringCellValue().split("/");
		    	 for (String str : index) {
		    		 //查询指标 新增关联关系
		    	SearchIndex index2=	 indexService.getByName(str);
		    	if (index2!=null) {
		    		SearchReportsIndex reportsIndex=new SearchReportsIndex();
		    		reportsIndex.setId(GenerationSequenceUtil.getUUID());
		    		reportsIndex.setReportId(ids);
		    		reportsIndex.setIndexId(index2.getId());
		    		if(user!=null&&StringUtils.isNotBlank(user.getId())){
		    		reportsIndex.setCreateBy(user.getId());
					reportsIndex.setUpdateBy(user.getId());	
					}
		    		reportsIndex.setCreateDate(new Date());
		    		reportsIndex.setUpdateDate(new Date());
		    		   re=  reportIndexService.add(reportsIndex);
					
				}
					
				}
				
			}
		     
		  }
		     
		     
		  }
	}
	   
	       return new PublicResult<>(PublicResultConstant.SUCCESS, re);  
	    }
	
	
	/**
	 * 指标导入
	 * @author lixiaoyi
	 * @date 2018年6月22日 下午2:29:22
	 * @TODO
	 */
	@RequestMapping(value = "/index/excle", produces = { "application/json;charset=UTF-8" },method=RequestMethod.POST)
	@Log(description="API接口:/index/excle")
	public Object addIndexExcle(HttpServletRequest request,
							@RequestParam(value="file",required=false)
							MultipartFile file) 
							throws IOException{
	     User user= getLoginUser(request); 
	     int re=0;
	     Workbook wb0 =null;
	     if (file!=null) {
	    	//根据指定的文件输入流导入Excel从而产生Workbook对象
	    	 if (ExcleUtils.isExcel2007(file.getOriginalFilename())) {
	    		 wb0 = new XSSFWorkbook(file.getInputStream());
			}else {
				wb0 = new HSSFWorkbook(file.getInputStream());
			}
		  //获取Excel文档中的第一个表单
		  Sheet sht0 = wb0.getSheetAt(0);
		  Sheet sht1 = wb0.getSheetAt(1);
		 
		  //对Sheet中的每一行进行迭代
		  for (Row r : sht0) {
		          //如果当前行的行号（从0开始）未达到2（第三行）则从新循环
		  if(r.getRowNum()<1){
		  continue;
		  }
		  String ids=GenerationSequenceUtil.getUUID();
		  SearchIndex index=new SearchIndex();
		    index.setId(ids);
		    if (r.getCell(0)!=null) {
		    index.setName(r.getCell(0).getStringCellValue());	
			}
		    if (r.getCell(2)!=null) {
		    index.setCode(r.getCell(2).getStringCellValue());	   
		    }
		    if (r.getCell(3)!=null) {
		    index.setScreenUrl(r.getCell(3).getStringCellValue());	    
		    }
		    if (r.getCell(4)!=null) {
		    index.setDescs(r.getCell(4).getStringCellValue());			    
		    }
		    if (r.getCell(5)!=null) {
		    index.setShowTable(r.getCell(5).getStringCellValue());			   
		    }
		    if (r.getCell(6)!=null) {
		    index.setPlat(r.getCell(6).getStringCellValue());			    
		    }
		    if (r.getCell(7)!=null) {
		    index.setDingyi(r.getCell(7).getStringCellValue());   
		    }
		    if (r.getCell(8)!=null) {
		    index.setIndexType(r.getCell(8).getStringCellValue());	   																						
		    }
		    if (r.getCell(9)!=null) {
		    index.setIndexFenlei(r.getCell(9).getStringCellValue());				   
		    }
		    if (r.getCell(10)!=null) {
		    index.setExpression(r.getCell(10).getStringCellValue());	   
		    }
		    if (r.getCell(11)!=null) {
		    index.setWorkflow(r.getCell(11).getStringCellValue());	  
		    }
		    if (r.getCell(12)!=null) {
		    index.setCoordinator(r.getCell(12).getStringCellValue());				   
		    }
		    if (r.getCell(13)!=null) {
		    index.setGetDataMagic(r.getCell(13).getStringCellValue());				   
		    }
		    if (r.getCell(14)!=null) {
		    index.setGetDataTime(r.getCell(14).getStringCellValue());		   																			
		    }
		    if (r.getCell(15)!=null) {
		    index.setGetDataQuart(r.getCell(15).getStringCellValue());
		    }
		    if (r.getCell(16)!=null) {
		    index.setTimeLong(r.getCell(16).getStringCellValue());				
		    }
		    if (r.getCell(17)!=null) {
		    index.setGetDataType(r.getCell(17).getStringCellValue());    
		    }
		    if (r.getCell(18)!=null) {
		    index.setMaskInterfaceUser(r.getCell(18).getStringCellValue());   																
		    }
		    if (r.getCell(19)!=null) {
			index.setMaskInterfaceUserWorknum(r.getCell(19).getStringCellValue());   																
			}
		    if (r.getCell(20)!=null) {
			index.setMaskInterfaceUserEmail(r.getCell(20).getStringCellValue());   																
			}
		    if (r.getCell(21)!=null) {
		    index.setItInterfaceUser(r.getCell(21).getStringCellValue());	
		    }
		    if (r.getCell(22)!=null) {
			index.setItInterfaceUserWorknum(r.getCell(22).getStringCellValue());   																
			}
		    if (r.getCell(23)!=null) {
			index.setItInterfaceUserEmail(r.getCell(23).getStringCellValue());   																
			}
		    if (r.getCell(24)!=null) {
			    index.setOfferUser(r.getCell(24).getStringCellValue());   																
			}
		    if (r.getCell(25)!=null) {
		    index.setDataStatus(r.getCell(25).getStringCellValue());	
		    }
		    if (r.getCell(26)!=null) {
		    index.setCategory1(r.getCell(26).getStringCellValue());	
		    }
		    if (r.getCell(27)!=null) {
		    index.setCategory2(r.getCell(27).getStringCellValue());	
		    }
		    if (r.getCell(28)!=null) {
		    index.setCategory3(r.getCell(28).getStringCellValue());	
		    }
		    if (r.getCell(29)!=null) {
		    index.setTags(r.getCell(29).getStringCellValue()); 
		    }
		    if (r.getCell(30)!=null) {
			index.setViewNum((int) r.getCell(30).getNumericCellValue());
		    }
		    if (r.getCell(31)!=null) {
		    index.setZanNum((int)r.getCell(31).getNumericCellValue());	
		    }
		    if (r.getCell(32)!=null) {	 
			index.setShareNum((int)r.getCell(32).getNumericCellValue());
		    }
		    if (r.getCell(33)!=null) {
		    index.setRemarks(r.getCell(33).getStringCellValue());
		    }
		    if (r.getCell(37)!=null) {
			    index.setAccuracyStandard(r.getCell(37).getStringCellValue());
			    }
		    
		    if (r.getCell(38)!=null) {
			    index.setSql(r.getCell(38).getStringCellValue());
			    }
		    
		   index.setCreateBy(user.getId());
		   index.setUpdateBy(user.getId());
		   index.setCreateDate(new Date());
	       index.setUpdateDate(new Date());	
	       //新增指标前查询指标名字是否有重复 ，有重复删除 重新新增
	       SearchIndex index2= indexService.getName(index.getName());
	       if (index2!=null) {
		   re= indexService.delete(index2.getId());	
		}
	       List<Dict> dicts = dictService.getAll("index_code");
		     for (Dict dict : dicts) {
				  if (dict.getDescription().equals(index.getPlat())) {
					  //循环 查询指标中的编码是否重复
					  for (int i = 0; i < 10000; i++) {
						  //生成随机4位数	  
				          int a= (int) ((Math.random()*9+1)*1000);
				          String indexCode=dict.getValue()+String.valueOf(a);
				          //查询数据库是否有一样的
				          List<SearchIndex> indexsCode= indexService.getbyCode(indexCode);
				          if (indexsCode.size()==0) {
								index.setCode(indexCode);
								break;
					   }else {
						continue;
				     	}
				     }
					  break;
				}else {
					continue;
				}  
			}
		     //全空列不导入
		     if (index!=null) {
		    	 re= indexService.addIndex(index);
			}
		     
		 
		 if (r.getCell(1)!=null) {
		 if (StringUtils.isNotBlank(r.getCell(1).getStringCellValue())) {
		     ScanSubjectArea subjectArea= scanSubjectAreaService.selectbyname(r.getCell(1).getStringCellValue());
		   if (subjectArea!=null) {
			   //主题域不为空则新增关联关系
			   ScanSubjectAreaIndex areaIndex=new ScanSubjectAreaIndex();
			   areaIndex.setId(GenerationSequenceUtil.getUUID());
			   areaIndex.setSubjectAreaId(subjectArea.getId());
			   areaIndex.setIndexId(ids);
			   if(user!=null&&StringUtils.isNotBlank(user.getId())){
			   areaIndex.setCreateBy(user.getId());
			   areaIndex.setUpdateBy(user.getId());	
			}
			   areaIndex.setCreateDate(new Date());
			   areaIndex.setUpdateDate(new Date());
			  re=  areaIndexService.addExcle(areaIndex);
			   
			
		}else {
			//新增主题域
			ScanSubjectArea scanSubject=new ScanSubjectArea();
			scanSubject.setId(GenerationSequenceUtil.getUUID());
			scanSubject.setName(r.getCell(1).getStringCellValue());
			if(user!=null&&StringUtils.isNotBlank(user.getId())){
			scanSubject.setCreateBy(user.getId());
			scanSubject.setUpdateBy(user.getId());	
			}
			scanSubject.setCreateDate(new Date());
			scanSubject.setUpdateDate(new Date());
		 re=	 scanSubjectAreaService.addExcle(scanSubject);
			//新增关联关系
			   ScanSubjectAreaIndex areaIndex=new ScanSubjectAreaIndex();
			   areaIndex.setId(GenerationSequenceUtil.getUUID());
			   areaIndex.setSubjectAreaId(scanSubject.getId());
			   areaIndex.setIndexId(ids);
			   if(user!=null&&StringUtils.isNotBlank(user.getId())){
			   areaIndex.setCreateBy(user.getId());
			   areaIndex.setUpdateBy(user.getId());	
			}
			   areaIndex.setCreateDate(new Date());
			   areaIndex.setUpdateDate(new Date());
			   re= areaIndexService.addExcle(areaIndex);
		}
				
	}    
		  }  
		 //应用系统及目录
		 if (r.getCell(35)!=null) {
			 int i=0;
	         String system= r.getCell(35).getStringCellValue();
	           if (StringUtils.isNotBlank(system)){
				  String [] usersystem= system.split(",");
				  for (String string : usersystem) {
					  //查询应用系统id
					Dict dict=  dictService.selectByone(string);
					 SearchIndexPlat plat=new SearchIndexPlat();
					 if (dict==null) {
						 Dict dictt=new Dict();
						 dictt.setId(GenerationSequenceUtil.getUUID());
						 dictt.setValue(string);
						 dictt.setLabel(string);
						 dictt.setType("userSystem");
						 dictt.setDescription(string);
						 dictt.setCreateDate(new Date());
						 dictt.setUpdateDate(new Date()); 
						 dictService.add(dictt);
						  plat.setId(GenerationSequenceUtil.getUUID());
						  plat.setDicId(dictt.getId());
						  plat.setIndexId(index.getId());
					}else {
						  plat.setId(GenerationSequenceUtil.getUUID());
						  plat.setDicId(dict.getId());
						  plat.setIndexId(index.getId());
					}    
					 
					
					  //一个系统对应一个指标目录 获取指标目录
					  if (r.getCell(36)!=null) {
						  String cata= r.getCell(36).getStringCellValue();
							if (cata!=null) {
							String cate []=	cata.split("/");
							plat.setContents(cate[i]);
							  i++;
							}
					}
					
					plat.setCreateDate(new Date());
					plat.setCreateBy(index.getCreateBy());
					plat.setUpdateDate(new Date());
					plat.setUpdateBy(index.getUpdateBy());
				   re=  platMapper.add(plat);
					
				}
			}   
			
		}
		  if (r.getCell(34)!=null) {
			
		
		 //维度关系
		    if (StringUtils.isNotBlank(r.getCell(34).getStringCellValue())) {
		    	String  weidu [] =r.getCell(34).getStringCellValue().split("/");
		    	for (String stri : weidu) {
					
				
		    	Dict dict=dictService.selectByone(stri);
		    	if (dict!=null) {
		    	SearchIndexDimension indexDimension=new SearchIndexDimension();
		    	indexDimension.setId(GenerationSequenceUtil.getUUID());
		    	indexDimension.setReportId(ids);
		    	indexDimension.setDicId(dict.getId());
		    	if(user!=null&&StringUtils.isNotBlank(user.getId())){
		    	indexDimension.setCreateBy(user.getId());
				indexDimension.setUpdateBy(user.getId());	
				}
		    	indexDimension.setCreateDate(new Date());
		    	indexDimension.setUpdateDate(new Date());
		   re=  	indexService.add(indexDimension);
					
				}else {
					Dict dictt=new Dict();
					dictt.setId(GenerationSequenceUtil.getUUID());
					dictt.setValue(stri);
					dictt.setLabel(stri);
					dictt.setType("weidu_type");
					dictt.setDescription(stri);
					if(user!=null&&StringUtils.isNotBlank(user.getId())){
					dictt.setCreateBy(user.getId());
					dictt.setUpdateBy(user.getId());
					}
					
					dictt.setCreateDate(new Date());
					
					dictt.setUpdateDate(new Date());
				 re=   dictService.add(dictt);
				      SearchIndexDimension indexDimension=new SearchIndexDimension();
				    	indexDimension.setId(GenerationSequenceUtil.getUUID());
				    	indexDimension.setReportId(ids);
				    	indexDimension.setDicId(dictt.getId());
				    	if(user!=null&&StringUtils.isNotBlank(user.getId())){
				    	indexDimension.setCreateBy(user.getId());
						indexDimension.setUpdateBy(user.getId());	
						}
				    	indexDimension.setCreateDate(new Date());
				    	indexDimension.setUpdateDate(new Date());
				    re=	indexService.add(indexDimension);
					
				}
		    	}
			}
		  }
	} 
	    
		  
		  //取第二页sheet
		  for (Row r : sht1) {

	          //如果当前行的行号（从0开始）未达到2（第三行）则从新循环
	     if(r.getRowNum()<1){
	     continue;
	    }
	     
	   if (r.getCell(0)!=null&&r.getCell(1)!=null&&r.getCell(2)!=null&&r.getCell(3)!=null&&r.getCell(8)!=null
			 &&r.getCell(9)!=null) {
		     String indexName= 	r.getCell(0).getStringCellValue();
		     String dburl= 	r.getCell(1).getStringCellValue();
		     String dbname= 	r.getCell(2).getStringCellValue();
		     String password= 	r.getCell(3).getStringCellValue();		    
		     String tableName= 	r.getCell(8).getStringCellValue();
		     String column= 	r.getCell(9).getStringCellValue();
		     String remarks=null;
		     String dbtype=null;
		     String name=null;
		     String enname=null;
		     if (r.getCell(4)!=null) {
		    remarks=	 r.getCell(4).getStringCellValue();
			}
		     if (r.getCell(5)!=null) {
		    dbtype = 	r.getCell(5).getStringCellValue();	
			}
		    if (r.getCell(6)!=null) {
		    name= 	r.getCell(6).getStringCellValue();		
			} 
		     if (r.getCell(7)!=null) {
		    enname= 	r.getCell(7).getStringCellValue();	
			}
		  
	
	    //数据关系
	    if (StringUtils.isNotBlank(dburl)||StringUtils.isNotBlank(password)
	    		||StringUtils.isNotBlank(dbname)) {
	    	
	    	//先查询指标id
	    SearchIndex index=	   indexService.getByName(indexName);
	    	AdminDatasourceConfig config=new AdminDatasourceConfig();
	    	config.setDbUrl(dburl);
	    	config.setDbName(dbname);
	    	config.setDbPassword(password);
	    AdminDatasourceConfig config2=  	indexService.getConfig(config);
                if (config2!=null) {//如果数据库存在 则 查表是否存在
                	if (StringUtils.isNotBlank(tableName)) {
                		AdminDataContent content=new AdminDataContent();
                		content.setDataSourceId(config2.getId());
                		content.setTableName(tableName);
                AdminDataContent content2=indexService.getContent(content);
                if (content2!=null) {
             
                	if (StringUtils.isNotBlank(column)) {
						
					
                	 String columnString []=column.split(",");
                	 for (String col : columnString) {
                		 //循环字段 看表中是否存在 不存在则新增
                	 AdminDataContentDetail detail=new AdminDataContentDetail();
                  	   detail.setContentId(content2.getId());
                  	   detail.setDatasourceId(config2.getId());
                  	   detail.setColumnName(col);
                 AdminDataContentDetail detail2= 	   indexService.getDetail(detail);
				if (detail2!=null) {
					ScanIndexTableRelative relative=new ScanIndexTableRelative();
					relative.setId(GenerationSequenceUtil.getUUID());
					relative.setIndexId(index.getId());
					relative.setDataSourceId(config2.getId());
					relative.setContentId(content2.getId());
					relative.setContentDeatilId(detail2.getId());
					if(user!=null&&StringUtils.isNotBlank(user.getId())){
						relative.setCreateBy(user.getId());
						relative.setUpdateBy(user.getId());
						
					}
					relative.setCreateDate(new Date());
					relative.setUpdateDate(new Date());
				 re=	indexService.addRelative(relative);
					
				}else {
					AdminDataContentDetail detail3=new AdminDataContentDetail();
					detail3.setId(GenerationSequenceUtil.getUUID());
					detail3.setDatasourceId(config2.getId());
					detail3.setContentId(content2.getId());
					detail3.setColumnName(col);
					if(user!=null&&StringUtils.isNotBlank(user.getId())){
						detail3.setCreateBy(user.getId());
						detail3.setUpdateBy(user.getId());	
					}
					detail3.setCreateDate(new Date());
					detail3.setUpdateDate(new Date());
				 re=	indexService.addDetail(detail3);
					ScanIndexTableRelative relative=new ScanIndexTableRelative();
					relative.setId(GenerationSequenceUtil.getUUID());
					relative.setIndexId(index.getId());
					relative.setDataSourceId(config2.getId());
					relative.setContentId(content2.getId());
					relative.setContentDeatilId(detail3.getId());
					if(user!=null&&StringUtils.isNotBlank(user.getId())){
						relative.setCreateBy(user.getId());
						relative.setUpdateBy(user.getId());
						
					}
					relative.setCreateDate(new Date());
					relative.setUpdateDate(new Date());
				 re=	indexService.addRelative(relative);
					
				}
                	 
                	 }
                	}
                
				}else {//表新增
					AdminDataContent content3=new AdminDataContent();
					content3.setId(GenerationSequenceUtil.getUUID());
					content3.setDataSourceId(config2.getId());
					content3.setTableName(tableName);
					if(user!=null&&StringUtils.isNotBlank(user.getId())){
					content3.setCreateBy(user.getId());
					content3.setUpdateBy(user.getId());		
					}
					content3.setCreateDate(new Date());
					content3.setUpdateDate(new Date());
				 re=	indexService.addContent(content3);
				if (StringUtils.isNotBlank(column)) {
					
				
				//拆分字段
					String colun[]=column.split(",");
				for (String colum : colun) {
					AdminDataContentDetail detail3=new AdminDataContentDetail();
					detail3.setId(GenerationSequenceUtil.getUUID());
					detail3.setDatasourceId(config2.getId());
					detail3.setContentId(content3.getId());
					detail3.setColumnName(colum);
					if(user!=null&&StringUtils.isNotBlank(user.getId())){
						detail3.setCreateBy(user.getId());
						detail3.setUpdateBy(user.getId());	
					}
					detail3.setCreateDate(new Date());
					detail3.setUpdateDate(new Date());
				 re=	indexService.addDetail(detail3);
					ScanIndexTableRelative relative=new ScanIndexTableRelative();
					relative.setId(GenerationSequenceUtil.getUUID());
					relative.setIndexId(index.getId());
					relative.setDataSourceId(config2.getId());
					relative.setContentId(content3.getId());
					relative.setContentDeatilId(detail3.getId());
					if(user!=null&&StringUtils.isNotBlank(user.getId())){
						relative.setCreateBy(user.getId());
						relative.setUpdateBy(user.getId());
						
					}
					relative.setCreateDate(new Date());
					relative.setUpdateDate(new Date());
				 re=	indexService.addRelative(relative);
				}
				}
				}
						
                	}	
                	//数据库与列关系对照完毕 修改指标增加原数据源id
                	SearchIndex indexSearch=new SearchIndex();
                	indexSearch.setSourceTableId(config2.getId());
                	indexSearch.setId(index.getId());
                  re=  	indexService.update(indexSearch);
                	}else {//如果数据库不存在 则全部新增
						AdminDatasourceConfig config3=new AdminDatasourceConfig();
						config3.setId(GenerationSequenceUtil.getUUID());
						config3.setDbUrl(dburl);
						config3.setDbName(dbname);
						config3.setDbPassword(password);
						config3.setDbType(dbtype);
						config3.setRemarks(remarks);
						config3.setName(name);
						config3.setEnname(enname);
						if(user!=null&&StringUtils.isNotBlank(user.getId())){
						config3.setCreateBy(user.getId());
						config3.setUpdateBy(user.getId());
							
						}
						config3.setCreateDate(new Date());
						config3.setUpdateDate(new Date());
            		 re=	indexService.addConfig(config3);
            		
            		AdminDataContent content3=new AdminDataContent();
					content3.setId(GenerationSequenceUtil.getUUID());
					content3.setDataSourceId(config3.getId());
					content3.setTableName(tableName);
					if(user!=null&&StringUtils.isNotBlank(user.getId())){
					content3.setCreateBy(user.getId());
					content3.setUpdateBy(user.getId());		
					}
					content3.setCreateDate(new Date());
					content3.setUpdateDate(new Date());
				 re=	indexService.addContent(content3);
				if (StringUtils.isNotBlank(column)) {
					
				
				//拆分字段
					String colun[]=column.split(",");
				for (String colum : colun) {
					AdminDataContentDetail detail3=new AdminDataContentDetail();
					detail3.setId(GenerationSequenceUtil.getUUID());
					detail3.setDatasourceId(config3.getId());
					detail3.setContentId(content3.getId());
					detail3.setColumnName(colum);
					if(user!=null&&StringUtils.isNotBlank(user.getId())){
						detail3.setCreateBy(user.getId());
						detail3.setUpdateBy(user.getId());	
					}
					detail3.setCreateDate(new Date());
					detail3.setUpdateDate(new Date());
				 re=	indexService.addDetail(detail3);
					ScanIndexTableRelative relative=new ScanIndexTableRelative();
					relative.setId(GenerationSequenceUtil.getUUID());
					relative.setIndexId(index.getId());
					relative.setDataSourceId(config3.getId());
					relative.setContentId(content3.getId());
					relative.setContentDeatilId(detail3.getId());
					if(user!=null&&StringUtils.isNotBlank(user.getId())){
						relative.setCreateBy(user.getId());
						relative.setUpdateBy(user.getId());
						
					}
					relative.setCreateDate(new Date());
					relative.setUpdateDate(new Date());
				 re=	indexService.addRelative(relative);
						
					}
				}
				SearchIndex indexSearch=new SearchIndex();
            	indexSearch.setSourceTableId(config3.getId());
            	indexSearch.setId(index.getId());
              re=  	indexService.update(indexSearch);	
				}			
		}
	   }
	    
		  
			  
			  
			  
			  /*
	          //如果当前行的行号（从0开始）未达到2（第三行）则从新循环
	     if(r.getRowNum()<1){
	     continue;
	    }
	     
	   if (r.getCell(0)!=null&&r.getCell(1)!=null&&r.getCell(2)!=null&&r.getCell(3)!=null&&r.getCell(8)!=null
			   &&r.getCell(9)!=null&&r.getCell(10)!=null) {
		     String indexName= 	r.getCell(0).getStringCellValue();
		     String ydburl= 	r.getCell(1).getStringCellValue();
		     String ydbname= 	r.getCell(2).getStringCellValue();
		     String ypassword= 	r.getCell(3).getStringCellValue();	
		     String mdburl= 	r.getCell(8).getStringCellValue();
		     String mdbname= 	r.getCell(9).getStringCellValue();
		     String mpassword= 	r.getCell(10).getStringCellValue();	
		     String yremarks=null;
		     String ydbtype=null;
		     String yname=null;
		     String yenname=null;
		     String mremarks=null;
		     String mdbtype=null;
		     String mname=null;
		     String menname=null;
		     if (r.getCell(4)!=null) {
		    yremarks=	 r.getCell(4).getStringCellValue();
			}
		     if (r.getCell(5)!=null) {
		    ydbtype = 	r.getCell(5).getStringCellValue();	
			}
		    if (r.getCell(6)!=null) {
		    yname= 	r.getCell(6).getStringCellValue();		
			} 
		     if (r.getCell(7)!=null) {
		    yenname= 	r.getCell(7).getStringCellValue();	
			}
            if (r.getCell(11)!=null) {
		    mremarks=	 r.getCell(4).getStringCellValue();
			}
		     if (r.getCell(12)!=null) {
		    mdbtype = 	r.getCell(5).getStringCellValue();	
			}
		    if (r.getCell(13)!=null) {
		    mname= 	r.getCell(6).getStringCellValue();		
			} 
		     if (r.getCell(14)!=null) {
		    menname= 	r.getCell(7).getStringCellValue();	
			}  
		   //先查询指标id 
		  SearchIndex index=	   indexService.getByName(indexName);
	    //数据关系
	    if (StringUtils.isNotBlank(ydburl)||StringUtils.isNotBlank(ypassword)
	    		||StringUtils.isNotBlank(ydbname)) {
	    
	    	AdminDatasourceConfig config=new AdminDatasourceConfig();
	    	config.setDbUrl(ydburl);
	    	config.setDbName(ydbname);
	    	config.setDbPassword(ypassword);
	    AdminDatasourceConfig config2=  	indexService.getConfig(config);
                if (config2!=null) {//如果数据库存在 则修改指标
                   SearchIndex index2=new SearchIndex();
                   index2.setId(index.getId());
                   index2.setSourceTableId(config2.getId());
                	indexService.update(index);
                	}else {//如果数据库不存在 则全部新增
						AdminDatasourceConfig config3=new AdminDatasourceConfig();
						config3.setId(GenerationSequenceUtil.getUUID());
						config3.setDbUrl(ydburl);
						config3.setDbName(ydbname);
						config3.setDbPassword(ypassword);
						config3.setDbType(ydbtype);
						config3.setRemarks(yremarks);
						config3.setName(yname);
						config3.setEnname(yenname);
						if(user!=null&&StringUtils.isNotBlank(user.getId())){
						config3.setCreateBy(user.getId());
						config3.setUpdateBy(user.getId());
							
						}
						config3.setCreateDate(new Date());
						config3.setUpdateDate(new Date());
            		 re=	indexService.addConfig(config3);
            		 SearchIndex index2=new SearchIndex();
                     index2.setId(index.getId());
                     index2.setSourceTableId(config3.getId());
                  	indexService.update(index);				
				}			
		}
	    
	    if (StringUtils.isNotBlank(mdburl)||StringUtils.isNotBlank(mpassword)
	    		||StringUtils.isNotBlank(mdbname)) {
	    
	    	AdminDatasourceConfig config=new AdminDatasourceConfig();
	    	config.setDbUrl(mdburl);
	    	config.setDbName(mdbname);
	    	config.setDbPassword(mpassword);
	    AdminDatasourceConfig config2=  	indexService.getConfig(config);
                if (config2!=null) {//如果数据库存在 则修改指标
                   SearchIndex index2=new SearchIndex();
                   index2.setId(index.getId());
                   index2.setSourceTableId(config2.getId());
                	indexService.update(index);
                	}else {//如果数据库不存在 则全部新增
						AdminDatasourceConfig config3=new AdminDatasourceConfig();
						config3.setId(GenerationSequenceUtil.getUUID());
						config3.setDbUrl(mdburl);
						config3.setDbName(mdbname);
						config3.setDbPassword(mpassword);
						config3.setDbType(mdbtype);
						config3.setRemarks(mremarks);
						config3.setName(mname);
						config3.setEnname(menname);
						if(user!=null&&StringUtils.isNotBlank(user.getId())){
						config3.setCreateBy(user.getId());
						config3.setUpdateBy(user.getId());
							
						}
						config3.setCreateDate(new Date());
						config3.setUpdateDate(new Date());
            		 re=	indexService.addConfig(config3);
            		 SearchIndex index2=new SearchIndex();
                     index2.setId(index.getId());
                     index2.setSourceTableId(config3.getId());
                  	indexService.update(index);				
				}			
		}
	   }
	    
		  */}
		  
	     }
		 return new PublicResult<>(PublicResultConstant.SUCCESS, re);  
	}
	
	
	
	
	
	
	@GetMapping(value = "/data/toIndexExcelExport", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/data/toIndexExcelExport")
	public Object toDataExport(HttpServletRequest request, HttpServletResponse response) {
		String title="";
		String[] rowsName = new String[]{"指标名称","指标主题域","指标编码","屏幕地址","指标描述","显示表","平台","定义","指标类型","指标分类","计算公式","工作流","调度","取数逻辑","取数时间","取数频次","取数时长","取数方式","业务接口人","业务接口人工号","业务接口人邮箱","技术接口人","技术接口人工号","技术接口人邮箱","接口提供者","数据状态","分类1","分类2","分类3","标签","浏览量","点赞量","分享量","备注","指标维度","所属系统","目录","指标准确性标准"};
		String[] rowsName2 = new String[]{"指标名称","数据地址","数据库用户名","原系统","数据源类型","数据库名称","数据库英文名称","显示表","使用字段","目标表"};
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(); // 创建工作表
		HSSFSheet sheet2 =workbook.createSheet();
		workbook.setSheetName(0, "指标");
		workbook.setSheetName(1, "数据信息");
		int columnNum = rowsName.length;
		int columnNum2 = rowsName2.length;
		HSSFRow rowRowName = sheet.createRow(0); // 在索引0的位置创建行(最顶端的行开始的第二行)
		HSSFRow rowRowName2 = sheet2.createRow(0); // 在索引0的位置创建行(最顶端的行开始的第二行)

		rowRowName.setHeight((short) (25 * 30)); // 设置高度

		// 将列头设置到sheet的单元格中
		for (int n = 0; n < columnNum; n++) {
			HSSFCell cellRowName = rowRowName.createCell(n); // 创建列头对应个数的单元格
			cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING); // 设置列头单元格的数据类型
			HSSFRichTextString text = new HSSFRichTextString(rowsName[n]);
			cellRowName.setCellValue(text); // 设置列头单元格的值
			
		}
		for (int n = 0; n < columnNum2; n++) {
			HSSFCell cellRowName = rowRowName2.createCell(n); // 创建列头对应个数的单元格
			cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING); // 设置列头单元格的数据类型
			HSSFRichTextString text = new HSSFRichTextString(rowsName2[n]);
			cellRowName.setCellValue(text); // 设置列头单元格的值
			
		}
		try {
			OutputStream output = response.getOutputStream();
			response.reset();
			response.setHeader("Content-disposition",
					"attachment; filename=" + new String(title.getBytes(), "iso-8859-1")
							+ new SimpleDateFormat("yyyyMMdd").format(new Date()).toString() + ".xls");
			response.setContentType("application/msexcel");
			workbook.write(output);
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.FAILED, "excel导出失败!");
		}
		return new PublicResult<>(PublicResultConstant.SUCCESS, null);
		}
	
	
	/**
	 * 根据有规则的指标编码字符串获取其对应的指标实体
	 * @author lzg 2018/8/20
	 * @param request 
	 * @return
	 */
	@RequestMapping(value = "/searchIndex/addplat", produces = { "application/json;charset=UTF-8" })
	@Log(description="API接口:/searchIndex/addplat")
	public Object addplat(String indexCodes){  
		
		 List<Dict> dicts = dictService.getAll("index_code");
	     for (Dict dict : dicts) {
			  if (dict.getDescription().equals(indexCodes)) {
				  //循环 查询指标中的编码是否重复
				  for (int i = 0; i < 10000; i++) {
					  //生成随机4位数	  
			          int a= (int) ((Math.random()*9+1)*1000);
			          String indexCode=dict.getValue()+String.valueOf(a);
			          //查询数据库是否有一样的
			          List<SearchIndex> indexsCode= indexService.getbyCode(indexCode);
			          if (indexsCode.size()==0) {
							//index.setCode(indexCode);
			        	  break;
				   }else {
					continue;
			     	}
			     }	  
			}else {
				continue;
			}  
		}
	
		return new PublicResult<>(PublicResultConstant.SUCCESS, null);
		
	}
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy.MM.dd");
	@GetMapping(value = "/searchIndex/doExport", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/searchIndex/doExport")
	public Object list(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="name",required = false) String name,
			@RequestParam(value="code",required = false) String code,@RequestParam(value="areaId",required = false) String areaId,
			@RequestParam(value="entering",required = false)String entering) {
		String fileName="指标明细-"+sdf.format(new Date())+".xlsx";
		SearchIndex index = new SearchIndex();
		index.setName(name);
		index.setCode(code);
		index.setAreaId(areaId);
		index.setEntering(entering);
		User user = getLoginUser(request);
		if (user != null) {
			if (!"1".equals(user.getUserType())) {
				index.setCreateBy(user.getId());
			}
		}
		List<SearchIndex> indexs = indexService.getAll(index);
		SearchIndexExport export= new SearchIndexExport(indexs);
		 try {
			 fileName=new String(fileName.getBytes("GBK"), "ISO-8859-1");
			export.doExport(response, fileName);
			return new PublicResult<>(PublicResultConstant.SUCCESS, "导出成功");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.SUCCESS, "导出失败");
		}
		
		
	}
	
	@GetMapping(value = "/searchIndex/Export", produces = { "application/json;charset=UTF-8" })
	@Log(description = "API接口:/searchIndex/Export")
	public Object export(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="name",required = false) String name,
			@RequestParam(value="code",required = false) String code,@RequestParam(value="areaId",required = false) String areaId,
			@RequestParam(value="entering",required = false)String entering) {
		String fileName="系统对照指标与报表详情-"+sdf.format(new Date())+".xlsx";
		SearchIndex index = new SearchIndex();
		index.setName(name);
		index.setCode(code);
		index.setAreaId(areaId);
		index.setEntering(entering);
		User user = getLoginUser(request);
		if (user != null) {
			if (!"1".equals(user.getUserType())) {
				index.setCreateBy(user.getId());
			}
		}
		Map<String,Object> map=new HashMap<String, Object>();
		
		// 查询系统 ，及系统下的指标，报表
	    List<Dict> dicts = dictServiceImpl.getAll("userSystem");
	    String [] sheetNames =null ;
	   
	    for (Dict dict : dicts) {
	    	//系统下的报表
	    	List<SearchReports> reports = reportMapper.getBysys(dict.getValue());
	      sheetNames= 	new String[(reports.size())+1]; 
	    	int i=1;
	    	for (SearchReports searchReports : reports) {
	    		List<SearchIndex> searchIndexs = indexService.getbyReport(searchReports.getId());
				//用remarks字段接收系统名称
	    		searchReports.setRemarks(dict.getValue());
	    		//用点赞数字段接收指标个数
				searchReports.setViewNum(searchIndexs.size());
				//用标签字段接收平台名称
				if(searchIndexs!=null&&searchIndexs.size()>0){
					searchReports.setTags(searchIndexs.get(0).getPlat());
				}
				
				map.put(searchReports.getName()+"", searchIndexs);
				sheetNames[i]=searchReports.getName();
				i++;
			} 
		    
		    sheetNames[0]="目录";
		    map.put("sys", reports);
	    }
	   
		//List<SearchIndex> indexs = indexService.getAll(index);
		SysIndexReportExport export= new SysIndexReportExport(map, sheetNames);
		 try {
			 fileName=new String(fileName.getBytes("GBK"), "ISO-8859-1");
			export.doExport(response, fileName, sheetNames);
			return new PublicResult<>(PublicResultConstant.SUCCESS, "导出成功");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new PublicResult<>(PublicResultConstant.SUCCESS, "导出失败");
		}
		
		
	}
	
	
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

